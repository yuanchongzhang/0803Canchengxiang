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
import com.mingmen.canting.adapter.LoginRestruantAdapter;
import com.mingmen.canting.adapter.YiShenHeAdapter;
import com.mingmen.canting.defineview.ClearEditText;
import com.mingmen.canting.model.LoginBean;
import com.mingmen.canting.util.PinnedHeaderDecoration;
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
public class YiShenHeActivity extends Activity {
    @BindView(R.id.img_return)
    ImageView imgReturn;

    @BindView(R.id.layout_close)
    LinearLayout layoutClose;
    private List<LoginBean.DataBean> mContactModels;
    private List<LoginBean.DataBean> mShowModels;
    private RecyclerView mRecyclerView;


    private YiShenHeAdapter mAdapter;
    String string;
    String attrs = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yishenhe);
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
        attrs = string;
/*        Gson gson = new Gson();
        ShopTestBean shopTestBean = new ShopTestBean();
        shopTestBean = gson.fromJson(attrs, ShopTestBean.class);*/

//        Log.d("dkdkdk", shopTestBean.getMessage());

        attrs = "{\n" +
                "    \"code\":0,\n" +
                "    \"message\":\"操作成功\",\n" +
                "    \"time\":\"2020-07-25 13:50:58\",\n" +
                "    \"data\":[\n" +
                "        {\n" +
                "            \"companyId\":681,\n" +
                "            \"companyName\":\"迎宾烧烤\",\n" +
                "            \"userId\":1286889976134504448,\n" +
                "            \"userName\":\"张宇\",\n" +
                "            \"userRole\":\"1000\",\n" +
                "            \"departmentId\":0,\n" +
                "            \"departmentRole\":\"\",\n" +
                "            \"randomId\":\"1\",\n" +
                "            \"token\":\"1bff315b09ad4ed795f26d337cb97050\"\n" +
                "        },  {\n" +
                "            \"companyId\":681,\n" +
                "            \"companyName\":\"迎宾烧烤\",\n" +
                "            \"userId\":1286889976134504448,\n" +
                "            \"userName\":\"张宇\",\n" +
                "            \"userRole\":\"1000\",\n" +
                "            \"departmentId\":0,\n" +
                "            \"departmentRole\":\"\",\n" +
                "            \"randomId\":\"1\",\n" +
                "            \"token\":\"1bff315b09ad4ed795f26d337cb97050\"\n" +
                "        },  {\n" +
                "            \"companyId\":681,\n" +
                "            \"companyName\":\"迎宾烧烤\",\n" +
                "            \"userId\":1286889976134504448,\n" +
                "            \"userName\":\"张宇\",\n" +
                "            \"userRole\":\"1000\",\n" +
                "            \"departmentId\":0,\n" +
                "            \"departmentRole\":\"\",\n" +
                "            \"randomId\":\"1\",\n" +
                "            \"token\":\"1bff315b09ad4ed795f26d337cb97050\"\n" +
                "        },  {\n" +
                "            \"companyId\":681,\n" +
                "            \"companyName\":\"迎宾烧烤\",\n" +
                "            \"userId\":1286889976134504448,\n" +
                "            \"userName\":\"张宇\",\n" +
                "            \"userRole\":\"1000\",\n" +
                "            \"departmentId\":0,\n" +
                "            \"departmentRole\":\"\",\n" +
                "            \"randomId\":\"1\",\n" +
                "            \"token\":\"1bff315b09ad4ed795f26d337cb97050\"\n" +
                "        },  {\n" +
                "            \"companyId\":681,\n" +
                "            \"companyName\":\"迎宾烧烤\",\n" +
                "            \"userId\":1286889976134504448,\n" +
                "            \"userName\":\"张宇\",\n" +
                "            \"userRole\":\"1000\",\n" +
                "            \"departmentId\":0,\n" +
                "            \"departmentRole\":\"\",\n" +
                "            \"randomId\":\"1\",\n" +
                "            \"token\":\"1bff315b09ad4ed795f26d337cb97050\"\n" +
                "        },  {\n" +
                "            \"companyId\":681,\n" +
                "            \"companyName\":\"迎宾烧烤\",\n" +
                "            \"userId\":1286889976134504448,\n" +
                "            \"userName\":\"张宇\",\n" +
                "            \"userRole\":\"1000\",\n" +
                "            \"departmentId\":0,\n" +
                "            \"departmentRole\":\"\",\n" +
                "            \"randomId\":\"1\",\n" +
                "            \"token\":\"1bff315b09ad4ed795f26d337cb97050\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        initData();
        bindView();

    }

    private void bindView() {

        mAdapter = new YiShenHeAdapter(mShowModels);

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

        mAdapter.setOnitemClickLintener(new YiShenHeAdapter.OnitemClick() {
            @Override
            public void onItemClick(int position) {

                Log.d("kdkdkdkf", mShowModels.get(position).getCompanyName() + "");
//                setResult(1, new Intent().putExtra("value", "data1"));
                Intent intent = new Intent(YiShenHeActivity.this, LoginActivity.class);
                intent.putExtra("companyName", mShowModels.get(position).getCompanyName());
                intent.putExtra("companyId", mShowModels.get(position).getCompanyId() + "");
                intent.putExtra("token", mShowModels.get(position).getToken());
                setResult(100, intent);
                finish();
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

                Intent intent = new Intent(YiShenHeActivity.this, RegisterActivity.class);
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