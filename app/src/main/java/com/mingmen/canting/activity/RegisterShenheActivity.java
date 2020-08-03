package com.mingmen.canting.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mingmen.canting.R;
import com.mingmen.canting.immersionbar.ImmersionBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterShenheActivity extends Activity {


    @BindView(R.id.layout_shenhe)
    LinearLayout layoutShenhe;
    @BindView(R.id.text_return_login)
    TextView textReturnLogin;
    @BindView(R.id.text_return)
    TextView textReturn;
    @BindView(R.id.text_center)
    TextView textCenter;
    @BindView(R.id.img_right)
    ImageView imgRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.linear_return)
    LinearLayout linear_return;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shenhe);
        ButterKnife.bind(this);
        textCenter.setText("注册审核");
        mToolbar.setBackground(getResources().getDrawable(R.color.transparent));
        ImmersionBar.with(this).titleBar(mToolbar)
                .navigationBarColor(R.color.black).init();

    }

    @OnClick({R.id.layout_shenhe, R.id.text_return_login, R.id.linear_return})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.mToolbar:
                break;
            case R.id.layout_shenhe:
                break;
            case R.id.text_return_login:
                finish();
                break;
            case R.id.linear_return:
                finish();
                break;
        }
    }

    @OnClick(R.id.text_return)
    public void onViewClicked() {
    }
}
