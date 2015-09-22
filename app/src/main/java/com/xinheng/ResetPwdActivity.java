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

/**
 * 重置密码界面
 */
public class ResetPwdActivity extends BaseActivity implements DataView
{

    public static void actionResetPwd(BaseActivity activity)
    {
        Intent intent = new Intent(activity, ResetPwdActivity.class);
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
        if (!pwd1.equals(pwd2))
        {
            showCroutonToast("两次输入的密码不一致");
            return;
        }
        showCroutonToast("此时请求服务器去重置密码");
    }

    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_reset_pwd);
    }

}
