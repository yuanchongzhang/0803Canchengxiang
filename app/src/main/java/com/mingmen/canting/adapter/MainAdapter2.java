/*
 * Copyright 2016 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mingmen.canting.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;


import com.mingmen.canting.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by YOLANDA on 2016/7/22.
 */
public class MainAdapter2 extends BaseAdapter<MainAdapter2.ViewHolder> {

    private List<String> mDataList;
    Context context;
    private HashMap<Integer, Boolean> isSelected = new HashMap<>();

    public MainAdapter2(Context context) {
        super(context);
        this.context = context;
    }

    public HashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }

    public void setDataList(List<String> list) {
        mDataList = list;

        initData();

        notifyDataSetChanged();
    }

    private void initData() {
        for (int i = 0; i < mDataList.size(); i++) {
            getIsSelected().put(i, false);
        }
    }

    @Override
    public void notifyDataSetChanged(List<String> dataList) {
        this.mDataList = dataList;
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new ViewHolder(getInflater().inflate(R.layout.item_menu_main, parent, false));
        ViewHolder viewHolder = new ViewHolder(getInflater().inflate(R.layout.item_menu_main, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.setData(mDataList.get(position));
        holder.tvTitle.setText(mDataList.get(position));


/*      holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getIsSelected().put(position, isChecked);
            }
        });

        holder.checkbox.setChecked(getIsSelected().get(position));*/
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        CheckBox checkbox;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
//            checkbox = itemView.findViewById(R.id.checkbox);
        }


    }

    public interface OnCheckChangeListener {
        void onCheckedChanged(List<String> var1, int position, boolean ischecked);


    }
}