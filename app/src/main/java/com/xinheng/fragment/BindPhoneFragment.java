package com.xinheng.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinheng.R;
import com.xinheng.base.BaseFragment;
import com.xinheng.eventbus.OnBindPhoneModifyEvent;
import com.xinheng.mvp.model.LoginSuccessItem;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.presenter.BindPhoneAuthPhonePresenter;
import com.xinheng.mvp.presenter.SendCodePresenter;
import com.xinheng.mvp.presenter.impl.BindPhoneAuthPhonePresenterImpl;
import com.xinheng.mvp.presenter.impl.SendCodePresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.PatternUtils;
import com.xinheng.util.VerifyCodeUtils;

import de.greenrobot.event.EventBus;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：  绑定手机号码
 */
public class BindPhoneFragment extends BaseFragment      implements DataView
{

    /***
     * 获取验证码请求
     */
    public static final String REQUEST_CODE_TAG = "request_code";

    /***
     * 验证旧的手机号码
     */
    public static final String REQUEST_AUTH_OLD_PHONE = "request_auth_old_phone";
    /***
     * 验证新的的手机号码
     */
    public static final String REQUEST_AUTH_NEW_PHONE = "request_auth_new_phone";
    /***
     *修改密码
     */
    public static final String REQUEST_MODIFY_PWD = "request_modify_pwd";
    public static BindPhoneFragment newInstance()
    {
        BindPhoneFragment fragment = new BindPhoneFragment();
        return fragment;
    }

    /**
     * 代表当前正在操作的步骤 ,默认目前是第一步
     */
    private int mCurrentOPT = 1;
    /***
     * 第一步
     */
    private TextView mTv1;
    /***
     * 第二步
     */
    private TextView mTv2;
    /***
     * 第三步
     */
    private TextView mTv3;
    /**
     * 验证码输入框
     */
    private EditText mEtCode;
    private Button mBtnCode;
    /***
     * 下一步
     */
    private Button mBtnNext;

    /***
     * 之前绑定的手机号码
     */
    private TextView mTvOldBindPhone;
    /***
     * 第一步显示的View
     */
    private LinearLayout mLinear1;
    /***
     * 第二步显示的View
     */
    private LinearLayout mLinear2;
    /***
     * 第三步显示的View
     */
    private LinearLayout mLinear3;

    /***
     * 第二步新的手机号码
     */
    private EditText mEtNewPhone;
    /***
     * 第二步输入的新手机对应的验证码
     */
    private EditText mEtNewPhoneCode;
    /***
     * 获取新手机对应的验证码
     */
    private Button mBtnNewPhoneCode;

    /***
     *第三步提示绑定成功
     */
    private TextView mTvFinish;

