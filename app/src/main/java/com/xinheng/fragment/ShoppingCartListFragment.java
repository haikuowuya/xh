package com.xinheng.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.gson.reflect.TypeToken;
import com.xinheng.R;
import com.xinheng.adapter.shopping.ShoppingCartListAdapter;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.ShoppingCartItem;
import com.xinheng.mvp.model.user.UserCounselItem;
import com.xinheng.mvp.presenter.ShoppingCartPresenter;
import com.xinheng.mvp.presenter.impl.ShoppingCartPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.DensityUtils;
import com.xinheng.util.GsonUtils;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：  购物车列表
 */
public class ShoppingCartListFragment extends PTRListFragment implements DataView
{
    private static final String DATA = UserCounselItem.DEBUG_SUCCESS;

    public static ShoppingCartListFragment newInstance()
    {
        ShoppingCartListFragment fragment = new ShoppingCartListFragment();
        return fragment;
    }

    private LinkedList<ShoppingCartItem> mShoppingCartItems = new LinkedList<>();
    private ShoppingCartListAdapter mShoppingCartListAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        getListView().setDividerHeight(0);
        getListView().setBackgroundColor(0xFFF0F0F0);
        int leftRight = DensityUtils.dpToPx(mActivity, 10.f);
        getListView().setDivider(new ColorDrawable(0x00000000));

    }

    @Override
    public String getFragmentTitle()
    {
        return getString(R.string.tv_fragment_ptr_list);
    }

    @Override
    protected void doRefresh()
    {
        doGetData();
    }

    @Override
    protected void doGetData()
    {
        ShoppingCartPresenter shoppingCartPresenter = new ShoppingCartPresenterImpl(mActivity, this);
        shoppingCartPresenter.doGetShoppingCartList();
    }

    @Override
    public void onGetDataSuccess(ResultItem resultItem, String requestTag)
    {
        refreshComplete();
        if (null != resultItem)
        {
            mActivity.showToast(resultItem.message);
            if (resultItem.success())
            {
                Type type = new TypeToken<List<ShoppingCartItem>>()
                {
                }.getType();
                List<ShoppingCartItem> items = GsonUtils.jsonToResultItemToList(GsonUtils.toJson(resultItem), type);
                if (null != items)
                {
                    mShoppingCartItems.clear();
                    mShoppingCartItems.addAll(items);
                    if (null == mShoppingCartListAdapter)
                    {
                        mShoppingCartListAdapter = new ShoppingCartListAdapter(mActivity, mShoppingCartItems);
                        getListView().setAdapter(mShoppingCartListAdapter);
                    } else
                    {
                        mShoppingCartListAdapter.notifyDataSetChanged();
                    }
                }

            }
        }

    }

    @Override
    public void onGetDataFailured(String msg, String requestTag)
    {

    }
}
