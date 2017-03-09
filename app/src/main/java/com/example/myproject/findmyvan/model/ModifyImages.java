package com.example.myproject.findmyvan.model;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by TheeranaiAsipong on 27/9/2559.
 */

public class ModifyImages {
    public ModifyImages(){}

    public Bitmap setCirclebitmap(Bitmap input) {

        Bitmap output;

        if (input.getWidth() > input.getHeight()) {
            output = Bitmap.createBitmap(input.getHeight(), input.getHeight(), Bitmap.Config.ARGB_8888);
        } else {
            output = Bitmap.createBitmap(input.getWidth(), input.getWidth(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, input.getWidth(), input.getHeight());

        float r = 0;

        if (input.getWidth() > input.getHeight()) {
            r = input.getHeight() / 2;
        } else {
            r = input.getWidth() / 2;
        }

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(r, r, r, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(input, rect, rect, paint);
        return output;

    }

    public Bitmap createBitmap(String url, int denominator_width, int denominator_height){
        Bitmap outBitmap;
        Bitmap inbitmap = null;
        try {
            InputStream inputStream = new URL(url).openStream();
            inbitmap = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int width = inbitmap.getWidth();
        int height = inbitmap.getHeight();

        float scaleWidth = ((float) denominator_width) / width;
        float scaleHeiht = ((float) denominator_height) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeiht);
        outBitmap = Bitmap.createBitmap(inbitmap, 0, 0, width, height, matrix, false);

        return outBitmap;
    }

    public static Bitmap createDrawableFromView(Context context, View view){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0,0,displayMetrics.widthPixels,displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(),view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return  bitmap;
    }
}
