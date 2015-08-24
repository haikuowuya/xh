package com.xinheng;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.xinheng.base.BaseActivity;
import com.xinheng.mvp.presenter.LoginPresenter;
import com.xinheng.mvp.presenter.impl.LoginPresenterImpl;
import com.xinheng.slidingmenu.SlidingMenu;

/**
 * 用户登录界面
 */
public class LoginActivity extends BaseActivity

{
    public static void actionLogin(BaseActivity activity)
    {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

    /***
     * 立即注册
     */
    private Button mBtnRegister;
    /***
     * 忘记密码
     */
    private Button mBtnForgetPwd;

    /***
     *  登录按钮  
     */
    private Button mBtnLogin;
    /***
     * 微博登录按钮container
     */
    private LinearLayout mLinearWeiBoContainer;
    /**
     * 微信登录按钮container
     */
    private LinearLayout mLinearWeiXinContainer;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);//TODO
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        configTitleLayout();
        initView();
        setListener();
    }


    /**
     * 初始化View控件
     */
    private void initView()
    {
        mBtnForgetPwd = (Button) findViewById(R.id.btn_forget_pwd);
        mLinearWeiBoContainer = (LinearLayout) findViewById(R.id.linear_weibo_container);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mLinearWeiXinContainer = (LinearLayout) findViewById(R.id.linear_weixin_container);
        mBtnRegister = (Button) findViewById(R.id.btn_register);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(mBtnRegister.getText());
        UnderlineSpan span = new UnderlineSpan();
        spannableStringBuilder.setSpan(span, 0, spannableStringBuilder.length() - 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
    }

    /***
     * 按钮的点击事件
     */
    private void setListener()
    {
        OnViewClickListenerImpl listener = new OnViewClickListenerImpl();
        mBtnForgetPwd.setOnClickListener(listener);
        mBtnRegister.setOnClickListener(listener);
        mLinearWeiBoContainer.setOnClickListener(listener);
        mLinearWeiXinContainer.setOnClickListener(listener);
        mBtnLogin.setOnClickListener(listener);
    }

    private void configTitleLayout()
    {
        getTitleContainer().setVisibility(View.GONE);
        ((View) getContentViewGroup().getParent()).setPadding(0, 0, 0, 0);
    }

    private class OnViewClickListenerImpl implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.btn_register://立即注册
                    register();
                    break;
                //微博登录
                case R.id.linear_weibo_container:
                    weibo();
                    break;
                //微信登录
                case R.id.linear_weixin_container:
                    weixin();
                    break;
                case R.id.btn_forget_pwd:
                    forgetPwd();
                    break;
                case R.id.btn_login:
                    login();
                    break;
            }
        }

        /***
         * 登录
         */
        private void login()
        {
            LoginPresenter loginPresenter = new LoginPresenterImpl(mActivity);
            loginPresenter.doLogin("13915433160","110916");
        }

        /***
         * 立即注册
         */
        private void register()
        {
            RegisterActivity.actionRegister(mActivity);
        }

        /***
         * 微博登录
         */
        private void weibo()
        {
            showCroutonToast("微博登录");
        }

        /***
         * 微信登录
         */
        private void weixin()
        {
            showCroutonToast("微信登录");
        }

        /**
         * 忘记密码
         */
        private void forgetPwd()
        {
            showCroutonToast("忘记密码");
        }
    }

    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_login);
    }


}
