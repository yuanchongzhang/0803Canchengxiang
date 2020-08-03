package com.mingmen.canting.defineview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.OvershootInterpolator;

public class ToggleButton extends View {
    /**
     * 边框宽
     */
    private final int BORDER_WIDTH = 2;
    /**
     * 底盘色
     */
    private int mBasePlaneColor = Color.parseColor("#0a95f7");
    private int mOpenSlotColor = Color.parseColor("#0a95f7");
    /**
     * 关时滑槽色
     */
    private int mOffSlotColor = Color.parseColor("#ffffff");
    private int mSlotColor;
    private RectF mRect = new RectF();
    /**
     * 绘参（关时滑槽色）
     */
    private float mBackPlaneRadius;
    /**
     * 手柄半径
     */
    private float mSpotRadius;
    /**
     * 手柄起始X位（切时平移改变）
     */
    private float spotStartX;
    /**
     * 手柄起始X位（不变）
     */
    private float mSpotY;
    /**
     * 关时手柄水平位
     */
    private float mOffSpotX;
    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 开关标记
     */
    private boolean mIsToggleOn;
    /**
     * 事件监听
     */
    private OnToggleListener onToggleListener;

    public interface OnToggleListener {
        /**
         * 切换监听
         *
         * @param switchState 切换状
         */
        void onSwitchChangeListener(boolean switchState);
    }

    public ToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsToggleOn) {
                    toggleOff();
                } else {
                    toggleOn();
                }
                mIsToggleOn = !mIsToggleOn;
                if (onToggleListener != null) {
                    onToggleListener.onSwitchChangeListener(mIsToggleOn);
                }
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        int resultWidth = wSize;
        int resultHeight = hSize;
        Resources resources = Resources.getSystem();
        // lp等wrapContent时指定默值
        if (wMode == MeasureSpec.AT_MOST) {
            resultWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, resources.getDisplayMetrics());
        }
        if (hMode == MeasureSpec.AT_MOST) {
            resultHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, resources.getDisplayMetrics());
        }
        setMeasuredDimension(resultWidth, resultHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        mBackPlaneRadius = Math.min(getWidth(), getHeight()) * 0.5f;
        mSpotRadius = mBackPlaneRadius - BORDER_WIDTH;
        spotStartX = 0;
        mSpotY = 0;
        mOffSpotX = getMeasuredWidth() - mBackPlaneRadius * 2;
        mSlotColor = mOffSlotColor;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 画底板
        mRect.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
        mPaint.setColor(mBasePlaneColor);
        canvas.drawRoundRect(mRect, mBackPlaneRadius, mBackPlaneRadius, mPaint);
        // 边框高
        int borderHeight = 2;
        // 画手柄槽
        mRect.set(BORDER_WIDTH, borderHeight, getMeasuredWidth() - BORDER_WIDTH, getMeasuredHeight() - borderHeight);
        mPaint.setColor(mSlotColor);
        canvas.drawRoundRect(mRect, mSpotRadius, mSpotRadius, mPaint);
        // 手柄含两部分（深色底板和白板），为使圆盘有边框
        // 手柄底盘
        mRect.set(spotStartX, mSpotY, spotStartX + mBackPlaneRadius * 2, mSpotY + mBackPlaneRadius * 2);
        mPaint.setColor(mBasePlaneColor);
        canvas.drawRoundRect(mRect, mBackPlaneRadius, mBackPlaneRadius, mPaint);
        // 手柄圆板
        mRect.set(spotStartX + BORDER_WIDTH, mSpotY + BORDER_WIDTH, mSpotRadius * 2 + spotStartX + BORDER_WIDTH, mSpotRadius * 2 + mSpotY + BORDER_WIDTH);
        mPaint.setColor(Color.WHITE);
        canvas.drawRoundRect(mRect, mSpotRadius, mSpotRadius, mPaint);
    }

    public float getSpotStartX() {
        return spotStartX;
    }

    public void setSpotStartX(float spotStartX) {
        this.spotStartX = spotStartX;
    }

    /**
     * 算切时手柄槽色
     *
     * @param fraction   动画进度
     * @param startColor 起始色
     * @param endColor   终止色
     */
    public void calculateColor(float fraction, int startColor, int endColor) {
        final int fb = Color.blue(startColor);
        final int fr = Color.red(startColor);
        final int fg = Color.green(startColor);
        final int tb = Color.blue(endColor);
        final int tr = Color.red(endColor);
        final int tg = Color.green(endColor);
        // RGB三通道线性渐变
        int sr = (int) (fr + fraction * (tr - fr));
        int sg = (int) (fg + fraction * (tg - fg));
        int sb = (int) (fb + fraction * (tb - fb));
        // 范围限定
        sb = clamp(sb, 0, 255);
        sr = clamp(sr, 0, 255);
        sg = clamp(sg, 0, 255);
        mSlotColor = Color.rgb(sr, sg, sb);
    }

    private int clamp(int value, int low, int high) {
        return Math.min(Math.max(value, low), high);
    }

    /**
     * 开
     */
    public void toggleOff() {
        // 属性动画实现手柄槽色渐变和手柄滑动
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "spotStartX", mOffSpotX, 0);
        animator.setDuration(300);
        animator.start();
        animator.setInterpolator(new OvershootInterpolator(0.5f));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                calculateColor(fraction, mOpenSlotColor, mOffSlotColor);
                invalidate();
            }
        });
    }

    /**
     * 关
     */
    public void toggleOn() {
        // 属性动画实现手柄槽色渐变和手柄滑动
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "spotStartX", 0, mOffSpotX);
        animator.setDuration(300);
        animator.start();
        animator.setInterpolator(new OvershootInterpolator(0.5f));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                calculateColor(fraction, mOffSlotColor, mOpenSlotColor);
                invalidate();
            }
        });
    }

    public boolean getSwitchState() {
        return mIsToggleOn;
    }

    public void setToggle(boolean state) {
        mIsToggleOn = state;
        if (mIsToggleOn) {
            toggleOff();
        } else {
            toggleOn();
        }
    }

    public void setOnToggleListener(OnToggleListener listener) {
        onToggleListener = listener;
    }

}
