package com.xinheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.xinheng.R;
import com.xinheng.base.BaseFragment;
import com.xinheng.eventbus.OnModifyAccountNameEvent;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.presenter.UserAccountNamePresenter;
import com.xinheng.mvp.presenter.impl.UserAccountNamePresenterImpl;
import com.xinheng.mvp.view.DataView;

import de.greenrobot.event.EventBus;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明： 修改昵称Fragment界面
 */
public class UserAccountNameFragment extends BaseFragment implements DataView
{
    public static UserAccountNameFragment newInstance()
    {
        UserAccountNameFragment fragment = new UserAccountNameFragment();
        return fragment;
    }

    private EditText mEtName;
    private Button mBtnSubmit;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_user_account_name, null);      //TODO
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        mEtName = (EditText) view.findViewById(R.id.et_name);
        mBtnSubmit = (Button) view.findViewById(R.id.btn_submit);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        if (null != mActivity.getLoginSuccessItem() && null != mActivity.getLoginSuccessItem().account)
        {

            mEtName.setText(mActivity.getLoginSuccessItem().account.account);
            mEtName.setSelection(mEtName.getText().length());
        }
        setListener();
    }

    private void setListener()
    {
        OnClickListenerImpl onClickListener = new OnClickListenerImpl();
        mBtnSubmit.setOnClickListener(onClickListener);
    }

    @Override
    public String getFragmentTitle()
    {
        return getString(R.string.tv_fragment_user_account);
    }

    @Override
    public void onGetDataSuccess(ResultItem resultItem, String requestTag)
    {
        if (resultItem != null)
        {
            mActivity.showToast(resultItem.message);
            if (resultItem.success())
            {
                EventBus.getDefault().post(new OnModifyAccountNameEvent(mEtName.getText().toString()));
                mActivity.finish();
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
                case R.id.btn_submit://提交
                    submit();
                    break;
            }
        }
    }

    private void submit()
    {
        String name = mEtName.getText().toString();
        if (TextUtils.isEmpty(name))
        {
            mActivity.showToast("新的昵称不可以为空");
            return;
        }
        if (name.length() < 4 || name.length() > 20)
        {
            mActivity.showToast("昵称必须4~20位数字、字母或下划线");
            return;
        }

        UserAccountNamePresenter userAccountNamePresenter = new UserAccountNamePresenterImpl(mActivity,this);
        userAccountNamePresenter.doModifyUserAccountName(name);
        mActivity.hideSoftKeyBorard(mEtName);
    }

}