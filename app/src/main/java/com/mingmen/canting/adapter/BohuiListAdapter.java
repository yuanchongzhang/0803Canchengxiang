package com.mingmen.canting.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mingmen.canting.R;
import com.mingmen.canting.model.MyLiveList;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by guohao on 2017/9/6.
 */

public class BohuiListAdapter extends RecyclerView.Adapter<BohuiListAdapter.ViewHolder> implements View.OnClickListener {

    private static final int MYLIVE_MODE_CHECK = 0;
    int mEditMode = MYLIVE_MODE_CHECK;

    private int secret = 0;
    private String title = "";
    private Context context;
    private List<MyLiveList> mMyLiveList;
    private OnItemClickListener mOnItemClickListener;

    public BohuiListAdapter(Context context) {
        this.context = context;
    }


    public void notifyAdapter(List<MyLiveList> myLiveList, boolean isAdd) {
        if (!isAdd) {
            this.mMyLiveList = myLiveList;
        } else {
            this.mMyLiveList.addAll(myLiveList);
        }
        notifyDataSetChanged();
    }

    public List<MyLiveList> getMyLiveList() {
        if (mMyLiveList == null) {
            mMyLiveList = new ArrayList<>();
        }
        return mMyLiveList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bohui, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return mMyLiveList.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final MyLiveList myLive = mMyLiveList.get(holder.getAdapterPosition());
        holder.mTvTitle.setText(myLive.getTitle());
        holder.mTvSource.setText(myLive.getSource());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mOnItemClickListener.onItemClickListener(holder.getAdapterPosition(), mMyLiveList);
                mOnItemClickListener.onItemClickListener(v, holder.getAdapterPosition(), ViewName.ITEM, mMyLiveList);
            }
        });
        holder.btn_zhuijia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClickListener(v, holder.getAdapterPosition(), ViewName.PRACTISE, mMyLiveList);
            }
        });
        holder.btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClickListener(v, holder.getAdapterPosition(), ViewName.PRACTISE, mMyLiveList);
            }
        });
        holder.layout_bohui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClickListener(v, holder.getAdapterPosition(), ViewName.PRACTISE, mMyLiveList);
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
        void onItemClickListener(View view, int pos, ViewName viewName, List<MyLiveList> myLiveLists);
    }

    public void setEditMode(int editMode) {
        mEditMode = editMode;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        TextView mTvTitle;

        TextView mTvSource;

        LinearLayout mRootView;


        Button btn_zhuijia;
        Button btn_submit;
        LinearLayout layout_bohui;

        public ViewHolder(View itemView) {
            super(itemView);

            mTvTitle = itemView.findViewById(R.id.tv_title);

            mTvSource = itemView.findViewById(R.id.tv_source);

            mRootView = itemView.findViewById(R.id.root_view);

            btn_zhuijia = itemView.findViewById(R.id.btn_zhuijia);
            btn_submit = itemView.findViewById(R.id.btn_submit);
            layout_bohui = itemView.findViewById(R.id.layout_bohui);
        }
    }

    public enum ViewName {
        ITEM,
        PRACTISE,
        CHECKBOX
    }
}
