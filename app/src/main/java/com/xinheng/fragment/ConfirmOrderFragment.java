package com.xinheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.xinheng.AddressListActivity;
import com.xinheng.PayDespatchActivity;
import com.xinheng.R;
import com.xinheng.base.BaseFragment;
import com.xinheng.eventbus.OnSelectAddressEvent;
import com.xinheng.eventbus.OnSelectPayDespatchEvent;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.address.AddressItem;
import com.xinheng.mvp.model.prescription.ConfirmOrderItem;
import com.xinheng.mvp.model.prescription.PostPayDespatchItem;
import com.xinheng.mvp.presenter.AddressPresenter;
import com.xinheng.mvp.presenter.ConfirmOrderPresenter;
import com.xinheng.mvp.presenter.SubmitOrderPresenter;
import com.xinheng.mvp.presenter.impl.AddressPresenterImpl;
import com.xinheng.mvp.presenter.impl.ConfirmOrderPresenterImpl;
import com.xinheng.mvp.presenter.impl.SubmitOrderPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.DateFormatUtils;
import com.xinheng.util.DensityUtils;
import com.xinheng.util.GsonUtils;

import java.lang.reflect.Type;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：  确认订单界面
 */
public class ConfirmOrderFragment extends BaseFragment implements DataView
{

    public static final String REQUEST_ADDRESS_TAG = "request_address";
    public static final String REQUEST_ORDER_INFO_TAG = "request_order_info";
    public static final String REQUEST_CONFIRM_ORDER_TAG = "request_confirm_order";

    public static final String ARG_ORDER_ID = "order_id";

