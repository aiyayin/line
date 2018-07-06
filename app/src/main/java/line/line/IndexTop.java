package line.line;

import com.example.yingfu.line.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author YING.FU
 * date: 2018-05-31
 */

public class IndexTop {
    private List<IndexTopItem> mTopItems = new ArrayList<>();

    public List<IndexTopItem> getTopItems() {
        if (mTopItems.size() == 0) {
            for (int i = 0; i < 5; i++) {
                IndexTopItem item = new IndexTopItem();
                item.setName("第" + i + "个");
                item.setImgDrawable(R.drawable.index_top_company);
                mTopItems.add(item);
            }
        }
        return mTopItems;
    }

    public class IndexTopItem {
        private int imgDrawable;
        private String name;

        public int getImgDrawable() {
            return imgDrawable;
        }

        public void setImgDrawable(int imgDrawable) {
            this.imgDrawable = imgDrawable;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
