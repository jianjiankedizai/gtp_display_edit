package com.wangchangjian.guitar_gtp.bar.note;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.RectF;

import com.wangchangjian.guitar_gtp.guitar.NoteView;
import com.wangchangjian.guitar_gtp.util.UiUtils;


public class BrokenNote extends Note {
    private int character = -1;

    @Override
    public void drawEndPoint(Canvas canvas, Point endPoint, NoteView view) {
        int arrowWidth = view.getHeight() / 50;
        int pointLX = endPoint.x - arrowWidth;
        int pointRX = endPoint.x + arrowWidth;
        int pointTY = endPoint.y - arrowWidth;
        int pointBY = endPoint.y + arrowWidth;

        if (character >= 0) {
            UiUtils.drawTextAtRectCenter(view.getPaint(), canvas, new RectF(pointLX, pointTY - arrowWidth * 1.5f, pointRX, pointBY - arrowWidth * 1.5f), String.valueOf(character));
        } else {
            canvas.drawLine(pointLX, pointBY, pointRX, pointTY, view.getPaint());
            canvas.drawLine(pointLX, pointTY, pointRX, pointBY, view.getPaint());
        }
    }
}
