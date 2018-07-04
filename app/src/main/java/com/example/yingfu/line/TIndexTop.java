package com.example.yingfu.line;

import java.util.ArrayList;
import java.util.List;

/**
 * @author YING.FU
 * date: 2018-05-31
 */

public class TIndexTop {
    private List<TIndexTopItem> mTopItems = new ArrayList<>();

    public List<TIndexTopItem> getTopItems() {
        if (mTopItems.size() == 0) {
            for (int i = 0; i < 5; i++) {
                TIndexTopItem item = new TIndexTopItem();
                item.setName("第" + i + "个");
                item.setImgDrawable(R.drawable.index_top_company);
                mTopItems.add(item);
            }
        }
        return mTopItems;
    }

    public class TIndexTopItem {
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
