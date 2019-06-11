package com.wangchangjian.guitar_gtp.guitar.noteview;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.wangchangjian.guitar_gtp.bar.Lyric;

public class LyricView extends TextView {
    private Lyric lyric;


    public LyricView(Context context, Lyric lyric) {
        super(context);
        this.lyric = lyric;
        setGravity(Gravity.CENTER_HORIZONTAL);
        setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(30)));
    }
}
