package com.ator.supmaintenance_va.act;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ator.supmaintenance_va.R;
import com.ator.supmaintenance_va.act.tables.CabinetActivity;
import com.ator.supmaintenance_va.act.tables.CabinetCorrosionActivity;
import com.ator.supmaintenance_va.act.tables.ControlCabinetActivity;
import com.ator.supmaintenance_va.act.tables.ControlCabinetPowerActivity;
import com.ator.supmaintenance_va.act.tables.ControlRoomActivity;
import com.ator.supmaintenance_va.act.tables.ControlRoomPowerMagnetismActivity;
import com.ator.supmaintenance_va.act.tables.ControllerCheckActivity;
import com.ator.supmaintenance_va.act.tables.GroundCheckActivity;
import com.ator.supmaintenance_va.act.tables.MasterCardCheckActivity;
import com.ator.supmaintenance_va.act.tables.OpStationCheckActivity;
import com.ator.supmaintenance_va.act.tables.OperatingStationActivity;
import com.ator.supmaintenance_va.act.tables.OperatingStationCheckActivity;
import com.ator.supmaintenance_va.act.tables.OperatingStationCorrosionDetectionActivity;
import com.ator.supmaintenance_va.act.tables.OperatingStationPowerActivity;
import com.ator.supmaintenance_va.act.tables.PowerAllActivity;
import com.ator.supmaintenance_va.act.tables.PowerCheckActivity;
import com.ator.supmaintenance_va.act.tables.PowerSystemActivity;
import com.ator.supmaintenance_va.act.tables.RoomCorrosionDetectionActivity;
import com.ator.supmaintenance_va.act.tables.SBUSCheckActivity;
import com.ator.supmaintenance_va.act.tables.SCNETCheckActivity;
import com.ator.supmaintenance_va.act.tables.SysInfoActivity;
import com.ator.supmaintenance_va.act.tables.SysRunActivity;
import com.ator.supmaintenance_va.act.tables.SystemPowerCheckActivity;
import com.ator.supmaintenance_va.item.RtEnv;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.power_all_button).setOnClickListener(this);
        findViewById(R.id.power_cabinet_button).setOnClickListener(this);
        findViewById(R.id.ground_check_button).setOnClickListener(this);
        findViewById(R.id.run_check_button).setOnClickListener(this);
        findViewById(R.id.controller_check_button).setOnClickListener(this);
        findViewById(R.id.opstation_check_button).setOnClickListener(this);
        findViewById(R.id.system_info_button).setOnClickListener(this);
        findViewById(R.id.cabinet_corrosion_detection_button).setOnClickListener(this);
        findViewById(R.id.control_cabine_button).setOnClickListener(this);
        findViewById(R.id.control_cabine_power_button).setOnClickListener(this);
        findViewById(R.id.control_room_button).setOnClickListener(this);
        findViewById(R.id.control_room_power_magnetism_button).setOnClickListener(this);
        findViewById(R.id.master_card_check_button).setOnClickListener(this);
        findViewById(R.id.operating_station_button).setOnClickListener(this);
        findViewById(R.id.operating_station_check_button).setOnClickListener(this);
        findViewById(R.id.operating_station_corrosion_detection_button).setOnClickListener(this);
        findViewById(R.id.operating_station_power_button).setOnClickListener(this);
        findViewById(R.id.power_check_button).setOnClickListener(this);
        findViewById(R.id.power_system_button).setOnClickListener(this);
        findViewById(R.id.room_corrosion_detection_button).setOnClickListener(this);
        findViewById(R.id.sbus_check_button).setOnClickListener(this);
        findViewById(R.id.scnet_check_button).setOnClickListener(this);
        findViewById(R.id.system_power_check_button).setOnClickListener(this);


        initTop();

    }

    private void initTop(){

        findViewById(R.id.im_back).setOnClickListener(new  View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        TextView tv = (TextView) findViewById(R.id.tv_title);

        tv.setText("机柜间巡检");
    }


    @Override
    protected int initContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void getIntentData() {
        return;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void start() {

    }


    @Override
    protected void onPostSucc(){
    }

    @Override
    protected void onPostFaile(){
    }

    @Override
    protected void onGetSucc(){

    }

    @Override
    protected void onGetFaile(){

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.power_all_button:
                RtEnv.startActivity(PowerAllActivity.class);
                break;
            case R.id.power_cabinet_button:
                RtEnv.startActivity(CabinetActivity.class);
                break;
            case R.id.ground_check_button:
                RtEnv.startActivity(GroundCheckActivity.class);
                break;
            case R.id.run_check_button:
                RtEnv.startActivity(SysRunActivity.class);
                break;
            case R.id.controller_check_button:
                RtEnv.startActivity(ControllerCheckActivity.class);
                break;
            case R.id.opstation_check_button:
                RtEnv.startActivity(OpStationCheckActivity.class);
                break;
            case R.id.system_info_button:
                RtEnv.startActivity(SysInfoActivity.class);
                break;
            case R.id.cabinet_corrosion_detection_button:
                RtEnv.startActivity(CabinetCorrosionActivity.class);
                break;
            case R.id.control_cabine_button:
                RtEnv.startActivity(ControlCabinetActivity.class);
                break;
            case R.id.control_cabine_power_button:
                RtEnv.startActivity(ControlCabinetPowerActivity.class);
                break;
            case R.id.control_room_button:
                RtEnv.startActivity(ControlRoomActivity.class);
                break;
            case R.id.control_room_power_magnetism_button:
                RtEnv.startActivity(ControlRoomPowerMagnetismActivity.class);
                break;
            case R.id.master_card_check_button:
                RtEnv.startActivity(MasterCardCheckActivity.class);
                break;
            case R.id.operating_station_button:
                RtEnv.startActivity(OperatingStationActivity.class);
                break;
            case R.id.operating_station_check_button:
                RtEnv.startActivity(OperatingStationCheckActivity.class);
                break;
            case R.id.operating_station_corrosion_detection_button:
                RtEnv.startActivity(OperatingStationCorrosionDetectionActivity.class);
                break;
            case R.id.operating_station_power_button:
                RtEnv.startActivity(OperatingStationPowerActivity.class);
                break;
            case R.id.power_check_button:
                RtEnv.startActivity(PowerCheckActivity.class);
                break;
            case R.id.power_system_button:
                RtEnv.startActivity(PowerSystemActivity.class);
                break;
            case R.id.room_corrosion_detection_button:
                RtEnv.startActivity(RoomCorrosionDetectionActivity.class);
                break;
            case R.id.sbus_check_button:
                RtEnv.startActivity(SBUSCheckActivity.class);
                break;
            case R.id.scnet_check_button:
                RtEnv.startActivity(SCNETCheckActivity.class);
                break;
            case R.id.system_power_check_button:
                RtEnv.startActivity(SystemPowerCheckActivity.class);
                break;

        }
    }



}
