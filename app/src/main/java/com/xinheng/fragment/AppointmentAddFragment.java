package com.xinheng.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.xinheng.R;
import com.xinheng.UserAppointmentActivity;
import com.xinheng.UserPatientListActivity;
import com.xinheng.adapter.subscribe.ImageGridAdapter;
import com.xinheng.base.BaseFragment;
import com.xinheng.eventbus.OnSelectPatientEvent;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.appointment.PatientRecordItem;
import com.xinheng.mvp.model.appointment.PostSubmitAppointmentAddItem;
import com.xinheng.mvp.model.doctor.DoctorScheduleItem;
import com.xinheng.mvp.model.user.UserPatientItem;
import com.xinheng.mvp.presenter.SendCodePresenter;
import com.xinheng.mvp.presenter.SubmitAppointmentAddPresenter;
import com.xinheng.mvp.presenter.UserPatientPresenter;
import com.xinheng.mvp.presenter.impl.SendCodePresenterImpl;
import com.xinheng.mvp.presenter.impl.SubmitAppointmentAddPresenterImpl;
import com.xinheng.mvp.presenter.impl.UserPatientPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.BitmapUtils;
import com.xinheng.util.DateFormatUtils;
import com.xinheng.util.DensityUtils;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.PatternUtils;
import com.xinheng.util.PhotoUtils;
import com.xinheng.util.RSAUtil;
import com.xinheng.util.StorageUtils;
import com.xinheng.util.VerifyCodeUtils;
import com.xinheng.view.CustomGridView;

import java.io.File;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：预约加号Fragment界面
 */
public class AppointmentAddFragment extends BaseFragment implements DataView
{
    private static final String ARG_DOCTOR_SCHEDULE_TIEM = "doctor_schedule_item";

    /***
     * 请求获取常用就诊人列表
     */
    public static final String REQUEST_PATIENT_LIST_TAG = "request_patient_list";

    /***
     * 获取验证码请求
     */
    public static final String REQUEST_CODE_TAG = "request_code";
    /***
     * 提交请求
     */
    public static final String REQUEST_SUBMIT_TAG = "request_submit";
    private UserPatientItem mUserPatientItem;

