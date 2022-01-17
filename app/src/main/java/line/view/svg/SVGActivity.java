package line.view.svg;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;

import android.widget.ImageView;

import com.example.yingfu.line.R;
import com.yin.line.base.BaseActivity;

public class SVGActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.svg_activity);
        //-----SVG-------
        AnimatedVectorDrawable drawable = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            drawable = (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.ic_add);
            ImageView imageView = findViewById(R.id.image);
            imageView.setImageDrawable(drawable);
            drawable.start();
        }
        //-----SVG-------
    }
}
