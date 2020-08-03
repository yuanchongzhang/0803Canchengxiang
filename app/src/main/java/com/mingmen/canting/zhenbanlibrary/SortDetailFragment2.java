package com.mingmen.canting.zhenbanlibrary;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mingmen.canting.R;
import com.mingmen.canting.adapter.LinkLabelAdapter;
import com.mingmen.canting.model.LinkLabelBean;
import com.mingmen.canting.model.RightBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SortDetailFragment2 extends BaseFragment<SortDetailPresenter, String> implements CheckListener, ClassifyNewAdapter2.OnItemClickListener {
    private RecyclerView mRv;
    private ClassifyNewAdapter2 mAdapter;
    private GridLayoutManager mManager;
    private List<RightBean> mDatas = new ArrayList<>();
    //    private ItemHeaderDecoration mDecoration;
    private boolean move = false;
    private int mIndex = 0;
    private CheckListener checkListener;
    private ClassifyNewAdapter2 mAdapter2;
    TextView tv_total;
    CheckBox cb_check;
    RecyclerView recycle_biaoqian;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sort_detail;
    }

    private List<LinkLabelBean> list = new ArrayList<>();

    private Gson gson = new Gson();

    private String[] str = new String[]{"标题1", "帅哥", "校园", "军事", "悬疑", "科幻"};
    private LinkLabelBean linkLabelBean;

    private Map<String, List<String>> hashMap = new HashMap<>();

    @Override
    protected void initCustomView(View view) {
        mRv = (RecyclerView) view.findViewById(R.id.rv);
        tv_total = (TextView) view.findViewById(R.id.tv_total);
        cb_check = (CheckBox) view.findViewById(R.id.cb_check);
        recycle_biaoqian=view.findViewById(R.id.recycle_biaoqian);
        cb_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    if (cb_check.isChecked()) {
                        for (int i = 0; i < mDatas.size(); i++) {
                            if (mDatas.get(i).getCount() == 0) {
                                mDatas.get(i).setChecked(false);
                            } else {
                                mDatas.get(i).setChecked(true);
                            }

                        }
                        showTotalPrice();
                        mAdapter2.notifyDataSetChanged();
                    }

                } else {
                    for (int i = 0; i < mDatas.size(); i++) {
                        mDatas.get(i).setChecked(false);
                    }
                    showTotalPrice();
                    mAdapter2.notifyDataSetChanged();
                }
            }
        });
        mRv.setItemViewCacheSize(20);
        mRv.setDrawingCacheEnabled(true);
        mRv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        initData2();
        initView();
    }
    private void initData2() {
        for (int i = 0; i < str.length; i++) {
            linkLabelBean = new LinkLabelBean();
            linkLabelBean.setStr(str[i]);
            list.add(linkLabelBean);
        }
    }
    private void initView() {
        final GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 4);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int type = recycle_biaoqian.getAdapter().getItemViewType(position);//获得返回值
                if (type == 99) {
                    return mLayoutManager.getSpanCount();
                } else {
                    return 1;
                }
            }
        });
        recycle_biaoqian.setLayoutManager(mLayoutManager);
        LinkLabelAdapter linkLabelAdapter = new LinkLabelAdapter(getActivity(), list);
        recycle_biaoqian.setAdapter(linkLabelAdapter);
    }
    @Override
    protected void initListener() {
        mRv.addOnScrollListener(new RecyclerViewListener());
    }

    @Override
    protected SortDetailPresenter initPresenter() {
        showRightPage(1);
        mManager = new GridLayoutManager(mContext, 1);
        //通过isTitle的标志来判断是否是title
        mManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mDatas.get(position).isTitle() ? 1 : 1;
            }
        });
        mRv.setLayoutManager(mManager);


//        mRv.setAdapter(mAdapter);

        mAdapter2 = new ClassifyNewAdapter2(mContext, mDatas);
        mRv.setAdapter(mAdapter2);

        mAdapter2.setOnItemClickListener(this);

