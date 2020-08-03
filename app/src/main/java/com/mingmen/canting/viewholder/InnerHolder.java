package com.mingmen.canting.viewholder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.mingmen.canting.R;
import com.mingmen.canting.model.InnerBean;


/**
 * Created by 梁遂 on 2018/7/2.
 */

public class InnerHolder extends BaseViewHolder<InnerBean> {

    private TextView textView;
    public InnerHolder(ViewGroup itemView) {
        this(itemView, R.layout.item_inner);
    }

    public InnerHolder(ViewGroup parent, int res) {
        super(parent, res);
        textView = $(R.id.text_title);
    }

    @Override
    public void setData(InnerBean data) {
        super.setData(data);
        textView.setText(data.getTitle());
    }
}
