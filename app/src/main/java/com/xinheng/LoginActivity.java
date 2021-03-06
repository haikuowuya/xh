package com.xinheng;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.xinheng.base.BaseActivity;
import com.xinheng.mvp.model.LoginItem;
import com.xinheng.mvp.model.LoginSuccessItem;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.presenter.AutoLoginPresenter;
import com.xinheng.mvp.presenter.LoginPresenter;
import com.xinheng.mvp.presenter.impl.AutoLoginPresenterImpl;
import com.xinheng.mvp.presenter.impl.LoginPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.AndroidUtils;
import com.xinheng.util.Constants;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.MD5;
import com.xinheng.util.RSAUtil;

/**
 * 用户登录界面
 */
public class LoginActivity extends BaseActivity implements DataView
{
    public static final String DEFAULT_USERNAME = "15850217017";
    public static final String DEFAULT_USERNAME_ME = "18625273625";
    public static final String DEFAULT_PWD = "111111";
    public static final String EXTRA_USERNAME = "username";
    public static final String EXTRA_PWD = "pwd";

    /**
     * 跳转到用户登录界面
     *
     * @param activity ：当前跳转的Activity页面
     */
    public static void actionLogin(BaseActivity activity)
    {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 跳转到用户登录界面， 带有用户名和用户密码
     *
     * @param activity ：当前跳转的Activity页面
     * @param username ：用户名
     * @param pwd      ：用户密码
     */
    public static void actionLogin(BaseActivity activity, String username, String pwd)
    {
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.putExtra(EXTRA_USERNAME, username);
        intent.putExtra(EXTRA_PWD, pwd);
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
        configTitleLayout();
        initView();
        setListener();
        initUsernamePwd();
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
        mEtUsername.setSelection(mEtUsername.getText().length());
        mEtPwd.setSelection(mEtPwd.getText().length());
    }

    /***
     * 初始化用户名和用户密码
     * 如果通过Intent传递过来则使用Intent传递的用户名和密码，
     * 否则如果是测试阶段 {@link LoginActivity#DEFAULT_USERNAME}、{@link LoginActivity#DEFAULT_PWD} 不为空
     */
    private void initUsernamePwd()
    {
        String userName = DEFAULT_USERNAME;
        if (Constants.IMEI.equals(AndroidUtils.getIMEI(mActivity)) &&mPreferences.getBoolean(Constants.PREF_IS_ME,true))
        {
            userName = DEFAULT_USERNAME_ME;
        }
        String pwd = DEFAULT_PWD;
        if (!TextUtils.isEmpty(getIntent().getStringExtra(EXTRA_USERNAME)) && !TextUtils.isEmpty(getIntent().getStringExtra(EXTRA_PWD)))
        {
            userName = getIntent().getStringExtra(EXTRA_USERNAME);
            pwd = getIntent().getStringExtra(EXTRA_PWD);
        }
        mEtUsername.setText(userName);
        mEtPwd.setText(pwd);
        mEtUsername.setSelection(userName.length());
        mEtPwd.setSelection(pwd.length());
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if(mActivity.getPreferences().getBoolean(Constants.PREF_IS_AUTO_LOGIN,false) && mActivity.getLoginSuccessItem() !=null &&!TextUtils.isEmpty(mActivity.getPreferences().getString(Constants.PREF_RSA_USERNAME_PWD, null)))
        {
            AutoLoginPresenter autoLoginPresenter = new AutoLoginPresenterImpl(mActivity);
            autoLoginPresenter.doAutoLogin();
        }
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
    public void onGetDataSuccess(ResultItem resultItem, String requestTag)
    {
        if (null != resultItem)
        {
            mActivity.showToast(resultItem.message);
            if (resultItem.success())
            {
                LoginSuccessItem loginSuccessItem = GsonUtils.jsonToClass(resultItem.properties.getAsJsonObject().toString(), LoginSuccessItem.class);
                //   System.out.println("loginSuccessItem = " + loginSuccessItem);
                if (null != loginSuccessItem)
                {
                    //登录成功后将加密后的用户名密码rsa字符串保存下来，用于session过期的自动登录
                    String pwd = new MD5().getMD5_32(mEtPwd.getText().toString());
                    LoginItem loginItem = new LoginItem(mEtUsername.getText().toString(), pwd);
                    final String jsonLoginItem = GsonUtils.toJson(loginItem);
                    //加密的字符串
                    final String encryptUsernamePwd = RSAUtil.clientEncrypt(jsonLoginItem);
                    mPreferences.edit().putString(Constants.PREF_RSA_USERNAME_PWD, encryptUsernamePwd).commit();
                    //登录成功后将加密后的用户名密码rsa字符串保存下来，用于session过期的自动登录

                    //保存登录信息
                    mActivity.saveLoginSuccessItem(loginSuccessItem);
                    MainActivity.actioMain(mActivity);
                }
            }
        }
    }

    @Override
    public void onGetDataFailured(String msg, String requestTag)
    {
        mActivity.showToast(msg);
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
            LoginPresenter loginPresenter = new LoginPresenterImpl(mActivity, LoginActivity.this);
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
