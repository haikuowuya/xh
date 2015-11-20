package com.xinheng.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.xinheng.R;
import com.xinheng.base.BaseFragment;
import com.xinheng.eventbus.OnUserCheckPayEvent;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.prescription.PostPayDespatchItem;
import com.xinheng.mvp.model.user.UserCheckDetailItem;
import com.xinheng.mvp.presenter.UserCheckDetailPresenter;
import com.xinheng.mvp.presenter.UserCheckPresenter;
import com.xinheng.mvp.presenter.impl.UserCheckDetailPresenterImpl;
import com.xinheng.mvp.presenter.impl.UserCheckPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.DateFormatUtils;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.OrderDetailPopupWindowUtils;

import de.greenrobot.event.EventBus;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：我的检查详情
 */
public class UserCheckDetailFragment extends BaseFragment implements DataView
{
    public static final String ARG_ID = "id";
    public static final String REQUEST_PAY_TAG = "pay";

    public static UserCheckDetailFragment newInstance(String medicalId)
    {
        UserCheckDetailFragment fragment = new UserCheckDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_ID, medicalId);
        fragment.setArguments(bundle);
        return fragment;
    }

    /***
     * 检查id
     */
    private String mUserCheckId;

    private ScrollView mScrollView;

    /**
     * 患者姓名
     */
    private TextView mTvPatientName;
    /***
     * 开具处方日期
     */
    private TextView mTvRecipeTime;
    /***
     * 科室信息
     */
    private TextView mTvDepartName;
    /***
     * 主治医师
     */
    private TextView mTvDoctorName;

    /***
     * 处方信息
     */
    private LinearLayout mLinearRecipeContainer;

    /***
     *
     */
    private LinearLayout mLinearMoneyContainer;
    /***
     * 支付钱
     */
    private TextView mTvMoney;
    /***
     * 支付
     */
    private TextView mTvPay;

    /***
     * 用户上传时的审核状态
     */
    private TextView mTvCheckStatus;
    /***
     * 检查类型
     */
    private TextView mTvCheckType;
    private String mFee = "0";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_user_check_detail, null); //TODO 布局文件
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        mTvRecipeTime = (TextView) view.findViewById(R.id.tv_recipe_time);
        mTvPatientName = (TextView) view.findViewById(R.id.tv_patient_name);
        mTvDepartName = (TextView) view.findViewById(R.id.tv_dept_name);
        mTvDoctorName = (TextView) view.findViewById(R.id.tv_doctor_name);
        mTvMoney = (TextView) view.findViewById(R.id.tv_money);
        mTvPay = (TextView) view.findViewById(R.id.tv_pay);
        mTvCheckType = (TextView) view.findViewById(R.id.tv_check_type);
        mTvCheckStatus = (TextView) view.findViewById(R.id.tv_check_status);
        mScrollView = (ScrollView) view.findViewById(R.id.sv_scrollview);
        mLinearRecipeContainer = (LinearLayout) view.findViewById(R.id.linear_recipe_container);
        mLinearMoneyContainer = (LinearLayout) view.findViewById(R.id.linear_money_container);
        mLinearMoneyContainer.setVisibility(View.GONE);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mUserCheckId = getArguments().getString(ARG_ID);
        doGetData();
        setListener();
    }

    private void setListener()
    {
        OnClickListenerImpl onClickListener = new OnClickListenerImpl();
        mTvPay.setOnClickListener(onClickListener);
    }

    @Override
    protected void doGetData()
    {
        UserCheckDetailPresenter userCheckDetailPresenter = new UserCheckDetailPresenterImpl(mActivity, this);
        userCheckDetailPresenter.doGetUserCheckDetail(mUserCheckId);
        mScrollView.setVisibility(View.GONE);
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
                if (REQUEST_PAY_TAG.equals(requestTag))
                {
//                    UserCenterActivity.actionUserCenter(mActivity);
                    EventBus.getDefault().post(new OnUserCheckPayEvent());
                    mActivity.finish();
                } else
                {
                    UserCheckDetailItem userCheckDetailItem = GsonUtils.jsonToClass(resultItem.properties.getAsJsonObject().toString(), UserCheckDetailItem.class);
                    if (null != userCheckDetailItem)
                    {
                        showUserCheckDetailInfo(userCheckDetailItem);
                    }
                }
            }
        }
    }

    private void showUserCheckDetailInfo(UserCheckDetailItem userCheckDetailItem)
    {
        if (null != userCheckDetailItem)
        {
            mScrollView.setVisibility(View.VISIBLE);
            String type = "医院同步";
            if ("1".equals(userCheckDetailItem.type))
            {
                type = " 用户上传";
            }
            String status = "审核中";
            if (!TextUtils.isEmpty(userCheckDetailItem.consentState))
            {
                if ("1".equals(userCheckDetailItem.consentState))
                {
                    status = "审核通过";
                } else if ("2".equals(userCheckDetailItem.consentState))
                {
                    status = "审核不通过";
                }
            } else
            {
                if ("0".equals(userCheckDetailItem.payState))
                {
                    status = "未缴费";
                } else if ("1".equals(userCheckDetailItem.payState))
                {
                    status = "已缴费";
                }
            }
            mTvPatientName.setText("患者姓名：" + userCheckDetailItem.patient);
            mTvRecipeTime.setText("检查时间：" + DateFormatUtils.format(userCheckDetailItem.checkTime, true, false));
            mTvDepartName.setText(" 科  别 ：" + userCheckDetailItem.department);
            mTvCheckType.setText(" 类  型 ：" + type);
            mTvCheckStatus.setText(" 状  态 ：" + status);
            mTvDoctorName.setText("检查医师：" + userCheckDetailItem.doctor);

            if (null != userCheckDetailItem.list && !userCheckDetailItem.list.isEmpty())
            {
                if ("1".equals(userCheckDetailItem.consentState))     //   "审核通过";
                {
                    mLinearMoneyContainer.setVisibility(View.VISIBLE);
                }
                for (int i = 0; i < userCheckDetailItem.list.size(); i++)
                {
                    View childItem = LayoutInflater.from(mActivity).inflate(R.layout.layout_user_check_detail_check_item, null);
                    TextView tvName = (TextView) childItem.findViewById(R.id.tv_name);

                    TextView tvUnit = (TextView) childItem.findViewById(R.id.tv_unit);
                    tvName.getPaint().setFakeBoldText(false);
                    tvUnit.getPaint().setFakeBoldText(false);
                    UserCheckDetailItem.ListItem listItem = userCheckDetailItem.list.get(i);
                    tvName.setText(listItem.name + "");

                    tvUnit.setText(listItem.cost + "");
                    mLinearRecipeContainer.addView(childItem);
                }
                String money = "0.0";
                if (!TextUtils.isEmpty(userCheckDetailItem.fee))
                {
                    money = userCheckDetailItem.fee;
                    mFee = money;
                }
                String HJ = "合计：";
                SpannableStringBuilder spannableText = new SpannableStringBuilder(HJ + "￥" + money + "元");
                spannableText.setSpan(new ForegroundColorSpan(0xFFFF5906), HJ.length(), spannableText.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                mTvMoney.setText(spannableText);
            }
            mScrollView.fullScroll(View.FOCUS_DOWN);
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
                case R.id.tv_pay://立即支付
                    showConfirmOrderPopupWindow();
                    break;
            }
        }

    }

    private void pay()
    {
//        mActivity.showToast("立即支付");

        UserCheckPresenter userCheckPresenter = new UserCheckPresenterImpl(mActivity, this, REQUEST_PAY_TAG);
        userCheckPresenter.doUserCheckPay(mUserCheckId);
    }

    private void showConfirmOrderPopupWindow()
    {
        View contentView = LayoutInflater.from(mActivity).inflate(R.layout.layout_order_detail, null);
        contentView.setFocusableInTouchMode(true);
        contentView.setFocusable(true);
        OrderDetailPopupWindowUtils.showOrderDetail(mActivity, contentView);
        TextView tvPayWay = (TextView) contentView.findViewById(R.id.tv_pay_way);
        TextView tvPayMoney = (TextView) contentView.findViewById(R.id.tv_pay_money);
        TextView tvAccountMoney = (TextView) contentView.findViewById(R.id.tv_account_money);
        Button btnPayOrder = (Button) contentView.findViewById(R.id.btn_pay_order);
        ImageView ivCancle = (ImageView) contentView.findViewById(R.id.iv_cancle);
        String payText = PostPayDespatchItem.PAY_ACCOUNT_TEXT;
        tvPayWay.setText(payText);
        tvPayMoney.setText(mFee + "元");
        tvAccountMoney.setText("6840" + "元");
        btnPayOrder.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        OrderDetailPopupWindowUtils.dismiss();
                        pay();
                    }
                });
        ivCancle.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        new AlertDialog.Builder(mActivity).setMessage("确定要放弃付款？").setPositiveButton(
                                "确定", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        dialog.dismiss();
                                        OrderDetailPopupWindowUtils.dismiss();
//                                        UserOrderActivity.actionUserOrder(mActivity, 3);
//                                        mActivity.finish();
                                    }
                                }).setNegativeButton(
                                "取消", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        dialog.dismiss();
                                    }
                                }).show();
                    }
                });
        mActivity.getContentViewGroup().setOnKeyListener(
                new View.OnKeyListener()
                {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event)
                    {
                        if (keyCode == KeyEvent.KEYCODE_BACK)
                        {
                            mActivity.showToast("点击返回键");
                        }
                        return false;
                    }
                });
        contentView.setOnKeyListener(
                new View.OnKeyListener()
                {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event)
                    {
                        if (keyCode == KeyEvent.KEYCODE_BACK)
                        {
                            mActivity.showToast("点击返回键");
                        }
                        return false;
                    }
                });

    }

}
