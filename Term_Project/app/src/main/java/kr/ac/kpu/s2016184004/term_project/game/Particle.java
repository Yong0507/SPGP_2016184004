package kr.ac.kpu.s2016184004.term_project.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import kr.ac.kpu.s2016184004.term_project.R;
import kr.ac.kpu.s2016184004.term_project.framework.GameBitmap;
import kr.ac.kpu.s2016184004.term_project.framework.GameObject;

public class Particle implements GameObject {
    private final Bitmap bitmap;
    private final int right;
    final int top;

    public Particle(int right, int top) {
        bitmap = GameBitmap.load(R.mipmap.effect);
        this.right = right;
        this.top = top;
    }

    @Override
    public void draw(Canvas canvas) {

        canvas.drawBitmap(bitmap, right, top, null);
    }

    @Override
    public void update() {

    }
}
