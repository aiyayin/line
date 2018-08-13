package line.line;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.yingfu.line.R;
import java.util.ArrayList;
import java.util.List;

import line.util.ToolUtil;

public class IndexLineActivity extends AppCompatActivity {
    private IndexTop mIndexTop;
    private LinearLayout mLayout;
    private ObservableScrollView horizontalScrollView;
    private IndexLineView mLineView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_item_top);

        horizontalScrollView = findViewById(R.id.top_scrollview);
        mLayout = findViewById(R.id.top_ll);
        mLineView = findViewById(R.id.top_line);

        mIndexTop = new IndexTop();
        List<IndexTop.IndexTopItem> mTopItems = new ArrayList<>();
        mTopItems.addAll(mIndexTop.getTopItems());

        if (mLayout.getChildCount() > 0) {
            mLayout.removeAllViews();
        }


        for (int i = 0; i < mTopItems.size(); i++) {
            View view = LayoutInflater.from(IndexLineActivity.this).inflate(R.layout.index_item_top_child, mLayout, false);
            ImageView iconImg = view.findViewById(R.id.top_child_img);
            TextView nameTxt = view.findViewById(R.id.top_child_txt);
            IndexTop.IndexTopItem item = mTopItems.get(i);
            iconImg.setImageDrawable(IndexLineActivity.this.getResources().getDrawable(item.getImgDrawable()));
            nameTxt.setText(item.getName());
            mLayout.addView(view);
        }


        horizontalScrollView.setScrollViewListener(new ObservableScrollView.ScrollViewListener() {//滑动监听
            @Override
            public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
                int scrollX = scrollView.getScrollX();
                Log.e("ying>>", "onPageScrolled: x " + x);
                int w = (mIndexTop.getTopItems().size() - 4) * ToolUtil.dpToPx(IndexLineActivity.this, 98);
                mLineView.move(scrollX * (ToolUtil.dpToPx(IndexLineActivity.this, 8)) / w);
            }
        });

    }


}
