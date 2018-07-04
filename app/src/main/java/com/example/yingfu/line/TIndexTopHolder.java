//package com.example.yingfu.line;
//
//
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.to8to.housekeeper.R;
//import com.to8to.steward.ui.index.newindex.entity.TIndexTop;
//import com.to8to.steward.ui.stream.viewtype.holder.TBaseStreamHolder;
//import com.to8to.steward.util.ToolUtil;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author ying.fu
// *         date: 2018-05-28
// */
//
//public class TIndexTopHolder extends TBaseStreamHolder {
//    private TIndexTop mIndexTop;
//    private ViewPager mViewPager;
//    private LinearLayout mLayout;
//    private TIndexLineView mLineView;
//
//    public TIndexTopHolder(View itemView) {
//        super(itemView);
//        initView();
//    }
//
//    private void initView() {
//        mViewPager = (ViewPager) itemView.findViewById(R.id.top_viewpager);
//        mLayout = (LinearLayout) itemView.findViewById(R.id.top_ll);
//        mLineView = (TIndexLineView) itemView.findViewById(R.id.top_line);
//    }
//
//    @Override
//    public void bindData(Object data, int position, boolean isLastPosition) {
//        mIndexTop = (TIndexTop) data;
//
//        if (mIndexTop.getTopItems() == null || mIndexTop.getTopItems().size() == 0) {
//            return;
//        }
//        String tag = (String) itemView.getTag();
//        if (tag != null && tag.equals(ToolUtil.MD5(mIndexTop.toString()))) {
//            return;
//        }
//
//        setTop();
//        itemView.setTag(ToolUtil.MD5(mIndexTop.toString()));
//    }
//
//    private void setTop() {
//        List<TIndexTop.TIndexTopItem> mTopItems = new ArrayList<>();
//        mTopItems.addAll(mIndexTop.getTopItems());
//        PagerAdapter adapter = new TTopPagerAdapter(mTopItems);
//        mViewPager.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
//        mViewPager.setPageMargin(0);
//        mViewPager.setOffscreenPageLimit(mIndexTop.getTopItems().size());
//        mLayout.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                // dispatch the events to the ViewPager, to solve the problem that we can swipe only the middle view.
//                return mViewPager.dispatchTouchEvent(event);
//            }
//        });
//
//        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                if (positionOffset != 0) {
//                    Log.e("ying>>", "onPageScrolled: " + position + "    " + positionOffset + "    " + positionOffsetPixels);
//                    mLineView.move((int) ((position + positionOffset) * (position - 4)/4 * ToolUtil.dpToPx(getContext(), 20)));
//                }
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//    }
//
//    public class TTopPagerAdapter extends PagerAdapter {
//        private List<TIndexTop.TIndexTopItem> mTopItems = new ArrayList<>();
//
//        public TTopPagerAdapter(List<TIndexTop.TIndexTopItem> mTopItems) {
//            this.mTopItems = mTopItems;
//        }
//
//        @Override
//        public int getCount() {
//            return mTopItems.size();
//        }
//
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return view == object;
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView((View) object);
//        }
//
//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            View view = LayoutInflater.from(getContext()).inflate(R.layout.index_item_top_child, null, false);
//            ImageView iconImg = (ImageView) view.findViewById(R.id.top_child_img);
//            TextView nameTxt = (TextView) view.findViewById(R.id.top_child_txt);
//            TIndexTop.TIndexTopItem item = mTopItems.get(position);
//            iconImg.setImageDrawable(getContext().getResources().getDrawable(item.getImgDrawable()));
//            nameTxt.setText(item.getName());
//            container.addView(view);
//            return view;
//        }
//    }
//}
