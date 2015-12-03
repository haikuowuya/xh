package com.xinheng.adapter.drug;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xinheng.APIURL;
import com.xinheng.R;
import com.xinheng.base.BaseActivity;
import com.xinheng.base.BaseAdapter;
import com.xinheng.base.ViewHolder;
import com.xinheng.common.AbsImageLoadingListener;
import com.xinheng.mvp.model.prescription.DrugItem;

import java.util.LinkedList;
import java.util.List;

/**
 * 药品搜索列表适配器
 */
public class DrugListAdapter extends BaseAdapter<DrugItem>
{
    private LinkedList<DrugItem> mSelectedItems = new LinkedList<>();

    public DrugListAdapter(BaseActivity activity, List<DrugItem> data)
    {
        super(activity, R.layout.list_drug_item, data);
    }

    public DrugListAdapter(BaseActivity activity, List<DrugItem> data, List<DrugItem> selectedItems)
    {
        super(activity, R.layout.list_drug_item, data);
        if (null != selectedItems && !selectedItems.isEmpty())
        {
            mSelectedItems.clear();
            mSelectedItems.addAll(selectedItems);
        }
    }

    public LinkedList<DrugItem> getSelectedItems()
    {
        return mSelectedItems;
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
            ImageLoader.getInstance().loadImage(
                    img, new AbsImageLoadingListener()
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
        final TextView tvAdd = ViewHolder.getView(convertView, R.id.tv_add);
        if (mSelectedItems.contains(drugItem))
        {
            tvAdd.setText("取消");
            tvAdd.setBackgroundResource(R.drawable.btn_cancle_click_gray_selector);
        } else
        {
            tvAdd.setText("加入处方");
            tvAdd.setBackgroundResource(R.drawable.btn_register_code_selector);
        }
        tvAdd.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        if ("取消".equals(tvAdd.getText().toString()))
                        {
                            mSelectedItems.remove(drugItem);
                            tvAdd.setText("加入处方");
                            tvAdd.setBackgroundResource(R.drawable.btn_register_code_selector);
                        } else
                        {
                            if (mSelectedItems != null && !mSelectedItems.isEmpty())
                            {
                                String name = "";
                                for (DrugItem item : mSelectedItems)
                                {
                                    name += item.name;
                                }
                                if (name.contains(BAI_SHAO))
                                {
                                    if (drugItem.name.equals(XUAN_SHEN) || drugItem.name.equals(XI_XIN))
                                    {
                                        getActivity().showToast(BAI_SHAO + " 与 " + drugItem.name + " 存在配伍禁忌，禁止搭配服用！");
                                        return;
                                    }
                                }

                                if (name.contains(XUAN_SHEN) || name.contains(XI_XIN))
                                {
                                    if (drugItem.name.equals(BAI_SHAO))
                                    {
                                        getActivity().showToast(name + " 与 " + drugItem.name + " 存在配伍禁忌，禁止搭配服用！");
                                        return;
                                    }
                                }
                                if (name.contains(GAN_CAO))
                                {
                                    if (drugItem.name.equals(HAI_ZAO))
                                    {
                                        getActivity().showToast(GAN_CAO + " 与 " + drugItem.name + " 存在配伍禁忌，禁止搭配服用！");
                                        return;
                                    }
                                }
                                if (name.contains(HAI_ZAO))
                                {
                                    if (drugItem.name.equals(GAN_CAO))
                                    {
                                        getActivity().showToast(HAI_ZAO + " 与 " + drugItem.name + " 存在配伍禁忌，禁止搭配服用！");
                                        return;
                                    }
                                }
                            }

                            if (drugItem.name.equals(SHU_DA_HUANG) ||drugItem.name.contains(CHAO_WANG_BU_LIU_XING) || drugItem.name.equals(JIU_CHAO_SHUI_ZHI))
                            {
                                new AlertDialog.Builder(getActivity()).setTitle("妊娠禁忌提示").setMessage(drugItem.name+"为孕妇禁忌药品，是否要继续加入您的处方？").setPositiveButton(
                                        "确定", new DialogInterface.OnClickListener()
                                        {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which)
                                            {
                                                tvAdd.setText("取消");
                                                mSelectedItems.add(drugItem);
                                                tvAdd.setBackgroundResource(R.drawable.btn_cancle_click_gray_selector);
                                                dialog.dismiss();

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
                                return;
                            }

                            tvAdd.setText("取消");
                            mSelectedItems.add(drugItem);
                            tvAdd.setBackgroundResource(R.drawable.btn_cancle_click_gray_selector);
                        }
                    }
                });
    }

    public static final String GAN_CAO = "甘草";
    public static final String BAI_SHAO = "白芍";
    public static final String HAI_ZAO = "海藻";
    public static final String XUAN_SHEN = "玄参";
    public static final String XI_XIN = "细辛";
    public static final String CHAO = "炒";
    public static final String SHU_DA_HUANG = "熟大黄";
    public static final String JIU_CHAO_SHUI_ZHI="酒炒水蛭"   ;
    public static final String CHAO_WANG_BU_LIU_XING="王不留行"   ;

}
