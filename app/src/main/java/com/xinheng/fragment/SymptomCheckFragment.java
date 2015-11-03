package com.xinheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.xinheng.R;
import com.xinheng.base.BaseFragment;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.auto.BodyKV;
import com.xinheng.mvp.presenter.SymptomCheckPresenter;
import com.xinheng.mvp.presenter.impl.SymptomCheckPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.DensityUtils;
import com.xinheng.util.GsonUtils;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：症状测试Fragment界面
 */
public class SymptomCheckFragment extends BaseFragment implements DataView
{
    /***
     * 请求获取常用就诊人列表
     */
    public static final String REQUEST_DEPAET_CHECK_LIST_TAG = "request_depart_check_list";
    public static final String REQUEST_SYMPTOM_CHECK_FIRST_QUESTION_TAG = "sysmptom_check_first_question";
    public static final String REQUEST_SYMPTOM_CHECK_YES_TAG = "sysmptom_check_yes";
    public static final String REQUEST_SYMPTOM_CHECK_NO_TAG = "sysmptom_check_no";
    /***
     * 症状id
     */
    public static final String ARG_SYMPTOM = "symptom";

    public static SymptomCheckFragment newInstance(BodyKV.SymptomItem symptom)
    {
        SymptomCheckFragment fragment = new SymptomCheckFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_SYMPTOM, symptom);
        fragment.setArguments(bundle);
        return fragment;
    }

    /***
     * 症状
     */
    private BodyKV.SymptomItem mSymptomItem;

    /***
     * 症状自测提示
     */
    private TextView mTvSymptomHint;
    /***
     * 症状问题
     */
    private TextView mtvQuestion;
    private ScrollView mScrollView;
    private Button mBtnYes;
    private Button mBtnNo;
    private BodyKV.SymptomQuestion mSymptomQuestion;
    /***
     * 问题布局
     */
    private LinearLayout mLinearQuestionContainer;
    /***
     * 结果布局
     */
    private LinearLayout mLinearResultContainer;

    private TextView mTvResult;
    private LinearLayout mLinearDepartContainer;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_symptom_check, null); //TODO 布局文件
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        mTvSymptomHint = (TextView) view.findViewById(R.id.tv_symptom_hint);
        mBtnNo = (Button) view.findViewById(R.id.btn_no);
        mScrollView = (ScrollView) view.findViewById(R.id.sv_scrollview);
        mtvQuestion = (TextView) view.findViewById(R.id.tv_question);
        mBtnYes = (Button) view.findViewById(R.id.btn_yes);
        mTvResult = (TextView) view.findViewById(R.id.tv_result);
        mLinearDepartContainer = (LinearLayout) view.findViewById(R.id.linear_depart_container);
        mLinearQuestionContainer = (LinearLayout) view.findViewById(R.id.linear_symptom_question_container);
        mLinearResultContainer = (LinearLayout) view.findViewById(R.id.linear_symptom_result_container);
        mLinearQuestionContainer.setVisibility(View.VISIBLE);
        mLinearResultContainer.setVisibility(View.GONE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        if (null != getArguments())
        {
            mSymptomItem = getArguments().getSerializable(ARG_SYMPTOM) == null ? null : (BodyKV.SymptomItem) getArguments().getSerializable(ARG_SYMPTOM);
        }
        if (null != mSymptomItem)
        {
            mScrollView.setVisibility(View.GONE);
            mTvSymptomHint.setText(mSymptomItem.name + "导诊自测");
            doGetData();
        }
        setListener();
    }

    private void setListener()
    {
        OnClickListenerImpl onClickListener = new OnClickListenerImpl();
        mBtnNo.setOnClickListener(onClickListener);
        mBtnYes.setOnClickListener(onClickListener);
    }

    @Override
    protected void doGetData()
    {
        SymptomCheckPresenter symptomCheckPresenter = new SymptomCheckPresenterImpl(mActivity, this, REQUEST_SYMPTOM_CHECK_FIRST_QUESTION_TAG);
        symptomCheckPresenter.doGetSymptomCheckQuestion(mSymptomItem.id);
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
                mScrollView.setVisibility(View.VISIBLE);
                if (REQUEST_SYMPTOM_CHECK_FIRST_QUESTION_TAG.equals(requestTag))
                {
                    mSymptomQuestion = GsonUtils.jsonToClass(resultItem.properties.getAsJsonObject().toString(), BodyKV.SymptomQuestion.class);
                    if (null != mSymptomQuestion)
                    {
                        mtvQuestion.setText(mSymptomQuestion.content);
                    }
                }
                else if (REQUEST_SYMPTOM_CHECK_NO_TAG.equals(requestTag))
                {
                    BodyKV.SymptomNextQuestion symptomNextQuestion = GsonUtils.jsonToClass(resultItem.properties.getAsJsonObject().toString(), BodyKV.SymptomNextQuestion.class);
                    if (null != symptomNextQuestion && null != symptomNextQuestion.nextquestion)
                    {
                        mSymptomQuestion = symptomNextQuestion.nextquestion;
                        mtvQuestion.setText(mSymptomQuestion.content);
                    }
                }
                else if (REQUEST_SYMPTOM_CHECK_YES_TAG.equals(requestTag))
                {
                    BodyKV.SymptomResult symptomResult = GsonUtils.jsonToClass(resultItem.properties.getAsJsonObject().toString(), BodyKV.SymptomResult.class);
                    if (null != symptomResult && null != symptomResult.exit)
                    {
                        BodyKV.SymptomExitItem exitItem = symptomResult.exit;
                        mLinearResultContainer.setVisibility(View.VISIBLE);
                        mLinearQuestionContainer.setVisibility(View.GONE);
                        mTvSymptomHint.setText("自测结果");
                        mTvResult.setText(exitItem.result);
                        if (null != exitItem.depart && !exitItem.depart.isEmpty())
                        {
                            for (int i = 0; i < exitItem.depart.size(); i++)
                            {
                                TextView textView = new TextView(mActivity);
                                textView.setText(exitItem.depart.get(i).name);
                                textView.setBackgroundColor(0xFFF5E6CF);
                                textView.setTextColor(0xFF666666);
                                textView.setGravity(Gravity.CENTER);
                                int paddingLTRB = DensityUtils.dpToPx(mActivity, 4.f);
                                textView.setPadding(paddingLTRB, paddingLTRB, paddingLTRB, paddingLTRB);
                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                                layoutParams.leftMargin = paddingLTRB;
                                layoutParams.topMargin = paddingLTRB;
                                layoutParams.rightMargin = paddingLTRB * 2;
                                layoutParams.bottomMargin = paddingLTRB;
                                mLinearDepartContainer.addView(textView, layoutParams);
                            }
                        }
                    }
                }
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
            if (null != mSymptomQuestion)
            {
                switch (v.getId())
                {
                    case R.id.btn_yes://是
                        SymptomCheckPresenter symptomCheckPresenter = new SymptomCheckPresenterImpl(mActivity, SymptomCheckFragment.this, REQUEST_SYMPTOM_CHECK_YES_TAG);
                        symptomCheckPresenter.doGetSymptomCheckResultOrNext(mSymptomQuestion.flowId, "1");
                        break;
                    case R.id.btn_no://否
                        symptomCheckPresenter = new SymptomCheckPresenterImpl(mActivity, SymptomCheckFragment.this, REQUEST_SYMPTOM_CHECK_NO_TAG);
                        symptomCheckPresenter.doGetSymptomCheckResultOrNext(mSymptomQuestion.flowId, "0");
                        break;
                }
            }
        }
    }

}
