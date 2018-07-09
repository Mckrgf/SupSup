package com.ator.supmaintenance.act;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ator.supmaintenance.R;
import com.ator.supmaintenance.adapter.PicturePagerAdapter;
import com.ator.supmaintenance.item.MyNetHelper;
import com.ator.supmaintenance.item.RtEnv;
import com.ator.supmaintenance.item.UserUtil;
import com.ator.supmaintenance.operations.OperationMainActivity;

import java.util.ArrayList;
import java.util.List;

public class FirstActivity extends BaseActivity implements View.OnClickListener {

//    public ViewPager mViewPaper;
//    public int currentItem;
//    private List<ImageView> images;
//    private int[] imageIds = new int[]{
//            R.drawable.view_pager_1,
//            R.drawable.view_pager_2,
//            R.drawable.view_pager_3
//    };
//    private List<View> dots;
//    /**记录上一次点的位置*/
//    private int oldPosition = 0;

    private TextView mTvUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        getSupportActionBar().hide();

        findViewById(R.id.iv_runrec).setOnClickListener(this);
        findViewById(R.id.loc_query).setOnClickListener(this);
        findViewById(R.id.ai_btn).setOnClickListener(this);
        findViewById(R.id.inspection_btn).setOnClickListener(this);
        findViewById(R.id.nfc_btn).setOnClickListener(this);
        findViewById(R.id.iv_temptask).setOnClickListener(this);
        findViewById(R.id.ll_user).setOnClickListener(this);
        findViewById(R.id.iv_setting).setOnClickListener(this);

        mTvUser = (TextView)findViewById(R.id.tv_user_name);
        //viewPagerInit();

        SetUser();

    }

    private void SetUser(){

        mTvUser.setText(UserUtil.getInstance().mCurEmployee.name);
    }

//    public void viewPagerInit(){
//        mViewPaper = findViewById(R.id.picture_view_pager);
//
//        images = new ArrayList<>();
//        for(int i = 0; i < imageIds.length; i++){
//            ImageView imageView = new ImageView(this);
//            imageView.setBackgroundResource(imageIds[i]);
//            images.add(imageView);
//        }
//
//        dots = new ArrayList<>();
//        dots.add(findViewById(R.id.dot_0));
//        dots.add(findViewById(R.id.dot_1));
//        dots.add(findViewById(R.id.dot_2));
//
//        PicturePagerAdapter mAdapter = new PicturePagerAdapter(images);
//
//        mViewPaper.setAdapter(mAdapter);
//
//        mViewPaper.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                dots.get(position).setBackgroundResource(R.drawable.dot_focus);
//                dots.get(oldPosition).setBackgroundResource(R.drawable.dot_unfocus);
//
//                oldPosition = position;
//                currentItem = position;
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//    }


    @Override
    protected int initContentView() {
        return R.layout.activity_first;
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

        Toast.makeText(this,"上传成功",Toast.LENGTH_LONG);

    }

    @Override
    protected void onPostFaile(){

        Toast.makeText(this,"上传失败",Toast.LENGTH_LONG);

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
            case R.id.iv_runrec:
//                RtEnv.startActivity(MainActivity.class);
                RtEnv.startActivity(OperationMainActivity.class);
                break;
            case R.id.loc_query:
                RtEnv.startActivity(LocationActivity.class);
                break;
            case R.id.ai_btn:
                if (mNetState == MyNetHelper.NETWORK_NONE)
                {
                    Toast.makeText(getApplicationContext(), "请先联入网络", Toast.LENGTH_LONG).show();
                }else {
                    RtEnv.startActivity(AIActivity.class);
                }
                break;
            case R.id.inspection_btn:
                RtEnv.startActivity(InspectionMainActivity.class);
                break;
            case R.id.ll_setting:
                RtEnv.startActivity(SettingsActivity.class);
                break;
            case R.id.nfc_btn:
                RtEnv.startActivity(MyNFCActivity.class);
                break;
            case R.id.iv_temptask:
                RtEnv.startActivity(TempTaskListActivity.class);
                break;
            case R.id.ll_user:
                RtEnv.startActivity(LoginActivity.class);
                break;
            case R.id.iv_setting:
                RtEnv.startActivity(SettingsActivity.class);
                break;

        }
    }
}
