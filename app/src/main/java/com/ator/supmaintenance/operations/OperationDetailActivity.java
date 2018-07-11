package com.ator.supmaintenance.operations;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ator.supmaintenance.R;
import com.ator.supmaintenance.item.MyDateUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OperationDetailActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.im_back)
    ImageView imBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.im_menu)
    ImageView imMenu;
    @BindView(R.id.et_facilitator)
    EditText etFacilitator;
    @BindView(R.id.et_company)
    EditText etCompany;
    @BindView(R.id.et_project)
    EditText etProject;
    @BindView(R.id.et_operator)
    EditText etOperator;
    @BindView(R.id.et_time)
    EditText etTime;
    @BindView(R.id.ll_alltop)
    RelativeLayout llAlltop;
    @BindView(R.id.et_sys_type)
    EditText etSysType;
    @BindView(R.id.et_pid)
    EditText etPid;
    private OperationBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_detail);
        ButterKnife.bind(this);
        bean = (OperationBean) getIntent().getSerializableExtra("BEAN");
        initData();
        initView();
    }

    private void initView() {
        imBack.setOnClickListener(this);
        tvTitle.setText("巡检项目详情");
        imMenu.setVisibility(View.GONE);
    }

    private void initData() {
        etFacilitator.setText(bean.getFacilitator());
        etCompany.setText(bean.getCompany());
        etProject.setText(bean.getProject());
        etOperator.setText(bean.getOperator());
        etTime.setText(MyDateUtils.getDateFromLong(bean.getStart_time(), MyDateUtils.date_Format2));
        etSysType.setText(bean.getSystem_type());
        etPid.setText(bean.getP_id());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_back:
                finish();
                break;
        }
    }
}
