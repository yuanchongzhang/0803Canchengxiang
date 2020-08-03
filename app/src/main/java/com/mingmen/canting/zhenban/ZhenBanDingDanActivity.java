package com.mingmen.canting.zhenban;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;
import com.mingmen.canting.R;
import com.mingmen.canting.adapter.MyLabelAdapter;
import com.mingmen.canting.definerecycleview.SwipeRecyclerView;
import com.mingmen.canting.flycotablelayout.flyco.tablayout.SlidingTabLayout;
import com.mingmen.canting.flycotablelayout.flyco.tablayout.listener.OnTabSelectListener;
import com.mingmen.canting.flycotablelayout.flyco.tablayout.widget.MsgView;
import com.mingmen.canting.http.MyOkhttp;
import com.mingmen.canting.http.callback.StringCallback;
import com.mingmen.canting.httpconfig.HttpResponse;
import com.mingmen.canting.immersionbar.ImmersionBar;
import com.mingmen.canting.jingli.JingLiCaiGouDanActivity;
import com.mingmen.canting.model.NewTestBean;
import com.mingmen.canting.model.ShopBean;
import com.mingmen.canting.model.TestBean;
import com.mingmen.canting.popwindow.PopupWindowCompat;
import com.mingmen.canting.tabuse.ui.SimpleCardFragment;
import com.mingmen.canting.tabuse.ui.SlidingTabActivity;
import com.mingmen.canting.util.GsonUtil;
import com.mingmen.canting.util.SharePreferenceUtil;
import com.mingmen.canting.util.ViewFindUtils;

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

public class ZhenBanDingDanActivity extends AppCompatActivity implements OnTabSelectListener {

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
    private MyPagerAdapter mAdapter;
    String token;
    List<ShopBean.DataBean> data = new ArrayList<>();
    TimePickerView pvTime;
    SlidingTabLayout tabLayout_2;
    MyLabelAdapter myLabelAdapter;
    private String[] mVals = new String[]
            {"一次性筷子", "餐巾纸", "大白菜(大) ", "大白菜(小)", "白菜", "大头菜",
                    "奶白菜", "奶白菜"};
    ViewPager vp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhenbandingdan);
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(mToolbar)
                .navigationBarColor(R.color.black).init();
        popWindowCreate();
        canldaCreate();
        token = (String) SharePreferenceUtil.get(this, "token", "");
        View decorView = getWindow().getDecorView();
        vp = ViewFindUtils.find(decorView, R.id.vp);
        tabLayout_2 = ViewFindUtils.find(decorView, R.id.tl_2);
/*        HttpResponse.getShop(token, "0", "2", new StringCallback(this) {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                Log.d("sdkdkddk", s);

                ShopBean shopBean = new ShopBean();
                Gson gson = new Gson();
                shopBean = gson.fromJson(s, ShopBean.class);
                data = shopBean.getData();
                for (int i = 0; i < shopBean.getData().size(); i++) {

                    mFragments.add(DingDanFragment3.getInstance(s));
                }
                mAdapter = new MyPagerAdapter(getSupportFragmentManager());
                vp.setAdapter(mAdapter);
                mAdapter.setDataList(data);

                tabLayout_2.setViewPager(vp);
            }
        });*/
        setData();
        tabLayout_2.setOnTabSelectListener(this);
    }
    public void setData() {
        HttpResponse.getShop(token, "0", "2", new StringCallback(this) {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                Log.d("sdkdkddk", s);
                ShopBean shopBean = new ShopBean();
                Gson gson = new Gson();
                shopBean = gson.fromJson(s, ShopBean.class);
                data = shopBean.getData();
                for (int i = 0; i < shopBean.getData().size(); i++) {
                    mFragments.add(DingDanFragment3.getInstance(s));
                }
                mAdapter = new MyPagerAdapter(getSupportFragmentManager());
                vp.setAdapter(mAdapter);
                mAdapter.setDataList(data);
                tabLayout_2.setViewPager(vp);
            }
        });
    }


    @Override
    public void onTabSelect(int position) {
        Toast.makeText(ZhenBanDingDanActivity.this, position + "", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTabReselect(int position) {
        Toast.makeText(ZhenBanDingDanActivity.this, position + "", Toast.LENGTH_LONG).show();
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        List<ShopBean.DataBean> mylist;

        public void setDataList(List<ShopBean.DataBean> mylist) {
            this.mylist = mylist;
            for (int i = 0; i < mylist.size(); i++) {
                Log.d("setDataList", mylist.get(i).getCategoryName());
            }
            notifyDataSetChanged();
        }

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
//            return mTitles[position];
            Log.d("dkdkkdkdkdkddwer", mylist.get(position).getCategoryName());
            return mylist.get(position).getCategoryName();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

    PopupWindowCompat popupWindow;

    @OnClick({R.id.text_return, R.id.layout_more, R.id.layout_riqi, R.id.linear_return})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_return:
                break;


            case R.id.layout_more:


                popupWindow.showAsDropDown(tabLayout_2);
                break;
            case R.id.layout_riqi:
                pvTime.show(layoutRiqi);
                break;
            case R.id.linear_return:
                finish();
                break;
        }
    }

    public void canldaCreate() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2014, 1, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2027, 2, 28);
        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                Toast.makeText(ZhenBanDingDanActivity.this, getTime(date), Toast.LENGTH_SHORT).show();
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

    public void popWindowCreate() {
        popupWindow = new PopupWindowCompat(this);
        View contentView = LayoutInflater.from(this).inflate(R.layout.poplabel, null);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setContentView(contentView);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        TextView textView = contentView.findViewById(R.id.tv_kdd);
        textView.setText("");
        LinearLayout layout_content = contentView.findViewById(R.id.layout_content);
        layout_content.getBackground().setAlpha(180);
        SwipeRecyclerView recyclerView = contentView.findViewById(R.id.recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ZhenBanDingDanActivity.this, 4);
        recyclerView.setLayoutManager(gridLayoutManager);
        myLabelAdapter = new MyLabelAdapter(ZhenBanDingDanActivity.this);
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
                Toast.makeText(ZhenBanDingDanActivity.this, myLiveList.get(pos) + "", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();

            }
        });
        DividerItemDecoration divider = new DividerItemDecoration(ZhenBanDingDanActivity.this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(ZhenBanDingDanActivity.this, R.drawable.custom_divider));

        recyclerView.addItemDecoration(divider);
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}
