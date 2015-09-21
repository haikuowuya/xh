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

import com.xinheng.R;
import com.xinheng.base.BaseFragment;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.prescription.ConfirmOrderItem;
import com.xinheng.mvp.presenter.ConfirmOrderPresenter;
import com.xinheng.mvp.presenter.impl.ConfirmOrderPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.DateFormatUtils;
import com.xinheng.util.DensityUtils;
import com.xinheng.util.GsonUtils;

import java.util.List;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：  确认订单界面
 */
public class ConfirmOrderFragment extends BaseFragment implements DataView
{

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
    private TextView mTvDispatchWay;
    /**
     * 配送时间
     */
    private TextView mTvDispatchTime;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_confirm_order, null);  //TODO
        initView(view);
        mIsInit = true;
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mOrderId = getArguments().getString(ARG_ORDER_ID);
        mTvDispatchTime.setText(DateFormatUtils.format(System.currentTimeMillis()+"",true,false));
        mTvDispatchWay.setText("在线支付"+"\n"+"普通快递");
        OnClickListenerImpl onClickListener = new OnClickListenerImpl();
    }

    private void initView(View view)
    {
        mTvFee = (TextView) view.findViewById(R.id.tv_fee);
        mTvDispatchTime = (TextView) view.findViewById(R.id.tv_dispatch_time);
        mTvDispatchWay = (TextView) view.findViewById(R.id.tv_dispatch_way);
        mBtnOk = (Button) view.findViewById(R.id.btn_ok);
        mTvConfirmFee = (TextView) view.findViewById(R.id.tv_confirm_fee);
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
        ConfirmOrderPresenter confirmOrderPresenter = new ConfirmOrderPresenterImpl(mActivity, this);
        confirmOrderPresenter.doGetConfirmOrder(mOrderId);
    }

    @Override
    public void onGetDataSuccess(ResultItem resultItem)
    {
        if (null != resultItem)
        {
            mActivity.showCroutonToast(resultItem.message);
            if (resultItem.success())
            {
                ConfirmOrderItem confirmOrderItem = GsonUtils.jsonToClass(resultItem.properties.getAsJsonObject().toString(), ConfirmOrderItem.class);
                if (null != confirmOrderItem && null != confirmOrderItem.list && !confirmOrderItem.list.isEmpty())
                {
                    fillLinearDrugContainer(confirmOrderItem.list);

                    String countInfo = "共" + confirmOrderItem.quantity + "件商品，合计：￥:";
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
        }
    }

    @Override
    public void onGetDataFailured(String msg)
    {

    }

    /**
     * 将药方中添加的药品展示出来
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
                //    tvDrugPrice.setText("￥" + item.cost);
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
                case  R.id.btn_ok://确认
                    ok();
            }

        }
    }

    private void ok()
    {

    }

}
