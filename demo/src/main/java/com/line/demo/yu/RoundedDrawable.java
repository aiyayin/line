package com.line.demo.yu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.douyu.lib.utils.DYDensityUtils;

public class RoundedDrawable extends Drawable {
    private final Paint mPaint;
    private final RectF mRectF;
    private final Path mPath;
    private Bitmap bitmap;
    private int mBitmapHeight;

    public RoundedDrawable(Context context, Bitmap bitmap) {
        int mBitmapWidth = bitmap.getWidth();
        this.bitmap = bitmap;
        mBitmapHeight = bitmap.getHeight();
        mRectF = new RectF(0, 0, mBitmapWidth, mBitmapHeight);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setFilterBitmap(true);
        mPaint.setDither(true);
        mPaint.setShader(new BitmapShader(bitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.REPEAT));
        mPath = new Path();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        if (bounds != null && mBitmapHeight != bounds.height()) {
            // 调整Bitmap的大小以匹配新的高度
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bounds.width(), bounds.height(), true);
            bitmap.recycle(); // 释放原始的Bitmap
            bitmap = scaledBitmap; // 更新Bitmap
            mBitmapHeight = bitmap.getHeight();
            mPaint.setShader(new BitmapShader(bitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.REPEAT));
        }

        mRectF.set(bounds);
        float cornerRadius = 40f;
        mPath.reset();
        mPath.addRoundRect(mRectF, cornerRadius, cornerRadius, Path.Direction.CW);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    // Helper method to create RoundedDrawable from a BitmapDrawable
    public static RoundedDrawable fromBitmapDrawable(Context context, BitmapDrawable bitmapDrawable) {
        return new RoundedDrawable(context, bitmapDrawable.getBitmap());
    }
}
