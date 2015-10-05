package com.xinheng;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xinheng.base.BaseActivity;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.presenter.LoginPresenter;
import com.xinheng.mvp.presenter.RegisterPresenter;
import com.xinheng.mvp.presenter.SendCodePresenter;
import com.xinheng.mvp.presenter.impl.LoginPresenterImpl;
import com.xinheng.mvp.presenter.impl.RegisterPresenterImpl;
import com.xinheng.mvp.presenter.impl.SendCodePresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.mvp.view.impl.LoginViewImpl;
import com.xinheng.util.PatternUtils;
import com.xinheng.util.VerifyCodeUtils;

/**
 * 用户注册界面
 */
public class RegisterActivity extends BaseActivity implements DataView
{
    /***
     * 请求获取验证码的TAG
     */
    public static final String  REQUEST_CODE_TAG="request_code";
    /***
     * 请求注册的TAG
     */
    public static final String  REQUEST_REGISTER_TAG="request_register";
    /**
     * 跳转到用户注册界面
     * @param activity:跳转时所在的Activity
     */
    public static void actionRegister(BaseActivity activity)
    {
        Intent intent = new Intent(activity, RegisterActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 手机号码
     */
    private EditText mEtMobile;
    /**
     * 手机密码
     */
    private EditText mEtPwd;
    /**
     * 手机验证码
     */
    private EditText mEtCode;
    /**
     * 获取手机验证码按钮
     */
    private Button mBtnCode;
    /**
     * 注册按钮
     */
    private Button mBtnRegister;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);//TODO
        configTitleLayout();
        initView();
        setListener();
    }

    /***
     * 设置View的点击事件
     */
    private void setListener()
    {
        OnViewClickListenerImpl onViewClickListener = new OnViewClickListenerImpl();
        mBtnCode.setOnClickListener(onViewClickListener);
        mBtnRegister.setOnClickListener(onViewClickListener);
    }

    /**
     * 初始化View
     */
    private void initView()
    {
        mEtCode = (EditText) findViewById(R.id.et_code);
        mEtMobile = (EditText) findViewById(R.id.et_mobile);
        mEtPwd = (EditText) findViewById(R.id.et_pwd);
        mBtnCode = (Button) findViewById(R.id.btn_code);
        mBtnRegister = (Button) findViewById(R.id.btn_register);
    }

    private void configTitleLayout()
    {
        getTitleContainer().setVisibility(View.GONE);
        ((View) getContentViewGroup().getParent()).setPadding(0, 0, 0, 0);
    }

    @Override
    public void onGetDataSuccess(ResultItem resultItem,String requestTag)
    {
        if (null != resultItem)
        {
            showToast(resultItem.message);

            if(resultItem.success())
            {
                if(REQUEST_REGISTER_TAG.equals(requestTag))
                {
                    LoginPresenter loginPresenter = new LoginPresenterImpl(mActivity, new LoginViewImpl(mActivity));
                    loginPresenter.doLogin(mEtMobile.getText().toString(), mEtPwd.getText().toString());
                }
                else if(REQUEST_CODE_TAG.equals(requestTag))
                {
                    VerifyCodeUtils.cancle();
                    mBtnCode.setText("获取验证码成功");
                }
            }

        }
    }

    @Override
    public void onGetDataFailured(String msg,String requestTag)
    {
        if (!TextUtils.isEmpty(msg))
        {
            showToast(msg);
        }
    }

    private class OnViewClickListenerImpl implements View.OnClickListener
    {

        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.btn_code://获取验证码
                    code();
                    break;
                case R.id.btn_register://注册
                    register();
                    break;
            }
        }
    }

    /**
     * 注册按钮的点击执行事件
     */
    private void register()
    {
        String mobile = mEtMobile.getText().toString();
        if (TextUtils.isEmpty(mobile))
        {
            showToast("手机号码不可以为空");
            return;
        }
        if (mobile.length() != 11)
        {
            showToast("手机号码应该为11位数字");
            return;
        }
        if (!PatternUtils.isPhoneNumber(mobile))
        {
            showToast("手机号码格式不正确");
            return;
        }
        String pwd = mEtPwd.getText().toString();
        if (TextUtils.isEmpty(pwd))
        {
            showToast("密码不可以为空");
            return;
        }
        if (TextUtils.getTrimmedLength(pwd) < 6)
        {
            showToast("密码的长度最少为6位");
            return;
        }
        String code = mEtCode.getText().toString();
        RegisterPresenter registerPresenter = new RegisterPresenterImpl(mActivity, this);
        registerPresenter.doRegister(mobile, pwd, code);
    }

    private void code()
    {
        String mobile = mEtMobile.getText().toString();
        if (TextUtils.isEmpty(mobile))
        {
            showCroutonToast("手机号码不可以为空");
            return;
        }
        if (mobile.length() != 11)
        {
            showCroutonToast("手机号码应该为11位数字");
            return;
        }
        if (!PatternUtils.isPhoneNumber(mobile))
        {
            showCroutonToast("手机号码格式不正确");
            return;
        }

        VerifyCodeUtils.getVerifyCode(mActivity,mBtnCode,60*1000,mEtMobile.getText().toString());
        SendCodePresenter sendCodePresenter = new SendCodePresenterImpl(mActivity, this,REQUEST_CODE_TAG);
        sendCodePresenter.doSendCode(mobile);
    }

    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_register);
    }

}
