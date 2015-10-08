package com.xinheng.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xinheng.APIURL;
import com.xinheng.AddDrugActivity;
import com.xinheng.ConfirmOrderActivity;
import com.xinheng.R;
import com.xinheng.base.BaseFragment;
import com.xinheng.common.AbsImageLoadingListener;
import com.xinheng.eventbus.OnAddDrugItemEvent;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.prescription.DrugItem;
import com.xinheng.mvp.model.prescription.OrderPrescItem;
import com.xinheng.mvp.model.prescription.PostSavePrescriptionItem;
import com.xinheng.mvp.presenter.SavePrescriptionPresenter;
import com.xinheng.mvp.presenter.impl.SavePrescriptionPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.DensityUtils;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.PhotoUtils;
import com.xinheng.util.RSAUtil;
import com.xinheng.util.StorageUtils;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：  按方抓药Fragment界面
 */
public class PrescriptionFragment extends BaseFragment implements DataView
{
    public static final String TEXT_FINISHED = "完成";
    public static final String TEXT_ADD_MEDICAL = "添加药品";

    public static PrescriptionFragment newInstance()
    {
        PrescriptionFragment fragment = new PrescriptionFragment();
        return fragment;
    }

    private List<DrugItem> mDrugItems = null;
    private List<String> mDrugItemCounts = new LinkedList<>();

    /***
     * 选择的图片路径
     */
    private String mImageFilePath;

    /***
     * 选择的图片
     */
    private ImageView mIvImage;

