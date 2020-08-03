package com.mingmen.canting.jingli;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mingmen.canting.R;
import com.mingmen.canting.adapter.LinkLabelAdapter;
import com.mingmen.canting.adapter.MyLabelAdapter;
import com.mingmen.canting.model.LinkLabelBean;
import com.mingmen.canting.zhenban.BohuiListragment;
import com.mingmen.canting.zhenbanlibrary.ClassifyNewAdapter;
import com.mingmen.canting.zhenbanlibrary.MyGoodsInfo;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class JingLiCaiGouFragment extends Fragment implements JingLiCaiGouAdapter.OnItemClickListener {
    private RecyclerView mRv;
    private JingLiCaiGouAdapter mAdapter;


    TextView tv_total;
    CheckBox cb_check;
    RecyclerView recycle_liebiao;

    List<MyGoodsInfo> myGoodsInfos = new ArrayList<>();
    MyGoodsInfo myGoodsInfo;
    LinearLayoutManager linearLayoutManager;
    GridLayoutManager gridLayoutManager;

    String mTitle;

    private String[] mVals = new String[]
            {"一次性筷子", "餐巾纸", "大白菜(大) ", "大白菜(小)", "白菜", "大头菜",
                    "奶白菜", "奶白菜"};

    public static JingLiCaiGouFragment getInstance(String title) {
        JingLiCaiGouFragment sf = new JingLiCaiGouFragment();
        sf.mTitle = title;
        return sf;
    }

    RecyclerView recycle_biaoqian;

    MyLabelAdapter myLabelAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jinglicaigou, null);
        mRv = (RecyclerView) view.findViewById(R.id.rv);
        tv_total = (TextView) view.findViewById(R.id.tv_total);
        cb_check = (CheckBox) view.findViewById(R.id.cb_check);
        recycle_liebiao = view.findViewById(R.id.recycle_liebiao);
        recycle_biaoqian = view.findViewById(R.id.recycle_biaoqian);
        Log.d("dkdkkd", "到这里了么");
        myGoodsInfo = new MyGoodsInfo();
        for (int i = 0; i < 10; i++) {
            myGoodsInfo.setImgsrc("234324");
            myGoodsInfo.setName("3298923");
            myGoodsInfo.setTag("234324");
            myGoodsInfo.setTitle(true);
            myGoodsInfos.add(myGoodsInfo);
        }

        linearLayoutManager = new LinearLayoutManager(getActivity());
        recycle_liebiao.setLayoutManager(linearLayoutManager);

        mAdapter = new JingLiCaiGouAdapter(getActivity());
        recycle_liebiao.setAdapter(mAdapter);
        for (int i = 0; i < myGoodsInfos.size(); i++) {
            Log.d("dkdk", myGoodsInfo.getImgsrc());
        }
        mAdapter.setDataList(myGoodsInfos);
        mAdapter.setOnItemClickListener(this);
        cb_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (cb_check.isChecked()) {
                        for (int i = 0; i < myGoodsInfos.size(); i++) {
                            if (myGoodsInfos.get(i).getCount() == 0) {
                                myGoodsInfos.get(i).setChecked(false);
                            } else {
                                myGoodsInfos.get(i).setChecked(true);
                            }

                        }
                        showTotalPrice();
                        mAdapter.notifyDataSetChanged();
                    }

                } else {
                    for (int i = 0; i < myGoodsInfos.size(); i++) {
                        myGoodsInfos.get(i).setChecked(false);
                    }
                    showTotalPrice();
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
        gridLayoutManager = new GridLayoutManager(getActivity(), 4);
        recycle_biaoqian.setLayoutManager(gridLayoutManager);


        myLabelAdapter = new MyLabelAdapter(getActivity());
        recycle_biaoqian.setAdapter(myLabelAdapter);
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
            }
        });
//        recycle_biaoqian.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        DividerItemDecoration divider=new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getActivity(),R.drawable.custom_divider));

        recycle_biaoqian.addItemDecoration(divider);
        return view;
    }


    @Override
    public void onItemClickListener(View view, int pos, List<MyGoodsInfo> myLiveList) {
        switch (view.getId()) {
            case R.id.checkbox:

                showTotalPrice();
                break;
        }
    }

    public void showTotalPrice() {
        float total = getTotalPrice();
        tv_total.setText(Html.fromHtml("合计 ￥<span style='color:#eb4f38'>" + total + "</span>"),
                TextView.BufferType.SPANNABLE);
    }


    private float getTotalPrice() {

        float sum = 0;
        if (!isNull())
            return sum;

        for (MyGoodsInfo cart : myGoodsInfos) {
            if (cart.isChecked()) {   //是否勾上去了
//                sum += cart.getCount() * cart.getPrice();
                sum += cart.getCount() * 10;
            }
        }
        Log.d("sumsumsumsum", sum + "");
        return sum;
    }


    /**
     * 计算总和
     */

    public boolean isNull() {
        return (myGoodsInfos != null && myGoodsInfos.size() > 0);
    }
}
