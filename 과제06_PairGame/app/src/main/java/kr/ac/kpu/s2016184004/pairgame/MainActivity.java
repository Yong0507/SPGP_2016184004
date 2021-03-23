package kr.ac.kpu.s2016184004.pairgame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName(); // "MainActivity";
    private static final int[] buttonIds = {
            R.id.card_00, R.id.card_01, R.id.card_02, R.id.card_03,
            R.id.card_10, R.id.card_11, R.id.card_12, R.id.card_13,
            R.id.card_20, R.id.card_21, R.id.card_22, R.id.card_23,
            R.id.card_30, R.id.card_31, R.id.card_32, R.id.card_33,
            R.id.card_40, R.id.card_41, R.id.card_42, R.id.card_43,
    };

    private int[] cards = {
            R.mipmap.character1, R.mipmap.character1, R.mipmap.character2, R.mipmap.character2,
            R.mipmap.character3, R.mipmap.character3, R.mipmap.character4, R.mipmap.character4,
            R.mipmap.character5, R.mipmap.character5, R.mipmap.character6, R.mipmap.character6,
            R.mipmap.character7, R.mipmap.character7, R.mipmap.character8, R.mipmap.character8,
            R.mipmap.character9, R.mipmap.character9, R.mipmap.character10, R.mipmap.character10,
    };

    private ImageButton prevButton;
    private int visibleCardCount;
    private TextView scoreTextView;
    private int score;

    public void setScore(int score) {
        this.score = score;
        scoreTextView.setText("Flips: " + score);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreTextView = findViewById(R.id.scoreTextView);

        startGame();
    }

    public void onBtnCard(View view) {
        if(view == prevButton) { return; }         // 같은거 2개 누르면 return (1번만 실행)
        int prevCard = 0;
        if(prevButton != null ) {
            prevButton.setImageResource(R.mipmap.character_br);
            prevCard = (Integer) prevButton.getTag();  // object를 정수에 대입 안되기 떄문에 Integar 클래스로 타입 캐스팅
        }

        int buttonIndex = getButtonIndex(view.getId());
        Log.d(TAG,"onBtnCard() has been called. ID=" + view.getId() + " buttonIndex=" + buttonIndex);

        int card = cards[buttonIndex];
        ImageButton imageButton = (ImageButton)view;
        imageButton.setImageResource(card);

        if(card == prevCard) {
            imageButton.setVisibility(View.INVISIBLE);
            prevButton.setVisibility(View.INVISIBLE);
            prevButton = null;      // 게임 재시작 시 버그 방지를 위해
            visibleCardCount -= 2;
            if(visibleCardCount == 0) {
                askRestart();
            }
            return;
        }

        if(prevButton != null) {
            setScore(score + 1);
        }
        prevButton= imageButton;
    }

    private int getButtonIndex(int resId) {
        for(int i = 0; i < buttonIds.length; ++i) {
            if(buttonIds[i] == resId) {
                return i;
            }
        }
        return -1;
    }

    public void onBtnRestart(View view) {
        askRestart();
    }

    private void askRestart() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);   // context -> MainActivity
        builder.setTitle("Restart");
        builder.setMessage("Do you really want to restart the game?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                 startGame();
            }
        });
        builder.setNegativeButton("No", null);
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void startGame() {
        Random random = new Random();
        for(int i = 0; i < cards.length; i++) {
            int ri = random.nextInt(cards.length);
            int t = cards[i];
            cards[i] = cards[ri];
            cards[ri] = t;
        }

        for(int i = 0; i < buttonIds.length; i++) {
            ImageButton b = findViewById(buttonIds[i]) ;
            b.setTag(cards[i]);                                      // Tag - 모든 object에 사용 가능
            b.setVisibility(View.VISIBLE);
            b.setImageResource(R.mipmap.character_br);
        }

        prevButton = null;
        visibleCardCount = cards.length;

        setScore(0);
    }
}