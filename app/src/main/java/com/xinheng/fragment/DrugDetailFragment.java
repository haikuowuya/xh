package com.xinheng.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xinheng.APIURL;
import com.xinheng.R;
import com.xinheng.base.BaseFragment;
import com.xinheng.common.AbsImageLoadingListener;
import com.xinheng.mvp.model.DrugDetailItem;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.presenter.DrugDetailPresenter;
import com.xinheng.mvp.presenter.ShoppingCartPresenter;
import com.xinheng.mvp.presenter.impl.DrugDetailPresenterImpl;
import com.xinheng.mvp.presenter.impl.ShoppingCartPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：药品详情
 */
public class DrugDetailFragment extends BaseFragment implements DataView
{
    public static final String REQUEST_GET_DRUG_DETAIL_TAG = "request_get_drug_detail";
    public static final String REQUEST_ADD_TO_SHOPPING_CART_TAG = "request_add_to_shopping_cart";
    public static final String ARG_DRUG_ID = "drug_id";

    public static DrugDetailFragment newInstance(String drugId)
    {
        DrugDetailFragment fragment = new DrugDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_DRUG_ID, drugId);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String mDrugId;

    private ScrollView mScrollView;
    /***
     * 药品名称
     */
    private TextView mTvDrugName;
    /***
     * 生产厂家
     */
    private TextView mTvDrugProducer;
    /**
     * 单位
     */
    private TextView mTvDrugUnit;
    /**
     * 规格
     */
    private TextView mTvDrugSpecs;
    /**
     * 价格
     */
    private TextView mTvDrugPrice;
    /***
     * 生产地址
     */
    private TextView mTvDrugPlace;

    private ImageView mImageView;

    /**
     * 药品编号
     */
    private TextView mTvDrugNo;

    private TextView mTvDrugNature;

    private TextView mTvShoppingCart;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_drug_detail, null); //TODO 布局文件
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        mScrollView = (ScrollView) view.findViewById(R.id.sv_scrollview);
        mTvDrugName = (TextView) view.findViewById(R.id.tv_drug_name);
        mTvDrugProducer = (TextView) view.findViewById(R.id.tv_drug_producer);
        mTvDrugPrice = (TextView) view.findViewById(R.id.tv_drug_price);
        mTvDrugUnit = (TextView) view.findViewById(R.id.tv_drug_unit);
        mTvDrugPlace = (TextView) view.findViewById(R.id.tv_drug_place);
        mTvDrugSpecs = (TextView) view.findViewById(R.id.tv_drug_specs);
        mTvDrugNature = (TextView) view.findViewById(R.id.tv_drug_nature);
        mTvDrugNo = (TextView) view.findViewById(R.id.tv_drug_no);
        mImageView = (ImageView) view.findViewById(R.id.iv_image);
        mTvShoppingCart = (TextView) view.findViewById(R.id.tv_shopping_cart);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mDrugId = getArguments().getString(ARG_DRUG_ID);
        doGetData();
        setListener();
    }

    private void setListener()
    {
        OnClickListenerImpl onClickListener = new OnClickListenerImpl();
        mTvShoppingCart.setOnClickListener(onClickListener);
    }

    @Override
    protected void doGetData()
    {
        DrugDetailPresenter drugDetailPresenter = new DrugDetailPresenterImpl(mActivity, this, REQUEST_GET_DRUG_DETAIL_TAG);
        drugDetailPresenter.doGetDrugDetail(mDrugId);
        mScrollView.setVisibility(View.GONE);
        mTvShoppingCart.setVisibility(View.GONE);
    }

    @Override
    public String getFragmentTitle()
    {
        return getString(R.string.tv_fragment_user_account);
    }

    @Override
    public void onGetDataSuccess(ResultItem resultItem, String requestTag)
    {
        if (null != resultItem)
        {
            mActivity.showToast(resultItem.message);
            if (resultItem.success())
            {
                if (REQUEST_GET_DRUG_DETAIL_TAG.equals(requestTag))
                {
                    DrugDetailItem drugDetailItem = GsonUtils.jsonToClass(resultItem.properties.getAsJsonObject().toString(), DrugDetailItem.class);
                    if (null != drugDetailItem)
                    {
                        showDrugDetailItem(drugDetailItem);
                    }
                } else if (REQUEST_ADD_TO_SHOPPING_CART_TAG.equals(requestTag))
                {

                }
            }
        }
    }

    private void showDrugDetailItem(DrugDetailItem drugDetailItem)
    {
        mScrollView.setVisibility(View.VISIBLE);
        if (null != drugDetailItem)
        {
            mTvShoppingCart.setVisibility(View.VISIBLE);
            mTvDrugNo.setText("药品编号：" + drugDetailItem.drugCode);
            mTvDrugName.setText("药品名称：" + drugDetailItem.name);
            mTvDrugPlace.setText("药品产地：" + drugDetailItem.place);
            mTvDrugProducer.setText("生产厂家：" + drugDetailItem.producer);
            mTvDrugSpecs.setText("药品规格：" + drugDetailItem.specs);
            mTvDrugPrice.setText("药品单价：" + drugDetailItem.price);
            mTvDrugUnit.setText("药品单位：" + drugDetailItem.unit);
            mTvDrugNature.setText("药品特性：" + drugDetailItem.name);
            String imageUrl = drugDetailItem.img;
            if (!imageUrl.startsWith(APIURL.BASE_API_URL))
            {
                imageUrl = APIURL.BASE_API_URL + imageUrl;
            }
            ImageLoader.getInstance().loadImage(
                    imageUrl, new AbsImageLoadingListener()
                    {
                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
                        {
                            mImageView.setImageBitmap(loadedImage);
                        }
                    });
        }
    }

    @Override
    public void onGetDataFailured(String msg, String requestTag)
    {
        mActivity.showToast(msg);
    }

    private class OnClickListenerImpl implements View.OnClickListener
    {
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.tv_shopping_cart://
//                    mActivity.showToast("加入购物车");
                    ShoppingCartPresenter shoppingCartPresenter = new ShoppingCartPresenterImpl(mActivity, DrugDetailFragment.this, REQUEST_ADD_TO_SHOPPING_CART_TAG);
                    shoppingCartPresenter.doAddToShoppingCart(mDrugId, "1");
                    break;
            }
        }
    }

}
