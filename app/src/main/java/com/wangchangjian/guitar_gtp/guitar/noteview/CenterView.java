package com.wangchangjian.guitar_gtp.guitar.noteview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.text.TextPaint;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.wangchangjian.guitar_gtp.bar.Bar;

/**
 * Created by Administrator on 2018/12/25 0025.
 */
public class CenterView extends View {
    public static final int LINE_NUM = 6;
    private Bar bar;
    private TextView contrastTextView;


    public CenterView(Context context, Bar bar) {
        super(context);
        this.bar = bar;
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShort("点击 了");
            }
        });
        setWillNotDraw(false);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float strokeWidth = getPaint().getStrokeWidth();
        drawSixLines(canvas);
        getPaint().setStrokeWidth(getHeight() / 100);
        drawMusicalNote(canvas);
        getPaint().setStrokeWidth(strokeWidth);

    }

    private void drawMusicalNote(Canvas canvas) {
        if (bar != null)
            bar.drawBatsInView(canvas, this);
    }

    public void setBar(Bar bar) {
        this.bar = bar;
        invalidate();
    }

    private void drawSixLines(Canvas canvas) {
        for (int i = 0; i < LINE_NUM; i++) {
            canvas.drawLine(HORIZONTAL_PADDING,
                    linesVerticals[i],
                    getWidth() - HORIZONTAL_PADDING,
                    linesVerticals[i],
                    getPaint());
        }
        canvas.drawLine(
                HORIZONTAL_PADDING,
                TOP_PADDING,
                HORIZONTAL_PADDING,
                linesVerticals[LINE_NUM - 1],
                getPaint());
        canvas.drawLine(
                getWidth() - HORIZONTAL_PADDING,
                TOP_PADDING,
                getWidth() - HORIZONTAL_PADDING,
                linesVerticals[LINE_NUM - 1],
                getPaint());
    }


    private int linePadding;
    private int TOP_PADDING;
    private int BOTTOM_PADDING;
    private int HORIZONTAL_PADDING = 0;
    public static int[] linesVerticals;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (linesVerticals == null)
            linesVerticals = new int[LINE_NUM];
        BOTTOM_PADDING = TOP_PADDING = (int) (getHeight() * 0.3f);
        linePadding = (getHeight() - TOP_PADDING - BOTTOM_PADDING) / (LINE_NUM - 1);
        for (int i = 0; i < LINE_NUM; i++) {
            linesVerticals[i] = i * linePadding + TOP_PADDING;
        }

    }


    public void setChecked(boolean checked) {
        this.checked = checked;
        if (checked) setBackgroundColor(Color.YELLOW);
        else setBackgroundColor(Color.WHITE);
    }


    @Override
    public boolean performClick() {
        setChecked(!checked);
        return super.performClick();
    }

    private boolean checked;

    public void contrastTextView(TextView textView) {
        this.contrastTextView = textView;
    }

    public TextPaint getPaint() {
        return contrastTextView.getPaint();
    }
}
