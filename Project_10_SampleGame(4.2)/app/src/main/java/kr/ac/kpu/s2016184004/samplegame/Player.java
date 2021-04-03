package kr.ac.kpu.s2016184004.samplegame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Player implements  GameObject{
    private static int imageWidth;
    private static int imageHeight;
    private float x, y;
    private float dx, dy;
    private static Bitmap bitmap;

    public Player(float x, float y, float dx, float dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        if(bitmap == null) {
            Resources res = GameView.view.getResources();
            bitmap = BitmapFactory.decodeResource(res, R.mipmap.plane_240);
            imageWidth = bitmap.getWidth();
            imageHeight = bitmap.getHeight();
        }
    }

    public void moveTo(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public void update() {

    }

    public void draw(Canvas canvas) {
        float left = x -imageWidth / 2;
        float top = y - imageWidth / 2;
        canvas.drawBitmap(bitmap,this.x, this.y,null);
    }
}
