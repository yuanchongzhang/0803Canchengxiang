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
import com.mingmen.canting.model.MyLiveList;
import com.mingmen.canting.model.ZhenBanShenHe;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by guohao on 2017/9/6.
 */

public class YiShenHeListAdapter extends RecyclerView.Adapter<YiShenHeListAdapter.ViewHolder> implements View.OnClickListener {

    private static final int MYLIVE_MODE_CHECK = 0;
    int mEditMode = MYLIVE_MODE_CHECK;

    private int secret = 0;
    private String title = "";
    private Context context;
    private List<ZhenBanShenHe.DataBean> mMyLiveList;
    private OnItemClickListener mOnItemClickListener;

    public YiShenHeListAdapter(Context context,List<ZhenBanShenHe.DataBean> mMyLiveList) {
        this.context = context;
        this.mMyLiveList=mMyLiveList;
    }


    public void setDataList(List<ZhenBanShenHe.DataBean> myLiveList) {
        this.mMyLiveList = myLiveList;
        notifyDataSetChanged();
    }
    public void notifyAdapter(List<ZhenBanShenHe.DataBean> myLiveList, boolean isAdd) {
        if (!isAdd) {
            this.mMyLiveList = myLiveList;
        } else {
            this.mMyLiveList.addAll(myLiveList);
        }
        notifyDataSetChanged();
    }

    public List<ZhenBanShenHe.DataBean> getMyLiveList() {
        if (mMyLiveList == null) {
            mMyLiveList = new ArrayList<>();
        }
        return mMyLiveList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_yishenhe, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        return mMyLiveList.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ZhenBanShenHe.DataBean myLive = mMyLiveList.get(holder.getAdapterPosition());
        holder.mTvTitle.setText(myLive.getRejectCommodityName());
        holder.mTvSource.setText(myLive.getApplyTime());


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
        void onItemClickListener(View view, int pos, YiShenHeListAdapter.ViewName viewName, List<ZhenBanShenHe.DataBean> myLiveLists);
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

        public ViewHolder(View itemView) {
            super(itemView);

            mTvTitle = itemView.findViewById(R.id.tv_title);

            mTvSource = itemView.findViewById(R.id.tv_source);

            mRootView = itemView.findViewById(R.id.root_view);

            btn_zhuijia = itemView.findViewById(R.id.btn_zhuijia);
            btn_submit = itemView.findViewById(R.id.btn_submit);
        }
    }

    public enum ViewName {
        ITEM,
        PRACTISE,
        CHECKBOX
    }
}
