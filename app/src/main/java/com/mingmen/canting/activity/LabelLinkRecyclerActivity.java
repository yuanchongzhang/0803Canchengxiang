package com.mingmen.canting.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mingmen.canting.R;
import com.mingmen.canting.adapter.LinkLabelAdapter;
import com.mingmen.canting.model.LinkLabelBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LabelLinkRecyclerActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.email_sign_in_button)
    Button btn;
    @BindView(R.id.text_select)
    TextView textSelect;

    private List<LinkLabelBean> list = new ArrayList<>();

    private Gson gson = new Gson();

    private String[] str = new String[]{"标题1", "帅哥", "校园", "军事", "悬疑", "科幻", "露露", "奇幻", "韦恩", "标题2",
            "古言", "抢女", "总裁", "网红", "穿越", "美女", "灵异", "色魔", "标题3",
            "恐怖", "红魔", "言情", "科幻", "诺克萨斯", "人妖", "荒岛", "其他"};
    private Map<String, List<String>> hashMap = new HashMap<>();
    private LinkLabelBean linkLabelBean;
    LinkLabelAdapter linkLabelAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label_link_recycler);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        for (int i = 0; i < str.length; i++) {
            linkLabelBean = new LinkLabelBean();
            linkLabelBean.setStr(str[i]);
            list.add(linkLabelBean);
        }
    }

    private int Zeng = 0;

    private void initView() {
        final GridLayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 4);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int type = recyclerView.getAdapter().getItemViewType(position);//获得返回值
                if (type == 99) {
                    return mLayoutManager.getSpanCount();
                } else {
                    return 1;
                }
            }
        });
        recyclerView.setLayoutManager(mLayoutManager);
        linkLabelAdapter = new LinkLabelAdapter(getApplicationContext(), list);
        recyclerView.setAdapter(linkLabelAdapter);


        linkLabelAdapter.setOnItemClickListener(new LinkLabelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, LinkLabelAdapter.ViewName viewName, int position) {
                switch (v.getId()) {
                    case R.id.textViewContent:
                        Toast.makeText(LabelLinkRecyclerActivity.this, "text" + (position + 1), Toast.LENGTH_SHORT).show();


                        break;

                    default:

                        Toast.makeText(LabelLinkRecyclerActivity.this, "你点击了item按钮" + (position + 1), Toast.LENGTH_SHORT).show();
                        break;
                }
            }




        });

    }

    @OnClick({R.id.email_sign_in_button, R.id.text_select})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.email_sign_in_button:
                //这边其实可以优化 但是时间不够就不再做优化。
                boolean zhishao = false;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).isSelect()) {
                        zhishao = true;
                    }
                }
                if (!zhishao) {
//                    ToastUtil.showToast(LabelLinkRecyclerActivity.class, "至少选择一个标签。");
                    return;
                }
                hashMap.clear();

                String s = gson.toJson(hashMap);
//                ToastUtils.showSingleToast("选中数据--" + s);
                Toast.makeText(this, "选中数据--" + s + "", Toast.LENGTH_SHORT).show();
                break;
            case R.id.text_select:

                break;

        }
    }


}
