package kr.ac.kpu.s2016184004.term_project.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import kr.ac.kpu.s2016184004.term_project.R;
import kr.ac.kpu.s2016184004.term_project.framework.GameBitmap;
import kr.ac.kpu.s2016184004.term_project.framework.GameObject;
import kr.ac.kpu.s2016184004.term_project.ui.view.GameView;

public class Itemeffect implements GameObject {
    private final Bitmap bitmap;

    public int is_double = 0;

    public void setIsDouble(int _double) {is_double = _double;}

    public Itemeffect(int x, int y) {
        bitmap = GameBitmap.load(R.mipmap.multiple);
    }

    @Override
    public void draw(Canvas canvas) {

        for(int i=0; i < is_double; ++i) {
            canvas.drawBitmap(bitmap, GameView.view.getWidth() - 100, 200, null);
        }
    }

    @Override
    public void update() {

    }
}
