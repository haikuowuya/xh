package com.xinheng.adapter.shopping;

import android.graphics.Bitmap;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xinheng.APIURL;
import com.xinheng.R;
import com.xinheng.base.BaseActivity;
import com.xinheng.base.BaseAdapter;
import com.xinheng.base.ViewHolder;
import com.xinheng.common.AbsImageLoadingListener;
import com.xinheng.mvp.model.ShoppingCartItem;
import com.xinheng.util.DensityUtils;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/24 0024
 * 时间： 13:50
 * 说明：  购物车列表适配器
 */
public class ShoppingCartListAdapter extends BaseAdapter<ShoppingCartItem>
{
    public ShoppingCartListAdapter(BaseActivity activity, List<ShoppingCartItem> data)
    {
        super(activity, R.layout.list_shopping_cart_item, data);
    }

    @Override
    public void bindDataToView(View convertView, final ShoppingCartItem item)
    {
        if (null != item)
        {
            LinearLayout linearDrugContainer = ViewHolder.getView(convertView, R.id.linear_drug_container);
            ((View)ViewHolder.getView(convertView, R.id.tv_del_order).getParent()).setVisibility(View.GONE);
            linearDrugContainer.removeAllViews();
            setTextViewText(convertView, R.id.tv_hospital_name, item.hospital);
            if (null != item.list && !item.list.isEmpty())
            {
                double mFee = 0.0;
                for (ShoppingCartItem.ListItem listItem : item.list)
                {
                    final View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_prescription_drug_item, null);  //TODO
                    final ImageView ivImageView = (ImageView) view.findViewById(R.id.iv_image);
                    RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.relative_normal);
                    LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linear_edit_container);
                    relativeLayout.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.GONE);
                    TextView tvDrugName = (TextView) relativeLayout.findViewById(R.id.tv_drug_name);
                    TextView tvDrugInfo = (TextView) relativeLayout.findViewById(R.id.tv_drug_info);
                    TextView tvDrugPrice = (TextView) relativeLayout.findViewById(R.id.tv_drug_price);
                    String img = listItem.drugImg;
                    if (!TextUtils.isEmpty(img))
                    {
                        if (!img.startsWith(APIURL.BASE_API_URL))
                        {
                            img = APIURL.BASE_API_URL + img;
                        }
                        ivImageView.setTag(img);
                        ImageLoader.getInstance().loadImage(img, new AbsImageLoadingListener()
                        {
                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
                            {
                                if (null != loadedImage && imageUri.equals(ivImageView.getTag()))
                                {
                                    ivImageView.setImageBitmap(loadedImage);
                                }
                            }
                        });
                    }

                    final TextView tvDrugCount = (TextView) relativeLayout.findViewById(R.id.tv_drug_count);
                    tvDrugName.setText(listItem.name);
                    tvDrugPrice.setText("￥" + listItem.price);
                    tvDrugCount.setText("x1");
                    String info = "包装规格：" + listItem.specs + "    产地：" + listItem.place + "\n生产厂家：" + listItem.producer;
                    tvDrugInfo.setText(info);
                    int height = DensityUtils.dpToPx(getActivity(), 100.f);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                    linearDrugContainer.addView(view, layoutParams);
                    mFee=  mFee + Double.parseDouble(listItem.price);
                }

                String countInfo = "共" + item.list.size() + "件商品,合计：￥:";
                String despatchFee = "0.00";
//                if (!TextUtils.isEmpty(item.despatchFee))
//                {
//                    despatchFee = item.despatchFee;
//                }

                try
                {
                    mFee = mFee+ Double.parseDouble(despatchFee);
                    mFee = Double.parseDouble(new DecimalFormat("#.00").format(mFee));
                }
                catch (Exception e)
                {

                }
                String countFeeInfo = countInfo + mFee + "(含运费￥" + despatchFee + ")";
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(countFeeInfo);
                spannableStringBuilder.setSpan(new ForegroundColorSpan(0xFFFFA800), countInfo.length(), (countInfo + mFee).length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                setTextViewText(convertView, R.id.tv_order_fee_info, spannableStringBuilder);
            }

        }
    }
}
