package hu.daniels.servicetestapplication;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;

public class SensorDataRecorderService extends Service implements SensorEventListener {

    private static final int SAMPLE_PER_SECOND = 1;
    private static final double SAMPLE_DELAY = 1000000000 / SAMPLE_PER_SECOND;
    private static final int SENSOR_TYPE = Sensor.TYPE_LINEAR_ACCELERATION;

    private SensorManager sensorManager;
    private Sensor sensor;

    private long firstSensorEventTime;
    private int counter;

    @Override
    public void onCreate() {
        super.onCreate();
        counter = 0;
        setupSensor();
    }

    private void setupSensor() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(SENSOR_TYPE);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder(counter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == SENSOR_TYPE && event.timestamp >= firstSensorEventTime + counter * SAMPLE_DELAY) {
            counter++;
        }
        if (firstSensorEventTime == 0) {
            firstSensorEventTime = event.timestamp;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    private static class MyBinder extends Binder {
        private final int count;

        public MyBinder(int count) {
            this.count = count;
        }

        public int getCount() {
            return count;
        }
    }

}
