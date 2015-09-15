package com.xinheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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
import com.xinheng.util.DensityUtils;
import com.xinheng.util.PhotoUtils;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：  按方抓药Fragment界面
 */
public class GetMedicalFragment extends BaseFragment
{
    public static GetMedicalFragment newInstance()
    {
        GetMedicalFragment fragment = new GetMedicalFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_get_medical, null);
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
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        OnClickListenerImpl onClickListener = new OnClickListenerImpl();
        mBtnImage.setOnClickListener(onClickListener);
        mBtnAddMedical.setOnClickListener(onClickListener);
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
            }

        }
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
