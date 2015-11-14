package com.xinheng.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xinheng.APIURL;
import com.xinheng.AppointmentActivity;
import com.xinheng.AppointmentAddActivity;
import com.xinheng.DepartmentDoctorDetailActivity;
import com.xinheng.OnLineCounselActivity;
import com.xinheng.R;
import com.xinheng.base.BaseFragment;
import com.xinheng.common.AbsImageLoadingListener;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.depart.DepartDoctorItem;
import com.xinheng.mvp.model.doctor.DoctorDetailItem;
import com.xinheng.mvp.model.doctor.DoctorScheduleItem;
import com.xinheng.mvp.presenter.AttentionPresenter;
import com.xinheng.mvp.presenter.DoctorDetailPresenter;
import com.xinheng.mvp.presenter.impl.AddAttentionPresenterImpl;
import com.xinheng.mvp.presenter.impl.DoctorDetailPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.view.CircularImageView;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：科室医生详情
 */
public class DepartmentDoctorDetailFragment extends BaseFragment implements DataView
{
    public static final String HAS_ATTENTION = "已关注";
    public static final String ATTENTION = "关注";
    /**
     * 添加关注的请求TAG
     */
    private static final String REQUEST_ADD_ATTENTION_TAG = "add_attention";
    /***
     * 取消关注的请求TAG
     */
    private static final String REQUEST_CANCEL_ATTENTION_TAG = "cancel_attention";
    private static final String REQUEST_GET_DOCTOR_DETAIL_TAG = "get_doctor_detail";

