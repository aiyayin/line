package line.html;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import androidx.annotation.RawRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import com.example.yingfu.line.R;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.io.InputStream;
import java.util.Scanner;

public class ArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
//222
        TextView text = findViewById(R.id.text);
        text.setText(HtmlCompat.fromHtml(convertStreamToString(R.raw.article3), HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS));
//        RichText.fromHtml(R.raw.article).into(text);


//过滤图片标签
//        Pattern compile = Pattern.compile("<\\s*(img|IMG)\\s+([^>]*)\\s*>");
//        String[] strings = compile.split(content);


    }

    private String convertStreamToString(@RawRes int resId) {
        InputStream inputStreamText = getResources().openRawResource(resId);
        Scanner s = new Scanner(inputStreamText).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }


}