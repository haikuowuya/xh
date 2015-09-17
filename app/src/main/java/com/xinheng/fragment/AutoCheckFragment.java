package com.xinheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xinheng.R;
import com.xinheng.base.BaseFragment;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.view.DataView;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：  按方抓药-添加药品Fragment页面
 */
public class AutoCheckFragment extends BaseFragment   implements DataView
{
    public static AutoCheckFragment newInstance()
    {
        AutoCheckFragment fragment = new AutoCheckFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_auto_check, null);
        initView(view);
        mIsInit = true;
        return view;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        OnClickListenerImpl onClickListener = new OnClickListenerImpl();

    }

    private void initView(View view)
    {

    }


    @Override
    public String getFragmentTitle()
    {
        return "我的订单";
    }
    @Override
    public void onGetDataSuccess(ResultItem resultItem)
    {
        if(resultItem != null)
        {
            mActivity.showCroutonToast(resultItem.message);


        }
    }

    @Override
    public void onGetDataFailured(String msg)
    {

    }

    private class OnClickListenerImpl implements View.OnClickListener
    {
        public void onClick(View v)
        {
            switch (v.getId())
            {

            }
        }
    }




}
