package com.xinheng.adapter.main;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xinheng.R;
import com.xinheng.base.AbsImageLoadingListener;
import com.xinheng.mvp.model.AdItem;

import java.util.List;

/**
 * 首页顶部广告适配器
 */
public class AdPagerAdapter extends PagerAdapter
{
    private Activity mActivity;
    private List<AdItem> mData;

    public AdPagerAdapter(Activity context, List<AdItem> data)
    {
        mActivity = context;
        mData = data;
    }

    @Override
    public int getCount()
    {
        return null == mData ? 0 : mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position)
    {
        final ImageView imageView = new ImageView(mActivity);
        final AdItem item = mData.get(position);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageResource(R.mipmap.ic_ad_banner);
        container.addView(imageView);
        String imageUrl = item.imageUrl;
        ImageLoader.getInstance().loadImage(imageUrl, new AbsImageLoadingListener()
        {
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
            {
              //  imageView.setImageBitmap(loadedImage);
            }
        });
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        container.removeView((View) object);
    }

}
