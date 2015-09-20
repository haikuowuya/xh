package com.xinheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinheng.R;
import com.xinheng.base.BaseFragment;
import com.xinheng.eventbus.OnAddOrModifyOrDelAddressEvent;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.address.AddressItem;
import com.xinheng.mvp.model.address.PostAddressItem;
import com.xinheng.mvp.presenter.AddOrModifyAddressPresenter;
import com.xinheng.mvp.presenter.DeleteAddressPresenter;
import com.xinheng.mvp.presenter.impl.AddOrModifyAddressPresenterImpl;
import com.xinheng.mvp.presenter.impl.DeleteAddressPresenterImpl;
import com.xinheng.mvp.view.DataView;

import de.greenrobot.event.EventBus;

/**
 * 作者：  libo
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明： 新增收货地址
 */
public class AddOrModifyAddressFragment extends BaseFragment implements DataView
{
    public static final String ARG_ADDRESS_ITEM = "address_item";

    public static AddOrModifyAddressFragment newInstance(AddressItem addressItem)
    {
        AddOrModifyAddressFragment fragment = new AddOrModifyAddressFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_ADDRESS_ITEM, addressItem);
        fragment.setArguments(bundle);
        return fragment;
    }

    private AddressItem mAddressItem;
    /****
     * 省市地区信息
     */
    private TextView mTvCity;
    /***
     * 收货人手机号码
     */
    private EditText mEtPhone;
    /***
     * 收货地址名字
     */
    private EditText mEtName;
    /**
     * 收货详细地址
     */
    private EditText mEtAddress;
    /***
     * 邮政编码
     */
    private EditText mEtZipCode;

    /***
     * 提交
     */
    private Button mBtnSubmit;

    /***
     * 是否是默认收货地址
     */
    private CheckBox mCbDefault;

    /***
     * 删除收货地址，只有在修改收货地址时才显示
     */
    private ImageView mIvDeleteAddress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_add_or_modify_address, null);
        initView(view);
        mIsInit = true;
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        setListener();
        mAddressItem = getArguments().getSerializable(ARG_ADDRESS_ITEM) == null ? null : (AddressItem) getArguments().getSerializable(ARG_ADDRESS_ITEM);
        if (null != mAddressItem)
        {
            mIvDeleteAddress.setVisibility(View.VISIBLE);
            mTvCity.setText(mAddressItem.city);
            mEtAddress.setText(mAddressItem.address);
            mEtAddress.setSelection(mAddressItem.address.length());
            mEtName.setText(mAddressItem.name);
            mEtName.setSelection(mAddressItem.name.length());
            mEtPhone.setText(mAddressItem.mobile);
            mEtPhone.setSelection(mAddressItem.mobile.length());
            mEtZipCode.setText(mAddressItem.zipcode);
            mEtZipCode.setSelection(mAddressItem.zipcode.length());
            mCbDefault.setChecked("1".equals(mAddressItem.isDefault));
        }

    }

    private void initView(View view)
    {
        mTvCity = (TextView) view.findViewById(R.id.tv_city);
        mEtAddress = (EditText) view.findViewById(R.id.et_address);
        mEtName = (EditText) view.findViewById(R.id.et_name);
        mEtPhone = (EditText) view.findViewById(R.id.et_phone);
        mEtZipCode = (EditText) view.findViewById(R.id.et_zipcode);
        mBtnSubmit = (Button) view.findViewById(R.id.btn_submit);
        mCbDefault = (CheckBox) view.findViewById(R.id.cb_default);
        mIvDeleteAddress = (ImageView) view.findViewById(R.id.iv_delete_address);
        mIvDeleteAddress.setVisibility(View.INVISIBLE);
    }

    private void setListener()
    {
        mBtnSubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                submit();
            }
        });

        mIvDeleteAddress.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                deleteAddress();
            }
        });
    }

    private void deleteAddress()
    {
        if(null != mAddressItem)
        {
            DeleteAddressPresenter deleteAddressPresenter = new DeleteAddressPresenterImpl(mActivity, this);
            deleteAddressPresenter.doDeleteAddress(mAddressItem.id);
        }

    }

    private void submit()
    {
        String city = mTvCity.getText().toString();
        if (TextUtils.isEmpty(city))
        {
            mActivity.showCroutonToast("地区不可以为空");
            return;
        }
        String address = mEtAddress.getText().toString();
        if (TextUtils.isEmpty(address))
        {
            mActivity.showCroutonToast("详细地址不可以为空");
            return;
        }
        String name = mEtName.getText().toString();
        if (TextUtils.isEmpty(name))
        {
            mActivity.showCroutonToast("收货人姓名不可以为空");
            return;
        }
        String phone = mEtPhone.getText().toString();

        if (TextUtils.isEmpty(phone))
        {
            mActivity.showCroutonToast("收货人手机号码不可以为空");
            return;
        }
        String zipCode = mEtZipCode.getText().toString();
        if (TextUtils.isEmpty(zipCode))
        {
            mActivity.showCroutonToast("邮政编码不可以为空");
            return;
        }

        PostAddressItem postAddressItem = new PostAddressItem();
        postAddressItem.isDefault = mCbDefault.isChecked() ? "1" : "0";
        if (mAddressItem != null)
        {
            postAddressItem.id = mAddressItem.id;//如果id不为空代表修改
        }

        postAddressItem.city = city;    //   {"message":"cityCode错误！","result":"-1"}
        postAddressItem.cityCode = "330824";//使用文档中的
        postAddressItem.mobile = phone;
        postAddressItem.address = address;
        postAddressItem.name = name;
        postAddressItem.zipcode = zipCode;
        AddOrModifyAddressPresenter addOrModifyAddressPresenter = new AddOrModifyAddressPresenterImpl(mActivity, this);
        addOrModifyAddressPresenter.doAddOrModifyAddress(postAddressItem);
        mActivity.hideSoftKeyBorard(mActivity.getCurrentFocus());
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
            if (resultItem.success())
            {
                EventBus.getDefault().post(new OnAddOrModifyOrDelAddressEvent());
                mActivity.finish();
            }
        }
    }

    @Override
    public void onGetDataFailured(String msg)
    {

    }
}
