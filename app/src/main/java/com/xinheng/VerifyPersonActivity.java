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
 * 验证常用就诊人界面
 */
public class VerifyPersonActivity extends UserBaseActivity implements DataView
{

    private static  final String EXTRA_MOBILE="mobile";

    public static void actionVerifyPerson(BaseActivity activity,String mobile )
    {
        Intent intent = new Intent(activity, VerifyPersonActivity.class);
        intent.putExtra(EXTRA_MOBILE, mobile);
        activity.startActivity(intent);
    }


    /***
     * 下一步
     */
    private Button mBtnNext;

    /**
     * 常用就诊人姓名
     */
    private EditText mEtName;

    /***
     * 从上一个验证通过的页面获取到的手机号码
     */
    private String mMobile;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_person);//TODO
        initView();
        setIvRightVisibility(View.GONE);
        setListener();
        mMobile = getIntent().getExtras().getString(EXTRA_MOBILE);
    }

    private void setListener()
    {
        OnViewClickListenerImpl onViewClickListener = new OnViewClickListenerImpl();
        mBtnNext.setOnClickListener(onViewClickListener);
    }

    private void initView()
    {
        mEtName = (EditText) findViewById(R.id.et_name);
        mBtnNext = (Button) findViewById(R.id.btn_next);
    }

    @Override
    public void onGetDataSuccess(ResultItem resultItem,String requestTag)
    {
        if (null != resultItem)
        {
            showToast(resultItem.message);
            if(resultItem.success())
            {
                ResetPwdActivity.actionResetPwd(mActivity,mMobile);
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
            }
        }
    }


    /**
     * 下一步按钮的点击执行事件
     */
    private void next()
    {
        String name = mEtName.getText().toString();
        if (TextUtils.isEmpty(name))
        {
            showCroutonToast("常用就诊人姓名不可以为空");
            return;
        }
        ForgetPwdPresenter forgetPwdPresenter = new ForgetPwdPresenterImpl(mActivity, this);
        forgetPwdPresenter.doAuthPatient(mMobile, name);

    }

    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_verify_person);
    }

}
