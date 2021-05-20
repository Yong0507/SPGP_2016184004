package kr.ac.kpu.s2016184004.term_project.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import kr.ac.kpu.s2016184004.term_project.R;
import kr.ac.kpu.s2016184004.term_project.framework.GameBitmap;
import kr.ac.kpu.s2016184004.term_project.framework.GameObject;
import kr.ac.kpu.s2016184004.term_project.ui.view.GameView;

public class Heart implements GameObject {
    private final Bitmap bitmap;
    private final int x;
    private final int y;

    public Heart(int x, int y) {
        bitmap = GameBitmap.load(R.mipmap.heart);
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw(Canvas canvas) {

        canvas.drawBitmap(bitmap, x, y, null);
    }

    @Override
    public void update() {

    }
}
