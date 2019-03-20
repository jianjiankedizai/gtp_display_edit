package com.wangchangjian.guitar_gtp.bar.note;

import android.graphics.Canvas;
import android.graphics.Point;

import com.wangchangjian.guitar_gtp.guitar.NoteView;


public class SwipeNote extends Note {
    @Override
    public void drawEndPoint(Canvas canvas, Point endPoint, NoteView view) {
        int arrowWidth = view.getHeight() / 55;
        int pointLX = endPoint.x - arrowWidth;
        int pointRX = endPoint.x + arrowWidth;
        int pointY;
        if (endLine - startLine > 0) pointY = endPoint.y - arrowWidth * 4;
        else pointY = endPoint.y + arrowWidth * 4;
        canvas.drawLine(pointLX, pointY, endPoint.x, endPoint.y, view.getPaint());
        canvas.drawLine(pointRX, pointY, endPoint.x, endPoint.y, view.getPaint());
    }
}
