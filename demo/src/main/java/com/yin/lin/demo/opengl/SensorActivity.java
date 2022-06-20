package com.yin.lin.demo.opengl;


import android.os.Bundle;
import android.view.View;

import com.yin.lin.demo.R;
import com.yin.line.base.BaseActivity;


public class SensorActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor_activity);
       SensorLayoutWithTrans layout = findViewById(R.id.sensor_ll);
        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.register();
            }
        });
        findViewById(R.id.stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.unregister();
            }
        });

        SensorLayoutWithScroll layout2 = findViewById(R.id.sensor_ll_2);
        findViewById(R.id.start_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout2.register();
            }
        });
        findViewById(R.id.stop_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout2.unregister();
            }
        });


    }


}
