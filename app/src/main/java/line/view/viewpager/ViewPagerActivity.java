package line.view.viewpager;


import android.os.Bundle;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import line.BaseActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yingfu.line.R;

public class ViewPagerActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager_activity);

        ViewPager viewPager = findViewById(R.id.viewpager);
        ViewPager viewPager2 = findViewById(R.id.viewpager_2);
        viewPager.setPageMargin(30);
        viewPager2.setPageMargin(30);
        viewPager.setOffscreenPageLimit(3);

        PagerAdapter adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return 6;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = LayoutInflater.from(ViewPagerActivity.this).inflate(R.layout.index_item_top_child, container, false);
                TextView nameTxt = view.findViewById(R.id.top_child_txt);
                nameTxt.setText("这是第 " + position + "个");
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        };
        viewPager.setAdapter(adapter);
        viewPager2.setAdapter(adapter);
    }


}
