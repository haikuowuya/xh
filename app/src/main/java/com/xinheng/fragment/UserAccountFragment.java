package com.xinheng.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xinheng.AccountSecurityActivity;
import com.xinheng.AddressListActivity;
import com.xinheng.R;
import com.xinheng.base.BaseFragment;
import com.xinheng.util.DensityUtils;
import com.xinheng.util.PhotoUtils;
import com.xinheng.util.StorageUtils;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：  我的帐号页面
 */
public class UserAccountFragment extends BaseFragment
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

    private class OnClickListenerImpl implements View.OnClickListener
    {
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.linear_photo_container://上传头像
                    photo();
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

        /***
         * 上传头像
         */
        private void photo()
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

        /**
         * 账户安全
         */
        private void accountSecure()
        {
           // mActivity.showCroutonToast("账户安全");
            AccountSecurityActivity.actionAccountSecurity(mActivity);
        }

        /**
         * 地址管理
         */
        private void address()
        {
//            mActivity.showCroutonToast("地址管理");
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
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                mBitmap = BitmapFactory.decodeFile(mImageFilePath, options); //此时返回bm为空
                int scale = 1;
                while (true)
                {
                    if (options.outWidth / 2 >= PhotoUtils.W_H && options.outHeight / 2 >= PhotoUtils.W_H)
                    {
                        options.outWidth /= 2;
                        options.outHeight /= 2;
                        scale++;
                    }
                    else
                    {
                        break;
                    }
                }
                options.inSampleSize = scale;
                //重新读入图片，注意这次要把options.inJustDecodeBounds 设为 false哦
                options.inJustDecodeBounds = false;
                mBitmap = BitmapFactory.decodeFile(mImageFilePath, options);
                mBitmap = PhotoUtils.rotateBitmap(mImageFilePath, mBitmap);
                mIvPhoto.setImageBitmap(mBitmap);
            }
        }
        System.out.println("fragment requestCode = " + requestCode + " resultCode = " + resultCode + " imageFilePath = " + mImageFilePath + " data = " + data);
    }
}
