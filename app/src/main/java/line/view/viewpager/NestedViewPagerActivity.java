package line.view.viewpager;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.yingfu.line.R;

public class NestedViewPagerActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager_nested);

        ViewPager viewPager = findViewById(R.id.viewpager);

        PagerAdapter adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = LayoutInflater.from(NestedViewPagerActivity.this).inflate(R.layout.layout_viewpager_nested_child, container, false);
                TChildViewPager childViewPager = view.findViewById(R.id.viewpager);
                childViewPager.setPageMargin(30);
                childViewPager.setOffscreenPageLimit(3);
                childViewPager.setViewPager(viewPager);
                TextView nameTxt = view.findViewById(R.id.txt_position);
                nameTxt.setText("这是子viewpager第 " + position + "个");

                PagerAdapter adapterChild = new PagerAdapter() {
                    @Override
                    public int getCount() {
                        return 3;
                    }

                    @Override
                    public boolean isViewFromObject(View view, Object object) {
                        return view == object;
                    }

                    @Override
                    public Object instantiateItem(ViewGroup containerChild, int position) {
                        View viewChild = LayoutInflater.from(NestedViewPagerActivity.this).inflate(R.layout.index_item_top_child, containerChild, false);
                        TextView nameTxt = viewChild.findViewById(R.id.top_child_txt);
                        nameTxt.setText("这是第 " + position + "个");
                        containerChild.addView(viewChild);
                        return viewChild;
                    }

                    @Override
                    public void destroyItem(ViewGroup containerChila, int position, Object object) {
                        containerChila.removeView((View) object);
                    }
                };
                childViewPager.setAdapter(adapterChild);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        };
        viewPager.setAdapter(adapter);

    }


}
