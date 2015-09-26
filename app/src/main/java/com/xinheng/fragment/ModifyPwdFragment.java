package com.xinheng.fragment;

import android.os.Bundle;
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
import com.xinheng.util.Constants;
import com.xinheng.util.VerifyCodeUtils;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：  修改密码界面
 */
public class ModifyPwdFragment extends BaseFragment
{
    public static final String MODIFY = "修改";
    public static final String HAS_BIND_PHONE_TEXT = "已绑定手机号码：";

    public static ModifyPwdFragment newInstance()
    {
        ModifyPwdFragment fragment = new ModifyPwdFragment();
        return fragment;
    }

    /***
     * 第一步
     */
    private TextView mTv1;
    /***
     * 第二步
     */
    private TextView mTv2;

    /**
     * 获取验证码
     */
    private Button mBtnCode;
    /***
     * 验证码输入框
     */
    private EditText mEtCode;

    /**
     * 下一步
     */
    private Button mBtnNext;

    /***
     * 第一步显示的View
     */
    private LinearLayout mLinear1;
    /***
     * 第二步显示的View
     */
    private LinearLayout mLinear2;

    private EditText mEtPwd1;
    private EditText mEtPwd2;

    /***
     * 已经绑定手机号码的提示
     */
    private TextView mTvBindPhoneHint;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_modify_pwd, null);      //TODO
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        mTv1 = (TextView) view.findViewById(R.id.tv_1);
        mTv2 = (TextView) view.findViewById(R.id.tv_2);

        mTvBindPhoneHint = (TextView) view.findViewById(R.id.tv_bind_phone_hint);
        mBtnCode = (Button) view.findViewById(R.id.btn_code);
        mEtCode = (EditText) view.findViewById(R.id.et_code);
        mBtnNext = (Button) view.findViewById(R.id.btn_next);
        mLinear1 = (LinearLayout) view.findViewById(R.id.linear_1);
        mLinear2 = (LinearLayout) view.findViewById(R.id.linear_2);
        mEtPwd1 = (EditText) view.findViewById(R.id.et_pwd1);
        mEtPwd2 = (EditText) view.findViewById(R.id.et_pwd2);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        setListener();
        mTvBindPhoneHint.setText(HAS_BIND_PHONE_TEXT + mActivity.getLoginSuccessItem().mobile);
    }

    private void setListener()
    {
        OnClickListenerImpl onClickListener = new OnClickListenerImpl();
        mBtnCode.setOnClickListener(onClickListener);
        mBtnNext.setOnClickListener(onClickListener);

    }

    @Override
    public String getFragmentTitle()
    {
        return getString(R.string.tv_fragment_user_account);
    }

    private class OnClickListenerImpl implements View.OnClickListener
    {
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.btn_code://
                    getVerifyCode( mActivity.getLoginSuccessItem().mobile);
                    break;
                case R.id.btn_next://下一步
                    if (MODIFY.equals(mBtnNext.getText().toString()))
                    {
                        modifyPwd();
                    }
                    else
                    {
                        next();
                    }
            }
        }

    }

    private void modifyPwd()
    {
        String pwd1 = mEtPwd1.getText().toString();
        if (TextUtils.isEmpty(pwd1))
        {
            mActivity.showCroutonToast("新密码不可以为空");
            return;
        }
        String pwd2 = mEtPwd2.getText().toString();
        if (!pwd1.equals(pwd2))
        {
            mActivity.showCroutonToast("两次输入的密码不一致");
            return;
        }
        mActivity.hideSoftKeyBorard(mEtPwd2);
    }

    private void next()
    {
        if (Constants.ALL_OK_CODE.equals(mEtCode.getText().toString()))
        {
            mBtnNext.setText(MODIFY);
            mLinear1.setVisibility(View.GONE);
            mLinear2.setVisibility(View.VISIBLE);
            mTv1.setTextColor(0xFF999999);
            mTv2.setTextColor(0xFF333333);
            mTv1.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_verify_1_disable, 0, 0);
            mTv2.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_verify_2_enable, 0, 0);
            mActivity.hideSoftKeyBorard(mEtCode);
        }
        else
        {
            mActivity.showCroutonToast("验证码不正确");
        }
    }

    /***
     * 获取手机验证码
     */
    private void getVerifyCode(String phone )
    {
        VerifyCodeUtils.getVerifyCode(mActivity, mBtnCode, 60 * 1000, phone);
        mActivity.showCroutonToast("此时请求服务器获取验证码");
    }
}