package com.line.demo.yu.view;

import static java.lang.System.out;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.exifinterface.media.ExifInterface;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import tv.douyu.lib.ui.imagecroppicker.imagecropper.callback.BitmapCropCallback;
import tv.douyu.lib.ui.imagecroppicker.imagecropper.model.CropParameters;
import tv.douyu.lib.ui.imagecroppicker.imagecropper.model.ExifInfo;
import tv.douyu.lib.ui.imagecroppicker.imagecropper.model.ImageState;
import tv.douyu.lib.ui.imagecroppicker.imagecropper.util.BitmapLoadUtils;
import tv.douyu.lib.ui.imagecroppicker.imagecropper.util.FileUtils;
import tv.douyu.lib.ui.imagecroppicker.imagecropper.util.ImageHeaderParser;

/**
 * Crops part of image that fills the crop bounds.
 * <p/>
 * First image is downscaled if max size was set and if resulting image is larger that max size.
 * Then image is rotated accordingly.
 * Finally new Bitmap object is created and saved to file.
 * 裁切填充裁切范围的图像部分。 * <p />
 * *如果设置了最大尺寸，并且生成的图像大于该最大尺寸，则缩小第一张图像的尺寸。 *然后相应旋转图像。 *最后，将创建新的Bitmap对象并将其保存到文件中。
 */
public class BitmapCropTask extends AsyncTask<Void, Void, Throwable> {

    private static final String TAG = "BitmapCropTask";

    private Bitmap mViewBitmap;

    private final RectF mCropRect;
    private final RectF mCurrentImageRect;

    private float mCurrentScale, mCurrentAngle;
    private final int mMaxResultImageSizeX, mMaxResultImageSizeY;

    private final Bitmap.CompressFormat mCompressFormat;
    private final int mCompressQuality;
    private final String mImageInputPath, mImageOutputPath;
    private final ExifInfo mExifInfo;
    private final BitmapCropCallback mCropCallback;

    private int mCroppedImageWidth, mCroppedImageHeight;
    private int cropOffsetX, cropOffsetY;


    public BitmapCropTask(@Nullable Bitmap viewBitmap, @NonNull ImageState imageState, @NonNull CropParameters cropParameters,
                          @Nullable BitmapCropCallback cropCallback) {

        mViewBitmap = viewBitmap;
        mCropRect = imageState.mCropRect;
        mCurrentImageRect = imageState.mCurrentImageRect;

        mCurrentScale = imageState.mCurrentScale;
        mCurrentAngle = imageState.mCurrentAngle;
        mMaxResultImageSizeX = cropParameters.mMaxResultImageSizeX;
        mMaxResultImageSizeY = cropParameters.mMaxResultImageSizeY;

        mCompressFormat = cropParameters.mCompressFormat;
        mCompressQuality = cropParameters.mCompressQuality;

        mImageInputPath = cropParameters.mImageInputPath;
        mImageOutputPath = cropParameters.mImageOutputPath;
        mExifInfo = cropParameters.mExifInfo;

        mCropCallback = cropCallback;
    }

    @Override
    @Nullable
    protected Throwable doInBackground(Void... params) {
        if (mViewBitmap == null) {
            return new NullPointerException("ViewBitmap is null");
        } else if (mViewBitmap.isRecycled()) {
            return new NullPointerException("ViewBitmap is recycled");
        } else if (mCurrentImageRect.isEmpty()) {
            return new NullPointerException("CurrentImageRect is empty");
        }

//        float resizeScale = resize();

        try {
//            crop(resizeScale);
            crop();
            mViewBitmap = null;
        } catch (Throwable throwable) {
            return throwable;
        }

        return null;
    }

    private float resize() {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mImageInputPath, options);

        boolean swapSides = mExifInfo.mExifDegrees == 90 || mExifInfo.mExifDegrees == 270;
        float scaleX = (swapSides ? options.outHeight : options.outWidth) / (float) mViewBitmap.getWidth();
        float scaleY = (swapSides ? options.outWidth : options.outHeight) / (float) mViewBitmap.getHeight();

        float resizeScale = Math.min(scaleX, scaleY);

        mCurrentScale /= resizeScale;

