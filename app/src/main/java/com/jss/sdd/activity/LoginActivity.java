package com.jss.sdd.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jss.sdd.R;
import com.jss.sdd.http.DataRequest;
import com.jss.sdd.http.HttpRequest;
import com.jss.sdd.http.IRequestListener;
import com.jss.sdd.parse.LoginHandler;
import com.jss.sdd.utils.AESUtils;
import com.jss.sdd.utils.ConstantUtil;
import com.jss.sdd.utils.ToastUtil;
import com.jss.sdd.utils.Urls;
import com.jss.sdd.widget.ClearEditText;
import com.jss.sdd.widget.statusbar.StatusBarUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements IRequestListener
{
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.et_phone)
    ClearEditText etPhone;
    @BindView(R.id.iv_hide_pwd)
    ImageView ivHidePwd;
    @BindView(R.id.et_pwd)
    ClearEditText etPwd;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_forget)
    TextView tvForget;
    @BindView(R.id.tv_register)
    TextView tvRegister;

    private boolean isHidePwd;
    private static final String USER_LOGIN = "user_login";
    private static final int REQUEST_SUCCESS = 0x01;
    private static final int REQUEST_FAIL = 0x02;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case REQUEST_SUCCESS:
                    ToastUtil.show(LoginActivity.this, "登录成功");
                    break;


                case REQUEST_FAIL:
                    ToastUtil.show(LoginActivity.this, msg.obj.toString());
                    break;

            }
        }
    };

    @Override
    protected void initData()
    {

    }

    @Override
    protected void initViews(Bundle savedInstanceState)
    {
        StatusBarUtil.setTransparentStatusBar(this);
        setContentView(R.layout.activity_login);
        StatusBarUtil.setStatusBarBackground(this, R.color.main_bg);
        StatusBarUtil.StatusBarLightMode(LoginActivity.this, false);
    }

    @Override
    protected void initEvent()
    {

    }

    @Override
    protected void initViewData()
    {
        tvTitle.setText("用户登录");
    }


    @OnClick({R.id.iv_back, R.id.iv_hide_pwd, R.id.tv_login, R.id.tv_forget, R.id.tv_register})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_hide_pwd:

                if (isHidePwd)
                {
                    isHidePwd = false;
                    etPwd.setInputType(InputType.TYPE_CLASS_TEXT);
                    ivHidePwd.setImageResource(R.drawable.ic_show_pwd);
                }
                else
                {
                    isHidePwd = true;
                    etPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ivHidePwd.setImageResource(R.drawable.ic_hide_pwd);
                }
                break;
            case R.id.tv_login:

                String phone = etPhone.getText().toString();
                String pwd = etPwd.getText().toString();
                if (TextUtils.isEmpty(phone) || phone.length() < 11)
                {
                    ToastUtil.show(LoginActivity.this, "请输入11位手机号码！");
                    return;
                }


                if (TextUtils.isEmpty(pwd))
                {
                    ToastUtil.show(LoginActivity.this, "密码不能为空！");
                    return;
                }

                try
                {
                    showProgressDialog();
                    //   String encryptStr = "13921408272;iKWne" + System.currentTimeMillis();
                    String encryptStr = "13921408272;iKWne1547537752269";

                    Map<String, String> valuePairs = new HashMap<>();
                    valuePairs.put("origin", "2");
                    Gson gson = new Gson();
                    Map<String, String> postMap = new HashMap<>();
                    postMap.put("json", gson.toJson(valuePairs));
                    postMap.put("encryptID", AESUtils.Encrypt(encryptStr, AESUtils.KEY));
                    DataRequest.instance().request(LoginActivity.this, Urls.getLoginUrl(), this, HttpRequest.POST, USER_LOGIN, postMap, new
                            LoginHandler());
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                break;
            case R.id.tv_forget:
                break;
            case R.id.tv_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
        }
    }

    @Override
    public void notify(String action, String resultCode, String resultMsg, Object obj)
    {
        hideProgressDialog();
        if (USER_LOGIN.equals(action))
        {
            if (ConstantUtil.RESULT_SUCCESS.equals(resultCode))
            {
                mHandler.sendMessage(mHandler.obtainMessage(REQUEST_SUCCESS, obj));
            }
            else
            {
                mHandler.sendMessage(mHandler.obtainMessage(REQUEST_FAIL, resultMsg));
            }
        }
    }
}
