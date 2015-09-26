package com.xinheng.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.xinheng.R;
import com.xinheng.adapter.subscribe.ImageGridAdapter;
import com.xinheng.base.BaseFragment;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.appointment.PatientRecordItem;
import com.xinheng.mvp.model.appointment.PostSubmitAppointmentAddItem;
import com.xinheng.mvp.model.doctor.DoctorScheduleItem;
import com.xinheng.mvp.presenter.SendCodePresenter;
import com.xinheng.mvp.presenter.SubmitAppointmentAddPresenter;
import com.xinheng.mvp.presenter.impl.SendCodePresenterImpl;
import com.xinheng.mvp.presenter.impl.SubmitAppointmentAddPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.Constants;
import com.xinheng.util.DateFormatUtils;
import com.xinheng.util.PatternUtils;
import com.xinheng.util.PhotoUtils;
import com.xinheng.util.RSAUtil;
import com.xinheng.util.StorageUtils;
import com.xinheng.util.VerifyCodeUtils;
import com.xinheng.view.CustomGridView;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

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
     * 获取验证码请求
     */
    public static final String REQUEST_CODE_TAG = "request_code";
    /***
     * 提交请求
     */
    public static final String REQUEST_SUBMIT_TAG = "request_submit";

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
        mCustomGridView = (CustomGridView) view.findViewById(R.id.custom_gridview);
        mCustomGridView.setNumColumns(3);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mDoctorScheduleItem = getArguments().getSerializable(ARG_DOCTOR_SCHEDULE_TIEM) == null ? null : (DoctorScheduleItem) getArguments().getSerializable(ARG_DOCTOR_SCHEDULE_TIEM);
        if (null != mDoctorScheduleItem)
        {
            mTvDate.setText(mDoctorScheduleItem.date);
        }
        setListener();
        mImageFilePaths = new LinkedList<>();
        mImageFilePaths.add(null);
        mCustomGridView.setAdapter(new ImageGridAdapter(mActivity, mImageFilePaths));
    }

    @Override
    public void onDestroy()
    {
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
            if (REQUEST_CODE_TAG.equals(requestTag))
            {
                System.out.println("info = " + resultItem.properties);
            }
        }
    }
    @Override
    public void onGetDataFailured(String msg, String requestTag)
    {
        mActivity.showCroutonToast(msg);
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
                if ("1".equals(patientRecordItem.isOpen))
                {
                    ivImage.setImageResource(R.mipmap.ic_subscribe_patient_record_auth);
                }
            }

        }
    }



    private class OnClickListenerImpl implements View.OnClickListener
    {
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.btn_code://获取验证码
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
        String age = mEtAge.getText().toString();
        if(TextUtils.isEmpty(age))
        {
            mActivity.showToast("请输入年龄");
            return;
        }
        if (TextUtils.isDigitsOnly(age))
        {
            int ageInt = Integer.parseInt(age);
            if (ageInt > 120)
            {
                mActivity.showToast("你是千年老妖吗");
                return;
            }
        }
        else
        {
            mActivity.showToast("请输入合理的年龄");
            return;
        }
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
        if (R.id.rb_woman == mRgGender.getCheckedRadioButtonId())
        {
            sex = "1";
        }

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
        if (!Constants.ALL_OK_CODE.equals(code))
        {
            mActivity.showToast("验证码不正确");
            return;
        }

        PostSubmitAppointmentAddItem postSubmitAppointmentAddItem = new PostSubmitAppointmentAddItem();
        postSubmitAppointmentAddItem.userId = RSAUtil.clientEncrypt(mActivity.getLoginSuccessItem().id);
        postSubmitAppointmentAddItem.scheduleId = RSAUtil.clientEncrypt(mDoctorScheduleItem.scheduleId);
        postSubmitAppointmentAddItem.symptoms = RSAUtil.clientEncrypt(diseaseName);
        postSubmitAppointmentAddItem.conditionReport = RSAUtil.clientEncrypt(diseaseDesc);
        postSubmitAppointmentAddItem.checkcode = code;
        postSubmitAppointmentAddItem.age = age;
        postSubmitAppointmentAddItem.message = patientMsg;
        postSubmitAppointmentAddItem.sex = sex;
        if (mImageFilePaths.size() > 1)
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
                        mImageFilePaths.addFirst(imageFilePath);
                        mCustomGridView.setAdapter(new ImageGridAdapter(mActivity, mImageFilePaths));

                    }
                }
            }
        }
    }
}
