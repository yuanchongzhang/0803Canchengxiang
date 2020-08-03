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
package com.mingmen.canting.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mingmen.canting.R;
import com.mingmen.canting.adapter.LoginAdapter;
import com.mingmen.canting.defineview.ClearEditText;
import com.mingmen.canting.model.LoginReturnBean;
import com.mingmen.canting.util.PinnedHeaderDecoration;
import com.mingmen.canting.util.Trans2PinYinUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * <p>
 * ListView形式的侧滑菜单，支持横向和竖向的。
 * </p>
 * Created by Yan Zhenjie on 2016/7/27.
 */
public class FilterLoginListBeiFenActivity extends Activity {
    private RecyclerView mRecyclerView;
    private LoginAdapter mAdapter;
    private List<LoginReturnBean.DataBean> mContactModels;
    private List<LoginReturnBean.DataBean> mShowModels;
    String string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginbeifen);
        string = "{" +
                "code\":200,\n" +
                "    \"message\":\"餐厅列表\",\n" +
                "    \"time\":\"2020-07-22 11:15:59\",\n" +
                "    \"data\":[\n" +
                "        {\"companyId\":1285772488172769280,\"companyName\":\"111\",\"departmentId\":0,\"userId\":0}\n" +
                "    ]\n" +
                "}";
        mRecyclerView = (RecyclerView) findViewById(R.id.main_recycler);
        initData();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new LoginAdapter(mShowModels);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        mContactModels = new ArrayList<>();
        mShowModels = new ArrayList<>();
        Gson gson = new Gson();
        LoginReturnBean loginReturnBean = new LoginReturnBean();
        loginReturnBean = gson.fromJson(string, LoginReturnBean.class);
        mContactModels = loginReturnBean.getData();
//        mContactModels.addAll(ContactModel.getContacts());
        mContactModels.addAll(mContactModels);
        mShowModels.addAll(mContactModels);
    }

}