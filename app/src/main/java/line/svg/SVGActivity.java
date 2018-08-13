package line.svg;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.yingfu.line.R;

public class SVGActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.svg_activity);
        //-----SVG-------
        AnimatedVectorDrawable drawable = (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.ic_add);
        ImageView imageView = findViewById(R.id.image);
        imageView.setImageDrawable(drawable);
        drawable.start();
        //-----SVG-------
    }
}
