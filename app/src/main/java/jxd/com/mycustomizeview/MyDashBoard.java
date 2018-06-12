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
 * 自定义仪表盘
 */

public class MyDashBoard extends View {

    private Paint mProPaint;
    private Paint mDashPaint;
    private Paint mScaleTextPaint;
    private Paint mSpeedTextPaint;

    private int mDashProgress; // 进度
    private int mDashColor; // 默认颜色
    private int mDashProColor; // 进度颜色
    private int mDashWidth; // 指针宽度
    private int mDashLength; // 指针长度
    private float mDegrees; // 刻度旋转度数
    private int mDashStartAngle; // 仪表盘起始角度
    private int mRingWidth; // 进度条宽度
    private int mRadius; // 半径
    private int mRingStartAngle; // 进度条起始角度
    private int mSweepAngle; // 进度条扫过的角度
    private int mDashIcon; // 指针图标
    private int mDashTextSize; // 字体大小

    private float length;
    private float progress;

    public MyDashBoard(Context context) {
        super(context);
        init(null, 0);
    }

    public MyDashBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public MyDashBoard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(
                attrs, R.styleable.MyDashBoard, defStyle, 0);
        mDashProgress = typedArray.getInteger(R.styleable.MyDashBoard_dashProgress, 0);
        mDashColor = typedArray.getColor(R.styleable.MyDashBoard_dashColor,
                ContextCompat.getColor(getContext(), R.color.colorGray));
        mDashProColor = typedArray.getColor(R.styleable.MyDashBoard_dashProColor,
                ContextCompat.getColor(getContext(), R.color.colorPrimary));
        mDashWidth = typedArray.getInteger(R.styleable.MyDashBoard_dashWidth, 6);
        mDashLength = typedArray.getInteger(R.styleable.MyDashBoard_dashLength, 30);
        mDegrees = typedArray.getFloat(R.styleable.MyDashBoard_degrees, 2);
        mDashStartAngle = typedArray.getInteger(R.styleable.MyDashBoard_dashStartAngle, 220);
        mRingWidth = typedArray.getInteger(R.styleable.MyDashBoard_ringWidth, 8);
        mRadius = typedArray.getInteger(R.styleable.MyDashBoard_radius, 300);
        mRingStartAngle = typedArray.getInteger(R.styleable.MyDashBoard_ringStartAngle, 125);
        mSweepAngle = typedArray.getInteger(R.styleable.MyDashBoard_sweepAngle, 290);
        mDashIcon = typedArray.getResourceId(R.styleable.MyDashBoard_dashIcon, R.mipmap.ic_launcher);
        mDashTextSize = typedArray.getInteger(R.styleable.MyDashBoard_dashTextSize, 25);
        typedArray.recycle();

        mDashPaint = new Paint();
        mDashPaint.setAntiAlias(true);

        mProPaint = new Paint();
        mProPaint.setAntiAlias(true);

        mScaleTextPaint = new Paint();
        mScaleTextPaint.setAntiAlias(true);

