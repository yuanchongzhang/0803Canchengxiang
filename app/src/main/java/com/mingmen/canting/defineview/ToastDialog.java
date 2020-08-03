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


public class ToastDialog extends Dialog   {
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

    public ToastDialog(Context context) {
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


    protected MainAdapter2 mAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toast_dialog);
        setCanceledOnTouchOutside(true);
        initView();


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



    public interface VerifyClickListener {
        void cancel();

        void send(String str);
    }
}
