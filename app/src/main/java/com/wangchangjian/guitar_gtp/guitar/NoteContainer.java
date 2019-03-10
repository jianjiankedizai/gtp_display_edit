package com.wangchangjian.guitar_gtp.guitar;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wangchangjian.guitar_gtp.bar.Bar;

import java.util.List;

import static com.wangchangjian.guitar_gtp.guitar.Constant.CONTAINER_PADDING_LEFT;
import static com.wangchangjian.guitar_gtp.guitar.Constant.CONTAINER_PADDING_RIGHT;
import static com.wangchangjian.guitar_gtp.guitar.Constant.NOTE_HEIGHT;
import static com.wangchangjian.guitar_gtp.guitar.Constant.NOTE_VERTICAL_PADDING;


public class NoteContainer extends FrameLayout {

    public List<List<Bar>> bars;
    private TextView textView;

    public NoteContainer(Context context) {
        this(context, null);
    }


    public void setBars(List<List<Bar>> bars) {
        this.bars = bars;
        initNotes();
        requestLayout();
    }

    private void initNotes() {
        if (bars == null) return;
        removeAllViews();
        for (int i = 0; i < bars.size(); i++) {
            List<Bar> barHors = bars.get(i);
            if (barHors == null || barHors.size() == 0) continue;
            for (int j = 0; j < barHors.size(); j++) {
                NoteView noteView = new NoteView(getContext());
                noteView.contrastTextView(textView);
                noteView.setBar(barHors.get(j));
                noteView.setTag(new Point(i, j));
                addView(noteView);
            }
        }
    }

    public NoteContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (bars != null) {
            setMeasuredDimension(getMeasuredWidth(), (NOTE_HEIGHT + NOTE_VERTICAL_PADDING) * bars.size());
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view.getTag() instanceof Point) {
                Point tag = (Point) view.getTag();
                int childWidth = (getWidth() - CONTAINER_PADDING_LEFT - CONTAINER_PADDING_RIGHT) / bars.get(tag.x).size();
                int childLeft = tag.y * childWidth + CONTAINER_PADDING_LEFT;
                int childBottom = tag.x * (NOTE_HEIGHT + NOTE_VERTICAL_PADDING) + NOTE_HEIGHT;
                int childRight = tag.y * childWidth + childWidth + CONTAINER_PADDING_LEFT;
                int childTop = tag.x * (NOTE_HEIGHT + NOTE_VERTICAL_PADDING);
                view.layout(childLeft, childTop, childRight, childBottom);
            }

        }
    }

    public void contrastTextView(TextView textView) {
        this.textView = textView;

    }
}
