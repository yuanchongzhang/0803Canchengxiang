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
import android.support.v7.widget.DividerItemDecoration;
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
public class FilterNewLoginListActivity extends Activity {
    @BindView(R.id.img_return)
    ImageView imgReturn;

    @BindView(R.id.layout_close)
    LinearLayout layoutClose;
    private List<LoginReturnBean.DataBean> mContactModels;
    private List<LoginReturnBean.DataBean> mShowModels;
    private RecyclerView mRecyclerView;

    private ClearEditText mSearchEditText;
    private LoginAdapter mAdapter;
    String string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newfitter_lis);
        ButterKnife.bind(this);
        string = "{" +
                "code\":200,\n" +
                "    \"message\":\"餐厅列表\",\n" +
                "    \"time\":\"2020-07-22 11:15:59\",\n" +
                "    \"data\":[\n" +
                "        {\"companyId\":1285772488172769280,\"companyName\":\"111\",\"departmentId\":0,\"userId\":0}\n" +
                "    ]\n" +
                "}";
        initData();
        bindView();

    }

    private void bindView() {

        mAdapter = new LoginAdapter(mShowModels);

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
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnitemClickLintener(new LoginAdapter.OnitemClick() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(FilterNewLoginListActivity.this, position + "", Toast.LENGTH_SHORT).show();
                Log.d("kdkdkdkf", mShowModels.get(position).getCompanyName() + "");

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
                for (LoginReturnBean.DataBean model : mContactModels) {
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
        LoginReturnBean loginReturnBean = new LoginReturnBean();
        loginReturnBean = gson.fromJson(string, LoginReturnBean.class);
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
            case R.id.layout_close:
                finish();
                break;
            case R.id.img_return:
                finish();
                break;

        }
    }

}