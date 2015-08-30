package com.xinheng;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xinheng.base.BaseActivity;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.PatternUtils;

/**
 *  忘记密码界面
 */
public class ForgetPwdActivity extends BaseActivity implements DataView
{
    public static void actionForgetPwd(BaseActivity activity)
    {
        Intent intent = new Intent(activity, ForgetPwdActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 手机号码
     */
    private EditText mEtMobile;
    /**
     * 手机验证码
     */
    private EditText mEtCode;
    /**
     * 下一步按钮
     */
    private Button mBtnNext;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);//TODO

        initView();
        setListener();
    }

    private void setListener()
    {
        OnViewClickListenerImpl onViewClickListener = new OnViewClickListenerImpl();
        mBtnNext.setOnClickListener(onViewClickListener);
    }

    private void initView()
    {
        mEtCode = (EditText) findViewById(R.id.et_code);
        mEtMobile = (EditText) findViewById(R.id.et_mobile);
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
                case R.id.btn_code://获取验证码
                    code();
                    break;
                case R.id.btn_next://下一步
                    next();
                    break;
            }
        }
    }

    /**
     * 下一步按钮的点击执行事件
     */
    private void next()
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
        String code = mEtCode.getText().toString();
    }

    private void code()
    {

    }

    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_forget_pwd);
    }

}
