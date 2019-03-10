package com.wangchangjian.guitar_gtp.circle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2019/2/24 0024.
 */
public class CircleItemView extends TextView {
    private Paint mPaint;//画笔


    public CircleItemView(Context context) {
        this(context, null);
    }

    private RectF cycle_little = new RectF();


    public CircleItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//取消锯齿
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);//描边
        mPaint.setColor(Color.GREEN);
        setWillNotDraw(false);


    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float x = (getWidth() - getHeight() / 2) / 2;
        float y = getHeight() / 4;

        cycle_little.set(5, 5,
                getWidth() - 5, getHeight() - 5);

    }

    int arcAngle = 60;
    boolean start = true;
    int nums = 3;

    int offset = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < nums; i++) {
            canvas.drawArc(cycle_little, 360 * i / nums + offset, arcAngle, false, mPaint);
        }
        offset++;
        if (offset == 360) offset = 0;
        if (start)
            invalidate();
    }


}
