package kr.ac.kpu.s2016184004.term_project.game;

import android.graphics.Canvas;
import android.graphics.RectF;

import java.util.Random;

import kr.ac.kpu.s2016184004.term_project.R;
import kr.ac.kpu.s2016184004.term_project.framework.BoxCollidable;
import kr.ac.kpu.s2016184004.term_project.framework.GameBitmap;
import kr.ac.kpu.s2016184004.term_project.framework.GameObject;
import kr.ac.kpu.s2016184004.term_project.framework.Recyclable;
import kr.ac.kpu.s2016184004.term_project.ui.view.GameView;

public class Item implements GameObject, BoxCollidable, Recyclable {

    private static final int[] RESOURCE_IDS = {
            // temp image
            R.mipmap.heart, R.mipmap.doublescore, R.mipmap.shield
    };

    private int type;
    private float x,y;
    private int speed;
    private GameBitmap bitmap;

    public static Item get(int type, float x, float y, int speed) {
        MainGame game = MainGame.get();
        Item item = (Item) game.get(Item.class);

        if (item == null) {
            return new Item(type, x, y, speed);
        }

        item.init(type, x, y, speed);
        return item;
    }

    public Item(int type, float x, float y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.type = type;

        int resId = RESOURCE_IDS[type - 1];
        this.bitmap = new GameBitmap(resId);

        Random r = new Random();
        int rand_num = r.nextInt(5) + 1;
    }

    private void init(int type, float x, float y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = -speed;
        this.type = type;

        int resId = RESOURCE_IDS[type - 1];
        this.bitmap = new GameBitmap(resId);
    }

    public int getItemtype() { return type - 1; }

    @Override
    public void update() {
        MainGame game = MainGame.get();

        y += speed * game.frameTime;

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

    }

}
