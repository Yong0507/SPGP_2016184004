package kr.ac.kpu.s2016184004.term_project.game;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.HashMap;

import kr.ac.kpu.s2016184004.term_project.R;
import kr.ac.kpu.s2016184004.term_project.framework.GameObject;
import kr.ac.kpu.s2016184004.term_project.framework.Recyclable;
import kr.ac.kpu.s2016184004.term_project.ui.view.GameView;
import kr.ac.kpu.s2016184004.term_project.utils.CollisionHelper;

public class MainGame {
    private static final String TAG = MainGame.class.getSimpleName();
    // singleton
    private static MainGame instance;
    private Player player;
    private Score score;

    //
    private EnemyGenerator enemyGenerator;

    public static MainGame get() {
        if (instance == null) {
            instance = new MainGame();
        }
        return instance;
    }

    public float frameTime;
    private boolean initialized;

    //    Player player;
    ArrayList<ArrayList<GameObject>> layers;
    private static HashMap<Class, ArrayList<GameObject>> recycleBin = new HashMap<>();

    public void recycle(GameObject object) {
        Class clazz = object.getClass();
        ArrayList<GameObject> array = recycleBin.get(clazz);
        if (array == null) {
            array = new ArrayList<>();
            recycleBin.put(clazz, array);
        }
        array.add(object);
    }

    public GameObject get(Class clazz) {
        ArrayList<GameObject> array = recycleBin.get(clazz);
        if (array == null || array.isEmpty()) return null;
        return array.remove(0);
    }

    public enum Layer {
        bg1, enemy, bossbullet, boss, bullet, player, bg2, ui, controller, ENEMY_COUNT
    }

    public boolean initResources() {
        if (initialized) {
            return false;
        }
        int w = GameView.view.getWidth();
        int h = GameView.view.getHeight();

        initLayers(Layer.ENEMY_COUNT.ordinal());

        player = new Player(w / 2, h - 300);
        add(Layer.player, player);

        add(Layer.controller, new EnemyGenerator());

        int margin = (int) (20 * GameView.MULTIPLIER);
        score = new Score(w - margin, margin);
        score.setScore(0);
        add(Layer.ui, score);

        VerticalScrollBackground bg = new VerticalScrollBackground(R.mipmap.bg_grass, 10);
        add(Layer.bg1, bg);

        initialized = true;
        return true;
    }

    private void initLayers(int layerCount) {
        layers = new ArrayList<>();
        for (int i = 0; i < layerCount; i++) {
            layers.add(new ArrayList<>());
        }
    }

    public void update() {
        for (ArrayList<GameObject> objects : layers) {
            for (GameObject o : objects) {
                o.update();
            }
        }

        ArrayList<GameObject> enemies = layers.get(Layer.enemy.ordinal());
        ArrayList<GameObject> bullets = layers.get(Layer.bullet.ordinal());

        for (GameObject o1 : enemies) {
            Enemy enemy = (Enemy) o1;
            boolean collided = false;
            for (GameObject o2 : bullets) {
                Bullet bullet = (Bullet) o2;
                if (CollisionHelper.collides(enemy, bullet)) {
                    remove(bullet, false);
                    remove(enemy, false);
                    score.addScore(10);
                    collided = true;
                    break;
                }
            }
            if (collided) {
                break;
            }
        }

//        ArrayList<GameObject> enemies = layers.get(Layer.enemy.ordinal());
//        ArrayList<GameObject> bosses = layers.get(Layer.boss.ordinal());
//
//        ArrayList<GameObject> player_bullets = layers.get(Layer.bullet.ordinal());
//
//        for (GameObject o1 : enemies) {
//            Enemy enemy = (Enemy) o1;
//            boolean collided = false;
//
//            for(GameObject o3 : bosses) {
//                Boss boss = (Boss) o3;
//                boolean b_collided = false;
//            }
//
//            for (GameObject o2 : player_bullets) {
//                Bullet bullet = (Bullet) o2;
//                if (CollisionHelper.collides(enemy, bullet)) {
//                    remove(bullet, false);
//                    remove(enemy, false);
//                    score.addScore(10);
//                    collided = true;
//                    break;
//                }
//
//                if(CollisionHelper.collides(boss, bullet))
//                {
//                    remove(bullet, false);
//                    remove(boss, false);
//                    score.addScore(3000);
//                    b_collided = true;
//                    break;
//                }
//            }
//
//            if (collided) {
//                break;
//            }
//        }




    }

    public void draw(Canvas canvas) {
        //if (!initialized) return;
        for (ArrayList<GameObject> objects : layers) {
            for (GameObject o : objects) {
                o.draw(canvas);
            }
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
            player.moveTo(event.getX(), event.getY());
            return true;
        }
        return false;
    }

    public void add(Layer layer, GameObject gameObject) {
        GameView.view.post(new Runnable() {
            @Override
            public void run() {
                ArrayList<GameObject> objects = layers.get(layer.ordinal());
                objects.add(gameObject);
            }
        });
//        Log.d(TAG, "<A> object count = " + objects.size());
    }

    public void remove(GameObject gameObject) {
        remove(gameObject, true);
    }

    public void remove(GameObject gameObject, boolean delayed) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (ArrayList<GameObject> objects : layers) {
                    boolean removed = objects.remove(gameObject);
                    if (removed) {
                        if (gameObject instanceof Recyclable) {
                            ((Recyclable) gameObject).recycle();
                            recycle(gameObject);
                        }
                        //Log.d(TAG, "Removed: " + gameObject);
                        break;
                    }
                }
            }
        };
        if (delayed) {
            GameView.view.post(runnable);
        } else {
            runnable.run();
        }
    }
}