package kr.ac.kpu.s2016184004.term_project.game;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import java.util.Random;

import kr.ac.kpu.s2016184004.term_project.R;
import kr.ac.kpu.s2016184004.term_project.framework.BoxCollidable;
import kr.ac.kpu.s2016184004.term_project.framework.GameBitmap;
import kr.ac.kpu.s2016184004.term_project.framework.GameObject;
import kr.ac.kpu.s2016184004.term_project.ui.view.GameView;

public class Cracon implements GameObject, BoxCollidable {
    private static final String TAG = Cracon.class.getSimpleName();
    private static final float FIRE_INTERVAL = 1.0f;

    private float fireTime;
    private float x, y;
    private float speed;
    private GameBitmap planeBitmap;
    private int missile_speed;

    private Cracon() {
        Log.d(TAG, "Cracon constructor");
    }

    public static Cracon get(int x, int y, int speed) {
        MainGame game = MainGame.get();
        Cracon cracon = (Cracon) game.get(Cracon.class);
        if (cracon == null) {
            cracon = new Cracon();
        }

        cracon.init(x, y, speed);
        return cracon;
    }

    private void init(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;

        this.planeBitmap = new GameBitmap(R.mipmap.boss_2);
    }

    public void update() {
        MainGame game = MainGame.get();

        x += speed * game.frameTime;

        if(x > GameView.view.getWidth() - 200 )
            speed = -speed;

        else if (x < 200)
            speed = -speed;

        fireTime += game.frameTime;
        if (fireTime >= FIRE_INTERVAL) {
            fireBullet();
            fireTime -= FIRE_INTERVAL;
        }
    }

    private void fireBullet() {

        MainGame game = MainGame.get();

        Random r = new Random();

        int rand_speed = r.nextInt(3);

        if(rand_speed == 0) missile_speed = 10;
        if(rand_speed == 1) missile_speed = 15;
        if(rand_speed == 2) missile_speed = 25;

        CraconMissile craconmissile = CraconMissile.get(x,y,missile_speed);
        game.add(MainGame.Layer.craconmissile, craconmissile);

    }

    public void draw(Canvas canvas) {
        planeBitmap.draw(canvas, x, y);
    }

    @Override
    public void getBoundingRect(RectF rect) {
        planeBitmap.getBoundingRect(x, y, rect);
    }
}
