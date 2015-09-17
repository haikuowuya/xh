package com.xinheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.xinheng.AddDrugActivity;
import com.xinheng.R;
import com.xinheng.base.BaseFragment;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.prescription.PostSavePrescriptionItem;
import com.xinheng.mvp.presenter.SavePrescriptionPresenter;
import com.xinheng.mvp.presenter.impl.SavePrescriptionPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.DensityUtils;
import com.xinheng.util.PhotoUtils;

import java.io.File;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：  按方抓药Fragment界面
 */
public class PrescriptionFragment extends BaseFragment     implements DataView
{
    public static PrescriptionFragment newInstance()
    {
        PrescriptionFragment fragment = new PrescriptionFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_prescription, null);
        initView(view);
        mIsInit = true;
        return view;
    }

    /**
     * 药方名称
     */
    private EditText mEtMedicalName;

    /***
     * 浏览图片按钮
     */
    private Button mBtnImage;
    /**
     * 处方医院
     */
    private EditText mEtHospital;
    /***
     * 处方医生名称
     */
    private EditText mEtDoctorName;

    /**
     * 患者名称
     */
    private EditText mEtUserName;

    /**
     * 每份价格
     */
    private EditText mEtPrice;

    /**
     * 添加药品
     */
    private Button mBtnAddMedical;

    /***
     * 保存处方
     */
    private Button mBtnSavePrescription;

    /**
     * 提交审核
     */
    private Button mBtnSubmit;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        OnClickListenerImpl onClickListener = new OnClickListenerImpl();
        mBtnImage.setOnClickListener(onClickListener);
        mBtnAddMedical.setOnClickListener(onClickListener);
        mBtnSavePrescription.setOnClickListener(onClickListener);
        mBtnSubmit.setOnClickListener(onClickListener);
    }

    private void initView(View view)
    {
        mEtDoctorName = (EditText) view.findViewById(R.id.et_doctor_name);
        mEtHospital = (EditText) view.findViewById(R.id.et_hospital);
        mEtMedicalName = (EditText) view.findViewById(R.id.et_medical_name);
        mEtPrice = (EditText) view.findViewById(R.id.et_price);
        mEtUserName = (EditText) view.findViewById(R.id.et_username);
        mBtnImage = (Button) view.findViewById(R.id.btn_image);
        mBtnAddMedical = (Button) view.findViewById(R.id.btn_add_medical);
        mBtnSavePrescription = (Button) view.findViewById(R.id.btn_save_prescription);
        mBtnSubmit = (Button) view.findViewById(R.id.btn_submit);
    }


    @Override
    public String getFragmentTitle()
    {
        return "我的订单";
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
        if(null != resultItem)
        {
            mActivity.showCroutonToast(resultItem.message);
        }
    }

    @Override
    public void onGetDataFailured(String msg)
    {

    }

    private class OnClickListenerImpl implements View.OnClickListener
    {
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.btn_image://选择图片:
                    selectPic();
                    break;
                case R.id.btn_add_medical://添加药品
                    AddDrugActivity.actionAddMedical(mActivity);
                    break;
                case R.id.btn_save_prescription://保存药方
                    savePrescription();
                    break;
                case R.id.btn_submit://提交审核
                    submit();
                    break;
            }

        }
    }

    /***
     * 保存药方
     */
    private void savePrescription()
    {


    }

    /**
     * 提交审核
     */
    private void submit()
    {
        String name = mEtMedicalName.getText().toString();
        if(TextUtils.isEmpty(name))
        {
            mActivity.showCroutonToast("药方名称不可以为空");
            return;
        }

        //userid字段此处可以不赋值，后面会获取登录信息的userid
        PostSavePrescriptionItem item = new PostSavePrescriptionItem();
        item.name = name;
        item.file = new File("");
        item.hosname = mEtHospital.getText().toString();
        item.doctorname = mEtDoctorName.getText().toString();
        item.patientname = mEtUserName.getText().toString();
        SavePrescriptionPresenter savePrescriptionPresenter = new SavePrescriptionPresenterImpl(mActivity, this);
        savePrescriptionPresenter.doSavePrescription(item);
    }

    /***
     * 浏览图片
     */
    private void selectPic()
    {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.layout_dialog_modify_photo, null);
        LinearLayout linearCameraContainer = (LinearLayout) view.findViewById(R.id.linear_camera_container);
        LinearLayout linearGalleryContainer = (LinearLayout) view.findViewById(R.id.linear_gallery_container);
        final AlertDialog alertDialog = new AlertDialog.Builder(mActivity).setView(view).create();
        int width = DensityUtils.getScreenWidthInPx(mActivity) - DensityUtils.dpToPx(mActivity, 40);
        alertDialog.getWindow().setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT);
        alertDialog.show();
        linearCameraContainer.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                PhotoUtils.selectPicFromCamera(mActivity);
                alertDialog.dismiss();
            }
        });
        linearGalleryContainer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                PhotoUtils.selectPicFromSD(mActivity);
                alertDialog.dismiss();
            }
        });
    }
}
