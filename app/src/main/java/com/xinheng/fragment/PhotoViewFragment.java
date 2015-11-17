package com.xinheng.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xinheng.APIURL;
import com.xinheng.R;
import com.xinheng.base.BaseFragment;
import com.xinheng.common.AbsImageLoadingListener;
import com.xinheng.drawable.DrawerArrowDrawable;
import com.xinheng.view.HackyViewPager;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：  图片详情查看Fragment页面
 */
public class PhotoViewFragment extends BaseFragment
{

    public static final String ARG_LIST_IMAGE_URL = "list_image_url";
    public static final String ARG_POSITION = "position";

    public static PhotoViewFragment newInstance(ArrayList<String> imageUrls, int position)
    {
        PhotoViewFragment fragment = new PhotoViewFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(ARG_LIST_IMAGE_URL, imageUrls);
        bundle.putInt(ARG_POSITION, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    private ArrayList<String> mImageUrls;

    private HackyViewPager mViewPager;
    private TextView mTvText;

    private TextView mTvLeftTitle;

    private LinearLayout mLinearTitleContainer;

    private ImageView mIvBack;

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
        mViewPager = (HackyViewPager) view.findViewById(R.id.vp_viewpager);
//        int paddingLRTB = DensityUtils.dpToPx(mActivity, 30.f);
//        mViewPager.setPadding(0, paddingLRTB, 0, paddingLRTB);
        mTvText = (TextView) view.findViewById(R.id.tv_text);
        mTvLeftTitle = (TextView) view.findViewById(R.id.tv_left_title);
        mIvBack = (ImageView) view.findViewById(R.id.iv_photo_back);
        mLinearTitleContainer = (LinearLayout) view.findViewById(R.id.linear_photo_title_container);
        mLinearTitleContainer.setVisibility(View.GONE);
        mIvBack.setImageDrawable(new DrawerArrowDrawable.BackDrawerArrowDrawable(mActivity));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mImageUrls = getArguments().getStringArrayList(ARG_LIST_IMAGE_URL);
        int position = getArguments().getInt(ARG_POSITION);
        if (null != mImageUrls && !mImageUrls.isEmpty())
        {
            mTvText.setText((1 + position) + "/" + mImageUrls.size());
            mTvLeftTitle.setText((1 + position) + "/" + mImageUrls.size());
            mViewPager.setAdapter(new ImagePagerAdapter(mImageUrls));
            mViewPager.setCurrentItem(position);
            mViewPager.setOnPageChangeListener(
                    new ViewPager.OnPageChangeListener()
                    {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
                        {

                        }

                        @Override
                        public void onPageSelected(int position)
                        {
                            mTvText.setText((1 + position) + "/" + mImageUrls.size());
                            mTvLeftTitle.setText((1 + position) + "/" + mImageUrls.size());
                        }

                        @Override
                        public void onPageScrollStateChanged(int state)
                        {

                        }
                    });
        }
        setListener();
    }

    private void setListener()
    {
        mIvBack.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        mActivity.hideSoftKeyBorard();
                        mActivity.onBackPressed();
                    }
                });

    }

    @Override
    public String getFragmentTitle()
    {
        return getString(R.string.tv_fragment_user_account);
    }

    private class ImagePagerAdapter extends PagerAdapter
    {
        private ArrayList<String> imageUrls;

        public ImagePagerAdapter(ArrayList<String> imageUrls)
        {
            this.imageUrls = imageUrls;
        }

        @Override
        public int getCount()
        {
            return null == imageUrls ? 0 : imageUrls.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object)
        {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)
        {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            final PhotoView photoView = new PhotoView(container.getContext());
            String imageUrl = imageUrls.get(position);
            if (!imageUrl.startsWith(APIURL.BASE_API_URL))
            {
                imageUrl = APIURL.BASE_API_URL + imageUrl;
            }
            photoView.setTag(imageUrl);

            ImageLoader.getInstance().loadImage(
                    imageUrl, new AbsImageLoadingListener()
                    {
                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
                        {
                            if (imageUri.equals(photoView.getTag()))
                            {
                                photoView.setImageBitmap(loadedImage);
                            }
                        }
                    });
            photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            container.addView(photoView, layoutParams);
//            photoView.setOnClickListener(
//                    new View.OnClickListener()
//                    {
//                        public void onClick(View v)
//                        {
//                            mLinearTitleContainer.setVisibility(mLinearTitleContainer.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
//                        }
//                    });
            photoView.setOnViewTapListener(
                    new PhotoViewAttacher.OnViewTapListener()
                    {
                        @Override
                        public void onViewTap(View view, float x, float y)
                        {
                            mLinearTitleContainer.setVisibility(mLinearTitleContainer.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);

                        }
                    });
            return photoView;

        }
    }

}
