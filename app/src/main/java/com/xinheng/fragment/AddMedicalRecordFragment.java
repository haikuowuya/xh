package com.xinheng.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.xinheng.mvp.model.doctor.DoctorScheduleItem;
import com.xinheng.mvp.model.user.UserPatientItem;
import com.xinheng.mvp.presenter.UserPatientPresenter;
import com.xinheng.mvp.presenter.impl.UserPatientPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.BitmapUtils;
import com.xinheng.util.DateFormatUtils;
import com.xinheng.util.DensityUtils;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.PhotoUtils;
import com.xinheng.util.StorageUtils;
import com.xinheng.view.CustomGridView;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：添加病历Fragment界面
 */
public class AddMedicalRecordFragment extends BaseFragment implements DataView
{
    /***
     * 请求获取常用就诊人列表
     */
    public static final String REQUEST_PATIENT_LIST_TAG = "request_patient_list";

    /***
     * 提交请求
     */
    public static final String REQUEST_SUBMIT_TAG = "request_submit";
    private UserPatientItem mUserPatientItem;

    public static AddMedicalRecordFragment newInstance()
    {
        AddMedicalRecordFragment fragment = new AddMedicalRecordFragment();
        return fragment;
    }

    /***
     * 预约加号的排班信息
     */
    private DoctorScheduleItem mDoctorScheduleItem;
    private Button mBtnSubmit;
    /***
     * 上传疾病照片gridview
     */
    private CustomGridView mCustomGridView0;
    /**
     * 上传检测报告诈骗gridview
     */
    private CustomGridView mCustomGridView1;
    /**
     * 上传处方单照片gridview
     */
    private CustomGridView mCustomGridView2;
    private LinkedList<String> mImageFilePaths0;
    private LinkedList<String> mImageFilePaths1;
    private LinkedList<String> mImageFilePaths2;
    /***
     * 就诊时间
     */
    private TextView mTvDate;
    /**
     * 当前选择照片时的gridview标志
     */
    private int mFlag = 0;

    /***
     * 就诊人container
     */
    private LinearLayout mLinearPatientContainer;
    /***
     * 选择患者
     */
    private TextView mTvSelectPatient;

    /***
     * 医疗机构编辑框
     */
    private EditText mEtHospital;
    /***
     * 门诊科室编辑框
     */
    private EditText mEtDepart;
    /***
     * 主治医师编辑框
     */
    private EditText mEtDoctorName;

    private SMSBroadcastReceiver mSMSBroadcastReceiver = new SMSBroadcastReceiver();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_add_medical_record, null); //TODO 布局文件
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        mBtnSubmit = (Button) view.findViewById(R.id.btn_submit);
        mTvDate = (TextView) view.findViewById(R.id.tv_date);
        mEtDepart = (EditText) view.findViewById(R.id.et_depart);
        mEtDoctorName = (EditText) view.findViewById(R.id.et_doctor_name);
        mEtHospital = (EditText) view.findViewById(R.id.et_hospital);
        mTvSelectPatient = (TextView) view.findViewById(R.id.tv_select_patient);
        mLinearPatientContainer = (LinearLayout) view.findViewById(R.id.linear_patient_container);
        mCustomGridView0 = (CustomGridView) view.findViewById(R.id.linear_grid_disease_container).findViewById(R.id.custom_gridview);
        mCustomGridView1 = (CustomGridView) view.findViewById(R.id.linear_grid_report_container).findViewById(R.id.custom_gridview);
        mCustomGridView2 = (CustomGridView) view.findViewById(R.id.linear_grid_recipe_container).findViewById(R.id.custom_gridview);
        mCustomGridView0.setNumColumns(3);
        mCustomGridView1.setNumColumns(3);
        mCustomGridView2.setNumColumns(3);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
        if (null != mDoctorScheduleItem)
        {
            mTvDate.setText(mDoctorScheduleItem.date);
        }
        setListener();
        mImageFilePaths0 = new LinkedList<>();
        mImageFilePaths1 = new LinkedList<>();
        mImageFilePaths2 = new LinkedList<>();
        mImageFilePaths0.add(null);
        mImageFilePaths1.add(null);
        mImageFilePaths2.add(null);
        mCustomGridView0.setAdapter(new ImageGridAdapter(mActivity, mImageFilePaths0));
        mCustomGridView1.setAdapter(new ImageGridAdapter(mActivity, mImageFilePaths1));
        mCustomGridView2.setAdapter(new ImageGridAdapter(mActivity, mImageFilePaths2));
        doGetUserPatient();
        registerSMSReceiver();
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
        mTvDate.setOnClickListener(onClickListener);
        mTvSelectPatient.setOnClickListener(onClickListener);
        OnItemClickListenerImpl onItemClickListener = new OnItemClickListenerImpl();
        mCustomGridView0.setOnItemClickListener(onItemClickListener);
        mCustomGridView1.setOnItemClickListener(onItemClickListener);
        mCustomGridView2.setOnItemClickListener(onItemClickListener);

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
                }
                else if (REQUEST_SUBMIT_TAG.equals(requestTag))
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
                case R.id.tv_date://选择日期
                    selectDate();
                    break;
                case R.id.tv_select_patient://选择常用患者
                    UserPatientListActivity.actionPatient(mActivity, true);
                    break;
                case R.id.btn_submit://提交
                    submit();
                    break;
            }
        }
    }

    private void selectDate()
    {
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener()
        {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                mTvDate.setText(year + "-" + (1 + monthOfYear) + "-" + dayOfMonth);
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(mActivity, callback, 2015, 9, 1);
        datePickerDialog.show();

    }

    private void submit()
    {
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
                     //   mActivity.showToast(" mFlag = " + mFlag);
                        if (mFlag == 0)
                        {
                            mImageFilePaths0.addFirst(imageFilePath);
                        }
                        else if (mFlag == 1)
                        {
                            mImageFilePaths1.addFirst(imageFilePath);
                        }
                        else if (mFlag == 2)
                        {
                            mImageFilePaths2.addFirst(imageFilePath);
                        }
                        mCustomGridView0.setAdapter(new ImageGridAdapter(mActivity, mImageFilePaths0));
                        mCustomGridView1.setAdapter(new ImageGridAdapter(mActivity, mImageFilePaths1));
                        mCustomGridView2.setAdapter(new ImageGridAdapter(mActivity, mImageFilePaths2));
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

    private class OnItemClickListenerImpl implements AdapterView.OnItemClickListener
    {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            if (parent == mCustomGridView0)
            {
                mFlag = 0;
            }
            else if (parent == mCustomGridView1)
            {
                mFlag = 1;
            }
            else if (parent == mCustomGridView2)
            {
                mFlag = 2;
            }
            String str = parent.getAdapter().getItem(position) == null ? null : parent.getAdapter().getItem(position).toString();
            if (TextUtils.isEmpty(str))
            {
                PhotoUtils.showSelectDialog(mActivity);
            }
        }
    }

}
