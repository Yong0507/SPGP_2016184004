package kr.ac.kpu.s2016184004.term_project.game;

import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.Random;

import kr.ac.kpu.s2016184004.term_project.R;
import kr.ac.kpu.s2016184004.term_project.framework.BoxCollidable;
import kr.ac.kpu.s2016184004.term_project.framework.GameBitmap;
import kr.ac.kpu.s2016184004.term_project.framework.GameObject;
import kr.ac.kpu.s2016184004.term_project.ui.view.GameView;

public class CraconMissile implements GameObject, BoxCollidable {
    private static final String TAG = CraconMissile.class.getSimpleName();
    private GameBitmap bitmap;
    private float x;
    private float y;
    private int type;
    private int speed;
    private int dir = 1;
    private int targetX;
    Player player;

    public CraconMissile(float x, float y, int speed){
        bitmap = new GameBitmap(R.mipmap.boss_bullet);
        this.x = x;
        this.y = y;
        this.speed = -speed;

        Random r = new Random();
        type = r.nextInt(3);

    }

    public static CraconMissile get(float x, float y, int speed) {
        MainGame game = MainGame.get();
        CraconMissile craconMissile = (CraconMissile) game.get(CraconMissile.class);
        if (craconMissile == null) {
            return new CraconMissile(x, y, speed);
        }
        craconMissile.init(x, y, speed);
        return craconMissile;
    }

    private void init(float x, float y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = -speed;
    }

    public void update(){
        MainGame game = MainGame.get();

        if (type == 0) {
            y -= speed * 2;
        }

        else if (type == 1) {
            if (x < 0)
                dir = 1;
            if (x > GameView.view.getWidth() - 100)
                dir = -1;

            x += 10 * dir;
            y -= speed;
        }

        else if (type == 2) {
            if (x < 0)
                dir = -1;
            if (x > GameView.view.getWidth() - 100)
                dir = 1;

            x -= 10 * dir;
            y -= speed;
        }

        if (y > GameView.view.getHeight()) {
            game.remove(this);
        }

    }
    public void draw(Canvas canvas){
        bitmap.draw(canvas, x, y);
    }

    @Override
    public void getBoundingRect(RectF rect) { bitmap.getBoundingRect(x, y, rect); }
}
