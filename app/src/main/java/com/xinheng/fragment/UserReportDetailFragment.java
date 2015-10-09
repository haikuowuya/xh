package com.xinheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.xinheng.R;
import com.xinheng.base.BaseFragment;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.user.UserReportDetailItem;
import com.xinheng.mvp.presenter.UserReportDetailPresenter;
import com.xinheng.mvp.presenter.impl.UserReportDetailPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.DateFormatUtils;
import com.xinheng.util.GsonUtils;
import com.xinheng.view.CustomGridView;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：我的报告详情
 */
public class UserReportDetailFragment extends BaseFragment implements DataView
{
    public static final String ARG_ID = "id";

    public static UserReportDetailFragment newInstance(String reportId)
    {
        UserReportDetailFragment fragment = new UserReportDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_ID, reportId);
        fragment.setArguments(bundle);
        return fragment;
    }

    /***
     * 报告id
     */
    private String mUserReportId;

    private ScrollView mScrollView;

    /**
     * 报告名称
     */
    private TextView mTvReportName;
    /***
     *  日期
     */
    private TextView mTvReportTime;
    /***
     * 类型
     */
    private TextView mTvReportType;
    /***
     * 医院
     */
    private TextView mTvHospitalName;

    private TextView mTvReportImgStatus;

    private CustomGridView mCustomGridView;

    private LinearLayout mLinearReportPatientDoctorContainer;
    private  TextView mTvReportPatient;
    private  TextView mTvReportPerson;
    private  TextView mTvReportDate;
    private TextView mTvReportDoctor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_user_report_detail, null); //TODO 布局文件
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        mTvReportTime = (TextView) view.findViewById(R.id.tv_report_time);
        mTvReportName = (TextView) view.findViewById(R.id.tv_report_name);
        mTvReportType = (TextView) view.findViewById(R.id.tv_report_type);
        mTvHospitalName = (TextView) view.findViewById(R.id.tv_hospital_name);
        mScrollView = (ScrollView) view.findViewById(R.id.sv_scrollview);
        mCustomGridView = (CustomGridView) view.findViewById(R.id.custom_gridview);
        mTvReportImgStatus = (TextView) view.findViewById(R.id.tv_report_img_status);
        mTvReportDate = (TextView) view.findViewById(R.id.tv_report_date);
        mTvReportDoctor = (TextView) view.findViewById(R.id.tv_report_doctor);
        mTvReportPatient = (TextView) view.findViewById(R.id.tv_report_patient);
        mTvReportPerson = (TextView) view.findViewById(R.id.tv_report_person);
        mLinearReportPatientDoctorContainer = (LinearLayout) view.findViewById(R.id.linear_report_patient_doctor_container);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mUserReportId = getArguments().getString(ARG_ID);
        doGetData();
        setListener();
    }

    private void setListener()
    {
        OnClickListenerImpl onClickListener = new OnClickListenerImpl();
    }

    @Override
    protected void doGetData()
    {
        UserReportDetailPresenter userReportDetailPresenter = new UserReportDetailPresenterImpl(mActivity, this);
        userReportDetailPresenter.doGetUserReportDetail(mUserReportId);
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

                UserReportDetailItem userReportDetailItem = GsonUtils.jsonToClass(resultItem.properties.getAsJsonObject().toString(), UserReportDetailItem.class);
                if (null != userReportDetailItem)
                {
                    showUserReportInfo(userReportDetailItem);
                }
            }
        }
    }

    private void showUserReportInfo(UserReportDetailItem userReportDetailItem)
    {
        if (null != userReportDetailItem)
        {
            mScrollView.setVisibility(View.VISIBLE);
            mTvReportName.setText("名称：" + userReportDetailItem.name);
            mTvReportTime.setText("日期：" + DateFormatUtils.format(userReportDetailItem.checkTime, true, false));
            String type = "用户上传";
            if (UserReportDetailItem.TYPE_0.equals(userReportDetailItem.type))
            {
                type = "医院同步";
                mLinearReportPatientDoctorContainer.setVisibility(View.VISIBLE);
                mTvReportPerson.setText("送检员：" + userReportDetailItem.checkPersonName);
                mTvReportPatient.setText("患者姓名：" + userReportDetailItem.patient);
                mTvReportDoctor.setText("送检医生："+ userReportDetailItem.doctor);
                mTvReportDate.setText("送检日期：" + DateFormatUtils.format(userReportDetailItem.reportDate,true,false));
            }
            mTvReportType.setText("类型：" + type);
            mTvHospitalName.setText("医院：" + userReportDetailItem.institution);
            if(userReportDetailItem.reportimgs != null && !userReportDetailItem.reportimgs.isEmpty())
            {
                mTvReportImgStatus.setVisibility(View.GONE);
            }
            else
            {
                mTvReportImgStatus.setVisibility(View.VISIBLE);
            }
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

    private void pay()
    {
        mActivity.showToast("立即支付");
    }

}
