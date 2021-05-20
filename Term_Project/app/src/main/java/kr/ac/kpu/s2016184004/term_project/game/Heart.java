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
    public int life_count = 3;

    public Heart(int x, int y) {
        bitmap = GameBitmap.load(R.mipmap.heart);
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw(Canvas canvas) {

        for(int i=0; i < life_count; ++i) {
            canvas.drawBitmap(bitmap, x + i * 60, y, null);
        }
    }

    @Override
    public void update() {

    }
}
