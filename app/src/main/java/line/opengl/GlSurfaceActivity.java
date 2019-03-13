package line.opengl;


import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.example.yingfu.line.R;

import androidx.appcompat.app.AppCompatActivity;

public class GlSurfaceActivity extends AppCompatActivity {
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
