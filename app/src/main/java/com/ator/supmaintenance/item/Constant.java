package com.ator.supmaintenance.item;

/**
 * Created by feizhenhua on 2018/2/14.
 */

public class Constant {

    public static final int TASKDOING_NOTSTART = 0;
    public static final int TASKDOING_STARTED = 1;
    public static final int TASKDOING_END = 2;
    public static final int TASKDOING_ABORT = 3;
    public static final int TASKDOING_OVERTIME = 4;


    /** url and test server addr */
    public static final String URL_HEAD = "http://";
    public static final String ADDR_TESTSVR = "118.31.42.34";


    public static final int FTP_PORT = 21;
    public static final String FTP_PATH = "118.31.42.34";

    public static final String FTP_NAME = "supcon";
    public static final String FTP_PAW = "supcon";

    public static final String PORT_TESTSVR = "8080";

    public static final String URL_INSPECTIONSVR = "120.27.228.184";
    public static final String INSPECTION_PORT = "8080";
    public static final String URL_INSPECTION_TOP_LEVEL = "inspection";

    public static final String URL_INSPECTION_PLAN = "plan";
        public static final String URL_INSPECTION_TASKBASE = "task";


    public static final String URL_USER_VERSION = "user/infoversion";
    public static final String URL_USER_ALL = "user/allinfo";

    public static final String ABOUT_SUPCON = "http://www.supcontech.com/#about";
    public static final String ABOUT_PRODUCT = "http://phone.supcontech.com/product/index.html";

    /** 运行时变量存储的key */
    public static final String RT_APP = "APP"; //当前的application
    public static final String RT_CURRENT_ACTIVITY = "CURRENT_ACTIVITY"; //当前的activity
    public static final String TASK_TYPE_NORMAL = "task";
    public static final String TASK_TYPE_TEMP = "temptask";

    public static final String TEMP_EQUIP_NAME = "805i";
    public static final int    WM_BLE_CONNECTED_STATE_CHANGE = 0x11;
    public static final int    WM_BLE_ONSERVICE_DISCOVERED = 0x12;          //onServicesDiscovered
    public static final int    WM_BLE_ONCHARACTERISTCCHANGED = 0x13;        //onCharacteristicChanged
    public static final int    WM_BLE_ONDESCRIPTOR_WRITE = 0x14;            //onDescriptorWrite
    public static final int    WM_BLE_CONNECT = 0x15;                     //connect


    /** SharedPreferences相关 */
    public static final String SP_USER = "SP_USER"; //用户SP
    public static final String SP_USER_NAME = "SP_USER_NAME"; //用户名字
    public static final String SP_USER_HEAD_PORTRAIT = "SP_USER_HEAD_PORTRAIT"; //用户头像

    /** 权限相关 */
    public static final int PERMISSION_READ_PHONE_STATE = RtEnv.makeID(); //读取手机状态
    public static final int PERMISSION_WRITE_SETTINGS = RtEnv.makeID(); //修改系统设置
    public static final int PERMISSION_WRITE_EXTERNAL_STORAGE = RtEnv.makeID(); //写入外部存储
    public static final int PERMISSION_CALL_PHONE = RtEnv.makeID(); //打电话
    public static final int PERMISSION_RECORD_AUDIO = RtEnv.makeID(); //录音
    public static final int PERMISSION_READ_CONTACTS = RtEnv.makeID(); //读取联系人信息
    public static final int PERMISSION_CAMERA = RtEnv.makeID(); //相机

    /** 请求码 */
    public static final int CODE_IMAGE_RECOGINZE = RtEnv.makeID(); //图像识别
    public static final int CODE_TEMPERATURE = RtEnv.makeID(); //温度识别

    public static final String KEY_IMAGE_RECOGINZE_RESULT = "RECOGINZE_RESULT"; //图像识别结果
    public static final String KEY_TEMPERATURE_RESULT = "TEMPERATURE_RESULT"; //温度识别结果

    public static final int CODE_OK = RtEnv.makeID(); //成功结果


}
