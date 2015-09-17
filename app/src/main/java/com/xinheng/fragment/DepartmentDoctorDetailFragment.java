package com.xinheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.xinheng.DepartmentDoctorDetailActivity;
import com.xinheng.R;
import com.xinheng.base.BaseFragment;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.depart.DepartDoctorItem;
import com.xinheng.mvp.model.doctor.DoctorDetailItem;
import com.xinheng.mvp.model.doctor.DoctorScheduleItem;
import com.xinheng.mvp.presenter.DoctorDetailPresenter;
import com.xinheng.mvp.presenter.impl.DoctorDetailPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：   科室医生详情
 */
public class DepartmentDoctorDetailFragment extends BaseFragment implements DataView
{
    private DepartDoctorItem mDepartDoctorItem;

    public static DepartmentDoctorDetailFragment newInstance(DepartDoctorItem doctorItem)
    {
        DepartmentDoctorDetailFragment fragment = new DepartmentDoctorDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DepartmentDoctorDetailActivity.EXTRA_DEPART_DOCTOR_ITEM, doctorItem);
        fragment.setArguments(bundle);
        return fragment;
    }

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

    /**
     * 排班信息
     */
    private LinearLayout mLinearScheduleContainer;
    /***
     * 医生擅长
     */
    private TextView mTvSkill;

    /***
     * 医生简介
     */
    private TextView mTvIntro;

    private ScrollView mScrollView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_depart_doctor_detail, null);      //TODO
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        mTvDepart = (TextView) view.findViewById(R.id.tv_dept_name);
        mTvDoctorName = (TextView) view.findViewById(R.id.tv_doctor_name);
        mTvIntro = (TextView) view.findViewById(R.id.tv_intro);
        mTvSkill = (TextView) view.findViewById(R.id.tv_skill);
        mTvTechnicalPost = (TextView) view.findViewById(R.id.tv_technical_post);
        mLinearScheduleContainer = (LinearLayout) view.findViewById(R.id.linear_schedule_container);
        mScrollView = (ScrollView) view.findViewById(R.id.sv_scrollview);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mDepartDoctorItem = getArguments().getSerializable(DepartmentDoctorDetailActivity.EXTRA_DEPART_DOCTOR_ITEM) == null ? null : (DepartDoctorItem) getArguments().getSerializable(DepartmentDoctorDetailActivity.EXTRA_DEPART_DOCTOR_ITEM);
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
        DoctorDetailPresenter doctorDetailPresenter = new DoctorDetailPresenterImpl(mActivity, this);
        doctorDetailPresenter.doGetDoctorDetail(mDepartDoctorItem.doctId);
        mScrollView.setVisibility(View.GONE);
    }

    @Override
    public String getFragmentTitle()
    {
        return getString(R.string.tv_fragment_user_account);
    }

    @Override
    public void onGetDataSuccess(ResultItem resultItem)
    {
        if (null != resultItem)
        {
            mScrollView.setVisibility(View.VISIBLE);
            mActivity.showCroutonToast(resultItem.message);
            if (resultItem.success())
            {
                Type type = new TypeToken<List<DoctorDetailItem>>()
                {
                }.getType();
                List<DoctorDetailItem> items = GsonUtils.jsonToResultItemToList(GsonUtils.toJson(resultItem), type);
                if (null != items && !items.isEmpty())
                {
                    DoctorDetailItem doctorDetailItem = items.get(0);
                }
            }
        }
    }

    @Override
    public void onGetDataFailured(String msg)
    {
        mActivity.showCroutonToast(msg);
        Type type = new TypeToken<List<DoctorDetailItem>>()
        {
        }.getType();
        List<DoctorDetailItem> items = GsonUtils.jsonToResultItemToList(DoctorDetailItem.DEBUG_SUCCESS, type);
        if (null != items && !items.isEmpty())
        {
            mScrollView.setVisibility(View.VISIBLE);
            DoctorDetailItem doctorDetailItem = items.get(0);
            mTvDepart.setText(doctorDetailItem.hospital + " / " + doctorDetailItem.department);
            mTvSkill.setText(doctorDetailItem.skill);
            mTvIntro.setText(doctorDetailItem.introduction);
            mTvDoctorName.setText(doctorDetailItem.doctName);
            mTvTechnicalPost.setText(doctorDetailItem.technicalPost);
            if (null != doctorDetailItem.schedule && !doctorDetailItem.schedule.isEmpty())
            {
                for (int i = 0; i < doctorDetailItem.schedule.size(); i++)
                {
                    DoctorScheduleItem doctorScheduleItem = doctorDetailItem.schedule.get(i);
                    View item = LayoutInflater.from(mActivity).inflate(R.layout.layout_doctor_schedule_item, null);
                    TextView tvDate = (TextView) item.findViewById(R.id.tv_date);
                    TextView tvTime = (TextView) item.findViewById(R.id.tv_time);
                    TextView tvStatus = (TextView) item.findViewById(R.id.tv_status);
                    tvDate.setText(doctorScheduleItem.date);
                    tvTime.setText(doctorScheduleItem.begintime + " - " + doctorScheduleItem.endtime);
                    mLinearScheduleContainer.addView(item);
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
            }
        }
    }

}
