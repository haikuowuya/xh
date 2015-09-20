package com.xinheng.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.xinheng.R;
import com.xinheng.base.BaseFragment;
import com.xinheng.eventbus.OnAddPatientItemEvent;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.patient.PostAddPatientItem;
import com.xinheng.mvp.model.user.UserPatientItem;
import com.xinheng.mvp.presenter.AddPatientPresenter;
import com.xinheng.mvp.presenter.impl.AddPatientPresenterImpl;
import com.xinheng.mvp.view.DataView;

import de.greenrobot.event.EventBus;

/**
 * 作者：  libo
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：  添加就诊人页面
 */
public class AddPatientFragment extends BaseFragment implements DataView
{
    public static AddPatientFragment newInstance()
    {
        AddPatientFragment fragment = new AddPatientFragment();
        return fragment;
    }

    /**
     * 就诊人姓名
     */
    private EditText mEtName;

    /***
     * 就诊人手机号码
     */
    private EditText mEtPhone;

    /**
     * 就诊人身份证号
     */
    private EditText mEtCard;

    /***
     * 出生年月
     */
    private TextView mTvDate;

    /***
     * 性别
     */
    private RadioGroup mRgGender;

    /**
     * 是否是默认就诊人
     */
    private Switch mSwitch;

    /***
     * 提交填写的内容
     */
    private Button mBtnAdd;
    private PostAddPatientItem mPostAddPatientItem   = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_add_patient, null);
        initView(view);
        mIsInit = true;
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        setListener();
    }

    private void initView(View view)
    {
        mEtCard = (EditText) view.findViewById(R.id.et_card);
        mEtName = (EditText) view.findViewById(R.id.et_name);
        mEtPhone = (EditText) view.findViewById(R.id.et_phone);
        mTvDate = (TextView) view.findViewById(R.id.tv_date);
        mRgGender = (RadioGroup) view.findViewById(R.id.rg_gender);
        mSwitch = (Switch) view.findViewById(R.id.switch_switch);
        mBtnAdd = (Button) view.findViewById(R.id.btn_add_patient);
    }

    private void setListener()
    {
        mTvDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onDateClick();
            }
        });
        mBtnAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                addPatient();
            }
        });
    }

    /***
     * 添加就诊人
     */
    private void addPatient()
    {
        String name = mEtName.getText().toString();
        String phone = mEtPhone.getText().toString();
        String card = mEtCard.getText().toString();
        if (TextUtils.isEmpty(name))
        {
            mActivity.showCroutonToast("姓名不可以为空");
            return;
        }
        if (TextUtils.isEmpty(card))
        {
            mActivity.showCroutonToast("身份证号不可以为空");
            return;
        }
        String date = mTvDate.getText().toString();
        if (TextUtils.isEmpty(date))
        {
            mActivity.showCroutonToast("出生日期不可以为空");
            return;
        }
        if (TextUtils.isEmpty(phone))
        {
            mActivity.showCroutonToast("手机号码不可以为空");
            return;
        }
        String genderText = UserPatientItem.DEFAULT_MAN;
        if (R.id.rb_woman == mRgGender.getCheckedRadioButtonId())
        {
            genderText = UserPatientItem.DEFAULT_WOMAN;
        }
        String isDefault = UserPatientItem.IS_DEFAULT;
        if (!mSwitch.isChecked())
        {
            isDefault = UserPatientItem.NOT_DEFAULT;
        }
        String info = "name = " + name + " card = " + card + " gender = " + genderText

                + " date = " + date + " phone = " + phone + " isdefault = " + isDefault;
        System.out.println("info = " + info);

        mPostAddPatientItem = new PostAddPatientItem();
        mPostAddPatientItem.birthday = date;
        mPostAddPatientItem.idcard = card;
        mPostAddPatientItem.mobile = phone;
        mPostAddPatientItem.name = name;
        mPostAddPatientItem.sex = genderText;
        mPostAddPatientItem.DEFAULT = isDefault;
        mPostAddPatientItem.isDefault = isDefault;
        AddPatientPresenter addPatientPresenter = new AddPatientPresenterImpl(mActivity,this);
        addPatientPresenter.doAddUserPatient(mPostAddPatientItem);
    }

    private void onDateClick()
    {
        DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                mTvDate.setText(year + "-" + (1 + monthOfYear) + "-" + dayOfMonth);
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(mActivity, callback, 2015, 8, 20);
        datePickerDialog.show();

    }

    @Override
    public String getFragmentTitle()
    {
        return "添加处方";
    }

    protected void doRefresh()
    {
        doGetData();
    }

    @Override
    protected void doGetData()
    {

    }

    @Override
    public void onGetDataSuccess(ResultItem resultItem)
    {
        if (null != resultItem)
        {
            mActivity.showCroutonToast(resultItem.message);
           if(resultItem.success())
           {
              if(null != mPostAddPatientItem)
              {
                  EventBus.getDefault().post(new OnAddPatientItemEvent(mPostAddPatientItem));
              }
               mActivity.finish();
           }
        }
    }

    @Override
    public void onGetDataFailured(String msg)
    {

    }
}
