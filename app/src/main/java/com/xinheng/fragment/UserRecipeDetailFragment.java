package com.xinheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.xinheng.R;
import com.xinheng.base.BaseFragment;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.user.UserRecipeDetailItem;
import com.xinheng.mvp.presenter.UserRecipeDetailPresenter;
import com.xinheng.mvp.presenter.impl.UserRecipeDetailPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.DateFormatUtils;
import com.xinheng.util.GsonUtils;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：我的处方详情
 */
public class UserRecipeDetailFragment extends BaseFragment implements DataView
{
    public static final String ARG_ID = "id";

    public static UserRecipeDetailFragment newInstance(String medicalId)
    {
        UserRecipeDetailFragment fragment = new UserRecipeDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_ID, medicalId);
        fragment.setArguments(bundle);
        return fragment;
    }

    /***
     * 处方id
     */
    private String mUserRecipeId;

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
    /**
     * 剂数
     */
    private TextView mTvQuantity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_user_recipe_detail, null); //TODO 布局文件
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
        mTvQuantity = (TextView) view.findViewById(R.id.tv_quantity);
        mScrollView = (ScrollView) view.findViewById(R.id.sv_scrollview);
        mLinearRecipeContainer = (LinearLayout) view.findViewById(R.id.linear_recipe_container);
        mLinearMoneyContainer = (LinearLayout) view.findViewById(R.id.linear_money_container);
        mLinearMoneyContainer.setVisibility(View.GONE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mUserRecipeId = getArguments().getString(ARG_ID);
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
        UserRecipeDetailPresenter userRecipeDetailPresenter = new UserRecipeDetailPresenterImpl(mActivity, this);
        userRecipeDetailPresenter.doGetUserRecipeDetail(mUserRecipeId);
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
                UserRecipeDetailItem userRecipeDetailItem = GsonUtils.jsonToClass(resultItem.properties.getAsJsonObject().toString(), UserRecipeDetailItem.class);
                if (null != userRecipeDetailItem)
                {
                    showUserRecipeInfo(userRecipeDetailItem);
                }
            }
        }
    }

    private void showUserRecipeInfo(UserRecipeDetailItem userRecipeDetailItem)
    {
        if (null != userRecipeDetailItem)
        {
            mScrollView.setVisibility(View.VISIBLE);
            mTvPatientName.setText("就诊人：" + userRecipeDetailItem.patient);
            mTvRecipeTime.setText("开具日期：" + DateFormatUtils.format(userRecipeDetailItem.prescTime, true, false));
            mTvDepartName.setText(" 科  别 ：" + userRecipeDetailItem.department);
            mTvDoctorName.setText("主治医师：" + userRecipeDetailItem.doctor);
            if (null != userRecipeDetailItem.prescription && null != userRecipeDetailItem.prescription.list && !userRecipeDetailItem.prescription.list.isEmpty())
            {
                mLinearMoneyContainer.setVisibility(View.VISIBLE);
                mTvQuantity.setText(userRecipeDetailItem.prescription.quantity + "剂");
                for (int i = 0; i < userRecipeDetailItem.prescription.list.size(); i++)
                {
                    View childItem = LayoutInflater.from(mActivity).inflate(R.layout.layout_user_recipe_detail_recipe_item, null);
                    TextView tvName = (TextView) childItem.findViewById(R.id.tv_name);
                    TextView tvNum = (TextView) childItem.findViewById(R.id.tv_num);
                    TextView tvUnit = (TextView) childItem.findViewById(R.id.tv_unit);
                    tvName.getPaint().setFakeBoldText(false);
                    tvNum.getPaint().setFakeBoldText(false);
                    tvUnit.getPaint().setFakeBoldText(false);
                    UserRecipeDetailItem.ListItem listItem = userRecipeDetailItem.prescription.list.get(i);
                    tvName.setText(listItem.name + "");
                    tvNum.setText(listItem.num + "");
                    tvUnit.setText(listItem.unit + "");
                    mLinearRecipeContainer.addView(childItem);
                }
                String money = "0.0";
                if (!TextUtils.isEmpty(userRecipeDetailItem.prescription.cost))
                {
                    money = userRecipeDetailItem.prescription.cost;
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
                    pay();
                    break;
            }
        }

    }

    private void pay()
    {
        mActivity.showToast("立即支付");
    }

}
