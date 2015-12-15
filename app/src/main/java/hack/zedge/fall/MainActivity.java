package hack.zedge.fall;

import android.hardware.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SensorManager manager = new SensorManager(this, new SensorManager.FallDetectedListener() {
            @Override
            public void fallDetected(boolean fall) {
                Toast.makeText(MainActivity.this, "Fall detected", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
