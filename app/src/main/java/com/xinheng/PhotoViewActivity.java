package com.xinheng;

import android.content.Intent;
import android.os.Bundle;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.PhotoViewFragment;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明：系统设置界面
 */
public class PhotoViewActivity extends BaseActivity
{
    public static final String EXTRA_IMAGE_URL = "image_url";

    public static void actionPhotoView(BaseActivity activity, String imageUrl)
    {
        Intent intent = new Intent(activity, PhotoViewActivity.class);
        intent.putExtra(EXTRA_IMAGE_URL, imageUrl);
        activity.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_common);
        String imageUrl = getIntent().getStringExtra(EXTRA_IMAGE_URL);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_container, PhotoViewFragment.newInstance(imageUrl)).commit();
    }

    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_photoview);
    }
}
