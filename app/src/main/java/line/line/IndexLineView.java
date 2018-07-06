package line.line;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.yingfu.line.R;

import line.util.ToolUtil;

/**
 * @author YING.FU
 *         date: 2018-05-31
 */

public class IndexLineView extends View {

    private Paint mPaint;
    private int left;
    private int indexWidth;
    private int indexColor;
    private int indexHeight;

    public IndexLineView(Context context) {
        this(context, null);
    }

    public IndexLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndexLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.IndexLineView, defStyleAttr, 0);
        indexWidth = typedArray.getDimensionPixelSize(R.styleable.IndexLineView_width, ToolUtil.dpToPx(context, 8));
        indexColor = typedArray.getColor(R.styleable.IndexLineView_index_color, context.getResources().getColor(R.color.green));
        typedArray.recycle();
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(indexColor);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        indexHeight = getMeasuredHeight();
        mPaint.setStrokeWidth(indexHeight);
        int w = indexHeight / 2;
        Log.e("ying>>>", "move: w  " + indexHeight);
        Log.e("ying>>>", "move: width  " + indexWidth);

        canvas.drawLine(left + w, w, left + indexWidth - w, w, mPaint);
    }

    public void move(int left) {
        this.left = left;
        invalidate();
    }

    public void move(int left, int width) {
        this.left = left;
        this.indexWidth = width;
        invalidate();
    }

}
