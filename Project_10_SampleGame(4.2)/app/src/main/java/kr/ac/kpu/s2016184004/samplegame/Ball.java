package kr.ac.kpu.s2016184004.samplegame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Ball implements GameObject {
    private static int imageWidth;
    private static int imageHeight;
    private float x, y;
    private float dx, dy;
    private static Bitmap bitmap;

    public Ball(float x, float y, float dx, float dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        if(bitmap == null) {
            Resources res = GameView.view.getResources();
            bitmap = BitmapFactory.decodeResource(res, R.mipmap.soccer_ball_240);
            imageWidth = bitmap.getWidth();
            imageHeight = bitmap.getHeight();
        }
    }

    public void update() {
        // 시간에 비례한 만큼 움직이도록 해야 한다. 그래야 1초에 어떤 상황에서도 100 만큼 움직임
        this.x += this.dx * GameView.frameTime;
        this.y += this.dy * GameView.frameTime;
        int w = GameView.view.getWidth();
        int h = GameView.view.getHeight();

        if(x < 0 || x > w - imageWidth) {
            dx *= -1;
        }
        if(y < 0 || y > h - imageHeight) {
            dy = -dy;
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, this.x, this.y, null);
        canvas.drawBitmap(bitmap, this.x, this.y, null);
    }
}
