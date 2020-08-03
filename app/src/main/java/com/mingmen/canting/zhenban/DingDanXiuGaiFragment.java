package com.mingmen.canting.zhenban;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mingmen.canting.R;
import com.mingmen.canting.http.callback.StringCallback;
import com.mingmen.canting.httpconfig.HttpResponse;
import com.mingmen.canting.model.ShopListModel;
import com.mingmen.canting.util.SharePreferenceUtil;
import com.mingmen.canting.zhenbanlibrary.CheckListener;
import com.mingmen.canting.zhenbanlibrary.DingDanAdapter;
import com.mingmen.canting.zhenbanlibrary.SortDetailFragment;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

@SuppressLint("ValidFragment")
public class DingDanXiuGaiFragment extends Fragment implements CheckListener {

    private RecyclerView rvSort;
    private DingDanAdapter mSortAdapter;
    private SortXiuGaiFragment mSortDetailFragment;

    private Context mContext;
    private LinearLayoutManager mLinearLayoutManager;
    private int targetPosition;//点击左边某一个具体的item的位置
    private boolean isMoved;

    //Fragment的View加载完毕的标记
    private boolean isLoading = false;

    //Fragment对用户可见的标记
    private boolean isUIVisible;


    String getData;
    String token;
    List<ShopListModel.DataBean> data = new ArrayList<>();
    List<String> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_dingdan, null);
        TextView card_title_tv = (TextView) v.findViewById(R.id.card_title_tv);
        card_title_tv.setText(getData);
        mContext = getActivity();
        rvSort = (RecyclerView) v.findViewById(R.id.rv_sort);
        Log.d("Dkkkc,c,c,c,,c","到这里了么");

//        initData();
        token = (String) SharePreferenceUtil.get(getActivity(), "token", "");
        if (!list.isEmpty()) {
            list.clear();
        }
        Log.d("DingDanFragment3DingDanFragment3", getData);

      /*  ShopBean shopBean = GsonUtil.GsonToBean(getData, ShopBean.class);
        data = shopBean.getData();
        for (int i = 0; i < data.size(); i++) {
            list.add(data.get(i).getCategoryName());
        }
        mLinearLayoutManager = new LinearLayoutManager(mContext);
        rvSort.setLayoutManager(mLinearLayoutManager);

        mSortAdapter = new DingDanAdapter(mContext);
        rvSort.setAdapter(mSortAdapter);
        mSortAdapter.setDataList(list);*/

        HttpResponse.getFenLeiShop(token, "100", new StringCallback(getActivity()) {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                Log.d("tokentoken",s);
                Gson gson = new Gson();
                ShopListModel shopListModel = new ShopListModel();
                shopListModel = gson.fromJson(s, ShopListModel.class);

                data = shopListModel.getData();
                for (int i = 0; i < data.size(); i++) {
                    list.add(data.get(i).getCommodityName());
                }
                mLinearLayoutManager = new LinearLayoutManager(mContext);
                rvSort.setLayoutManager(mLinearLayoutManager);

                mSortAdapter = new DingDanAdapter(mContext);
                rvSort.setAdapter(mSortAdapter);
                mSortAdapter.setDataList(list);
                mSortAdapter.setOnItemClickListener(new DingDanAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClickListener(View view, int pos, List<String> myLiveLists) {
                        setChecked(pos, true);
                    }
                });
           FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                mSortDetailFragment = new SortXiuGaiFragment();

                Bundle bundle = new Bundle();

                bundle.putString("right", s);
                mSortDetailFragment.setArguments(bundle);
//        mSortDetailFragment.setListener(this);
                fragmentTransaction.add(R.id.lin_fragment, mSortDetailFragment);
                fragmentTransaction.commit();
                createFragment();

            }
        });



    /*    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        mSortDetailFragment = new SortDetailFragment();
//        mSortDetailFragment = new SortDetailFragment();
        Bundle bundle = new Bundle();
//        bundle.putParcelableArrayList("right", (ArrayList<? extends Parcelable>) twoArrayBeans);
        bundle.putString("right", twoArrayBeans.toString());
        mSortDetailFragment.setArguments(bundle);
//        mSortDetailFragment.setListener(this);
        fragmentTransaction.add(R.id.lin_fragment, mSortDetailFragment);
        fragmentTransaction.commit();
        createFragment();*/
        return v;
    }



    @Override
    public void onResume() {
        super.onResume();

    }



    public static DingDanXiuGaiFragment getInstance(String title) {
        DingDanXiuGaiFragment sf = new DingDanXiuGaiFragment();
        sf.getData = title;
        Log.d("DingDandkdkdkd", title);

        return sf;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    private void setChecked(int position, boolean isLeft) {
        Log.d("p-------->", String.valueOf(position));
        if (isLeft) {
            mSortAdapter.setCheckedPosition(position);
            //此处的位置需要根据每个分类的集合来进行计算
            int count = 0;
            for (int i = 0; i < position; i++) {
//                count += mSortBean.getCategoryOneArray().get(i).getCategoryTwoArray().size();
            }
            count += position;

//            mSortDetailFragment.setData(count);
//            ItemHeaderDecoration.setCurrentTag(String.valueOf(targetPosition));//凡是点击左边，将左边点击的位置作为当前的tag
        } else {
            if (isMoved) {
                isMoved = false;
            } else
                mSortAdapter.setCheckedPosition(position);
//            ItemHeaderDecoration.setCurrentTag(String.valueOf(position));//如果是滑动右边联动左边，则按照右边传过来的位置作为tag

        }
        moveToCenter(position);

    }

    //将当前选中的item居中
    private void moveToCenter(int position) {
        //将点击的position转换为当前屏幕上可见的item的位置以便于计算距离顶部的高度，从而进行移动居中
        View childAt = rvSort.getChildAt(position - mLinearLayoutManager.findFirstVisibleItemPosition());
        if (childAt != null) {
            int y = (childAt.getTop() - rvSort.getHeight() / 2);
            rvSort.smoothScrollBy(0, y);
        }

    }


    public void createFragment() {

    }

    @Override
    public void check(int position, boolean isScroll) {
        setChecked(position, isScroll);
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