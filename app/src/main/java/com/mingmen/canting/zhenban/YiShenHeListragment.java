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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.mingmen.canting.R;
import com.mingmen.canting.activity.QueRenLingQuActivity;
import com.mingmen.canting.activity.YiShenHeActivity;
import com.mingmen.canting.adapter.DaishenheListAdapter;
import com.mingmen.canting.adapter.YiShenHeListAdapter;
import com.mingmen.canting.base.BaseBean;
import com.mingmen.canting.definerecycleview.SwipeRecyclerView;
import com.mingmen.canting.defineview.ToastDialog;
import com.mingmen.canting.http.MyOkhttp;
import com.mingmen.canting.http.callback.StringCallback;
import com.mingmen.canting.httpconfig.HttpResponse;
import com.mingmen.canting.jingli.JingLiCaiGouDanActivity;
import com.mingmen.canting.model.MyLiveList;
import com.mingmen.canting.model.ZhenBanShenHe;
import com.mingmen.canting.util.DividerItemDecoration;
import com.mingmen.canting.util.GsonUtil;
import com.mingmen.canting.util.SharePreferenceUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Response;

@SuppressLint("ValidFragment")
public class YiShenHeListragment extends Fragment implements YiShenHeListAdapter.OnItemClickListener {

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
    private YiShenHeListAdapter yiShenHeListAdapter = null;
    private LinearLayoutManager mLinearLayoutManager;
    TimePickerView pvTime, pvTime2;
    @BindView(R.id.textview_calendafirst)
    TextView textview_calendafirst;
    @BindView(R.id.textview_calendasecond)
    TextView textview_calendasecond;
    public static YiShenHeListragment getInstance(String title) {
        YiShenHeListragment sf = new YiShenHeListragment();
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
    @BindView(R.id.layout_calenda_first)
    LinearLayout layout_calenda_first;
    @BindView(R.id.layout_calenda_second)
    LinearLayout layout_calenda_second;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_yishenhe, null);
        TextView card_title_tv = (TextView) v.findViewById(R.id.card_title_tv);
        card_title_tv.setText(mTitle);

        unbinder = ButterKnife.bind(this, v);
        token = (String) SharePreferenceUtil.get(getActivity(), "token", "");
        initView();
        getCaneda();
//        initData();
        yiShenHeListAdapter = new YiShenHeListAdapter(getActivity(), dataBeanList);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerview.setLayoutManager(mLinearLayoutManager);
        DividerItemDecoration itemDecorationHeader = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        itemDecorationHeader.setDividerDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divider_main_bg_height_1));
        mRecyclerview.addItemDecoration(itemDecorationHeader);
        mRecyclerview.setAdapter(yiShenHeListAdapter);
        getMyData();

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


        return v;
    }

    public void getMyData() {

        HttpResponse.daiShenhe(token, "1", "", "", "1", new StringCallback(getActivity()) {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                Log.d("DaishenHelist", s);

                BaseBean baseBean = GsonUtil.GsonToBean(s, BaseBean.class);
                if (baseBean.getCode() == 500) {
                    Toast.makeText(getActivity(), baseBean.getMessage(), Toast.LENGTH_SHORT).show();
                } else {

                    ZhenBanShenHe zhenBanShenHe = new ZhenBanShenHe();
                    zhenBanShenHe = GsonUtil.GsonToBean(s, ZhenBanShenHe.class);
                    if (dataBeanList.size() > 0) {
                        dataBeanList.clear();
                    }
                    dataBeanList = zhenBanShenHe.getData();
                    if (dataBeanList.size() <= 0) {
//                    frame_layout.setVisibility(View.VISIBLE);
//                    layout_nodata.setVisibility(View.GONE);
                    } else {
//                    layout_nodata.setVisibility(View.VISIBLE);
//                    frame_layout.setVisibility(View.GONE);
                    }
//                    dataBeanList.addAll(zhenBanShenHe.getData());
                    yiShenHeListAdapter.setDataList(dataBeanList);

                    Log.d("YiShenHe", zhenBanShenHe.getData().size() + "");
                }

            }
        });


    }

    public void getCaneda() {


        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2014, 1, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2027, 2, 28);
        pvTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                Toast.makeText(getActivity(), getTime(date), Toast.LENGTH_SHORT).show();
                switch (v.getId()){
                    case R.id.layout_calenda_first:
                        textview_calendafirst.setText(getTime(date));
                        break;
                    case R.id.layout_calenda_second:
                        textview_calendasecond.setText(getTime(date));
                        break;
                }


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
       /* yiShenHeListAdapter = new YiShenHeListAdapter(getActivity());
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
            mList.add(myLiveList);
            yiShenHeListAdapter.notifyAdapter(mList, false);
        }
*/

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

    @OnClick({R.id.recyclerview, R.id.layout_calenda_first, R.id.layout_calenda_second})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.recyclerview:
                break;
            case R.id.layout_calenda_first:

                break;
            case R.id.layout_calenda_second:

                break;
        }
    }


 @Override
    public void onItemClickListener(View view, int pos, YiShenHeListAdapter.ViewName viewName, List<ZhenBanShenHe.DataBean> myLiveLists) {
        switch (view.getId()) {
            case R.id.btn_zhuijia:
//                Toast.makeText(getActivity(), "追加" + pos, Toast.LENGTH_SHORT).show();
                HttpResponse.addNewPrice(token, myLiveLists.get(pos).getApplyId(), "2020-08-04", new StringCallback(getActivity()) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d("追加追加追加", s);
                        BaseBean baseBean = GsonUtil.GsonToBean(s, BaseBean.class);
                        Toast.makeText(getActivity(), baseBean.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


                break;
            case R.id.btn_submit:
//                startActivity(new Intent(getActivity(), QueRenLingQuActivity.class));
                Intent intent = new Intent(getActivity(), QueRenLingQuActivity.class);
                intent.putExtra("applyId", myLiveLists.get(pos).getApplyId());
//                applyTime
                intent.putExtra("applyTime", myLiveLists.get(pos).getApplyTime());
                startActivity(intent);
                break;
            default:
//                startActivity(new Intent(getActivity(), YiShenHeActivity.class));
                Intent intent2 = new Intent(getActivity(), YiShenHeActivity.class);
                intent2.putExtra("applyId", myLiveLists.get(pos).getApplyId());
//                applyTime
                intent2.putExtra("applyTime", myLiveLists.get(pos).getApplyTime());
                startActivity(intent2);
                break;

        }
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

}