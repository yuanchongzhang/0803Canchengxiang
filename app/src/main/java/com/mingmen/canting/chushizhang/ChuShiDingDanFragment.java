package com.mingmen.canting.chushizhang;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mingmen.canting.R;
import com.mingmen.canting.adapter.ChuShiDingDanAdapter;
import com.mingmen.canting.model.SimpleData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

@SuppressLint("ValidFragment")
public class ChuShiDingDanFragment extends Fragment {
    @BindView(R.id.recycle_first)
    RecyclerView recycleFirst;
    Unbinder unbinder;
    private String mTitle;
    //Fragment的View加载完毕的标记
    private boolean isLoading = false;

    //Fragment对用户可见的标记
    private boolean isUIVisible;
    private List<SimpleData> list = new ArrayList<>();
    private List<SimpleData.ChildData> childList1 = new ArrayList<>();
    private List<SimpleData.ChildData> childList2 = new ArrayList<>();
    ChuShiDingDanAdapter chuShiDingDanAdapter;

    public static ChuShiDingDanFragment getInstance(String title) {
        ChuShiDingDanFragment sf = new ChuShiDingDanFragment();
        sf.mTitle = title;
        Log.d("dkdkdkdkasdf", sf.mTitle);
        return sf;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layoutchushi, null);
        unbinder = ButterKnife.bind(this, v);
        TextView card_title_tv = (TextView) v.findViewById(R.id.card_title_tv);
        card_title_tv.setText(mTitle);
        childList1.add(new SimpleData.ChildData("a"));
        childList1.add(new SimpleData.ChildData("b"));
        childList1.add(new SimpleData.ChildData("c"));
        childList1.add(new SimpleData.ChildData("d"));
        childList1.add(new SimpleData.ChildData("e"));
        list.add(new SimpleData("A", childList1));

        childList2.add(new SimpleData.ChildData("f"));
        childList2.add(new SimpleData.ChildData("j"));
        childList2.add(new SimpleData.ChildData("h"));
        childList2.add(new SimpleData.ChildData("i"));
        childList2.add(new SimpleData.ChildData("j"));
        list.add(new SimpleData("B", childList2));

        chuShiDingDanAdapter = new ChuShiDingDanAdapter(getActivity(), list);
        recycleFirst.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycleFirst.setAdapter(chuShiDingDanAdapter);
        chuShiDingDanAdapter.setOnitemClickLintener(new ChuShiDingDanAdapter.OnitemClick() {
            @Override
            public void onItemClick(int position, List<SimpleData.ChildData> mList) {
                Toast.makeText(getActivity(), mList.get(position).childName+"", Toast.LENGTH_SHORT).show();
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

    @OnClick(R.id.recycle_first)
    public void onViewClicked() {
    }


    /**
     * 设置一个public方法,供adapter点击事件调用
     *
     * @param position 为第一层recycleview位置
     * @param tag      为第二层recycleview位置
     */
/*    public void OnClickListener(int position, int tag) {
        final List<SimpleData> datasBeans = list;
        for (int i = 0; i < datasBeans.size(); i++) {
            if (i == position) {
                List<SimpleData.ChildData> option = datasBeans.get(i).list;
                for (int j = 0; j < option.size(); j++) {
                    if (j == tag) {
                        option.get(j).select = (true);
                    } else {
                        option.get(j).select = (false);
                    }
                }
                Toast.makeText(getActivity(),
                        datasBeans.get(position).typeName + "-" + option.get(tag).childName,
                        Toast.LENGTH_SHORT).show();

            } else {
                //这里让之前选中的效果还原成未选中
                List<SimpleData.ChildData> option = datasBeans.get(i).list;
                for (int j = 0; j < option.size(); j++) {
                    option.get(j).select = (false);
                }
            }
        }
        chuShiDingDanAdapter.notifyDataSetChanged();
    }*/
}