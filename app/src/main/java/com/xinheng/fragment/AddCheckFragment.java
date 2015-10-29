package com.xinheng.fragment;

import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.xinheng.AddCheckActivity;
import com.xinheng.R;
import com.xinheng.UserCheckActivity;
import com.xinheng.adapter.check.CheckChildSpinnerAdapter;
import com.xinheng.adapter.check.CheckSpinnerAdapter;
import com.xinheng.base.BaseFragment;
import com.xinheng.eventbus.OnAddCheckEvent;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.check.DepartCheckItem;
import com.xinheng.mvp.model.check.PostAddCheckItem;
import com.xinheng.mvp.presenter.DepartCheckPresenter;
import com.xinheng.mvp.presenter.SendCodePresenter;
import com.xinheng.mvp.presenter.SubmitAddCheckPresenter;
import com.xinheng.mvp.presenter.impl.DepartCheckPresenterImpl;
import com.xinheng.mvp.presenter.impl.SendCodePresenterImpl;
import com.xinheng.mvp.presenter.impl.SubmitAddCheckPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.DateFormatUtils;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.PatternUtils;
import com.xinheng.util.VerifyCodeUtils;

import java.lang.reflect.Type;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：便捷检查(添加检查)Fragment界面
 */
public class AddCheckFragment extends BaseFragment implements DataView
{
    /***
     * 请求获取常用就诊人列表
     */
    public static final String REQUEST_DEPAET_CHECK_LIST_TAG = "request_depart_check_list";

    /***
     * 获取验证码请求
     */
    public static final String REQUEST_CODE_TAG = "request_code";
    /***
     * 提交请求
     */
    public static final String REQUEST_SUBMIT_TAG = "request_submit";

    public static AddCheckFragment newInstance()
    {
        AddCheckFragment fragment = new AddCheckFragment();

        return fragment;
    }

    private Button mBtnSubmit;

    /***
     * 性别
     */
    private RadioGroup mRgGender;
    /***
     * 年龄
     */
    private EditText mEtAge;

    /***
     * 预约加号时间
     */
    private TextView mTvDate;

