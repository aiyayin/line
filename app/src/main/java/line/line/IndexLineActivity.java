package line.line;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yingfu.line.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import line.util.ToolUtil;

public class IndexLineActivity extends AppCompatActivity implements BlankFragment.OnFragmentInteractionListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_item_top);
        initScrollView();
        initViewPager();

    }

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private IndexLineView mLineViewPager;

    private void initViewPager() {
        mViewPager = findViewById(R.id.top_viewpager);
        mTabLayout = findViewById(R.id.tab_layout);
        mLineViewPager = findViewById(R.id.top_line_two);
        for (int i = 0; i < 4; i++) {
            mTabLayout.addTab(mTabLayout.newTab());
            mFragments.add(new BlankFragment());
        }
        mViewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
        final int childTabWidth = ToolUtil.getScreenWidth(IndexLineActivity.this) / 4;
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("ying>>>", "onPageScrolled: position ：" + position + " positionOffset ： " + positionOffset);
                if (positionOffset != 0) {
                    if (positionOffset <= 0.5) {
                        int left = position * childTabWidth;
                        int width = (int) (childTabWidth + (positionOffset * 2) * childTabWidth);
                        mLineViewPager.move(left, width);
                    } else {
                        int left = (int) ((position + (positionOffset - 0.5) * 2) * childTabWidth);
                        int width = (position + 2) * childTabWidth - left;
                        mLineViewPager.move(left, width);
                    }
                } else {
                    mLineViewPager.move(position * childTabWidth, childTabWidth);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private List<android.support.v4.app.Fragment> mFragments = new ArrayList<>();

    @Override
    public void onFragmentInteraction(@NotNull Uri uri) {

    }

    class PagerAdapter extends FragmentPagerAdapter {
        private String[] mTitles = new String[]{"第1个", "第2个", "第3个", "第4个"};

        PagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mTitles.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }

    private IndexTop mIndexTop;
    private LinearLayout mLayout;
    private ObservableScrollView horizontalScrollView;
    private IndexLineView mLineView;

    private void initScrollView() {
        horizontalScrollView = findViewById(R.id.top_scrollview);
        mLayout = findViewById(R.id.top_ll);
        mLineView = findViewById(R.id.top_line);
        initScrollChildView();
        horizontalScrollView.setScrollViewListener(new ObservableScrollView.ScrollViewListener() {//滑动监听
            @Override
            public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
                int scrollX = scrollView.getScrollX();
                Log.e("ying>>", "onPageScrolled: x " + scrollX);
                mLineView.move(getLineMoveWithScroll(scrollX));
            }
        });

    }

    private void initScrollChildView() {
        mIndexTop = new IndexTop();
        List<IndexTop.IndexTopItem> mTopItems = new ArrayList<>();
        mTopItems.addAll(mIndexTop.getTopItems());

        if (mLayout.getChildCount() > 0) {
            mLayout.removeAllViews();
        }

        ViewGroup.LayoutParams layoutParams = mLineView.getLayoutParams();
        layoutParams.width = mLineView.getIndexWidth() * mTopItems.size();
        mLineView.setLayoutParams(layoutParams);

        for (int i = 0; i < mTopItems.size(); i++) {
            View view = LayoutInflater.from(IndexLineActivity.this).inflate(R.layout.index_item_top_child, mLayout, false);
            ImageView iconImg = view.findViewById(R.id.top_child_img);
            TextView nameTxt = view.findViewById(R.id.top_child_txt);
            IndexTop.IndexTopItem item = mTopItems.get(i);
            iconImg.setImageDrawable(IndexLineActivity.this.getResources().getDrawable(item.getImgDrawable()));
            nameTxt.setText(item.getName());
            mLayout.addView(view);
        }
    }

    private int getLineMoveWithScroll(int scrollX) {
        int w = getEnableScrollWidth();
        return scrollX * (mLineView.getWidth() - mLineView.getIndexWidth()) / w;
    }

    private int getEnableScrollWidth() {
        return mIndexTop.getTopItems().size() * ToolUtil.dpToPx(IndexLineActivity.this, 100) - ToolUtil.getScreenWidth(IndexLineActivity.this);
    }


}
