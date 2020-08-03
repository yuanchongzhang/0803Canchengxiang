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
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.mingmen.canting.R;
import com.mingmen.canting.adapter.LoginFilterAdapter;
import com.mingmen.canting.adapter.LoginRestruantAdapter;
import com.mingmen.canting.defineview.ClearEditText;
import com.mingmen.canting.model.LoginBean;
import com.mingmen.canting.model.LoginFilterBean;
import com.mingmen.canting.util.PinnedHeaderDecoration;
import com.mingmen.canting.util.SharePreferenceUtil;
import com.mingmen.canting.util.Trans2PinYinUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;


/**
 * <p>
 * ListView形式的侧滑菜单，支持横向和竖向的。
 * </p>
 * Created by Yan Zhenjie on 2016/7/27.
 */
public class FilterLoginRestruantActivity extends Activity {
    @BindView(R.id.img_return)
    ImageView imgReturn;

    @BindView(R.id.layout_close)
    LinearLayout layoutClose;
    private List<LoginBean.DataBean> mContactModels;
    private List<LoginBean.DataBean> mShowModels;
    private RecyclerView mRecyclerView;

    private ClearEditText mSearchEditText;
    private LoginRestruantAdapter mAdapter;
    String string;
    String attrs = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newfitter_lis);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        string = "{" +
                "code\":200,\n" +
                "    \"message\":\"餐厅列表\",\n" +
                "    \"time\":\"2020-07-22 11:15:59\",\n" +
                "    \"data\":[\n" +
                "        {\"companyId\":1285772488172769280,\"companyName\":\"111\",\"departmentId\":0,\"userId\":0}\n" +
                "    ]\n" +
                "}";

/*        Gson gson = new Gson();
        ShopTestBean shopTestBean = new ShopTestBean();
        shopTestBean = gson.fromJson(attrs, ShopTestBean.class);*/

//        Log.d("dkdkdk", shopTestBean.getMessage());

        attrs = getIntent().getStringExtra("logindata");

        initData();
        bindView();

    }

    private void bindView() {

        mAdapter = new LoginRestruantAdapter(mShowModels);

        // RecyclerView设置相关
        mRecyclerView = (RecyclerView) findViewById(R.id.main_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final PinnedHeaderDecoration decoration = new PinnedHeaderDecoration();
        decoration.registerTypePinnedHeader(1, new PinnedHeaderDecoration.PinnedHeaderCreator() {
            @Override
            public boolean create(RecyclerView parent, int adapterPosition) {
                return true;
            }
        });
//        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnitemClickLintener(new LoginRestruantAdapter.OnitemClick() {
            @Override
            public void onItemClick(int position) {

                Log.d("kdkdkdkf", mShowModels.get(position).getCompanyName() + "");
//                setResult(1, new Intent().putExtra("value", "data1"));
                Intent intent = new Intent(FilterLoginRestruantActivity.this, LoginActivity.class);
                intent.putExtra("companyName", mShowModels.get(position).getCompanyName());
                intent.putExtra("companyId", mShowModels.get(position).getCompanyId()+"");
                intent.putExtra("token", mShowModels.get(position).getToken());
                SharePreferenceUtil.put(FilterLoginRestruantActivity.this,"token",mShowModels.get(position).getToken());
                SharePreferenceUtil.put(FilterLoginRestruantActivity.this,"companyId",mShowModels.get(position).getCompanyId());
                SharePreferenceUtil.put(FilterLoginRestruantActivity.this,"companyName",mShowModels.get(position).getCompanyName());
                setResult(100, intent);
                finish();
            }
        });

        // 搜索按钮相关
        mSearchEditText = (ClearEditText) findViewById(R.id.main_search);
        mSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mShowModels.clear();
                for (LoginBean.DataBean model : mContactModels) {
                   /* String str = Trans2PinYinUtil.trans2PinYin(model.getName());
                    if (str.contains(s.toString()) || model.getName().contains(s.toString())) {
                        mShowModels.add(model);
                    }*/
                    String str = Trans2PinYinUtil.trans2PinYin(model.getCompanyName());
                    if (str.contains(s.toString()) || model.getCompanyName().contains(s.toString())) {
                        mShowModels.add(model);
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }


    private void initData() {
        mContactModels = new ArrayList<>();
        mShowModels = new ArrayList<>();
        Gson gson = new Gson();
        LoginBean loginReturnBean = new LoginBean();
        loginReturnBean = gson.fromJson(attrs, LoginBean.class);
        mContactModels = loginReturnBean.getData();
//        mContactModels.addAll(ContactModel.getContacts());
//        mContactModels.addAll(mContactModels);
        mShowModels.addAll(mContactModels);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mContactModels != null) {
            mContactModels.clear();
            mContactModels = null;
        }
    }

    @OnClick({R.id.img_return, R.id.layout_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.img_return:
            case R.id.layout_close:

                Intent intent = new Intent(FilterLoginRestruantActivity.this, RegisterActivity.class);
                intent.putExtra("companyName", "");
                intent.putExtra("companyId", "");
                setResult(100, intent);
                finish();

                break;

        }
    }

    public void onEventMainThread(Integer type) {

    }
}