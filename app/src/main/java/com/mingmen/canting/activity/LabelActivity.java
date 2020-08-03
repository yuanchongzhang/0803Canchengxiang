package com.mingmen.canting.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mingmen.canting.R;
import com.mingmen.canting.adapter.LabelAdapter;
import com.mingmen.canting.adapter.MineRadioAdapter;
import com.mingmen.canting.definerecycleview.SwipeRecyclerView;
import com.mingmen.canting.model.MyLiveList;
import com.mingmen.canting.util.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LabelActivity extends Activity implements LabelAdapter.OnItemClickListener {

    private static final int MYLIVE_MODE_CHECK = 0;
    private static final int MYLIVE_MODE_EDIT = 1;
    @BindView(R.id.btn_editor)
    TextView mBtnEditor;
    @BindView(R.id.recyclerview)
    SwipeRecyclerView mRecyclerview;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.tv_select_num)
    TextView mTvSelectNum;
    @BindView(R.id.btn_delete)
    Button mBtnDelete;
    @BindView(R.id.select_all)
    TextView mSelectAll;
    @BindView(R.id.ll_mycollection_bottom_dialog)
    LinearLayout mLlMycollectionBottomDialog;

    private LabelAdapter labelAdapter = null;
    private LinearLayoutManager mLinearLayoutManager;
    private List<MyLiveList> mList = new ArrayList<>();
    private int mEditMode = MYLIVE_MODE_CHECK;
    private boolean isSelectAll = false;
    private boolean editorStatus = false;
    private int index = 0;
    @BindView(R.id.tv_list)
    TextView tv_list;
    @BindView(R.id.tv_selectall)
    TextView tv_selectall;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chceckbox);
        ButterKnife.bind(this);

        initView();
        initData();
        initListener();
    }

    private void initView() {
    }

    private void initData() {
        labelAdapter = new LabelAdapter(this);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(mLinearLayoutManager);
        DividerItemDecoration itemDecorationHeader = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        itemDecorationHeader.setDividerDrawable(ContextCompat.getDrawable(this, R.drawable.divider_main_bg_height_1));
        mRecyclerview.addItemDecoration(itemDecorationHeader);

        mRecyclerview.setAdapter(labelAdapter);
        for (int i = 0; i < 30; i++) {
            MyLiveList myLiveList = new MyLiveList();
            myLiveList.setTitle("这是第" + i + "个条目");
            myLiveList.setSource("来源" + i);
            mList.add(myLiveList);
            labelAdapter.notifyAdapter(mList, false);
        }


    }

    /**
     * 根据选择的数量是否为0来判断按钮的是否可点击.
     *
     * @param size
     */
    private void setBtnBackground(int size) {
        if (size != 0) {
            mBtnDelete.setBackgroundResource(R.drawable.button_shape);
            mBtnDelete.setEnabled(true);
            mBtnDelete.setTextColor(Color.WHITE);
        } else {
            mBtnDelete.setBackgroundResource(R.drawable.button_noclickable_shape);
            mBtnDelete.setEnabled(false);
            mBtnDelete.setTextColor(ContextCompat.getColor(this, R.color.color_b7b8bd));
        }
    }

    private void initListener() {
        labelAdapter.setOnItemClickListener(this);
        editorStatus = true;
    }

    @OnClick({R.id.btn_editor, R.id.recyclerview, R.id.tv, R.id.tv_select_num, R.id.btn_delete, R.id.select_all, R.id.ll_mycollection_bottom_dialog,R.id.tv_selectall})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_editor:
                updataEditMode();
                break;
            case R.id.recyclerview:
                break;
            case R.id.tv:
                break;
            case R.id.tv_select_num:
                break;
            case R.id.btn_delete:
                deleteVideo();
                break;
            case R.id.select_all:
                selectAllMain();
                break;
            case R.id.ll_mycollection_bottom_dialog:
                break;
            case R.id.tv_selectall:
                selectAllMain();
                break;
        }
    }


    /**
     * 全选和反选
     */
    private void selectAllMain() {
        if (labelAdapter == null) return;
        if (!isSelectAll) {
            myList.clear();
            for (int i = 0, j = labelAdapter.getMyLiveList().size(); i < j; i++) {
                labelAdapter.getMyLiveList().get(i).setSelect(true);
//                myList.add(labelAdapter.getMyLiveList().get(i).getTitle());
                myList.add(labelAdapter.getMyLiveList().get(i).getTitle());
            }
            index = labelAdapter.getMyLiveList().size();

            tv_list.setText(myList.toString());
            mBtnDelete.setEnabled(true);
            mSelectAll.setText("取消全选");
            isSelectAll = true;

        } else {
            for (int i = 0, j = labelAdapter.getMyLiveList().size(); i < j; i++) {
                labelAdapter.getMyLiveList().get(i).setSelect(false);
            }
            index = 0;
            mBtnDelete.setEnabled(false);
            mSelectAll.setText("全选");
            isSelectAll = false;
        }
        labelAdapter.notifyDataSetChanged();
        setBtnBackground(index);
        mTvSelectNum.setText(String.valueOf(index));
    }

    /**
     * 删除逻辑
     */
    private void deleteVideo() {
        if (index == 0) {
            mBtnDelete.setEnabled(false);
            return;
        }
        final AlertDialog builder = new AlertDialog.Builder(this)
                .create();
        builder.show();
        if (builder.getWindow() == null) return;
        builder.getWindow().setContentView(R.layout.pop_user);//设置弹出框加载的布局
        TextView msg = (TextView) builder.findViewById(R.id.tv_msg);
        Button cancle = (Button) builder.findViewById(R.id.btn_cancle);
        Button sure = (Button) builder.findViewById(R.id.btn_sure);
        if (msg == null || cancle == null || sure == null) return;

        if (index == 1) {
            msg.setText("删除后不可恢复，是否删除该条目？");
        } else {
            msg.setText("删除后不可恢复，是否删除这" + index + "个条目？");
        }
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = labelAdapter.getMyLiveList().size(), j = 0; i > j; i--) {
                    MyLiveList myLive = labelAdapter.getMyLiveList().get(i - 1);
                    if (myLive.isSelect()) {

                        labelAdapter.getMyLiveList().remove(myLive);
                        index--;
                    }
                }
                index = 0;
                mTvSelectNum.setText(String.valueOf(0));
                setBtnBackground(index);
                if (labelAdapter.getMyLiveList().size() == 0) {
                    mLlMycollectionBottomDialog.setVisibility(View.GONE);
                }
                labelAdapter.notifyDataSetChanged();
                builder.dismiss();
            }
        });
    }

    private void updataEditMode() {
        mEditMode = mEditMode == MYLIVE_MODE_CHECK ? MYLIVE_MODE_EDIT : MYLIVE_MODE_CHECK;
        if (mEditMode == MYLIVE_MODE_EDIT) {
            mBtnEditor.setText("取消");
            mLlMycollectionBottomDialog.setVisibility(View.VISIBLE);
            editorStatus = true;
        } else {
            mBtnEditor.setText("编辑");
            mLlMycollectionBottomDialog.setVisibility(View.GONE);
            editorStatus = false;
            clearAll();
        }
        labelAdapter.setEditMode(mEditMode);
    }


    private void clearAll() {
        mTvSelectNum.setText(String.valueOf(0));
        isSelectAll = false;
        mSelectAll.setText("全选");
        setBtnBackground(0);
    }

    List<String> myList = new ArrayList<>();

    @Override

    public void onItemClickListener(int pos, List<MyLiveList> myLiveList) {
        if (editorStatus) {
            MyLiveList myLive = myLiveList.get(pos);
            boolean isSelect = myLive.isSelect();
            if (!isSelect) {
                index++;
                myLive.setSelect(true);
                if (index == myLiveList.size()) {
                    isSelectAll = true;
                    mSelectAll.setText("取消全选");
                }
                myList.add(myLive.getTitle());

            } else {
                myLive.setSelect(false);
                myList.remove(myLive.getTitle());
                index--;
                isSelectAll = false;
                mSelectAll.setText("全选");
            }

            tv_list.setText(myList.toString());

            setBtnBackground(index);
            mTvSelectNum.setText(String.valueOf(index));
            labelAdapter.notifyDataSetChanged();
        }
    }
}
