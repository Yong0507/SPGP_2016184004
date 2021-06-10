package kr.ac.kpu.s2016184004.term_project.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import kr.ac.kpu.s2016184004.term_project.R;
import kr.ac.kpu.s2016184004.term_project.framework.GameBitmap;
import kr.ac.kpu.s2016184004.term_project.framework.GameObject;
import kr.ac.kpu.s2016184004.term_project.ui.view.GameView;

public class invincible implements GameObject {
    private final Bitmap bitmap;
    public float x;
    public float y;
    public int invin = 0;

    public invincible(float x, float y) {
        bitmap = GameBitmap.load(R.mipmap.tra);
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw(Canvas canvas) {

        for(int i=0; i < invin; ++i) {
            canvas.drawBitmap(bitmap, x , y, null);
        }
    }

    @Override
    public void update() {

    }
}