        resizeScale = 1;
        if (mMaxResultImageSizeX > 0 && mMaxResultImageSizeY > 0) {
            float cropWidth = mCropRect.width() / mCurrentScale;
            float cropHeight = mCropRect.height() / mCurrentScale;

            if (cropWidth > mMaxResultImageSizeX || cropHeight > mMaxResultImageSizeY) {

                scaleX = mMaxResultImageSizeX / cropWidth;
                scaleY = mMaxResultImageSizeY / cropHeight;
                resizeScale = Math.min(scaleX, scaleY);

                mCurrentScale /= resizeScale;
            }
        }
        return resizeScale;
    }

    private boolean crop() throws IOException {
        ExifInterface originalExif = new ExifInterface(mImageInputPath);

        cropOffsetX = Math.max(0, Math.round((mCropRect.left - mCurrentImageRect.left) / mCurrentScale));
        cropOffsetY = Math.max(0, Math.round((mCropRect.top - mCurrentImageRect.top) / mCurrentScale));

        mCroppedImageWidth = Math.round(Math.min(mCropRect.width(), mCurrentImageRect.width()) / mCurrentScale);
        mCroppedImageHeight = Math.round(Math.min(mCropRect.height(), mCurrentImageRect.height()) / mCurrentScale);

        boolean shouldCrop = shouldCrop(mCroppedImageWidth, mCroppedImageHeight);
        Log.i(TAG, "Should crop: " + shouldCrop);

        if (shouldCrop) {
            boolean cropped = cropCImg(mImageInputPath, mImageOutputPath,
                    cropOffsetX, cropOffsetY, mCroppedImageWidth, mCroppedImageHeight,
                    mCompressFormat, mCompressQuality);
            if (cropped && mCompressFormat.equals(Bitmap.CompressFormat.JPEG)) {
                ImageHeaderParser.copyExif(originalExif, mCroppedImageWidth, mCroppedImageHeight, mImageOutputPath);
            }
            return cropped;
        } else {
            FileUtils.copyFile(mImageInputPath, mImageOutputPath);
            return false;
        }
    }

    private boolean cropCImg(String mImageInputPath, String imageOutputPath, int cropOffsetX, int cropOffsetY,
                             int mCroppedImageWidth, int mCroppedImageHeight,
                             Bitmap.CompressFormat mCompressFormat, int mCompressQuality) {
        Bitmap saveBitmap;
        Bitmap targetBitmap;
        File f = new File(mImageInputPath);
        try {
            if (f.exists()) {
                targetBitmap = BitmapFactory.decodeFile(mImageInputPath);
                targetBitmap = checkBitmap(targetBitmap);
                saveBitmap = Bitmap.createBitmap(targetBitmap, cropOffsetX, cropOffsetY, mCroppedImageWidth,
                        mCroppedImageHeight);
                f = new File(imageOutputPath);
                //这里的输入和输出路径已经相等，在读取图片时将读入的图片拷贝到输出路径所在
                FileOutputStream out = new FileOutputStream(f);
                saveBitmap.compress(mCompressFormat, mCompressQuality, out);
                out.flush();
                out.close();
                Log.e(TAG, "已经保存");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeSafe(out);
        }
        return true;
    }

    //部分原生相机会把图片旋转，这样裁剪就不准了
    private Bitmap checkBitmap(Bitmap targetBitmap) {
        int degrees = mExifInfo.mExifDegrees;
        Matrix matrix = new Matrix();
        if (degrees != 0) {
            matrix.preRotate(degrees);
            targetBitmap = BitmapLoadUtils.transformBitmap(targetBitmap, matrix);
        }
        return targetBitmap;
    }

    private static void closeSafe(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException ignored) {
            }
        }
    }

    /**
     * Check whether an image should be cropped at all or just file can be copied to the destination path.
     * For each 1000 pixels there is one pixel of error due to matrix calculations etc.
     * 检查是否应完全裁剪图像或仅将文件复制到目标路径。 *由于矩阵计算等原因，每1000像素有一个像素错误。
     *
     * @param width  - crop area width
     * @param height - crop area height
     * @return - true if image must be cropped, false - if original image fits requirements
     */
    private boolean shouldCrop(int width, int height) {
        int pixelError = 1;
        pixelError += Math.round(Math.max(width, height) / 1000f);
        return (mMaxResultImageSizeX > 0 && mMaxResultImageSizeY > 0)
                || Math.abs(mCropRect.left - mCurrentImageRect.left) > pixelError
                || Math.abs(mCropRect.top - mCurrentImageRect.top) > pixelError
                || Math.abs(mCropRect.bottom - mCurrentImageRect.bottom) > pixelError
                || Math.abs(mCropRect.right - mCurrentImageRect.right) > pixelError
                || mCurrentAngle != 0;
    }

    //

    @Override
    protected void onPostExecute(@Nullable Throwable t) {
        if (mCropCallback != null) {
            if (t == null) {
                Uri uri = Uri.fromFile(new File(mImageOutputPath));
                mCropCallback.onBitmapCropped(uri, cropOffsetX, cropOffsetY, mCroppedImageWidth, mCroppedImageHeight);
            } else {
                mCropCallback.onCropFailure(t);
            }
        }
    }

}