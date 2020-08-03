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
import com.mingmen.canting.adapter.YuCaiGouDanAdapter;
import com.mingmen.canting.adapter.ZhiweiAdapter;
import com.mingmen.canting.defineview.ClearEditText;
import com.mingmen.canting.http.callback.StringCallback;
import com.mingmen.canting.httpconfig.HttpResponse;
import com.mingmen.canting.model.ZhiweiBean;
import com.mingmen.canting.util.PinnedHeaderDecoration;
import com.mingmen.canting.util.Trans2PinYinUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import okhttp3.Call;
import okhttp3.Response;


/**
 * <p>
 * ListView形式的侧滑菜单，支持横向和竖向的。
 * </p>
 * Created by Yan Zhenjie on 2016/7/27.
 */
public class YuCaiGouDanActivity extends Activity {
    @BindView(R.id.img_return)
    ImageView imgReturn;

    @BindView(R.id.layout_close)
    LinearLayout layoutClose;

    private RecyclerView mRecyclerView;

    private ClearEditText mSearchEditText;
    private YuCaiGouDanAdapter mAdapter;
    String string;
    String attrs = "";
    List<String> mylist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitterlogin);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        for (int i = 0; i < 5; i++) {
            mylist.add("i" + i);
        }
        bindView();
        string = "{" +
                "code\":200,\n" +
                "    \"message\":\"餐厅列表\",\n" +
                "    \"time\":\"2020-07-22 11:15:59\",\n" +
                "    \"data\":[\n" +
                "        {\"companyId\":1285772488172769280,\"companyName\":\"111\",\"departmentId\":0,\"userId\":0}\n" +
                "    ]\n" +
                "}";


    }

    private void bindView() {

        mAdapter = new YuCaiGouDanAdapter(mylist);

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

   /*     mAdapter.setOnitemClickLintener(new ZhiweiAdapter.OnitemClick() {
            @Override
            public void onItemClick(int position) {

                Log.d("kdkdkdkf", mShowModels.get(position).getPositionNamName() + "");
//                setResult(1, new Intent().putExtra("value", "data1"));
                Intent intent = new Intent(YuCaiGouDanActivity.this, RegisterActivity.class);
                intent.putExtra("position", mShowModels.get(position).getPosition() + "");
                intent.putExtra("companyName", mShowModels.get(position).getPositionNamName());
                setResult(200, intent);
                finish();
            }
        });*/
   mAdapter.setOnitemClickLintener(new YuCaiGouDanAdapter.OnitemClick() {
       @Override
       public void onItemClick(int position) {
           Log.d("kdkdkdkf", mylist.get(position) + "");
       }
   });


    }






    @OnClick({R.id.img_return, R.id.layout_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_close:

            case R.id.img_return:
                Intent intent = new Intent(YuCaiGouDanActivity.this, RegisterActivity.class);
                intent.putExtra("position", "");
                intent.putExtra("companyName", "请选择您的岗位");
                setResult(200, intent);

                finish();
                break;

        }
    }

    public void onEventMainThread(Integer type) {

    }


}