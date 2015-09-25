package com.xinheng.fragment;

import android.graphics.Bitmap;
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

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xinheng.APIURL;
import com.xinheng.R;
import com.xinheng.adapter.subscribe.ImageGridAdapter;
import com.xinheng.base.AbsImageLoadingListener;
import com.xinheng.base.BaseFragment;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.appointment.PatientRecordItem;
import com.xinheng.mvp.model.doctor.DoctorScheduleItem;
import com.xinheng.mvp.model.user.UserAppointmentDetailItem;
import com.xinheng.mvp.model.user.UserPatientItem;
import com.xinheng.mvp.presenter.UserAppointmentDetailPresenter;
import com.xinheng.mvp.presenter.impl.UserAppointmentDetailPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.DateFormatUtils;
import com.xinheng.util.DensityUtils;
import com.xinheng.util.GsonUtils;
import com.xinheng.view.CircularImageView;
import com.xinheng.view.CustomGridView;

import java.util.List;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：我的预约详情界面
 */
public class UserAppointmentDetailFragment extends BaseFragment implements DataView
{

    public static final String ARG_USER_APPOINTMENT_ID = "user_appointment_id";


    public static UserAppointmentDetailFragment newInstance(String appointmentId)
    {
        UserAppointmentDetailFragment fragment = new UserAppointmentDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_USER_APPOINTMENT_ID, appointmentId);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * 就诊人
     */
    private UserPatientItem mUserPatientItem;
    /***
     * 预约id
     */
    private String mUserAppointmentId;

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
     * 就诊人姓名
     */
    private TextView mTvPatientName;
    /**
     * 就诊人是初诊还是复诊
     */
    private TextView mtvPatientVisit;

    /***
     * 病历布局
     */
    private LinearLayout mLinearPatientRecordContainer;
    /**
     * 显示病历图片的gridview
     */
    private CustomGridView mCustomGridView;
    /**
     * 医生的头像
     */
    private CircularImageView mCivPhoto;

    private  TextView mTvDoctMsg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_user_appointment_detail, null); //TODO 布局文件
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        mTvDepart = (TextView) view.findViewById(R.id.tv_dept_name);
        mCivPhoto = (CircularImageView) view.findViewById(R.id.iv_photo);
        mTvDoctorName = (TextView) view.findViewById(R.id.tv_doctor_name);
        mTvTechnicalPost = (TextView) view.findViewById(R.id.tv_technical_post);
        mTvAddress = (TextView) view.findViewById(R.id.tv_address);
        mTvFee = (TextView) view.findViewById(R.id.tv_fee);
        mTvType = (TextView) view.findViewById(R.id.tv_type);
        mTvDoctMsg = (TextView) view.findViewById(R.id.tv_doctor_msg);
        mTvDate = (TextView) view.findViewById(R.id.tv_date);
        mTvPatientName = (TextView) view.findViewById(R.id.tv_patient_name);
        mtvPatientVisit = (TextView) view.findViewById(R.id.tv_patient_visit);
        mLinearPatientRecordContainer = (LinearLayout) view.findViewById(R.id.linear_patient_record_container);
        mCustomGridView = (CustomGridView) view.findViewById(R.id.custom_gridview);
        mCustomGridView.setNumColumns(3);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mUserAppointmentId = getArguments().getString(ARG_USER_APPOINTMENT_ID);
        setListener();
        doGetData();
    }

    /**
     * 将选择的排班日期显示出来
     *
     * @param doctorScheduleItem
     */
    private void showSelectSchedule(DoctorScheduleItem doctorScheduleItem)
    {
        mTvDate.setText(DateFormatUtils.format(doctorScheduleItem.date, true, false));
        mTvAddress.setText(doctorScheduleItem.address);
        String fee = doctorScheduleItem.fee;
        if (TextUtils.isEmpty(fee))
        {
            fee = "0";
        }
        mTvFee.setText(fee);
        mTvType.setText(doctorScheduleItem.type);
    }

    private void setListener()
    {
        OnClickListenerImpl onClickListener = new OnClickListenerImpl();
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
            mActivity.showCroutonToast(resultItem.message);
            if (resultItem.success())
            {
                UserAppointmentDetailItem userAppointmentDetailItem = GsonUtils.jsonToClass(resultItem.properties.getAsJsonObject().toString(), UserAppointmentDetailItem.class);
                if (null != userAppointmentDetailItem)
                {
                    showDoctorInfo(userAppointmentDetailItem);
                    showSelectSchedule(userAppointmentDetailItem.schedule);
                    showPatientInfo(userAppointmentDetailItem.patient);
                    showPatientRecord(userAppointmentDetailItem.authrecord);

                    if (userAppointmentDetailItem.imgs != null && !userAppointmentDetailItem.imgs.isEmpty())
                    {
                        mCustomGridView.setAdapter(new ImageGridAdapter(mActivity, userAppointmentDetailItem.imgs));
                    }
                }
            }
        }
    }

    private void showPatientInfo(UserAppointmentDetailItem.PatientItem patient)
    {
        mTvPatientName.setText(patient.name);
        String visit = "初诊";
        if ("1".equals(patient.tag))
        {
            visit = "复诊";
        }
        mtvPatientVisit.setText(visit);
    }

    private void showDoctorInfo(UserAppointmentDetailItem userAppointmentDetailItem)
    {
        mTvDepart.setText(userAppointmentDetailItem.department + " / " + userAppointmentDetailItem.technicalPost);
        mTvDoctorName.setText(userAppointmentDetailItem.doctName);
        mTvTechnicalPost.setText(userAppointmentDetailItem.technicalPost);
        String photo = userAppointmentDetailItem.photo;
        if (!TextUtils.isEmpty(photo))
        {
            if (!photo.startsWith(APIURL.BASE_API_URL))
            {
                photo = APIURL.BASE_API_URL + photo;
            }
            ImageLoader.getInstance().loadImage(
                    photo, new AbsImageLoadingListener()
                    {
                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
                        {
                            if (null != loadedImage)
                            {
                                mCivPhoto.setImageBitmap(loadedImage);
                            }
                        }
                    });
        }
    }

    /***
     * 将选中的就诊人的病历显示出来
     *
     * @param items
     */
    private void showPatientRecord(List<PatientRecordItem> items)
    {
        mLinearPatientRecordContainer.removeAllViews();

        if (null != items && !items.isEmpty())
        {
            for (PatientRecordItem patientRecordItem : items)
            {
                View view = LayoutInflater.from(mActivity).inflate(R.layout.layout_patient_record_item, null);
                TextView tvDate = (TextView) view.findViewById(R.id.tv_date);
                TextView tvDepatName = (TextView) view.findViewById(R.id.tv_dept_name);
                ImageView ivImage = (ImageView) view.findViewById(R.id.iv_image);
                String dateTime = patientRecordItem.createDate;
                if(TextUtils.isEmpty(dateTime))
                {
                    dateTime = patientRecordItem.createTime;
                }
                tvDate.setText(DateFormatUtils.format(dateTime, true, false));
                tvDepatName.setText(patientRecordItem.departName);
                if ("1".equals(patientRecordItem.isOpen))
                {
                    ivImage.setImageResource(R.mipmap.ic_subscribe_patient_record_auth);
                }
                mLinearPatientRecordContainer.addView(view);
            }
        } else
        {
            TextView textView = new TextView(mActivity);
            int height = DensityUtils.dpToPx(mActivity, 44.f);
            textView.setGravity(Gravity.CENTER);
            textView.setText("暂无病历信息");
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
            mLinearPatientRecordContainer.addView(textView, layoutParams);

        }
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

            }
        }
    }

}