    /***
     * 文件是否选取的状态
     */
    private TextView mTvFileStatus;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_prescription, null);  //TODO 布局文件
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

    /***
     * 添加药品后的编辑按钮
     */
    private Button mBtnEdit;

    /**
     * 服用剂数
     */
    private EditText mEtQuantity;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
        setListener();
    }

    private void setListener()
    {
        OnClickListenerImpl onClickListener = new OnClickListenerImpl();
        mBtnImage.setOnClickListener(onClickListener);
        mBtnAddMedical.setOnClickListener(onClickListener);
        mBtnSavePrescription.setOnClickListener(onClickListener);
        mBtnSubmit.setOnClickListener(onClickListener);
        mEtQuantity.addTextChangedListener(
                new TextWatcher()
                {
                    public void beforeTextChanged(CharSequence s, int start, int count, int after)
                    {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count)
                    {

                    }

                    @Override
                    public void afterTextChanged(Editable s)
                    {
                        if (mDrugItems != null && !mDrugItems.isEmpty() && mPrice > 0.0)
                        {
                            setTextPrice(mPrice);
                        }
                    }
                });
    }

    private void initView(View view)
    {
        mEtDoctorName = (EditText) view.findViewById(R.id.et_doctor_name);
        mEtHospital = (EditText) view.findViewById(R.id.et_hospital);
        mEtQuantity = (EditText) view.findViewById(R.id.et_quantity);
        mIvImage = (ImageView) view.findViewById(R.id.iv_image);
        mTvFileStatus = (TextView) view.findViewById(R.id.tv_file_status);
        mEtMedicalName = (EditText) view.findViewById(R.id.et_medical_name);
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
        setTextPrice(0.00);
    }

    //===============================EVENT BUS========================
    @Subscribe
    public void onEventMainThread(OnAddDrugItemEvent event)
    {
        if (null != mLinearDrugContainer && null != event && event.mDrugItems != null && !event.mDrugItems.isEmpty())
        {
            mLinearDrugContainer.removeAllViews();
            mDrugItems = event.mDrugItems;
//            mActivity.showCroutonToast("event.size = " + event.mDrugItems.size());
            mBtnEdit.setVisibility(View.VISIBLE);
            fillLinearDrugContainer(event.mDrugItems);
            mBtnEdit.setOnClickListener(
                    new View.OnClickListener()
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
                final ImageView ivImageView = (ImageView) view.findViewById(R.id.iv_image);
                RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.relative_normal);
                LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linear_edit_container);
                relativeLayout.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.GONE);
                TextView tvDrugName = (TextView) relativeLayout.findViewById(R.id.tv_drug_name);
                TextView tvDrugInfo = (TextView) relativeLayout.findViewById(R.id.tv_drug_info);
                String img = item.img;
                if (!TextUtils.isEmpty(img))
                {
                    if (!img.startsWith(APIURL.BASE_API_URL))
                    {
                        img = APIURL.BASE_API_URL + img;
                    }
                    ivImageView.setTag(img);
                    ImageLoader.getInstance().loadImage(
                            img, new AbsImageLoadingListener()
                            {
                                @Override
                                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
                                {
                                    if (null != loadedImage && imageUri.equals(ivImageView.getTag()))
                                    {
                                        ivImageView.setImageBitmap(loadedImage);
                                    }
                                }
                            });
                }
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
                ivIncrease.setOnClickListener(
                        new View.OnClickListener()
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
                ivDecrease.setOnClickListener(
                        new View.OnClickListener()
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

                tvDelete.setOnClickListener(
                        new View.OnClickListener()
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
        long quantity = 1;
        if (!TextUtils.isEmpty(mEtQuantity.getText()) && TextUtils.isDigitsOnly(mEtQuantity.getText()))
        {
            quantity = Long.parseLong(mEtQuantity.getText().toString());
        }
        double countPrice = quantity * price;
        mTvPrice.setText("￥" + new java.text.DecimalFormat("#0.00").format(countPrice) + "元");
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
    public void onGetDataSuccess(ResultItem resultItem, String requestTag)
    {
        if (null != resultItem)
        {
            mActivity.showToast(resultItem.message);
            if (resultItem.success())
            {
                OrderPrescItem orderPrescItem = GsonUtils.jsonToClass(resultItem.properties.getAsJsonObject().toString(), OrderPrescItem.class);
                if (null != orderPrescItem && !TextUtils.isEmpty(orderPrescItem.orderId))
                {
                    ConfirmOrderActivity.actionConfirmOrder(mActivity, orderPrescItem.orderId);
                    mActivity.finish();
                }
            }
        }
    }

    @Override
    public void onGetDataFailured(String msg, String requestTag)
    {
        mActivity.showCroutonToast(msg);
    }

    private class OnClickListenerImpl implements View.OnClickListener
    {
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.btn_image://选择图片:
                    PhotoUtils.showSelectDialog(mActivity);
                    break;
                case R.id.btn_add_medical://添加药品
                    if (mBtnAddMedical.getText().toString().equals(TEXT_ADD_MEDICAL))
                    {
                        AddDrugActivity.actionAddDrug(mActivity);
                    } else
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
                    } else
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
            mActivity.showToast("处方中药品不可以为空");
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
        if (TextUtils.isEmpty(mImageFilePath))
        {
            mActivity.showToast("请选择处方图片");
            return;
        }
        String quantity = mEtQuantity.getText().toString();
        if (TextUtils.isEmpty(quantity))
        {
//            mActivity.showCroutonToast("请输入服用剂数");
//            return;
            quantity = "1";
        }
        //userid字段此处可以不赋值，后面会获取登录信息的userid
        PostSavePrescriptionItem item = new PostSavePrescriptionItem();
        item.name = name;
        item.hosname = mEtHospital.getText().toString();
        item.doctorname = mEtDoctorName.getText().toString();
        item.patientname = mEtUserName.getText().toString();
        item.result = RSAUtil.clientEncrypt("结果");
        item.quantity = quantity;
        item.file = new File(mImageFilePath);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == Activity.RESULT_OK)
        {
            if (requestCode == PhotoUtils.REQUEST_FROM_PHOTO)
            {
                if (null != data && data.getData() != null)
                {
                    mImageFilePath = StorageUtils.getFilePathFromUri(mActivity, data.getData());
                }
            }

            if (!TextUtils.isEmpty(mImageFilePath))
            {
                mTvFileStatus.setVisibility(View.GONE);
                mIvImage.setImageBitmap(BitmapFactory.decodeFile(mImageFilePath));
            }
        }
        // System.out.println("fragment requestCode = " + requestCode + " resultCode = " + resultCode + " imageFilePath = " + mImageFilePath + " data = " + data);
        // mActivity.showCroutonToast("图片路径 = " + mImageFilePath);
    }
}
