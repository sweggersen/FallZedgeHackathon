package hack.zedge.fall;

import android.graphics.Color;
import android.hardware.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout mBackground;
    private int[] colors = { Color.parseColor("#F44336"), Color.parseColor("#E91E63"), Color.parseColor("#9C27B0") };
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBackground = (RelativeLayout) findViewById(R.id.background);

        random = new Random();
        SensorManager manager = new SensorManager(this, new SensorManager.FallDetectedListener() {
            @Override
            public void fallDetected(boolean fall) {
                // Fall detected

                Toast.makeText(MainActivity.this, "Fall detected", Toast.LENGTH_SHORT).show();
                mBackground.setBackgroundColor(colors[random.nextInt(colors.length)]);
            }
        });
    }
}
