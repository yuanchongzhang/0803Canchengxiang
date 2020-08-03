package com.mingmen.canting.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.mingmen.canting.R;
import com.mingmen.canting.model.SimpleData;

import java.util.ArrayList;
import java.util.List;


/**
 *
 */
public class ChuShiDingDanAdapter extends RecyclerView.Adapter<ChuShiDingDanAdapter.ViewHolder> {
    //新增itemType
    public static final int ITEM_TYPE = 100;

    private Context mContext;
    private List<SimpleData> mList;

    //定义一个点击事件的接口
    public interface OnitemClick {
        void onItemClick(int position, List<SimpleData.ChildData> mList);
    }

    private OnitemClick onitemClick;   //定义点击事件接口
    public void setOnitemClickLintener(OnitemClick onitemClick) {
        this.onitemClick = onitemClick;
    }
    public ChuShiDingDanAdapter(Context context, List<SimpleData> list) {
        mContext = context;
        mList = list;
    }

    //重写改方法，设置ItemViewType
    @Override
    public int getItemViewType(int position) {
        //返回值与使用时设置的值需保持一致
        return ITEM_TYPE;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detaillist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_title.setText(mList.get(position).typeName);

        /*
         1.把内部RecyclerView的adapter和集合数据通过holder缓存
         2.使内部RecyclerView的adapter提供设置position的方法
         */
        holder.list.clear();
        holder.list.addAll(mList.get(position).list);
        if (holder.mRvAdapter == null) {
            holder.mRvAdapter = new SecondAdapter(mContext, holder.list, position);
            LinearLayoutManager layoutManage = new LinearLayoutManager(mContext);
            holder.rvItemItem.setLayoutManager(layoutManage);
//            holder.rvItemItem.addItemDecoration(new GridSpacingItemDecoration(2, 20, false));
            holder.rvItemItem.setAdapter(holder.mRvAdapter);
        } else {
            holder.mRvAdapter.setPosition(position);
            holder.mRvAdapter.notifyDataSetChanged();
        }

        holder.mRvAdapter.setOnitemClickLintener(new SecondAdapter.OnitemClick() {
            @Override
            public void onItemClick(int position, List<SimpleData.ChildData> mList) {
                if (onitemClick != null) {
                    onitemClick.onItemClick(position,mList);
                }
            }


        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        RecyclerView rvItemItem;

        private SecondAdapter mRvAdapter;
        private List<SimpleData.ChildData> list = new ArrayList<>();

        ViewHolder(View view) {
            super(view);

            tv_title = view.findViewById(R.id.tv_title);
            rvItemItem = view.findViewById(R.id.rv_item);
        }
    }
}