package line.html;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import com.example.yingfu.line.R;

import org.sufficientlysecure.htmltextview.HtmlTextView;

public class ArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
//222
        HtmlTextView text = findViewById(R.id.text);
        text.setHtml(R.raw.article,new HtmlGlideImageGetter(text,R.drawable.ic_book));
//        RichText.fromHtml(R.raw.article).into(text);


//过滤图片标签
//        Pattern compile = Pattern.compile("<\\s*(img|IMG)\\s+([^>]*)\\s*>");
//        String[] strings = compile.split(content);


    }



}