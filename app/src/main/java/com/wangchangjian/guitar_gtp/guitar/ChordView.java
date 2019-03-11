package com.wangchangjian.guitar_gtp.guitar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.wangchangjian.guitar_gtp.bar.Chord;

import static com.wangchangjian.guitar_gtp.bar.Chord.C_CHORD;
import static com.wangchangjian.guitar_gtp.guitar.NoteView.LINE_NUM;

/**
 * Created by Administrator on 2018/12/25 0025.
 */
public class ChordView extends TextView {
    private Chord chord;
    private int vertical_padding;


    public ChordView(Context context) {
        this(context, null);
    }

    public ChordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShort("点击 了");
            }
        });
        setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
        setPadding(0, SizeUtils.dp2px(2), 0, 0);
        Chord chord = new Chord();
        chord.rootNote = C_CHORD;
        chord.chordName = "C";
        chord.pressPoints = new Point[]{new Point(5, 3), new Point(4, 2), new Point(2, 1)};
        setChord(chord);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float strokeWidth = getPaint().getStrokeWidth();
        drawSixLines(canvas);
        getPaint().setStrokeWidth(getHeight() / 120);
        getPaint().setTextSize(SizeUtils.sp2px(10));
        drawPressPoint(canvas);
        getPaint().setStrokeWidth(strokeWidth);

    }

    private void drawPressPoint(Canvas canvas) {
        if (chord == null) return;
        for (int i = 0; i < chord.pressPoints.length; i++) {
            Point pressPoint = chord.pressPoints[i];
            canvas.drawCircle(LINES_VERTICAL[pressPoint.x - 1], LINES_HORIZONTAL[pressPoint.y - 1] - vertical_padding / 2, HORIZONTAL_PADDING / 3, getPaint());
        }
    }

    public void setChord(Chord chord) {
        this.chord = chord;
        setText(chord.chordName);
        invalidate();
    }

    private void drawSixLines(Canvas canvas) {
        for (int i = 0; i < LINE_NUM; i++) {
            canvas.drawLine(LINES_VERTICAL[i],
                    TOP_PADDING,
                    LINES_VERTICAL[i],
                    LINES_HORIZONTAL[0],
                    getPaint());
        }
        for (int i = 0; i < LINES_HORIZONTAL.length; i++) {
            canvas.drawLine(HORIZONTAL_PADDING,
                    LINES_HORIZONTAL[i],
                    getWidth() - HORIZONTAL_PADDING,
                    LINES_HORIZONTAL[i],
                    getPaint());
        }


    }


    private int linePadding;
    private int TOP_PADDING;
    private int HORIZONTAL_PADDING;
    public static final int[] LINES_VERTICAL = new int[LINE_NUM];
    public static final int[] LINES_HORIZONTAL = new int[4];

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        TOP_PADDING = (int) (getHeight() * 0.3f);
        HORIZONTAL_PADDING = (int) (getHeight() * 0.15f);
        linePadding = (getWidth() - HORIZONTAL_PADDING - HORIZONTAL_PADDING) / (LINE_NUM - 1);
        for (int i = 0; i < LINE_NUM; i++) {
            LINES_VERTICAL[i] = i * linePadding + HORIZONTAL_PADDING;
        }

        vertical_padding = (getHeight() - TOP_PADDING) / 3;
        LINES_HORIZONTAL[3] = TOP_PADDING;
        LINES_HORIZONTAL[2] = TOP_PADDING + vertical_padding;
        LINES_HORIZONTAL[1] = TOP_PADDING + vertical_padding * 2;
        LINES_HORIZONTAL[0] = getHeight() - 1;


    }

}
