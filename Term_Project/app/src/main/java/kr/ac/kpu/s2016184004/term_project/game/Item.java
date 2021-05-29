package kr.ac.kpu.s2016184004.term_project.game;

import android.graphics.Canvas;
import android.graphics.RectF;

import kr.ac.kpu.s2016184004.term_project.R;
import kr.ac.kpu.s2016184004.term_project.framework.BoxCollidable;
import kr.ac.kpu.s2016184004.term_project.framework.GameBitmap;
import kr.ac.kpu.s2016184004.term_project.framework.GameObject;
import kr.ac.kpu.s2016184004.term_project.framework.Recyclable;
import kr.ac.kpu.s2016184004.term_project.ui.view.GameView;

public class Item implements GameObject, BoxCollidable, Recyclable {

    private static final int[] RESOURCE_IDS = {
            // temp image
            R.mipmap.heart, R.mipmap.effect,
    };

    private int level;
    private float x,y;
    private int speed;
    private GameBitmap bitmap;

    public static Item get(int level, float x, float y, int speed) {
        MainGame game = MainGame.get();
        Item item = (Item) game.get(Item.class);

        if (item == null) {
            return new Item(level, x, y, speed);
        }

        item.init(level, x, y, speed);
        return item;
    }

    private Item(int level, float x, float y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.level = level;

        int resId = RESOURCE_IDS[level - 1];
        this.bitmap = new GameBitmap(resId);
    }

    private void init(int lev, float x, float y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = -speed;
        this.level = lev;

        // 0: coin, 1:DualShot, 2:bullet Level, 3: Life
        int resId = RESOURCE_IDS[level - 1];
        this.bitmap = new GameBitmap(resId);
    }

    public int getLevel() {
        return level - 1;
    }

    @Override
    public void update() {
        MainGame game = MainGame.get();
        y -= speed * game.frameTime;

        if (y > GameView.view.getHeight() || y < 0
                || x > GameView.view.getWidth() || x < 0) {
            game.remove(this);
        }
    }

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
