package kr.ac.kpu.s2016184004.term_project.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import kr.ac.kpu.s2016184004.term_project.R;
import kr.ac.kpu.s2016184004.term_project.framework.GameBitmap;
import kr.ac.kpu.s2016184004.term_project.framework.GameObject;

public class Particle implements GameObject {
    private final Bitmap bitmap;
    private final int x;
    private final int y;
    private int time;

    public Particle(int x, int y) {
        bitmap = GameBitmap.load(R.mipmap.effect);
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x, y, null);
    }

    @Override
    public void update() {
        MainGame game = MainGame.get();
        time ++;

        if(time > 10)
            game.remove(this);
    }
}
