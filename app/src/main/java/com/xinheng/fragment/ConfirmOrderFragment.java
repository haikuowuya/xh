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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xinheng.AddDrugActivity;
import com.xinheng.R;
import com.xinheng.base.BaseFragment;
import com.xinheng.eventbus.OnAddDrugItemEvent;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.prescription.DrugItem;
import com.xinheng.mvp.model.prescription.PostSavePrescriptionItem;
import com.xinheng.mvp.presenter.SavePrescriptionPresenter;
import com.xinheng.mvp.presenter.impl.SavePrescriptionPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.DensityUtils;
import com.xinheng.util.PhotoUtils;
import com.xinheng.util.RSAUtil;

import java.util.LinkedList;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：  确认订单界面
 */
public class ConfirmOrderFragment extends BaseFragment implements DataView
{
    public static final String TEXT_FINISHED = "完成";
    public static final String TEXT_ADD_MEDICAL = "添加药品";

    public static ConfirmOrderFragment newInstance()
    {
        ConfirmOrderFragment fragment = new ConfirmOrderFragment();
        return fragment;
    }

    private List<DrugItem> mDrugItems = null;
    private List<String> mDrugItemCounts = new LinkedList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_confirm_order, null);  //TODO
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

    /**
     * 处方添加的药品
     */
    private LinearLayout mLinearDrugContainer;
    /**
     * 计算的处方的价格
     */
    private TextView mTvPrice;

    private double mPrice;

    private Button mBtnEdit;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
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
        mTvPrice = (TextView) view.findViewById(R.id.tv_price);
        mBtnEdit = (Button) view.findViewById(R.id.btn_edit);
        mLinearDrugContainer = (LinearLayout) view.findViewById(R.id.linear_drug_container);
        mLinearDrugContainer.setVisibility(View.GONE);
        mBtnEdit.setVisibility(View.GONE);
    }

    //===============================EVENT BUS========================
    @Subscribe
    public void onEventMainThread(OnAddDrugItemEvent event)
    {
        if (null != mLinearDrugContainer && null != event && event.mDrugItems != null && !event.mDrugItems.isEmpty())
        {
            mDrugItems = event.mDrugItems;
//            mActivity.showCroutonToast("event.size = " + event.mDrugItems.size());
            mBtnEdit.setVisibility(View.VISIBLE);
            fillLinearDrugContainer(event.mDrugItems);
            mBtnEdit.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    onEdit();
                }
            });
        }
    }

    private void onEdit()
    {
        mBtnAddMedical.setText(TEXT_FINISHED);
        mBtnEdit.setVisibility(View.GONE);
        for (int i = 0; i < mLinearDrugContainer.getChildCount(); i++)
        {
            View view = mLinearDrugContainer.getChildAt(i);
            if (view.findViewById(R.id.relative_normal) != null)
            {
                view.findViewById(R.id.relative_normal).setVisibility(View.GONE);
                view.findViewById(R.id.linear_edit_container).setVisibility(View.VISIBLE);
            }
        }
    }

    private void onEditFinsh()
    {
        mBtnAddMedical.setText(TEXT_ADD_MEDICAL);
        mBtnEdit.setVisibility(View.VISIBLE);
        for (int i = 0; i < mLinearDrugContainer.getChildCount(); i++)
        {
            View view = mLinearDrugContainer.getChildAt(i);
            if (view.findViewById(R.id.relative_normal) != null)
            {
                view.findViewById(R.id.relative_normal).setVisibility(View.VISIBLE);
                view.findViewById(R.id.linear_edit_container).setVisibility(View.GONE);
                TextView tvCount = (TextView) view.findViewById(R.id.tv_drug_count);

            }
        }
    }

    /**
     * 将药方中添加的药品展示出来
     *
     * @param drugItems
     */
    private void fillLinearDrugContainer(List<DrugItem> drugItems)
    {
        if (null != drugItems)
        {
            mLinearDrugContainer.setVisibility(View.VISIBLE);
            for (int i = 0; i < drugItems.size(); i++)
            {
                final DrugItem item = drugItems.get(i);

                final View view = LayoutInflater.from(mActivity).inflate(R.layout.layout_prescription_drug_item, null);  //TODO
                RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.relative_normal);
                LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linear_edit_container);
                relativeLayout.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.GONE);
                TextView tvDrugName = (TextView) relativeLayout.findViewById(R.id.tv_drug_name);
                TextView tvDrugInfo = (TextView) relativeLayout.findViewById(R.id.tv_drug_info);
                TextView tvDrugPrice = (TextView) relativeLayout.findViewById(R.id.tv_drug_price);
                final TextView tvDrugCount = (TextView) relativeLayout.findViewById(R.id.tv_drug_count);
                tvDrugName.setText(item.name);
                tvDrugPrice.setText("￥" + item.cost);
                tvDrugCount.setText("x1");
                String info = "包装规格：" + item.specs + "    产地：" + item.place + "\n生产厂家：" + item.producer;
                tvDrugInfo.setText(info);
                final double cost = Double.parseDouble(item.cost);
                mPrice += cost;
                ImageView ivIncrease = (ImageView) linearLayout.findViewById(R.id.iv_edit_increase);
                ImageView ivDecrease = (ImageView) linearLayout.findViewById(R.id.iv_edit_decrease);
                TextView tvDelete = (TextView) linearLayout.findViewById(R.id.tv_edit_delete);
                TextView tvUnit = (TextView) linearLayout.findViewById(R.id.tv_unit);
                tvUnit.setText("包装规格：" + item.specs);
                final TextView tvEditCount = (TextView) linearLayout.findViewById(R.id.tv_edit_count);
                final int finalI = i;
                ivIncrease.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        int count = Integer.parseInt(tvEditCount.getText().toString());
                        count += 1;
                        mPrice = mPrice + cost;
                        tvEditCount.setText(count + "");
                        tvDrugCount.setText(count + "");

                        setTextPrice(mPrice);
                    }
                });
                ivDecrease.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        int count = Integer.parseInt(tvEditCount.getText().toString());
                        count -= 1;
                        if (count < 1)
                        {
                            mActivity.showCroutonToast("数量不可以小于1");
                            return;
                        }
                        mPrice = mPrice - cost;
                        tvEditCount.setText(count + "");
                        tvDrugCount.setText(count + "");

                        setTextPrice(mPrice);
                    }
                });

                tvDelete.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        if (view.getParent() != null)
                        {
                            int count = Integer.parseInt(tvEditCount.getText().toString());
                            mPrice = mPrice - cost * count;
                            mLinearDrugContainer.removeView(view);
                            if (mLinearDrugContainer.getChildCount() == 1)//只剩下标题title
                            {
                                mLinearDrugContainer.setVisibility(View.GONE);
                            }
                            setTextPrice(mPrice);
                            mDrugItems.remove(item);

                            if (mDrugItems.isEmpty())
                            {
                                onEditFinsh();
                                mBtnEdit.setVisibility(View.GONE);
                            }
                        }
                    }
                });
                int height = DensityUtils.dpToPx(mActivity, 100.f);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                mLinearDrugContainer.addView(view, layoutParams);
            }
            setTextPrice(mPrice);

        }
    }

    private void setTextPrice(double price)
    {
        mTvPrice.setText(new java.text.DecimalFormat("#0.00").format(price) + "元");
    }

    //===============================EVENT BUS========================
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
        if (null != resultItem)
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
                    if (mBtnAddMedical.getText().toString().equals(TEXT_ADD_MEDICAL))
                    {
                        AddDrugActivity.actionAddDrug(mActivity);
                    }
                    else
                    {
                        onEditFinsh();
                    }
                    break;
                case R.id.btn_save_prescription://保存药方
                    savePrescription();
                    break;
                case R.id.btn_submit://提交审核
                    if (mBtnAddMedical.getText().toString().equals(TEXT_ADD_MEDICAL))
                    {
                        submit();
                    }
                    else
                    {
                        mActivity.showCroutonToast("请先点击完成按钮");
                    }
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
        if (null == mDrugItems || mDrugItems.isEmpty())
        {
            mActivity.showCroutonToast("处方中药品不可以为空");
            return;
        }
        //获取添加到处方中的药品数目
        mDrugItemCounts.clear();
        for (int i = 0; i < mLinearDrugContainer.getChildCount(); i++)
        {
            View view = mLinearDrugContainer.getChildAt(i);
            if (view.findViewById(R.id.relative_normal) != null)
            {
                view.findViewById(R.id.relative_normal).setVisibility(View.VISIBLE);
                view.findViewById(R.id.linear_edit_container).setVisibility(View.GONE);
                TextView tvCount = (TextView) view.findViewById(R.id.tv_edit_count);
                mDrugItemCounts.add(tvCount.getText().toString());
               }

        }
        String name = mEtMedicalName.getText().toString();
        if (TextUtils.isEmpty(name))
        {
//            mActivity.showCroutonToast("药方名称不可以为空");
//            return;
        }
        //userid字段此处可以不赋值，后面会获取登录信息的userid
        PostSavePrescriptionItem item = new PostSavePrescriptionItem();
        item.name = name;
        item.hosname = mEtHospital.getText().toString();
        item.doctorname = mEtDoctorName.getText().toString();
        item.patientname = mEtUserName.getText().toString();
        item.result = "结果";
        item.quantity = "111";

        LinkedList<String> drugList = new LinkedList<>();
        for (DrugItem drugitem : mDrugItems)
        {
            String durgId = drugitem.id;
            drugList.add(RSAUtil.clientEncrypt(durgId));
        }
        item.drugId = drugList;
        item.drugQuantity = mDrugItemCounts;
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
