package com.xinheng;

import android.content.Intent;
import android.os.Bundle;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.PhotoViewFragment;

import java.util.ArrayList;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 13:48
 * 说明：t
 */
public class PhotoViewActivity extends BaseActivity
{

    public static final String EXTRA_LIST_IMAGE_URL = "list_image_url";
    public static final String  EXTRA_POSITION ="position";



    public static void actionPhotoView(BaseActivity activity, ArrayList<String> imageUrls,int position )
    {
        Intent intent = new Intent(activity, PhotoViewActivity.class);
        intent.putStringArrayListExtra(EXTRA_LIST_IMAGE_URL,imageUrls)  ;
        intent.putExtra(EXTRA_POSITION, position);
        activity.startActivity(intent);
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_common);

        ArrayList<String > imageUrls = getIntent().getStringArrayListExtra(EXTRA_LIST_IMAGE_URL);
        int position = getIntent().getIntExtra(EXTRA_POSITION,0);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content_container, PhotoViewFragment.newInstance(imageUrls,position)).commit();
    }

    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_photoview);
    }
}
