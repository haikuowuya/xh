package com.xinheng.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xinheng.APIURL;
import com.xinheng.R;
import com.xinheng.base.BaseFragment;
import com.xinheng.common.AbsImageLoadingListener;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.view.DataView;

import uk.co.senab.photoview.PhotoView;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：  图片详情查看Fragment页面
 */
public class PhotoViewFragment extends BaseFragment implements DataView
{
    public static final String ARG_IMAGE_URL = "image_url";

    public static PhotoViewFragment newInstance(String imageUrl)
    {
        PhotoViewFragment fragment = new PhotoViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_IMAGE_URL, imageUrl);
        fragment.setArguments(bundle);
        return fragment;
    }

    private String mImageUrl;

    private PhotoView mPhotoView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_photoview, null);      //TODO
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        mPhotoView = (PhotoView) view.findViewById(R.id.pv_photoview);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mImageUrl = getArguments().getString(ARG_IMAGE_URL);

        if (!TextUtils.isEmpty(mImageUrl))
        {
            if (!mImageUrl.startsWith(APIURL.BASE_API_URL))
            {
                mImageUrl = APIURL.BASE_API_URL + mImageUrl;
            }

            ImageLoader.getInstance().loadImage(
                    mImageUrl, new AbsImageLoadingListener()
                    {
                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
                        {
                            mPhotoView.setImageBitmap(loadedImage);
                        }
                    });

            setListener();
        }
        ;
    }

    private void setListener()
    {
        OnClickListenerImpl onClickListener = new OnClickListenerImpl();
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

        }
    }

    @Override
    public void onGetDataFailured(String msg, String requestTag)
    {

    }

    private class OnClickListenerImpl implements View.OnClickListener
    {
        public void onClick(View v)
        {
            switch (v.getId())
            {

            }
        }
    }

}
