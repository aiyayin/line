package com.example.yingfu.line;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        findViewById(R.id.line).setOnClickListener(this);
        findViewById(R.id.HoverLinearLayout).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.line:
                intent.setClass(this, IndexLineActivity.class);
                startActivity(intent);
                break;
            case R.id.HoverLinearLayout:
                intent.setClass(this, ScrollViewActivity.class);
                startActivity(intent);
                break;
        }
    }
}
