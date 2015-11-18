package com.xinheng.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xinheng.APIURL;
import com.xinheng.AddressListActivity;
import com.xinheng.PayDespatchActivity;
import com.xinheng.R;
import com.xinheng.base.BaseFragment;
import com.xinheng.common.AbsImageLoadingListener;
import com.xinheng.eventbus.OnOrderStatusChangedEvent;
import com.xinheng.eventbus.OnSelectAddressEvent;
import com.xinheng.eventbus.OnSelectPayDespatchEvent;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.address.AddressItem;
import com.xinheng.mvp.model.order.OrderDetailItem;
import com.xinheng.mvp.model.prescription.PostPayDespatchItem;
import com.xinheng.mvp.model.user.UserOrderItem;
import com.xinheng.mvp.presenter.AddressPresenter;
import com.xinheng.mvp.presenter.PayOrderPresenter;
import com.xinheng.mvp.presenter.SubmitOrderPresenter;
import com.xinheng.mvp.presenter.UserOrderDetailPresenter;
import com.xinheng.mvp.presenter.impl.AddressPresenterImpl;
import com.xinheng.mvp.presenter.impl.PayOrderPresenterImpl;
import com.xinheng.mvp.presenter.impl.SubmitOrderPresenterImpl;
import com.xinheng.mvp.presenter.impl.UserOrderDetailPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.DateFormatUtils;
import com.xinheng.util.DensityUtils;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.OrderDetailPopupWindowUtils;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：我的订单详情
 */
public class UserOrderDetailFragment extends BaseFragment implements DataView
{
    public static final String ARG_ID = "id";
    public static final String REQUEST_ADDRESS_TAG = "request_address";
    public static final String REQUEST_GET_ORDER_DETAIL_TAG = "request_get_order_detail";
    public static final String REQUEST_CONFIRM_ORDER_TAG = "request_confirm_order";
    public static final String REQUEST_PAY_ORDER_TAG = "request_pay_order";

