package com.line.demo.opengl.panorama.video360;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.yin.lin.demo.R;


/**
 * Basic Activity to hold {@link MonoscopicView} and render a 360 video in 2D.
 *
 * Most of this Activity's code is related to Android & VR permission handling. The real work is in
 * MonoscopicView.
 *
 * The default intent for this Activity will load a 360 placeholder panorama. For more options on
 * how to load other media using a custom Intent, see {@link MediaLoader}.
 */
public class GoogleVideoActivity extends Activity {
  private static final String TAG = "GoogleVideoActivity";
  private MonoscopicView videoView;

  /**
   * Checks that the appropriate permissions have been granted. Otherwise, the sample will wait
   * for the user to grant the permission.
   *
   * @param savedInstanceState unused in this sample but it could be used to track video position
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_video);
    videoView = (MonoscopicView) findViewById(R.id.video_view);
      Log.e("ying>>", "onCreate before initialize: ");
    videoView.initialize();
      Log.e("ying>>", "onCreate after initialize: ");
      videoView.loadMedia();

  }


    @Override
    protected void onResume() {
        super.onResume();
        videoView.onResume();
    }

    @Override
    protected void onPause() {
        videoView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        videoView.destroy();
        super.onDestroy();
    }
}
