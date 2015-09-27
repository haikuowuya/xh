package com.xinheng.adapter.online;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xinheng.APIURL;
import com.xinheng.R;
import com.xinheng.base.AbsImageLoadingListener;
import com.xinheng.base.BaseActivity;
import com.xinheng.base.BaseAdapter;
import com.xinheng.base.ViewHolder;
import com.xinheng.mvp.model.online.HomeOnLineItem;

import java.util.List;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/17 0017
 * 时间： 18:39
 * 说明：在线售药最下面的grid
 */
public class OnlineBottomGridAdapter extends BaseAdapter<HomeOnLineItem.Item>
{
    public OnlineBottomGridAdapter(BaseActivity activity, List<HomeOnLineItem.Item> adItems)
    {
        super(activity, R.layout.grid_online_bottom_item, adItems);
    }

    @Override
    public void bindDataToView(View convertView, HomeOnLineItem.Item item)
    {
        setTextViewText(convertView, R.id.tv_text, item.title);
        final ImageView imageView = ViewHolder.getView(convertView, R.id.iv_image);

        String img = item.img;
        if (!TextUtils.isEmpty(img))
        {
            if (!img.startsWith(APIURL.BASE_API_URL))
            {
                img = APIURL.BASE_API_URL + img;
            }
            imageView.setTag(img);
            ImageLoader.getInstance().loadImage(img, new AbsImageLoadingListener()
            {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
                {
                    if (null != loadedImage && imageUri.equals(imageView.getTag()))
                    {
                        imageView.setImageBitmap(loadedImage);
                    }
                }
            });

        }
    }
}