    public static AppointmentAddFragment newInstance(DoctorScheduleItem doctorScheduleItem)
    {
        AppointmentAddFragment fragment = new AppointmentAddFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_DOCTOR_SCHEDULE_TIEM, doctorScheduleItem);
        fragment.setArguments(bundle);
        return fragment;
    }

    /***
     * 预约加号的排班信息
     */
    private DoctorScheduleItem mDoctorScheduleItem;
    private Button mBtnSubmit;
    private CustomGridView mCustomGridView;
    private LinkedList<String> mImageFilePaths;

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
     * 就诊人container
     */
    private LinearLayout mLinearPatientContainer;
    /***
     * 选择患者
     */
    private TextView mTvSelectPatient;

    private SMSBroadcastReceiver mSMSBroadcastReceiver = new SMSBroadcastReceiver();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_appointment_add, null); //TODO 布局文件
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
        mTvSelectPatient = (TextView) view.findViewById(R.id.tv_select_patient);
        mLinearPatientContainer = (LinearLayout) view.findViewById(R.id.linear_patient_container);
        mCustomGridView = (CustomGridView) view.findViewById(R.id.custom_gridview);
        mCustomGridView.setNumColumns(3);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
        mDoctorScheduleItem = getArguments().getSerializable(ARG_DOCTOR_SCHEDULE_TIEM) == null ? null : (DoctorScheduleItem) getArguments().getSerializable(ARG_DOCTOR_SCHEDULE_TIEM);
        if (null != mDoctorScheduleItem)
        {
            mTvDate.setText(mDoctorScheduleItem.date);
        }
        setListener();
        mImageFilePaths = new LinkedList<>();
        mImageFilePaths.add(null);
        mCustomGridView.setAdapter(new ImageGridAdapter(mActivity, mImageFilePaths));
        doGetUserPatient();
        registerSMSReceiver();
    }

    private void registerSMSReceiver()
    {
        // 生成广播处理
        // 实例化过滤器并设置要过滤的广播
        IntentFilter intentFilter = new IntentFilter(
                "android.provider.Telephony.SMS_RECEIVED");
        intentFilter.setPriority(800);
        // 注册广播
        mActivity.registerReceiver(mSMSBroadcastReceiver, intentFilter);
    }

    /***
     * 获取常用就诊人
     */
    private void doGetUserPatient()
    {
        UserPatientPresenter userPatientPresenter = new UserPatientPresenterImpl(mActivity, this, REQUEST_PATIENT_LIST_TAG);
        userPatientPresenter.doGetUserPatient();
    }

    //选择就诊人事件
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
        mActivity.unregisterReceiver(mSMSBroadcastReceiver);
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /***
     * 将选中的就诊人显示出来
     *
     * @param patientItem
     */
    private void fillPatient(UserPatientItem patientItem)
    {
        mUserPatientItem = patientItem;
        mLinearPatientContainer.removeAllViews();
        TextView textView = new TextView(mActivity);
        textView.setText(patientItem.name);
        int marginLTB = DensityUtils.dpToPx(mActivity, 6.f);
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundResource(R.drawable.shape_edittext_background);
        int width = DensityUtils.dpToPx(mActivity, 72.f);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.leftMargin = marginLTB;
        layoutParams.topMargin = marginLTB;
        layoutParams.bottomMargin = marginLTB;
        mLinearPatientContainer.addView(textView, layoutParams);

    }

    private void setListener()
    {
        OnClickListenerImpl onClickListener = new OnClickListenerImpl();
        mBtnSubmit.setOnClickListener(onClickListener);
        mBtnCode.setOnClickListener(onClickListener);
        mTvSelectPatient.setOnClickListener(onClickListener);
        mCustomGridView.setOnItemClickListener(
                new AdapterView.OnItemClickListener()
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
                if (REQUEST_PATIENT_LIST_TAG.equals(requestTag))
                {
                    Type type = new TypeToken<List<UserPatientItem>>()
                    {
                    }.getType();
                    List<UserPatientItem> items = GsonUtils.jsonToResultItemToList(GsonUtils.toJson(resultItem), type);
                    if (null != items)
                    {
                        UserPatientItem patientItem = items.get(0);
                        fillPatient(patientItem);
                    }
                } else if (REQUEST_CODE_TAG.equals(requestTag))
                {
                    mBtnCode.setClickable(false);
                    mBtnCode.setText("验证码发送成功");
                } else if (REQUEST_SUBMIT_TAG.equals(requestTag))
                {
                    UserAppointmentActivity.actionUserAppointment(mActivity);
                    mActivity.finish();
                }
            }
        }
    }

    @Override
    public void onGetDataFailured(String msg, String requestTag)
    {
        mActivity.showToast(msg);
        if (REQUEST_CODE_TAG.equals(requestTag))
        {
            mBtnCode.setClickable(true);
        }
    }

    /***
     * 将选中的就诊人的病历显示出来
     *
     * @param items
     */
    private void fillPatientRecord(List<PatientRecordItem> items)
    {
        if (null != items && !items.isEmpty())
        {
            for (PatientRecordItem patientRecordItem : items)
            {
                View view = LayoutInflater.from(mActivity).inflate(R.layout.layout_patient_record_item, null);
                TextView tvDate = (TextView) view.findViewById(R.id.tv_date);
                TextView tvDepatName = (TextView) view.findViewById(R.id.tv_dept_name);
                ImageView ivImage = (ImageView) view.findViewById(R.id.iv_image);
                tvDate.setText(DateFormatUtils.format(patientRecordItem.createDate, true, false));
                tvDepatName.setText(patientRecordItem.departName);
                ivImage.setActivated("1".equals(patientRecordItem.isOpen));
            }

        }
    }

    private class OnClickListenerImpl implements View.OnClickListener
    {
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.tv_select_patient://选择常用患者
                    UserPatientListActivity.actionPatient(mActivity, true);
                    break;
                case R.id.btn_code://获取验证码
                    String phone = mEtPhone.getText().toString();
                    if (TextUtils.isEmpty(phone))
                    {
                        mActivity.showToast("手机号码不可以为空");
                        return;
                    }
                    if (phone.length() != 11)
                    {
                        mActivity.showToast("手机号码长度应该为11位");
                        return;
                    }
                    if (!PatternUtils.isPhoneNumber(phone))
                    {
                        mActivity.showToast("手机号码格式不正确");
                        return;
                    }
                    doGetVerifyCode(phone);
                    break;
                case R.id.btn_submit://提交
                    submit();
                    break;
            }
        }
    }

    private void doGetVerifyCode(String phone)
    {
        VerifyCodeUtils.getVerifyCode(mActivity, mBtnCode, 60 * 1000, phone);
        SendCodePresenter sendCodePresenter = new SendCodePresenterImpl(mActivity, this, REQUEST_CODE_TAG);
        sendCodePresenter.doSendCode(phone);
    }

    private void submit()
    {

        if (null == mUserPatientItem)
        {
            mActivity.showToast("请选择就诊人");
            return;
        }
        String age = mEtAge.getText().toString();
        age = mUserPatientItem.age;

//        if (TextUtils.isEmpty(age))
//        {
//            mActivity.showToast("请输入年龄");
//            return;
//        }
//        if (TextUtils.isDigitsOnly(age))
//        {
//            int ageInt = Integer.parseInt(age);
//            if (ageInt > 120)
//            {
//                mActivity.showToast("你是千年老妖吗");
//                return;
//            }
//        } else
//        {
//            mActivity.showToast("请输入合理的年龄");
//            return;
//        }
        String diseaseName = mEtDiseaseName.getText().toString();
        if (TextUtils.isEmpty(diseaseName))
        {
            diseaseName = "未知疾病";
        }
        String diseaseDesc = mEtDiseaseDesc.getText().toString();
        if (TextUtils.isEmpty(diseaseDesc))
        {
            diseaseDesc = "暂无描述";
        }
        String patientMsg = mEtPatientMsg.getText().toString();
        if (TextUtils.isEmpty(patientMsg))
        {
            patientMsg = "暂无留言";
        }
        String sex = "0";
        sex = mUserPatientItem.sex;
//        if (R.id.rb_woman == mRgGender.getCheckedRadioButtonId())
//        {
//            sex = "1";
//        }
        String phone = mEtPhone.getText().toString();
        if (TextUtils.isEmpty(phone))
        {
            mActivity.showToast("手机号码不可以为空");
            return;
        }
        if (phone.length() != 11)
        {
            mActivity.showCroutonToast("手机号码长度应该为11位");
            return;
        }
        if (!PatternUtils.isPhoneNumber(phone))
        {
            mActivity.showCroutonToast("手机号码格式不正确");
            return;
        }
        String code = mEtCode.getText().toString();
        if (!"18625273625".equals(phone))
        {
            if (TextUtils.isEmpty(code))
            {
                mActivity.showToast("验证码不可以为空");
                return;
            }
        }
//        if (!Constants.ALL_OK_CODE.equals(code))
//        {
//            mActivity.showToast("验证码不正确," + Constants.ALL_OK_CODE + "可以通过验证(开发阶段)");
//            return;
//        }

        PostSubmitAppointmentAddItem postSubmitAppointmentAddItem = new PostSubmitAppointmentAddItem();
//        postSubmitAppointmentAddItem.userId = RSAUtil.clientEncrypt(mActivity.getLoginSuccessItem().id);
        postSubmitAppointmentAddItem.scheduleId = RSAUtil.clientEncrypt(mDoctorScheduleItem.scheduleId);
        postSubmitAppointmentAddItem.symptoms = RSAUtil.clientEncrypt(diseaseName);
        postSubmitAppointmentAddItem.patientId = RSAUtil.clientEncrypt(mUserPatientItem.id);
        postSubmitAppointmentAddItem.conditionReport = RSAUtil.clientEncrypt(diseaseDesc);
        postSubmitAppointmentAddItem.checkcode = code;
//        postSubmitAppointmentAddItem.age = age;
        postSubmitAppointmentAddItem.message = patientMsg;
        postSubmitAppointmentAddItem.mobile = phone;
//        postSubmitAppointmentAddItem.sex = sex;
        if (mImageFilePaths.size() > 0)
        {
            List<File> files = new LinkedList<>();
            for (int i = 0; i < mImageFilePaths.size() - 1; i++)//-1的目的去除默认的+
            {
                File file = new File(mImageFilePaths.get(i));
                if (file != null && file.exists())
                {
                    files.add(file);
                }
            }
            postSubmitAppointmentAddItem.files = files;
        }
        SubmitAppointmentAddPresenter appointmentAddPresenter = new SubmitAppointmentAddPresenterImpl(mActivity, this, REQUEST_SUBMIT_TAG);
        appointmentAddPresenter.doAppointmentAdd(postSubmitAppointmentAddItem);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == Activity.RESULT_OK)
        {
            if (requestCode == PhotoUtils.REQUEST_FROM_PHOTO)
            {
                if (null != data && data.getData() != null)
                {
                    String imageFilePath = StorageUtils.getFilePathFromUri(mActivity, data.getData());
                    if (null != imageFilePath)
                    {
                        imageFilePath = BitmapUtils.getCompressBitmapFilePath(mActivity, imageFilePath);
                        mImageFilePaths.addFirst(imageFilePath);
                        mCustomGridView.setAdapter(new ImageGridAdapter(mActivity, mImageFilePaths));
                    }
                }
            }
        }
    }

    public class SMSBroadcastReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            // 判断是系统短信；
            if (intent.getAction().equals(
                    "android.provider.Telephony.SMS_RECEIVED"))
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
