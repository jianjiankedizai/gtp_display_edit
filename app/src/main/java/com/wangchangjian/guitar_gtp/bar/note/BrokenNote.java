package com.wangchangjian.guitar_gtp.bar.note;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.RectF;

import com.wangchangjian.guitar_gtp.guitar.NoteView;
import com.wangchangjian.guitar_gtp.util.UiUtils;

import java.util.ArrayList;
import java.util.List;


public class BrokenNote extends Note {
    private List<TouchPoint> touchPoints = new ArrayList<>();

    public static class TouchPoint {
        public int character = -1;
        public int line = -1;

    }

    @Override
    public void drawEndPoint(Canvas canvas, Point endPoint, NoteView view) {
        int arrowWidth = view.getHeight() / 50;
        for (int i = 0; i < touchPoints.size(); i++) {
            TouchPoint touchPoint = touchPoints.get(i);
            int pointLX = endPoint.x - arrowWidth;
            int pointRX = endPoint.x + arrowWidth;
            int pointTY = view.linesVerticals[touchPoint.line]
                    - arrowWidth;
            int pointBY = view.linesVerticals[touchPoint.line]
                    + arrowWidth;

            if (touchPoint.character >= 0 && touchPoint.line >= 0) {
                UiUtils.drawTextAtRectCenter(view.getPaint(), canvas, new RectF(pointLX, pointTY - arrowWidth * 1.5f, pointRX, pointBY - arrowWidth * 1.5f), String.valueOf(touchPoint.character));
            } else {
                canvas.drawLine(pointLX, pointBY, pointRX, pointTY, view.getPaint());
                canvas.drawLine(pointLX, pointTY, pointRX, pointBY, view.getPaint());
            }

        }

    }
}
