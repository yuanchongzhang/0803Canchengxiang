package com.mingmen.canting.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mingmen.canting.R;
import com.mingmen.canting.model.LeftBean;
import com.mingmen.canting.model.MyLiveList;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by guohao on 2017/9/6.
 */

public class MyDingDanLeftAdapter extends RecyclerView.Adapter<MyDingDanLeftAdapter.ViewHolder> implements View.OnClickListener {

    private static final int MYLIVE_MODE_CHECK = 0;
    int mEditMode = MYLIVE_MODE_CHECK;

    private int secret = 0;
    private String title = "";
    private Context context;

    private OnItemClickListener mOnItemClickListener;
    List<LeftBean> mMyLiveList;

    public MyDingDanLeftAdapter(Context context, List<LeftBean> mMyLiveList) {
        this.context = context;
        this.mMyLiveList = mMyLiveList;
    }


    public void notifyAdapter(List<LeftBean> myLiveList, boolean isAdd) {
        if (!isAdd) {
            this.mMyLiveList = myLiveList;
        } else {
            this.mMyLiveList.addAll(myLiveList);
        }
        notifyDataSetChanged();
    }

    public List<LeftBean> getMyLiveList() {
        if (mMyLiveList == null) {
            mMyLiveList = new ArrayList<>();
        }
        return mMyLiveList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_left, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return mMyLiveList.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final LeftBean myLive = mMyLiveList.get(holder.getAdapterPosition());
        holder.mTvTitle.setText(myLive.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                View view, int pos, MyDingDanLeftAdapter.ViewName viewName, List<MyLiveList> myLiveLists
                mOnItemClickListener.onItemClickListener(v,holder.getAdapterPosition(),ViewName.ITEM,mMyLiveList);
            }
        });

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();

    }

    public interface OnItemClickListener {
        //        void onItemClickListener(int pos, DaishenheListAdapter.ViewName viewName, List<MyLiveList> myLiveList);
        void onItemClickListener(View view, int pos, ViewName viewName, List<LeftBean> myLiveLists);
    }

    public void setEditMode(int editMode) {
        mEditMode = editMode;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        TextView mTvTitle;


        public ViewHolder(View itemView) {
            super(itemView);

            mTvTitle = itemView.findViewById(R.id.text_menu);

        }
    }

    public enum ViewName {
        ITEM,
        PRACTISE,
        CHECKBOX
    }
    private int prePosition = 0;

}
