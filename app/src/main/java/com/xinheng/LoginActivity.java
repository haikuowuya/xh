package com.xinheng;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.xinheng.base.BaseActivity;
import com.xinheng.mvp.model.LoginSuccessItem;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.presenter.LoginPresenter;
import com.xinheng.mvp.presenter.impl.LoginPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.slidingmenu.SlidingMenu;
import com.xinheng.util.Constants;
import com.xinheng.util.GsonUtils;

/**
 * 用户登录界面
 */
public class LoginActivity extends BaseActivity implements DataView
{
    public static final String DEFAULT_USERNAME = "15850217017";
    public static final String DEFAULT_PWD = "111111";

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
     * 登录按钮
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

    /**
     * 用户名
     */
    private EditText mEtUsername;
    /**
     * 用户密码
     */
    private EditText mEtPwd;

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
        mEtUsername = (EditText) findViewById(R.id.et_username);
        mEtPwd = (EditText) findViewById(R.id.et_pwd);
        mLinearWeiBoContainer = (LinearLayout) findViewById(R.id.linear_weibo_container);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mLinearWeiXinContainer = (LinearLayout) findViewById(R.id.linear_weixin_container);
        mBtnRegister = (Button) findViewById(R.id.btn_register);
        mEtUsername.setText(DEFAULT_USERNAME);
        mEtPwd.setText(DEFAULT_PWD);
        mEtUsername.setSelection(mEtUsername.getText().length());
        mEtPwd.setSelection(mEtPwd.getText().length());
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

    @Override
    public void onGetDataSuccess(ResultItem resultItem)
    {
        if (null != resultItem)
        {
            // showCroutonToast(resultItem.message);
            showToast(resultItem.message);
            LoginSuccessItem loginSuccessItem = GsonUtils.jsonToClass(resultItem.properties.getAsJsonObject().toString(), LoginSuccessItem.class);
            System.out.println("loginSuccessItem = " + loginSuccessItem);
            if (null != loginSuccessItem)
            {
                mPreferences.edit().putString(Constants.PREF_LOGIN, GsonUtils.toJson(loginSuccessItem)).commit();
//                UserCenterActivity.actionUserCenter(mActivity);
               MainActivity.actioMain(mActivity);
            }

//           InterfaceActivity.actionInterface(mActivity, loginSuccessItem);
        }
    }

    @Override
    public void onGetDataFailured(String msg)
    {
        showCroutonToast(msg);
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
            String username = mEtUsername.getText().toString();
            String pwd = mEtPwd.getText().toString();
            if (TextUtils.isEmpty(username))
            {
                showCroutonToast("登录名不可以为空");
                return;
            }
            if (TextUtils.isEmpty(pwd))
            {
                showCroutonToast("密码不可以为空");
            }
            LoginPresenter loginPresenter = new LoginPresenterImpl(mActivity);
            loginPresenter.doLogin(username, pwd);
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
//            showCroutonToast("忘记密码");
            ForgetPwdActivity.actionForgetPwd(mActivity);
        }
    }

    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_login);
    }

}
