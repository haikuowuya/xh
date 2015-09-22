package com.xinheng;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.xinheng.base.BaseActivity;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.user.UserDoctorDetailItem;
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
    private static final String TEXT_BLANK = "   ";
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

    /**
     * 服务编辑框
     */
    private EditText mEtService;
    /**
     * 费用编辑框
     */
    private EditText mEtFee;
    /**
     * 疗效编辑框
     */
    private  EditText mEtEffect;
    /**
     * 服务评分条
     */
    private RatingBar mRbService;
    /**
     * 费用评分条
     */
    private RatingBar mRbFee;
    /**
     * 疗效评分条
     */
    private  RatingBar mRbEffect;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_evaluation);   //TODO
        mDoctId = getIntent().getStringExtra(EXTRA_DOCT_ID);
        initView();
        if (!TextUtils.isEmpty(mDoctId))
        {
            DoctorEvaluationPresenter doctorEvaluationPresenter = new DoctorEvaluationPresenterImpl(mActivity, this);
            doctorEvaluationPresenter.doGetDoctorEvaluationDetail(mDoctId);
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
        mEtFee = (EditText) findViewById(R.id.et_fee);
        mEtService = (EditText) findViewById(R.id.et_service);
        mEtEffect = (EditText) findViewById(R.id.et_effect);
        mRbFee =(RatingBar) findViewById(R.id.rb_fee);
        mRbEffect = (RatingBar) findViewById(R.id.rb_effect);
        mRbService = (RatingBar) findViewById(R.id.rb_service);
    }

    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_doctor_evaluation);
    }

    @Override
    public void onGetDataSuccess(ResultItem resultItem,String requestTag)
    {
        if (null != resultItem)
        {
            showCroutonToast(resultItem.message);
            if (null == mUserDoctorDetailItem)
            {
                mUserDoctorDetailItem = GsonUtils.jsonToClass(resultItem.properties.getAsJsonObject().toString(), UserDoctorDetailItem.class);
                if (null != mUserDoctorDetailItem)
                {
                    mScrollView.setVisibility(View.VISIBLE);
                    mTvDoctorName.setText(mUserDoctorDetailItem.doctName);
                    String dept = mUserDoctorDetailItem.institution + "   " + mUserDoctorDetailItem.department + "/" + mUserDoctorDetailItem.technicalPost;
                    mTvDoctorHospitalDept.setText(dept);
                    String evaluation = TEXT_SERVICE + mUserDoctorDetailItem.serviceGrade + TEXT_BLANK + TEXT_RESULT + mUserDoctorDetailItem.effectGrade + TEXT_BLANK + TEXT_FEE + mUserDoctorDetailItem.feeGrade;
                    SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(evaluation);
                    ForegroundColorSpan foregroundColorSpan1 = new ForegroundColorSpan(0xFFFF5907);
                    ForegroundColorSpan foregroundColorSpan2 = new ForegroundColorSpan(0xFFFF5907);
                    ForegroundColorSpan foregroundColorSpan3 = new ForegroundColorSpan(0xFFFF5907);
                    int start1 = TEXT_SERVICE.length();
                     if(!TextUtils.isEmpty(mUserDoctorDetailItem.serviceGrade)
                             &&!TextUtils.isEmpty(mUserDoctorDetailItem.effectGrade)
                             &&!TextUtils.isEmpty(mUserDoctorDetailItem.feeGrade)
                             )
                     {
                         int end1 = start1 + mUserDoctorDetailItem.serviceGrade.length();
                         spannableStringBuilder.setSpan(foregroundColorSpan1, start1, end1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                         int start2 = (TEXT_SERVICE + mUserDoctorDetailItem.serviceGrade + TEXT_BLANK + TEXT_RESULT).length();
                         int end2 = start2 + mUserDoctorDetailItem.effectGrade.length();
                         spannableStringBuilder.setSpan(foregroundColorSpan2, start2, end2, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                         int start3 = (TEXT_SERVICE + mUserDoctorDetailItem.serviceGrade + TEXT_BLANK + TEXT_RESULT + mUserDoctorDetailItem.effectGrade + TEXT_BLANK + TEXT_FEE).length();
                         int end3 = start3 + mUserDoctorDetailItem.feeGrade.length();
                         spannableStringBuilder.setSpan(foregroundColorSpan3, start3, end3, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                         mTvDoctorEvaluation.setText(spannableStringBuilder);
                     }

                    mBtnSubmit.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
//                            showCroutonToast("提交");
                            DoctorEvaluationPresenter doctorEvaluationPresenter = new DoctorEvaluationPresenterImpl(mActivity, DoctorEvaluationActivity.this);
                            String serviceStr = mEtService.getText().toString();
                            String feeStr = mEtFee.getText().toString();
                            String  effectStr = mEtEffect.getText().toString();
                            String serviceNum = mRbService.getRating()*2+"";
                            String effectNum = mRbEffect.getRating() * 2+"";
                            String feeNum = mRbFee.getRating() * 2+"";


                            System.out.println("serviceNum = " + serviceNum + " effectNum = " + effectNum + " feeNum = " + feeNum);
                            mUserDoctorDetailItem.effectGrade = effectNum;
                            mUserDoctorDetailItem.feeGrade = feeNum;
                            mUserDoctorDetailItem.serviceGrade = serviceNum;

                            doctorEvaluationPresenter.doSubmitDoctorEvaluation(mUserDoctorDetailItem, serviceStr, effectStr, feeStr);
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
    public void onGetDataFailured(String msg,String requestTag)
    {
        showCroutonToast(msg);
    }
}





