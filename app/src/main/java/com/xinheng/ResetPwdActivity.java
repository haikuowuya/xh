package com.xinheng;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xinheng.base.BaseActivity;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.presenter.ForgetPwdPresenter;
import com.xinheng.mvp.presenter.impl.ForgetPwdPresenterImpl;
import com.xinheng.mvp.view.DataView;

/**
 * 重置密码界面
 */
public class ResetPwdActivity extends BaseActivity implements DataView
{
    private static  final String EXTRA_MOBILE="mobile";
    public static void actionResetPwd(BaseActivity activity ,String mobile)
    {
        Intent intent = new Intent(activity, ResetPwdActivity.class);
        intent.putExtra(EXTRA_MOBILE, mobile);
        activity.startActivity(intent);
    }


    /***
     * 修改密码按钮
     */
    private Button mBtnSubmit;

    /**
     * 密码输入框1
     */
    private EditText mEtPwd1;
    /**
     * 密码输入框2
     */
    private EditText mEtPwd2;

    /***
     * 从上一个验证通过的页面获取到的手机号码
     */
    private String mMobile;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd);//TODO
        initView();
        setListener();
        setIvRightVisibility(View.GONE);
        mMobile = getIntent().getExtras().getString(EXTRA_MOBILE);
    }

    private void setListener()
    {
        OnViewClickListenerImpl onViewClickListener = new OnViewClickListenerImpl();
        mBtnSubmit.setOnClickListener(onViewClickListener);
    }

    private void initView()
    {
        mEtPwd1 = (EditText) findViewById(R.id.et_pwd1);
        mEtPwd2 = (EditText) findViewById(R.id.et_pwd2);
        mBtnSubmit = (Button) findViewById(R.id.btn_submit);
    }

    @Override
    public void onGetDataSuccess(ResultItem resultItem,String requestTag)
    {
        if (null != resultItem)
        {
            showToast(resultItem.message);
            if(resultItem.success())
            {
                LoginActivity.actionLogin(mActivity,mActivity.getLoginSuccessItem().mobile, mEtPwd1.getText().toString());
            }
        }
    }

    @Override
    public void onGetDataFailured(String msg,String requestTag)
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
                case R.id.btn_submit://修改提交
                    submit();
                    break;
            }
        }
    }


    /**
     *  修改密码按钮的点击执行事件
     */
    private void submit()
    {
       String pwd1 = mEtPwd1.getText().toString();
        String pwd2  = mEtPwd2.getText().toString();
        if (TextUtils.isEmpty(pwd1))
        {
            showCroutonToast("新密码不可以为空");
            return;
        }
        if(pwd1.length() < 6||pwd1.length() >32)
        {
            showToast("密码长度为6-32位");
            return;
        }
        if (!pwd1.equals(pwd2))
        {
            showCroutonToast("两次输入的密码不一致");
            return;
        }
        ForgetPwdPresenter forgetPwdPresenter = new ForgetPwdPresenterImpl(mActivity, this);
        forgetPwdPresenter.doResetPwd(mMobile, pwd1);
    }

    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_reset_pwd);
    }

}
