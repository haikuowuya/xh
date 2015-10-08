package com.xinheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.xinheng.R;
import com.xinheng.mvp.model.LoginSuccessItem;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.view.DataView;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：  接口测试列表
 */
public class InterfaceFragment extends PTRListFragment implements DataView
{
    private LoginSuccessItem mLoginSuccessItem;

    public static InterfaceFragment newInstance()
    {
        InterfaceFragment fragment = new InterfaceFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mLoginSuccessItem = mActivity.getLoginSuccessItem();
        getListView().setAdapter(ArrayAdapter.createFromResource(mActivity, R.array.array_interface, android.R.layout.simple_list_item_activated_1));
        if (null != mLoginSuccessItem)
        {
            getListView().setOnItemClickListener(
                    new AdapterView.OnItemClickListener()
                    {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                        {
                            String text = parent.getAdapter().getItem(position).toString();
                            if (getString(R.string.tv_activity_user_order).equals(text))
                            {
                            }
                            if (position == 1)//session失效
                            {
                                LoginSuccessItem loginSuccessItem = mActivity.getLoginSuccessItem();
                                loginSuccessItem.sessionId = "session_" + System.currentTimeMillis();
                                mActivity.saveLoginSuccessItem(loginSuccessItem);
                            }
                        }
                    });
        }
    }

    @Override
    public String getFragmentTitle()
    {
        return getString(R.string.tv_activity_interface);
    }

    protected void doRefresh()
    {
        doGetData();
    }

    @Override
    protected void doGetData()
    {

    }

    @Override
    public void onGetDataSuccess(ResultItem resultItem, String requestTag)
    {
        refreshComplete();
        if (null != resultItem)
        {
            mActivity.showCroutonToast(resultItem.message);
        }
    }

    @Override
    public void onGetDataFailured(String msg, String requestTag)
    {
        refreshComplete();
    }
}
