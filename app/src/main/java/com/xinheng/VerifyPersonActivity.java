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
 * 验证就诊人界面
 */
public class VerifyPersonActivity extends BaseActivity implements DataView
{
    public static final String  TEST_NAME= "123456";

    public static void actionVerifyPerson(BaseActivity activity)
    {
        Intent intent = new Intent(activity, VerifyPersonActivity.class);
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
        setListener();
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
        if (!name.equalsIgnoreCase(TEST_NAME))
        {
            showCroutonToast("常用就诊人姓名不正确，123456可以通过验证");
            return;
        }
        showCroutonToast("此时请求服务器去验证常用就诊人姓名是否存在");
        ResetPwdActivity.actionResetPwd(mActivity);
    }

    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_bind_phone_verify);
    }

}
