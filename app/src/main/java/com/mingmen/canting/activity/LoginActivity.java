package com.mingmen.canting.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mingmen.canting.R;
import com.mingmen.canting.base.BaseBean;
import com.mingmen.canting.defineview.ClearEditText;
import com.mingmen.canting.http.MyOkhttp;
import com.mingmen.canting.http.callback.StringCallback;
import com.mingmen.canting.httpconfig.HttpResponse;
import com.mingmen.canting.immersionbar.ImmersionBar;
import com.mingmen.canting.model.LoginBean;
import com.mingmen.canting.model.LoginReturnBean;
import com.mingmen.canting.tabuse.ui.SimpleHomeActivity;
import com.mingmen.canting.util.DESUtil;
import com.mingmen.canting.util.GsonUtil;
import com.mingmen.canting.util.Mobile;
import com.mingmen.canting.util.SharePreferenceUtil;
import com.mingmen.canting.util.ToastUtil;
import com.mingmen.canting.zhenban.ZhenBanDingDanActivity;
import com.mingmen.canting.zhenban.ZhenBanReplaceFragmentActivity;
import com.scwang.smart.refresh.layout.util.DesignUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    //登录按钮
    @BindView(R.id.btn_login)
    Button btnLogin;


    @BindView(R.id.edit_Phone)
    ClearEditText editPhone;
    @BindView(R.id.edit_Passwrord)
    ClearEditText editPasswrord;

    @BindView(R.id.text_forget)
    TextView textForget;
    @BindView(R.id.text_register)
    TextView textRegister;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    //    密码可见
    @BindView(R.id.img_open)
    ImageView imgOpen;
    //    密码不可见
    @BindView(R.id.img_close)
    ImageView imgClose;
    @BindView(R.id.img_logo)
    ImageView imgLogo;
    private boolean flag;
    String jsonReturn;
    List<LoginReturnBean.DataBean> dataBeanList = new ArrayList<>();
    String companyId;
    private int resurtResurant = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        ImmersionBar.with(this).titleBar(tvLogin)
                .navigationBarColor(R.color.black).init();
        companyId = (String) SharePreferenceUtil.get(this, "companyId", "");
        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(LoginActivity.this, FilterNewLoginListActivity.class));
                startActivity(new Intent(LoginActivity.this, ZhenBanReplaceFragmentActivity.class));
            }
        });
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(LoginActivity.this, SimpleHomeActivity.class));
                startActivity(new Intent(LoginActivity.this, ZhenBanDingDanActivity.class));
            }
        });


        //待加密内容
        String str = "测试内容";
        //密码，长度要是8的倍数
        String password = "mingmenkeji";

        byte[] result = DESUtil.encrypt(str.getBytes(), password);
        System.out.println("加密后：" + new String(result));
        //直接将如上内容解密
        try {
            byte[] decryResult = DESUtil.decrypt(result, password);
            System.out.println("解密后：" + new String(decryResult));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        jsonReturn = "{" +
                "code\":200,\n" +
                "    \"message\":\"餐厅列表\",\n" +
                "    \"time\":\"2020-07-22 11:15:59\",\n" +
                "    \"data\":[\n" +
                "        {\"companyId\":1285772488172769280,\"companyName\":\"111\",\"departmentId\":0,\"userId\":0}\n" +
                "    ]\n" +
                "}";

    }


    @OnClick({R.id.edit_Phone, R.id.edit_Passwrord, R.id.btn_login, R.id.text_forget, R.id.text_register, R.id.img_close, R.id.img_open})
    public void onViewClicked(View view) {
        if (isFastClick()) {
            return;
        }
        switch (view.getId()) {

            case R.id.edit_Phone:
                break;
            case R.id.edit_Passwrord:
                break;
            case R.id.btn_login:
//                对登录条件进行判断
                if (TextUtils.isEmpty(editPhone.getText().toString())) {
                    ToastUtil.showToast(LoginActivity.this, "手机号不能为空");
                } else if (!Mobile.isMobile(editPhone.getText().toString())) {
                    ToastUtil.showToast(LoginActivity.this, "请输入正确手机号");
                } else if (TextUtils.isEmpty(editPasswrord.getText().toString())) {
                    ToastUtil.showToast(LoginActivity.this, "密码不能为空");
                } else {
//                    ToastUtil.showToast(LoginActivity.this, "登录");
                    getLogin();
                }

                break;
            case R.id.text_forget:
                startActivity(new Intent(LoginActivity.this, ForgetActivity.class));
                break;
            case R.id.text_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

                break;
            case R.id.img_open:
                //密码明文显示
                editPasswrord.setTransformationMethod(PasswordTransformationMethod.getInstance());

                imgClose.setVisibility(View.VISIBLE);
                imgOpen.setVisibility(View.GONE);
                editPasswrord.setSelection(editPasswrord.length());
                break;
            case R.id.img_close:
                //密码秘闻显示
                editPasswrord.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                imgClose.setVisibility(View.GONE);
                imgOpen.setVisibility(View.VISIBLE);
                editPasswrord.setSelection(editPasswrord.length());
                break;
        }
    }

    private static final int MIN_DELAY_TIME = 1000;  // 两次点击间隔不能少于1000ms
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }

    List<LoginBean.DataBean> loginBeanList = new ArrayList<>();

    public void getLogin() {
        if (companyId.isEmpty()) {
            companyId = "0";
        } else {
            {

            }
        }

        HttpResponse.getLogin(editPhone.getText().toString(), editPasswrord.getText().toString(), "2", "1", companyId, new StringCallback(LoginActivity.this) {
            @Override
            public void onSuccess(String s, Call call, Response response) {

                Log.d("login", s);
                BaseBean baseBean = GsonUtil.GsonToBean(s, BaseBean.class);
                if (baseBean.getCode() == 0) {
//                    startActivity(new Intent(LoginActivity.this, ReplaceFragmentActivity.class));

                    Intent intent = new Intent(LoginActivity.this, FilterLoginRestruantActivity.class);
                    intent.putExtra("logindata", s);
                    startActivityForResult(intent, resurtResurant);

                    SharePreferenceUtil.put(LoginActivity.this, "loginreturnstr", s);
//                    startActivity(new Intent(LoginActivity.this, FilterListActivity.class));


//                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, baseBean.getMessage() + "", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void onEventMainThread(Integer type) {


    }

    String token;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == resurtResurant) {

            companyId = data.getStringExtra("companyId");
            token = data.getStringExtra("token");
            SharePreferenceUtil.put(LoginActivity.this, "companyId", companyId);
            SharePreferenceUtil.put(LoginActivity.this, "token", token);
            startActivity(new Intent(LoginActivity.this, ZhenBanReplaceFragmentActivity.class));
            Log.d("loginactivity", token);
        }
    }
}




