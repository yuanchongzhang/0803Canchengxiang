package com.mingmen.canting.zhenban;

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
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.mingmen.canting.R;
import com.mingmen.canting.flycotablelayout.flyco.tablayout.SlidingTabLayout;
import com.mingmen.canting.flycotablelayout.flyco.tablayout.listener.OnTabSelectListener;
import com.mingmen.canting.flycotablelayout.flyco.tablayout.widget.MsgView;
import com.mingmen.canting.http.callback.StringCallback;
import com.mingmen.canting.httpconfig.HttpResponse;
import com.mingmen.canting.immersionbar.ImmersionBar;
import com.mingmen.canting.model.ShopBean;
import com.mingmen.canting.util.GsonUtil;
import com.mingmen.canting.util.SharePreferenceUtil;
import com.mingmen.canting.util.ViewFindUtils;
import com.mingmen.canting.zhenbanlibrary.MyGoodsInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class DingDanActivity extends AppCompatActivity implements OnTabSelectListener {

    @BindView(R.id.text_return)
    TextView textReturn;

    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.linear_return)
    RelativeLayout linearReturn;
    @BindView(R.id.layout_more)
    LinearLayout layoutMore;
    @BindView(R.id.layout_riqi)
    LinearLayout layoutRiqi;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {
            "办公用品", "低值易耗品", "日用品"
    };
    private MyPagerAdapter mAdapter;
    TimePickerView pvTime;
    String token;
    List<String> mylist = new ArrayList<>();
    List<ShopBean.DataBean> data = new ArrayList<>();
    ViewPager vp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caigou);
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(mToolbar)
                .navigationBarColor(R.color.black).init();
       /* for (String title : mTitles) {
            mFragments.add(DingDanFragment.getInstance(title));
        }*/
//        mFragments.add(DingDanFragment.getInstance(mTitles[0]));
        token = (String) SharePreferenceUtil.get(this, "token", "");
        View decorView = getWindow().getDecorView();
        vp = ViewFindUtils.find(decorView, R.id.vp);
        mFragments.add(DingDanFragment2.getInstance(mTitles[0]));
        mFragments.add(DingDanFragment2.getInstance(mTitles[1]));
        mFragments.add(DingDanFragment2.getInstance(mTitles[2]));
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(mAdapter);
        HttpResponse.getShop(token, "0", "2", new StringCallback(this) {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                Log.d("sdkdkddk", s);
                ShopBean shopBean = GsonUtil.GsonToBean(s, ShopBean.class);
                data = shopBean.getData();
                for (int i = 0; i < data.size(); i++) {
                    mylist.add(data.get(i).getCategoryName());
                }
                mAdapter.setDataList(mylist);
            }
        });


        /**自定义部分属性*/

        SlidingTabLayout tabLayout_2 = ViewFindUtils.find(decorView, R.id.tl_2);
        /** indicator固定宽度 */
        tabLayout_2.setViewPager(vp);
        tabLayout_2.setOnTabSelectListener(this);

        vp.setCurrentItem(0);


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
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2014, 1, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2027, 2, 28);
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                Toast.makeText(DingDanActivity.this, getTime(date), Toast.LENGTH_SHORT).show();
                Log.d("kdkdkdkdk", getTime(date));

            }
        }).setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_lunar, new CustomListener() {

                    @Override
                    public void customLayout(final View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTime.returnData();
                                pvTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTime.dismiss();
                            }
                        });
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(getResources().getColor(R.color.canedacolor))
                .build();
    }

    @Override
    public void onTabSelect(int position) {
        Toast.makeText(DingDanActivity.this, position + "", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTabReselect(int position) {
        Toast.makeText(DingDanActivity.this, position + "", Toast.LENGTH_LONG).show();
    }

    @OnClick({R.id.text_return, R.id.layout_more, R.id.layout_riqi, R.id.linear_return})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_return:
                break;


            case R.id.layout_more:
                break;
            case R.id.layout_riqi:
                pvTime.show(layoutRiqi);
                break;
            case R.id.linear_return:
                finish();
                break;
        }
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        List<String> mylist;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setDataList(List<String> mylist) {
            this.mylist = mylist;
            notifyDataSetChanged();
            for (int i = 0; i < mylist.size(); i++) {
                Log.d("DingDanadsffd", mylist.get(i));
            }

        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            try {
                return mylist.get(position);
            } catch (Exception e) {
                return mTitles[position];
            }


//
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
