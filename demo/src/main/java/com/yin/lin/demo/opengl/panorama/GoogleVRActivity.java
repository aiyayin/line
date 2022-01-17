//package com.yin.lin.demo.opengl.panorama;
//
//import android.annotation.TargetApi;
//import android.graphics.BitmapFactory;
//import android.os.Build;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.LinearLayout;
//
//import com.example.yingfu.line.R;
//import com.google.vr.sdk.widgets.pano.VrPanoramaEventListener;
//import com.google.vr.sdk.widgets.pano.VrPanoramaView;
//
//import androidx.appcompat.app.AppCompatActivity;
//import line.BaseActivity;
//
//public class GoogleVRActivity extends BaseActivity {
//    private static final String TAG = "ying>GoogleVRActivity:";
//    private VrPanoramaView mVrPanoramaView;
//    private VrPanoramaView.Options paNormalOptions;
//    LinearLayout mLinearLayout;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_google_vr);
//
//        mLinearLayout = findViewById(R.id.main_layout);
//            initVrPaNormalView();
//
//    }
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if(mVrPanoramaView!=null) {
//            mVrPanoramaView.pauseRendering();
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if(mVrPanoramaView!=null) {
//            mVrPanoramaView.resumeRendering();
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        // Destroy the widget and free memory.
//        super.onDestroy();
//        if(mVrPanoramaView!=null) {
//            mVrPanoramaView.shutdown();
//        }
//    }
//
//
//    //初始化VR图片
//    @TargetApi(Build.VERSION_CODES.KITKAT)
//    private void initVrPaNormalView() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            mVrPanoramaView = new VrPanoramaView(this);
//            paNormalOptions = new VrPanoramaView.Options();
//            paNormalOptions.inputType = VrPanoramaView.Options.TYPE_MONO;
//            mVrPanoramaView.setFullscreenButtonEnabled(false); //隐藏全屏模式按钮
//            mVrPanoramaView.setInfoButtonEnabled(false); //设置隐藏最左边信息的按钮
//            mVrPanoramaView.setStereoModeButtonEnabled(false); //设置隐藏立体模型的按钮
//            mVrPanoramaView.setEventListener(new ActivityEventListener()); //设置监听
//            //加载本地的图片源
//            mVrPanoramaView.loadImageFromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ball), paNormalOptions);
//            //设置网络图片源
////        panoWidgetView.loadImageFromByteArray();
//            mLinearLayout.addView(mVrPanoramaView);
//        }
//    }
//
//    private class ActivityEventListener extends VrPanoramaEventListener {
//        @Override
//        public void onLoadSuccess() {//图片加载成功
//            Log.e(TAG, "onLoadSuccess: " );
//        }
//
//
//        @Override
//        public void onLoadError(String errorMessage) {//图片加载失败
//            Log.e(TAG, "onLoadError: "+errorMessage );
//        }
//
//        @Override
//        public void onClick() {//当我们点击了VrPanoramaView 时候触发
//            //         super.onClick();
//            Log.e(TAG, "onClick: ");
//        }
//
//        @Override
//        public void onDisplayModeChanged(int newDisplayMode) {
//            //改变显示模式时候出发（全屏模式和纸板模式）
//            Log.e(TAG, "onDisplayModeChanged: ");
//            super.onDisplayModeChanged(newDisplayMode);
//        }
//    }
//}
