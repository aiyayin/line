/*
 * Copyright 2017 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package line.opengl.panorama.video360;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.AttributeSet;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import androidx.annotation.AnyThread;
import androidx.annotation.BinderThread;
import androidx.annotation.UiThread;
import line.opengl.panorama.video360.rendering.SceneRenderer;

/**
 * Renders a GL scene in a non-VR Activity that is affected by phone orientation and touch input.
 *
 * <p>The two input components are the TYPE_GAME_ROTATION_VECTOR Sensor and a TouchListener. The GL
 * renderer combines these two inputs to render a scene with the appropriate camera orientation.
 *
 * <p>The primary complexity in this class is related to the various rotations. It is important to
 * apply the touch and sensor rotations in the correct order or the user's touch manipulations won't
 * match what they expect.
 */
public final class MonoscopicView extends GLSurfaceView {
  // We handle all the sensor orientation detection ourselves.
  private SensorManager sensorManager;
  private Sensor orientationSensor;
  private PhoneOrientationListener phoneOrientationListener;

  private MediaLoader mediaLoader;
  private Renderer renderer;

  /** Inflates a standard GLSurfaceView. */
  public MonoscopicView(Context context, AttributeSet attributeSet) {
    super(context, attributeSet);
    setPreserveEGLContextOnPause(true);
  }

  /**
   * Finishes initialization. This should be called immediately after the View is inflated.
   */
  public void initialize() {
    mediaLoader = new MediaLoader(getContext());

    // Configure OpenGL.
    renderer = new Renderer(mediaLoader);
    setEGLContextClientVersion(2);
    setRenderer(renderer);
    setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

    // Configure sensors and touch.
    sensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
    // TYPE_GAME_ROTATION_VECTOR is the easiest sensor since it handles all the complex math for
    // fusion. It's used instead of TYPE_ROTATION_VECTOR since the latter uses the mangetometer on
    // devices. When used indoors, the magnetometer can take some time to settle depending on the
    // device and amount of metal in the environment.
    if (sensorManager != null) {
      orientationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
    }
    phoneOrientationListener = new PhoneOrientationListener();

  }

  /** Starts the sensor & video only when this View is active. */
  @Override
  public void onResume() {
    super.onResume();
    // Use the fastest sensor readings.
    sensorManager.registerListener(
        phoneOrientationListener, orientationSensor, SensorManager.SENSOR_DELAY_FASTEST);
  }

  /** Stops the sensors & video when the View is inactive to avoid wasting battery. */
  @Override
  public void onPause() {
    sensorManager.unregisterListener(phoneOrientationListener);
    super.onPause();
  }

  /** Destroys the underlying resources. If this is not called, the MediaLoader may leak. */
  public void destroy() {
    mediaLoader.destroy();
  }

  /** Parses the Intent and loads the appropriate media. */
  public void loadMedia() {
    mediaLoader.loadImage();
  }

  /** Detects sensor events and saves them as a matrix. */
  private class PhoneOrientationListener implements SensorEventListener {
    private final float[] phoneInWorldSpaceMatrix = new float[16];
    private final float[] remappedPhoneMatrix = new float[16];
    private final float[] angles = new float[3];

    @Override
    @BinderThread
    public void onSensorChanged(SensorEvent event) {
      SensorManager.getRotationMatrixFromVector(phoneInWorldSpaceMatrix, event.values);

      // Extract the phone's roll and pass it on to touchTracker & renderer. Remapping is required
      // since we need the calculated roll of the phone to be independent of the phone's pitch &
      // yaw. Any operation that decomposes rotation to Euler angles needs to be performed
      // carefully.
      SensorManager.remapCoordinateSystem(
          phoneInWorldSpaceMatrix,
          SensorManager.AXIS_X, SensorManager.AXIS_MINUS_Z,
          remappedPhoneMatrix);
      SensorManager.getOrientation(remappedPhoneMatrix, angles);
      float roll = angles[2];
//      touchTracker.setRoll(roll);

      // Rotate from Android coordinates to OpenGL coordinates. Android's coordinate system
      // assumes Y points North and Z points to the sky. OpenGL has Y pointing up and Z pointing
      // toward the user.
      Matrix.rotateM(phoneInWorldSpaceMatrix, 0, 90, 1, 0, 0);
      renderer.setDeviceOrientation(phoneInWorldSpaceMatrix, roll);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
  }