    public static UserOrderDetailFragment newInstance(String orderId)
    {
        UserOrderDetailFragment fragment = new UserOrderDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_ID, orderId);
        fragment.setArguments(bundle);
        return fragment;
    }

    /***
     * 订单id
     */
    private String mUserOrderId;

    /**
     * 费用说明展示
     */
    private TextView mTvFee;

    /**
     * 处方添加的药品
     */
    private LinearLayout mLinearDrugContainer;

    /***
     * 确认按钮旁边的费用信息
     */
    private TextView mTvConfirmFee;

    private Button mBtnOk;

    /***
     * 配送方式以及支付方式
     */
    private TextView mTvDespatchWay;
    /**
     * 配送时间
     */
    private TextView mTvDespatchTime;

    /***
     * 收获人姓名
     */
    private TextView mTvName;
    /***
     * 收货地址
     */
    private TextView mTvAddress;
    /***
     * 收获人号码
     */
    private TextView mTvPhone;
    /**
     * 收货布局
     */
    private LinearLayout mLinearAddressContainer;
    /***
     * 支付配送布局
     */
    private LinearLayout mLinearPayDespatchContainer;

    /***
     * 最后点击确认要提交的post实体
     */
    private PostPayDespatchItem mPostPayDespatchItem = new PostPayDespatchItem();

    /***
     * 用于订单详情的显示，订单的总费用
     */
    private String mFee;

    private LinearLayout mLinearOrderSuccess;
    private LinearLayout mLinearSubmitOrderContainer;
    private ScrollView mScrollView;


    private OrderDetailItem mOrderDetailItem;



    @Override
    public View onCreateView(LayoutInflater inflater,   ViewGroup container,   Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_user_order_detail, null); //TODO 布局文件
        initView(view);
        mIsInit = true;
        return view;
    }

    private void initView(View view)
    {
        mScrollView = (ScrollView) view.findViewById(R.id.sv_scrollview);
        mTvFee = (TextView) view.findViewById(R.id.tv_fee);
        mTvDespatchTime = (TextView) view.findViewById(R.id.tv_despatch_time);
        mTvDespatchWay = (TextView) view.findViewById(R.id.tv_despatch_way);
        mTvAddress = (TextView) view.findViewById(R.id.tv_address);
        mTvName = (TextView) view.findViewById(R.id.tv_name);
        mTvPhone = (TextView) view.findViewById(R.id.tv_phone);
        mBtnOk = (Button) view.findViewById(R.id.btn_ok);
        mTvConfirmFee = (TextView) view.findViewById(R.id.tv_confirm_fee);
        mLinearOrderSuccess = (LinearLayout) view.findViewById(R.id.linear_order_success);
        mLinearSubmitOrderContainer = (LinearLayout) view.findViewById(R.id.linear_submit_order_container);
        mLinearAddressContainer = (LinearLayout) view.findViewById(R.id.linear_address_container);
        mLinearPayDespatchContainer = (LinearLayout) view.findViewById(R.id.linear_pay_despatch_container);
        mLinearDrugContainer = (LinearLayout) view.findViewById(R.id.linear_drug_container);
        mLinearDrugContainer.setVisibility(View.GONE);
        mLinearOrderSuccess.setVisibility(View.GONE);
        mScrollView.setVisibility(View.GONE);
    }

    @Override
    public void onActivityCreated(  Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mUserOrderId = getArguments().getString(ARG_ID);
        EventBus.getDefault().register(this);

        //TODO 得到订单id
        mPostPayDespatchItem.id = mUserOrderId;
        mTvDespatchTime.setText(DateFormatUtils.format(System.currentTimeMillis() + "", true, false));
        //mTvDespatchWay.setText("在线支付" + "\n" + "普通快递");
        mTvDespatchWay.setText(PostPayDespatchItem.PAY_ACCOUNT_TEXT + "\n" + PostPayDespatchItem.DESPATCH_NORMA_TEXT);
        setListener();
    }
    //===============================EVENT BUS========================
    //选择收货地址事件
    @Subscribe
    public void onEventMainThread(OnSelectAddressEvent event)
    {
        if (null != event && null != event.mAddressItem)
        {
            fillAddressItemToView(event.mAddressItem);
        }
    }

    //选择支付配送方式事件
    @Subscribe
    public void onEventMainThread(OnSelectPayDespatchEvent event)
    {
        if (null != event && null != event.mPostPayDespatchItem)
        {
            //TODO 得到支付配送方式
            mPostPayDespatchItem.payType = event.mPostPayDespatchItem.payType;
            mPostPayDespatchItem.despatchType = event.mPostPayDespatchItem.despatchType;
            String payText = PostPayDespatchItem.PAY_ONLINE_TEXT;
            if (PostPayDespatchItem.PAY_OFFLINE.equals(event.mPostPayDespatchItem.payType))
            {
                payText = PostPayDespatchItem.PAY_OFFLINE_TEXT;
            }
            else if (PostPayDespatchItem.PAY_ACCOUNT.equals(event.mPostPayDespatchItem.payType))
            {
                payText = PostPayDespatchItem.PAY_ACCOUNT_TEXT;
            }
            String despatchText = PostPayDespatchItem.DESPATCH_NORMA_TEXT;
            if (PostPayDespatchItem.DESPATCH_SELF.equals(event.mPostPayDespatchItem.despatchType))
            {
                despatchText = PostPayDespatchItem.DESPATCH_SELF_TEXT;
            }
            mTvDespatchWay.setText(payText + "\n" + despatchText);
        }
    }

    //===============================EVENT BUS========================
    @Override
    public void onDestroy()
    {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    private void setListener()
    {
        OnClickListenerImpl onClickListener = new OnClickListenerImpl();
        mLinearAddressContainer.setOnClickListener(onClickListener);
        mLinearPayDespatchContainer.setOnClickListener(onClickListener);
        mBtnOk.setOnClickListener(onClickListener);
    }

    @Override
    protected void doGetData()
    {
        UserOrderDetailPresenter userOrderDetailPresenter = new UserOrderDetailPresenterImpl(mActivity, this, REQUEST_GET_ORDER_DETAIL_TAG);
        userOrderDetailPresenter.doGetUserOrderDetail(mUserOrderId);
        AddressPresenter addressPresenter = new AddressPresenterImpl(mActivity, this, REQUEST_ADDRESS_TAG);
        addressPresenter.doGetAddressList();
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
            if (resultItem.success())
            {
                if (REQUEST_GET_ORDER_DETAIL_TAG.equals(requestTag))
                {
                    mActivity.showToast(resultItem.message);
                    mOrderDetailItem = GsonUtils.jsonToClass(resultItem.properties.getAsJsonObject().toString(), OrderDetailItem.class);
                    if (null != mOrderDetailItem)
                    {
                        showOrderDetail(mOrderDetailItem);
                    }
                }
                else if (REQUEST_ADDRESS_TAG.equals(requestTag))
                {
                    Type type = new TypeToken<List<AddressItem>>()
                    {
                    }.getType();
                    List<AddressItem> items = GsonUtils.jsonToResultItemToList(GsonUtils.toJson(resultItem), type);
                    if (null != items && !items.isEmpty())
                    {
                        AddressItem addressItem = items.get(0);
                        fillAddressItemToView(addressItem);
                    }
                }
                else if (REQUEST_CONFIRM_ORDER_TAG.equals(requestTag))
                {
                    mActivity.showToast(resultItem.message);
                    mBtnOk.setEnabled(false);
                    showConfirmOrderPopupWindow();
                }
                else if (REQUEST_PAY_ORDER_TAG.equals(requestTag))
                {
                  //  UserOrderActivity.actionUserOrder(mActivity, 5);
                    mActivity.finish();
                    EventBus.getDefault().post(new OnOrderStatusChangedEvent());
                }
            }
            else
            {
                if (REQUEST_GET_ORDER_DETAIL_TAG.equals(requestTag)) {
                    mActivity.showToast(resultItem.message);
                }
            }
        }

    }
    private void showConfirmOrderPopupWindow()
    {
        View contentView = LayoutInflater.from(mActivity).inflate(R.layout.layout_order_detail, null);
        OrderDetailPopupWindowUtils.showOrderDetail(mActivity, contentView);
        TextView tvPayWay = (TextView) contentView.findViewById(R.id.tv_pay_way);
        TextView tvPayMoney = (TextView) contentView.findViewById(R.id.tv_pay_money);
        TextView tvAccountMoney = (TextView) contentView.findViewById(R.id.tv_account_money);
        Button btnPayOrder = (Button) contentView.findViewById(R.id.btn_pay_order);
        ImageView ivCancle = (ImageView) contentView.findViewById(R.id.iv_cancle);
        String payText = PostPayDespatchItem.PAY_ACCOUNT_TEXT;
        if (PostPayDespatchItem.PAY_OFFLINE.equals(mPostPayDespatchItem.payType))
        {
            payText = PostPayDespatchItem.PAY_OFFLINE_TEXT;
        }
        else if (PostPayDespatchItem.PAY_ACCOUNT.equals(mPostPayDespatchItem.payType))
        {
            payText = PostPayDespatchItem.PAY_ACCOUNT_TEXT;
        }
        tvPayWay.setText(payText);
        tvPayMoney.setText(mFee + "元");
        tvAccountMoney.setText("6840" + "元");
        btnPayOrder.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                OrderDetailPopupWindowUtils.dismiss();
                payOrder();
            }
        });
        ivCancle.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                new AlertDialog.Builder(mActivity).setMessage("确定要放弃付款？").setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                        OrderDetailPopupWindowUtils.dismiss();
