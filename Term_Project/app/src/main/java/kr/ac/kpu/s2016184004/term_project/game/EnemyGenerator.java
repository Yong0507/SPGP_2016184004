package kr.ac.kpu.s2016184004.term_project.game;

import android.graphics.Canvas;

import java.util.Random;

import kr.ac.kpu.s2016184004.term_project.framework.GameObject;
import kr.ac.kpu.s2016184004.term_project.ui.view.GameView;

public class EnemyGenerator implements GameObject {

    private static final float INITIAL_SPAWN_INTERVAL = 2.0f;
    private static final String TAG = EnemyGenerator.class.getSimpleName();
    private float time;
    private float spawnInterval;
    public int wave;

    // 보스 생성 시점 판단
    private boolean Is_boss;
    public void setIsBoss(boolean is_boss) { Is_boss = is_boss; }
    public boolean getIsBoss() { return Is_boss; }

    public EnemyGenerator() {
        time = INITIAL_SPAWN_INTERVAL;
        spawnInterval = INITIAL_SPAWN_INTERVAL;
        wave = 0;
    }
    @Override
    public void update() {
        MainGame game = MainGame.get();
        time += game.frameTime;
        if (time >= spawnInterval) {
            generate();
            time -= spawnInterval;
        }
    }

    private void generate() {
        wave++;
        //Log.d(TAG, "Generate now !!");
        MainGame game = MainGame.get();
        int tenth = GameView.view.getWidth() / 10;
        Random r = new Random();
        for (int i = 1; i <= 9; i += 2) {
            int x = tenth * i;
            int y = 0;

            if( i <= 5 )
                y = i * 70;
            if ( i > 5 )
                y = 700 - i * 70;

            int level = wave / 10 - r.nextInt(3);
            if (level < 1) level = 1;
            if (level > 4) level = 4;
            Enemy enemy = Enemy.get(level, x, y, 700);
            game.add(MainGame.Layer.enemy, enemy);
        }

        // Boss 생성되는 시점
        if(wave == 10)
        {
            int x = GameView.view.getWidth() / 2;
            int y = 200;
            Boss boss = Boss.get(x, y, 300);
            game.add(MainGame.Layer.boss, boss);
        }

        if(wave > 25 && game.getBoss1Dead() == true)
        {
            int x = GameView.view.getWidth() / 2;
            int y = 200;
            Cracon cracon = Cracon.get(x, y, 300);
            game.add(MainGame.Layer.cracon, cracon);
            game.setBoss1Dead(false);
        }


    }

    @Override
    public void draw(Canvas canvas) {
        // does nothing
    }
}