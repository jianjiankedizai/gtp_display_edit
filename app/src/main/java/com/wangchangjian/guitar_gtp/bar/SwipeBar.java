package com.wangchangjian.guitar_gtp.bar;

import android.graphics.Canvas;
import android.graphics.Point;


import com.wangchangjian.guitar_gtp.bar.bat.Bat;
import com.wangchangjian.guitar_gtp.bar.note.Note;
import com.wangchangjian.guitar_gtp.guitar.Constant;
import com.wangchangjian.guitar_gtp.guitar.NoteView;

import static com.wangchangjian.guitar_gtp.guitar.Constant.DOT_RADIUS;
import static com.wangchangjian.guitar_gtp.guitar.NoteView.linesVerticals;

/**
 * Created by Administrator on 2019/2/26 0026.
 */
public class SwipeBar extends Bar {
    public Bat[] bats;


    @Override
    public void drawBatsInView(Canvas canvas, NoteView view) {
        if (bats == null) return;
        int batWidth = view.getWidth() / bats.length;
        for (int i = 0; i < bats.length; i++) {
            drawBat(i, canvas, view, batWidth);
        }
    }

    private void drawBat(int index, Canvas canvas, NoteView view, int batWidth) {
        Note[] swipeNotes = bats[index].notes;
        if (swipeNotes == null || swipeNotes.length == 0) return;
        int lineX;
        int lineStartY;
        int lineEndY;
        for (int i = 0; i < swipeNotes.length; i++) {
            lineX = getLineOffsetX(i, swipeNotes, batWidth) + batWidth * index + view.getWidth() / 30;
            Note note = swipeNotes[i];
            lineStartY = linesVerticals[note.startLine];
            lineEndY = linesVerticals[note.endLine];
            canvas.drawLine(lineX, lineStartY, lineX, lineEndY, view.getPaint());
            drawTimeLine(canvas, lineX, i, swipeNotes, view, batWidth);
            note.drawEndPoint(canvas, new Point(lineX, lineEndY), view);
        }
    }

    private void drawTimeLine(Canvas canvas, int lineX, int index, Note[] swipeNotes, NoteView view, int batWidth) {
        Note rightNote = index == swipeNotes.length - 1 || swipeNotes.length == 1 ? null : swipeNotes[index + 1];
        Note currentNote = swipeNotes[index];

        Point verticalStartPoint = new Point(lineX, linesVerticals[5]);
        Point verticalEndPoint = new Point(lineX, linesVerticals[5] + view.getHeight() / 6);
        int horLineCount = 0;
        if (currentNote.time == Constant.ONE_PAD) horLineCount = 0;
        else if (currentNote.time == Constant.HALF_PAD) horLineCount = 1;
        else if (currentNote.time == Constant.QUARTER_PAD) horLineCount = 2;
        else if (currentNote.time == Constant.EIGHTH_PAD) horLineCount = 3;
        else if (currentNote.time == Constant.SIXTEEN_PAD) horLineCount = 4;

        int horEndX = lineX;
        if (rightNote != null && currentNote.time >= rightNote.time) {
            horEndX += (int) (currentNote.time * batWidth);
        }
        int timeLineStopY;
        for (int i = 0; i < horLineCount; i++) {
            timeLineStopY = verticalEndPoint.y - view.getHeight() * i / 20;
            canvas.drawLine(verticalEndPoint.x, timeLineStopY, horEndX, timeLineStopY, view.getPaint());
        }
        int dotStopX;
        int dotStopY = linesVerticals[5] + view.getHeight() / 25;

        for (int i = 0; i < currentNote.dotCount; i++) {
            dotStopX = lineX + batWidth * (i + 1) / 5;
            canvas.drawCircle(dotStopX, dotStopY, DOT_RADIUS, view.getPaint());
        }
        canvas.drawLine(verticalStartPoint.x, verticalStartPoint.y, verticalEndPoint.x, verticalEndPoint.y, view.getPaint());
    }

    private int getLineOffsetX(int lineIndex, Note[] swipeNotes, int batWidth) {
        int lineOffsetX = 0;
        for (int i = 0; i < lineIndex; i++) {
            lineOffsetX += swipeNotes[i].getTime() * batWidth;
        }
        return lineOffsetX;
    }
}
