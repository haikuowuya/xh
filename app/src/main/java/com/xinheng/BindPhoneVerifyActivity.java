package com.xinheng;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xinheng.base.BaseActivity;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.presenter.ForgetPwdPresenter;
import com.xinheng.mvp.presenter.SendCodePresenter;
import com.xinheng.mvp.presenter.impl.ForgetPwdPresenterImpl;
import com.xinheng.mvp.presenter.impl.SendCodePresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.PatternUtils;
import com.xinheng.util.VerifyCodeUtils;

/**
 * 绑定手机验证界面
 */
public class BindPhoneVerifyActivity extends UserBaseActivity implements DataView
{

    /***
     * 获取验证码请求
     */
    public static final String REQUEST_CODE_TAG = "request_code";
    /***
     * 第二步验证手机号码
     */
    public static final String REQUEST_AUTH_PHONE_TAG="request_auth_phone";
    private static  final String EXTRA_MOBILE="mobile";
    public static void actionBindPhoneVerify(BaseActivity activity, String mobile )
    {
        Intent intent = new Intent(activity, BindPhoneVerifyActivity.class);
        intent.putExtra(EXTRA_MOBILE, mobile);
        activity.startActivity(intent);
    }

    /***
     * 顶部的提示语
     */
    private TextView mTvText;
    /***
     * 获取手机验证码
     */
    private Button mBtnCode;
    /***
     * 下一步
     */
    private Button mBtnNext;

    /**
     * 手机验证码
     */
    private EditText mEtCode;

    /***
     * 从上一个验证通过的页面获取到的手机号码
     */
    private String mMobile;


    /***
     * 是否发送验证码成功
     */
    private boolean mIsCodeSuccess = false;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_phone_verify);//TODO
        initView();
        setIvRightVisibility(View.GONE);
        setListener();
        mMobile = getIntent().getExtras().getString(EXTRA_MOBILE);
        String tmpMobile =  mMobile.substring(0, 3)+"****"+mMobile.substring(7);
        mTvText.setText(getString(R.string.tv_verify_mobile_hint, tmpMobile));
    }

    private void setListener()
    {
        OnViewClickListenerImpl onViewClickListener = new OnViewClickListenerImpl();
        mBtnCode.setOnClickListener(onViewClickListener);
        mBtnNext.setOnClickListener(onViewClickListener);
    }

    private void initView()
    {
        mEtCode = (EditText) findViewById(R.id.et_code);
        mBtnCode = (Button) findViewById(R.id.btn_code);
        mTvText = (TextView) findViewById(R.id.tv_text);
        mBtnNext = (Button) findViewById(R.id.btn_next);
    }

    @Override
    public void onGetDataSuccess(ResultItem resultItem,String requestTag)
    {
        if (null != resultItem)
        {
            showToast(resultItem.message);
            if (resultItem.success())
            {
                if (REQUEST_CODE_TAG.equals(requestTag))
                {
                    VerifyCodeUtils.cancle();
                    mBtnCode.setText("验证码发送成功");
                    mIsCodeSuccess = true;
                    mBtnCode.setClickable(false);
                    new Handler().postDelayed(
                            new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    mIsCodeSuccess = false;
                                }
                            }, 60 * 1000);
                }
                else if(REQUEST_AUTH_PHONE_TAG.equals(requestTag))
                {
                    VerifyPersonActivity.actionVerifyPerson(mActivity,mMobile);
                }
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
                case R.id.btn_next://下一步
                    next();
                    break;
                case R.id.btn_code://获取手机验证码
                    String phone = mMobile;
                    if (!mIsCodeSuccess)
                    {
                        if (TextUtils.isEmpty(phone))
                        {
                            mActivity.showToast("手机号码不可以为空");
                            return;
                        }
                        if (phone.length() != 11)
                        {
                            mActivity.showToast("手机号码长度应该为11位");
                            return;
                        }
                        if (!PatternUtils.isPhoneNumber(phone))
                        {
                            mActivity.showToast("手机号码格式不正确");
                            return;
                        }
                        doGetVerifyCode(phone);
                    } else
                    {
                        mActivity.showToast("验证码已经发送到你手机，请稍候再试");
                    }
                    break;
            }
        }
    }

    /***
     * 获取手机验证码
     */
    private void doGetVerifyCode(String phone)
    {
        VerifyCodeUtils.getVerifyCode(mActivity, mBtnCode, 60 * 1000, phone);
        SendCodePresenter sendCodePresenter = new SendCodePresenterImpl(mActivity, this, REQUEST_CODE_TAG);
        sendCodePresenter.doSendCode(phone);
    }


    /**
     * 下一步按钮的点击执行事件
     */
    private void next()
    {
        String code = mEtCode.getText().toString();
        if (TextUtils.isEmpty(mMobile))
        {
            showCroutonToast("手机号码不可以为空");
            return;
        }
        if (TextUtils.getTrimmedLength(mMobile) != 11)
        {
            showCroutonToast("手机号码长度应该为11位");
            return;
        }
        if (!PatternUtils.isPhoneNumber(mMobile))
        {
            showCroutonToast("手机号码格式不正确");
            return;
        }
        if (TextUtils.isEmpty(code))
        {
            showCroutonToast("验证码不可以为空");
            return;
        }
        ForgetPwdPresenter forgetPwdPresenter = new ForgetPwdPresenterImpl(mActivity, this, REQUEST_AUTH_PHONE_TAG);
        forgetPwdPresenter.doAuthPhone(mMobile, code);;

    }

    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_bind_phone_verify);
    }

}
