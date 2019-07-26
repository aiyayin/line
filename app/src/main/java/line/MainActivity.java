package line;


import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yingfu.line.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import line.bezier.BezierActivity;
import line.entity.ActivityItem;
import line.flutter.MyFlutterActivity;
import line.javafoundation.tree.TreeActivity;
import line.line.IndexLineActivity;
import line.opengl.panorama.GoogleVRActivity;
import line.opengl.panorama.OpenGLActivity;
import line.opengl.panorama.video360.GoogleVideoActivity;
import line.scroller.ScrollViewActivity;
import line.svg.SVGActivity;
import line.viewpager.ViewPagerActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final int READ_EXTERNAL_STORAGE_PERMISSION_ID = 1;
    private ComponentName mDefault;
    private ComponentName mDouble11;
    private ComponentName mDouble;
    private PackageManager mPm;
    private List<ActivityItem> activityItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_EXTERNAL_STORAGE_PERMISSION_ID);
        }
        activityItems = new ArrayList<>();
        activityItems.add(new ActivityItem("Line", R.drawable.ic_line, IndexLineActivity.class));
        activityItems.add(new ActivityItem("ScrollView", R.drawable.ic_list, ScrollViewActivity.class));
        activityItems.add(new ActivityItem("Bezier", R.drawable.ic_wave, BezierActivity.class));
        activityItems.add(new ActivityItem("Tree", R.drawable.ic_tree, TreeActivity.class));
        activityItems.add(new ActivityItem("SVG", R.drawable.ic_line, SVGActivity.class));
        activityItems.add(new ActivityItem("ViewPager",
                R.drawable.ic_list, ViewPagerActivity.class));
        activityItems.add(new ActivityItem("Flutter",
                R.drawable.ic_flutter, MyFlutterActivity.class));
        activityItems.add(new ActivityItem("OpenGL", R.drawable.ic_vr, OpenGLActivity.class));
        activityItems.add(new ActivityItem("GoogleVR", R.drawable.ic_vr, GoogleVRActivity.class));
        activityItems.add(new ActivityItem("GoogleVideo", R.drawable.ic_vr, GoogleVideoActivity.class));

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(new RecyclerView.Adapter<ActivityViewHolder>() {
            @NonNull
            @Override
            public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new ActivityViewHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.main_activity_item, parent, false));
            }

            @Override
            public void onBindViewHolder(@NonNull ActivityViewHolder holder, int position) {
                ActivityItem item = activityItems.get(position);
                holder.tvTitle.setText(item.name);
                holder.ivIcon.setImageResource(item.icon);
                holder.itemView.setTag("recyclerView" + position);
                holder.itemView.setOnClickListener(MainActivity.this);
            }

            @Override
            public int getItemCount() {
                return activityItems == null ? 0 : activityItems.size();
            }
        });


        mDefault = getComponentName();
        mDouble11 = new ComponentName(getBaseContext(), "com.example.yingfu.line.redLine");
        mDouble = new ComponentName(getBaseContext(), "com.example.yingfu.line.Line");
        mPm = getApplicationContext().getPackageManager();
    }

    @Override
    public void onClick(View v) {
        if (v.getTag() instanceof String) {
            String tag = (String) v.getTag();
            int length = "recyclerView".length();
            if (tag.contains("recyclerView") && tag.length() > length) {
                try {
                    int position = Integer.parseInt(tag.substring(length, tag.length()));
                    ActivityItem item = activityItems.get(position);
                    Intent intent = new Intent();
                    intent.setClass(this, item.targetActivity);
                    startActivity(intent);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void changeIconDefault() {
        disableComponent(mDouble11);
        enableComponent(mDouble);
    }

    public void changeIcon() {
        disableComponent(mDefault);
        enableComponent(mDouble11);
    }


    private void disableComponent(ComponentName componentName) {
        mPm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

    private void enableComponent(ComponentName componentName) {
        mPm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] results) {
        if (requestCode == READ_EXTERNAL_STORAGE_PERMISSION_ID) {
            if (results.length > 0 && results[0] == PackageManager.PERMISSION_GRANTED) {
            }
        }
    }

    public class ActivityViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public ImageView ivIcon;

        public ActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.title);
            ivIcon = itemView.findViewById(R.id.icon);
        }
    }


}
