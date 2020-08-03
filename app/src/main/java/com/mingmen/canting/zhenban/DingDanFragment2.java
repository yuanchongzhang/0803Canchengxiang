package com.mingmen.canting.zhenban;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.mingmen.canting.adapter.BohuiListAdapter;
import com.mingmen.canting.http.callback.StringCallback;
import com.mingmen.canting.httpconfig.HttpResponse;
import com.mingmen.canting.model.MyLiveList;
import com.mingmen.canting.model.ShopBean;
import com.mingmen.canting.util.GsonUtil;
import com.mingmen.canting.util.SharePreferenceUtil;
import com.mingmen.canting.zhenbanlibrary.CheckListener;
import com.mingmen.canting.zhenbanlibrary.DingDanAdapter;
import com.mingmen.canting.zhenbanlibrary.ItemHeaderDecoration;
import com.mingmen.canting.zhenbanlibrary.RvListener;
import com.mingmen.canting.zhenbanlibrary.SortAdapter;
import com.mingmen.canting.zhenbanlibrary.SortBean;
import com.mingmen.canting.zhenbanlibrary.SortDetailFragment;
import com.mingmen.canting.zhenbanlibrary.SortDetailFragment2;
import com.mingmen.canting.zhenbanlibrary.TotalBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

@SuppressLint("ValidFragment")
public class DingDanFragment2 extends Fragment implements CheckListener {

    private RecyclerView rvSort;
    private DingDanAdapter mSortAdapter;
    private SortDetailFragment mSortDetailFragment;

    private Context mContext;
    private LinearLayoutManager mLinearLayoutManager;
    private int targetPosition;//点击左边某一个具体的item的位置
    private boolean isMoved;
    private SortBean mSortBean;
    //Fragment的View加载完毕的标记
    private boolean isLoading = false;

    //Fragment对用户可见的标记
    private boolean isUIVisible;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    String getData;
    String token;
    List<ShopBean.DataBean> data = new ArrayList<>();
    List<String> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_dingdan, null);
        TextView card_title_tv = (TextView) v.findViewById(R.id.card_title_tv);
        card_title_tv.setText(getData);
        mContext = getActivity();
        rvSort = (RecyclerView) v.findViewById(R.id.rv_sort);
        initData();

     /*   if (!list.isEmpty()) {
            list.clear();
        }
        ShopBean shopBean = GsonUtil.GsonToBean(getData, ShopBean.class);
        data = shopBean.getData();
        for (int i = 0; i < data.size(); i++) {
            list.add(data.get(i).getCategoryName());
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
        });*/

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

    private void initData() {
        //获取asset目录下的资源文件
        String assetsData = getAssetsData("sort.json");

        Gson gson = new Gson();
        Log.d("dkdkdk", assetsData);

        mSortBean = gson.fromJson(assetsData, SortBean.class);
        List<SortBean.CategoryOneArrayBean> categoryOneArray = mSortBean.getCategoryOneArray();

        TotalBean totalBean = new TotalBean();
        totalBean = gson.fromJson(assetsData, TotalBean.class);

        List<TotalBean.CategoryOneArrayBean> twoArrayBeans = new ArrayList<>();
//        twoArrayBeans = totalBean.getCategoryOneArray().get(0).getCategoryTwoArray();
        twoArrayBeans = totalBean.getCategoryOneArray();
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("name", twoArrayBeans.get(0).getName());
            jsonObject.put("imgsrc", twoArrayBeans.get(0).getName());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        List<String> list = new ArrayList<>();
        //初始化左侧列表数据
        for (int i = 0; i < categoryOneArray.size(); i++) {
            list.add(categoryOneArray.get(i).getName());
            Log.d("dkdkdkdk", list.get(i));
        }

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    //从资源文件中获取分类json
    private String getAssetsData(String path) {
        String result = "";
        try {
            //获取输入流
            InputStream mAssets = getActivity().getAssets().open(path);
            //获取文件的字节数
            int lenght = mAssets.available();
            //创建byte数组
            byte[] buffer = new byte[lenght];
            //将文件中的数据写入到字节数组中
            mAssets.read(buffer);
            mAssets.close();
            result = new String(buffer);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("fuck", e.getMessage());
            return result;
        }
    }

    public static DingDanFragment2 getInstance(String title) {
        DingDanFragment2 sf = new DingDanFragment2();
        sf.getData = title;
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
                count += mSortBean.getCategoryOneArray().get(i).getCategoryTwoArray().size();
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