    /***
     * 是否发送验证码成功
     */
    private boolean mIsCodeSuccess = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_bind_phone, null);      //TODO
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        mEtCode = (EditText) view.findViewById(R.id.et_code);
        mBtnCode = (Button) view.findViewById(R.id.btn_code);
        mBtnNext = (Button) view.findViewById(R.id.btn_next);
        mTv1 = (TextView) view.findViewById(R.id.tv_1);
        mTv2 = (TextView) view.findViewById(R.id.tv_2);
        mTv3 = (TextView) view.findViewById(R.id.tv_3);
        mTvOldBindPhone = (TextView) view.findViewById(R.id.tv_old_bind_phone);
        mLinear1 = (LinearLayout) view.findViewById(R.id.linear_1);
        mLinear2 = (LinearLayout) view.findViewById(R.id.linear_2);
        mEtNewPhone = (EditText) view.findViewById(R.id.et_new_phone);
        mEtNewPhoneCode = (EditText) view.findViewById(R.id.et_new_phone_code);
        mBtnNewPhoneCode = (Button) view.findViewById(R.id.btn_new_phone_code);
        mLinear3 = (LinearLayout) view.findViewById(R.id.linear_3);
        mTvFinish = (TextView) view.findViewById(R.id.tv_finish);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        setListener();
        mTvOldBindPhone.setText("已绑定手机号码：" + mActivity.getLoginSuccessItem().mobile);
    }

    private void setListener()
    {
        OnClickListenerImpl onClickListener = new OnClickListenerImpl();
        mBtnCode.setOnClickListener(onClickListener);
        mBtnNext.setOnClickListener(onClickListener);
        mBtnNewPhoneCode.setOnClickListener(onClickListener);

    }

    @Override
    public String getFragmentTitle()
    {
        return getString(R.string.tv_fragment_user_account);
    }

    @Override
    public void onGetDataSuccess(ResultItem resultItem, String requestTag)
    {
        if (null != resultItem)
        {
            mActivity.showToast(resultItem.message);
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
                } else if (REQUEST_AUTH_OLD_PHONE.equals(requestTag))
                {
                    mIsCodeSuccess = false;
                    mCurrentOPT = 2;
                    mLinear1.setVisibility(View.GONE);
                    mLinear2.setVisibility(View.VISIBLE);
                    mTv1.setTextColor(0xFF999999);
                    mTv2.setTextColor(0xFF333333);
                    mTv1.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_verify_1_disable, 0, 0);
                    mTv2.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_verify_2_enable, 0, 0);
                    mActivity.hideSoftKeyBorard(mEtCode);
                }
                else if(REQUEST_AUTH_NEW_PHONE.equals(requestTag))
                {
                    mCurrentOPT = 3;
                    mIsCodeSuccess = false;
                    mLinear1.setVisibility(View.GONE);
                    mLinear2.setVisibility(View.GONE);
                    mLinear3.setVisibility(View.VISIBLE);
                    mBtnNext.setText("完成");
                    mTv1.setTextColor(0xFF999999);
                    mTv2.setTextColor(0xFF999999);
                    mTv3.setTextColor(0xFF333333);
                    mTv1.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_verify_1_disable, 0, 0);
                    mTv2.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_verify_2_disable, 0, 0);
                    mTv3.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_verify_3_enable, 0, 0);
                    mActivity.hideSoftKeyBorard(mEtCode);
                    mTvFinish.setText("恭喜你，已修改绑定新的手机号码：" + mEtNewPhone.getText().toString());
                    EventBus.getDefault().post(new OnBindPhoneModifyEvent(mEtNewPhone.getText().toString()));
                    LoginSuccessItem loginSuccessItem = mActivity.getLoginSuccessItem();
                    loginSuccessItem.mobile = mEtNewPhone.getText().toString();
                    mActivity.saveLoginSuccessItem(loginSuccessItem);
                }
            }
        }
    }

    @Override
    public void onGetDataFailured(String msg, String requestTag)
    {

    }

    private class OnClickListenerImpl implements View.OnClickListener
    {
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.btn_code://获取验证码  ,之前绑定的手机号码
                    String phone = mActivity.getLoginSuccessItem().mobile;
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
                case R.id.btn_new_phone_code://获取新手机号码验证码
                    String newPhone = mEtNewPhone.getText().toString();
                    if (!mIsCodeSuccess)
                    {
                        if (TextUtils.isEmpty(newPhone))
                        {
                            mActivity.showCroutonToast("新手机号码不可以为空");
                            return;
                        }
                        if (newPhone.length() != 11)
                        {
                            mActivity.showCroutonToast("新手机号码长度应该为11位");
                            return;
                        }
                        if (!PatternUtils.isPhoneNumber(newPhone))
                        {
                            mActivity.showCroutonToast("新手机号码格式不正确");
                            return;
                        }
                        doGetVerifyCode(newPhone);
                    }
                    else
                    {
                        mActivity.showToast("验证码已经发送到你手机，请稍候再试");
                    }
                    break;
                case R.id.btn_next://下一步
                    if (mCurrentOPT == 1)         //第一步
                    {
                        verifyOldCode();
                    }
                    else if (mCurrentOPT == 2)//第二步
                    {
                        verifyNewCode();
                    }
                    else if (mCurrentOPT == 3)
                    {
                        mActivity.finish();
                    }
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

    /***
     * 校验之前绑定的手机号码的验证码
     */
    private void verifyOldCode()
    {
        String code = mEtCode.getText().toString();
        if (TextUtils.isEmpty(code))
        {
            mActivity.showCroutonToast("验证码不可以为空");
            return;
        }

        BindPhoneAuthPhonePresenter bindPhoneAuthPhonePresenter  = new BindPhoneAuthPhonePresenterImpl(mActivity,this, REQUEST_AUTH_OLD_PHONE);
        bindPhoneAuthPhonePresenter.doAuthOldPhone(mActivity.getLoginSuccessItem().mobile, code);
    }

    /***
     * 校验新的手机号码的验证码
     */
    private void verifyNewCode()
    {
        String code = mEtNewPhoneCode.getText().toString();
        if (TextUtils.isEmpty(code))
        {
            mActivity.showCroutonToast("验证码不可以为空");
            return;
        }
        BindPhoneAuthPhonePresenter bindPhoneAuthPhonePresenter  = new BindPhoneAuthPhonePresenterImpl(mActivity,this, REQUEST_AUTH_NEW_PHONE);
        bindPhoneAuthPhonePresenter.doAuthNewPhone(mActivity.getLoginSuccessItem().mobile,mEtNewPhone.getText().toString(), code);

    }


}