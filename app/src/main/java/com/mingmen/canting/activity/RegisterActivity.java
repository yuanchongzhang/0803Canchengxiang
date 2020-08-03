package com.mingmen.canting.activity;


import android.content.Intent;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.edit_Phone)
    ClearEditText editPhone;
    @BindView(R.id.edit_yanzheng)
    ClearEditText editYanzheng;
    @BindView(R.id.btn_getcode)
    Button btnGetcode;
    @BindView(R.id.edit_pwd)
    ClearEditText editPwd;
    @BindView(R.id.edit_confirm)
    ClearEditText editConfirm;
    @BindView(R.id.text_canting)
    TextView textCanting;
    @BindView(R.id.img_cantingarrow)
    ImageView imgCantingarrow;
    @BindView(R.id.text_gangwei)
    TextView textGangwei;
    @BindView(R.id.img_ganweiarrow)
    ImageView imgGanweiarrow;
    @BindView(R.id.btn_register)
    Button btnRegister;


    @BindView(R.id.text_return)
    TextView textReturn;
    @BindView(R.id.text_center)
    TextView textCenter;
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.text_register)
    TextView textRegister;
    @BindView(R.id.img_close_first)
    ImageView imgCloseFirst;
    @BindView(R.id.img_open_first)
    ImageView imgOpenFirst;
    @BindView(R.id.img_close_second)
    ImageView imgCloseSecond;
    @BindView(R.id.img_open_second)
    ImageView imgOpenSecond;
    @BindView(R.id.check_register)
    CheckBox checkRegister;
    @BindView(R.id.edit_name)
    ClearEditText editName;
    @BindView(R.id.linear_return)
    LinearLayout linear_return;

    private int reckonTime = 60;
    //    验证码倒计时
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

    String id_canting, id_gangwei;
    String restruantData;

    private int resurtResurant = 100;
    private int resurtPosition = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        mToolbar.setBackground(getResources().getDrawable(R.color.transparent));
        textCenter.setText("注册");
        ImmersionBar.with(this).titleBar(mToolbar)
                .navigationBarColor(R.color.black).init();
        getRestruantData();
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
        checkRegister.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });
    }


    @OnClick({R.id.edit_Phone, R.id.edit_yanzheng, R.id.btn_getcode, R.id.edit_pwd, R.id.edit_confirm, R.id.text_canting, R.id.img_cantingarrow, R.id.text_gangwei, R.id.img_ganweiarrow, R.id.btn_register, R.id.text_return, R.id.text_center, R.id.img_close_first, R.id.img_open_first, R.id.img_close_second, R.id.img_open_second, R.id.linear_return})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.edit_Phone:
                break;
            case R.id.edit_yanzheng:
                break;
            case R.id.btn_getcode:
                if (TextUtils.isEmpty(editPhone.getText().toString())) {
                    ToastUtil.showToast(RegisterActivity.this, "手机号不能为空");
                } else if (!Mobile.isMobile(editPhone.getText().toString())) {
                    ToastUtil.showToast(RegisterActivity.this, "请输入正确手机号");
                } else {

                    getcode();
                }


                break;
            case R.id.edit_pwd:
                break;
            case R.id.edit_confirm:
                break;
            case R.id.text_canting:
            case R.id.img_cantingarrow:
//                startActivity(new Intent(RegisterActivity.this, FilterListActivity.class));
                Intent intent = new Intent(RegisterActivity.this, FilterLoginListActivity.class);
                intent.putExtra("restruant", restruantData);
                startActivityForResult(intent, resurtResurant);

//                startActivity(new Intent(RegisterActivity.this, FilterListActivity.class));
//                verifyDialog.show();
                break;


            case R.id.text_gangwei:
            case R.id.img_ganweiarrow:
//                startActivity(new Intent(RegisterActivity.this, FilterListActivity.class));

//                startActivity(new Intent(RegisterActivity.this, ReplaceFragmentActivity.class));
//                resurtPosition
                Intent intent2 = new Intent(RegisterActivity.this, FilterZhiWeiActivity.class);
                intent2.putExtra("restruant", resurtPosition);
                startActivityForResult(intent2, resurtPosition);

