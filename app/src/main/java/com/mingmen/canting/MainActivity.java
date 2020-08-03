package com.mingmen.canting;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mingmen.canting.activity.ListActivity;
import com.mingmen.canting.activity.RefreshActivity;
import com.mingmen.canting.http.MyOkhttp;
import com.mingmen.canting.http.callback.StringCallback;
import com.mingmen.canting.immersionbar.ImmersionBar;
import com.mingmen.canting.model.TestBean;
import com.mingmen.canting.tabuse.ui.SimpleHomeActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_normalrecycleview)
    TextView tvNormalrecycleview;
    @BindView(R.id.tv_normalrecycleview2)
    TextView tvNormalrecycleview2;
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_normalrecycleview3)
    TextView tvNormalrecycleview3;
    List<TestBean.ResultBean.ContentBean> resultBeanList = new ArrayList<>();
    @BindView(R.id.tv_normalrecycleview4)
    TextView tvNormalrecycleview4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(mToolbar)
                .navigationBarColor(R.color.black).init();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = Math.min(dm.widthPixels,dm.heightPixels);
//        tv1.setText("dpi : "+dm.densityDpi +"   smallest width pixels : "+width);
//        tv2.setText("计算出来的smallestWidth : "+width/(dm.densityDpi/160.0) +"dp");
//        tv3.setText("实际使用的smallestWidth :  "+getResources().getString(R.string.base_dpi));
//        tv4.setText("当前手机： "+SystemUtil.getDeviceBrand()+"  "+SystemUtil.getSystemModel()+ " \n"+"当前系统： "+SystemUtil.getSystemVersion()+ " ");
//        LinearLayout.LayoutParams p= (LinearLayout.LayoutParams) view.getLayoutParams();
//        p.width = getResources().getDimensionPixelSize(R.dimen.qb_px_375);
//        view.setLayoutParams(p);
        /*HttpResponseUtilApi.getData2(new StringNoDialogCallback(this) {


            @Override
            public void onSuccess(String s, Call call, Response response) {
                ObjectResult<TestBean> objectResult=new ObjectResult<>();

            }
        });*/
        MyOkhttp.get("https://api.apiopen.top/musicRankings")
                .tag(this).execute(new StringCallback(this) {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                Log.d("mainactivity",s);
            }
        });


    }


    @OnClick({R.id.tv_normalrecycleview, R.id.tv_normalrecycleview2, R.id.tv_normalrecycleview3,R.id.tv_normalrecycleview4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_normalrecycleview:
                startActivity(new Intent(MainActivity.this, ListActivity.class));

                break;
            case R.id.tv_normalrecycleview2:
                startActivity(new Intent(MainActivity.this, SimpleHomeActivity.class));
                break;
            case R.id.tv_normalrecycleview3:
                startActivity(new Intent(MainActivity.this, RefreshActivity.class));
                break;
            case R.id.tv_normalrecycleview4:

                break;
        }
    }



}
