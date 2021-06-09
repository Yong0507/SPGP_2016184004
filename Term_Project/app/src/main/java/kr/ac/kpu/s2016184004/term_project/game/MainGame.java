package kr.ac.kpu.s2016184004.term_project.game;

import android.graphics.Canvas;
import android.media.effect.Effect;
import android.util.Log;
import android.view.MotionEvent;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import kr.ac.kpu.s2016184004.term_project.R;
import kr.ac.kpu.s2016184004.term_project.framework.GameBitmap;
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
    private Heart heart;

    private Particle particle;
    private Item item;

    // 보스 몬스터 데미지
    private int HitbossCount;
    private int HitplayerCount;

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
        bg1, enemy, bossbullet, boss, bullet, player, particle,item, ui, heart, controller, ENEMY_COUNT
    }

    public boolean initResources() {
        if (initialized) {
            return false;
        }

        int w = GameView.view.getWidth();
        int h = GameView.view.getHeight();

        initLayers(Layer.ENEMY_COUNT.ordinal());

        player = new Player(w / 2, h - 200);
        add(Layer.player, player);

        add(Layer.controller, new EnemyGenerator());

        int margin = (int) (20 * GameView.MULTIPLIER);
        score = new Score(w - margin, margin);
        score.setScore(0);
        add(Layer.ui, score);

        heart = new Heart(100,60);
        add(Layer.heart, heart);

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

    // 무조건 아이템이 나오는 것이 아니라 랜덤한 확률로 아이템이 나오게 됨
    private int randRate()
    {
        Random r = new Random();
        int rand_num = r.nextInt(100);
        return rand_num;
    }

    public void update() {
        for (ArrayList<GameObject> objects : layers) {
            for (GameObject o : objects) {
                o.update();
            }
        }

        ArrayList<GameObject> enemies = layers.get(Layer.enemy.ordinal());
        ArrayList<GameObject> bosses = layers.get(Layer.boss.ordinal());
        ArrayList<GameObject> player_bullets = layers.get(Layer.bullet.ordinal());
        ArrayList<GameObject> boss_bullets = layers.get(Layer.bossbullet.ordinal());
        ArrayList<GameObject> players = layers.get(Layer.player.ordinal());
        ArrayList<GameObject> items = layers.get(Layer.item.ordinal());

        // 일반 몬스터 <-> 플레이어 총알 충돌처리
        for (GameObject o1 : enemies) {
            Enemy enemy = (Enemy) o1;
            boolean collided = false;
            for (GameObject o2 : player_bullets) {
                Bullet player_bullet = (Bullet) o2;
                if (CollisionHelper.collides(enemy, player_bullet)) {
                    remove(player_bullet, false);
                    remove(enemy, false);
                    score.addScore(10);

                    // 아이템 생성
                    randRate();

                    if(randRate() <= 10) {
                        item = new Item(1, enemy.getX(), enemy.getY(), 1000);
                        add(Layer.item, item);
                    }

                    else if( 10 < randRate() && randRate()<= 30) {
                        item = new Item(2,enemy.getX(),enemy.getY(), 1000);
                        add(Layer.item, item);
                    }

                    else if ( 30< randRate() && randRate() <= 40) {
                        item = new Item(3, enemy.getX(), enemy.getY(), 1000);
                        add(Layer.item, item);
                    }

                    collided = true;
                    break;
                }
            }
            if (collided) {
                break;
            }
        }

        // 플레이어 <-> 아이템 충돌처리
        for(GameObject o1 : items) {
            Item item = (Item) o1;
            boolean collided = false;
            for(GameObject o2 : players) {
                Player player = (Player) o2;
                if(CollisionHelper.collides(item, player)) {

                    // 1번 아이템 - 하트
                    if(item.getItemtype() == 0) {
                        if(heart.life_count < 5)
                            heart.life_count++;

                        remove(item, false);
                    }

                    // 2번 아이템 - 더블 스코어
                    if(item.getItemtype() == 1) {
                        // 더블 스코어
                        remove(item, false);

                        score.setDouble(2);
                    }

                    // 3번 아이템 - 무적
                    if(item.getItemtype() == 2) {
                        remove(item, false);
                    }

                    collided = true;
                    break;
                }
            }
            if (collided) {
                break;
            }
        }

        // 보스 몬스터 <-> 플레이어 총알 충돌처리
        for (GameObject o1 : bosses) {
            Boss boss = (Boss) o1;
            boolean collided = false;
            for (GameObject o2 : player_bullets) {
                Bullet player_bullet = (Bullet) o2;
                if (CollisionHelper.collides(boss, player_bullet)) {
                    remove(player_bullet, false);

                    HitbossCount++;
                    if(HitbossCount > 100) {
                        remove(boss, false);
                        score.addScore(100);
                        collided = true;
                        break;
                    }

                    collided = true;
                    break;
                }
            }
            if (collided) {
                break;
            }
        }

        // 플레이어 <-> 보스 몬스터 총알 충돌처리
        for (GameObject o1 : players) {
            Player player = (Player) o1;
            boolean collided = false;
            for(GameObject o2 : boss_bullets) {
                BossBullet boss_bullet = (BossBullet) o2;
                if (CollisionHelper.collides(player, boss_bullet)) {
                    remove(boss_bullet);

                    particle = new Particle((int)player.getX() - 120, (int)player.getY() - 180);
                    add(Layer.particle, particle);

                    HitplayerCount ++;
                    heart.life_count --;

                    if(heart.life_count == 0)
                        remove(player, false);

                    collided = true;
                    break;
                }
                if (collided) {
                    break;
                }
            }
        }

        // 플레이어 <-> 일반 몬스터 충돌처리
        for (GameObject o1 : players) {
            Player player = (Player) o1;
            boolean collided = false;
            for(GameObject o2 : enemies) {
                Enemy enemy = (Enemy) o2;
                if(CollisionHelper.collides(player, enemy)) {
                    remove(enemy, false);

                    particle = new Particle((int)player.getX() - 120, (int)player.getY() - 180);
                    add(Layer.particle, particle);

                    heart.life_count --;
                    if(heart.life_count == 0)
                        remove(player, false);

                    collided = true;
                    break;
                }
                if(collided){
                    break;
                }
            }
        }
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