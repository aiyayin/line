package com.example.yingfu.line;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ScrollViewActivity extends AppCompatActivity {
    private TIndexTop mIndexTop;
    private LinearLayout mLayout;
    private ObservableScrollView horizontalScrollView;
    private TIndexLineView mLineView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_item_top1);
    }
}
