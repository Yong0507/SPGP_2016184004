package kr.ac.kpu.s2016184004.term_project.game;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import kr.ac.kpu.s2016184004.term_project.R;
import kr.ac.kpu.s2016184004.term_project.framework.AnimationGameBitmap;
import kr.ac.kpu.s2016184004.term_project.framework.BoxCollidable;
import kr.ac.kpu.s2016184004.term_project.framework.GameBitmap;
import kr.ac.kpu.s2016184004.term_project.framework.GameObject;
import kr.ac.kpu.s2016184004.term_project.framework.Recyclable;
import kr.ac.kpu.s2016184004.term_project.ui.view.GameView;

public class Boss implements GameObject, BoxCollidable, Recyclable {
    private static final float FRAMES_PER_SECOND = 8.0f;
    private static final int[] RESOURCE_IDS = {
            R.mipmap.boss
    };
    private static final String TAG = Boss.class.getSimpleName();
    private float x;
    private GameBitmap bitmap;
    private int level;
    private float y;
    private int type;
    // 보스 패턴
    private int coolTime;
    // 보스 총알
    private static final int BULLET_SPEED = 1500;
    private static final float FIRE_INTERVAL = 1.0f / 7.5f;
    private static final float LASER_DURATION = FIRE_INTERVAL / 3;
    private float fireTime;

    private Boss() {
        Log.d(TAG, "Boss constructor");
    }

    public static Boss get(int level, int x, int y, int speed) {
        MainGame game = MainGame.get();
        Boss boss = (Boss) game.get(Boss.class);
        if (boss == null) {
            boss = new Boss();
        }

        boss.init(level, x, y, speed);
        return boss;
    }

    private void init(int level, int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.level = level;

        int resId = RESOURCE_IDS[level - 1];

        this.bitmap = new AnimationGameBitmap(resId, FRAMES_PER_SECOND, 0);
    }

    @Override
    public void update() {
        MainGame game = MainGame.get();
        // Boss의 움직임 패턴

        x += 5 * game.frameTime;

        if (x > GameView.view.getWidth()) {
            x -= 5 * game.frameTime;
        }

        // 총알
        fireTime += game.frameTime;
        if (fireTime >= FIRE_INTERVAL) {
            fireBullet();
            fireTime -= FIRE_INTERVAL;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        bitmap.draw(canvas, x, y);
    }

    @Override
    public void getBoundingRect(RectF rect) {
        bitmap.getBoundingRect(x, y, rect);
    }

    @Override
    public void recycle() {
        // 재활용통에 들어가는 시점에 불리는 함수. 현재는 할일없음.
    }

    private void fireBullet() {
        BossBullet bullet = BossBullet.get(this.x, this.y, BULLET_SPEED);
        MainGame game = MainGame.get();
        game.add(MainGame.Layer.bossbullet, bullet);
    }
}