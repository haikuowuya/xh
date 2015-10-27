package com.xinheng.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xinheng.APIURL;
import com.xinheng.AccountSecurityActivity;
import com.xinheng.AddressListActivity;
import com.xinheng.R;
import com.xinheng.UserAccountNameActivity;
import com.xinheng.UserNicknameActivity;
import com.xinheng.base.BaseFragment;
import com.xinheng.common.AbsImageLoadingListener;
import com.xinheng.eventbus.OnModifyAccountNameEvent;
import com.xinheng.eventbus.OnModifyNicknameEvent;
import com.xinheng.eventbus.OnModifyPhotoEvent;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.presenter.UserPhotoPresenter;
import com.xinheng.mvp.presenter.impl.UserPhotoPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.BitmapUtils;
import com.xinheng.util.PhotoUtils;
import com.xinheng.util.StorageUtils;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

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

    private TextView mTvNickname;
    private TextView mTvAccountName;

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
        mTvNickname = (TextView) view.findViewById(R.id.tv_nickname);
        mTvAccountName = (TextView) view.findViewById(R.id.tv_account_name);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
        setListener();
        if (null != mActivity.getLoginSuccessItem())
        {
            String photo = mActivity.getLoginSuccessItem().photo;
            if (photo.startsWith(StorageUtils.getCacheDir(mActivity).getAbsolutePath()))
            {
                mIvPhoto.setImageBitmap(BitmapFactory.decodeFile(photo));
            }
            else  if (!TextUtils.isEmpty(photo))
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

            if (null != mActivity.getLoginSuccessItem().account)
            {
                mTvNickname.setText(mActivity.getLoginSuccessItem().account.nickname);
                mTvAccountName.setText(mActivity.getLoginSuccessItem().account.account);
            }
        }
    }

    @Subscribe
    public void onEventMainThread(OnModifyNicknameEvent event)
    {
        if (null != event && !TextUtils.isEmpty(event.newNickname))
        {
            mTvNickname.setText(event.newNickname);
        }
    }

    @Subscribe
    public void onEventMainThread(OnModifyAccountNameEvent event)
    {
        if (null != event && !TextUtils.isEmpty(event.mNewAccountName))
        {
            mTvAccountName.setText(event.mNewAccountName);
        }
    }

    @Override
    public void onDestroy()
    {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
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
            if(resultItem.success())
            {
                EventBus.getDefault().post(new OnModifyPhotoEvent(mImageFilePath));
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
                    nickName();
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
        private void nickName()
        {
//            mActivity.showCroutonToast("昵称");
            UserNicknameActivity.actionUserNickname(mActivity);
        }

        /***
         * 姓名
         */
        private void username()
        {
//            mActivity.showCroutonToast("姓名");
            if (!TextUtils.isEmpty(mTvAccountName.getText()))
            {
                mActivity.showToast("账户只允许设置一次");
                return;
            }
            UserAccountNameActivity.actionUserAccountName(mActivity);
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
            else if(requestCode == PhotoUtils.REQUEST_FROM_CAMERA)
            {
                mImageFilePath = PhotoUtils.getFinalCameraImagePath();
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
