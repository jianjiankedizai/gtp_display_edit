package com.wangchangjian.guitar_gtp.circle;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2019/2/23 0023.
 */
public class CircleLayout extends FrameLayout {
    public CircleLayout(Context context) {
        this(context, null);
    }

    public CircleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    private Point[] childCenters;
    private int padding = 40;
    private float radius;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int childCount = getChildCount();
        childCenters = new Point[childCount];
        float x = getWidth() / 2f;
        float y = getHeight() / 2f;
        radius = x - padding;
        for (int i = selectPosition; i < childCount + selectPosition; i++) {
            int position = i;
            if (position >= childCount) position = position - childCount;
            childCenters[position] = new Point(
                    ((int) (x + Math.sin(2 * Math.PI * (i - selectPosition) / childCount + offsetAngle * Math.PI / 180d) * radius)),
                    ((int) (y + Math.cos(2 * Math.PI * (i - selectPosition) / childCount + offsetAngle * Math.PI / 180d) * radius)));
        }
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            Point childCenter = childCenters[i];
            childAt.layout(childCenter.x - childAt.getWidth() / 2,
                    childCenter.y - childAt.getWidth() / 2,
                    childCenter.x + childAt.getWidth() / 2,
                    childCenter.y + childAt.getWidth() / 2);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    int touchX;
    int touchY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 记录触摸点坐标
                touchX = x;
                touchY = y;
                return true;
            case MotionEvent.ACTION_MOVE:
                // 计算偏移量
                int offsetX = x - touchX;
                offsetAngle = 90 * (offsetX) / getWidth();

                requestLayout();

                // 在当前left、top、right、bottom的基础上加上偏移量
                break;
            case MotionEvent.ACTION_UP:
                // 记录触摸点坐标
                int nearlyPosition = getNearlyPosition();
                setSelection(nearlyPosition);
                return true;


        }
        return super.onTouchEvent(event);
    }

    private int selectPosition = 0;

    public void setSelection(int position) {
        if (position > getChildCount() - 1 || position < 0) return;

        Point childCenter = childCenters[position];
        offsetAngle = (int) (Math.asin((childCenter.x - getWidth() / 2) / radius) * 180 / Math.PI);
        startAnim();
        selectPosition = position;
    }

    private int offsetAngle = 0;


    private void startAnim() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(offsetAngle, 0).setDuration(200);
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                offsetAngle = (int) valueAnimator.getAnimatedValue();
                requestLayout();
            }

        });
        valueAnimator.start();
    }

    public int getSelectPosition() {
        return selectPosition;
    }

    public int getNearlyPosition() {
        double[] distances = new double[childCenters.length];

        for (int i = 0; i < childCenters.length; i++) {
            distances[i] = Math.pow(childCenters[i].x - getWidth() / 2, 2) + Math.pow(childCenters[i].y - getHeight() / 2 - radius, 2);
        }
        int mNearlyPosition = 0;
        for (int i = 0; i < distances.length; i++) {
            if (distances[i] < distances[mNearlyPosition])
                mNearlyPosition = i;
        }
        return mNearlyPosition;
    }
}
