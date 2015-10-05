package com.xinheng.util;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.xinheng.R;
import com.xinheng.base.BaseActivity;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created by Administrator on 2015/10/6 0006.
 */
public class UILUtils
{
    public static void config(BaseActivity activity)
    {
        if (!ImageLoader.getInstance().isInited())
        {
            DisplayImageOptions options = new DisplayImageOptions.Builder().showImageForEmptyUri(R.mipmap.ic_launcher).showImageOnLoading(R.mipmap.ic_launcher).showImageOnFail(R.mipmap.ic_launcher).delayBeforeLoading(0).cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY_STRETCHED).bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
            ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(activity).defaultDisplayImageOptions(options).threadPriority(Thread.NORM_PRIORITY).threadPoolSize(4).denyCacheImageMultipleSizesInMemory().memoryCache(new WeakMemoryCache()).discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).imageDownloader(new CustomImageDownloader(activity, 30 * 1000, 30 * 1000))
                    //  imageDownloader(new BaseImageDownloader(activity, 30 * 1000, 30 * 1000))
                    .build();
            ImageLoader.getInstance().init(configuration);
        }
    }

    public static class CustomImageDownloader extends BaseImageDownloader
    {
        private BaseActivity mBaseActivity;

        //universal image loader获取图片时,若需要cookie，
        // 需在application中进行配置添加此类。
        public CustomImageDownloader(BaseActivity activity)
        {
            super(activity);
            mBaseActivity = activity;
        }

        public CustomImageDownloader(BaseActivity baseActivity, int connectTimeout, int readTimeout)
        {
            super(baseActivity, connectTimeout, readTimeout);
            mBaseActivity = baseActivity;
        }

        @Override
        protected HttpURLConnection createConnection(String url, Object extra) throws IOException
        {
            // Super...
            HttpURLConnection connection = super.createConnection(url, extra);
            connection.setRequestProperty(Constants.SESSION_ID, mBaseActivity.getLoginSuccessItem().sessionId);
            connection.setRequestProperty(Constants.COOKIE, Constants.SID + mBaseActivity.getLoginSuccessItem().sessionId);
            connection.setRequestProperty(Constants.USER_AGENT_KEY, Constants.USER_AGENT_VALUE);
            //userId要客户端加密
            String encryptUserId = RSAUtil.clientEncrypt(mBaseActivity.getLoginSuccessItem().id);
//                    System.out.println("加密后的userId = " + encryptUserId);
            connection.setRequestProperty(Constants.USER_ID, encryptUserId);
            return connection;
        }
    }
}
