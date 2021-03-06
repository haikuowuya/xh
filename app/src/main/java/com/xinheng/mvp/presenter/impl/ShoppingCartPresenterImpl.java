package com.xinheng.mvp.presenter.impl;

import android.text.TextUtils;

import com.xinheng.APIURL;
import com.xinheng.base.BaseActivity;
import com.xinheng.http.RequestUtils;
import com.xinheng.mvp.model.PostItem;
import com.xinheng.mvp.model.prescription.PostPayDespatchItem;
import com.xinheng.mvp.presenter.ShoppingCartPresenter;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

import java.util.List;

/**
 * 购物车列表接口接口的实现类
 */
public class ShoppingCartPresenterImpl implements ShoppingCartPresenter
{
    private BaseActivity mActivity;
    private DataView mDataView;

    private String mRequestTag;

    public ShoppingCartPresenterImpl(BaseActivity activity, DataView dataView)
    {
        mActivity = activity;
        mDataView = dataView;
    }

    public ShoppingCartPresenterImpl(BaseActivity activity, DataView dataView, String requestTag)
    {
        mActivity = activity;
        mRequestTag = requestTag;
        mDataView = dataView;
    }

    @Override
    public void doGetShoppingCartList()
    {
        String userCounselUrl = APIURL.GET_SHOPPING_CART_LIST_URL;
        PostItem postItem = new PostItem();
        postItem.userId = mActivity.getLoginSuccessItem().id;
        String mingPostBody = GsonUtils.toJson(postItem);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        System.out.println("postBody = " + postBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, userCounselUrl, postBody, mActivity.getLoginSuccessItem(), mDataView, mRequestTag);
    }

    @Override
    public void doAddToShoppingCart(String drugId, String count)
    {
        String userCounselUrl = APIURL.ADD_TO_SHOPPING_CART_URL;
        PostAddShoppingCartItem postItem = new PostAddShoppingCartItem();
        postItem.userId = mActivity.getLoginSuccessItem().id;
        postItem.drugId = drugId;
        if (TextUtils.isEmpty(count))
        {
            count = "1";
        }
        postItem.count = count;
        String mingPostBody = GsonUtils.toJson(postItem);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        System.out.println("postBody = " + postBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, userCounselUrl, postBody, mActivity.getLoginSuccessItem(), mDataView, mRequestTag);
    }

    @Override
    public void doEditSaveShoppingCart(List<String> drugIds, List<String> counts)
    {
        String userCounselUrl = APIURL.EDIT_SAVE_SHOPPING_CART_URL;
        PostUpdateShoppingCartItem postItem = new PostUpdateShoppingCartItem();
        postItem.userId = mActivity.getLoginSuccessItem().id;
        postItem.drugIds = drugIds;
        postItem.counts = counts;
        String mingPostBody = GsonUtils.toJson(postItem);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        System.out.println("postBody = " + postBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, userCounselUrl, postBody, mActivity.getLoginSuccessItem(), mDataView, mRequestTag);

    }

    @Override
    public void subimtShoppingCart(PostPayDespatchItem PostPayDespatchItem)
    {
        String userMedicalUrl = APIURL.SUBMIT_SHOPPING_CART_URL;
        PostPayDespatchItem.userId = mActivity.getLoginSuccessItem().id;
        String mingPostBody = GsonUtils.toJson(PostPayDespatchItem);
        System.out.println("mingPostBody = " + mingPostBody);
        String postBody = RSAUtil.clientEncrypt(mingPostBody);
        System.out.println("postBody = " + postBody);
        RequestUtils.getDataFromUrlByPostWithLoginInfo(mActivity, userMedicalUrl, postBody, mActivity.getLoginSuccessItem(), mDataView, mRequestTag);

    }

    public static class PostAddShoppingCartItem extends PostItem
    {
        public String count;
        public String drugId;
    }

    public static class PostUpdateShoppingCartItem extends PostItem
    {
        public List<String> counts;
        public List<String> drugIds;
    }
}
