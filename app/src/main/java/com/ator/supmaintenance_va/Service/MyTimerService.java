package com.ator.supmaintenance_va.Service;


import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import com.ator.supmaintenance_va.act.InspectionMainActivity;
import com.ator.supmaintenance_va.item.InspectionUtil;
import com.ator.supmaintenance_va.item.RtEnv;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyTimerService extends Service {

    public static boolean mbFirst = true;
    public static Date mTagetTime = new Date(); //触发目标时间，准点

    public MyTimerService() {
    }

    private long   getTriggerTimer(){

        long dt = 1000;
        SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMddHH");

        Date tmnow = new Date();
        Calendar cnow = Calendar.getInstance();
        cnow.setTime(tmnow);
        int nowminute = cnow.get(Calendar.MINUTE);

        String tmnowstring = sdf.format(tmnow);
        tmnowstring +="0000";

        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
            Date tm1 = sdf1.parse(tmnowstring);

            Calendar c = Calendar.getInstance();
            c.setTime(tm1);
            c.add(Calendar.HOUR,+1);

            int hhh = c.get(Calendar.HOUR);
            int mmmm = c.get(Calendar.MINUTE);

            Date tagetTime = c.getTime();

            dt = tagetTime.getTime() - tmnow.getTime();

            mTagetTime = c.getTime();

        }catch (Exception e){
            e.printStackTrace();
        }

        return dt;

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private String getRunningActivityName(){
        ActivityManager activityManager=(ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        String runningActivity=activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
        return runningActivity;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("LongRunningService", "executed at " + new Date().
                        toString());

                InspectionUtil.getInstance().makeMyTasksbyPlan();   //生成taskid


                //若为巡检首页则刷新activity
                String actname = getRunningActivityName();
                if (actname.equals("com.ator.supmaintenance.act.InspectionMainActivity") ||
                        actname.equals("com.ator.supmaintenance.act.CardListActivity")){
                    RtEnv.startActivity(InspectionMainActivity.class);
                }

            }
        }).start();

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        int anHour = 60 * 60 * 1000; // 这是一小时的毫秒数
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;;

        if (mbFirst){
            triggerAtTime = SystemClock.elapsedRealtime() + + getTriggerTimer();
            mbFirst = false;
        }else { //每小时触发
            triggerAtTime = SystemClock.elapsedRealtime() + getTriggerTimer();
        }

        Intent i = new Intent(this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            manager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            manager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        }else{
            manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
        }
        return super.onStartCommand(intent, flags, startId);
    }

}
