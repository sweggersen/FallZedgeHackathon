package hack.zedge.fall;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.*;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends Activity {

    private RelativeLayout mBackground;
    private int[] colors = { Color.parseColor("#F44336"), Color.parseColor("#E91E63"), Color.parseColor("#9C27B0") };
    private Random random;
    private int breakscreen[] = {R.mipmap.screen_broken_04, R.mipmap.screen_youbrokeme};

    MediaPlayer mp = null;
    MediaPlayer mp2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        mBackground = (RelativeLayout) findViewById(R.id.background);
        mBackground.setBackgroundDrawable(getResources().getDrawable(R.mipmap.screen_normal));

        mp = MediaPlayer.create(this, R.raw.wilhelm);
        mp2 = MediaPlayer.create(this, R.raw.breaking_glass);

        random = new Random();
        SensorManager manager = new SensorManager(this, new SensorManager.FallDetectedListener() {
            @Override
            public void fallDetected(boolean fall) {
                // Fall detected

                //Toast.makeText(MainActivity.this, "Fall detected", Toast.LENGTH_SHORT).show();

                mp.start();
            }

            @Override
            public void hitDetected(boolean fall) {
                //Toast.makeText(MainActivity.this, "Hit detected", Toast.LENGTH_SHORT).show();
                int thing = Math.round(random.nextFloat()*(breakscreen.length-1));
                int val = breakscreen[thing];
                mBackground = (RelativeLayout) findViewById(R.id.background);
                mBackground.setBackgroundDrawable(getResources().getDrawable(val));
                mp2.start();

            }
        });
    }
}
