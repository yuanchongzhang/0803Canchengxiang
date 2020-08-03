package com.mingmen.canting.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.mingmen.canting.R;

import java.util.ArrayList;
import java.util.List;


public class ZheXianActivity extends Activity implements View.OnClickListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhexian);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_open_gallery:


                break;

        }
    }



}
