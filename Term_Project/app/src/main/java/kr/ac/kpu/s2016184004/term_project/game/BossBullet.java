package kr.ac.kpu.s2016184004.term_project.game;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import kr.ac.kpu.s2016184004.term_project.R;
import kr.ac.kpu.s2016184004.term_project.framework.BoxCollidable;
import kr.ac.kpu.s2016184004.term_project.framework.GameBitmap;
import kr.ac.kpu.s2016184004.term_project.framework.GameObject;
import kr.ac.kpu.s2016184004.term_project.framework.Recyclable;

public class BossBullet implements GameObject, BoxCollidable, Recyclable {
    private static final String TAG = BossBullet.class.getSimpleName();
    private float x;
    private final GameBitmap bitmap;
    private float y;
    private int speed;

    private BossBullet(float x, float y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = -speed;

        Log.d(TAG, "loading bitmap for Boss bullet");
        this.bitmap = new GameBitmap(R.mipmap.boss_bullet);
    }

    //    private static ArrayList<Bullet> recycleBin = new ArrayList<>();
    public static BossBullet get(float x, float y, int speed) {
        MainGame game = MainGame.get();
        BossBullet Bossbullet = (BossBullet) game.get(BossBullet.class);
        if (Bossbullet == null) {
            return new BossBullet(x, y, speed);
        }
        Bossbullet.init(x, y, speed);
        return Bossbullet;
    }

    private void init(float x, float y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = -speed;
    }

    @Override
    public void update() {
        MainGame game = MainGame.get();
        y += speed * game.frameTime;

        if (y < 0) {
            game.remove(this);
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
}
