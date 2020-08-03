package com.mingmen.canting.zhenbanlibrary;

import android.content.Context;
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

import java.util.List;


public class ClassifyNewAdapter2 extends RecyclerView.Adapter<ClassifyNewAdapter2.ViewHolder> {
    List<RightBean> myLiveList;
    Context mContext;
    private OnItemClickListener mOnItemClickListener;


    public interface CheckItemListener {
        void itemChecked(RightBean checkBean, boolean isChecked);
    }

    private CheckItemListener mCheckListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(View view, int pos, List<RightBean> myLiveList);
    }

    public ClassifyNewAdapter2(Context context, List<RightBean> myLiveList, CheckItemListener mCheckListener) {
        this.mContext = context;
        this.myLiveList = myLiveList;
        this.mCheckListener = mCheckListener;
    }

    public ClassifyNewAdapter2(Context context, List<RightBean> myLiveList) {
        this.mContext = context;
        this.myLiveList = myLiveList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_classify_detail4, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    int count = 0;
    int mycount = 0;

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final RightBean myLive = myLiveList.get(holder.getAdapterPosition());



    /*    holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, holder.getAdapterPosition() + "", Toast.LENGTH_SHORT).show();
            }
        });
        holder.ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClickListener(view, position, myLiveList);
            }
        });*/

        holder.checkbox.setChecked(myLive.isChecked());

        holder.mTvTitle.setText(myLive.getName());
        holder.tv_num.setText(myLive.getCount() + "");
//        holder.ivAvatar
//        Glide.with(mContext).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1596014050715&di=6a1ac18ffe1c13bd0957c1073c477cd7&imgtype=0&src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fphotoblog%2F8%2F0%2F6%2F5%2F8065746%2F20091%2F6%2F1231206650604.jpg").into(holder.ivAvatar);
        mycount = myLive.getCount();

        if (myLive.getCount() == 0) {
            holder.img_add.setVisibility(View.VISIBLE);
            holder.layout_produce.setVisibility(View.GONE);
        } else {
            holder.layout_produce.setVisibility(View.VISIBLE);
            holder.img_add.setVisibility(View.GONE);
        }

      /*  if (myLive.getCount() == 0) {
            holder.tv_reduce.setVisibility(View.VISIBLE);
            holder.tv_num.setVisibility(View.INVISIBLE);
            holder.tv_price.setVisibility(View.INVISIBLE);
        } else {
            holder.tv_reduce.setVisibility(View.VISIBLE);
            holder.tv_num.setVisibility(View.VISIBLE);
            holder.tv_price.setVisibility(View.INVISIBLE);
        }*/

        holder.tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mOnItemClickListener.onItemClickListener(view, position, myLiveList);
//                count = count + 1;
//                myLive.setCount(count);
//                holder.tv_num.setText(myLive.getCount() + "");

                int mycount = myLive.getCount() + 1;
                myLive.setCount(mycount);
                holder.tv_num.setText(myLive.getCount() + "");
                notifyItemChanged(position);

            }
        });
        holder.img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mycount = myLive.getCount() + 1;
                myLive.setCount(mycount);
                holder.tv_num.setText(myLive.getCount() + "");
                notifyItemChanged(position);
            }
        });

        holder.tv_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mycount = myLive.getCount() - 1;
                myLive.setCount(mycount);
                holder.tv_num.setText(myLive.getCount() + "");

            }
        });

        //点击实现选择功能，当然可以把点击事件放在item_cb对应的CheckBox上，只是焦点范围较小
     /*   holder.content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myLive.setChecked(!myLive.isChecked());
                holder.checkbox.setChecked(myLive.isChecked());

                mOnItemClickListener.onItemClickListener(view, position, myLiveList);

            }
        });*/

        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myLive.setChecked(!myLive.isChecked());
                holder.checkbox.setChecked(myLive.isChecked());

                mOnItemClickListener.onItemClickListener(view, position, myLiveList);
            }
        });


    }

    @Override
    public int getItemCount() {
        return myLiveList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        TextView mTvTitle;

        ImageView ivAvatar;
        TextView tv_num;
        TextView tv_reduce;
        CheckBox checkbox;
        LinearLayout content;
        TextView tv_price;

        TextView tv_add;

        ImageView img_add;
        LinearLayout layout_produce;

        public ViewHolder(View itemView) {
            super(itemView);

            mTvTitle = (TextView) itemView.findViewById(R.id.tvCity);
            ivAvatar = (ImageView) itemView.findViewById(R.id.ivAvatar);
            tv_num = (TextView) itemView.findViewById(R.id.tv_num);
            tv_reduce = (TextView) itemView.findViewById(R.id.tv_reduce);
            checkbox = (CheckBox) itemView.findViewById(R.id.checkbox);
            content = (LinearLayout) itemView.findViewById(R.id.content);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);

            tv_add = (TextView) itemView.findViewById(R.id.tv_add);
            img_add = itemView.findViewById(R.id.img_add);
            layout_produce = itemView.findViewById(R.id.layout_produce);
        }
    }


}
