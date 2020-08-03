package com.mingmen.canting.zhenban;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mingmen.canting.R;
import com.mingmen.canting.activity.CaiGouDanActivity;
import com.mingmen.canting.adapter.LeftMenuAdapter;
import com.mingmen.canting.adapter.MyLabelAdapter;
import com.mingmen.canting.adapter.RightDishAdapter;
import com.mingmen.canting.base.BaseBean;
import com.mingmen.canting.definerecycleview.SwipeRecyclerView;
import com.mingmen.canting.flycotablelayout.flyco.tablayout.SlidingTabLayout;
import com.mingmen.canting.flycotablelayout.flyco.tablayout.listener.OnTabSelectListener;
import com.mingmen.canting.flycotablelayout.flyco.tablayout.widget.MsgView;
import com.mingmen.canting.fragment.Daicaigouragment;
import com.mingmen.canting.fragment.ZhexianFagment;
import com.mingmen.canting.http.callback.StringCallback;
import com.mingmen.canting.httpconfig.HttpResponse;
import com.mingmen.canting.immersionbar.ImmersionBar;
import com.mingmen.canting.jingli.JingLiCaiGouDanActivity;
import com.mingmen.canting.model.CouneModel;
import com.mingmen.canting.model.Dish;
import com.mingmen.canting.model.DishMenu;
import com.mingmen.canting.model.ShopCart;
import com.mingmen.canting.popwindow.PopupWindowCompat;
import com.mingmen.canting.tabuse.ui.SimpleCardFragment;
import com.mingmen.canting.util.FakeAddImageView;
import com.mingmen.canting.util.GsonUtil;
import com.mingmen.canting.util.PointFTypeEvaluator;
import com.mingmen.canting.util.SharePreferenceUtil;
import com.mingmen.canting.util.ShopCartDialog;
import com.mingmen.canting.util.ShopCartImp;
import com.mingmen.canting.util.ViewFindUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class ZhenBanCaiGouDanActivity extends FragmentActivity implements OnTabSelectListener {
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

    private Context mContext = this;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {
            "待审核", "已审核", "被驳回"
    };
    private ZhenBanCaiGouDanActivity.MyPagerAdapter mAdapter;
    SlidingTabLayout tabLayout_2;
    String token;
    int str_pos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhenbancaigou);
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(mToolbar)
                .navigationBarColor(R.color.black).init();

        textCenter.setText("采购单");
        token = (String) SharePreferenceUtil.get(this, "token", "");
        getCountBohui();
        mFragments.add(DaiShenHeListragment.getInstance(mTitles[0]));
        mFragments.add(YiShenHeListragment.getInstance(mTitles[1]));
        mFragments.add(BohuiListragment.getInstance(mTitles[1]));
        imgRight.setVisibility(View.VISIBLE);
        View decorView = getWindow().getDecorView();
        ViewPager vp = ViewFindUtils.find(decorView, R.id.vp);
        mAdapter = new ZhenBanCaiGouDanActivity.MyPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(mAdapter);
        /**自定义部分属性*/
        tabLayout_2 = ViewFindUtils.find(decorView, R.id.tl_2);
        /** indicator固定宽度 */
        tabLayout_2.setViewPager(vp);
        tabLayout_2.setOnTabSelectListener(this);

        vp.setCurrentItem(0);

//        tabLayout_2.showMsg(2, 5);
        tabLayout_2.setMsgMargin(2, 0, 10);
        MsgView rtv_2_3 = tabLayout_2.getMsgView(3);
        if (rtv_2_3 != null) {
            rtv_2_3.setBackgroundColor(Color.parseColor("#6D8FB0"));
        }
//        tabLayout_2.showMsg(2, 5);

        if (str_pos==0){

        }else {
            tabLayout_2.showMsg(2, str_pos);
        }

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
    }

    public void getCountBohui() {
        HttpResponse.getBohuiCount(token, "", "", new StringCallback(this) {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                BaseBean baseBean=GsonUtil.GsonToBean(s,BaseBean.class);
                Log.d("c", s);
                if (baseBean.getCode()==500){

                }else {

                    CouneModel couneModel = new CouneModel();
                    couneModel = GsonUtil.GsonToBean(s, CouneModel.class);

                    str_pos = couneModel.getData().get(0);
                }


            }
        });
    }


    @Override
    public void onTabSelect(int position) {
        Toast.makeText(ZhenBanCaiGouDanActivity.this, position + "", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTabReselect(int position) {
        Toast.makeText(ZhenBanCaiGouDanActivity.this, position + "", Toast.LENGTH_LONG).show();
    }

    @OnClick({R.id.text_return, R.id.text_center, R.id.img_right, R.id.tv_right, R.id.linear_return})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_return:
                break;
            case R.id.text_center:
                break;
            case R.id.img_right:
                PopupWindowCompat popupWindow = new PopupWindowCompat(this);
                View contentView = LayoutInflater.from(this).inflate(R.layout.pop_zhenbanl, null);
                popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                popupWindow.setContentView(contentView);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.showAsDropDown(mToolbar);
                LinearLayout layout_content = contentView.findViewById(R.id.layout_content);
                layout_content.getBackground().setAlpha(180);
                break;
            case R.id.tv_right:
                break;
            case R.id.linear_return:
                finish();
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