    public static ConfirmOrderFragment newInstance(String orderId)
    {
        ConfirmOrderFragment fragment = new ConfirmOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_ORDER_ID, orderId);
        fragment.setArguments(bundle);
        return fragment;
    }

    /***
     * 根据订单id获取订单中的信息
     */
    private String mOrderId;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_confirm_order, null);  //TODO  布局文件
        initView(view);
        mIsInit = true;
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
        mOrderId = getArguments().getString(ARG_ORDER_ID);
        //TODO 得到订单id
        mPostPayDespatchItem.id = mOrderId;
        mTvDespatchTime.setText(DateFormatUtils.format(System.currentTimeMillis() + "", true, false));
        //mTvDespatchWay.setText("在线支付" + "\n" + "普通快递");
        mTvDespatchWay.setText(PostPayDespatchItem.PAY_ONLINE_TEXT + "\n" + PostPayDespatchItem.DESPATCH_NORMA_TEXT);
        OnClickListenerImpl onClickListener = new OnClickListenerImpl();
        mLinearAddressContainer.setOnClickListener(onClickListener);
        mLinearPayDespatchContainer.setOnClickListener(onClickListener);
        mBtnOk.setOnClickListener(onClickListener);
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

    private void initView(View view)
    {
        mTvFee = (TextView) view.findViewById(R.id.tv_fee);
        mTvDespatchTime = (TextView) view.findViewById(R.id.tv_despatch_time);
        mTvDespatchWay = (TextView) view.findViewById(R.id.tv_despatch_way);
        mTvAddress = (TextView) view.findViewById(R.id.tv_address);
        mTvName = (TextView) view.findViewById(R.id.tv_name);
        mTvPhone = (TextView) view.findViewById(R.id.tv_phone);
        mBtnOk = (Button) view.findViewById(R.id.btn_ok);
        mTvConfirmFee = (TextView) view.findViewById(R.id.tv_confirm_fee);
        mLinearAddressContainer = (LinearLayout) view.findViewById(R.id.linear_address_container);
        mLinearPayDespatchContainer = (LinearLayout) view.findViewById(R.id.linear_pay_despatch_container);
        mLinearDrugContainer = (LinearLayout) view.findViewById(R.id.linear_drug_container);
        mLinearDrugContainer.setVisibility(View.GONE);
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
        ConfirmOrderPresenter confirmOrderPresenter = new ConfirmOrderPresenterImpl(mActivity, this, REQUEST_ORDER_INFO_TAG);
        confirmOrderPresenter.doGetConfirmOrder(mOrderId);
        AddressPresenter addressPresenter = new AddressPresenterImpl(mActivity, this, REQUEST_ADDRESS_TAG);
        addressPresenter.doGetAddressList();
    }

    @Override
    public void onGetDataSuccess(ResultItem resultItem, String requestTag)
    {
        if (null != resultItem)
        {
            if (resultItem.success())
            {
                if (REQUEST_ORDER_INFO_TAG.equals(requestTag))
                {
                    mActivity.showCroutonToast(resultItem.message);
                    ConfirmOrderItem confirmOrderItem = GsonUtils.jsonToClass(resultItem.properties.getAsJsonObject().toString(), ConfirmOrderItem.class);
                    if (null != confirmOrderItem && null != confirmOrderItem.list && !confirmOrderItem.list.isEmpty())
                    {
                        fillLinearDrugContainer(confirmOrderItem.list);
                        String countInfo = "共" + confirmOrderItem.list.size() + "件商品，合计：￥:";
                        String tmp = " (含运费￥0.00)";
                        String countFeeInfo = countInfo + confirmOrderItem.fee;
                        String allInfo = countFeeInfo + tmp;
                        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(allInfo);
                        spannableStringBuilder.setSpan(new ForegroundColorSpan(0xFFFFA800), countInfo.length(), countFeeInfo.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                        spannableStringBuilder.setSpan(new ForegroundColorSpan(0xFF999999), countFeeInfo.length(), allInfo.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                        mTvFee.setText(spannableStringBuilder);

                        SpannableStringBuilder spannableStringBuilder1 = new SpannableStringBuilder(("合计：￥:" + confirmOrderItem.fee) + "\n" + tmp);
                        spannableStringBuilder1.setSpan(new ForegroundColorSpan(0xFFFFA800), "合计：￥:".length(), ("合计：￥:" + confirmOrderItem.fee).length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                        spannableStringBuilder1.setSpan(new ForegroundColorSpan(0xFF999999), ("合计：￥:" + confirmOrderItem.fee).length(), spannableStringBuilder1.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                        mTvConfirmFee.setText(spannableStringBuilder1);
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
                    mActivity.showCroutonToast(resultItem.message);
                }
            }
        }
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

    @Override
    public void onGetDataFailured(String msg, String requestTag)
    {
        mActivity.showCroutonToast(msg);
    }

    /**
     * 将订单中添加的药品展示出来
     *
     * @param drugItems
     */
    private void fillLinearDrugContainer(List<ConfirmOrderItem.ListItem> drugItems)
    {
        if (null != drugItems)
        {
            mLinearDrugContainer.setVisibility(View.VISIBLE);
            for (int i = 0; i < drugItems.size(); i++)
            {
                final ConfirmOrderItem.ListItem item = drugItems.get(i);
                final View view = LayoutInflater.from(mActivity).inflate(R.layout.layout_prescription_drug_item, null);  //TODO
                RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.relative_normal);
                LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linear_edit_container);
                relativeLayout.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.GONE);
                TextView tvDrugName = (TextView) relativeLayout.findViewById(R.id.tv_drug_name);
                TextView tvDrugInfo = (TextView) relativeLayout.findViewById(R.id.tv_drug_info);
                TextView tvDrugPrice = (TextView) relativeLayout.findViewById(R.id.tv_drug_price);
                final TextView tvDrugCount = (TextView) relativeLayout.findViewById(R.id.tv_drug_count);
                tvDrugName.setText(item.name);
                tvDrugPrice.setText("￥" + item.price);
                tvDrugCount.setText("x1");
                String info = "包装规格：" + item.specs + "\n生产厂家：" + item.producer;
                tvDrugInfo.setText(info);
                int height = DensityUtils.dpToPx(mActivity, 100.f);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                mLinearDrugContainer.addView(view, layoutParams);
            }

        }
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
                    address();
                    break;
                case R.id.linear_pay_despatch_container://点击支付配送
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

    private void submitOrder()
    {
        SubmitOrderPresenter submitOrderPresenter = new SubmitOrderPresenterImpl(mActivity, this, REQUEST_CONFIRM_ORDER_TAG);
        submitOrderPresenter.doSubmitOrder(mPostPayDespatchItem);
    }

}
