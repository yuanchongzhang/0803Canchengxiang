package com.mingmen.canting.zhenban;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mingmen.canting.R;
import com.mingmen.canting.flycotablelayout.flyco.tablayout.SlidingTabLayout;
import com.mingmen.canting.flycotablelayout.flyco.tablayout.listener.OnTabSelectListener;
import com.mingmen.canting.flycotablelayout.flyco.tablayout.widget.MsgView;
import com.mingmen.canting.immersionbar.ImmersionBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class ZhenBanDaiShenHeFragment extends Fragment implements OnTabSelectListener {


    @BindView(R.id.text_return)
    TextView textReturn;
    @BindView(R.id.linear_return)
    LinearLayout linearReturn;
    @BindView(R.id.text_center)
    TextView textCenter;
    @BindView(R.id.img_right)
    ImageView imgRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;

    SlidingTabLayout tabLayout_2;


    ViewPager vp;
    Unbinder unbinder;
    private Context mContext = getActivity();
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {
            "待审核", "已审核", "被驳回"
    };
    private MyPagerAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zhenbandai, null);

        unbinder = ButterKnife.bind(this, view);

        mContext = getActivity();
       /* for (String title : mTitles) {
            mFragments.add(SimpleCardFragment.getInstance(title));
        }*/
        mFragments.add(DaiShenHeListragment.getInstance(mTitles[0]));
        mFragments.add(YiShenHeListragment.getInstance(mTitles[1]));
        mFragments.add(BohuiListragment.getInstance(mTitles[2]));
        imgRight.setVisibility(View.VISIBLE);


        ImmersionBar.with(this).titleBar(mToolbar)
                .navigationBarColor(R.color.black).init();

        imgRight.setVisibility(View.VISIBLE);
        View decorView = getActivity().getWindow().getDecorView();
//        ViewPager vp = ViewFindUtils.find(decorView, R.id.vp);
        ViewPager vp = view.findViewById(R.id.vp);
        mAdapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
        vp.setAdapter(mAdapter);
        /**自定义部分属性*/
//        SlidingTabLayout tabLayout_2 = ViewFindUtils.find(decorView, R.id.tl_2);
        SlidingTabLayout tabLayout_2 = view.findViewById(R.id.tl_2);
        /** indicator固定宽度 */
        tabLayout_2.setViewPager(vp);
        tabLayout_2.setOnTabSelectListener(this);
        vp.setCurrentItem(0);
//        tabLayout_2.showMsg(2, 5);
//        tabLayout_2.setMsgMargin(3, 0, 10);
        MsgView rtv_2_3 = tabLayout_2.getMsgView(2);
        if (rtv_2_3 != null) {
            rtv_2_3.setBackgroundColor(Color.parseColor("#E84343"));
        }
        tabLayout_2.showMsg(2, 5);
        tabLayout_2.setMsgMargin(2, 4, 5);

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
        return view;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }


    @OnClick({R.id.text_return, R.id.linear_return, R.id.img_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_return:

                break;
            case R.id.linear_return:
                break;
            case R.id.img_right:
                break;
        }
    }

    @Override
    public void onTabSelect(int position) {
        Toast.makeText(getActivity(), position + "", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTabReselect(int position) {
        Toast.makeText(getActivity(), position + "", Toast.LENGTH_LONG).show();
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
