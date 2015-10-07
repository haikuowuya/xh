package com.xinheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.xinheng.R;
import com.xinheng.base.BaseFragment;
import com.xinheng.eventbus.OnSelectPayDespatchEvent;
import com.xinheng.mvp.model.prescription.PostPayDespatchItem;

import de.greenrobot.event.EventBus;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明： 支付配送选择Fragment
 */
public class PayDespatchFragment extends BaseFragment
{

    public static PayDespatchFragment newInstance()
    {
        PayDespatchFragment fragment = new PayDespatchFragment();
        return fragment;
    }

    /**
     * 在线支付
     */
    private Button mBtnPayOnLine;
    /***
     * 货到付款
     */
    private Button mBtnPayOffLine;
    /**
     * 余额扣款
     */
    private Button mBtnPayAccount;
    /**
     * 上门自提
     */
    private Button mBtnDespatchSelf;
    /***
     * 普通快递
     */
    private Button mBtnDespatchNormal;
    private PostPayDespatchItem mPostPayDespatchItem = new PostPayDespatchItem();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_pay_dispatch, null);  //TODO
        initView(view);
        mIsInit = true;
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        OnClickListenerImpl onClickListener = new OnClickListenerImpl();
        mBtnPayOffLine.setOnClickListener(onClickListener);
        mBtnDespatchSelf.setOnClickListener(onClickListener);
        mBtnPayOnLine.setOnClickListener(onClickListener);
        mBtnPayAccount.setOnClickListener(onClickListener);
        mBtnDespatchNormal.setOnClickListener(onClickListener);

    }

    private void initView(View view)
    {
        mBtnDespatchNormal = (Button) view.findViewById(R.id.btn_despatch_normal);
        mBtnDespatchSelf = (Button) view.findViewById(R.id.btn_despatch_self);
        mBtnPayOffLine = (Button) view.findViewById(R.id.btn_pay_offline);
        mBtnPayAccount = (Button) view.findViewById(R.id.btn_pay_account);
        mBtnPayOnLine = (Button) view.findViewById(R.id.btn_pay_online);
        mBtnPayOnLine.setActivated(true);
        mBtnPayOnLine.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_pay_despatch_select, 0, 0, 0);
        mBtnDespatchNormal.setActivated(true);
        mBtnDespatchNormal.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_pay_despatch_select, 0, 0, 0);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        EventBus.getDefault().post(new OnSelectPayDespatchEvent(mPostPayDespatchItem));
    }

    @Override
    public String getFragmentTitle()
    {
        return "我的订单";
    }

    protected void doRefresh()
    {
        doGetData();
    }

    @Override
    protected void doGetData()
    {

    }

    private class OnClickListenerImpl implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.btn_pay_online:
                    mBtnPayOnLine.setActivated(true);
                    mBtnPayOnLine.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_pay_despatch_select, 0, 0, 0);
                    mBtnPayOffLine.setActivated(false);
                    mBtnPayAccount.setActivated(false);
                    mBtnPayOffLine.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    mBtnPayAccount.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    mPostPayDespatchItem.payType = PostPayDespatchItem.PAY_ONLINE;
                    break;

                case R.id.btn_pay_account:
                    mBtnPayAccount.setActivated(true);
                    mBtnPayAccount.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_pay_despatch_select, 0, 0, 0);
                    mBtnPayOffLine.setActivated(false);
                    mBtnPayOnLine.setActivated(false);
                    mBtnPayOffLine.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    mBtnPayOnLine.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    mPostPayDespatchItem.payType = PostPayDespatchItem.PAY_ACCOUNT;
                    break;
                case R.id.btn_pay_offline:
                    mBtnPayOffLine.setActivated(true);
                    mBtnPayOffLine.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_pay_despatch_select, 0, 0, 0);
                    mBtnPayOnLine.setActivated(false);
                    mBtnPayAccount.setActivated(false);
                    mBtnPayAccount.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    mBtnPayOnLine.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    mPostPayDespatchItem.payType = PostPayDespatchItem.PAY_OFFLINE;
                    break;
                case R.id.btn_despatch_normal:
                    mBtnDespatchNormal.setActivated(true);
                    mBtnDespatchNormal.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_pay_despatch_select, 0, 0, 0);
                    mBtnDespatchSelf.setActivated(false);
                    mBtnDespatchSelf.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    mPostPayDespatchItem.despatchType = PostPayDespatchItem.DESPATCH_NORMAL;
                    break;
                case R.id.btn_despatch_self:
                    mBtnDespatchSelf.setActivated(true);
                    mBtnDespatchSelf.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_pay_despatch_select, 0, 0, 0);
                    mBtnDespatchNormal.setActivated(false);
                    mBtnDespatchNormal.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    mPostPayDespatchItem.despatchType = PostPayDespatchItem.DESPATCH_SELF;
                    break;
            }
        }
    }

}
