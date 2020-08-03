package com.mingmen.canting.jingli;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mingmen.canting.R;
import com.mingmen.canting.adapter.DaishenheListAdapter;
import com.mingmen.canting.adapter.JingLiDaishenheListAdapter;
import com.mingmen.canting.definerecycleview.SwipeRecyclerView;
import com.mingmen.canting.defineview.ToastDialog;
import com.mingmen.canting.dialog.ButtomDialogView;
import com.mingmen.canting.model.MyLiveList;
import com.mingmen.canting.util.DividerItemDecoration;
import com.mingmen.canting.zhenban.DingDanActivity;
import com.mingmen.canting.zhenban.ZhenBanDingDanActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

@SuppressLint("ValidFragment")
public class JingLiDaiShenHeListragment extends Fragment implements JingLiDaishenheListAdapter.OnItemClickListener {
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
    @BindView(R.id.check_daishen)
    CheckBox check_daishen;
    LinearLayout llMycollectionBottomDialog;
    Unbinder unbinder;
    @BindView(R.id.btn_digndan)
    Button btn_digndan;
    @BindView(R.id.btn_suofang)
    Button btnSuofang;
    @BindView(R.id.img_list)
    ImageView imgList;
    @BindView(R.id.tv_list)
    TextView tvList;
    private String mTitle;
    //Fragment的View加载完毕的标记
    private boolean isLoading = false;

    //Fragment对用户可见的标记
    private boolean isUIVisible;

    //    private static final int MYLIVE_MODE_CHECK = 0;
    private static final int MYLIVE_MODE_CHECK = 0;
    private static final int MYLIVE_MODE_EDIT = 1;
    private JingLiDaishenheListAdapter mRadioAdapter = null;
    private LinearLayoutManager mLinearLayoutManager;
    private List<MyLiveList> mList = new ArrayList<>();
    private int mEditMode = MYLIVE_MODE_CHECK;
    private boolean isSelectAll = false;
    private boolean editorStatus = false;
    private int index = 0;

    public static JingLiDaiShenHeListragment getInstance(String title) {
        JingLiDaiShenHeListragment sf = new JingLiDaiShenHeListragment();
        sf.mTitle = title;
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    ToastDialog toastDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frjingli_daishenhe, null);
        TextView card_title_tv = (TextView) v.findViewById(R.id.card_title_tv);
        card_title_tv.setText(mTitle);

        unbinder = ButterKnife.bind(this, v);
        initView();
        initData();
        initListener();
        imgList.setVisibility(View.GONE);
        toastDialog = new ToastDialog(getActivity());


        toastDialog.setVerifyClickListener("该条订单正在被审核", new ToastDialog.VerifyClickListener() {
            @Override
            public void cancel() {

            }

            @Override
            public void send(String str) {

            }
        });

        check_daishen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                toastDialog.show();

                if (b) {
                    selectAllMain();
                } else {
                    selectAllMain();
                }
            }
        });
        return v;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isUIVisible = true;
            lazyLoad();//调用下面的方法
        } else {
            isUIVisible = false;
        }
    }

    private void lazyLoad() {
        if (!isLoading && isUIVisible) {
//            这里只加载一次数据
            Log.d("SimpleCardFragment", "66666");
            //数据加载完毕,恢复标记,防止重复加载
            isLoading = true;
            isUIVisible = false;
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void initView() {
    }

    private void initData() {
        mRadioAdapter = new JingLiDaishenheListAdapter(getActivity());
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerview.setLayoutManager(mLinearLayoutManager);
        DividerItemDecoration itemDecorationHeader = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        itemDecorationHeader.setDividerDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divider_main_bg_height_1));
        mRecyclerview.addItemDecoration(itemDecorationHeader);

        mRecyclerview.setAdapter(mRadioAdapter);
        for (int i = 0; i < 30; i++) {
            MyLiveList myLiveList = new MyLiveList();
            myLiveList.setTitle("这是第" + i + "个条目");
            myLiveList.setSource("来源" + i);
            mList.add(myLiveList);
            mRadioAdapter.notifyAdapter(mList, false);
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
            mBtnDelete.setTextColor(ContextCompat.getColor(getActivity(), R.color.color_b7b8bd));
        }
    }

    private void initListener() {
        mRadioAdapter.setOnItemClickListener(this);

    }

    @OnClick({R.id.btn_editor, R.id.recyclerview, R.id.tv, R.id.tv_select_num, R.id.btn_delete, R.id.select_all, R.id.ll_mycollection_bottom_dialog, R.id.btn_digndan, R.id.btn_suofang, R.id.img_list, R.id.tv_list})
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
            case R.id.btn_digndan:
