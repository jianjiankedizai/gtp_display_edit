package com.wangchangjian.guitar_gtp.util;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.text.TextPaint;

import com.blankj.utilcode.util.Utils;

/**
 * Created by Administrator on 2018/6/19.
 */

public class UiUtils {
    /**
     * 将文字画在指定的矩形区域的中心位置
     *
     * @param paint      画笔
     * @param canvas     画布
     * @param targetRect 文字所基于的矩形
     * @param text       所要画的文字
     */
    public static void drawTextAtRectCenter(TextPaint paint, Canvas canvas, RectF targetRect, String text) {
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        // 转载请注明出处：http://blog.csdn.net/hursing
        float baseline = (targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
        // 下面这行是实现水平居中，drawText对应改为传入targetRect.centerX()
        Paint.Align defaultAlign = paint.getTextAlign();
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(text, targetRect.centerX(), baseline, paint);
        paint.setTextAlign(defaultAlign);
    }
    public static TextPaint initPaint(int colorRes) {
        TextPaint innerPaint = new TextPaint();
        innerPaint.setAntiAlias(true);
        innerPaint.setColor(Utils.getApp().getResources().getColor(colorRes));
        innerPaint.setStyle(Paint.Style.FILL);
        innerPaint.setStrokeCap(Paint.Cap.ROUND);
        return innerPaint;
    }

    /**
     * 往path路径追加新的点
     *
     * @param path      操作的路径
     * @param nextToAdd 将要追加的路径
     * @param last      当前路径上的最后一个点
     */
    public static void addToPath(Path path, PointF nextToAdd, PointF last) {
        double s = Math.sqrt(Math.pow(nextToAdd.x - last.x, 2) + Math.pow(nextToAdd.y - last.y, 2));
        if (s < 2) { // 往复颤抖间距估值，高分辨率上使用quadTo会出现异常绘画，反复在一点来回抖动会出现毛刺
            path.lineTo(nextToAdd.x, nextToAdd.y);
        } else {
            path.quadTo(last.x, last.y,
                    (last.x + nextToAdd.x) / 2.0f, (last.y + nextToAdd.y) / 2.0f);
        }
    }

}
