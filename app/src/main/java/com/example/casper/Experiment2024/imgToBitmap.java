package com.example.casper.Experiment2024;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;

public class imgToBitmap{

    /**
     * 将drawable资源ID转换成Bitmap
     *
     * @param context 上下文
     * @param drawableResId drawable资源ID
     * @return Bitmap对象
     */
    public static Bitmap drawableToBitmap(Context context, int drawableResId) {
        Resources res = context.getResources();
        // 通过资源ID获取drawable对象
        android.graphics.drawable.Drawable drawable = res.getDrawable(drawableResId);

        if (drawable == null) {
            throw new IllegalArgumentException("Drawable resource ID is invalid: " + drawableResId);
        }

        // 使用BitmapDrawable的getBitmap方法（适用于API 18及以上）
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        // 将drawable绘制到Bitmap上（适用于所有API级别）
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}