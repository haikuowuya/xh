package com.xinheng.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.xinheng.R;
import com.xinheng.UserPatientListActivity;
import com.xinheng.adapter.doctor.ScheduleListAdapter;
import com.xinheng.base.BaseFragment;
import com.xinheng.eventbus.OnSelectPatientEvent;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.doctor.DoctorDetailItem;
import com.xinheng.mvp.model.doctor.DoctorScheduleItem;
import com.xinheng.mvp.model.subscribe.PatientRecordItem;
import com.xinheng.mvp.model.subscribe.PostSubmitSubscribeItem;
import com.xinheng.mvp.model.user.UserPatientItem;
import com.xinheng.mvp.presenter.PatientRecordPresenter;
import com.xinheng.mvp.presenter.SubmitSubscribePresenter;
import com.xinheng.mvp.presenter.UserPatientPresenter;
import com.xinheng.mvp.presenter.impl.PatientRecordPresenterImpl;
import com.xinheng.mvp.presenter.impl.SubmitSubscribePresenterImpl;
import com.xinheng.mvp.presenter.impl.UserPatientPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.DensityUtils;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.RSAUtil;

import java.lang.reflect.Type;
import java.util.LinkedList;
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
    private static final String ARG_DOCTOR_DETAIL_TIEM = "doctor_detail_item";
    public static final String ARG_POSITION = "position";
    /***
     * 请求获取常用就诊人列表
     */
    public static final String REQUEST_PATIENT_LIST_TAG = "request_patient_list";
    /***
     * 请求获取选择的常用就诊人的病历
     */
    public static final String REQUEST_PATIENT_RECORD_TAG = "request_patient_record";
    /***
     * 请求提交挂号
     */
    public static final String REQUEST_SUBMIT_TAG = "request_submit";

    public static SubscribeFragment newInstance(DoctorDetailItem doctorDetailItem, int postion)
    {
        SubscribeFragment fragment = new SubscribeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_DOCTOR_DETAIL_TIEM, doctorDetailItem);
        bundle.putInt(ARG_POSITION, postion);
        fragment.setArguments(bundle);
        return fragment;
    }

    /***
     * 医生排班信息
     */
    private List<DoctorScheduleItem> mDoctorScheduleItems;
    /***
     * 医生详情
     */
    private DoctorDetailItem mDoctorDetailItem;

    /**
     * 就诊人
     */
    private UserPatientItem mUserPatientItem;
    /***
     * 选中的排班位置
     */
    private int mPosition = 0;

    /***
     * 医生名称
     */
    private TextView mTvDoctorName;
    /***
     * 医生职称
     */
    private TextView mTvTechnicalPost;

    /***
     * 科室信息
     */
    private TextView mTvDepart;

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
    /***
     * 初诊
     */
    private TextView mTvFirstVisit;
    /**
     * 复诊
     */
    private TextView mTvSecondVisit;

    /***
     * 病历布局
     */
    private LinearLayout mLinearPatientRecordContainer;

    /***
     * 用户输入的内容
     */
    private EditText mEtContent;

    private Button mBtnSubmit;

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
        mTvDepart = (TextView) view.findViewById(R.id.tv_dept_name);
        mTvDoctorName = (TextView) view.findViewById(R.id.tv_doctor_name);
        mTvTechnicalPost = (TextView) view.findViewById(R.id.tv_technical_post);
        mTvAddress = (TextView) view.findViewById(R.id.tv_address);
        mTvFee = (TextView) view.findViewById(R.id.tv_fee);
        mTvType = (TextView) view.findViewById(R.id.tv_type);
        mTvDate = (TextView) view.findViewById(R.id.tv_date);
        mIvSelectDate = (ImageView) view.findViewById(R.id.iv_select_date);
        mTvSelectPatient = (TextView) view.findViewById(R.id.tv_select_patient);
        mTvFirstVisit = (TextView) view.findViewById(R.id.tv_first_visit);
        mTvSecondVisit = (TextView) view.findViewById(R.id.tv_second_visit);
        mBtnSubmit = (Button) view.findViewById(R.id.btn_submit);
        mLinearPatientRecordContainer = (LinearLayout) view.findViewById(R.id.linear_patient_record_container);
        mEtContent = (EditText) view.findViewById(R.id.et_content);
        mLinearPatientContainer = (LinearLayout) view.findViewById(R.id.linear_patient_container);
        mTvFirstVisit.setActivated(true);
        mTvSecondVisit.setActivated(false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
        mDoctorDetailItem = getArguments().getSerializable(ARG_DOCTOR_DETAIL_TIEM) == null ? null : (DoctorDetailItem) getArguments().getSerializable(ARG_DOCTOR_DETAIL_TIEM);
        mPosition = getArguments().getInt(ARG_POSITION);
        if (null != mDoctorDetailItem && null != mDoctorDetailItem.schedule)
        {
            mDoctorScheduleItems = mDoctorDetailItem.schedule;
            mTvDepart.setText(mDoctorDetailItem.department + " / " + mDoctorDetailItem.technicalPost);
            mTvDoctorName.setText(mDoctorDetailItem.doctName);
            mTvTechnicalPost.setText(mDoctorDetailItem.technicalPost);
        }
        if (null != mDoctorScheduleItems && !mDoctorScheduleItems.isEmpty() && mPosition < mDoctorScheduleItems.size())
        {
            DoctorScheduleItem doctorScheduleItem = mDoctorScheduleItems.get(mPosition);
            showSelectSchedule(doctorScheduleItem);
        }

        doGetUserPatient();
        setListener();
    }

    /**
     * 将选择的排班日期显示出来
     *
     * @param doctorScheduleItem
     */
    private void showSelectSchedule(DoctorScheduleItem doctorScheduleItem)
    {
        mTvDate.setText(doctorScheduleItem.date);
        mTvAddress.setText(doctorScheduleItem.address);
        mTvFee.setText(doctorScheduleItem.fee);
        mTvType.setText(doctorScheduleItem.type);
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
        UserPatientPresenter userPatientPresenter = new UserPatientPresenterImpl(mActivity, this, REQUEST_PATIENT_LIST_TAG);
        userPatientPresenter.doGetUserPatient();
    }

    /***
     * 根据选定的就诊人获取对应的病历信息
     */
    private void doGetPatientRecord()
    {
        PatientRecordPresenter patientRecordPresenter = new PatientRecordPresenterImpl(mActivity, this, REQUEST_PATIENT_RECORD_TAG);
        patientRecordPresenter.doGetPatientRecord(mUserPatientItem.id, mDoctorDetailItem.doctId);

    }

    private void setListener()
    {
        OnClickListenerImpl onClickListener = new OnClickListenerImpl();
        mTvSelectPatient.setOnClickListener(onClickListener);
        mIvSelectDate.setOnClickListener(onClickListener);
        mTvFirstVisit.setOnClickListener(onClickListener);
        mTvSecondVisit.setOnClickListener(onClickListener);
        mBtnSubmit.setOnClickListener(onClickListener);

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
                } else if (REQUEST_PATIENT_RECORD_TAG.equals(requestTag))
                {
                    Type type = new TypeToken<List<PatientRecordItem>>()
                    {
                    }.getType();
                    List<PatientRecordItem> items = GsonUtils.jsonToResultItemToList(GsonUtils.toJson(resultItem), type);
                    if (null != items)
                    {
                        fillPatientRecord(items);
                    }
                }
                else if(REQUEST_SUBMIT_TAG.equals(requestTag))
                {
                    mActivity.showCroutonToast(GsonUtils.toJson(resultItem));
                }
            }
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

        }
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
        doGetPatientRecord();
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
                    UserPatientListActivity.actionPatient(mActivity, true);
                    break;
                case R.id.iv_select_date://选择挂号日期
                    seleteDate();
                    break;

                case R.id.tv_first_visit:
                    mTvSecondVisit.setActivated(false);
                    mTvFirstVisit.setActivated(true);
                    break;
                case R.id.tv_second_visit:
                    mTvFirstVisit.setActivated(false);
                    mTvSecondVisit.setActivated(true);
                    break;
                case R.id.btn_submit://提交
                    submit();
                    break;
            }
        }
    }

    private void submit()
    {
        String content = mEtContent.getText().toString();
        if(TextUtils.isEmpty(content))
        {
            content = "我没病";
//            mActivity.showCroutonToast("症状自述不可以为空");
//            return;
        }
        PostSubmitSubscribeItem postSubmitSubscribeItem = new PostSubmitSubscribeItem();
        postSubmitSubscribeItem.userId = RSAUtil.clientEncrypt(mActivity.getLoginSuccessItem().id);
        postSubmitSubscribeItem.patientId =RSAUtil.clientEncrypt(mUserPatientItem.id);
        String    scheduleId  = RSAUtil.clientDecrypt(mDoctorScheduleItems.get(mPosition).scheduleId);
        if(TextUtils.isEmpty(scheduleId))
        {
            scheduleId = "110";
        }
        postSubmitSubscribeItem.scheduleId = scheduleId;
        postSubmitSubscribeItem.status = mTvFirstVisit.isActivated()?"0":"1";
        postSubmitSubscribeItem.conditionReport = RSAUtil.clientEncrypt(content);
        postSubmitSubscribeItem.symptoms =  RSAUtil.clientEncrypt(content);
        postSubmitSubscribeItem.bmrIds = new LinkedList<>();
        postSubmitSubscribeItem.auths = new LinkedList<>();
        SubmitSubscribePresenter submitSubscribePresenter = new SubmitSubscribePresenterImpl(mActivity, this,REQUEST_SUBMIT_TAG ) ;
        submitSubscribePresenter.doSubmitSubscribe(postSubmitSubscribeItem);


    }

    private void seleteDate()
    {

        final AlertDialog alertDialog = new AlertDialog.Builder(mActivity).setSingleChoiceItems(
                new ScheduleListAdapter(mActivity, mDoctorScheduleItems), mPosition, null)

                .create();
        alertDialog.getListView().setDivider(new ColorDrawable(0xFF999999));
        alertDialog.getListView().setDividerHeight(DensityUtils.dpToPx(mActivity, 0.5f));
        alertDialog.getListView().setFooterDividersEnabled(false);
        alertDialog.getListView().setOnItemClickListener(
                new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        alertDialog.dismiss();
                        mPosition = position;
                        showSelectSchedule(mDoctorScheduleItems.get(position));
                    }
                });
        alertDialog.show();

    }

}
