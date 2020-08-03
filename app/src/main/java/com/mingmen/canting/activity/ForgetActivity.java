package com.mingmen.canting.activity;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mingmen.canting.R;
import com.mingmen.canting.base.BaseBean;
import com.mingmen.canting.defineview.ClearEditText;
import com.mingmen.canting.http.callback.StringCallback;
import com.mingmen.canting.httpconfig.HttpResponse;
import com.mingmen.canting.immersionbar.ImmersionBar;
import com.mingmen.canting.util.GsonUtil;
import com.mingmen.canting.util.Mobile;
import com.mingmen.canting.util.SharePreferenceUtil;
import com.mingmen.canting.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class ForgetActivity extends AppCompatActivity {

    //返回按钮
    @BindView(R.id.text_return)
    TextView textReturn;
    //    中间按钮
    @BindView(R.id.text_center)
    TextView textCenter;
    //    右键
    @BindView(R.id.img_right)
    ImageView imgRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    //    toolbar导航栏
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.edit_Phone)
    ClearEditText editPhone;
    @BindView(R.id.edit_yanzheng)
    ClearEditText editYanzheng;
    //    验证码阿牛
    @BindView(R.id.btn_getcode)
    Button btnGetcode;
    @BindView(R.id.edit_pwd)
    ClearEditText editPwd;
    @BindView(R.id.img_close_first)
    ImageView imgCloseFirst;
//    眼睛睁和闭

    @BindView(R.id.img_open_first)
    ImageView imgOpenFirst;
    @BindView(R.id.edit_confirm)
    ClearEditText editConfirm;
    @BindView(R.id.img_close_second)
    ImageView imgCloseSecond;
    @BindView(R.id.img_open_second)
    ImageView imgOpenSecond;
    //    登录按钮
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.linear_return)
    LinearLayout linear_return;

    String loginreturnstr;
    private int reckonTime = 60;
    private Handler mReckonHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 0x1) {
                btnGetcode.setText("(" + reckonTime + ")");
                reckonTime--;
                if (reckonTime < 0) {
                    mReckonHandler.sendEmptyMessage(0x2);
                } else {
                    mReckonHandler.sendEmptyMessageDelayed(0x1, 1000);
                }
            } else if (msg.what == 0x2) {
                // 60秒结束
                btnGetcode.setText("发送");
                btnGetcode.setEnabled(true);
                reckonTime = 60;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        ButterKnife.bind(this);
        mToolbar.setBackground(getResources().getDrawable(R.color.transparent));
//        s设置状态栏
        ImmersionBar.with(this).titleBar(mToolbar)
                .navigationBarColor(R.color.black).init();
        loginreturnstr = (String) SharePreferenceUtil.get(this, "loginreturnstr", "");
        textCenter.setText("修改密码");
//点击edit 输入框对光标进行处理 这里面对触摸事件进行自定义 并操作密码明文密文
        editYanzheng.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                imgCloseSecond.setVisibility(View.GONE);
                imgOpenSecond.setVisibility(View.GONE);
                if (editPwd.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
                    imgCloseFirst.setVisibility(View.VISIBLE);
                    imgOpenFirst.setVisibility(View.GONE);
                } else {
                    imgCloseFirst.setVisibility(View.GONE);
                    imgOpenFirst.setVisibility(View.VISIBLE);
                }

                return false;
            }
        });
