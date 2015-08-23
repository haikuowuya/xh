package com.xinheng;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xinheng.base.BaseActivity;
import com.xinheng.mvp.model.UserDoctorItem;

/***
 * 医生评价界面
 */
public class DoctorEvaluationActivity extends BaseActivity
{

    private static final String TEXT_SERVICE = "服务：";
    private static final String TEXT_BLANK = "    ";
    private static final String TEXT_FEE = "费用：";
    private static final String TEXT_RESULT = "疗效：";

    public static void actionDoctorEvaluation(BaseActivity activity)
    {
        Intent intent = new Intent(activity, DoctorEvaluationActivity.class);
        activity.startActivity(intent);
    }

    private UserDoctorItem mUserDoctorItem;

    public static void actionDoctorEvaluation(BaseActivity activity, UserDoctorItem item)
    {
        Intent intent = new Intent(activity, DoctorEvaluationActivity.class);
        intent.putExtra(EXTRA_ITEM, item);
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

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mUserDoctorItem = getIntent().getSerializableExtra(EXTRA_ITEM) == null ? null : (UserDoctorItem) getIntent().getSerializableExtra(EXTRA_ITEM);
        setContentView(R.layout.activity_doctor_evaluation);
        initView();
        if (null != mUserDoctorItem)
        {
            mTvDoctorName.setText(mUserDoctorItem.doctName);
            String dept = mUserDoctorItem.hospital + "   " + mUserDoctorItem.department + "/" + mUserDoctorItem.technicalPost;
            mTvDoctorHospitalDept.setText(dept);
            String evaluation = TEXT_SERVICE + mUserDoctorItem.grade + TEXT_BLANK + TEXT_RESULT + mUserDoctorItem.grade + TEXT_BLANK + TEXT_FEE + mUserDoctorItem.grade;
            mTvDoctorEvaluation.setText(evaluation);


            mBtnSubmit.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    showCroutonToast("提交");
                }
            });
        }

    }

    private void initView()
    {
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
}
