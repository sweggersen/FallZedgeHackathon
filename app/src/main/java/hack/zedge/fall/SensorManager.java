package hack.zedge.fall;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import java.text.MessageFormat;

public class SensorManager implements SensorEventListener {

    private android.hardware.SensorManager mSensorManager;
    private FallDetectedListener mFallDetectionListener;
    private boolean felt = false;

    public static final int SENSOR_PULL_FREQ = 40000; // in Hz

    private long lastFall = 0;
    private long lastHit = 0;
    private long threshold = 0;
    public SensorManager(Context context, FallDetectedListener listener) {
        mFallDetectionListener = listener;

        mSensorManager = (android.hardware.SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION), SENSOR_PULL_FREQ);
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(final SensorEvent event) {
        if (event.sensor.getType()==Sensor.TYPE_LINEAR_ACCELERATION) {
            double x = event.values[0];
            double y = event.values[1];
            double z = event.values[2];
            double a = Math.round(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2)));

            // System.out.println(MessageFormat.format("[{0}, {1}, {2}] : {3}", x, y, z, a));
            if ( a < 10.5 && a > 8.75 && (lastFall + 2000) < System.currentTimeMillis()) {
                threshold++;
                if (threshold > 10) {
                    threshold = 0;
                    lastFall = System.currentTimeMillis();
                    felt = true;
                    mFallDetectionListener.fallDetected(true);
                }
            }

            if ( a < 2 ) {
                threshold =0;
            }

            if ( a < 5 && felt == true && (lastHit + 2000) < System.currentTimeMillis()) {
                lastHit = System.currentTimeMillis();
                felt = false;
                mFallDetectionListener.hitDetected(true);
            }
        }
    }

    public interface FallDetectedListener {
        void fallDetected(boolean fall);
        void hitDetected(boolean fall);
    }
}