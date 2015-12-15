package hack.zedge.fall;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;

import android.view.WindowManager;
import android.widget.RelativeLayout;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends Activity {

    private RelativeLayout mBackground;
    private int[] colors = { Color.parseColor("#F44336"), Color.parseColor("#E91E63"), Color.parseColor("#9C27B0") };
    private Random random;
    private int breakscreen[] = {R.mipmap.screen_broken_01, R.mipmap.screen_broken_02, R.mipmap.screen_broken_03, R.mipmap.screen_broken_04};

    private int[] screamsR = {R.raw.wilhelm, R.raw.cat_scream, R.raw.doh, R.raw.ahh};
    private int[] hitsR = {R.raw.breaking_glass};//, };

    ArrayList<MediaPlayer> screams = null;
    ArrayList<MediaPlayer> hits = null;


    @Override
    public boolean onTouchEvent(MotionEvent me) {
        mBackground.setBackgroundDrawable(getResources().getDrawable(R.mipmap.screen_normal));
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        screams = new ArrayList<>();
        hits = new ArrayList<>();

        for (int i = 0; i < screamsR.length; i++) {
            MediaPlayer mp;
            mp = MediaPlayer.create(this, screamsR[i]);
            screams.add(mp);
        }

        for (int i = 0; i < hitsR.length; i++) {
            MediaPlayer mp;
            mp = MediaPlayer.create(this, hitsR[i]);
            hits.add(mp);
        }

        AudioManager mgr = (AudioManager)getBaseContext().getSystemService(Context.AUDIO_SERVICE);

        mgr.setStreamVolume(AudioManager.STREAM_MUSIC,100,0);

        screams.add(new MediaPlayer());

        mBackground = (RelativeLayout) findViewById(R.id.background);
        mBackground.setBackgroundDrawable(getResources().getDrawable(R.mipmap.screen_normal));


        random = new Random();
        SensorManager manager = new SensorManager(this, new SensorManager.FallDetectedListener() {
            @Override
            public void fallDetected(boolean fall) {
                // Fall detected

                //Toast.makeText(MainActivity.this, "Fall detected", Toast.LENGTH_SHORT).show();
                int thing = Math.round(random.nextFloat()*(screams.size()-1));
                MediaPlayer mp = screams.get(thing);
                mp.start();
            }

            @Override
            public void hitDetected(boolean fall) {
                //Toast.makeText(MainActivity.this, "Hit detected", Toast.LENGTH_SHORT).show();
                int thing = Math.round(random.nextFloat()*(breakscreen.length-1));
                int val = breakscreen[thing];
                mBackground = (RelativeLayout) findViewById(R.id.background);
                mBackground.setBackgroundDrawable(getResources().getDrawable(val));

                int thingy = Math.round(random.nextFloat()*(hits.size()-1));
                MediaPlayer mp2 = hits.get(thingy);

                mp2.start();

            }
        });
    }
}
