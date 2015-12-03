package com.xinheng.adapter.drug;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xinheng.APIURL;
import com.xinheng.R;
import com.xinheng.base.BaseActivity;
import com.xinheng.base.BaseAdapter;
import com.xinheng.base.ViewHolder;
import com.xinheng.common.AbsImageLoadingListener;
import com.xinheng.mvp.model.prescription.DrugItem;

import java.util.List;

/**
 * 药品搜索结果列表适配器
 */
public class SearchDrugResultListAdapter extends BaseAdapter<DrugItem>
{
    public SearchDrugResultListAdapter(BaseActivity activity, List<DrugItem> data)
    {
        super(activity, R.layout.list_search_drug_result_item, data);
    }

    @Override
    public void bindDataToView(View convertView, final DrugItem drugItem)
    {
        setTextViewText(convertView, R.id.tv_drug_name, drugItem.name);
        String drugInfo = "产地:" + drugItem.place + " 规格:" + drugItem.specs + " 价格:" + drugItem.cost;
        setTextViewText(convertView, R.id.tv_drug_info, drugInfo);
        final ImageView imageView = ViewHolder.getView(convertView, R.id.civ_image);
        String img = drugItem.img;
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
