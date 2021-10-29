/*
 * Copyright (C) 2014-2016 Dominik Schürmann <dominik@schuermann.eu>
 * Copyright (C) 2013 Antarix Tandon
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package line.html;

import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html.ImageGetter;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

public class HtmlGlideImageGetter implements ImageGetter {
    private TextView container;

    private boolean matchParentWidth;
    private int placeHolder;
    private float ratioWH;

    private boolean compressImage = false;
    private int qualityImage = 50;

    public HtmlGlideImageGetter(TextView textView) {
        this.container = textView;
        this.matchParentWidth = false;
    }


    public HtmlGlideImageGetter(TextView textView, int placeHolder) {
        this.container = textView;
        this.placeHolder = placeHolder;

    }

    public void enableCompressImage(boolean enable) {
        enableCompressImage(enable, 50);
    }

    public void enableCompressImage(boolean enable, int quality) {
        compressImage = enable;
        qualityImage = quality;
    }

    public Drawable getDrawable(String source) {
        UrlDrawable urlDrawable = new UrlDrawable();
        if (placeHolder != 0) {
            Drawable placeDrawable = container.getContext().getResources().getDrawable(placeHolder);
//            ratioWH = container.getWidth() * 1.0f / placeDrawable.getIntrinsicHeight();
            placeDrawable.setBounds(0, 0, 1080, 300);
            urlDrawable.setBounds(0, 0, 1080, 300);
            urlDrawable.drawable = placeDrawable;
        }
        // get the actual source

        Glide.with(container.getContext()).load(source).apply(new RequestOptions().transform(new CenterCrop(), new RoundedCorners(10))).addListener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                resource.setBounds(0, 0, 1080, 300);
                urlDrawable.drawable = resource;
//                ratioWH = container.getWidth() * 1.0f / resource.getIntrinsicWidth();


                // redraw the image by invalidating the container
                container.invalidate();
                // re-set text to fix images overlapping text
                container.setText(container.getText());
                return false;
            }
        }).submit();


        // return reference to URLDrawable which will asynchronously load the image specified in the src tag
        return urlDrawable;
    }


    @SuppressWarnings("deprecation")
    public class UrlDrawable extends BitmapDrawable {
        protected Drawable drawable;

        @Override
        public void draw(Canvas canvas) {
            // override the draw to facilitate refresh function later
            if (drawable != null) {
                drawable.draw(canvas);
            }
        }
    }
} 
