package kr.ac.kpu.s2016184004.term_project.game;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import kr.ac.kpu.s2016184004.term_project.R;
import kr.ac.kpu.s2016184004.term_project.framework.AnimationGameBitmap;
import kr.ac.kpu.s2016184004.term_project.framework.BoxCollidable;
import kr.ac.kpu.s2016184004.term_project.framework.GameBitmap;
import kr.ac.kpu.s2016184004.term_project.framework.GameObject;
import kr.ac.kpu.s2016184004.term_project.ui.view.GameView;

public class Boss implements GameObject, BoxCollidable {
    private static final String TAG = Boss.class.getSimpleName();
    private static final int BULLET_SPEED = 1500;
    private static final float FIRE_INTERVAL = 1.0f / 7.5f;
    private static final float LASER_DURATION = FIRE_INTERVAL / 3;
    private float fireTime;
    private float x, y;
    private float speed;
    private GameBitmap planeBitmap;
    private GameBitmap fireBitmap;

    private Boss() {
        Log.d(TAG, "Boss constructor");
    }

    public static Boss get(int x, int y, int speed) {
        MainGame game = MainGame.get();
        Boss boss = (Boss) game.get(Boss.class);
        if (boss == null) {
            boss = new Boss();
        }

        boss.init(x, y, speed);
        return boss;
    }

    private void init(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;

        this.planeBitmap = new GameBitmap(R.mipmap.boss);
        this.fireBitmap = new GameBitmap(R.mipmap.laser_0);
    }


//    public Boss(float x, float y) {
//        this.x = x;
//        this.y = y;
//        this.speed = 800;
//        this.planeBitmap = new GameBitmap(R.mipmap.boss);
//        this.fireBitmap = new GameBitmap(R.mipmap.laser_0);
//        this.fireTime = 0.0f;
//    }

    public void update() {
        MainGame game = MainGame.get();

        y += speed * game.frameTime;

        if (y > GameView.view.getHeight()) {
            game.remove(this);
        }



        fireTime += game.frameTime;
        if (fireTime >= FIRE_INTERVAL) {
            fireBullet();
            fireTime -= FIRE_INTERVAL;
        }
    }

    private void fireBullet() {
        BossBullet bossbullet = BossBullet.get(this.x, this.y , BULLET_SPEED);
        MainGame game = MainGame.get();
        game.add(MainGame.Layer.bossbullet, bossbullet);
    }

    public void draw(Canvas canvas) {
        planeBitmap.draw(canvas, x, y);
        if (fireTime < LASER_DURATION) {
            fireBitmap.draw(canvas, x, y - 50);
        }
    }

    @Override
    public void getBoundingRect(RectF rect) {
        planeBitmap.getBoundingRect(x, y, rect);
    }
}
