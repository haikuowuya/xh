package com.xinheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.xinheng.R;
import com.xinheng.UserPatientListActivity;
import com.xinheng.base.BaseFragment;
import com.xinheng.eventbus.OnSelectPatientEvent;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.doctor.DoctorScheduleItem;
import com.xinheng.mvp.model.user.UserPatientItem;
import com.xinheng.mvp.presenter.UserPatientPresenter;
import com.xinheng.mvp.presenter.impl.UserPatientPresenterImpl;
import com.xinheng.mvp.view.DataView;
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
 * 说明：预约挂号Fragment界面
 */
public class SubscribeFragment extends BaseFragment implements DataView
{
    private static final String ARG_JSON_SCHEDULE_ITEMS = "json_schedule_item";
    /***
     * 请求获取常用就诊人列表
     */
    public static final String REQUEST_PATIENT_TAG = "request_patient";

    public static SubscribeFragment newInstance(String doctorScheduleItemJson)
    {
        SubscribeFragment fragment = new SubscribeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_JSON_SCHEDULE_ITEMS, doctorScheduleItemJson);
        fragment.setArguments(bundle);
        return fragment;
    }

    /***
     * 医生排班信息
     */
    private List<DoctorScheduleItem> mDoctorScheduleItems;

    /***
     * 就诊地点
     */
    private TextView mTvAddress;

    /**
     * 就诊日期
     */
    private TextView mTvDate;
    /***
     * 门诊类型
     */
    private TextView mTvType;
    /**
     * 挂号费用
     */
    private TextView mTvFee;

    /***
     * 就诊人container
     */
    private LinearLayout mLinearPatientContainer;

    private ImageView mIvSelectDate;

    /***
     * 选择患者
     */
    private TextView mTvSelectPatient;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_subscribe, null); //TODO 布局文件
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        mTvAddress = (TextView) view.findViewById(R.id.tv_address);
        mTvFee = (TextView) view.findViewById(R.id.tv_fee);
        mTvType = (TextView) view.findViewById(R.id.tv_type);
        mTvDate = (TextView) view.findViewById(R.id.tv_date);
        mIvSelectDate = (ImageView) view.findViewById(R.id.iv_select_date);
        mTvSelectPatient = (TextView) view.findViewById(R.id.tv_select_patient);
        mLinearPatientContainer = (LinearLayout) view.findViewById(R.id.linear_patient_container);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
        String json = getArguments().getString(ARG_JSON_SCHEDULE_ITEMS);
        if (!TextUtils.isEmpty(json))
        {
            Type type = new TypeToken<List<DoctorScheduleItem>>()
            {
            }.getType();
            mDoctorScheduleItems = GsonUtils.jsonToList(json, type);
        }
        if (null != mDoctorScheduleItems && !mDoctorScheduleItems.isEmpty())
        {
            DoctorScheduleItem doctorScheduleItem = mDoctorScheduleItems.get(0);
            mTvDate.setText(doctorScheduleItem.date);
            mTvAddress.setText(doctorScheduleItem.address);
            mTvFee.setText(doctorScheduleItem.fee);
            mTvType.setText(doctorScheduleItem.type);
        }

        doGetUserPatient();
        setListener();
    }

    //选择支付配送方式事件
    @Subscribe
    public void onEventMainThread(OnSelectPatientEvent event)
    {
        if (null != event && null != event.mUserPatientItem)
        {
            fillPatient(event.mUserPatientItem);
        }
    }

    @Override
    public void onDestroy()
    {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /***
     * 获取常用就诊人
     */
    private void doGetUserPatient()
    {
        UserPatientPresenter userPatientPresenter = new UserPatientPresenterImpl(mActivity, this, REQUEST_PATIENT_TAG);
        userPatientPresenter.doGetUserPatient();
    }

    private void setListener()
    {
        OnClickListenerImpl onClickListener = new OnClickListenerImpl();
        mTvSelectPatient.setOnClickListener(onClickListener);
        mIvSelectDate.setOnClickListener(onClickListener);

    }

    @Override
    protected void doGetData()
    {

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
            mActivity.showCroutonToast(resultItem.message);
            if (resultItem.success())
            {
                if (REQUEST_PATIENT_TAG.equals(requestTag))
                {
                    Type type = new TypeToken<List<UserPatientItem>>()
                    {
                    }.getType();
                    List<UserPatientItem> items = GsonUtils.jsonToResultItemToList(GsonUtils.toJson(resultItem), type);
                    if (null != items)
                    {
                        mLinearPatientContainer.removeAllViews();
                        UserPatientItem patientItem = items.get(0);
                        fillPatient(patientItem);
                    }
                }
            }
        }
    }

    /***
     * 将选中的就诊人显示出来
     *
     * @param patientItem
     */
    private void fillPatient(UserPatientItem patientItem)
    {
        TextView textView = new TextView(mActivity);
        textView.setText(patientItem.name);
        int paddingLRTB = DensityUtils.dpToPx(mActivity, 10.f);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setBackgroundResource(R.drawable.shape_edittext_background);
        textView.setPadding(paddingLRTB, paddingLRTB, paddingLRTB, paddingLRTB);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.rightMargin = paddingLRTB;

        mLinearPatientContainer.addView(textView, layoutParams);
    }

    @Override
    public void onGetDataFailured(String msg, String requestTag)
    {
        mActivity.showCroutonToast(msg);
    }

    private class OnClickListenerImpl implements View.OnClickListener
    {
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.tv_select_patient://选择常用患者
                    UserPatientListActivity.actionPatient(mActivity);
                    break;
            }
        }
    }

}
