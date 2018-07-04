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

public class IndexLineActivity extends AppCompatActivity {
    private TIndexTop mIndexTop;
    private LinearLayout mLayout;
    private ObservableScrollView horizontalScrollView;
    private TIndexLineView mLineView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_item_top);

        horizontalScrollView = findViewById(R.id.top_viewpager);
        mLayout = (LinearLayout) findViewById(R.id.top_ll);
        mLineView = (TIndexLineView) findViewById(R.id.top_line);

        mIndexTop = new TIndexTop();
        List<TIndexTop.TIndexTopItem> mTopItems = new ArrayList<>();
        mTopItems.addAll(mIndexTop.getTopItems());

        if (mLayout.getChildCount() > 0) {
            mLayout.removeAllViews();
        }


        for (int i = 0; i < mTopItems.size(); i++) {
            View view = LayoutInflater.from(IndexLineActivity.this).inflate(R.layout.index_item_top_child, mLayout, false);
            ImageView iconImg = (ImageView) view.findViewById(R.id.top_child_img);
            TextView nameTxt = (TextView) view.findViewById(R.id.top_child_txt);
            TIndexTop.TIndexTopItem item = mTopItems.get(i);
            iconImg.setImageDrawable(IndexLineActivity.this.getResources().getDrawable(item.getImgDrawable()));
            nameTxt.setText(item.getName());
            mLayout.addView(view);
        }


        horizontalScrollView.setScrollViewListener(new ObservableScrollView.ScrollViewListener() {//滑动监听,获取图片
            @Override
            public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
                int scrollX = scrollView.getScrollX();
                Log.e("ying>>", "onPageScrolled: x " + x);
                int w = (mIndexTop.getTopItems().size() - 4)*dpToPx(IndexLineActivity.this, 98);
                mLineView.move(scrollX * (dpToPx(IndexLineActivity.this, 8))/w);
            }
        });

    }


    public int dpToPx(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
