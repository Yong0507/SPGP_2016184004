package kr.ac.kpu.s2016184004.term_project.game;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import java.util.Random;

import kr.ac.kpu.s2016184004.term_project.R;
import kr.ac.kpu.s2016184004.term_project.framework.AnimationGameBitmap;
import kr.ac.kpu.s2016184004.term_project.framework.BoxCollidable;
import kr.ac.kpu.s2016184004.term_project.framework.GameBitmap;
import kr.ac.kpu.s2016184004.term_project.framework.GameObject;
import kr.ac.kpu.s2016184004.term_project.ui.view.GameView;

public class Boss implements GameObject, BoxCollidable {
    private static final String TAG = Boss.class.getSimpleName();
    private static final int BULLET_SPEED = 1000;
    private static final float FIRE_INTERVAL = 1.5f;
    private static final float LASER_DURATION = FIRE_INTERVAL / 3;


    private float fireTime;
    private float x, y;
    private float speed;
    private GameBitmap planeBitmap;

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
        int tenth = GameView.view.getWidth() / 10;
        Random r = new Random();
        int rand_x1 = 2 * r.nextInt(5) + 1;   // 1~9 홀수 중 1개를 뽑아낸다
        int rand_x2 = ( rand_x1 + 2 ) % 10;
        int rand_x3 = ( rand_x1 + 4 ) % 10;
        int rand_x4 = ( rand_x1 + 6 ) % 10;

        BossBullet bossbullet1 = BossBullet.get(tenth * rand_x1, 200 , BULLET_SPEED);
        BossBullet bossbullet2 = BossBullet.get(tenth * rand_x2, 200 , BULLET_SPEED);
        BossBullet bossbullet3 = BossBullet.get(tenth * rand_x3, 200 , BULLET_SPEED);
        BossBullet bossbullet4 = BossBullet.get(tenth * rand_x4, 200 , BULLET_SPEED);

        MainGame game = MainGame.get();

        game.add(MainGame.Layer.bossbullet, bossbullet1);
        game.add(MainGame.Layer.bossbullet, bossbullet2);
        game.add(MainGame.Layer.bossbullet, bossbullet3);
        game.add(MainGame.Layer.bossbullet, bossbullet4);
    }

    public void draw(Canvas canvas) {
        planeBitmap.draw(canvas, x, y);
    }

    @Override
    public void getBoundingRect(RectF rect) {
        planeBitmap.getBoundingRect(x, y, rect);
    }
}
