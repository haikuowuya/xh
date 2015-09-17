package com.xinheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinheng.R;
import com.xinheng.base.BaseFragment;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.UserCounselItem;
import com.xinheng.mvp.model.UserCounselReplyItem;
import com.xinheng.mvp.presenter.UserCounselDetailPresenter;
import com.xinheng.mvp.presenter.impl.UserCounselDetailPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.DateFormatUtils;
import com.xinheng.util.GsonUtils;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：  我的咨询详情页面
 */
public class UserCounselDetailFragment extends BaseFragment implements DataView
{
    public static final String SERVER_ERROR_TEST_RESULT="{\"message\":\"获取信息成功！\",\"properties\":{\"createTime\":\"111111\",\"id\":\"1\",\"question\":\"感冒发热怎么办\",\"replys\":[{\"content\":\"感冒\",\"createTime\":\"1437726354000\",\"department\":\"男性皮肤科\",\"doctId\":\"1\",\"doctName\":\"tom\",\"hospital\":\"贵州省中医医院\",\"technicalPost\":\"主任医师\"},{\"content\":\"正常情况\",\"createTime\":\"1437725575000\",\"department\":\"男性皮肤科\",\"doctId\":\"1\",\"doctName\":\"tom\",\"hospital\":\"贵州省中医医院\",\"technicalPost\":\"主任医师\"}],\"title\":\"感冒\",\"total\":\"2\"},\"result\":\"1\"}";
    public static UserCounselDetailFragment newInstance()
    {
        UserCounselDetailFragment fragment = new UserCounselDetailFragment();
        return fragment;
    }

    /***
     * 咨询问题
     */
    private TextView mTvQuestion;
    /**
     * 咨询时间
     */
    private TextView mTvCounselTime;
    /**
     * 咨询描述
     */
    private TextView mTvCounselDesc;
    /***
     * 医生回答个数
     */
    private TextView mTvAnswerCount;
    private LinearLayout mLinearReplyContainer;

    private EditText mEtContent;
    private Button mBtnSubmit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_user_counsel_detail, null);      //TODO
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        mTvAnswerCount = (TextView) view.findViewById(R.id.tv_answer_count);
        mTvCounselDesc = (TextView) view.findViewById(R.id.tv_counsel_desc);
        mTvCounselTime = (TextView) view.findViewById(R.id.tv_counsel_time);
        mTvQuestion = (TextView) view.findViewById(R.id.tv_counsel_question);
        mLinearReplyContainer = (LinearLayout) view.findViewById(R.id.linear_doctor_answer_container);
        mEtContent = (EditText) view.findViewById(R.id.et_content);
        mBtnSubmit = (Button) view.findViewById(R.id.btn_submit);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        setListener();
        doGetData();
    }

    private void setListener()
    {
       OnClickListenerImpl onClickListener = new OnClickListenerImpl();
        mBtnSubmit.setOnClickListener(onClickListener);
    }

    @Override
    protected void doGetData()
    {
        UserCounselDetailPresenter userCounselDetailPresenter = new UserCounselDetailPresenterImpl(mActivity, this);
        userCounselDetailPresenter.doGetUserCounselDetail();
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
            mActivity.showCroutonToast(resultItem.message);
            if (resultItem.success())
            {

            }
        }
    }

    @Override
    public void onGetDataFailured(String msg)
    {
        mActivity.showCroutonToast(msg);
        ResultItem resultItem = GsonUtils.jsonToClass(SERVER_ERROR_TEST_RESULT, ResultItem.class);
        if(null != resultItem && resultItem.success())
        {
            UserCounselItem  userCounselItem = GsonUtils.jsonToClass(resultItem.properties.getAsJsonObject().toString(), UserCounselItem.class);
            if(null != userCounselItem)
            {
                 showContent(userCounselItem);
            }
        }
    }

    private void showContent(UserCounselItem userCounselItem)
    {
        mTvQuestion.setText(userCounselItem.title);
        mTvCounselTime.setText(DateFormatUtils.format(userCounselItem.createTime));
        mTvCounselDesc.setText(userCounselItem.question);
        int total = 0;
        if (TextUtils.isDigitsOnly(userCounselItem.total))
        {
            total = Integer.parseInt(userCounselItem.total);
        }
        mTvAnswerCount.setText("医生回答(" + total + ")");
        if (userCounselItem.replys != null && !userCounselItem.replys.isEmpty())
        {
            for(int i = 0;i < userCounselItem.replys.size();i++)
            {
                View itemView = LayoutInflater.from(mActivity).inflate(R.layout.layout_user_counsel_detail_reply_item,null);
                //医生回答时间
               TextView tvAnswerTime = (TextView) itemView.findViewById(R.id.tv_answer_time);
                //医生信息
                TextView tvDoctorInfo = (TextView) itemView.findViewById(R.id.tv_doctor_info);
                //医生回答内容
                TextView tvAnswerContent = (TextView) itemView.findViewById(R.id.tv_answer_content);
                TextView tvAddMsg = (TextView) itemView.findViewById(R.id.tv_add_msg);
                UserCounselReplyItem reply = userCounselItem.replys.get(i);
                String answerTime = DateFormatUtils.format(reply.createTime);
                tvAnswerTime.setText(answerTime);
                String doctorInfo = reply.doctName + " " + reply.hospital + "  " + reply.department + "/" + reply.technicalPost;
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(doctorInfo);
                ForegroundColorSpan span = new ForegroundColorSpan(0xFF2FAD68);
                spannableStringBuilder.setSpan(span, 0, reply.doctName.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                tvDoctorInfo.setText(spannableStringBuilder);
                tvAnswerContent.setText(reply.content);
                mLinearReplyContainer.addView(itemView);
                final int finalI = i;
                tvAddMsg.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        mActivity.showCroutonToast("追问:回答"+(1+ finalI));
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
                   case R.id.btn_submit://提交
                       submit();
                       break;
               }
        }

        private void submit()
        {
            mActivity.showCroutonToast("提交");
        }

    }

}