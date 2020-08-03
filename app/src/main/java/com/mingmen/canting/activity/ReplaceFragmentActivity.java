package com.mingmen.canting.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mingmen.canting.R;
import com.mingmen.canting.fragment.HomeIndexFragment;
import com.mingmen.canting.fragment.RefreshPracticeFragment;
import com.mingmen.canting.fragment.RefreshStylesFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReplaceFragmentActivity extends AppCompatActivity {
    HomeIndexFragment homeIndexFragment;
    RefreshPracticeFragment refreshPracticeFragment;
    RefreshStylesFragment refreshStylesFragment;
    @BindView(R.id.main_frame_layout)
    FrameLayout mainFrameLayout;

    @BindView(R.id.container)
    LinearLayout container;
    @BindView(R.id.iv_home)
    ImageView ivHome;
    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.ll_home)
    LinearLayout llHome;
    @BindView(R.id.iv_touzi)
    ImageView ivTouzi;
    @BindView(R.id.tv_touzi)
    TextView tvTouzi;
    @BindView(R.id.ll_touzi)
    LinearLayout llTouzi;
    @BindView(R.id.iv_me)
    ImageView ivMe;
    @BindView(R.id.tv_me)
    TextView tvMe;
    @BindView(R.id.ll_me)
    LinearLayout llMe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_main);
        ButterKnife.bind(this);

        reSetTab();
        initFragment1();
        tvHome.setTextColor(getResources().getColor(R.color.home_back_selected));
        tvMe.setTextColor(getResources().getColor(R.color.home_back_unselected));
        tvTouzi.setTextColor(getResources().getColor(R.color.home_back_unselected));
        ivHome.setImageResource(R.drawable.bid01);
    }

    //显示第一个fragment
    private void initFragment1() {
        //开启事务，fragment的控制是由事务来实现的
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        //第一种方式（add），初始化fragment并添加到事务中，如果为null就new一个
     /*   if(homeIndexFragment == null){
            homeIndexFragment = new HomeIndexFragment();
            transaction.add(R.id.main_frame_layout, homeIndexFragment);
        }
        //隐藏所有fragment
        hideFragment(transaction);
        //显示需要显示的fragment
        transaction.show(homeIndexFragment);*/

        //第二种方式(replace)，初始化fragment
        if (homeIndexFragment == null) {
            homeIndexFragment = new HomeIndexFragment();
        }
        transaction.replace(R.id.main_frame_layout, homeIndexFragment);

        //提交事务
        transaction.commit();
    }

    //显示第二个fragment
    private void initFragment2() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

      /*  if(refreshPracticeFragment == null){
            refreshPracticeFragment = new RefreshPracticeFragment();
            transaction.add(R.id.main_frame_layout,refreshPracticeFragment);
        }
        hideFragment(transaction);
        transaction.show(refreshPracticeFragment);*/

        if (refreshPracticeFragment == null) {
            refreshPracticeFragment = new RefreshPracticeFragment();
        }
        transaction.replace(R.id.main_frame_layout, refreshPracticeFragment);

        transaction.commit();
    }

    //显示第三个fragment
    private void initFragment3() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

      /*  if(refreshStylesFragment == null){
            refreshStylesFragment = new RefreshStylesFragment();
            transaction.add(R.id.main_frame_layout,refreshStylesFragment);
        }
        hideFragment(transaction);
        transaction.show(refreshStylesFragment);*/

        if (refreshStylesFragment == null) {
            refreshStylesFragment = new RefreshStylesFragment();
        }
        transaction.replace(R.id.main_frame_layout, refreshStylesFragment);

        transaction.commit();
    }

    @OnClick({R.id.iv_home, R.id.tv_home, R.id.ll_home, R.id.iv_touzi, R.id.tv_touzi, R.id.ll_touzi, R.id.iv_me, R.id.tv_me, R.id.ll_me})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_home:

            case R.id.tv_home:

            case R.id.ll_home:
                reSetTab();
                initFragment1();
                tvHome.setTextColor(getResources().getColor(R.color.home_back_selected));
                tvMe.setTextColor(getResources().getColor(R.color.home_back_unselected));
                tvTouzi.setTextColor(getResources().getColor(R.color.home_back_unselected));
                ivHome.setImageResource(R.drawable.bid01);
                break;
            case R.id.iv_touzi:

            case R.id.tv_touzi:

            case R.id.ll_touzi:
                reSetTab();
                initFragment2();
                ivTouzi.setImageResource(R.drawable.bid03);
                tvTouzi.setTextColor(getResources().getColor(R.color.home_back_selected));
                tvHome.setTextColor(getResources().getColor(R.color.home_back_unselected));
                tvMe.setTextColor(getResources().getColor(R.color.home_back_unselected));
                break;
            case R.id.iv_me:

            case R.id.tv_me:

            case R.id.ll_me:
                reSetTab();
                initFragment3();
                tvMe.setTextColor(getResources().getColor(R.color.home_back_selected));
                tvHome.setTextColor(getResources().getColor(R.color.home_back_unselected));
                tvTouzi.setTextColor(getResources().getColor(R.color.home_back_unselected));
                ivMe.setImageResource(R.drawable.bid05);
                break;
        }
    }

    private void reSetTab() {
        ivHome.setImageResource(R.drawable.bid02);
        ivTouzi.setImageResource(R.drawable.bid04);
        ivMe.setImageResource(R.drawable.bid06);


    }
    //隐藏所有的fragment
/*    private void hideFragment(FragmentTransaction transaction){
        if(f1 != null){
            transaction.hide(f1);
        }
        if(f2 != null){
            transaction.hide(f2);
        }
        if(f3 != null){
            transaction.hide(f3);
        }
    }*/




 /*   @OnClick({R.id.btn_message, R.id.btn_contact, R.id.btn_dongtai})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_message:
                initFragment1();
                break;
            case R.id.btn_contact:
                initFragment2();
                break;
            case R.id.btn_dongtai:
                initFragment3();
                break;
        }
    }*/
}
