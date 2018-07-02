package com.ator.supmaintenance.act;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ator.supmaintenance.R;
import com.ator.supmaintenance.adapter.HisFileListAdapter;
import com.ator.supmaintenance.item.ComparatorFile;
import com.ator.supmaintenance.item.FileUtil;
import com.ator.supmaintenance.item.MyNetHelper;
import com.ator.supmaintenance.item.MyRecUtil;
import com.ator.supmaintenance.item.RtEnv;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class HisFileListActivity extends BaseActivity implements View.OnClickListener {

    String mStrSub = "";
    static String mLastSub = "";

    private RecyclerView mRecyclerView;

    private HisFileListAdapter mAdapter;
    private TextView mtvSearch;

    private boolean bLoc = false;

    public static int SEARCHMODE_LIST = 1;
    public static int SEARCHMODE_FILE = 2;

    private int nSearchmode = 0;
    private String mLastSelFile = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_his_file_list);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mStrSub = bundle.getString("type");

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(HisFileListActivity.this));
        mAdapter = new HisFileListAdapter();
        mRecyclerView.setAdapter(mAdapter);

        if (!mLastSub.equals(mStrSub)) {
            mAdapter.ClearResult();
        }
        mLastSub = mStrSub;

        findViewById(R.id.ll_search).setOnClickListener(this);
        mtvSearch = (TextView)findViewById(R.id.tv_search);
        mtvSearch.setText("搜索：" + MyRecUtil.getRecTypeDes(mStrSub));

        mAdapter.setOnItemClickListener(new HisFileListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final int position) {

                nSearchmode = SEARCHMODE_FILE;
                final String strName = mAdapter.mFileAry.get(position);
                mLastSelFile = strName;
                if (bLoc) {
                    new Thread(new Runnable(){
                        @Override
                        public void run() {
                            try{
                                    String result = FileUtil.getFile(getApplicationContext(),mStrSub,strName);
                                    if (result.equals("")){
                                        Log.i("111","1111");
                                    }else {
                                        MyRecUtil.parseResult(result);
                                        RtEnv.startActivity(QueryResultActivity.class,mStrSub,strName);
                                    }

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } else {
                    long tm = getFileTime(strName);
                    if (tm >0) {
                        String strURL = MyRecUtil.getFileURLbyTime(mStrSub,tm);
                        getJasonString(strURL,5000,200,true,"下载","正在下载...");
                    }
                }

            }
        });

    }

    private long getFileTime(String strName){

        long queryTm = 0;
        int npos = strName.lastIndexOf("bad");

        if (npos >=0){
            strName = strName.substring(0,npos);
        }

        String sample = "yyyy年MM月dd日 HH时mm分ss秒";
        try
        {
            int nLen = sample.length();
            if (strName.length() == nLen) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(sample);
                Date date = simpleDateFormat.parse(strName);
                queryTm = date.getTime();

            }else {
                String sample2 = "yyyy-MM-dd HH-mm-ss";
                int nlen2 = sample2.length();
                String strHead = strName.substring(0,nlen2-1);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(sample2);
                Date date = simpleDateFormat.parse(strHead);
                queryTm = date.getTime();

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return queryTm;
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_his_file_list;
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

        if (nSearchmode == SEARCHMODE_LIST){
            dealList();
        }else if (nSearchmode == SEARCHMODE_FILE){
            dealfile();
        }

    }

    @Override
    protected void onGetFaile(){

        if (nSearchmode == SEARCHMODE_LIST){
            mAdapter.ClearResult();
        }else if (nSearchmode == SEARCHMODE_FILE){

        }

        alertText("失败","下载失败");
    }

    private void dealList(){
        mAdapter.ClearResult();
        try{
            JSONArray jary = new JSONArray(mStrGet);
            int n = jary.length();
            for(int i=0;i<n;i++)
            {
                String strObj = jary.get(i).toString();
                JSONObject jobj = new JSONObject(strObj);
                String strName = jobj.getString("filename");
                mAdapter.mFileAry.add(strName);
            }
            showResult();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void dealfile(){

        if (mLastSelFile.equals("")) {
            return;
        }

        try{
            JSONArray jary = new JSONArray(mStrGet);
            int n = jary.length();
            if (n>0) {
                String strValue = jary.get(0).toString();
                MyRecUtil.parseResult(strValue);
                RtEnv.startActivity(QueryResultActivity.class,mStrSub,mLastSelFile);
            }else {
                Log.i("err","ary count = 0");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected  void onDestroy(){
        super.onDestroy();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_search:
                nSearchmode = SEARCHMODE_LIST;
                if (mNetState == MyNetHelper.NETWORK_NONE) {
                    bLoc = true;
                    alertText("提示：","无网络，正在本机查询");
                    SearchLocFiles();

                }else {
                    SearchFileListFromSvr();
                    bLoc = false;
                }
                break;
        }
    }

    public void SearchFileListFromSvr(){

        String strURL = MyRecUtil.getListURLbyType(mStrSub);

        getJasonString(strURL,5000,200,true,"下载","正在下载...");

    }

    public void SearchLocFiles(){

        new Thread(new Runnable(){
            @Override
            public void run() {
                try{
                    mAdapter.ClearResult();
                    Thread.currentThread().sleep(1000);
                    mAdapter.mFileAry = FileUtil.GetFileList(getApplicationContext(),mStrSub,".f");
                    showResult();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showResult(){
        Comparator comp = new ComparatorFile();
        Collections.sort(mAdapter.mFileAry,comp);
        finish();
        RtEnv.startActivity(HisFileListActivity.class,mStrSub);
    }

}