//                startActivity(new Intent(RegisterActivity.this, FilterListActivity.class));

                break;
            case R.id.btn_register:
                if (TextUtils.isEmpty(editPhone.getText().toString().trim())) {
                    ToastUtil.showToast(RegisterActivity.this, "手机号不能为空");
                } else if (!Mobile.isMobile(editPhone.getText().toString().trim())) {
                    ToastUtil.showToast(RegisterActivity.this, "请输入正确手机号");
                } else if (TextUtils.isEmpty(editYanzheng.getText().toString().trim())) {
                    ToastUtil.showToast(RegisterActivity.this, "验证码不能为空");
                } else if (TextUtils.isEmpty(editPwd.getText().toString().trim())) {
                    ToastUtil.showToast(RegisterActivity.this, "密码不能为空");
                } else if (TextUtils.isEmpty(editConfirm.getText().toString().trim())) {
                    ToastUtil.showToast(RegisterActivity.this, "确认密码不能为空");
                } else if (!editPwd.getText().toString().trim().equals(editConfirm.getText().toString().trim())) {
                    ToastUtil.showToast(RegisterActivity.this, "两次输入密码不一致");
                } else if (TextUtils.isEmpty(editName.getText().toString().trim())) {
                    ToastUtil.showToast(RegisterActivity.this, "请输入您的姓名");
                } else if (TextUtils.isEmpty(companyId)) {
                    ToastUtil.showToast(RegisterActivity.this, "请选择餐厅");
                } else if (TextUtils.isEmpty(position)) {
                    ToastUtil.showToast(RegisterActivity.this, "请选择岗位");
                } else {
                    Register();
                }

                break;
            case R.id.text_register:

                break;
            case R.id.text_return:
                finish();
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
            case R.id.linear_return:
                finish();
                break;
        }
    }

    private void getcode() {
        HttpResponse.getCode(editPhone.getText().toString(), "2", new StringCallback(RegisterActivity.this) {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                BaseBean baseBean = GsonUtil.GsonToBean(s, BaseBean.class);


                if (baseBean.getCode() == 0) {
                    btnGetcode.setEnabled(false);
                    // 开始计时
                    mReckonHandler.sendEmptyMessage(0x1);
                } else {
                    Toast.makeText(RegisterActivity.this, baseBean.getMessage() + "", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(RegisterActivity.this, baseBean.getMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Register() {

        Log.d("ddddd", editYanzheng.getText().toString());
        Log.d("ddddd", editPhone.getText().toString());
        Log.d("ddddd", editPwd.getText().toString());

// companyId;position, ;
        HttpResponse.getRegister(editPhone.getText().toString().trim(), editPwd.getText().toString(), editYanzheng.getText().toString(), companyId, "0", position, "2", editName.getText().toString(),
                new StringCallback(RegisterActivity.this) {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
//                        startActivity(new Intent(RegisterActivity.this, RegisterShenheActivity.class));
//                        finish();

                        BaseBean baseBean = GsonUtil.GsonToBean(s, BaseBean.class);

                        if (baseBean.getCode() == 0) {
                            startActivity(new Intent(RegisterActivity.this, RegisterShenheActivity.class));
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, baseBean.getMessage() + "", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void getRestruantData() {
        HttpResponse.getRestruant(new StringCallback(this) {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                restruantData = s;
            }
        });
    }

    private String comPanyName, companyId;
    String position, positionName;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == resurtResurant) {
            comPanyName = data.getStringExtra("companyName");
            companyId = data.getStringExtra("companyId");
            Log.d("dkdkdk", comPanyName);
            Log.d("dkdkdk", companyId);
            textCanting.setText(comPanyName);
            SharePreferenceUtil.put(RegisterActivity.this, "companyId", companyId);

        } else if (requestCode == resurtPosition) {
            positionName = data.getStringExtra("companyName");
            Log.d("dkdkdk", positionName);
            position = data.getStringExtra("position");
            textGangwei.setText(positionName);
        }
    }
}




