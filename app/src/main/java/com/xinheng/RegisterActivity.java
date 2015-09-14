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
import com.xinheng.mvp.presenter.impl.LoginPresenterImpl;
import com.xinheng.mvp.presenter.impl.RegisterPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.mvp.view.impl.LoginViewImpl;
import com.xinheng.slidingmenu.SlidingMenu;
import com.xinheng.util.PatternUtils;
import com.xinheng.util.VerifyCodeUtils;

/**
 * 用户注册界面
 */
public class RegisterActivity extends BaseActivity implements DataView
{
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
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
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
    public void onGetDataSuccess(ResultItem resultItem)
    {
        if (null != resultItem)
        {
            showCroutonToast(resultItem.message);
            if(resultItem.success())
            {
                LoginPresenter loginPresenter = new LoginPresenterImpl(mActivity, new LoginViewImpl(mActivity));
                loginPresenter.doLogin(mEtMobile.getText().toString(), mEtPwd.getText().toString());
            }
            else
            {
              //  LoginActivity.actionLogin(mActivity,mEtMobile.getText().toString(), mEtPwd.getText().toString());
            }
        }
    }

    @Override
    public void onGetDataFailured(String msg)
    {
        if (!TextUtils.isEmpty(msg))
        {
            showCroutonToast(msg);
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
        String pwd = mEtPwd.getText().toString();
        if (TextUtils.isEmpty(pwd))
        {
            showCroutonToast("密码不可以为空");
            return;
        }
        if (TextUtils.getTrimmedLength(pwd) < 6)
        {
            showCroutonToast("密码的长度最少为6位");
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
        showCroutonToast("正在获取验证码");
        VerifyCodeUtils.getVerifyCode(mActivity,mBtnCode,60*1000,mEtMobile.getText().toString());
    }

    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_register);
    }

}
