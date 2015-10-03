package com.xinheng.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.xinheng.R;
import com.xinheng.adapter.subscribe.AppointmentDetailImageGridAdapter;
import com.xinheng.base.BaseFragment;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.user.UserAppointmentAddDetailItem;
import com.xinheng.mvp.presenter.UserAppointmentDetailPresenter;
import com.xinheng.mvp.presenter.impl.UserAppointmentDetailPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.DateFormatUtils;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.PhotoUtils;
import com.xinheng.view.CustomGridView;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：预约加号详情Fragment界面
 */
public class UserAppointmentAddDetailFragment extends BaseFragment implements DataView
{
    public static final String ARG_USER_APPOINTMENT_ID = "user_appointment_id";
    public static UserAppointmentAddDetailFragment newInstance(String appointmentId)
    {
        UserAppointmentAddDetailFragment fragment = new UserAppointmentAddDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_USER_APPOINTMENT_ID, appointmentId);
        fragment.setArguments(bundle);
        return fragment;
    }
    private Button mBtnSubmit;
    private CustomGridView mCustomGridView;
    /***
     * 显示是否有图片的标志
     */
    private TextView mTvGridViewStatus;

    /***
     * 性别
     */
    private RadioGroup mRgGender;
    /***
     * 年龄
     */
    private EditText mEtAge;

    /***
     * 预约加号时间
     */
    private TextView mTvDate;

    /***
     * 疾病名称
     */
    private EditText mEtDiseaseName;

    /****
     * 疾病描述
     */
    private EditText mEtDiseaseDesc;
    /***
     * 患者留言
     */
    private EditText mEtPatientMsg;

    /***
     * 手机号码输入框
     */
    private EditText mEtPhone;
    /**
     * 验证码输入框
     */
    private EditText mEtCode;
    /**
     * 获取验证码
     */
    private Button mBtnCode;

    /***
     * 患者信息
     */
    private TextView mTvPatientInfo;


    private SMSBroadcastReceiver mSMSBroadcastReceiver = new SMSBroadcastReceiver();

    /***
     * 预约id
     */
    private String mUserAppointmentId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_user_appointment_add_detail, null); //TODO 布局文件
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        mBtnSubmit = (Button) view.findViewById(R.id.btn_submit);
        mEtAge = (EditText) view.findViewById(R.id.et_age);
        mEtCode = (EditText) view.findViewById(R.id.et_code);
        mEtDiseaseDesc = (EditText) view.findViewById(R.id.et_disease_desc);
        mEtDiseaseName = (EditText) view.findViewById(R.id.et_disease_name);
        mEtPatientMsg = (EditText) view.findViewById(R.id.et_patient_msg);
        mTvDate = (TextView) view.findViewById(R.id.tv_date);
        mEtPhone = (EditText) view.findViewById(R.id.et_phone);
        mRgGender = (RadioGroup) view.findViewById(R.id.rg_gender);
        mBtnCode = (Button) view.findViewById(R.id.btn_code);
        mTvGridViewStatus = (TextView) view.findViewById(R.id.tv_gridview_status);
        mTvPatientInfo = (TextView) view.findViewById(R.id.tv_patient_info);
        mCustomGridView = (CustomGridView) view.findViewById(R.id.custom_gridview);
        mCustomGridView.setNumColumns(3);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mUserAppointmentId = getArguments().getString(ARG_USER_APPOINTMENT_ID);
        setListener();
        registerSMSReceiver();
        doGetData();
    }

    private void registerSMSReceiver()
    {
        // 生成广播处理
        // 实例化过滤器并设置要过滤的广播
        IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        intentFilter.setPriority(800);
        // 注册广播
        mActivity.registerReceiver(mSMSBroadcastReceiver, intentFilter);
    }

    @Override
    public void onDestroy()
    {
        mActivity.unregisterReceiver(mSMSBroadcastReceiver);
        super.onDestroy();
    }

    private void setListener()
    {
        OnClickListenerImpl onClickListener = new OnClickListenerImpl();
        mBtnSubmit.setOnClickListener(onClickListener);
        mBtnCode.setOnClickListener(onClickListener);
        mCustomGridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String str = parent.getAdapter().getItem(position) == null ? null : parent.getAdapter().getItem(position).toString();
                if (TextUtils.isEmpty(str))
                {
                    PhotoUtils.showSelectDialog(mActivity);
                }
            }
        });
    }

    @Override
    protected void doGetData()
    {
        UserAppointmentDetailPresenter userAppointmentDetailPresenter = new UserAppointmentDetailPresenterImpl(mActivity, this);
        userAppointmentDetailPresenter.doGetUserAppointmentDetail(mUserAppointmentId);
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
                UserAppointmentAddDetailItem userAppointmentAddDetailItem = GsonUtils.jsonToClass(resultItem.properties.getAsJsonObject().toString(), UserAppointmentAddDetailItem.class);
                if(null != userAppointmentAddDetailItem)
                {
                    showAppointmentAddInfo(userAppointmentAddDetailItem);
                }

            }
        }
    }

    /***
     * 显示获取到的预约加号详情信息
     * @param userAppointmentAddDetailItem
     */
    private void showAppointmentAddInfo(UserAppointmentAddDetailItem userAppointmentAddDetailItem)
    {
          mTvDate.setText(DateFormatUtils.format(userAppointmentAddDetailItem.seeTime,true,false));;
        String sex ="1".equals(userAppointmentAddDetailItem.sex)?"男":"女";
        mTvPatientInfo.setText("性别     "+sex + "   "+userAppointmentAddDetailItem.age+" 岁");
        mEtPatientMsg.setText(userAppointmentAddDetailItem.patientmessage);
        mEtDiseaseName.setText(userAppointmentAddDetailItem.conditionReport);
        mEtDiseaseDesc.setText(userAppointmentAddDetailItem.symptoms);

        if (userAppointmentAddDetailItem.imgs != null && !userAppointmentAddDetailItem.imgs.isEmpty())
        {
            mTvGridViewStatus.setVisibility(View.GONE);
            mCustomGridView.setAdapter(new AppointmentDetailImageGridAdapter(mActivity, userAppointmentAddDetailItem.imgs));
        }
        else
        {
            mTvGridViewStatus.setVisibility(View.VISIBLE);
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
            }
        }
    }

    public class SMSBroadcastReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            // 判断是系统短信；
            if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED"))
            {
                // 不再往下传播；
                this.abortBroadcast();
                String content = null;
                Bundle bundle = intent.getExtras();
                if (bundle != null)
                {
                    // 通过pdus获得接收到的所有短信消息，获取短信内容；
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    // 构建短信对象数组；
                    SmsMessage[] mges = new SmsMessage[pdus.length];
                    for (int i = 0; i < pdus.length; i++)
                    {
                        // 获取单条短信内容，以pdu格式存,并生成短信对象；
                        mges[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    }
                    for (SmsMessage mge : mges)
                    {
                        content = mge.getMessageBody();// 获取短信的内容
                        System.out.println(" sms content = " + content);
                    }
                }
            }
        }
    }

}
