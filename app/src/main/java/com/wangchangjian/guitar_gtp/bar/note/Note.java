package com.wangchangjian.guitar_gtp.bar.note;

import android.graphics.Canvas;
import android.graphics.Point;

import com.wangchangjian.guitar_gtp.guitar.noteview.CenterView;


public abstract class Note {
    public int startLine;
    public int endLine;
    public float time;
    public int dotCount;

    public float getTime() {
        float realTime = time;
        for (int i = 0; i < dotCount; i++) {
            realTime += Math.pow(0.5d, i + 1) * time;
        }
        return realTime;
    }

    public abstract void drawEndPoint(Canvas canvas, Point endPoint, CenterView view);
}
