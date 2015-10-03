package com.xinheng.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xinheng.APIURL;
import com.xinheng.R;
import com.xinheng.UserAppointmentActivity;
import com.xinheng.UserPatientListActivity;
import com.xinheng.adapter.subscribe.ImageGridAdapter;
import com.xinheng.adapter.subscribe.ScheduleListAdapter;
import com.xinheng.base.BaseFragment;
import com.xinheng.common.AbsImageLoadingListener;
import com.xinheng.eventbus.OnSelectPatientEvent;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.appointment.PatientRecordItem;
import com.xinheng.mvp.model.appointment.PostSubmitAppointmentItem;
import com.xinheng.mvp.model.doctor.DoctorDetailItem;
import com.xinheng.mvp.model.doctor.DoctorScheduleItem;
import com.xinheng.mvp.model.user.UserPatientItem;
import com.xinheng.mvp.presenter.PatientRecordPresenter;
import com.xinheng.mvp.presenter.SubmitAppointmentPresenter;
import com.xinheng.mvp.presenter.UserPatientPresenter;
import com.xinheng.mvp.presenter.impl.PatientRecordPresenterImpl;
import com.xinheng.mvp.presenter.impl.SubmitAppointmentPresenterImpl;
import com.xinheng.mvp.presenter.impl.UserPatientPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.BitmapUtils;
import com.xinheng.util.DateFormatUtils;
import com.xinheng.util.DensityUtils;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.PhotoUtils;
import com.xinheng.util.RSAUtil;
import com.xinheng.util.StorageUtils;
import com.xinheng.view.CircularImageView;
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
 * 说明：预约挂号Fragment界面
 */
