package com.wangchangjian.guitar_gtp.bar;

import android.graphics.Canvas;


import com.wangchangjian.guitar_gtp.guitar.noteview.CenterView;

import java.util.List;

/**
 * Created by Administrator on 2019/2/26 0026.
 */
public abstract class Bar {

    List<Chord> chords;

    public abstract void drawBatsInView(Canvas canvas, CenterView view);
}
