package com.xinheng.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.xinheng.R;
import com.xinheng.UserPatientListActivity;
import com.xinheng.adapter.subscribe.ImageGridAdapter;
import com.xinheng.base.BaseFragment;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.user.PostAddRecipeItem;
import com.xinheng.mvp.presenter.SubmitAddRecipePresenter;
import com.xinheng.mvp.presenter.impl.SubmitAddRecipePresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.BitmapUtils;
import com.xinheng.util.DateFormatUtils;
import com.xinheng.util.PhotoUtils;
import com.xinheng.util.RSAUtil;
import com.xinheng.util.StorageUtils;
import com.xinheng.view.CustomGridView;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：添加处方Fragment界面
 */
public class AddRecipeFragment extends BaseFragment implements DataView
{

    /***
     * 提交请求
     */
    public static final String REQUEST_SUBMIT_TAG = "request_submit";

    public static AddRecipeFragment newInstance()
    {
        AddRecipeFragment fragment = new AddRecipeFragment();
        return fragment;
    }

    private Button mBtnSubmit;
    /***
     * 上传疾病照片gridview
     */
    private CustomGridView mCustomGridView;
    private LinkedList<String> mImageFilePaths;
    /***
     * 就诊时间
     */
    private TextView mTvDate;
    /***
     * 医疗机构编辑框
     */
    private EditText mEtHospital;
    /***
     * 处方名称
     */
    private EditText mEtRecipeName;
    /***
     * 是否用药提醒
     */
    private CheckBox mCbCheck;

    private SMSBroadcastReceiver mSMSBroadcastReceiver = new SMSBroadcastReceiver();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_add_recipe, null); //TODO 布局文件
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        mBtnSubmit = (Button) view.findViewById(R.id.btn_submit);
        mTvDate = (TextView) view.findViewById(R.id.tv_date);
        mEtHospital = (EditText) view.findViewById(R.id.et_hospital);
        mEtRecipeName = (EditText) view.findViewById(R.id.et_recipe_name);
        mCbCheck = (CheckBox) view.findViewById(R.id.cb_check);
        mCustomGridView = (CustomGridView) view.findViewById(R.id.linear_grid_disease_container).findViewById(R.id.custom_gridview);
        mCustomGridView.setNumColumns(3);
        mTvDate.setText(DateFormatUtils.format(System.currentTimeMillis() + "", true, false));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        setListener();
        mImageFilePaths = new LinkedList<>();
        mImageFilePaths.add(null);
        mCustomGridView.setAdapter(new ImageGridAdapter(mActivity, mImageFilePaths));
    }

    private void registerSMSReceiver()
    {
        // 生成广播处理
        // 实例化过滤器并设置要过滤的广播
        IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        intentFilter.setPriority(800);
        // 注册广播
        mActivity.registerReceiver(mSMSBroadcastReceiver, intentFilter);
    }

    private void setListener()
    {
        OnClickListenerImpl onClickListener = new OnClickListenerImpl();
        mBtnSubmit.setOnClickListener(onClickListener);
        mTvDate.setOnClickListener(onClickListener);
        OnItemClickListenerImpl onItemClickListener = new OnItemClickListenerImpl();
        mCustomGridView.setOnItemClickListener(onItemClickListener);
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
                if (REQUEST_SUBMIT_TAG.equals(requestTag))
                {
                    mActivity.finish();
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
            switch (v.getId())
            {
                case R.id.tv_date://选择日期
                    selectDate();
                    break;
                case R.id.tv_select_patient://选择常用患者
                    UserPatientListActivity.actionPatient(mActivity, true);
                    break;
                case R.id.btn_submit://提交
                    submit();
                    break;
            }
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

    private void submit()
    {

        String hospital = mEtHospital.getText().toString();
        if (TextUtils.isEmpty(hospital))
        {
            mActivity.showToast("请填写医疗机构");
            return;
        }
        String recipeName = mEtRecipeName.getText().toString();
        if (TextUtils.isEmpty(recipeName))
        {
            mActivity.showToast("请填写处方名称");
            return;
        }
        String checkDate = DateFormatUtils.timeToLongString(mTvDate.getText().toString());
        PostAddRecipeItem item = new PostAddRecipeItem();
        if (mImageFilePaths.size() > 0)
        {
            List<File> files = new LinkedList<>();
            for (int i = 0; i < mImageFilePaths.size() - 1; i++)//-1的目的去除默认的+
            {
                File file = new File(mImageFilePaths.get(i));
                if (file != null && file.exists())
                {
                    files.add(file);
                }
            }
            item.files = files;
        }
        item.prescTime = checkDate;
        item.name = recipeName;
        item.institution = hospital;
        item.userId = RSAUtil.clientEncrypt(mActivity.getLoginSuccessItem().id);
        SubmitAddRecipePresenter submitAddRecipePresenter = new SubmitAddRecipePresenterImpl(mActivity, this, REQUEST_SUBMIT_TAG);
        submitAddRecipePresenter.doAddRecipe(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == Activity.RESULT_OK)
        {
            if (requestCode == PhotoUtils.REQUEST_FROM_PHOTO)
            {
                if (null != data && data.getData() != null)
                {
                    String imageFilePath = StorageUtils.getFilePathFromUri(mActivity, data.getData());
                    if (null != imageFilePath)
                    {
                        imageFilePath = BitmapUtils.getCompressBitmapFilePath(mActivity, imageFilePath);
                        mImageFilePaths.addFirst(imageFilePath);
                        mCustomGridView.setAdapter(new ImageGridAdapter(mActivity, mImageFilePaths));
                    }
                }
            }
        }
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

    private class OnItemClickListenerImpl implements AdapterView.OnItemClickListener
    {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            String str = parent.getAdapter().getItem(position) == null ? null : parent.getAdapter().getItem(position).toString();
            if (TextUtils.isEmpty(str))
            {
                PhotoUtils.showSelectDialog(mActivity);
            }
        }
    }

}
