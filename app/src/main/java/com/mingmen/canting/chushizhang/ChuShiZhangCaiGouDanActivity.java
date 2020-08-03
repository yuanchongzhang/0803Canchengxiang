package com.mingmen.canting.chushizhang;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mingmen.canting.R;
import com.mingmen.canting.flycotablelayout.flyco.tablayout.SlidingTabLayout;
import com.mingmen.canting.flycotablelayout.flyco.tablayout.listener.OnTabSelectListener;
import com.mingmen.canting.flycotablelayout.flyco.tablayout.widget.MsgView;
import com.mingmen.canting.fragment.Daicaigouragment;
import com.mingmen.canting.fragment.ZhexianFagment;
import com.mingmen.canting.immersionbar.ImmersionBar;
import com.mingmen.canting.util.ViewFindUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChuShiZhangCaiGouDanActivity extends AppCompatActivity implements OnTabSelectListener {

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
    private Context mContext = this;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {
            "订单审核", "查看采购单", "采购入库单"
    };
    private MyPagerAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chushi_caigou);
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(mToolbar)
                .navigationBarColor(R.color.black).init();
       /* for (String title : mTitles) {
            mFragments.add(Daicaigouragment.getInstance(title));
        }*/
        mFragments.add(ChuShiShenHefragment.getInstance(mTitles[0]));
//        mFragments.add(CaiGouZhongFragment.getInstance(mTitles[1]));
        mFragments.add(Daicaigouragment.getInstance(mTitles[1]));
        mFragments.add(ZhexianFagment.getInstance(mTitles[2]));
        imgRight.setVisibility(View.VISIBLE);
        View decorView = getWindow().getDecorView();
        ViewPager vp = ViewFindUtils.find(decorView, R.id.vp);
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(mAdapter);
        /**自定义部分属性*/
        SlidingTabLayout tabLayout_2 = ViewFindUtils.find(decorView, R.id.tl_2);
        /** indicator固定宽度 */
        tabLayout_2.setViewPager(vp);
        tabLayout_2.setOnTabSelectListener(this);

        vp.setCurrentItem(0);

//        tabLayout_2.showMsg(2, 5);
//        tabLayout_2.setMsgMargin(3, 0, 10);
        MsgView rtv_2_3 = tabLayout_2.getMsgView(2);

        if (rtv_2_3 != null) {

//            rtv_2_3.setBackgroundColor(Color.parseColor("#6D8FB0"));
            rtv_2_3.setBackgroundColor(Color.parseColor("#E84343"));
        }
//        tabLayout_2.showMsg(2, 5);

//        tabLayout_2.setMsgMargin(2, 8, 5);



        vp.setOffscreenPageLimit(0);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("viewpager", position + "");
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onTabSelect(int position) {
        Toast.makeText(ChuShiZhangCaiGouDanActivity.this, position + "", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTabReselect(int position) {
        Toast.makeText(ChuShiZhangCaiGouDanActivity.this, position + "", Toast.LENGTH_LONG).show();
    }

    @OnClick({R.id.text_return, R.id.text_center, R.id.img_right, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_return:
                break;
            case R.id.text_center:
                break;
            case R.id.img_right:
                break;
            case R.id.tv_right:
                break;
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
