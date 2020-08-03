package com.mingmen.canting.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mingmen.canting.R;
import com.mingmen.canting.model.RightBean;
import com.mingmen.canting.zhenbanlibrary.MyGoodsInfo;

import java.util.List;


public class MyLabelAdapter extends RecyclerView.Adapter<MyLabelAdapter.ViewHolder> {
    List<String> myLiveList;
    Context mContext;
    private OnItemClickListener mOnItemClickListener;
    private int checkedPosition = -1;

    //recycleview标签
    public void setCheckedPosition(int checkedPosition) {
        this.checkedPosition = checkedPosition;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(View view, int pos, List<String> myLiveList);
    }


    public MyLabelAdapter(Context context) {
        this.mContext = context;


    }

    public void setDataList(List<String> list) {
        myLiveList = list;

        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mylabel, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv_label.setText(myLiveList.get(position));

        if (position == checkedPosition) {
//            holder.content.setBackgroundColor(Color.parseColor("#f3f3f3"));
            holder.content.setBackground(mContext.getResources().getDrawable(R.drawable.shape_label_select));
            holder.tv_label.setTextColor(Color.parseColor("#BF0B22"));
        } else {
            holder.content.setBackground(mContext.getResources().getDrawable(R.drawable.shape_label_unselect));
            holder.tv_label.setTextColor(Color.parseColor("#303030"));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClickListener(v, position, myLiveList);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myLiveList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_label;
        LinearLayout content;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_label = (TextView) itemView.findViewById(R.id.tv_label);
            content = itemView.findViewById(R.id.content);
        }
    }


}
