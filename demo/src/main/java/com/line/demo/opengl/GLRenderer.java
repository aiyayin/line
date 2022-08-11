package com.line.demo.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;


import com.yin.lin.demo.R;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by ying.fu.
 * Date: 2019/3/7.
 */
public class GLRenderer implements GLSurfaceView.Renderer {
    private Context context;

    public GLRenderer(Context context) {
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        String vertexShader = ShaderUtils.readRawTextFile(context, R.raw.vertex_shader);
        String fragmentShader = ShaderUtils.readRawTextFile(context, R.raw.fragment_shader);
      int  programId = ShaderUtils.createProgram(vertexShader, fragmentShader);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {

    }
}
