package com.xinheng.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.google.gson.reflect.TypeToken;
import com.xinheng.R;
import com.xinheng.adapter.user.UserOrderListAdapter;
import com.xinheng.base.BaseFragment;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.UserOrderItem;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.view.CustomListView;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：  我的订单列表
 */
public class UserOrderFragment extends BaseFragment implements DataView
{
    private static final String DATA = UserOrderItem.DEBUG_SUCCESS;

    public static UserOrderFragment newInstance()
    {
        UserOrderFragment fragment = new UserOrderFragment();
        return fragment;
    }
    private PtrClassicFrameLayout mPtrClassicFrameLayout;
    private CustomListView mCustomListView;
    private LinkedList<UserOrderItem> mUserOrderItems = new LinkedList<>();
    private UserOrderListAdapter mUserOrderListAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_user_order, null);
        initView(view);
        mIsInit = true;
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mPtrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler()
        {
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout)
            {
                doRefresh();
            }
        });
        mCustomListView.setAdapter(ArrayAdapter.createFromResource(mActivity, R.array.array_menu, android.R.layout.simple_list_item_activated_1));
        mCustomListView.setSelector(new ColorDrawable(0x00000000));
        mCustomListView.setDividerHeight(4);
        mCustomListView.setDivider(new ColorDrawable(0x00000000));
    }
    private void initView(View view)
    {
        mCustomListView = (CustomListView) view.findViewById(R.id.lv_custom_listview);
        mPtrClassicFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.ptr_scrollview_container);
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
        mActivity.showProgressDialog();
        RequestUtils.getDataFromUrl(mActivity, "http://www.baidu.com", this);
    }

    @Override
    public void onGetDataSuccess(ResultItem resultItem)
    {
      mPtrClassicFrameLayout.refreshComplete();
        Type type = new TypeToken<List<UserOrderItem>>()
        {
        }.getType();
        List<UserOrderItem>   userOrderItems = GsonUtils.jsonToResultItemToList( DATA, type);
        if(null != userOrderItems)
        {
            mUserOrderItems.addAll(userOrderItems);
            if (null == mUserOrderListAdapter)
            {
                mUserOrderListAdapter = new UserOrderListAdapter(mActivity, mUserOrderItems);
                mCustomListView.setAdapter(mUserOrderListAdapter);
            } else
            {
                mUserOrderListAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onGetDataFailured(String msg)
    {

    }
}
