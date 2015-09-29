package com.xinheng.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xinheng.APIURL;
import com.xinheng.AccountSecurityActivity;
import com.xinheng.AddressListActivity;
import com.xinheng.R;
import com.xinheng.base.BaseFragment;
import com.xinheng.common.AbsImageLoadingListener;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.presenter.UserPhotoPresenter;
import com.xinheng.mvp.presenter.impl.UserPhotoPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.BitmapUtils;
import com.xinheng.util.PhotoUtils;
import com.xinheng.util.StorageUtils;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：  我的帐号Fragment页面
 */
public class UserAccountFragment extends BaseFragment implements DataView
{
    private Bitmap mBitmap;

    public static UserAccountFragment newInstance()
    {
        UserAccountFragment fragment = new UserAccountFragment();
        return fragment;
    }

    private String mImageFilePath = null;
    private LinearLayout mLinearPhotoContainer;
    /**
     * 姓名
     */
    private LinearLayout mLinearUserName;
    /***
     * 昵称
     */
    private LinearLayout mLinearNick;
    /***
     * 地址管理
     */
    private LinearLayout mLinearAddress;

    /***
     * 账户安全
     */
    private LinearLayout mLinearAccountSecure;
    /***
     * 我的头像
     */
    private ImageView mIvPhoto;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_user_account, null);      //TODO
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        mLinearAccountSecure = (LinearLayout) view.findViewById(R.id.linear_account_secure);
        mLinearAddress = (LinearLayout) view.findViewById(R.id.linear_address_container);
        mLinearNick = (LinearLayout) view.findViewById(R.id.linear_nick_container);
        mLinearUserName = (LinearLayout) view.findViewById(R.id.linear_username_container);
        mLinearPhotoContainer = (LinearLayout) view.findViewById(R.id.linear_photo_container);
        mIvPhoto = (ImageView) view.findViewById(R.id.iv_photo);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        setListener();
        if (null != mActivity.getLoginSuccessItem())
        {
            String photo = mActivity.getLoginSuccessItem().photo;
            if (!TextUtils.isEmpty(photo))
            {
                if (!photo.startsWith(APIURL.BASE_API_URL))
                {
                    photo = APIURL.BASE_API_URL + photo;
                }
                ImageLoader.getInstance().loadImage(photo, new AbsImageLoadingListener()
                        {
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
                            {
                                if (null != loadedImage)
                                {
                                    mIvPhoto.setImageBitmap(loadedImage);
                                }
                            }
                        });
            }
        }
    }

    private void setListener()
    {
        OnClickListenerImpl onClickListener = new OnClickListenerImpl();
        mLinearAccountSecure.setOnClickListener(onClickListener);
        mLinearAddress.setOnClickListener(onClickListener);
        mLinearNick.setOnClickListener(onClickListener);
        mLinearUserName.setOnClickListener(onClickListener);
        mLinearPhotoContainer.setOnClickListener(onClickListener);
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
                case R.id.linear_photo_container://上传头像
                    PhotoUtils.showSelectDialog(mActivity);
                    break;
                case R.id.linear_account_secure://账户安全
                    accountSecure();
                    break;
                case R.id.linear_address_container://地址管理
                    address();
                    break;
                case R.id.linear_nick_container://昵称
                    nick();
                    break;
                case R.id.linear_username_container://用户名/姓名
                    username();
                    break;
            }
        }

        /**
         * 账户安全
         */
        private void accountSecure()
        {
            AccountSecurityActivity.actionAccountSecurity(mActivity);
        }

        /**
         * 地址管理
         */
        private void address()
        {
            AddressListActivity.actionAddressManager(mActivity);
        }

        /**
         * 昵称
         */
        private void nick()
        {
            mActivity.showCroutonToast("昵称");
        }

        /***
         * 姓名
         */
        private void username()
        {
            mActivity.showCroutonToast("姓名");
        }
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
            if (null != mImageFilePath)
            {
                mImageFilePath = BitmapUtils.getCompressBitmapFilePath(mActivity, mImageFilePath);

                mBitmap = BitmapUtils.scaleBitmap(mImageFilePath);
                mBitmap = BitmapUtils.rotateBitmap(mImageFilePath, mBitmap);
                mIvPhoto.setImageBitmap(mBitmap);
                if (null != mImageFilePath)
                {
                    UserPhotoPresenter userPhotoPresenter = new UserPhotoPresenterImpl(mActivity, this);
                    userPhotoPresenter.doUploadPhoto(mImageFilePath);
                }
            }
            else
            {
                mActivity.showToast("图片选取失败");
            }
        }
        System.out.println("fragment requestCode = " + requestCode + " resultCode = " + resultCode + " imageFilePath = " + mImageFilePath + " data = " + data);
    }
}
