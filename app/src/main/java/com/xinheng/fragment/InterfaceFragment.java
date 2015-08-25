package com.xinheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.xinheng.APIURL;
import com.xinheng.InterfaceActivity;
import com.xinheng.R;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.LoginSuccessItem;
import com.xinheng.mvp.model.PostListItem;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

import java.io.Serializable;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：  接口测试列表
 */
public class InterfaceFragment extends PTRListFragment implements DataView
{
    private LoginSuccessItem mLoginSuccessItem;

    public static InterfaceFragment newInstance(Serializable serializable)
    {
        InterfaceFragment fragment = new InterfaceFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(InterfaceActivity.EXTRA_LOGIN_SUCCESS_ITEM, serializable);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
       mLoginSuccessItem = getArguments().getSerializable(InterfaceActivity.EXTRA_LOGIN_SUCCESS_ITEM) ==null?null: (LoginSuccessItem) getArguments().getSerializable(InterfaceActivity.EXTRA_LOGIN_SUCCESS_ITEM);
        getListView().setAdapter(ArrayAdapter.createFromResource(mActivity, R.array.array_interface, android.R.layout.simple_list_item_activated_1));
        if (null != mLoginSuccessItem)
        {
            getListView().setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    String text = parent.getAdapter().getItem(position).toString();
                    if (getString(R.string.tv_activity_user_order).equals(text))
                    {
                        PostListItem postListItem = new PostListItem();
                        postListItem.userId = mLoginSuccessItem.id;
                        postListItem.page = "-1";
                        String mingPostBody =GsonUtils.toJson(postListItem);
                       // System.out.println("明文POST 数据 = " +mingPostBody );
                        String postBody = RSAUtil.clientEncrypt(mingPostBody);
                        System.out.println("加密POST 数据 = " +postBody );
                        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, APIURL.USER_ORDER_LIST, postBody, mLoginSuccessItem, InterfaceFragment.this);
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
    public void onGetDataSuccess(ResultItem resultItem)
    {
        refreshComplete();
        if(null != resultItem)
        {
            mActivity.showCroutonToast(resultItem.message);
        }
    }

    @Override
    public void onGetDataFailured(String msg)
    {
        refreshComplete();
    }
}