        mSpeedTextPaint = new Paint();
        mSpeedTextPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        drawProgress(canvas, width, height);
        drawText(canvas, width, height);
    }

    /**
     * 绘制静态进度条
     *
     * @param canvas 画笔
     * @param width  宽度
     * @param height 高度
     */
    private void drawProgress(Canvas canvas, int width, int height) {
        canvas.save();
        mProPaint.setColor(mDashColor);
        mProPaint.setStyle(Paint.Style.STROKE);
        mProPaint.setStrokeWidth(mRingWidth);
        mProPaint.setStrokeCap(Paint.Cap.ROUND);
        RectF rectF = new RectF(mRingWidth, mRingWidth, width - mRingWidth, height);
        canvas.drawArc(rectF, mRingStartAngle, mSweepAngle, false, mProPaint);

        mDashPaint.setStrokeWidth(mDashWidth);
        mDashPaint.setColor(mDashColor);
        mDashPaint.setTextSize(mDashTextSize);
        canvas.translate(width / 2, height / 2);
        canvas.rotate(mDashStartAngle); //将起始刻度点旋转到正上方（270)
        for (int i = 0; i <= 140; i++) {
            if (i % 10 == 0) {   //画粗刻度和刻度值
                length = mDashLength + 20;
                canvas.drawLine(0, -mRadius, 0, -mRadius + length, mDashPaint);
                canvas.drawText(i * 2 + "", -mDashPaint.measureText(i + "") / 2, -mRadius + length + 25, mDashPaint);
            } else {         //画细刻度
                length = mDashLength;
                canvas.drawLine(0, -mRadius, 0, -mRadius + length, mDashPaint);
            }
            canvas.rotate(mDegrees); //逆时针
        }
        canvas.restore();

        canvas.save();
        if (mDashProgress > 0 && mDashProgress <= 280) {
            progress = mDashProgress;
            mProPaint.setColor(mDashProColor);
        } else if (mDashProgress > 280) {
            progress = 280;
            mProPaint.setColor(mDashProColor);
        } else {
            progress = 0;
            mProPaint.setColor(mDashColor);
        }
        mProPaint.setStyle(Paint.Style.STROKE);
        mProPaint.setStrokeWidth(mRingWidth);
        mProPaint.setStrokeCap(Paint.Cap.ROUND);
        rectF = new RectF(mRingWidth, mRingWidth, width - mRingWidth, height);
        canvas.drawArc(rectF, mRingStartAngle, (int) (mSweepAngle * (progress / 280)), false, mProPaint);

        if (mDashProgress > 0 && mDashProgress <= 280) {
            progress = mDashProgress;
            mDashPaint.setColor(mDashProColor);
        } else if (mDashProgress > 280) {
            progress = 280;
            mDashPaint.setColor(mDashProColor);
        } else {
            progress = 0;
            mDashPaint.setColor(mDashColor);
        }
        mDashPaint.setStrokeWidth(mDashWidth);
        canvas.translate(width / 2, height / 2);
        canvas.rotate(mDashStartAngle); //将起始刻度点旋转到正上方（270)
        for (int i = 0; i <= progress / 2; i++) {
            if (i % 10 == 0) {   //画粗刻度和刻度值
                length = mDashLength + 20;
                canvas.drawLine(0, -mRadius, 0, -mRadius + length, mDashPaint);
            } else {         //画细刻度
                length = mDashLength;
                canvas.drawLine(0, -mRadius, 0, -mRadius + length, mDashPaint);
            }
            canvas.rotate(mDegrees); //逆时针
        }
        canvas.restore();
    }

    /**
     * 绘制文字
     *
     * @param canvas 画笔
     */
    private void drawText(Canvas canvas, int width, int height) {
        canvas.save();
        mSpeedTextPaint.setColor(ContextCompat.getColor(getContext(), R.color.colorBlack));
        mSpeedTextPaint.setTextSize(100);
        mSpeedTextPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(mDashProgress + "", width / 2, height / 2, mSpeedTextPaint);
        mSpeedTextPaint.setTextSize(50);
        canvas.drawText("Km/h", width / 2, height / 2 + 50, mSpeedTextPaint);
        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int d = (width >= height) ? height : width;
        setMeasuredDimension(d, d);
    }

    public int getmRingStartAngle() {
        return mRingStartAngle;
    }

    public void setmRingStartAngle(int mRingStartAngle) {
        this.mRingStartAngle = mRingStartAngle;
    }

    public int getmRadius() {
        return mRadius;
    }

    public void setmRadius(int mRadius) {
        this.mRadius = mRadius;
    }

    public float getmDegrees() {
        return mDegrees;
    }

    public void setmDegrees(float mDegrees) {
        this.mDegrees = mDegrees;
    }

    public float getmDashLength() {
        return mDashLength;
    }

    public void setmDashLength(int mDashLength) {
        this.mDashLength = mDashLength;
    }

    public int getmDashTextSize() {
        return mDashTextSize;
    }

    public void setmDashTextSize(int mDashTextSize) {
        this.mDashTextSize = mDashTextSize;
    }

    public int getmDashIcon() {
        return mDashIcon;
    }

    public void setmDashIcon(int mDashIcon) {
        this.mDashIcon = mDashIcon;
    }

    public int getmDashStartAngle() {
        return mDashStartAngle;
    }

    public void setmDashStartAngle(int mDashStartAngle) {
        this.mDashStartAngle = mDashStartAngle;
    }

    public int getmSweepAngle() {
        return mSweepAngle;
    }

    public void setmSweepAngle(int mSweepAngle) {
        this.mSweepAngle = mSweepAngle;
    }

    public int getmDashColor() {
        return mDashColor;
    }

    public void setmDashColor(int mDashColor) {
        this.mDashColor = mDashColor;
    }

    public int getmDashProColor() {
        return mDashProColor;
    }

    public void setmDashProColor(int mDashProColor) {
        this.mDashProColor = mDashProColor;
    }

    public float getmDashWidth() {
        return mDashWidth;
    }

    public void setmDashWidth(int mDashWidth) {
        this.mDashWidth = mDashWidth;
    }

    public float getmRingWidth() {
        return mRingWidth;
    }

    public void setmRingWidth(int mDashRingWidth) {
        this.mRingWidth = mDashRingWidth;
    }

    public float getmDashProgress() {
        return mDashProgress;
    }

    public void setmDashProgress(int mDashProgress) {
        this.mDashProgress = mDashProgress;
        postInvalidate();
    }
}
