package com.mingmen.canting.chushizhang;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mingmen.canting.R;
import com.mingmen.canting.adapter.CaigouAdapter;
import com.mingmen.canting.flycotablelayout.flyco.tablayout.SegmentTabLayout;
import com.mingmen.canting.flycotablelayout.flyco.tablayout.listener.OnTabSelectListener;
import com.mingmen.canting.materialcalendar.DateTimeHelper;
import com.mingmen.canting.materialcalendar.MaterialCalendarDialog;
import com.mingmen.canting.model.GoodsInfo;
import com.mingmen.canting.model.StoreInfo;
import com.mingmen.canting.tabuse.ui.SegmentTabActivity;
import com.mingmen.canting.tabuse.ui.SimpleCardFragment;
import com.mingmen.canting.util.UtilTool;
import com.mingmen.canting.util.UtilsLog;
import com.mingmen.canting.util.ViewFindUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

@SuppressLint("ValidFragment")
public class ChuShiShenHefragment extends Fragment {

    private String mTitle;

    private boolean isLoading = false;
    //Fragment对用户可见的标记
    private boolean isUIVisible;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    SegmentTabLayout mTabLayout_3;

    public static ChuShiShenHefragment getInstance(String title) {
        ChuShiShenHefragment sf = new ChuShiShenHefragment();
        sf.mTitle = title;
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private String[] mTitles = {"订单", "种类"};

    SegmentTabLayout tabLayout_4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_chushidingdan, null);


        mFragments.add(ChuShiDingDanFragment.getInstance(mTitles[0]));
        mFragments.add(SimpleCardFragment.getInstance(mTitles[1]));
        tabLayout_4 = v.findViewById(R.id.tl_4);
        tabLayout_4.setTabData(mTitles, getActivity(), R.id.fl_change, mFragments);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isUIVisible = true;
            lazyLoad();//调用下面的方法


        } else {
            isUIVisible = false;
        }
    }

    private void lazyLoad() {
        if (!isLoading && isUIVisible) {
//            这里只加载一次数据
            Log.d("SimpleCardFragment", "66666");
            //数据加载完毕,恢复标记,防止重复加载
            isLoading = true;
            isUIVisible = false;

        }

    }

}