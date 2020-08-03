package com.mingmen.canting.zhenban;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mingmen.canting.R;
import com.mingmen.canting.activity.CaiGouDanActivity;
import com.mingmen.canting.adapter.IndexAdapter;
import com.mingmen.canting.adapter.MineRadioAdapter;
import com.mingmen.canting.chushizhang.ChuShiZhangCaiGouDanActivity;
import com.mingmen.canting.definerecycleview.SwipeRecyclerView;
import com.mingmen.canting.flycotablelayout.flyco.tablayout.SlidingTabLayout;
import com.mingmen.canting.flycotablelayout.flyco.tablayout.listener.OnTabSelectListener;
import com.mingmen.canting.flycotablelayout.flyco.tablayout.widget.MsgView;
import com.mingmen.canting.immersionbar.ImmersionBar;
import com.mingmen.canting.jingli.JingLiCaiGouDanActivity;
import com.mingmen.canting.model.MyLiveList;
import com.mingmen.canting.util.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class IndexFragment extends Fragment implements View.OnClickListener {

    SwipeRecyclerView rv_index;
    private static final int MYLIVE_MODE_CHECK = 0;
    private static final int MYLIVE_MODE_EDIT = 1;
    private IndexAdapter mRadioAdapter = null;
    private GridLayoutManager mLinearLayoutManager;
    private List<MyLiveList> mList = new ArrayList<>();
    private int mEditMode = MYLIVE_MODE_CHECK;
    private boolean isSelectAll = false;
    private boolean editorStatus = false;
    private int index = 0;
    LinearLayout layout_caigou;
    ImageView img_banner;
    LinearLayout layout_jingli;
    LinearLayout layout_caigoudan;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index, null);
        rv_index = view.findViewById(R.id.rv_index);
        initData();
        layout_caigou = view.findViewById(R.id.layout_caigou);
        img_banner = view.findViewById(R.id.img_banner);
        layout_jingli = view.findViewById(R.id.layout_jingli);
        layout_jingli.setOnClickListener(this);
        layout_caigoudan = view.findViewById(R.id.layout_caigoudan);
        layout_caigoudan.setOnClickListener(this);
        Glide.with(getActivity()).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1596014050715&di=f439675695f816befd04e5e1bc236d1a&imgtype=0&src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fphotoblog%2F4%2F0%2F3%2F4%2F4034297%2F20067%2F2%2F1151851112386.JPG").into(img_banner);

        layout_caigou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), ZhenBanCaiGouDanActivity.class));
                startActivity(new Intent(getActivity(), ZhenBanCaiGouDanActivity.class));
            }
        });
        return view;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void initData() {
        mRadioAdapter = new IndexAdapter(getActivity());
        mLinearLayoutManager = new GridLayoutManager(getActivity(), 3);
        rv_index.setLayoutManager(mLinearLayoutManager);
        DividerItemDecoration itemDecorationHeader = new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL_LIST);
//        rv_index.setDividerDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divider_main_bg_height_1));
//        mRecyclerview.addItemDecoration(itemDecorationHeader);

        rv_index.setAdapter(mRadioAdapter);
        for (int i = 0; i < 30; i++) {
            MyLiveList myLiveList = new MyLiveList();
            myLiveList.setTitle("这是第" + i + "个条目");
            myLiveList.setSource("来源" + i);
            mList.add(myLiveList);
            mRadioAdapter.notifyAdapter(mList, false);
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_jingli:
                startActivity(new Intent(getActivity(), JingLiCaiGouDanActivity.class));
                break;
            case R.id.layout_caigoudan:
                startActivity(new Intent(getActivity(), ChuShiZhangCaiGouDanActivity.class));
                break;
        }
    }
}
