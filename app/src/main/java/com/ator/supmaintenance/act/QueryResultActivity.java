package com.ator.supmaintenance.act;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ator.supmaintenance.R;
import com.ator.supmaintenance.adapter.HisFileListAdapter;
import com.ator.supmaintenance.adapter.QueryResultAdapter;
import com.ator.supmaintenance.item.FileUtil;
import com.ator.supmaintenance.item.MyRecUtil;
import com.ator.supmaintenance.item.RtEnv;

public class QueryResultActivity extends BaseActivity implements View.OnClickListener  {

    String mStrSub = "";
    String mStrName = "";
    private RecyclerView mRecyclerView;

    private QueryResultAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_result);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        mStrSub = bundle.getString("type");
        mStrName = bundle.getString("name");

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(QueryResultActivity.this));
        mAdapter = new QueryResultAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new QueryResultAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(final int position) {

            }
        });
        init();
    }

    private void init(){

        Button btnTitle = (Button)findViewById(R.id.btn_title);
        String str1 = MyRecUtil.getRecTypeDes(mStrSub);
        btnTitle.setText(str1 + " " + mStrName);
    }

    @Override
    protected int initContentView() {
        return R.layout.activity_query_result;
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
    protected void onResume() {
        super.onResume();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
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

}
