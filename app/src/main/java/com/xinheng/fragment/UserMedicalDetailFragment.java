package com.xinheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.xinheng.R;
import com.xinheng.adapter.user.UserMedicalImageGridAdapter;
import com.xinheng.base.BaseFragment;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.user.UserMedicalDetailItem;
import com.xinheng.mvp.presenter.UserMedicalDetailPresenter;
import com.xinheng.mvp.presenter.impl.UserMedicalDetailPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.DateFormatUtils;
import com.xinheng.util.GsonUtils;
import com.xinheng.view.CustomGridView;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：我的病历详情
 */
public class UserMedicalDetailFragment extends BaseFragment implements DataView
{
    public static final String ARG_ID = "id";

    public static UserMedicalDetailFragment newInstance(String medicalId)
    {
        UserMedicalDetailFragment fragment = new UserMedicalDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_ID, medicalId);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String mUserMedicalId;

    private ScrollView mScrollView;

    /**
     * 患者姓名
     */
    private TextView mTvPatientName;
    /***
     * 出生日期
     */
    private TextView mTvBirthday;
    /***
     * 诊断时间
     */
    private TextView mTvSeeTime;
    /***
     * 患者性别
     */
    private TextView mTvPatientSex;

    /**
     * 病历编号
     */
    private TextView mTvMedicalNo;

    /**
     * 诊断记录
     */
    private TextView mTvRecord;

    private CustomGridView mCustomReportGridView;
    private CustomGridView mCustomPresGridView;
    private CustomGridView mCustomIllnessGridView;

    private TextView mTvReportImgStatus;
    private TextView mTvPresImgStatus;
    private TextView mTvIllnessImgStatus;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_user_medical_detail, null); //TODO 布局文件
        initView(view);
        return view;
    }

    private void initView(View view)
    {

        mTvBirthday = (TextView) view.findViewById(R.id.tv_birthday);
        mTvRecord = (TextView) view.findViewById(R.id.tv_record);
        mTvMedicalNo = (TextView) view.findViewById(R.id.tv_medical_no);
        mTvPatientName = (TextView) view.findViewById(R.id.tv_patient_name);
        mTvReportImgStatus = (TextView) view.findViewById(R.id.tv_reprot_img_status);
        mTvIllnessImgStatus = (TextView) view.findViewById(R.id.tv_illness_img_status);
        mTvPresImgStatus = (TextView) view.findViewById(R.id.tv_pres_img_status);
        mTvPatientSex = (TextView) view.findViewById(R.id.tv_patient_sex);
        mTvSeeTime = (TextView) view.findViewById(R.id.tv_see_time);
        mCustomReportGridView = (CustomGridView) view.findViewById(R.id.linear_grid_report_container).findViewById(R.id.custom_gridview);
        mCustomIllnessGridView = (CustomGridView) view.findViewById(R.id.linear_grid_illness_container).findViewById(R.id.custom_gridview);
        mCustomPresGridView = (CustomGridView) view.findViewById(R.id.linear_grid_pres_container).findViewById(R.id.custom_gridview);
        mScrollView = (ScrollView) view.findViewById(R.id.sv_scrollview);
        mCustomIllnessGridView.setNumColumns(3);
        mCustomPresGridView.setNumColumns(3);
        mCustomReportGridView.setNumColumns(3);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mUserMedicalId = getArguments().getString(ARG_ID);
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
        UserMedicalDetailPresenter userMedicalDetailPresenter = new UserMedicalDetailPresenterImpl(mActivity, this);
        userMedicalDetailPresenter.doGetUserMedicalDetail(mUserMedicalId);
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
                UserMedicalDetailItem userMedicalDetailItem = GsonUtils.jsonToClass(resultItem.properties.getAsJsonObject().toString(), UserMedicalDetailItem.class);
                if (null != userMedicalDetailItem)
                {
                    ShowUserMedicalDetail(userMedicalDetailItem);
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
     * 显示获取的病历详情 ，
     *
     * @param userMedicalDetailItem
     */
    private void ShowUserMedicalDetail(final UserMedicalDetailItem userMedicalDetailItem)
    {
        mScrollView.setVisibility(View.VISIBLE);
        if (null != userMedicalDetailItem.medicalrecord)
        {
            mTvMedicalNo.setText("病历编号：" + userMedicalDetailItem.medicalrecord.id);
            mTvRecord.setText(userMedicalDetailItem.medicalrecord.record);
            if (userMedicalDetailItem.medicalrecord.reportimgs != null && !userMedicalDetailItem.medicalrecord.reportimgs.isEmpty())
            {
                mTvReportImgStatus.setVisibility(View.GONE);
                mCustomReportGridView.setVisibility(View.VISIBLE);
                mCustomReportGridView.setAdapter(new UserMedicalImageGridAdapter(mActivity, userMedicalDetailItem.medicalrecord.reportimgs));
            }
            else
            {
                mTvReportImgStatus.setVisibility(View.VISIBLE);
                mCustomReportGridView.setVisibility(View.GONE);
            }

            //

            if (userMedicalDetailItem.medicalrecord.illnessimgs != null && !userMedicalDetailItem.medicalrecord.illnessimgs.isEmpty())
            {
                mTvIllnessImgStatus.setVisibility(View.GONE);
                mCustomIllnessGridView.setVisibility(View.VISIBLE);
                mCustomIllnessGridView.setAdapter(new UserMedicalImageGridAdapter(mActivity, userMedicalDetailItem.medicalrecord.illnessimgs));
            }
            else
            {
                mTvIllnessImgStatus.setVisibility(View.VISIBLE);
                mCustomIllnessGridView.setVisibility(View.GONE);
            }

            //

            if (userMedicalDetailItem.medicalrecord.prescimgs != null && !userMedicalDetailItem.medicalrecord.prescimgs.isEmpty())
            {
                mTvPresImgStatus.setVisibility(View.GONE);
                mCustomPresGridView.setVisibility(View.VISIBLE);
                mCustomPresGridView.setAdapter(new UserMedicalImageGridAdapter(mActivity, userMedicalDetailItem.medicalrecord.prescimgs));
            }
            else
            {
                mTvPresImgStatus.setVisibility(View.VISIBLE);
                mCustomPresGridView.setVisibility(View.GONE);
            }
        }
        mTvBirthday.setText("出生年月：" + DateFormatUtils.format(userMedicalDetailItem.birthday, true, false));
        mTvSeeTime.setText("诊断时间：" + DateFormatUtils.format(userMedicalDetailItem.seeDate, true, false));
        mTvPatientName.setText("患者姓名：" + userMedicalDetailItem.name);
        mTvPatientSex.setText("患者性别：" + ("0".equals(userMedicalDetailItem.sex) ? "男" : "女"));

    }

    private class OnClickListenerImpl implements View.OnClickListener
    {
        public void onClick(View v)
        {

        }
    }

}