public class AppointmentFragment extends BaseFragment implements DataView
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

    public static AppointmentFragment newInstance(DoctorDetailItem doctorDetailItem, int postion)
    {
        AppointmentFragment fragment = new AppointmentFragment();
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
     * 用户输入的内容,症状自述
     */
    private EditText mEtConditionReport;
    /***
     * 用户输入的内容,病情自述
     */
    private EditText mEtSymptoms;

    private Button mBtnSubmit;

    private CustomGridView mCustomGridView;

    private LinkedList<String> mImageFilePaths;

    private LinkedList<String> mAuths = new LinkedList<>();
    private LinkedList<String> mBmrIds = new LinkedList<>();

    private CircularImageView mCivImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_appointment, null); //TODO 布局文件
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
        mTvFirstVisit = (TextView) view.findViewById(R.id.tv_first_visit);
        mTvSecondVisit = (TextView) view.findViewById(R.id.tv_second_visit);
        mBtnSubmit = (Button) view.findViewById(R.id.btn_submit);
        mLinearPatientRecordContainer = (LinearLayout) view.findViewById(R.id.linear_patient_record_container);
        mEtConditionReport = (EditText) view.findViewById(R.id.et_conditionReport);
        mEtSymptoms = (EditText) view.findViewById(R.id.et_symptoms);
        mTvSelectPatient = (TextView) view.findViewById(R.id.tv_select_patient);
        mLinearPatientContainer = (LinearLayout) view.findViewById(R.id.linear_patient_container);
        mCustomGridView = (CustomGridView) view.findViewById(R.id.custom_gridview);
        mCustomGridView.setNumColumns(3);
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

        if (null != mDoctorDetailItem)
        {
            String img = mDoctorDetailItem.img;
            if (!TextUtils.isEmpty(img))
            {
                if (!img.startsWith(APIURL.BASE_API_URL))
                {
                    img = APIURL.BASE_API_URL + img;
                }
                ImageLoader.getInstance().loadImage(img, new AbsImageLoadingListener()
                        {
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
                            {
                                if (null != loadedImage)
                                {
                                    mCivImage.setImageBitmap(loadedImage);
                                }
                            }
                        });
            }
        } if (null != mDoctorDetailItem && null != mDoctorDetailItem.schedule)
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
        mImageFilePaths = new LinkedList<>();
        mImageFilePaths.add(null);
        mCustomGridView.setAdapter(new ImageGridAdapter(mActivity, mImageFilePaths));
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
        mLinearPatientRecordContainer.removeAllViews();
        PatientRecordPresenter patientRecordPresenter = new PatientRecordPresenterImpl(mActivity, this, REQUEST_PATIENT_RECORD_TAG, false);
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
            //  mActivity.showCroutonToast(resultItem.message);
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
                else if (REQUEST_PATIENT_RECORD_TAG.equals(requestTag))
                {
                    Type type = new TypeToken<List<PatientRecordItem>>()
                    {
                    }.getType();
                    List<PatientRecordItem> items = GsonUtils.jsonToResultItemToList(GsonUtils.toJson(resultItem), type);
                    if (null != items)
                    {
                        //测试阶段
                        if (items.isEmpty())
                        {
                            items = GsonUtils.jsonToResultItemToList(PatientRecordItem.DEBUG_SUCCESS, type);
                        }
                        fillPatientRecord(items);
                    }
                }
                else if (REQUEST_SUBMIT_TAG.equals(requestTag))
                {
                    mActivity.showToast(resultItem.message);
                    UserAppointmentActivity.actionUserAppointment(mActivity);
                    mActivity.finish();
                }
            }
            else
            {
                if (REQUEST_SUBMIT_TAG.equals(requestTag))
                {
                    mActivity.showToast(resultItem.message);
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
            for (int i = 0; i < items.size(); i++)
            {
                PatientRecordItem patientRecordItem = items.get(i);
                View view = LayoutInflater.from(mActivity).inflate(R.layout.layout_patient_record_item, null);
                TextView tvDate = (TextView) view.findViewById(R.id.tv_date);
                TextView tvDepatName = (TextView) view.findViewById(R.id.tv_dept_name);
                final ImageView ivImage = (ImageView) view.findViewById(R.id.iv_image);
                tvDate.setText(DateFormatUtils.format(patientRecordItem.createDate, true, false));
                tvDepatName.setText(patientRecordItem.departName);
                if ("1".equals(patientRecordItem.isOpen))
                {
                    ivImage.setActivated(true);
                }
                else
                {
                    ivImage.setActivated(false);
                }
                mAuths.add(i, ivImage.isActivated() ? "1" : "0");
                mBmrIds.add(i, RSAUtil.clientEncrypt(patientRecordItem.bmrId));
                final int finalI = i;
                ivImage.setOnClickListener(new View.OnClickListener()
                        {
                            public void onClick(View v)
                            {
                                ivImage.setActivated(!ivImage.isActivated());
                                System.out.println("finalI = " + finalI);
                                mAuths.remove(finalI);
                                mAuths.add(finalI, ivImage.isActivated() ? "1" : "0");

                            }
                        });
                mLinearPatientRecordContainer.addView(view);

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
        String conditionReport = mEtConditionReport.getText().toString();
        if (TextUtils.isEmpty(conditionReport))
        {
            conditionReport = "我没病";
        }
        String symptoms = mEtSymptoms.getText().toString();
        if (TextUtils.isEmpty(symptoms))
        {
            symptoms = "我没病";
        }
        PostSubmitAppointmentItem postSubmitSubscribeItem = new PostSubmitAppointmentItem();
        postSubmitSubscribeItem.userId = RSAUtil.clientEncrypt(mActivity.getLoginSuccessItem().id);
        String patientId = mUserPatientItem.id;
        System.out.println("patientId = " + patientId);
        postSubmitSubscribeItem.patientId = RSAUtil.clientEncrypt(patientId);
        String scheduleId = RSAUtil.clientEncrypt(mDoctorScheduleItems.get(mPosition).scheduleId);
        postSubmitSubscribeItem.scheduleId = scheduleId;
        postSubmitSubscribeItem.status = mTvFirstVisit.isActivated() ? "0" : "1";
        postSubmitSubscribeItem.conditionReport = RSAUtil.clientEncrypt(conditionReport);
        postSubmitSubscribeItem.symptoms = RSAUtil.clientEncrypt(symptoms);
        postSubmitSubscribeItem.bmrIds = mBmrIds;
        postSubmitSubscribeItem.auths = mAuths;
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
            postSubmitSubscribeItem.files = files;
        }
        SubmitAppointmentPresenter submitSubscribePresenter = new SubmitAppointmentPresenterImpl(mActivity, this, REQUEST_SUBMIT_TAG);
        submitSubscribePresenter.doSubmitSubscribe(postSubmitSubscribeItem);
    }

    private void seleteDate()
    {
        final AlertDialog alertDialog = new AlertDialog.Builder(mActivity).setSingleChoiceItems(new ScheduleListAdapter(mActivity, mDoctorScheduleItems), mPosition, null).create();
        TextView tvTitle = new TextView(mActivity);
        int height = DensityUtils.dpToPx(mActivity, 48.f);
        tvTitle.setText("选择专家就诊日期");
        tvTitle.setPadding(height / 4, height / 4, height / 4, height / 4);
        tvTitle.setGravity(Gravity.CENTER);
        tvTitle.setBackgroundColor(0xFF3399FF);
        tvTitle.setTextColor(0xFFFFFFFF);
        tvTitle.setLayoutParams(new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, height));
        alertDialog.setCustomTitle(tvTitle);
        View footerView = LayoutInflater.from(mActivity).inflate(R.layout.layout_cancle, null);
        TextView tvCancle = (TextView) footerView.findViewById(R.id.tv_cancle);
        tvCancle.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        alertDialog.dismiss();
                    }
                });
        alertDialog.getListView().addFooterView(footerView);
        alertDialog.getListView().setDivider(new ColorDrawable(0xFF999999));
        alertDialog.getListView().setDividerHeight(DensityUtils.dpToPx(mActivity, 0.5f));
        alertDialog.getListView().setFooterDividersEnabled(false);
        alertDialog.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        if (parent.getAdapter().getItemViewType(position) != AdapterView.ITEM_VIEW_TYPE_HEADER_OR_FOOTER)
                        {
                            alertDialog.dismiss();
                            mPosition = position;
                            showSelectSchedule(mDoctorScheduleItems.get(position));
                        }
                    }
                });
        alertDialog.show();
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
}
