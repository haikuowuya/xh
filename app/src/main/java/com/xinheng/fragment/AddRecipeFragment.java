package com.xinheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.reflect.TypeToken;
import com.xinheng.R;
import com.xinheng.adapter.user.UserOrderListAdapter;
import com.xinheng.base.BaseFragment;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.user.UserOrderItem;
import com.xinheng.mvp.presenter.UserOrderPresenter;
import com.xinheng.mvp.presenter.impl.UserOrderPresenterImpl;
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
 * 作者：  libo
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：  添加处方
 */
public class AddRecipeFragment extends BaseFragment implements DataView
{
    public static AddRecipeFragment newInstance()
    {
        AddRecipeFragment fragment = new AddRecipeFragment();
        return fragment;
    }


    private PtrClassicFrameLayout mPtrClassicFrameLayout;
    private CustomListView mCustomListView;
    private LinkedList<UserOrderItem> mUserOrderItems = new LinkedList<>();
    private UserOrderListAdapter mUserOrderListAdapter;
    /**
     * 搜索按钮
     */
    private ImageView mIvSearch;
    /**
     * 搜索框
     */
    private EditText mEtSearch;

    /**
     * 订单的当前状态
     */
    private  String mOrderStatus = UserOrderItem.ORDER_STATUS_0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_add_recipe, null);
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
    }
    private void initView(View view)
    {
        mCustomListView = (CustomListView) view.findViewById(R.id.lv_custom_listview);
        mPtrClassicFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.ptr_scrollview_container);
        mIvSearch = (ImageView) view.findViewById(R.id.iv_search);
        mEtSearch = (EditText) view.findViewById(R.id.et_search);
    }

    @Override
    public String getFragmentTitle()
    {
        return "添加处方";
    }

    protected void doRefresh()
    {
        doGetData();
    }

    @Override
    protected void doGetData()
    {
        mActivity.showProgressDialog();
        UserOrderPresenter userOrderPresenter = new UserOrderPresenterImpl(mActivity, this);
        userOrderPresenter.doGetUserOrder(mOrderStatus);
    }

    @Override
    public void onGetDataSuccess(ResultItem resultItem)
    {
        mPtrClassicFrameLayout.refreshComplete();
        if (null != resultItem)
        {
            mActivity.showCroutonToast(resultItem.message);
            Type type = new TypeToken<List<UserOrderItem>>()
            {
            }.getType();
            List<UserOrderItem> userOrderItems = GsonUtils.jsonToResultItemToList(GsonUtils.toJson(resultItem), type);
            //  List<UserOrderItem>   userOrderItems = GsonUtils.jsonToResultItemToList( DATA, type);
            if (null != userOrderItems)
            {
                mUserOrderItems.addAll(userOrderItems);
                if (null == mUserOrderListAdapter)
                {
                    mUserOrderListAdapter = new UserOrderListAdapter(mActivity, mUserOrderItems);
                    mCustomListView.setAdapter(mUserOrderListAdapter);
                }
                else
                {
                    mUserOrderListAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void onGetDataFailured(String msg)
    {

    }
}