//                startActivity(new Intent(getActivity(), DingDanActivity.class));
                startActivity(new Intent(getActivity(), ZhenBanDingDanActivity.class));
                break;

            case R.id.btn_suofang:
                btn_digndan.setVisibility(View.GONE);
                btnSuofang.setVisibility(View.VISIBLE);
                imgList.setVisibility(View.VISIBLE);
                break;
            case R.id.img_list:
                startActivity(new Intent(getActivity(), DingDanActivity.class));
                break;
            case R.id.tv_list:
                break;
        }
    }


    /**
     * 全选和反选
     */
    private void selectAllMain() {
        if (mRadioAdapter == null) return;
        if (!isSelectAll) {
            for (int i = 0, j = mRadioAdapter.getMyLiveList().size(); i < j; i++) {
                mRadioAdapter.getMyLiveList().get(i).setSelect(true);
            }
            index = mRadioAdapter.getMyLiveList().size();
            mBtnDelete.setEnabled(true);
            mSelectAll.setText("取消全选");
            isSelectAll = true;
        } else {
            for (int i = 0, j = mRadioAdapter.getMyLiveList().size(); i < j; i++) {
                mRadioAdapter.getMyLiveList().get(i).setSelect(false);
            }
            index = 0;
            mBtnDelete.setEnabled(false);
            mSelectAll.setText("全选");
            isSelectAll = false;
        }
        mRadioAdapter.notifyDataSetChanged();
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
        final AlertDialog builder = new AlertDialog.Builder(getActivity())
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
                for (int i = mRadioAdapter.getMyLiveList().size(), j = 0; i > j; i--) {
                    MyLiveList myLive = mRadioAdapter.getMyLiveList().get(i - 1);
                    if (myLive.isSelect()) {

                        mRadioAdapter.getMyLiveList().remove(myLive);
                        index--;
                    }
                }
                index = 0;
                mTvSelectNum.setText(String.valueOf(0));
                setBtnBackground(index);
                if (mRadioAdapter.getMyLiveList().size() == 0) {
                    mLlMycollectionBottomDialog.setVisibility(View.GONE);
                }
                mRadioAdapter.notifyDataSetChanged();
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

        mRadioAdapter.setEditMode(mEditMode);
    }


    private void clearAll() {
        mTvSelectNum.setText(String.valueOf(0));
        isSelectAll = false;
        mSelectAll.setText("全选");
        setBtnBackground(0);
    }


    @Override
    public void onItemClickListener(View view, int pos, JingLiDaishenheListAdapter.ViewName viewName, List<MyLiveList> myLiveList) {

        switch (view.getId()) {
            case R.id.btn_zhuijia:
                Toast.makeText(getActivity(), "price" + pos, Toast.LENGTH_SHORT).show();
                break;

            case R.id.check_box:
                MyLiveList myLive = myLiveList.get(pos);
                boolean isSelect = myLive.isSelect();
                if (!isSelect) {
                    index++;
                    myLive.setSelect(true);
                    if (index == myLiveList.size()) {
                        isSelectAll = true;
                        mSelectAll.setText("取消全选");
                    }

                } else {
                    myLive.setSelect(false);
                    index--;
                    isSelectAll = false;
                    mSelectAll.setText("全选");
                }
                setBtnBackground(index);
                mTvSelectNum.setText(String.valueOf(index));
                mRadioAdapter.notifyDataSetChanged();
                Log.d("dkdkkd", index + "");
                break;
        }


    }


}