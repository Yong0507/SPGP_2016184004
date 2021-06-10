package kr.ac.kpu.s2016184004.term_project.framework;

import android.content.Context;
import android.media.MediaPlayer;
import kr.ac.kpu.s2016184004.term_project.R;

public class BGSound {
    private Context context;
    private MediaPlayer mp;

    public static BGSound get(){
        if(singleton == null){
            singleton = new BGSound();
        }
        return singleton;
    }
    private static BGSound singleton;

    public void init(Context context){
        this.context = context;
        //mp = MediaPlayer.create(this.context, R.raw.bgsound);
        mp.setVolume(1.f, 1.f);
    }

    public void playBGM() {
        mp.setLooping(true);
        mp.start();
    }

    public void stop() {
        mp.stop();
    }
}