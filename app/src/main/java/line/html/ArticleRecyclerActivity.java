package line.html;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.yingfu.line.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ArticleRecyclerActivity extends AppCompatActivity {
    String content = "<p style=\"line-height:1.75em;margin-top:10px;\"><span style=\"font-family:微软雅黑\">选择了合适的窗帘，仅仅是成功了一半，如果在安装时多注意细节，那么窗帘的装饰作用会发挥到最大。今天请来玖雅设计师给我们谈谈窗帘安装应该注意的细节问题。</span></p><p style=\"line-height:1.75em;margin-top:10px;\"><span style=\"font-family:微软雅黑\"><br /></span></p><p style=\"line-height:1.75em;text-align:center;margin-top:10px;\"><span style=\"font-family:微软雅黑\"><img src=\"http://pic.to8to.com/attch/day_190628/20190628_e7a9eb06c49219652d95lmsNzrF9YATH.jpg\" width=\"750\" height=\"64\" style=\"float:none;\" title=\"1.jpg\" alt=\"1.jpg\" border=\"0\" hspace=\"0\" vspace=\"0\" /><br /></span></p><p style=\"line-height:1.75em;text-align:center;margin-top:10px;\"><strong><span style=\"font-family:微软雅黑;color:#ffffff;background-color:#000000\">安装前</span></strong></p><p style=\"line-height:1.75em;text-align:center;margin-top:10px;\"><strong><span style=\"font-family:微软雅黑;color:#ffffff;background-color:#000000\"><br /></span></strong></p><p style=\"line-height:1.75em;margin-top:10px;\"><span style=\"font-family:微软雅黑\">1、明轨还是暗轨的选择在做吊顶时就应该选择好。在进行吊顶和包窗套设计时，就会进行配套的窗帘盒设计，在这时就要做明轨暗轨的选择了。</span></p><p style=\"line-height:1.75em;margin-top:10px;\"><span style=\"font-family:微软雅黑\">明轨顾名思义就是明面上可以看到的窗帘装饰轨，包括木制杆，铝合金杆，钢管杆，铁艺杆，塑钢杆等；&nbsp;&nbsp;</span></p><p style=\"line-height:1.75em;margin-top:10px;\"><span style=\"font-family:微软雅黑\"><br /></span></p><p style=\"text-align:center;\"><img src=\"http://pic.to8to.com/attch/day_190628/20190628_c94f674818746f138ce14JxuM4dMUPnf.jpg\" width=\"800\" height=\"500\" style=\"float:none;\" title=\"1▲明轨轨道.jpg\" alt=\"1▲明轨轨道.jpg\" border=\"0\" hspace=\"0\" vspace=\"0\" /><br /></p><p style=\"line-height:1.75em;text-align:center;margin-top:10px;\"><span style=\"font-family:微软雅黑\">▲明轨轨道</span></p><p style=\"line-height:1.75em;margin-top:10px;\"><span style=\"font-family:微软雅黑\"><br /></span></p><p style=\"text-align:center;\"><img src=\"http://pic.to8to.com/attch/day_190628/20190628_3eb19792d36cb62f7cafbrUhtWSwDmYg.jpg\" width=\"790\" height=\"619\" style=\"float:none;\" title=\"2▲此为明轨窗帘.jpg\" alt=\"2▲此为明轨窗帘.jpg\" border=\"0\" hspace=\"0\" vspace=\"0\" /><br /></p><p style=\"line-height:1.75em;text-align:center;margin-top:10px;\"><span style=\"font-family:微软雅黑\">▲此为明轨窗帘</span></p><p style=\"line-height:1.75em;margin-top:10px;\"><span style=\"font-family:微软雅黑\"><br /></span></p><p style=\"line-height:1.75em;margin-top:10px;\"><span style=\"font-family:微软雅黑\">暗轨就是隐藏在吊顶窗帘盒里的轨道，有纳米轨道，铝合金轨道，静音轨道等。明轨重装饰，暗轨重使用效果。</span></p><p style=\"line-height:1.75em;margin-top:10px;\"><span style=\"font-family:微软雅黑\"><br /></span></p><p style=\"text-align:center;\"><img src=\"http://pic.to8to.com/attch/day_190628/20190628_bfcf4c6dc20c0c6551e2Hhr8w7whBKJT.jpg\" width=\"800\" height=\"500\" style=\"float:none;\" title=\"3▲暗轨轨道.jpg\" alt=\"3▲暗轨轨道.jpg\" border=\"0\" hspace=\"0\" vspace=\"0\" /><br /></p><p style=\"line-height:1.75em;text-align:center;margin-top:10px;\"><span style=\"font-family:微软雅黑\">▲暗轨轨道</span></p><p style=\"line-height:1.75em;margin-top:10px;\"><span style=\"font-family:微软雅黑\"><br /></span></p><p style=\"text-align:center;\"><img src=\"http://pic.to8to.com/attch/day_190628/20190628_45560cecc569b1776858EKbwPuutWDMO.jpg\" width=\"750\" height=\"848\" style=\"float:none;\" title=\"4▲此为暗轨窗帘.jpg\" alt=\"4▲此为暗轨窗帘.jpg\" border=\"0\" hspace=\"0\" vspace=\"0\" /><br /></p><p style=\"line-height:1.75em;text-align:center;margin-top:10px;\"><span style=\"font-family:微软雅黑\">▲此为暗轨窗帘</span></p><p style=\"line-height:1.75em;margin-top:10px;\"><span style=\"font-family:微软雅黑\"><br /></span></p><p style=\"line-height:1.75em;margin-top:10px;\"><span style=\"font-family:微软雅黑\">2、测量尺寸应该在地面装饰完成后。如果在之前测量，会因为地面没铺设好，与实际成品的尺寸有一点出入，造成挂窗帘时多几公分或少几公分。地砖或地板铺设完成后测量的尺寸比较合适，不影响窗帘的安装效果。只要做好防护措施，就不怕蹭花地面。</span></p><p style=\"line-height:1.75em;margin-top:10px;\"><span style=\"font-family:微软雅黑\">3、考虑到固定件（托架或安装码）的牢固性，避免固定件的间距过大承受不住窗帘拉力，需要先测量所需安装轨道的尺寸，然后计算固定孔距，一般固定件的间隔距离不大于50公分，然后画线定位，定位的准确性关系到窗帘安装的成败。</span></p><p style=\"line-height:1.75em;margin-top:10px;\"><span style=\"font-family:微软雅黑\"><br /></span></p><p style=\"line-height:1.75em;text-align:center;margin-top:10px;\"><span style=\"font-family:微软雅黑\"><img src=\"http://pic.to8to.com/attch/day_190628/20190628_7f45db8840efe689f569WEbOtnwZTKWG.jpg\" width=\"742\" height=\"485\" style=\"float:none;\" title=\"第二阶段 安装过程.jpg\" alt=\"第二阶段 安装过程.jpg\" border=\"0\" hspace=\"0\" vspace=\"0\" /><br /></span></p><p style=\"line-height:1.75em;text-align:center;margin-top:10px;\"><span style=\"font-family:微软雅黑\"><br /></span></p><p style=\"line-height:1.75em;text-align:center;margin-top:10px;\"><span style=\"font-family:微软雅黑\"><img src=\"http://pic.to8to.com/attch/day_190628/20190628_e9d79046c2f7d13caa09AiePogfdUAWa.jpg\" width=\"750\" height=\"64\" style=\"float:none;\" title=\"2.jpg\" alt=\"2.jpg\" border=\"0\" hspace=\"0\" vspace=\"0\" /><br /></span></p><p style=\"line-height:1.75em;text-align:center;margin-top:10px;\"><strong><span style=\"font-family:微软雅黑;background-color:#000000;color:#ffffff\">安装过程</span></strong></p><p style=\"line-height:1.75em;margin-top:10px;\"><span style=\"font-family:微软雅黑\"><br /></span></p><p style=\"line-height:1.75em;margin-top:10px;\"><span style=\"font-family:微软雅黑\">1、最好选择在新房做开荒保洁之前安装，装得太早轨道上会落上尘灰，装得太晚保洁又白做了。</span></p><p style=\"line-height:1.75em;margin-top:10px;\"><span style=\"font-family:微软雅黑\">2、如果安装墙体是保温墙，建议使用加长膨胀螺丝来固定，长度至少10cm以上，甚至可以到16cm，如果工人没有，建议业主自己去买。</span></p><p style=\"line-height:1.75em;margin-top:10px;\"><span style=\"font-family:微软雅黑\">3、安装罗马杆</span></p><p style=\"line-height:1.75em;margin-top:10px;\"><span style=\"font-family:微软雅黑\">罗马杆一般和窗帘一起安装。&nbsp;</span></p><p style=\"line-height:1.75em;margin-top:10px;\"><span style=\"font-family:微软雅黑\">罗马杆分为有挂环的和没挂环的。没挂环的罗马杆主要用来挂穿杆穿孔窗帘。有挂环的罗马杆搭配挂钩窗帘，在安装之前，需要先组装好罗马杆。</span></p><p style=\"line-height:1.75em;margin-top:10px;\"><span style=\"font-family:微软雅黑\"><br /></span></p><p style=\"line-height:1.75em;text-align:center;margin-top:10px;\"><span style=\"font-family:微软雅黑\"><img src=\"http://pic.to8to.com/attch/day_190628/20190628_2357a93d421b37a500a02IMxorsV8v2g.jpg\" width=\"600\" height=\"250\" style=\"float:none;\" title=\"6.jpg\" alt=\"6.jpg\" border=\"0\" hspace=\"0\" vspace=\"0\" /><br /></span></p><p style=\"line-height:1.75em;text-align:center;margin-top:10px;\"><span style=\"font-family:微软雅黑\"><br /></span></p><p style=\"line-height:1.75em;margin-top:10px;\"><span style=\"font-family:微软雅黑\">安装的时候注意挑选与罗马杆相匹配的支架，如果支架太小，一方面容易损坏，另一方面，太小的支架与墙的接触面比较小，安装螺母的个数或者大小就会受到限制，容易使安装的窗帘杆不稳当。</span></p><p style=\"line-height:1.75em;margin-top:10px;\"><span style=\"font-family:微软雅黑\">要注意房顶与窗户高度，避免有压抑、窒息感。&nbsp;在墙上安装，即墙安，罗马杆距离顶部应在6cm到12cm左右。而安装在顶部时，罗马杆距离墙壁应在6cm到10cm左右，防止窗帘开合时，与墙面摩擦造成污损。&nbsp;</span></p><p style=\"line-height:1.75em;margin-top:10px;\"><span style=\"font-family:微软雅黑\">罗马杆的长度（不包括装饰头）应大于窗套宽度，左右预留应不低于10cm，避免漏光。</span></p><p style=\"line-height:1.75em;margin-top:10px;\"><span style=\"font-family:微软雅黑\"><br /></span></p><p style=\"line-height:1.75em;text-align:center;margin-top:10px;\"><span style=\"font-family:微软雅黑\"><img src=\"http://pic.to8to.com/attch/day_190628/20190628_7497e644aab5d66a80c6yeQQDAmEWNhV.jpg\" width=\"600\" height=\"333\" style=\"float:none;\" title=\"7.jpg\" alt=\"7.jpg\" border=\"0\" hspace=\"0\" vspace=\"0\" /><br /></span></p><p style=\"line-height:1.75em;text-align:center;margin-top:10px;\"><span style=\"font-family:微软雅黑\"><br /></span></p><p style=\"line-height:1.75em;margin-top:10px;\"><span style=\"font-family:微软雅黑\">满墙安装时，罗马杆的长度(不包括两端的装饰头)，要比室内净宽少约18~22cm，安装完毕后，罗马杆的装饰头与墙壁之间要留2~6cm的间隙。</span></p><p style=\"line-height:1.75em;margin-top:10px;\"><span style=\"font-family:微软雅黑\">罗马杆安装一定要水平，必要时用靠尺监测安装得平不平。</span></p><p style=\"line-height:1.75em;margin-top:10px;\"><span style=\"font-family:微软雅黑\">4、安装窗帘轨</span></p><p style=\"line-height:1.75em;margin-top:10px;\"><span style=\"font-family:微软雅黑\">窗帘轨道可以提前装，最好装修的时候就考虑好安装什么样的轨道。&nbsp;</span></p><p style=\"line-height:1.75em;margin-top:10px;\"><span style=\"font-family:微软雅黑\">轨道的长度取决于窗户加上窗套，再加上10厘米的缓冲位置。石膏线下沿与窗套上沿之间至少预留10cm的距离。</span></p><p style=\"line-height:1.75em;margin-top:10px;\"><span style=\"font-family:微软雅黑\"><br /></span></p><p style=\"line-height:1.75em;text-align:center;margin-top:10px;\"><span style=\"font-family:微软雅黑\"><img src=\"http://pic.to8to.com/attch/day_190628/20190628_fbc662d7ebc2cf8c0ba5plDs088VYJc8.jpg\" width=\"880\" height=\"714\" style=\"float:none;\" title=\"8.jpg\" alt=\"8.jpg\" border=\"0\" hspace=\"0\" vspace=\"0\" /><br /></span></p><p style=\"line-height:1.75em;text-align:center;margin-top:10px;\"><span style=\"font-family:微软雅黑\"><br /></span></p><p style=\"line-height:1.75em;margin-top:10px;\"><span style=\"font-family:微软雅黑\">如果窗户太宽，超过了1200mm，整根轨道就无法承受窗帘的重量，需要安装师傅将轨道从中间断开，再用连接件搭接起来，搭接长度应不小于200mm。&nbsp;</span></p><p style=\"line-height:1.75em;margin-top:10px;\"><span style=\"font-family:微软雅黑\">5、如果是采用窗帘挂钩固定窗帘的方式，则可以利用挂钩的不同安装方式，形成不同的窗帘褶。不过需要注意的是窗帘挂钩之间的距离尽量均匀，先计算好;发现有生锈的挂钩马上换掉，否则污染窗帘布。&nbsp;</span></p><p style=\"line-height:1.75em;margin-top:10px;\"><span style=\"font-family:微软雅黑\"><br /></span></p><p style=\"text-align:center;\"><img src=\"http://pic.to8to.com/attch/day_190628/20190628_ca218d7554fefd1d46b9ctP8K8yc5e4K.jpg\" width=\"750\" height=\"64\" style=\"float:none;\" title=\"3.jpg\" alt=\"3.jpg\" border=\"0\" hspace=\"0\" vspace=\"0\" /><br /></p><p style=\"line-height:1.75em;text-align:center;margin-top:10px;\"><strong><span style=\"font-family:微软雅黑;background-color:#000000;color:#ffffff\">安装完成</span></strong></p><p style=\"line-height:1.75em;margin-top:10px;\"><strong><span style=\"font-family:微软雅黑;background-color:#000000;color:#ffffff\"><br /></span></strong></p><p style=\"line-height:1.75em;margin-top:10px;\"><span style=\"font-family:微软雅黑\">窗帘安装完成后,一定要拉开、收拢几下，体验一下滑动是否灵活、顺畅，如果有问题，现场就要让工人找到原因及时修复。</span></p><p style=\"line-height:1.75em;margin-top:10px;\"><span style=\"font-family:微软雅黑\">视觉效果100分的窗帘，三分靠选材，七分靠细节，这些细节问题一定要认真检查，不然会换来后期不断的生活麻烦。</span></p><p><br /></p>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_recycler);
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        ArticleAdapter adapter = new ArticleAdapter(null);
        recyclerView.setAdapter(adapter);

        ArrayList<MultiItemEntity> list = new ArrayList<>();
        Pattern compile = Pattern.compile("<\\s*(img|IMG)\\s+([^>]*)\\s*>");
        String[] strings = compile.split(content);
        for (int i = 0; i < strings.length; i++) {
            String string = strings[i];
            int indexOf = string.lastIndexOf("<p>");
            if (indexOf + 3 == string.length()) {
                string = string.substring(0, indexOf);
            }
            int indexOf2 = string.indexOf("</p>");
            if (indexOf2 == 0) {
                string = string.substring(4);
            }

            list.add(new ArticleBean(string));
            if (i != (strings.length - 1)) {
                list.add(new ArticleImageBean("https://pic.to8to.com/case/20201109/e85cef5fe9af21ed3f0e0db3e8350152.jpg"));
            }

        }

        adapter.setNewInstance(list);
    }

    public class ArticleAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

        public ArticleAdapter(@Nullable List<MultiItemEntity> data) {
            super(data);
            addItemType(0, R.layout.layout_article);
            addItemType(1, R.layout.layout_article_img);
        }


        @Override
        protected void convert(@NotNull BaseViewHolder baseViewHolder, MultiItemEntity multiItemEntity) {
            if (multiItemEntity.getItemType() == 0) {
                HtmlTextView textView = baseViewHolder.getView(R.id.text);
                textView.setHtml(((ArticleBean) multiItemEntity).content);
            } else {
                ImageView imageView = baseViewHolder.getView(R.id.image);
                Glide.with(baseViewHolder.itemView.getContext()).load(((ArticleImageBean) multiItemEntity).url).error(R.drawable.ic_book).into(imageView);
            }
        }
    }

    public class ArticleBean implements MultiItemEntity {
        public String content;

        public ArticleBean(String content) {
            this.content = content;
        }

        @Override
        public int getItemType() {
            return 0;
        }
    }

    public class ArticleImageBean implements MultiItemEntity {
        public String url;

        public ArticleImageBean(String url) {
            this.url = url;
        }

        @Override
        public int getItemType() {
            return 1;
        }
    }
}