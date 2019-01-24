package com.jss.sdd.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jss.sdd.R;
import com.jss.sdd.http.DataRequest;
import com.jss.sdd.http.HttpRequest;
import com.jss.sdd.http.IRequestListener;
import com.jss.sdd.parse.ResultHandler;
import com.jss.sdd.utils.AESUtils;
import com.jss.sdd.utils.ConstantUtil;
import com.jss.sdd.utils.ToastUtil;
import com.jss.sdd.utils.Urls;
import com.jss.sdd.widget.ClearEditText;
import com.jss.sdd.widget.statusbar.StatusBarUtil;
import com.tuo.customview.VerificationCodeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity implements IRequestListener
{
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_phone)
    ClearEditText etPhone;
    @BindView(R.id.btn_get_code)
    Button btnGetCode;
    @BindView(R.id.ll_register_one)
    LinearLayout llRegisterOne;
    @BindView(R.id.verificationCodeView)
    VerificationCodeView verificationCodeView;
    @BindView(R.id.btn_verification)
    Button btnVerification;
    @BindView(R.id.ll_register_two)
    LinearLayout llRegisterTwo;
    @BindView(R.id.et_nickName)
    ClearEditText etNickName;
    @BindView(R.id.iv_hide_pwd)
    ImageView ivHidePwd;
    @BindView(R.id.et_pwd)
    ClearEditText etPwd;
    @BindView(R.id.iv_hide_pwd1)
    ImageView ivHidePwd1;
    @BindView(R.id.et_pwd1)
    ClearEditText etPwd1;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.ll_register_three)
    LinearLayout llRegisterThree;
    @BindView(R.id.tv_agreement)
    TextView tvAgreement;

    private boolean isHidePwd, isHidePwd1;
    private int step;
    private int time = 0;
    private List<LinearLayout> mLinearLayoutList = new ArrayList<>();

    private static final String GET_CODE = "get_code";
    private static final String USER_REGISTER = "user_register";
    private static final int REGISTER_SUCCESS = 0x01;
    private static final int REQUEST_FAIL = 0x02;
    private static final int GET_CODE_SUCCESS = 0x03;
    private static final int UPDATE_TIME = 0x04;


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case REGISTER_SUCCESS:
                    ToastUtil.show(RegisterActivity.this, "注册成功");
                    break;


                case REQUEST_FAIL:
                    ToastUtil.show(RegisterActivity.this, msg.obj.toString());
                    break;

                case GET_CODE_SUCCESS:
                    time = 60;
                    mHandler.sendEmptyMessage(UPDATE_TIME);
                    setStep(1);

                    break;

                case UPDATE_TIME:

                    if (time == 0)
                    {
                        btnGetCode.setEnabled(true);
                        btnGetCode.setText("获取验证码");
                    }
                    else
                    {
                        time--;
                        mHandler.sendEmptyMessageDelayed(UPDATE_TIME, 1000);
                        btnGetCode.setEnabled(false);
                        btnGetCode.setText(time + "秒后重新发送");
                    }
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
        setContentView(R.layout.activity_register);
        StatusBarUtil.setStatusBarBackground(this, R.color.main_bg);
        StatusBarUtil.StatusBarLightMode(RegisterActivity.this, false);
    }

    @Override
    protected void initEvent()
    {
        etPhone.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if (TextUtils.isEmpty(s) || s.length() < 11 || time != 0)
                {
                    btnGetCode.setEnabled(false);
                }
                else
                {
                    btnGetCode.setEnabled(true);
                }
            }
        });

        verificationCodeView.setInputCompleteListener(new VerificationCodeView.InputCompleteListener()
        {
            @Override
            public void inputComplete()
            {
                String content = verificationCodeView.getInputContent();
                if (!TextUtils.isEmpty(content) && content.length() == 6)
                {
                    btnVerification.setEnabled(true);
                }
            }

            @Override
            public void deleteContent()
            {
                btnVerification.setEnabled(false);
            }
        });
    }

    @Override
    protected void initViewData()
    {
        tvTitle.setText("用户注册");
        mLinearLayoutList.add(llRegisterOne);
        mLinearLayoutList.add(llRegisterTwo);
        mLinearLayoutList.add(llRegisterThree);
        setStep(0);
    }


    private void setStep(int step)
    {
        this.step = step;
        for (int i = 0; i < mLinearLayoutList.size(); i++)
        {
            if (i == step)
            {
                mLinearLayoutList.get(step).setVisibility(View.VISIBLE);
            }
            else
            {
                mLinearLayoutList.get(i).setVisibility(View.GONE);
            }
        }
    }


    @Override
    public void notify(String action, String resultCode, String resultMsg, Object obj)
    {
        hideProgressDialog();
        if (action.equals(GET_CODE))
        {
            if (ConstantUtil.RESULT_SUCCESS.equals(resultCode))
            {
                mHandler.sendMessage(mHandler.obtainMessage(GET_CODE_SUCCESS, obj));
            }
            else
            {
                mHandler.sendMessage(mHandler.obtainMessage(REQUEST_FAIL, resultMsg));
            }
        }
        else if (action.equals(USER_REGISTER))
        {
            if (ConstantUtil.RESULT_SUCCESS.equals(resultCode))
            {
                mHandler.sendMessage(mHandler.obtainMessage(REGISTER_SUCCESS, obj));
            }
            else
            {
                mHandler.sendMessage(mHandler.obtainMessage(REQUEST_FAIL, resultMsg));
            }
        }

    }


    @OnClick({R.id.iv_back, R.id.btn_get_code, R.id.btn_verification, R.id.iv_hide_pwd, R.id.iv_hide_pwd1, R.id.btn_register, R.id.tv_agreement})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.iv_back:
                if (step == 0)
                {
                    finish();
                }
                else
                {
                    setStep(step - 1);
                }
                break;
            case R.id.btn_get_code:
                getCodeAction();
                break;
            case R.id.btn_verification:
                setStep(2);
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
            case R.id.iv_hide_pwd1:
                if (isHidePwd1)
                {
                    isHidePwd1 = false;
                    etPwd1.setInputType(InputType.TYPE_CLASS_TEXT);
                    ivHidePwd1.setImageResource(R.drawable.ic_show_pwd);
                }
                else
                {
                    isHidePwd1 = true;
                    etPwd1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ivHidePwd1.setImageResource(R.drawable.ic_hide_pwd);
                }
                break;
            case R.id.btn_register:


                break;
            case R.id.tv_agreement:
                startActivity(new Intent(RegisterActivity.this, WebViewActivity.class).putExtra(WebViewActivity.EXTRA_URL, "http://www.baidu.com"));
                break;
        }
    }


    //获取验证码
    private void getCodeAction()
    {
        showProgressDialog();
        try
        {
            showProgressDialog();
            String phone = etPhone.getText().toString();
            String encryptStr = phone + System.currentTimeMillis();

            Map<String, String> valuePairs = new HashMap<>();
            valuePairs.put("phone", phone);
            valuePairs.put("flag", "0");
            Gson gson = new Gson();
            Map<String, String> postMap = new HashMap<>();
            postMap.put("json", gson.toJson(valuePairs));
            postMap.put("encryptID", AESUtils.Encrypt(encryptStr, AESUtils.KEY));
            DataRequest.instance().request(RegisterActivity.this, Urls.getSendSmsUrl(), this, HttpRequest.POST, GET_CODE, postMap, new
                    ResultHandler());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    private void registerAction()
    {
        showProgressDialog();
        try
        {
            String phone = etPhone.getText().toString();
            String phoneAuthCode = verificationCodeView.getInputContent();
            String inviteCode = "JSSYYS";
            String password = etPwd.getText().toString();
            String cpassword = etPwd1.getText().toString();
            String origin = "2";
            String nickName = etNickName.getText().toString();
            String encryptStr = phone + System.currentTimeMillis();

            if (TextUtils.isEmpty(phone) || phone.length() < 11)
            {
                ToastUtil.show(RegisterActivity.this, "请输入正确的手机号码!");
                return;
            }
            if (TextUtils.isEmpty(phoneAuthCode) || phoneAuthCode.length() != 6)
            {
                ToastUtil.show(RegisterActivity.this, "请输入正确的验证码!");
                return;
            }

            if (TextUtils.isEmpty(nickName) || nickName.length() < 2)
            {
                ToastUtil.show(RegisterActivity.this, "请输入长度大于4位的昵称");
                return;
            }

            if (TextUtils.isEmpty(password) || password.length() < 6)
            {
                ToastUtil.show(RegisterActivity.this, "请输入长度大于6位的密码");
                return;
            }

            if (!password.equals(cpassword))
            {
                ToastUtil.show(RegisterActivity.this, "两次密码输入不一致");
                return;
            }

            Map<String, String> valuePairs = new HashMap<>();
            valuePairs.put("phone", phone);
            valuePairs.put("inviteCode", inviteCode);
            valuePairs.put("password", password);
            valuePairs.put("cpassword", cpassword);
            valuePairs.put("origin ", origin);
            valuePairs.put("phoneAuthCode ", phoneAuthCode);
            valuePairs.put("nickName ", nickName);
            Gson gson = new Gson();
            Map<String, String> postMap = new HashMap<>();
            postMap.put("json", gson.toJson(valuePairs));
            postMap.put("encryptID", AESUtils.Encrypt(encryptStr, AESUtils.KEY));
            DataRequest.instance().request(RegisterActivity.this, Urls.getRegisterUrl(), this, HttpRequest.POST, GET_CODE, postMap, new
                    ResultHandler());

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed()
    {
        if (step == 0)
        {
            super.onBackPressed();
        }
        else
        {
            setStep(step - 1);
        }
    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (mHandler.hasMessages(UPDATE_TIME))
        {
            mHandler.removeMessages(UPDATE_TIME);
        }
    }
}
