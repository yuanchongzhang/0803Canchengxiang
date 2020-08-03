package com.mingmen.canting.zhenban;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mingmen.canting.R;
import com.mingmen.canting.activity.BohuiYuanyinActivity;
import com.mingmen.canting.adapter.BohuiListAdapter;
import com.mingmen.canting.adapter.DaishenheListAdapter;
import com.mingmen.canting.adapter.YiShenHeListAdapter;
import com.mingmen.canting.base.BaseBean;
import com.mingmen.canting.definerecycleview.SwipeRecyclerView;
import com.mingmen.canting.defineview.ToastDialog;
import com.mingmen.canting.http.MyOkhttp;
import com.mingmen.canting.http.callback.StringCallback;
import com.mingmen.canting.model.MyLiveList;
import com.mingmen.canting.model.ZhenBanShenHe;
import com.mingmen.canting.util.DividerItemDecoration;
import com.mingmen.canting.util.GsonUtil;
import com.mingmen.canting.util.SharePreferenceUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Response;

@SuppressLint("ValidFragment")
public class BohuiListragment extends Fragment implements BohuiListAdapter.OnItemClickListener {

    @BindView(R.id.recyclerview)
    SwipeRecyclerView mRecyclerview;

    Unbinder unbinder;
    private String mTitle;
    //Fragment的View加载完毕的标记
    private boolean isLoading = false;

    //Fragment对用户可见的标记
    private boolean isUIVisible;

    //    private static final int MYLIVE_MODE_CHECK = 0;
    private static final int MYLIVE_MODE_CHECK = 0;
    private static final int MYLIVE_MODE_EDIT = 1;
    private BohuiListAdapter yiShenHeListAdapter = null;
    private LinearLayoutManager mLinearLayoutManager;
    private List<ZhenBanShenHe.DataBean> mList = new ArrayList<>();
    private int mEditMode = MYLIVE_MODE_CHECK;
    private boolean isSelectAll = false;
    private boolean editorStatus = false;
    private int index = 0;

    public static BohuiListragment getInstance(String title) {
        BohuiListragment sf = new BohuiListragment();
        sf.mTitle = title;
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    ToastDialog toastDialog;
    String token;
    List<ZhenBanShenHe.DataBean> dataBeanList = new ArrayList<>();
    @BindView(R.id.frame_layout)
    FrameLayout frame_layout;
    @BindView(R.id.layout_nodata)
    LinearLayout layout_nodata;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bohui, null);
        TextView card_title_tv = (TextView) v.findViewById(R.id.card_title_tv);
        card_title_tv.setText(mTitle);
        token = (String) SharePreferenceUtil.get(getActivity(), "token", "");
        unbinder = ButterKnife.bind(this, v);
        initView();
//        initData();
        initListener();

        toastDialog = new ToastDialog(getActivity());
        toastDialog.setVerifyClickListener("该条订单正在被审核", new ToastDialog.VerifyClickListener() {
            @Override
            public void cancel() {

            }

            @Override
            public void send(String str) {

            }
        });
        MyOkhttp.get("http://192.168.0.18:8801/restaurant/apply/getApplyBillList")
                .params("token", token)
                .params("st", "0")
                .params("startTime", "")
                .params("endTime", "")
                .params("flag", 1).execute(new StringCallback(getActivity()) {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                BaseBean baseBean = new BaseBean();

                ZhenBanShenHe zhenBanShenHe = new ZhenBanShenHe();
                zhenBanShenHe = GsonUtil.GsonToBean(s, ZhenBanShenHe.class);
                dataBeanList = zhenBanShenHe.getData();
                if (dataBeanList.size() <= 0) {
                    frame_layout.setVisibility(View.VISIBLE);
                    layout_nodata.setVisibility(View.GONE);
                } else {
                    layout_nodata.setVisibility(View.VISIBLE);
                    frame_layout.setVisibility(View.GONE);
                }

                yiShenHeListAdapter = new BohuiListAdapter(getActivity());
                mRecyclerview.setAdapter(yiShenHeListAdapter);
                Log.d("dataBeanListdataBeanList", dataBeanList.size() + "");
//                yiShenHeListAdapter.notifyAdapter(dataBeanList, false);

            /*    yiShenHeListAdapter.setOnItemClickListener(new BohuiListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClickListener(View view, int pos, BohuiListAdapter.ViewName viewName, List<ZhenBanShenHe.DataBean> myLiveLists) {

                    }
                });*/
            }
        });

        return v;
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void initView() {
    }

    private void initData() {
        yiShenHeListAdapter = new BohuiListAdapter(getActivity());
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerview.setLayoutManager(mLinearLayoutManager);
        DividerItemDecoration itemDecorationHeader = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        itemDecorationHeader.setDividerDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divider_main_bg_height_1));
        mRecyclerview.addItemDecoration(itemDecorationHeader);

        mRecyclerview.setAdapter(yiShenHeListAdapter);
        for (int i = 0; i < 30; i++) {
            MyLiveList myLiveList = new MyLiveList();
            myLiveList.setTitle(i + "");
            myLiveList.setSource("来源" + i);
//            mList.add(myLiveList);
//            yiShenHeListAdapter.notifyAdapter(mList, false);
        }


    }

    /**
     * 根据选择的数量是否为0来判断按钮的是否可点击.
     *
     * @param size
     */
    private void setBtnBackground(int size) {
        if (size != 0) {

        } else {

        }
    }

    private void initListener() {
//        yiShenHeListAdapter.setOnItemClickListener(this);

    }

    @OnClick({R.id.recyclerview})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.recyclerview:
                break;


        }
    }

    @Override
    public void onItemClickListener(View view, int pos, BohuiListAdapter.ViewName viewName, List<MyLiveList> myLiveLists) {

    }


 /*   @Override
    public void onItemClickListener(View view, int pos, BohuiListAdapter.ViewName viewName, List<ZhenBanShenHe.DataBean> myLiveLists) {

        }
    }*/

/*    @Override
    public void onItemClickListener(View view, int pos, BohuiListAdapter.ViewName viewName, List<MyLiveList> myLiveLists) {
        switch (view.getId()) {
            case R.id.btn_zhuijia:
                Toast.makeText(getActivity(), "追加" + pos, Toast.LENGTH_SHORT).show();
                break;
            case R.id.layout_bohui:
//                Toast.makeText(getActivity(), "驳回原因" + pos, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), BohuiYuanyinActivity.class));

                break;
    }*/
}