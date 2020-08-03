package com.mingmen.canting.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.mingmen.canting.R;
import com.mingmen.canting.model.LinkLabelBean;

import java.util.List;

/**
 * Created by wangtao on 2018/4/18.s    RecyclerView.Adapter implements View.OnClickListener  RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> implements View.OnClickListener
 */

public class LinkLabelAdapter extends RecyclerView.Adapter<LinkLabelAdapter.MyViewHolder> {

    private Context context;
    private final int TITLE = 99;
    private final int CONTENT = 100;
    private int Zeng = 0;
    private List<LinkLabelBean> list;
    private OnItemClickListener mOnItemClickListener;//声明自定义的接口

    //定义方法并传给外面的使用者
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    //item里面有多个控件可以点击（item+item内部控件）
    public enum ViewName {
        ITEM,
        PRACTISE
    }

    //自定义一个回调接口来实现Click和LongClick事件
    public interface OnItemClickListener {
        void onItemClick(View v, ViewName viewName, int position);

    }

    public LinkLabelAdapter(Context context, List<LinkLabelBean> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_activity_label_content, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        LinkLabelBean data = list.get(position);

        holder.textViewContent.setText(data.getStr());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*    data.setSelect(!data.isSelect());
                holder.textViewContent.setSelected(data.isSelect());
                mOnItemClickListener.onItemClick(v, ViewName.ITEM, position);*/
                if (list.get(position).isSelect()) {
                    Zeng--;

                }
                if (Zeng < 1) {
                    if (holder.textViewContent.isSelected()) {
                        holder.textViewContent.setSelected(false);
                        list.get(position).setSelect(false);
                    } else {
                        ++Zeng;
                        holder.textViewContent.setSelected(true);
                        list.get(position).setSelect(true);
                    }
                } else {

              /*      holder.textViewContent.setSelected(true);
                    list.get(position).setSelect(true);*/

                }
                mOnItemClickListener.onItemClick(v, ViewName.ITEM, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    //创建MyViewHolder继承RecyclerView.ViewHolder
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewContent;

        public MyViewHolder(View itemView) {
            super(itemView);

            textViewContent = (TextView) itemView.findViewById(R.id.textViewContent);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = 50;


        }

    }


}
