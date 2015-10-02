package com.xinheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.xinheng.R;
import com.xinheng.base.BaseFragment;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.view.DataView;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明： 症状自测-自述导航页面
 */
public class AutoCheckFragment2 extends BaseFragment   implements DataView
{
    public static AutoCheckFragment2 newInstance()
    {
        AutoCheckFragment2 fragment = new AutoCheckFragment2();
        return fragment;
    }

    /***
     * 症状描述
     */
    private EditText mEtDiseaseDesc;
    /***
     * 提交
     */
    private Button mBtnSubmit;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_auto_check2, null);
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
        mEtDiseaseDesc = (EditText) view.findViewById(R.id.et_disease_desc);
        mBtnSubmit = (Button) view.findViewById(R.id.btn_submit);

    }


    @Override
    public String getFragmentTitle()
    {
        return "我的订单";
    }
    @Override
    public void onGetDataSuccess(ResultItem resultItem,String requestTag)
    {
        if(resultItem != null)
        {
            mActivity.showCroutonToast(resultItem.message);


        }
    }

    @Override
    public void onGetDataFailured(String msg,String requestTag)
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
