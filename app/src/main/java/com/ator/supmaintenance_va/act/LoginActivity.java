package com.ator.supmaintenance_va.act;


import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ator.supmaintenance_va.R;
import com.ator.supmaintenance_va.item.Company;
import com.ator.supmaintenance_va.item.CompanyBean;
import com.ator.supmaintenance_va.item.CompanyUtil;
import com.ator.supmaintenance_va.item.RtEnv;
import com.ator.supmaintenance_va.item.UserUtil;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;

import java.util.ArrayList;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    //用户名 密码
    private EditText etUsername, etPassword;
    //确定按钮
    private ImageView btnConfirm;

    private TextView mTvcompany;
    private TextView mTvDep;

    private ArrayList<CompanyBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private OptionsPickerView pvOptions;


    @Override
    protected int initContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void getIntentData() {
        return;
    }


    @Override
    protected void onPostSucc() {

    }

    @Override
    protected void onPostFaile() {


    }

    @Override
    protected void onGetSucc() {

    }

    @Override
    protected void onGetFaile() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        mTvcompany = (TextView) findViewById(R.id.tv_company);
        mTvDep = (TextView) findViewById(R.id.tv_department);

        findViewById(R.id.ll_nfc_login).setOnClickListener(this);
        findViewById(R.id.ll_scan_login).setOnClickListener(this);
        btnConfirm = (ImageView) findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(this);

        findViewById(R.id.im_chooseuser).setOnClickListener(this);

        initText();
        initData();
        initOptionPicker();

    }

    private void initText(){

        etUsername.setText(UserUtil.getInstance().mCurEmployee.name);
        mTvcompany.setText(UserUtil.getInstance().mCurEmployee.company);
        mTvDep.setText(UserUtil.getInstance().mCurEmployee.department);
    }

    @Override
    protected void initView() {


    }

    @Override
    protected void start() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_nfc_login:
                RtEnv.startActivity(MyNFCActivity.class);
                break;
            case R.id.ll_scan_login:
                RtEnv.startActivity(QRCodeActivity.class);
                break;
            case R.id.btn_confirm:
                login();
//                RtEnv.startActivity(FirstActivity.class);
//                finish();
                break;
            case R.id.im_chooseuser:
                pvOptions.show();
                break;
        }
    }

    /**
     * 登录
     */
    private void login() {
        String username = etUsername.getText().toString().toLowerCase();
        String password = etPassword.getText().toString().toLowerCase();

        if (UserUtil.getInstance().Login(username) > 0) {

            RtEnv.startActivity(FirstActivity.class);
            finish();
        } else {
            Toast.makeText(this, "用户名或者密码错误", Toast.LENGTH_SHORT).show();
        }
    }

    private void initData(){

        for (int i=0;i< CompanyUtil.getInstance().companys.size();i++){

            Company com = (Company) CompanyUtil.getInstance().companys.get(i);
            options1Items.add(new CompanyBean(0,com.name, com.name, com.name));

            ArrayList<String> options2its = new ArrayList<>();
            ArrayList<ArrayList<String>>    options3its = new ArrayList<>();

            for (int j = 0;j<com.departments.size();j++){
                String str = com.departments.get(j);
                options2its.add(str);

                options3its.add(UserUtil.getInstance().getUsers(com.name,str));

            }
            options2Items.add(options2its);
            options3Items.add(options3its);


        }
    }

    private void initOptionPicker() {//条件选择器初始化
        pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置

                String s1 = options1Items.get(options1).getPickerViewText();
                String s2 = options2Items.get(options1).get(options2);
                String s3 = options3Items.get(options1).get(options2).get(options3);

                mTvcompany.setText(s1);
                mTvDep.setText(s2);
                etUsername.setText(s3);

                etPassword.setText("");
            }
        })
                .setTitleText("选择")
                .setContentTextSize(20)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(0, 0,0)//默认选中项
                .setBgColor(Color.BLACK)
                .setTitleBgColor(Color.DKGRAY)
                .setTitleColor(Color.LTGRAY)
                .setCancelColor(Color.YELLOW)
                .setSubmitColor(Color.YELLOW)
                .setTextColorCenter(Color.LTGRAY)
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(true) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("", "", "")
                .setBackgroundId(0x00000000) //设置外部遮罩颜色
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                        String str = "options1: " + options1 + "\noptions2: " + options2 + "\noptions3: " + options3;
                    }
                })
                .build();

        //        pvOptions.setSelectOptions(1,1);
        /*pvOptions.setPicker(options1Items);//一级选择器*/
        //        pvOptions.setPicker(options1Items, options2Items);//二级选择器
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器

    }

    private static final String TAG = "LoginActivity";
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i(TAG,keyCode+"=================");
        return super.onKeyDown(keyCode, event);
    }
}
