package com.ator.supmaintenance.item;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.model.LatLng;
import com.ator.supmaintenance.operations.OperationBean;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by feizhenhua on 2018/4/30.
 */

public class MyApplication extends Application implements AMapLocationListener {

    private static Context context;
    public static  AMapLocationClient mLocationClient;
    public static  AMapLocationClientOption mLocationOption;

    public static  double myLocLat = 0;
    public static  double myLocLon = 0;

    public String imeiStr = "";
    public static ArrayList<OperationBean> op_data;

    public MyApplication(){

    }

    public static Context getContext() {
        return context;
    }
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

        context = getApplicationContext();

        initLoc();

        InspectionUtil.getInstance().makePicFilePath(context,"MyInspectionPic");


        //模拟运维巡检项目
        try {
            initOperatorData();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initOperatorData(){
        op_data = new ArrayList();

        ArrayList<String> equipments = new ArrayList<>();
        equipments.add("装置-EOPO");
        equipments.add("装置-绿科安");
        OperationBean bean = new OperationBean();
        bean.setCompany("企业名称-绿科安");
        bean.setFacilitator("服务商-中控");
        bean.setOperator("操作人-宋奇");
        bean.setProject("项目名-巡检项目A");
        bean.setP_id("201807110001");
        //mtk
        try {
            bean.setStart_time(MyDateUtils.getLongDateFromString("2018-7-15",MyDateUtils.date_Format2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        bean.setStatus();
        bean.setSystem_type("300xp");
        bean.setEquipment(equipments);
        op_data.add(bean);


        ArrayList<String> equipments1 = new ArrayList<>();
        equipments1.add("装置-六车间");
        equipments1.add("装置-七车间");
        OperationBean bean1 = new OperationBean();
        bean1.setCompany("企业名称-绿科安");
        bean1.setFacilitator("服务商-中控");
        bean1.setOperator("操作人-白建军");
        bean1.setProject("项目名-巡检项目B");
        bean1.setP_id("201807110002");
        try {
            bean1.setStart_time(MyDateUtils.getLongDateFromString("2018-7-16",MyDateUtils.date_Format2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        bean1.setStatus();
        bean1.setEquipment(equipments1);
        bean1.setSystem_type("700");
        op_data.add(bean1);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {

                myLocLon = aMapLocation.getLongitude();//获取经度
                myLocLat =  aMapLocation.getLatitude();//获取纬度

                Log.i("location:","lon:"+myLocLon + "  lat:"+myLocLat +"\n\r");

            }
        } else {
            //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
            Log.e("AmapError", "location Error, ErrCode:"
                    + aMapLocation.getErrorCode() + ", errInfo:"
                    + aMapLocation.getErrorInfo());
        }
    }

    private void initLoc(){

        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听，这里要实现AMapLocationListener接口，AMapLocationListener接口只有onLocationChanged方法可以实现，用于接收异步返回的定位结果，参数是AMapLocation类型。
        mLocationClient.setLocationListener(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);

        mLocationClient.startLocation();
    }

    public static LatLng getMylocation() {
        LatLng ll = new LatLng(myLocLat,myLocLon);
        return  ll;
    }

    /**
     * 一些公共参数 统一放到这里管理
     */
//    public static  String base_url = "http://192.168.8.104:8080";//本地测试服务器地址
    public static  String base_url = "http://118.31.42.34:8090";//云上服务器

}
