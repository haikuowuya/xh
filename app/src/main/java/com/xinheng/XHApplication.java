package com.xinheng;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.xinheng.base.BaseActivity;
import com.xinheng.crash.CrashWoodpecker;

import java.util.LinkedList;

/**
 * 应用程序的入口
 */
public class XHApplication extends android.app.Application
{
    private LinkedList<Activity> mActivities = new LinkedList<Activity>();
    private static XHApplication sInstance;

   // public RefWatcher mRefWatcher;

    @Override
    public void onCreate()
    {
        super.onCreate();
        sInstance = this;
        CrashWoodpecker.fly().to(this);
//        mRefWatcher = LeakCanary.install(this);
        DisplayImageOptions options = new DisplayImageOptions.Builder().showImageForEmptyUri(R.mipmap.ic_launcher).showImageOnLoading(R.mipmap.ic_launcher).showImageOnFail(R.mipmap.ic_launcher).delayBeforeLoading(0).cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY_STRETCHED).bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this).defaultDisplayImageOptions(options).threadPriority(Thread.NORM_PRIORITY).threadPoolSize(4).denyCacheImageMultipleSizesInMemory().memoryCache(new WeakMemoryCache()).discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).imageDownloader(new BaseImageDownloader(this, 30 * 1000, 30 * 1000)).build();
        ImageLoader.getInstance().init(configuration);
    }

    public static final XHApplication getInstance()
    {
        return sInstance;
    }

    /**
     * TODO 记录Activity
     */
    public void recordActivity(Activity activity)
    {
        mActivities.add(activity);
    }

    /**
     * TODO 退出APP
     */
    public void exitClient()
    {

        if (mActivities.size() > 0)
        {
            for (int i = 0; i < mActivities.size(); i++)
            {
                Activity activity = mActivities.get(i);
                if (null != activity)
                {
                    activity.finish();
                }
            }
        }
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.killBackgroundProcesses(getPackageName());
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(10);
    }

    public void showExitDialog(BaseActivity activity)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(activity).setTitle("提示").setMessage("确定退出吗").setPositiveButton(
                "确定", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                        exitClient();
                    }
                }).setNegativeButton(
                "取消", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                }).show();
    }

}
