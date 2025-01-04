package com.example.casper.Experiment2024.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import com.example.casper.Experiment2024.R;

import java.util.Calendar;

public class CustomClockView extends View {
    private Paint paint;
    private int width;
    private int height;
    private float hour;
    private float minute;
    private float second;

    private Bitmap dialBitmap; // 表盘图片
    private Bitmap hourHandBitmap; // 时针图片
    private Bitmap minuteHandBitmap; // 分针图片
    private Bitmap secondHandBitmap; // 秒针图片

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            invalidate(); // 刷新视图
            handler.postDelayed(this, 1000); // 每秒更新一次
        }
    };

    public CustomClockView(Context context) {
        super(context);
        init();
    }

    public CustomClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public void startUpdating() {
        handler.post(runnable); // 启动更新
    }
    public void stopUpdating() {
        handler.removeCallbacks(runnable);
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);

        // 加载图片资源
        dialBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.clock_dial);
        hourHandBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hour);
        minuteHandBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.minute);
        secondHandBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.second);
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacks(runnable); // 停止更新
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawClockFace(canvas);
        drawClockHands(canvas);
    }

    private void drawClockFace(Canvas canvas) {
        // 绘制表盘
        canvas.drawBitmap(dialBitmap, (width - dialBitmap.getWidth()) / 2, (height - dialBitmap.getHeight()) / 2, paint);
    }

    private void drawClockHands(Canvas canvas) {
        // 获取当前时间
        Calendar calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);
        second = calendar.get(Calendar.SECOND);

        // 绘制时针
        float hourAngle = (float) ((hour + minute / 60) * 30);
        drawHand(canvas, hourHandBitmap, hourAngle, Math.min(width, height) / 4);

        // 绘制分针
        float minuteAngle = (float) ((minute + second / 60) * 6);
        drawHand(canvas, minuteHandBitmap, minuteAngle, Math.min(width, height) / 3);

        // 绘制秒针
        float secondAngle = (float) (second * 6);
        drawHand(canvas, secondHandBitmap, secondAngle, Math.min(width, height) / 2.5f);
    }

    private void drawHand(Canvas canvas, Bitmap handBitmap, float angle, float length) {
        // 计算指针的终点位置
        float x = (float) (width / 2 + length * Math.sin(Math.toRadians(angle)));
        float y = (float) (height / 2 - length * Math.cos(Math.toRadians(angle)));

        // 计算指针的中心位置
        float handX = (width / 2) - (handBitmap.getWidth() / 2); // 将指针居中
        float handY = (height / 2) - (handBitmap.getHeight() / 2); // 将指针居中

        // 旋转画布
        canvas.save();
        // 将角度向左下角偏移 45 度
        canvas.rotate(angle + 129, handX + (handBitmap.getWidth() / 2), handY + (handBitmap.getHeight() / 2)); // 旋转中心在指针中心
        canvas.drawBitmap(handBitmap, handX, handY, paint); // 绘制指针
        canvas.restore();
    }
}
