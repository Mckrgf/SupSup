package com.ator.supmaintenance.operations;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ator.supmaintenance.R;
import com.ator.supmaintenance.item.UserUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OperationMainActivity extends AppCompatActivity {

    @BindView(R.id.im_back)
    ImageView imBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.im_menu)
    ImageView imMenu;
    @BindView(R.id.ll_alltop)
    RelativeLayout llAlltop;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_department)
    TextView tvUserDepartment;
    @BindView(R.id.ll_userlogin)
    LinearLayout llUserlogin;
    @BindView(R.id.tv_taskcount)
    TextView tvTaskcount;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        Date dd = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String strTime = sdf.format(dd);
        tvTime.setText(strTime);

        tvUserName.setText(UserUtil.getInstance().mCurEmployee.name);
        tvUserDepartment.setText(UserUtil.getInstance().mCurEmployee.department);
    }
}