  /**
   * Standard GL Renderer implementation. The notable code is the matrix multiplication in
   * onDrawFrame and updatePitchMatrix.
   */
  static class Renderer implements GLSurfaceView.Renderer {
    private final SceneRenderer scene = SceneRenderer.createFor2D();

    // Arbitrary vertical field of view. Adjust as desired.
    private static final int FIELD_OF_VIEW_DEGREES = 90;
    private static final float Z_NEAR = .1f;
    private static final float Z_FAR = 100;
    private final float[] projectionMatrix = new float[16];

    // There is no model matrix for this scene so viewProjectionMatrix is used for the mvpMatrix.
    private final float[] viewProjectionMatrix = new float[16];

    // Device orientation is derived from sensor data. This is accessed in the sensor's thread and
    // the GL thread.
    private final float[] deviceOrientationMatrix = new float[16];

    // Optional pitch and yaw rotations are applied to the sensor orientation. These are accessed on
    // the UI, sensor and GL Threads.
    private final float[] touchPitchMatrix = new float[16];
    private final float[] touchYawMatrix = new float[16];
    private float touchPitch;
    private float deviceRoll;

    // viewMatrix = touchPitch * deviceOrientation * touchYaw.
    private final float[] viewMatrix = new float[16];
    private final float[] tempMatrix = new float[16];
    private final MediaLoader mediaLoader;

    public Renderer( MediaLoader mediaLoader) {
      Matrix.setIdentityM(deviceOrientationMatrix, 0);
      Matrix.setIdentityM(touchPitchMatrix, 0);
      Matrix.setIdentityM(touchYawMatrix, 0);
      this.mediaLoader = mediaLoader;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
      scene.glInit();

      mediaLoader.onGlSceneReady(scene);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
      GLES20.glViewport(0, 0, width, height);
      Matrix.perspectiveM(
          projectionMatrix, 0, FIELD_OF_VIEW_DEGREES, (float) width / height, Z_NEAR, Z_FAR);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
      // Combine touch & sensor data.
      // Orientation = pitch * sensor * yaw since that is closest to what most users expect the
      // behavior to be.
      synchronized (this) {
        Matrix.multiplyMM(tempMatrix, 0, deviceOrientationMatrix, 0, touchYawMatrix, 0);
        Matrix.multiplyMM(viewMatrix, 0, touchPitchMatrix, 0, tempMatrix, 0);
      }

      Matrix.multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
      scene.glDrawFrame(viewProjectionMatrix, 0);
    }

    /** Adjusts the GL camera's rotation based on device rotation. Runs on the sensor thread. */
    @BinderThread
    public synchronized void setDeviceOrientation(float[] matrix, float deviceRoll) {
      System.arraycopy(matrix, 0, deviceOrientationMatrix, 0, deviceOrientationMatrix.length);
      this.deviceRoll = -deviceRoll;
      updatePitchMatrix();
    }

    /**
     * Updates the pitch matrix after a physical rotation or touch input. The pitch matrix rotation
     * is applied on an axis that is dependent on device rotation so this must be called after
     * either touch or sensor update.
     */
    @AnyThread
    private void updatePitchMatrix() {
      // The camera's pitch needs to be rotated along an axis that is parallel to the real world's
      // horizon. This is the <1, 0, 0> axis after compensating for the device's roll.
      Matrix.setRotateM(touchPitchMatrix, 0,
          -touchPitch, (float) Math.cos(deviceRoll), (float) Math.sin(deviceRoll), 0);
    }

    /** Set the pitch offset matrix. */
    @UiThread
    public synchronized void setPitchOffset(float pitchDegrees) {
      touchPitch = pitchDegrees;
      updatePitchMatrix();
    }

    /** Set the yaw offset matrix. */
    @UiThread
    public synchronized void setYawOffset(float yawDegrees) {
      Matrix.setRotateM(touchYawMatrix, 0, -yawDegrees, 0, 1, 0);
    }
  }
}
