package com.wangchangjian.guitar_gtp.guitar.noteview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.wangchangjian.guitar_gtp.bar.Bar;
import com.wangchangjian.guitar_gtp.bar.Chord;
import com.wangchangjian.guitar_gtp.bar.Lyric;

import java.util.ArrayList;
import java.util.List;

public class NoteView extends FrameLayout {
    private LyricView lyricView;
    private CenterView centerView;
    private List<ChordView> chordViews;
    private List<Chord> chords;
    private Bar bar;
    private Lyric lyric;


    public NoteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (chords != null && chords.size() != 0) {
            chordViews = new ArrayList<>();
            for (int i = 0; i < chords.size(); i++) {
                ChordView chordView = new ChordView(context, chords.get(i));
                addView(chordView);
                chordViews.add(chordView);
            }
        }

        if (bar != null) {
            centerView = new CenterView(context, bar);
            addView(centerView);
        }
        if (lyric != null) {
            lyricView = new LyricView(context, lyric);
            addView(lyricView);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = 0;
        if (lyric != null)
            height += lyricView.getMeasuredHeight();
        if (centerView != null)
            height += centerView.getMeasuredHeight();
        if (chordViews != null && chordViews.size() != 0)
            height += chordViews.get(0).getMeasuredHeight();
        setMeasuredDimension(getMeasuredWidth(), height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int currentY = 0;
        if (chordViews != null && chordViews.size() != 0) {
            currentY = chordViews.get(0).getMeasuredHeight();
            for (int i = 0; i < chordViews.size(); i++) {
                ChordView view = chordViews.get(i);
                int measuredWidth = view.getMeasuredWidth();
                view.layout(measuredWidth * i, 0, measuredWidth * (i + 1), currentY);
            }
        }
        if (centerView != null) {
            centerView.layout(0, currentY, centerView.getMeasuredWidth(), currentY + centerView.getMeasuredHeight());
            currentY += centerView.getMeasuredHeight();
        }

        if (lyricView != null) {
            lyricView.layout(0, currentY, lyricView.getMeasuredWidth(), currentY + lyricView.getMeasuredHeight());
        }

    }
}
