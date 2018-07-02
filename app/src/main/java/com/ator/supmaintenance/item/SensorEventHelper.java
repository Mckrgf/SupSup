package com.ator.supmaintenance.item;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;

import com.amap.api.maps.model.Marker;

public class SensorEventHelper implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensor1;
    private Sensor mSensor2;
    private long lastTime = 0;
    private final int TIME_SENSOR = 100;
    private float mAngle;
    private Context mContext;
    private Marker mMarker;
    private float mlastOutput;

    private float[] values, r, gravity, geomagnetic;

    public SensorEventHelper(Context context) {
        mContext = context;
        mSensorManager = (SensorManager) context
                .getSystemService(Context.SENSOR_SERVICE);
        mSensor1 = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mSensor2 = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //初始化数组
        values = new float[3];//用来保存最终的结果
        gravity = new float[3];//用来保存加速度传感器的值
        r = new float[9];//
        geomagnetic = new float[3];//用来保存地磁传感器的值

    }

    public void registerSensorListener() {
        mSensorManager.registerListener(this, mSensor1,
                SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mSensor2,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unRegisterSensorListener() {
        mSensorManager.unregisterListener(this, mSensor1);
        mSensorManager.unregisterListener(this, mSensor2);
    }

    public void setCurrentMarker(Marker marker) {
        mMarker = marker;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (System.currentTimeMillis() - lastTime < TIME_SENSOR) {
            return;
        }
        switch (event.sensor.getType()) {
            case Sensor.TYPE_MAGNETIC_FIELD: {
                geomagnetic = event.values.clone();
            }
            break;
            case Sensor.TYPE_ACCELEROMETER: {
                gravity = event.values.clone();
                getValue();
            }
            break;
        }
    }

        public void getValue() {
                // r从这里返回
                SensorManager.getRotationMatrix(r, null, gravity, geomagnetic);
                //values从这里返回
                SensorManager.getOrientation(r, values);
                //提取数据
                mAngle = (float) Math.toDegrees(values[0]);
                if (mAngle<0) {
                    mAngle=mAngle+360;
                }
                double pitch = Math.toDegrees(values[1]);
                double roll = Math.toDegrees(values[2]);

                float x = mAngle;
                x += getScreenRotationOnPhone(mContext);
                x %= 360.0F;
                if (x > 180.0F)
                    x -= 360.0F;
                else if (x < -180.0F)
                    x += 360.0F;

                if (Math.abs(mAngle - mlastOutput) < 1.0f) {
                    Log.i("marker angle fail:"," "+ mAngle + "mlastOutput = "+ mlastOutput);
                    return;
                }
                mAngle = Float.isNaN(x) ? 0 : x;
                if (mMarker != null) {
                    mMarker.setRotateAngle((360- mAngle)%360);
                    mlastOutput = mAngle;
                    Log.i("marker angle ok:"," "+ (360 - mAngle)%360);
                }
                lastTime = System.currentTimeMillis();

            }



    /**
     * 获取当前屏幕旋转角度
     *
     * @param context
     * @return 0表示是竖屏; 90表示是左横屏; 180表示是反向竖屏; 270表示是右横屏
     */
    public static int getScreenRotationOnPhone(Context context) {
        final Display display = ((WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        switch (display.getRotation()) {
            case Surface.ROTATION_0:
                return 0;

            case Surface.ROTATION_90:
                return 90;

            case Surface.ROTATION_180:
                return 180;

            case Surface.ROTATION_270:
                return -90;
        }
        return 0;
    }
}