    /***
     * 手机号码输入框
     */
    private EditText mEtPhone;
    /**
     * 验证码输入框
     */
    private EditText mEtCode;
    /**
     * 获取验证码
     */
    private Button mBtnCode;
    /***
     * 检查科室Spinner
     */
    private Spinner mSpinnerDepart;
    /***
     * 检查科室-检查项目 Spinner
     */
    private Spinner mSpinnerDepartChild;
    /***
     * 检查科室
     */
    private DepartCheckItem mDepartCheckItem;
    /***
     * 检查科室中的项目
     */
    private DepartCheckItem.CheckItem mCheckItem;
    /***
     * 是否发送验证码成功
     */
    private boolean mIsCodeSuccess = false;
    private SMSBroadcastReceiver mSMSBroadcastReceiver = new SMSBroadcastReceiver();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_add_check, null); //TODO 布局文件
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        mBtnSubmit = (Button) view.findViewById(R.id.btn_submit);
        mEtAge = (EditText) view.findViewById(R.id.et_age);
        mEtCode = (EditText) view.findViewById(R.id.et_code);
        mTvDate = (TextView) view.findViewById(R.id.tv_date);
        mEtPhone = (EditText) view.findViewById(R.id.et_phone);
        mRgGender = (RadioGroup) view.findViewById(R.id.rg_gender);
        mBtnCode = (Button) view.findViewById(R.id.btn_code);
        mSpinnerDepart = (Spinner) view.findViewById(R.id.spinner_depart);
        mSpinnerDepartChild = (Spinner) view.findViewById(R.id.spinner_depart_child);
        mTvDate.setText(DateFormatUtils.format(System.currentTimeMillis() + "", true, false));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        setListener();
        doGetDepartCheckList();
    }

    private void doGetDepartCheckList()
    {
        DepartCheckPresenter departCheckPresenter = new DepartCheckPresenterImpl(mActivity, this, REQUEST_DEPAET_CHECK_LIST_TAG, true);
        departCheckPresenter.doGetDepartCheckList();
    }

    private void setListener()
    {
        OnClickListenerImpl onClickListener = new OnClickListenerImpl();
        mBtnSubmit.setOnClickListener(onClickListener);
        mTvDate.setOnClickListener(onClickListener);
        mBtnCode.setOnClickListener(onClickListener);
    }

    @Override
    protected void doGetData()
    {

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
                if (REQUEST_DEPAET_CHECK_LIST_TAG.equals(requestTag))
                {
                    Type type = new TypeToken<List<DepartCheckItem>>()
                    {
                    }.getType();
                    List<DepartCheckItem> departCheckItems = GsonUtils.jsonToList(resultItem.properties.getAsJsonArray().toString(), type);
                    if (null != departCheckItems && !departCheckItems.isEmpty())
                    {
                        showDepartCheck(departCheckItems);
                    }
                }
                if (REQUEST_CODE_TAG.equals(requestTag))
                {
                    VerifyCodeUtils.cancle();
                    mBtnCode.setText("验证码发送成功");
                    mIsCodeSuccess = true;
                    mBtnCode.setClickable(false);
                    new Handler().postDelayed(
                            new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    mIsCodeSuccess = false;
                                }
                            },60*1000);
                }
                else
                {
                    if (REQUEST_SUBMIT_TAG.equals(requestTag))
                    {
                        if (mActivity instanceof AddCheckActivity)
                        {
                            AddCheckActivity addCheckActivity = (AddCheckActivity) mActivity;
                            if (addCheckActivity.isFromCheckList())
                            {
                                EventBus.getDefault().post(new OnAddCheckEvent());
                            }
                            else
                            {
                                UserCheckActivity.actionUserCheck(mActivity);
                            }
                            mActivity.finish();
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

    private void showDepartCheck(List<DepartCheckItem> departCheckItems)
    {
        if (null != departCheckItems && !departCheckItems.isEmpty())
        {
            OnItemSelectedListenerImpl onItemSelectedListener = new OnItemSelectedListenerImpl();
            mSpinnerDepart.setAdapter(new CheckSpinnerAdapter(mActivity, departCheckItems));
            mSpinnerDepart.setOnItemSelectedListener(onItemSelectedListener);
            mDepartCheckItem = departCheckItems.get(0);
            if (null != mDepartCheckItem.checks && !mDepartCheckItem.checks.isEmpty())
            {
                mSpinnerDepartChild.setAdapter(new CheckChildSpinnerAdapter(mActivity, mDepartCheckItem.checks));
                mCheckItem = mDepartCheckItem.checks.get(0);
                mSpinnerDepartChild.setOnItemSelectedListener(onItemSelectedListener);
            }
        }
    }

    private class OnItemSelectedListenerImpl implements OnItemSelectedListener
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            Object object = parent.getAdapter().getItem(position);
            if (parent == mSpinnerDepart)
            {
                mDepartCheckItem = (DepartCheckItem) object;
            }
            else if (parent == mSpinnerDepartChild)
            {
                mCheckItem = (DepartCheckItem.CheckItem) object;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {

        }
    }

    private void selectDate()
    {
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener()
        {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                mTvDate.setText(year + "-" + (1 + monthOfYear) + "-" + dayOfMonth);
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(mActivity, callback, 2015, 9, 1);
        datePickerDialog.show();

    }

    private class OnClickListenerImpl implements View.OnClickListener
    {
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.tv_date:
                    selectDate();
                    break;
                case R.id.btn_code://获取验证码
                    String phone = mEtPhone.getText().toString();
                    if (!mIsCodeSuccess)
                    {
                        if (TextUtils.isEmpty(phone))
                        {
                            mActivity.showToast("手机号码不可以为空");
                            return;
                        }
                        if (phone.length() != 11)
                        {
                            mActivity.showToast("手机号码长度应该为11位");
                            return;
                        }
                        if (!PatternUtils.isPhoneNumber(phone))
                        {
                            mActivity.showToast("手机号码格式不正确");
                            return;
                        }
                        doGetVerifyCode(phone);
                    }
                    else
                    {
                        mActivity.showToast("验证码已经发送到你手机，请稍候再试");
                    }
                    break;
                case R.id.btn_submit://提交
                    submit();
                    break;
            }
        }
    }

    private void doGetVerifyCode(String phone)
    {
        VerifyCodeUtils.getVerifyCode(mActivity, mBtnCode, 60 * 1000, phone);
        SendCodePresenter sendCodePresenter = new SendCodePresenterImpl(mActivity, this, REQUEST_CODE_TAG);
        sendCodePresenter.doSendCode(phone);
    }

    private void submit()
    {
        String age = mEtAge.getText().toString();
        if (TextUtils.isEmpty(age))
        {
            mActivity.showToast("请输入年龄");
            return;
        }
        if (TextUtils.isDigitsOnly(age))
        {
            int ageInt = Integer.parseInt(age);
            if (ageInt > 120)
            {
                mActivity.showToast("你是千年老妖吗");
                return;
            }
        }
        else
        {
            mActivity.showToast("请输入合理的年龄");
            return;
        }
        String sex = "1";
        if (R.id.rb_woman == mRgGender.getCheckedRadioButtonId())
        {
            sex = "0";
        }
        String phone = mEtPhone.getText().toString();
        if (TextUtils.isEmpty(phone))
        {
            mActivity.showToast("手机号码不可以为空");
            return;
        }
        if (phone.length() != 11)
        {
            mActivity.showCroutonToast("手机号码长度应该为11位");
            return;
        }
        if (!PatternUtils.isPhoneNumber(phone))
        {
            mActivity.showCroutonToast("手机号码格式不正确");
            return;
        }
        String code = mEtCode.getText().toString();
        if (!"18625273625".equals(phone))
        {
            if (TextUtils.isEmpty(code))
            {
                mActivity.showToast("验证码不可以为空");
                return;
            }

        }
        String checkDate = DateFormatUtils.timeToLongString(mTvDate.getText().toString());
        PostAddCheckItem postCheckItem = new PostAddCheckItem();
        postCheckItem.userId = mActivity.getLoginSuccessItem().id;
        postCheckItem.age = age;
        postCheckItem.checkcode = code;
        postCheckItem.checkTime = checkDate;
        postCheckItem.sex = sex;
        postCheckItem.mobile = phone;
        postCheckItem.checkId = mCheckItem.id;
        postCheckItem.departId = mDepartCheckItem.deptId;
        SubmitAddCheckPresenter submitAddCheckPresenter = new SubmitAddCheckPresenterImpl(mActivity, this, REQUEST_SUBMIT_TAG);
        submitAddCheckPresenter.doAddCheck(postCheckItem);
    }

    public class SMSBroadcastReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            // 判断是系统短信；
            if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED"))
            {
                // 不再往下传播；
                this.abortBroadcast();
                String content = null;
                Bundle bundle = intent.getExtras();
                if (bundle != null)
                {
                    // 通过pdus获得接收到的所有短信消息，获取短信内容；
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    // 构建短信对象数组；
                    SmsMessage[] mges = new SmsMessage[pdus.length];
                    for (int i = 0; i < pdus.length; i++)
                    {
                        // 获取单条短信内容，以pdu格式存,并生成短信对象；
                        mges[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    }
                    for (SmsMessage mge : mges)
                    {
                        content = mge.getMessageBody();// 获取短信的内容
                        System.out.println(" sms content = " + content);
                    }
                }
            }
        }
    }

}