//        mDecoration = new ItemHeaderDecoration(mContext, mDatas);
//        mRv.addItemDecoration(mDecoration);
//        mDecoration.setCheckListener(checkListener);
        initData();
        return new SortDetailPresenter();
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

        for (RightBean cart : mDatas) {
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
        return (mDatas != null && mDatas.size() > 0);
    }

    private void initData() {
        ArrayList<SortBean.CategoryOneArrayBean> rightList = getArguments().getParcelableArrayList("right");


        for (int i = 0; i < rightList.size(); i++) {
       /*   RightBean head = new RightBean(rightList.get(i).getName());
            //头部设置为true
            head.setTitle(true);
            head.setTitleName(rightList.get(i).getName());
            head.setTag(String.valueOf(i));
            mDatas.add(head);*/
            List<SortBean.CategoryOneArrayBean.CategoryTwoArrayBean> categoryTwoArray = rightList.get(i).getCategoryTwoArray();
            for (int j = 0; j < categoryTwoArray.size(); j++) {
                RightBean body = new RightBean(categoryTwoArray.get(j).getName());
                body.setTag(String.valueOf(i));
                String name = rightList.get(i).getName();
                body.setTitleName(name);
                mDatas.add(body);
            }
        }


//        mAdapter.notifyDataSetChanged();
//        mDecoration.setData(mDatas);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    public void refreshView(int code, String data) {

    }

    public void setData(int n) {
        mIndex = n;
        mRv.stopScroll();
        smoothMoveToPosition(n);
    }

    @Override
    protected void getData() {

    }

    public void setListener(CheckListener listener) {
        this.checkListener = listener;
    }

    private void smoothMoveToPosition(int n) {
        int firstItem = mManager.findFirstVisibleItemPosition();
        int lastItem = mManager.findLastVisibleItemPosition();
        Log.d("first--->", String.valueOf(firstItem));
        Log.d("last--->", String.valueOf(lastItem));
        if (n <= firstItem) {
            mRv.scrollToPosition(n);
        } else if (n <= lastItem) {
            Log.d("pos---->", String.valueOf(n) + "VS" + firstItem);
            int top = mRv.getChildAt(n - firstItem).getTop();
            Log.d("top---->", String.valueOf(top));
            mRv.scrollBy(0, top);
        } else {
            mRv.scrollToPosition(n);
            move = true;
        }
    }


    @Override
    public void check(int position, boolean isScroll) {
        checkListener.check(position, isScroll);

    }

/*    @Override
    public void onItemClickListener(View view, int pos, List<RightBean> myLiveList) {
        switch (view.getId()) {
            case R.id.ivAvatar:
                Toast.makeText(mContext, pos + "", Toast.LENGTH_SHORT).show();
                break;
        }
    }*/


    @Override
    public void onItemClickListener(View view, int pos, List<RightBean> myLiveList) {
        int count = 0;
//        case R.id.content:
        RightBean rightBean = myLiveList.get(pos);
        switch (view.getId()) {
            case R.id.checkbox:

                showTotalPrice();
                break;
        }


    }


    private class RecyclerViewListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (move && newState == RecyclerView.SCROLL_STATE_IDLE) {
                move = false;
                int n = mIndex - mManager.findFirstVisibleItemPosition();
                Log.d("n---->", String.valueOf(n));
                if (0 <= n && n < mRv.getChildCount()) {
                    int top = mRv.getChildAt(n).getTop();
                    Log.d("top--->", String.valueOf(top));
                    mRv.smoothScrollBy(0, top);
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (move) {
                move = false;
                int n = mIndex - mManager.findFirstVisibleItemPosition();
                if (0 <= n && n < mRv.getChildCount()) {
                    int top = mRv.getChildAt(n).getTop();
                    mRv.scrollBy(0, top);
                }
            }
        }
    }


}
