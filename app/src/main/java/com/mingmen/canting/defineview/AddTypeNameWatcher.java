package com.mingmen.canting.defineview;

import android.text.Editable;
import android.text.TextWatcher;

import com.mingmen.canting.model.Dish;
import com.mingmen.canting.model.Order;


//记录解决edittext数据刷新的问题
public class AddTypeNameWatcher implements TextWatcher {


    private Dish mData;

    public AddTypeNameWatcher(Dish data) {
        mData = data;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String s = charSequence.toString();
        mData.setDishName(s);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}