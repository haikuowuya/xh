package com.xinheng.adapter.user;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xinheng.APIURL;
import com.xinheng.PhotoViewActivity;
import com.xinheng.R;
import com.xinheng.base.BaseActivity;
import com.xinheng.base.BaseAdapter;
import com.xinheng.base.ViewHolder;
import com.xinheng.common.AbsImageLoadingListener;
import com.xinheng.mvp.model.ImageItem;
import com.xinheng.view.CircularImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steven on 2015/9/25 0025.
 */
public class UserImageGridAdapter extends BaseAdapter<ImageItem>
{
    private ArrayList<String> imageUrls = new ArrayList<>();

    public UserImageGridAdapter(BaseActivity activity, List<ImageItem> data)
    {
        super(activity, R.layout.grid_subscribe_image_item, data);
        if (null != data && !data.isEmpty())
        {
            for (ImageItem item : data)
            {
                imageUrls.add(item.imgUrl);
            }
        }
    }

    @Override
    public void bindDataToView(View convertView, ImageItem item)
    {
        final CircularImageView circularImageView = ViewHolder.getView(convertView, R.id.civ_image);
        if (TextUtils.isEmpty(item.imgUrl))
        {
            circularImageView.setImageResource(R.mipmap.ic_add_recipe);
        } else
        {
            //    circularImageView.setImageBitmap(BitmapFactory.decodeFile(s));
            String photo = item.imgUrl;
            if (!TextUtils.isEmpty(photo))
            {
                if (!photo.startsWith(APIURL.BASE_API_URL))
                {
                    photo = APIURL.BASE_API_URL + photo;
                }
                // System.out.println("photo = " + photo);
                circularImageView.setTag(photo);
                ImageLoader.getInstance().loadImage(
                        photo, new AbsImageLoadingListener()
                        {
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
                            {
                                if (null != loadedImage && imageUri.equals(circularImageView.getTag()))
                                {
                                    circularImageView.setImageBitmap(loadedImage);
                                    notifyDataSetChanged();
                                }
                            }
                        });
            }

            circularImageView.setOnClickListener(
                    new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            PhotoViewActivity.actionPhotoView(getActivity(), imageUrls, getPosition());
                          //  System.out.println(" adapter  position = " + getPosition());

                        }
                    });
        }
    }
}
