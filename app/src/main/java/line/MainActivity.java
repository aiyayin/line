package line;


import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import com.example.yingfu.line.R;
import androidx.appcompat.app.AppCompatActivity;
import line.bezier.BezierActivity;
import line.flutter.MyFlutterActivity;
import line.javafoundation.tree.TreeActivity;
import line.line.IndexLineActivity;
import line.scroller.ScrollViewActivity;
import line.svg.SVGActivity;
import line.viewpager.ViewPagerActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ComponentName mDefault;
    private ComponentName mDouble11;
    private ComponentName mDouble;
    private PackageManager mPm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        findViewById(R.id.line).setOnClickListener(this);
        findViewById(R.id.HoverLinearLayout).setOnClickListener(this);
        findViewById(R.id.Bezier).setOnClickListener(this);
        findViewById(R.id.tree).setOnClickListener(this);
        findViewById(R.id.SVG).setOnClickListener(this);
        findViewById(R.id.viewpager).setOnClickListener(this);
        findViewById(R.id.flutter).setOnClickListener(this);

        mDefault = getComponentName();
        mDouble11 = new ComponentName(getBaseContext(), "com.example.yingfu.line.redLine");
        mDouble = new ComponentName(getBaseContext(), "com.example.yingfu.line.Line");
        mPm = getApplicationContext().getPackageManager();
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
            case R.id.Bezier:
                intent.setClass(this, BezierActivity.class);
                startActivity(intent);
                break;
            case R.id.tree:
                intent.setClass(this, TreeActivity.class);
                startActivity(intent);
                break;
            case R.id.SVG:
                intent.setClass(this, SVGActivity.class);
                startActivity(intent);
                break;
            case R.id.viewpager:
                intent.setClass(this, ViewPagerActivity.class);
                startActivity(intent);
                break;
            case R.id.flutter:
                intent.setClass(this, MyFlutterActivity.class);
                startActivity(intent);
                break;
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

}
