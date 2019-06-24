package com.example.fire;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

public class Fire extends View {
    public Fire(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    private static final int[] firePalette = {
            0xff070707,
            0xff1F0707,
            0xff2F0F07,
            0xff470F07,
            0xff571707,
            0xff671F07,
            0xff771F07,
            0xff8F2707,
            0xff9F2F07,
            0xffAF3F07,
            0xffBF4707,
            0xffC74707,
            0xffDF4F07,
            0xffDF5707,
            0xffDF5707,
            0xffD75F07,
            0xffD75F07,
            0xffD7670F,
            0xffCF6F0F,
            0xffCF770F,
            0xffCF7F0F,
            0xffCF8717,
            0xffC78717,
            0xffC78F17,
            0xffC7971F,
            0xffBF9F1F,
            0xffBF9F1F,
            0xffBFA727,
            0xffBFA727,
            0xffBFAF2F,
            0xffB7AF2F,
            0xffB7B72F,
            0xffB7B737,
            0xffCFCF6F,
            0xffDFDF9F,
            0xffEFEFC7,
            0xffFFFFFF
    };

    private  int[][] firePixels;
    private int fireW;
    private int fireH;
    Paint paint =new Paint();
    private Bitmap bitmap;
    private final Random random = new Random();
    private int[] bitmapPixels;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        fireH = (int) h/3;
        fireW = (int) w/3;
        firePixels = new int[fireW][fireH];
        bitmap = Bitmap.createBitmap(fireW, fireH,Bitmap.Config.RGB_565);

        for (int x = 0; x < firePixels.length ; x++) {
            firePixels [x][fireH-1] = firePalette.length-1;
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        spreadFire();
        drewFire(canvas);
        invalidate();
    }
    private void drewFire (Canvas canvas){
        final int pixelCount = fireW * fireH;
        if (bitmapPixels == null || bitmapPixels.length < pixelCount){
            bitmapPixels = new  int[pixelCount];
        }

        for (int x = 0; x <fireW ; x++) {
            for (int y = 0; y <fireH ; y++) {
                int temp = firePixels[x][y];
                if (temp < 0){
                    temp = 0;
                }
                if (temp >= firePalette.length){
                    temp = firePalette.length - 1;
                }
                @ColorInt int color = firePalette[temp];
                bitmapPixels[fireW * y + x] = color;
            }
        }
        bitmap.setPixels(bitmapPixels,0,fireW,0,0,fireW,fireH);
        float scale = (float) getWidth() / fireW;
        canvas.scale(scale,scale );
        canvas.drawBitmap(bitmap,0,0, paint);
    }

    private  void spreadFire (){
        for (int y = 0; y <fireH - 1 ; y++) {
            for (int x = 0; x <fireW ; x++) {
                int rand_x = random.nextInt(3);
                int rand_y = random.nextInt(6);
                int dst_x = Math.min(fireW-1, Math.max(0,x + rand_x - 1));
                int dst_y = Math.min(fireH - 1, y + rand_y);
                int deltaFire = -(rand_x & 1);
                firePixels [x][y] = Math.max(0,firePixels[dst_x][dst_y] + deltaFire);

            }
        }

    }

}


