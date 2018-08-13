package com.ator.supmaintenance_va.act;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.ator.supmaintenance_va.R;
import com.ator.supmaintenance_va.adapter.QueryTaskInfoAdpter;
import com.ator.supmaintenance_va.item.DropdownButton;
import com.ator.supmaintenance_va.item.DropdownItemObject;
import com.ator.supmaintenance_va.item.DropdownListView;
import com.ator.supmaintenance_va.item.HisInspectionHelp;
import com.ator.supmaintenance_va.item.HisInspectionUtil;
import com.ator.supmaintenance_va.item.MyNetHelper;
import com.ator.supmaintenance_va.item.RtEnv;
import com.ator.supmaintenance_va.item.SupInspectionTask;

import java.util.ArrayList;
import java.util.List;


public class QueryInspectionActivity extends BaseActivity
        implements View.OnClickListener,DropdownListView.Container{

    public static int QUERYMODE_TASKINFO =0;
    public static int QUERYMODE_ONETASK =0;

    private int quertmode = QUERYMODE_TASKINFO;

    private static int mDay = 0;
    private static int mType= 0;
    private static String mPlanid = "";

    RecyclerView mRecyclerView;
    QueryTaskInfoAdpter mAdapter;

    DropdownButton chooseType;
    DropdownButton chooseDate;
    DropdownButton chooseName;

    View mask;
    DropdownListView dropdownType;
    DropdownListView dropdownDate;
    DropdownListView dropdownName;

    private DropdownListView currentDropdownList;
    Animation dropdown_in, dropdown_out, dropdown_mask_out;

    private List<DropdownItemObject> chooseTypeData = new ArrayList<>();//选择类型
    private List<DropdownItemObject> chooseDateData = new ArrayList<>();//选择时间
    private List<DropdownItemObject> chooseNameData = new ArrayList<>();//选择任务

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_inspection);

        chooseType = (DropdownButton) findViewById(R.id.db_choosetype);
        chooseDate = (DropdownButton) findViewById(R.id.db_choosedate);
        chooseName = (DropdownButton) findViewById(R.id.db_choosename);

        mask = findViewById(R.id.mask);
        dropdownType = (DropdownListView) findViewById(R.id.dropdownType);
        dropdownDate = (DropdownListView) findViewById(R.id.dropdownDate);
        dropdownName = (DropdownListView) findViewById(R.id.dropdownName);

        dropdown_in = AnimationUtils.loadAnimation(this, R.anim.dropdown_in);
        dropdown_out = AnimationUtils.loadAnimation(this,R.anim.dropdown_out);
        dropdown_mask_out = AnimationUtils.loadAnimation(this,R.anim.dropdown_mask_out);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(QueryInspectionActivity.this));
        mAdapter = new QueryTaskInfoAdpter(this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new QueryTaskInfoAdpter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                SupInspectionTask task = (SupInspectionTask)HisInspectionUtil.getInstance().getAryTaskInfos().get(position);
                SearchOneTask(task.taskid);
            }
        });

        init();
        mask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });

        findViewById(R.id.im_back).setOnClickListener(this);
        findViewById(R.id.tv_searchbtn).setOnClickListener(this);

        ShowHideShearBtn(false);

        initBold();
        setTitle();
    }

    private void ShowHideShearBtn(boolean bShow){

        if (bShow){
            findViewById(R.id.ll_search).setVisibility(View.VISIBLE);
        }else if (mAdapter.getItemCount() == 0){
            findViewById(R.id.ll_search).setVisibility(View.VISIBLE);
        }else {
            findViewById(R.id.ll_search).setVisibility(View.GONE);
        }


    }

    private void setTitle(){
        int nCount = mAdapter.getItemCount();

        TextView tv = (TextView)findViewById(R.id.tv_title);
        String str = String.format("共 %d 条记录",nCount);
        tv.setText(str);
    }

    private void initBold(){

        TextView tv;
        tv = (TextView)findViewById(R.id.tv_choisename);
        TextPaint paint = tv.getPaint();
        paint.setFakeBoldText(true);

    }

    void reset() {
        chooseType.setChecked(false);
        chooseDate.setChecked(false);
        chooseName.setChecked(false);

        dropdownType.setVisibility(View.GONE);
        dropdownDate.setVisibility(View.GONE);
        dropdownName.setVisibility(View.GONE);
        mask.setVisibility(View.GONE);

        dropdownType.clearAnimation();
        dropdownDate.clearAnimation();
        dropdownName.clearAnimation();
        mask.clearAnimation();
    }

    void init() {
        reset();
        chooseTypeData.add(new DropdownItemObject("全部类型", 0, "0"));
        chooseTypeData.add(new DropdownItemObject("正常", 1, "1"));
        chooseTypeData.add(new DropdownItemObject("隐患", 2, "2"));
        chooseTypeData.add(new DropdownItemObject("临检", 3, "3"));
        chooseTypeData.add(new DropdownItemObject("漏检", 4, "4"));
        dropdownType.bind(chooseTypeData, chooseType, this, mType);

        chooseDateData.add(new DropdownItemObject("今  天", 0, "0"));
        chooseDateData.add(new DropdownItemObject("近两天", 1, "1"));
        chooseDateData.add(new DropdownItemObject("近三天", 2, "2"));
        chooseDateData.add(new DropdownItemObject("近四天", 3, "3"));
        chooseDateData.add(new DropdownItemObject("近五天", 4, "4"));
        dropdownDate.bind(chooseDateData, chooseDate, this, mDay);

        chooseNameData.add(new DropdownItemObject("全部", 0, "0"));
        chooseNameData.add(new DropdownItemObject("指定", 0, "1"));
        dropdownName.bind(chooseNameData,chooseName,this,0);


        dropdown_mask_out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (currentDropdownList == null) {
                    reset();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    public void show(DropdownListView view) {
        if (currentDropdownList != null) {
            currentDropdownList.clearAnimation();
            currentDropdownList.startAnimation(dropdown_out);
            currentDropdownList.setVisibility(View.GONE);
            currentDropdownList.button.setChecked(false);
        }
        currentDropdownList = view;
        mask.clearAnimation();
        mask.setVisibility(View.VISIBLE);
        currentDropdownList.clearAnimation();
        currentDropdownList.startAnimation(dropdown_in);
        currentDropdownList.setVisibility(View.VISIBLE);
        currentDropdownList.button.setChecked(true);
    }

    @Override
    public void hide() {
        if (currentDropdownList != null) {
            currentDropdownList.clearAnimation();
            currentDropdownList.startAnimation(dropdown_out);
            currentDropdownList.button.setChecked(false);
            mask.clearAnimation();
            mask.startAnimation(dropdown_mask_out);
        }
        currentDropdownList = null;
    }


    @Override
    public void onSelectionChanged(DropdownListView view) {
        DropdownItemObject obj = view.current;
        if (view == dropdownType) {
            mType = Integer.parseInt(obj.value);
        }else if (view == dropdownDate){
            mDay = Integer.parseInt(obj.value);
        }else if(view == dropdownName){

        }

        ShowHideShearBtn(true);

        return;
    }
    @Override
    protected int initContentView() {
        return R.layout.activity_query_inspection;
    }

    @Override
    protected void getIntentData() {
        return;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void onPostSucc(){
        if(quertmode == QUERYMODE_TASKINFO){
            HisInspectionUtil.getInstance().parseTaskInfos(mStrGet);
            mAdapter.notifyDataSetChanged();
            setTitle();
            ShowHideShearBtn(false);

        }

    }

    @Override
    protected void onPostFaile(){
        alertText("查询","查询失败，请检查网络");

    }

    @Override
    protected void onGetSucc(){
        if (quertmode == QUERYMODE_ONETASK){
            HisInspectionUtil.getInstance().makeOneTaskByJString(mStrGet);
            RtEnv.startActivity(CardListActivity.class,"his");
        }

    }

    @Override
    protected void onGetFaile(){
        alertText("查询","查询失败，请检查网络");
    }

    @Override
    protected void start() {

    }

    private void SearchOneTask(String taskid){

        quertmode = QUERYMODE_ONETASK;
        HisInspectionHelp hhh = HisInspectionUtil.getInstance().makeHisOneTaskHelp(taskid);
        getJasonString(hhh.queryURL,5000,200,true,"查询","正在查询任务...");


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.im_back:
                finish();
                break;
            case R.id.tv_searchbtn:
                HisInspectionUtil.getInstance().ClearResult();
                mAdapter.notifyDataSetChanged();
                quertmode = QUERYMODE_TASKINFO;
                if (MyNetHelper.getNetWorkState(this)==MyNetHelper.NETWORK_NONE){
                    alertText("查询","当前无网络，请先接入网络");
                }else {
                    HisInspectionHelp hhh = HisInspectionUtil.getInstance().makeHisHelp(mDay,mType,mPlanid);
                    postJasonString(hhh.queryURL,hhh.queryString,5000,200,true,"查询","查询中");
                }

                break;

        }
    }
}
