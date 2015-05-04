package br.com.herediadesign.pecs.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

/**
 * Created by aheredia on 4/29/2015.
 */
public class ImageUtils {

    public static Bitmap overlay(Bitmap pecBase, Bitmap pec, String label) {
        Bitmap labelBmp = ImageUtils.textAsBitmap(label.toUpperCase(), pecBase.getHeight()/5, Color.BLACK);

        Bitmap bmOverlay = Bitmap.createBitmap(pecBase.getWidth(), pecBase.getHeight(), pecBase.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(pecBase, new Matrix(), null);
        canvas.drawBitmap(pec, (pecBase.getWidth()-pec.getWidth())/2, Math.round(pecBase.getHeight()/3.5), null);
        canvas.drawBitmap(labelBmp, (pecBase.getWidth()-labelBmp.getWidth())/2, 10, null);
        return bmOverlay;
    }

    public static Bitmap textAsBitmap(String text, float textSize, int textColor) {
        Paint paint = new Paint();
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        int width = (int) (paint.measureText(text) + 0.5f); // round
        float baseline = (int) (-paint.ascent() + 0.5f); // ascent() is negative
        int height = (int) (baseline + paint.descent() + 0.5f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }

    public static Bitmap crop(Bitmap srcBmp, int side) {

        Bitmap dstBmp;

        if (srcBmp.getWidth() >= srcBmp.getHeight()) {

            dstBmp = Bitmap.createBitmap(
                    srcBmp,
                    srcBmp.getWidth() / 2 - srcBmp.getHeight() / 2,
                    0,
                    srcBmp.getHeight(),
                    srcBmp.getHeight()
            );

        } else {

            dstBmp = Bitmap.createBitmap(
                    srcBmp,
                    0,
                    srcBmp.getHeight() / 2 - srcBmp.getWidth() / 2,
                    srcBmp.getWidth(),
                    srcBmp.getWidth()
            );
        }

        return Bitmap.createScaledBitmap(dstBmp, side, side, true);
    }
}
