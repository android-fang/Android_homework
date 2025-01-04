package com.example.casper.Experiment2024.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.casper.Experiment2024.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class WhackAMoleView extends SurfaceView implements Runnable {
    private Thread gameThread;
    private boolean isPlaying;
    private SurfaceHolder surfaceHolder;
    private Paint paint;
    private int score;
    private int timeSurvived;
    private ArrayList<Bomb> bombs;
    private Submarine submarine;
    private long startTime;
    private long gameStartTime;

    public WhackAMoleView(Context context) {
        super(context);
        init(context);
    }
    public WhackAMoleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        surfaceHolder = getHolder();
        paint = new Paint();
        score = 0;
        timeSurvived = 0;
        bombs = new ArrayList<>();
        submarine = new Submarine();
        gameStartTime = System.currentTimeMillis();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        submarine.move(event.getX(), event.getY());
        return true;
    }


    @Override
    public void run() {
        while (isPlaying) {
            update();
            draw();
            control();
        }
    }

    private void update() {
        // 每过一秒钟添加新的炸弹
        if (System.currentTimeMillis() - startTime > 1000) {
            bombs.add(new Bomb());
            startTime = System.currentTimeMillis();
        }

        // 更新炸弹的位置
        Iterator<Bomb> iterator = bombs.iterator();
        while (iterator.hasNext()) {
            Bomb bomb = iterator.next();
            bomb.move();
            if (bomb.isOffScreen()) {
                iterator.remove();
                score++;
            }
            if (bomb.checkCollision(submarine)) {
                isPlaying = false; // 游戏结束
            }
        }

        // 更新生存时间
        timeSurvived = (int) ((System.currentTimeMillis() - gameStartTime) / 1000);
    }

    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            Canvas canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.CYAN); // 背景色为蓝色，代表水域

            // 绘制潜艇
            submarine.draw(canvas, paint);

            // 绘制炸弹
            for (Bomb bomb : bombs) {
                bomb.draw(canvas, paint);
            }

            // 绘制分数和生存时间
            paint.setColor(Color.WHITE);
            paint.setTextSize(50);
            canvas.drawText("得分: " + score, 50, 50, paint);
            canvas.drawText("生存时间: " + timeSurvived + "s", 50, 100, paint);

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control() {
        try {
            Thread.sleep(20); // 控制帧率
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        isPlaying = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        isPlaying = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (submarine != null) {
            submarine.setPosition(w, h); // 设置潜艇的位置
        }
    }

    private class Submarine {
        private float x, y;
        private Bitmap bitmap;

        public Submarine() {
            // 加载潜艇图片
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.a);
        }

        public void setPosition(int screenWidth, int screenHeight) {
            // 设置潜艇的初始位置
            x = screenWidth / 2 - bitmap.getWidth() / 2; // 水平居中
            y = screenHeight - bitmap.getHeight(); // 放置在屏幕底部
        }

        public void move(float newX, float newY) {
            x = newX - bitmap.getWidth() / 2; // 根据触摸位置居中潜艇
            y = newY - bitmap.getHeight() / 2;
        }

        public void draw(Canvas canvas, Paint paint) {
            canvas.drawBitmap(bitmap, x, y, paint);
        }
    }

    private class Bomb {
        private float x, y;
        private float speed;
        private Bitmap bitmap;

        public Bomb() {
            Random random = new Random();
            x = random.nextInt(getWidth());
            y = 0; // 从顶部开始
            speed = 10 + random.nextInt(10); // 随机速度
            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.qiqiu); // 加载炸弹图片
        }

        public void move() {
            y += speed; // 向下移动
        }

        public boolean isOffScreen() {
            return y > getHeight();
        }

        public void draw(Canvas canvas, Paint paint) {
            canvas.drawBitmap(bitmap, x, y, paint);
        }

        public boolean checkCollision(Submarine submarine) {
            // 简单的碰撞检测
            return (x < submarine.x + submarine.bitmap.getWidth() &&
                    x + bitmap.getWidth() > submarine.x &&
                    y < submarine.y + submarine.bitmap.getHeight() &&
                    y + bitmap.getHeight() > submarine.y);
        }
    }
}