//        手机号码输入监听事件
        editPhone.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                imgCloseSecond.setVisibility(View.GONE);
                imgOpenSecond.setVisibility(View.GONE);
                if (editPwd.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
                    imgCloseFirst.setVisibility(View.VISIBLE);
                    imgOpenFirst.setVisibility(View.GONE);
                } else {
                    imgCloseFirst.setVisibility(View.GONE);
                    imgOpenFirst.setVisibility(View.VISIBLE);
                }

                return false;
            }
        });
        //        密码输入监听事件
        editPwd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                imgCloseSecond.setVisibility(View.GONE);
                imgOpenSecond.setVisibility(View.GONE);
                if (editPwd.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
                    imgCloseFirst.setVisibility(View.VISIBLE);
                    imgOpenFirst.setVisibility(View.GONE);
                } else {
                    imgCloseFirst.setVisibility(View.GONE);
                    imgOpenFirst.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
        //        确认密码输入监听事件
        editConfirm.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.d("ontouch", "ontouch");
                Log.d("guangdkdk", "dkdkkddk");
                imgCloseFirst.setVisibility(View.GONE);
                imgOpenFirst.setVisibility(View.GONE);
//判断获取明文 密文 并进行操作
                if (editConfirm.getTransformationMethod() == PasswordTransformationMethod.getInstance()) {
                    imgCloseSecond.setVisibility(View.VISIBLE);
                    imgOpenSecond.setVisibility(View.GONE);
                } else {
                    imgCloseSecond.setVisibility(View.GONE);
                    imgOpenSecond.setVisibility(View.VISIBLE);
                }

                return false;

            }
        });


    }


    @OnClick({R.id.text_return, R.id.edit_Phone, R.id.edit_yanzheng, R.id.btn_getcode, R.id.edit_pwd, R.id.img_close_first, R.id.img_open_first, R.id.edit_confirm, R.id.img_close_second, R.id.img_open_second, R.id.btn_login, R.id.linear_return})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_return:
                finish();
                break;
            case R.id.edit_Phone:
                break;
            case R.id.edit_yanzheng:
                break;
            case R.id.btn_getcode:
                if (TextUtils.isEmpty(editPhone.getText().toString())) {
                    ToastUtil.showToast(ForgetActivity.this, "手机号不能为空");
                } else if (!Mobile.isMobile(editPhone.getText().toString())) {
                    ToastUtil.showToast(ForgetActivity.this, "请输入正确手机号");
                } else {
                    getcode();
                }

                break;
            case R.id.edit_pwd:
                break;
            case R.id.img_close_first:

                editPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                imgCloseFirst.setVisibility(View.GONE);
                imgOpenFirst.setVisibility(View.VISIBLE);
                editPwd.setSelection(editPwd.length());
                imgCloseSecond.setVisibility(View.GONE);
                imgOpenSecond.setVisibility(View.GONE);
                break;
            case R.id.img_open_first:
                editPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                editPasswrord.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                imgCloseFirst.setVisibility(View.VISIBLE);
                imgOpenFirst.setVisibility(View.GONE);
                editPwd.setSelection(editPwd.length());
                imgCloseSecond.setVisibility(View.GONE);
                imgOpenSecond.setVisibility(View.GONE);
                break;
            case R.id.edit_confirm:
                break;
            case R.id.img_close_second:


                editConfirm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                imgCloseSecond.setVisibility(View.GONE);
                imgOpenSecond.setVisibility(View.VISIBLE);
                editConfirm.setSelection(editConfirm.length());
                break;
            case R.id.img_open_second:
                editConfirm.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                editPasswrord.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                imgCloseSecond.setVisibility(View.VISIBLE);
                imgOpenSecond.setVisibility(View.GONE);
                editConfirm.setSelection(editConfirm.length());
                break;
            case R.id.btn_login:
                if (TextUtils.isEmpty(editPhone.getText().toString())) {
                    ToastUtil.showToast(ForgetActivity.this, "手机号不能为空");
                } else if (!Mobile.isMobile(editPhone.getText().toString())) {
                    ToastUtil.showToast(ForgetActivity.this, "请输入正确手机号");
                } else if (TextUtils.isEmpty(editYanzheng.getText().toString())) {
                    ToastUtil.showToast(ForgetActivity.this, "验证码不能为空");
                }/* else if (TextUtils.isEmpty(editPwd.getText().toString())) {
                    ToastUtil.showToast(ForgetActivity.this, "密码不能为空");
                } */ else if (TextUtils.isEmpty(editConfirm.getText().toString())) {
                    ToastUtil.showToast(ForgetActivity.this, "确认密码不能为空");
                } else {
//                    ToastUtil.showToast(ForgetActivity.this, "修改密码成功");
                    modefy();

                }

                break;

            case R.id.linear_return:
                finish();
                break;
        }
    }

    private void getcode() {
        HttpResponse.getCode(editPhone.getText().toString(), "2", new StringCallback(ForgetActivity.this) {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                BaseBean baseBean = GsonUtil.GsonToBean(s, BaseBean.class);

                if (baseBean.getCode() == 0) {
                    btnGetcode.setEnabled(false);
                    // 开始计时
                    btnGetcode.setEnabled(false);
                    // 开始计时
                    mReckonHandler.sendEmptyMessage(0x1);


                } else {
//                    Toast.makeText(ForgetActivity.this, baseBean.getMessage() + "", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(ForgetActivity.this, baseBean.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void modefy() {
//        editPwd.getText().toString()
        HttpResponse.Forget("0", editPhone.getText().toString(), editYanzheng.getText().toString(), "0", editConfirm.getText().toString(), "2", new StringCallback(ForgetActivity.this) {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                Log.d("ForgetActivity", s);
                BaseBean baseBean = GsonUtil.GsonToBean(s, BaseBean.class);
                if (baseBean.getCode() == 0) {
                    Toast.makeText(ForgetActivity.this, baseBean.getMessage() + "", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ForgetActivity.this, baseBean.getMessage() + "", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}




