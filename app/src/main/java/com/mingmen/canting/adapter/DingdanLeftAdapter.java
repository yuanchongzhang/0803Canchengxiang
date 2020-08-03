package com.mingmen.canting.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.mingmen.canting.model.LeftBean;
import com.mingmen.canting.viewholder.LeftHolder;

import java.util.List;

/**
 * Created by 梁遂 on 2018/7/2.
 */

public class DingdanLeftAdapter extends RecyclerArrayAdapter<LeftBean> {

    private int prePosition = 0;

    public DingdanLeftAdapter(Context context) {
        super(context);
    }

    public DingdanLeftAdapter(Context context, LeftBean[] objects) {
        super(context, objects);
    }

    public DingdanLeftAdapter(Context context, List<LeftBean> objects) {
        super(context, objects);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new LeftHolder(parent);
    }

    public void setSelectedPosition(int position){
        if (prePosition != position){
            mRecyclerView.smoothScrollToPosition(position);
            mObjects.get(prePosition).setSelected(false);
            notifyItemChanged(prePosition);
            prePosition = position;
            mObjects.get(prePosition).setSelected(true);
            notifyItemChanged(position);
        }
    }

}
