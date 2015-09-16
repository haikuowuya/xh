package com.xinheng;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xinheng.base.BaseActivity;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.PatternUtils;
import com.xinheng.util.VerifyCodeUtils;

/**
 * 绑定手机验证界面
 */
public class BindPhoneVerifyActivity extends BaseActivity implements DataView
{
    public static final String  TEST_CODE = "1234";
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


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_phone_verify);//TODO
        initView();
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
    public void onGetDataSuccess(ResultItem resultItem)
    {
        if (null != resultItem)
        {
            showCroutonToast(resultItem.message);
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
                case R.id.btn_next://下一步
                    next();
                    break;
                case R.id.btn_code://获取手机验证码
                   getVerifyCode();
                    break;
            }
        }
    }

    /***
     * 获取手机验证码
     */
    private void getVerifyCode()
    {
        VerifyCodeUtils.getVerifyCode(mActivity, mBtnCode,60*1000, mMobile);
        showCroutonToast("此时请求服务器获取验证码");
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
        if (!code.equalsIgnoreCase(TEST_CODE)) //验证码不区分大小写
        {
            showCroutonToast("验证码不正确，请重新输入,1234可以通过验证");
            return;
        }
        VerifyPersonActivity.actionVerifyPerson(mActivity);
    }

    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_bind_phone_verify);
    }

}
