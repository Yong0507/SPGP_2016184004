package kr.ac.kpu.s2016184004.term_project.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.kpu.s2016184004.term_project.R;
import kr.ac.kpu.s2016184004.term_project.framework.GameBitmap;
import kr.ac.kpu.s2016184004.term_project.framework.GameObject;
import kr.ac.kpu.s2016184004.term_project.ui.view.GameView;

public class Score implements GameObject {
    private final Bitmap bitmap;
    private final int right;
    private final int top;

    private int doubleScore;
    private static final float INITIAL_DOBULE_SCORE = 15.0f;
    private float time;
    private float double_time;

    public void setScore(int score) {
        this.score = score;
        this.displayScore = score;
    }

    public void setDouble(int _doubleScore) { doubleScore =_doubleScore;}

    public void addScore(int amount) {
        this.score += (amount * doubleScore);
    }

    private int score, displayScore;
    private Rect src = new Rect();
    private RectF dst = new RectF();

    public Score(int right, int top) {
        bitmap = GameBitmap.load(R.mipmap.number_24x32);
        this.right = right;
        this.top = top;

        time = INITIAL_DOBULE_SCORE;
        double_time = INITIAL_DOBULE_SCORE;
    }
    @Override
    public void update() {
        if (displayScore < score) {
            displayScore++;
        }

        MainGame game = MainGame.get();
        time += game.frameTime;
        if (time >= double_time) {
            doubleScore = 1;
            time -= double_time;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        int value = this.displayScore;
        int nw = bitmap.getWidth() / 10;
        int nh = bitmap.getHeight();
        int x = right;
        int dw = (int) (nw * GameView.MULTIPLIER);
        int dh = (int) (nh * GameView.MULTIPLIER);
        while (value > 0) {
            int digit = value % 10;
            src.set(digit * nw, 0, (digit + 1) * nw, nh);
            x -= dw;
            dst.set(x, top, x + dw, top + dh);
            canvas.drawBitmap(bitmap, src, dst, null);
            value /= 10;
        }
    }
}