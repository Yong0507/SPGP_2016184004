package kr.ac.kpu.s2016184004.term_project.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import kr.ac.kpu.s2016184004.term_project.R;
import kr.ac.kpu.s2016184004.term_project.framework.GameBitmap;
import kr.ac.kpu.s2016184004.term_project.framework.GameObject;
import kr.ac.kpu.s2016184004.term_project.ui.view.GameView;

public class Heart implements GameObject {
    private final Bitmap bitmap;
    private final int right;
    final int top;

    public Heart(int right, int top) {
        bitmap = GameBitmap.load(R.mipmap.heart);
        this.right = right;
        this.top = top;
    }

    @Override
    public void draw(Canvas canvas) {
        int x = bitmap.getWidth();
        int y = bitmap.getHeight();

        canvas.drawBitmap(bitmap, x, y, null);
        canvas.drawBitmap(bitmap, x*2,y,null);
        canvas.drawBitmap(bitmap, x*3,y, null);
    }

    @Override
    public void update() {

    }
}