    public static DepartmentDoctorDetailFragment newInstance(DepartDoctorItem doctorItem)
    {
        DepartmentDoctorDetailFragment fragment = new DepartmentDoctorDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DepartmentDoctorDetailActivity.EXTRA_DEPART_DOCTOR_ITEM, doctorItem);
        fragment.setArguments(bundle);
        return fragment;
    }

    private DepartDoctorItem mDepartDoctorItem;
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

    /**
     * 添加关注
     */
    private TextView mTvAttention;

    /***
     * 在线咨询按钮
     */
    private Button mBtnOnlineCounsel;
    private ScrollView mScrollView;

    /***
     * 请求接口获取到的医生详情
     */
    private DoctorDetailItem mDoctorDetailItem;
    private CircularImageView mCivImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_depart_doctor_detail, null); //TODO 布局文件
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        mTvDepart = (TextView) view.findViewById(R.id.tv_dept_name);
        mTvDoctorName = (TextView) view.findViewById(R.id.tv_doctor_name);
        mTvTechnicalPost = (TextView) view.findViewById(R.id.tv_technical_post);
        mTvIntro = (TextView) view.findViewById(R.id.tv_intro);
        mTvAttention = (TextView) view.findViewById(R.id.tv_attention);
        mBtnOnlineCounsel = (Button) view.findViewById(R.id.btn_online_counsel);
        mTvSkill = (TextView) view.findViewById(R.id.tv_skill);
        mCivImage = (CircularImageView) view.findViewById(R.id.civ_image);
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
        mTvAttention.setOnClickListener(onClickListener);
        mBtnOnlineCounsel.setOnClickListener(onClickListener);
    }

    @Override
    protected void doGetData()
    {
        DoctorDetailPresenter doctorDetailPresenter = new DoctorDetailPresenterImpl(mActivity, this, REQUEST_GET_DOCTOR_DETAIL_TAG);
        doctorDetailPresenter.doGetDoctorDetail(mDepartDoctorItem.doctId);
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
//            mActivity.showCroutonToast(resultItem.message);
            if (resultItem.success())
            {
                if (REQUEST_GET_DOCTOR_DETAIL_TAG.equals(requestTag))
                {
                    mScrollView.setVisibility(View.VISIBLE);
                    DoctorDetailItem doctorDetailItem = GsonUtils.jsonToClass(resultItem.properties.getAsJsonObject().toString(), DoctorDetailItem.class);
                    if (null != doctorDetailItem)
                    {
                        mDoctorDetailItem = doctorDetailItem;
                        ShowDoctorDetailAndScheduleInfo(doctorDetailItem);
                    }
                }
                else if (REQUEST_ADD_ATTENTION_TAG.equals(requestTag))
                {
                    //添加关注成功
                    mActivity.showToast(resultItem.message);
                    mTvAttention.setText(HAS_ATTENTION);
                }
                else if (REQUEST_CANCEL_ATTENTION_TAG.equals(requestTag))
                {
                    mActivity.showToast(resultItem.message);
                    mTvAttention.setText(ATTENTION);
                }
            }
        }
    }

    @Override
    public void onGetDataFailured(String msg, String requestTag)
    {
        mActivity.showCroutonToast(msg);
    }

    /***
     * 显示获取的医生详情 ，以及对应的排班信息
     *
     * @param doctorDetailItem
     */
    private void ShowDoctorDetailAndScheduleInfo(final DoctorDetailItem doctorDetailItem)
    {
        String img = doctorDetailItem.img;
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
        if ("1".equals(mDoctorDetailItem.isAttention))
        {
            mTvAttention.setText(HAS_ATTENTION);
        }
        mTvDepart.setText(mDoctorDetailItem.department + " / " + mDoctorDetailItem.technicalPost);
        mTvSkill.setText(doctorDetailItem.skill);
        mTvIntro.setText(doctorDetailItem.introduction);
        mTvDoctorName.setText(doctorDetailItem.doctName);
        mTvTechnicalPost.setText(doctorDetailItem.technicalPost);
        if (null != doctorDetailItem.schedule && !doctorDetailItem.schedule.isEmpty())
        {
            for (int i = 0; i < doctorDetailItem.schedule.size(); i++)
            {
                final DoctorScheduleItem doctorScheduleItem = doctorDetailItem.schedule.get(i);
                final View item = LayoutInflater.from(mActivity).inflate(R.layout.layout_doctor_schedule_item, null);
                TextView tvDate = (TextView) item.findViewById(R.id.tv_date);
                TextView tvTime = (TextView) item.findViewById(R.id.tv_time);
                TextView tvStatus = (TextView) item.findViewById(R.id.tv_status);
                if (DoctorScheduleItem.STATUS_0.equals(doctorScheduleItem.status))
                {
                    tvStatus.setText("暂时无号");
                    tvStatus.setTextColor(0x88FF0000);
                }
                else if (DoctorScheduleItem.STATUS_1.equals(doctorScheduleItem.status))
                {
                    tvStatus.setText("预约挂号");
                }
                else if (DoctorScheduleItem.STATUS__1.equals(doctorScheduleItem.status))
                {
                    tvStatus.setText("预约已满");
                    tvStatus.setBackground(getResources().getDrawable(R.drawable.user_subscribe_evaluation_selector));
                }
                tvDate.setText(doctorScheduleItem.date);
                tvTime.setText(doctorScheduleItem.begintime + " - " + doctorScheduleItem.endtime);
                mLinearScheduleContainer.addView(item);
                final int finalI = i;

                //测试使用预约加号
//                if (i == doctorDetailItem.schedule.size() - 1)
//                {
//                    tvStatus.setText("预约已满");
//                    tvStatus.setBackground(getResources().getDrawable(R.drawable.user_subscribe_evaluation_selector));
//                    tvStatus.setOnClickListener(new View.OnClickListener()
//                    {
//                        @Override
//                        public void onClick(View v)
//                        {
//                            AppointmentAddActivity.actionAppointmentAdd(mActivity, doctorScheduleItem);
//                        }
//                    });
//                    return;
//                }

                tvStatus.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                if (DoctorScheduleItem.STATUS_0.equals(doctorScheduleItem.status))
                                {
                                    mActivity.showToast("暂时无号！");
                                }
                                else if (DoctorScheduleItem.STATUS_1.equals(doctorScheduleItem.status))
                                {
                                    AppointmentActivity.actionAppointment(mActivity, mDoctorDetailItem, finalI);
                                }
                                else if (DoctorScheduleItem.STATUS__1.equals(doctorScheduleItem.status))
                                {
                                    AppointmentAddActivity.actionAppointmentAdd(mActivity, doctorScheduleItem);
                                }
                            }
                        });
            }
        }
    }

    private class OnClickListenerImpl implements View.OnClickListener
    {
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.tv_attention://关注
                    attention();
                    break;
                case R.id.btn_online_counsel://在线咨询
                    onlineCounsel();
                    break;
            }
        }
    }

    /***
     * 在线咨询按钮点击事件
     */
    private void onlineCounsel()
    {
        OnLineCounselActivity.actionOnLineCounsel(mActivity, mDoctorDetailItem);

    }

    private void attention()
    {
        if (!HAS_ATTENTION.equals(mTvAttention.getText().toString()))
        {
            AttentionPresenter addAttentionPresenter = new AddAttentionPresenterImpl(mActivity, this, REQUEST_ADD_ATTENTION_TAG);
            addAttentionPresenter.doAddAttention(mDepartDoctorItem.doctId);
        }
        else if (!ATTENTION.equals(mTvAttention.getText().toString()))
        {
            AttentionPresenter addAttentionPresenter = new AddAttentionPresenterImpl(mActivity, this, REQUEST_CANCEL_ATTENTION_TAG);
            addAttentionPresenter.doCancelAttention(mDepartDoctorItem.doctId);
        }

    }

}
