package com.xinheng;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.xinheng.base.BaseActivity;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.UserDoctorDetailItem;
import com.xinheng.mvp.presenter.DoctorEvaluationPresenter;
import com.xinheng.mvp.presenter.impl.DoctorEvaluationPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;

/***
 * 医生评价界面
 */
public class DoctorEvaluationActivity extends BaseActivity implements DataView
{
    private static final String TEXT_SERVICE = "服务：";
    private static final String TEXT_BLANK = "    ";
    private static final String TEXT_FEE = "费用：";
    private static final String TEXT_RESULT = "疗效：";
    private static final String EXTRA_DOCT_ID = "doctId";
    private UserDoctorDetailItem mUserDoctorDetailItem;
    private String mDoctId;

    public static void actionDoctorEvaluation(BaseActivity activity, String doctId)
    {
        Intent intent = new Intent(activity, DoctorEvaluationActivity.class);
        intent.putExtra(EXTRA_DOCT_ID, doctId);
        activity.startActivity(intent);
    }

    /***
     * 医生姓名
     */
    private TextView mTvDoctorName;
    /***
     * 医生的服务、费用、医疗评价
     */
    private TextView mTvDoctorEvaluation;

    /**
     * 医生所在的医院 ， 科室以及职称
     */
    private TextView mTvDoctorHospitalDept;

    /**
     * 提交按钮
     */
    private Button mBtnSubmit;

    private ScrollView mScrollView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_evaluation);
        mDoctId = getIntent().getStringExtra(EXTRA_DOCT_ID);
        initView();
        if (!TextUtils.isEmpty(mDoctId))
        {
            DoctorEvaluationPresenter doctorEvaluationPresenter = new DoctorEvaluationPresenterImpl(mActivity, this);
            doctorEvaluationPresenter.getDoctorEvaluationDetail(mDoctId);
            mScrollView.setVisibility(View.GONE);
        }
    }

    private void initView()
    {
        mScrollView = (ScrollView) findViewById(R.id.sv_scrollview);
        mTvDoctorEvaluation = (TextView) findViewById(R.id.tv_dector_evaluation);
        mTvDoctorHospitalDept = (TextView) findViewById(R.id.tv_doctor_hospital_dept);
        mTvDoctorName = (TextView) findViewById(R.id.tv_doctor_name);
        mBtnSubmit = (Button) findViewById(R.id.btn_submit);
    }

    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_doctor_evaluation);
    }

    @Override
    public void onGetDataSuccess(ResultItem resultItem)
    {
        if (null != resultItem)
        {
            showCroutonToast(resultItem.message);
            if(null == mUserDoctorDetailItem)
            {
                mUserDoctorDetailItem = GsonUtils.jsonToClass(resultItem.properties.getAsJsonObject().toString(), UserDoctorDetailItem.class);
                if (null != mUserDoctorDetailItem)
                {
                    mScrollView.setVisibility(View.VISIBLE);
                    mTvDoctorName.setText(mUserDoctorDetailItem.doctName);
                    String dept = mUserDoctorDetailItem.institution + "   " + mUserDoctorDetailItem.department + "/" + mUserDoctorDetailItem.technicalPost;
                    mTvDoctorHospitalDept.setText(dept);
                    String evaluation = TEXT_SERVICE + mUserDoctorDetailItem.serviceGrade + TEXT_BLANK + TEXT_RESULT + mUserDoctorDetailItem.effectGrade + TEXT_BLANK + TEXT_FEE + mUserDoctorDetailItem.feeGrade;
                    mTvDoctorEvaluation.setText(evaluation);
                    mBtnSubmit.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            showCroutonToast("提交");
                            DoctorEvaluationPresenter doctorEvaluationPresenter = new DoctorEvaluationPresenterImpl(mActivity, DoctorEvaluationActivity.this);
                            doctorEvaluationPresenter.doDoctorEvaluation(mUserDoctorDetailItem, "111","111","111");
                        }
                    });
                }
            }
            else
            {

            }
        }

    }

    @Override
    public void onGetDataFailured(String msg)
    {
        showCroutonToast(msg);
    }
}





