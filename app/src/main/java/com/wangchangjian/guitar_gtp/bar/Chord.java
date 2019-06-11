package com.wangchangjian.guitar_gtp.bar;

import android.graphics.Point;

public class Chord {
    public static final int C_CHORD = 1;
    public static final int D_CHORD = 2;
    public static final int E_CHORD = 3;
    public static final int F_CHORD = 4;
    public static final int G_CHORD = 5;
    public static final int A_CHORD = 6;
    public static final int B_CHORD = 7;

    public int rootNote;
    public Point[] pressPoints;
    public Integer[] disableWire;
    public String chordName;

    public int startPat;


}