//                        UserOrderActivity.actionUserOrder(mActivity, 4);
                        mActivity.finish();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                }).show();
            }
        });
    }

    /***
     * 将获取到的地址信息绑定到对应的View
     *
     * @param addressItem
     */
    private void fillAddressItemToView(AddressItem addressItem)
    {
        mTvPhone.setText(addressItem.mobile);
        mTvAddress.setText(addressItem.city + addressItem.address);
        mTvName.setText(addressItem.name);
        //TODO 得到收货地址id
        mPostPayDespatchItem.aid = addressItem.id;
    }

    private void showOrderDetail(OrderDetailItem orderDetailItem)
    {
        if (null != orderDetailItem)
        {
            mScrollView.setVisibility(View.VISIBLE);
            mFee = orderDetailItem.fee;
            fillLinearDrugContainer(orderDetailItem.orderList);
            String countInfo = "共" + orderDetailItem.orderList.size() + "件商品，合计：￥:";
            double  despatchFee = 0.00;
            try
            {
                if(!TextUtils.isEmpty(orderDetailItem.despatchFee))
                {
                    despatchFee =   (Double.parseDouble(orderDetailItem.despatchFee));
                }
                if(mPostPayDespatchItem.despatchType .equals( PostPayDespatchItem.DESPATCH_NORMAL))
                {
                    mFee = (new DecimalFormat("#.00").format((Double.parseDouble(mFee)) + despatchFee)) + "";
                }
            }
            catch (Exception e)
            {

            }
            String tmp = " (含运费￥"+despatchFee+")";

            String countFeeInfo = countInfo + mFee;
            String allInfo = countFeeInfo + tmp;
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(allInfo);
            spannableStringBuilder.setSpan(new ForegroundColorSpan(0xFFFFA800), countInfo.length(), countFeeInfo.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            spannableStringBuilder.setSpan(new ForegroundColorSpan(0xFF999999), countFeeInfo.length(), allInfo.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            mTvFee.setText(spannableStringBuilder);

            SpannableStringBuilder spannableStringBuilder1 = new SpannableStringBuilder(("合计：￥:" + mFee) + "\n" + tmp);
            spannableStringBuilder1.setSpan(new ForegroundColorSpan(0xFFFFA800), "合计：￥:".length(), ("合计：￥:" + mFee).length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            spannableStringBuilder1.setSpan(new ForegroundColorSpan(0xFF999999), ("合计：￥:" + mFee).length(), spannableStringBuilder1.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            mTvConfirmFee.setText(spannableStringBuilder1);

            if (UserOrderItem.ORDER_STATUS_3.equals(orderDetailItem.orderStatus))
            {
                mLinearOrderSuccess.setVisibility(View.GONE);
                mLinearSubmitOrderContainer.setVisibility(View.VISIBLE);
            }
            else if (UserOrderItem.ORDER_STATUS_4.equals(orderDetailItem.orderStatus))
            {
                mLinearOrderSuccess.setVisibility(View.VISIBLE);
                mLinearSubmitOrderContainer.setVisibility(View.GONE);
            }

        }
    }

    /**
     * 将订单中添加的药品展示出来
     *
     * @param drugItems
     */
    private void fillLinearDrugContainer(List<OrderDetailItem.ListItem> drugItems)
    {
        if (null != drugItems)
        {
            mLinearDrugContainer.setVisibility(View.VISIBLE);
            for (int i = 0; i < drugItems.size(); i++)
            {
                final OrderDetailItem.ListItem item = drugItems.get(i);
                final View view = LayoutInflater.from(mActivity).inflate(R.layout.layout_prescription_drug_item, null);  //TODO
                final ImageView ivImageView = (ImageView) view.findViewById(R.id.iv_image);
                RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.relative_normal);
                LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linear_edit_container);
                relativeLayout.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.GONE);
                String img = item.drugImg;
                if (!TextUtils.isEmpty(img))
                {
                    if (!img.startsWith(APIURL.BASE_API_URL))
                    {
                        img = APIURL.BASE_API_URL + img;
                    }
                    ivImageView.setTag(img);
                    ImageLoader.getInstance().loadImage(img, new AbsImageLoadingListener()
                    {
                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
                        {
                            if (null != loadedImage && imageUri.equals(ivImageView.getTag()))
                            {
                                ivImageView.setImageBitmap(loadedImage);
                            }
                        }
                    });
                }
                TextView tvDrugName = (TextView) relativeLayout.findViewById(R.id.tv_drug_name);
                TextView tvDrugInfo = (TextView) relativeLayout.findViewById(R.id.tv_drug_info);
                TextView tvDrugPrice = (TextView) relativeLayout.findViewById(R.id.tv_drug_price);
                final TextView tvDrugCount = (TextView) relativeLayout.findViewById(R.id.tv_drug_count);
                tvDrugName.setText(item.drugName);
                tvDrugPrice.setText("￥" + item.unitPrice);
                tvDrugCount.setText("x1");
                String info = "包装规格：" + item.specs + "\n生产厂家：" + item.producer;
                tvDrugInfo.setText(info);
                int height = DensityUtils.dpToPx(mActivity, 100.f);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                mLinearDrugContainer.addView(view, layoutParams);
            }
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
                case R.id.btn_ok://提交订单
                    submitOrder();
                    break;
                case R.id.linear_address_container://点击收货地址
                      if(null != mOrderDetailItem &&(UserOrderItem.ORDER_STATUS_4.equals(mOrderDetailItem.orderStatus)))
                      {
                          return;
                      }
                    address();
                    break;
                case R.id.linear_pay_despatch_container://点击支付配送
                    if(null != mOrderDetailItem &&(UserOrderItem.ORDER_STATUS_4.equals(mOrderDetailItem.orderStatus)))
                    {
                        return;
                    }
                    payDispatch();
                    break;
            }
        }

    }
    private void payDispatch()
    {
        PayDespatchActivity.actionPayDispatch(mActivity);
    }
    private void address()
    {
        AddressListActivity.actionAddressManager(mActivity, true);
    }

    /***
     * 提交订单
     */
    private void submitOrder()
    {
        SubmitOrderPresenter submitOrderPresenter = new SubmitOrderPresenterImpl(mActivity, this, REQUEST_CONFIRM_ORDER_TAG);
        submitOrderPresenter.doSubmitOrder(mPostPayDespatchItem);
    }

    /***
     * 支付订单
     */
    private void payOrder()
    {
        PayOrderPresenter payOrderPresenter = new PayOrderPresenterImpl(mActivity, this, REQUEST_PAY_ORDER_TAG);
        payOrderPresenter.doPayOrder(mUserOrderId);
    }

}
