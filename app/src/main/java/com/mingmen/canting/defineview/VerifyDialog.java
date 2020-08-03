package com.mingmen.canting.defineview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mingmen.canting.R;
import com.mingmen.canting.adapter.MainAdapter2;
import com.mingmen.canting.definerecycleview.OnItemClickListener;
import com.mingmen.canting.definerecycleview.SwipeRecyclerView;
import com.mingmen.canting.definerecycleview.widget.DefaultItemDecoration;
import com.mingmen.canting.util.ScreenUtil;

import java.util.ArrayList;
import java.util.List;


public class VerifyDialog extends Dialog implements OnItemClickListener {
    private TextView mTipTv;
    private EditText mVerifyEdit;
    private TextView mCancel;
    private TextView mSend;

    private String mTip;
    private String mHint;
    private String mText;
    private int mInputLength;
    private VerifyClickListener mVerifyClickListener;
    private boolean dismiss = true;
    @StringRes
    private Integer cancel;
    private Integer ok;
    private Context mContext;

    public VerifyDialog(Context context) {
        super(context, R.style.MyDialog);
        this.mContext = context;
    }

    public void setVerifyClickListener(String tip, VerifyClickListener verifyClickListener) {
        this.mTip = tip;
        this.mVerifyClickListener = verifyClickListener;
    }

    public void setVerifyClickListener(String tip, String hint, VerifyClickListener verifyClickListener) {
        this.mTip = tip;
        this.mHint = hint;
        this.mVerifyClickListener = verifyClickListener;
    }

    public void setVerifyClickListener(String tip, String hint, String text, int inputLength, VerifyClickListener verifyClickListener) {
        this.mTip = tip;
        this.mHint = hint;
        this.mText = text;
        this.mInputLength = inputLength;
        this.mVerifyClickListener = verifyClickListener;
    }

    protected SwipeRecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected RecyclerView.ItemDecoration mItemDecoration;

    protected MainAdapter2 mAdapter;
    protected List<String> mDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_dialog);
        setCanceledOnTouchOutside(false);
        initView();
        mRecyclerView = findViewById(R.id.recycler_view);

        mLayoutManager = new LinearLayoutManager(mContext);
        mItemDecoration =new DefaultItemDecoration(ContextCompat.getColor(mContext, R.color.divider_color));
        mDataList = createDataList();
        mAdapter = new MainAdapter2(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(mItemDecoration);
        mRecyclerView.setOnItemClickListener(this);



        mRecyclerView.setAdapter(mAdapter);
//        mAdapter.notifyDataSetChanged(mDataList);
        mAdapter.setDataList(mDataList);
    }
    protected List<String> createDataList() {
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dataList.add("第" + i + "个Item");
        }
        return dataList;
    }
    private void initView() {
        mTipTv = (TextView) findViewById(R.id.tip_tv);
        if (!TextUtils.isEmpty(mTip)) {
            mTipTv.setText(mTip);
        }
        mVerifyEdit = (EditText) findViewById(R.id.verify_et);
        if (!TextUtils.isEmpty(mHint)) {
            mVerifyEdit.setHint(mHint);
        }
        if (!TextUtils.isEmpty(mText)) {
            mVerifyEdit.setText(mText);
        }
        if (mInputLength != 0) {
            mVerifyEdit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mInputLength)});
        }
        mCancel = (TextView) findViewById(R.id.cancel);
        mSend = (TextView) findViewById(R.id.send);
        if (cancel != null) {
            mCancel.setText(cancel);
        }
        if (ok != null) {
            mSend.setText(ok);
        }

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = (int) (ScreenUtil.getScreenWidth(getContext()) * 0.9);
        initEvent();
    }

    private void initEvent() {
        mCancel.setOnClickListener(v -> {
            if (dismiss) {
                dismiss();
            }
            if (mVerifyClickListener != null) {
                mVerifyClickListener.cancel();
            }
        });

        mSend.setOnClickListener(v -> {
            if (dismiss) {
                dismiss();
            }
            String str = mVerifyEdit.getText().toString();
            if (mVerifyClickListener != null) {
                mVerifyClickListener.send(str);
            }
        });
    }

    public void setDismiss(boolean dismiss) {
        this.dismiss = dismiss;
    }

    public void setCancelButton(@StringRes int cancel) {
        this.cancel = cancel;
        if (mCancel != null) {
            mCancel.setText(cancel);
        }
    }

    public void setOkButton(@StringRes int ok) {
        this.ok = ok;
        if (mSend != null) {
            mSend.setText(ok);
        }
    }

    @Override
    public void onItemClick(View view, int adapterPosition) {
        Toast.makeText(mContext, adapterPosition+"", Toast.LENGTH_SHORT).show();
        if (dismiss) {
            dismiss();
        }
        String str = adapterPosition+"";
        if (mVerifyClickListener != null) {
            mVerifyClickListener.send(str);
        }

    }

    public interface VerifyClickListener {
        void cancel();

        void send(String str);
    }
}
