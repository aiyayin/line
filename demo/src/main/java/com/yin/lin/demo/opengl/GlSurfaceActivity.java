package com.yin.lin.demo.opengl;


import android.opengl.GLSurfaceView;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.yin.lin.demo.R;
import com.yin.line.base.BaseActivity;


public class GlSurfaceActivity extends BaseActivity {
    GLSurfaceView mSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.surface_activity);

        mSurfaceView = findViewById(R.id.surface);
        mSurfaceView.setEGLContextClientVersion(2);
        mSurfaceView.setRenderer(new GLRenderer(this));
        mSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }


}
