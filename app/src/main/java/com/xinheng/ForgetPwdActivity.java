package com.xinheng;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.xinheng.base.BaseActivity;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.CodeUtils;
import com.xinheng.util.DensityUtils;
import com.xinheng.util.PatternUtils;

/**
 * 忘记密码界面
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
    /***
     * 本地生产的验证码图片
     */
    private ImageView mIvCode;
    /***
     * 生成的验证码字符串
     */
    private String mCode;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);//TODO
        initView();
        setListener();
        //进入Activity时生成验证码
        newCode();
    }

    private void setListener()
    {
        OnViewClickListenerImpl onViewClickListener = new OnViewClickListenerImpl();
        mBtnNext.setOnClickListener(onViewClickListener);
        mIvCode.setOnClickListener(onViewClickListener);
    }

    private void initView()
    {
        mEtCode = (EditText) findViewById(R.id.et_code);
        mEtMobile = (EditText) findViewById(R.id.et_mobile);
        mBtnNext = (Button) findViewById(R.id.btn_next);
        mIvCode = (ImageView) findViewById(R.id.iv_code);
    }

    @Override
    public void onGetDataSuccess(ResultItem resultItem,String requestTag)
    {
        if (null != resultItem)
        {
            showCroutonToast(resultItem.message);
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
                case R.id.iv_code://重新生成验证码
                    newCode();
                    break;
            }
        }
    }

    private void newCode()
    {
        int height = DensityUtils.dpToPx(mActivity, 36);
        int width = height * 2;
        int fontSize = DensityUtils.spToPx(mActivity, 18);
        mIvCode.setImageBitmap(CodeUtils.getInstance().createBitmap(width, height,fontSize));
        mCode = CodeUtils.getInstance().getCode();
        System.out.println("mCode = " + mCode);
    }

    /**
     * 下一步按钮的点击执行事件
     */
    private void next()
    {
        String code = mEtCode.getText().toString();
        String mobile = mEtMobile.getText().toString();
        if (TextUtils.isEmpty(mobile))
        {
            showCroutonToast("手机号码不可以为空");
            return;
        }
        if (TextUtils.getTrimmedLength(mobile) != 11)
        {
            showCroutonToast("手机号码长度应该为11位");
            return;
        }
        if (!PatternUtils.isPhoneNumber(mobile))
        {
            showCroutonToast("手机号码格式不正确");
            return;
        }
        if (TextUtils.isEmpty(code))
        {
            showCroutonToast("验证码不可以为空");
            return;
        }
        if (!code.equalsIgnoreCase(mCode)) //验证码不区分大小写
        {
            showCroutonToast("验证码不正确，请重新输入");
            return;
        }
        BindPhoneVerifyActivity.actionBindPhoneVerify(mActivity,mobile);
    }

    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_forget_pwd);
    }

}
