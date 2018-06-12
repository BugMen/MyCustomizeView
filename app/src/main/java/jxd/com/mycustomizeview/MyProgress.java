package jxd.com.mycustomizeview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 46123 on 2018/6/12.
 */

public class MyProgress extends View {

    private Paint mPaint;
    private Paint textPaint;

    private int mWidth;
    private int mHeight;
    private int mDefaultColor;
    private int mProgressWidth;
    private float progress;
    private int progressMax;
    private int progressColor;
    private String content;

    public MyProgress(Context context) {
        super(context);
        init(null, 0);
    }

    public MyProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public MyProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(
                attrs, R.styleable.MyProgress, defStyle, 0);
        mDefaultColor = typedArray.getColor(R.styleable.MyProgress_defaultColor,
                ContextCompat.getColor(getContext(), R.color.colorGray));
        mProgressWidth = typedArray.getColor(R.styleable.MyProgress_progressWidth, 10);
        typedArray.recycle();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawArc(canvas);
        drawText(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        } else {
            mWidth = 100;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else {
            mHeight = 100;
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    private void drawArc(Canvas canvas) {
        canvas.save();
        mPaint.setColor(mDefaultColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mProgressWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        RectF rectF = new RectF(mProgressWidth, mProgressWidth, mWidth - mProgressWidth,
                mHeight - mProgressWidth);
        canvas.drawArc(rectF, 0, 360, false, mPaint);

        if (progress > 0) {
            mPaint.setColor(progressColor);
        } else {
            mPaint.setColor(mDefaultColor);
        }
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mProgressWidth);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        rectF = new RectF(mProgressWidth, mProgressWidth, mWidth - mProgressWidth,
                mHeight - mProgressWidth);
        canvas.drawArc(rectF, -90, 360 * (progress / progressMax), false, mPaint);

        canvas.restore();
    }

    private void drawText(Canvas canvas) {
        canvas.save();
        textPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
        textPaint.setTextSize(40);
        textPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(content, mWidth / 2, mHeight / 2 + mProgressWidth, textPaint);
        canvas.restore();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getProgressMax() {
        return progressMax;
    }

    public void setProgressMax(int progressMax) {
        this.progressMax = progressMax;
    }

    public int getmDefaultColor() {
        return mDefaultColor;
    }

    public void setmDefaultColor(int mProgressColor) {
        this.mDefaultColor = mProgressColor;
    }

    public int getmProgressWidth() {
        return mProgressWidth;
    }

    public void setmProgressWidth(int mProgressWidth) {
        this.mProgressWidth = mProgressWidth;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        postInvalidate();
    }

    public int getProgressColor() {
        return progressColor;
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
    }
}
