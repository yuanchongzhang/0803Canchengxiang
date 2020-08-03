package com.mingmen.canting.viewholder;

import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.mingmen.canting.R;
import com.mingmen.canting.model.LeftBean;


/**
 * Created by 梁遂 on 2018/7/2.
 */

public class LeftHolder extends BaseViewHolder<LeftBean> {
    private TextView textView;
    public LeftHolder(ViewGroup itemView) {
        this(itemView, R.layout.item_left);
        textView = $(R.id.text_menu);
    }

    public LeftHolder(ViewGroup parent, int res) {
        super(parent, res);
    }

    @Override
    public void setData(LeftBean data) {
        super.setData(data);
        textView.setText(data.getName());
        if (data.isSelected()){
            textView.setBackgroundColor(Color.WHITE);
        }
        else {
            textView.setBackgroundColor(Color.parseColor("#747373"));
        }
    }
}
