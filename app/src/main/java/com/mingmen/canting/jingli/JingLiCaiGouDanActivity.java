package com.mingmen.canting.jingli;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.mingmen.canting.MainActivity;
import com.mingmen.canting.R;
import com.mingmen.canting.adapter.MyLabelAdapter;
import com.mingmen.canting.definerecycleview.SwipeRecyclerView;
import com.mingmen.canting.flycotablelayout.flyco.tablayout.SlidingTabLayout;
import com.mingmen.canting.flycotablelayout.flyco.tablayout.listener.OnTabSelectListener;
import com.mingmen.canting.flycotablelayout.flyco.tablayout.widget.MsgView;
import com.mingmen.canting.immersionbar.ImmersionBar;

import com.mingmen.canting.popwindow.PopupWindowCompat;
import com.mingmen.canting.util.ViewFindUtils;
import com.mingmen.canting.zhenban.BohuiListragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JingLiCaiGouDanActivity extends FragmentActivity implements OnTabSelectListener {
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
    @BindView(R.id.layout_riqi)
    LinearLayout layoutRiqi;
    private Context mContext = this;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {
            "待审核", "已审核", "被驳回"
    };
    private MyPagerAdapter mAdapter;
    TimePickerView pvTime;

    @BindView(R.id.layout_more)
    LinearLayout layout_more;
    SlidingTabLayout tabLayout_2;
    private String[] mVals = new String[]
            {"一次性筷子", "餐巾纸", "大白菜(大) ", "大白菜(小)", "白菜", "大头菜",
                    "奶白菜", "奶白菜"};
    MyLabelAdapter myLabelAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jinglicaigou);
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(mToolbar)
                .navigationBarColor(R.color.black).init();
     /*   for (String title : mTitles) {
            mFragments.add(ZhenBanDingDanFragment.getInstance(title));
        }*/

        View view = getLayoutInflater().inflate(R.layout.verify_dialog, null);

        mFragments.add(JingLiDaiShenHeListragment.getInstance(mTitles[0]));
        mFragments.add(JingLiCaiGouFragment.getInstance(mTitles[1]));
        mFragments.add(BohuiListragment.getInstance(mTitles[1]));
        imgRight.setVisibility(View.VISIBLE);
        View decorView = getWindow().getDecorView();
        ViewPager vp = ViewFindUtils.find(decorView, R.id.vp);
        mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(mAdapter);
        /**自定义部分属性*/
        tabLayout_2 = ViewFindUtils.find(decorView, R.id.tl_2);
        /** indicator固定宽度 */
        tabLayout_2.setViewPager(vp);
        tabLayout_2.setOnTabSelectListener(this);

        vp.setCurrentItem(0);

        tabLayout_2.showMsg(2, 5);
        tabLayout_2.setMsgMargin(2, 0, 10);
        MsgView rtv_2_3 = tabLayout_2.getMsgView(3);
        if (rtv_2_3 != null) {
            rtv_2_3.setBackgroundColor(Color.parseColor("#6D8FB0"));
        }
        tabLayout_2.showMsg(2, 5);
        tabLayout_2.setMsgMargin(2, 20, 10);

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
                Toast.makeText(JingLiCaiGouDanActivity.this, getTime(date), Toast.LENGTH_SHORT).show();
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
        Toast.makeText(JingLiCaiGouDanActivity.this, position + "", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTabReselect(int position) {
        Toast.makeText(JingLiCaiGouDanActivity.this, position + "", Toast.LENGTH_LONG).show();
    }



    @OnClick({R.id.text_return, R.id.text_center, R.id.img_right, R.id.tv_right, R.id.layout_riqi, R.id.layout_more})
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
            case R.id.layout_riqi:
                pvTime.show(layoutRiqi);
                break;
            case R.id.layout_more:
//                View view1 = getLayoutInflater().inflate(R.layout.poplabel, null);

                PopupWindowCompat popupWindow = new PopupWindowCompat(this);
                View contentView = LayoutInflater.from(this).inflate(R.layout.poplabel, null);
                popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.setContentView(contentView);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.showAsDropDown(tabLayout_2);
                TextView textView = contentView.findViewById(R.id.tv_kdd);
                textView.setText("");
                LinearLayout layout_content = contentView.findViewById(R.id.layout_content);
                layout_content.getBackground().setAlpha(180);
                SwipeRecyclerView recyclerView = contentView.findViewById(R.id.recycler_view);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(JingLiCaiGouDanActivity.this, 4);
                recyclerView.setLayoutManager(gridLayoutManager);
                myLabelAdapter = new MyLabelAdapter(JingLiCaiGouDanActivity.this);
                recyclerView.setAdapter(myLabelAdapter);
                List<String> mystr = new ArrayList<>();

                for (int i = 0; i < mVals.length; i++) {
                    mystr.add(mVals[i]);
                }

                myLabelAdapter.setDataList(mystr);
                myLabelAdapter.setOnItemClickListener(new MyLabelAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClickListener(View view, int pos, List<String> myLiveList) {
                        myLabelAdapter.setCheckedPosition(pos);
                        Log.d("JingLiCaigou", myLiveList.get(pos));
                        Toast.makeText(JingLiCaiGouDanActivity.this, myLiveList.get(pos) + "", Toast.LENGTH_SHORT).show();
                        popupWindow.dismiss();

                    }
                });
                DividerItemDecoration divider = new DividerItemDecoration(JingLiCaiGouDanActivity.this, DividerItemDecoration.VERTICAL);
                divider.setDrawable(ContextCompat.getDrawable(JingLiCaiGouDanActivity.this, R.drawable.custom_divider));

                recyclerView.addItemDecoration(divider);

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

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
