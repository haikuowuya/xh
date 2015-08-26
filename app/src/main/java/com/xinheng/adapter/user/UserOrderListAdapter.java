package com.xinheng.adapter.user;

import android.view.View;

import com.xinheng.R;
import com.xinheng.base.BaseActivity;
import com.xinheng.base.BaseAdapter;
import com.xinheng.mvp.model.UserOrderItem;

import java.util.List;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/24 0024
 * 时间： 13:50
 * 说明：  我的订单列表适配器
 */
public class UserOrderListAdapter extends BaseAdapter<UserOrderItem>
{
    public UserOrderListAdapter(BaseActivity activity, List<UserOrderItem> data)
    {
        super(activity, R.layout.list_user_order_item, data);
    }

    @Override
    public void bindDataToView(View convertView, UserOrderItem item)
    {
        if (null != item)
        {
            setTextViewText(convertView, R.id.tv_hospital_name, item.hospital);
            if (null != item.orderlist && !item.orderlist.isEmpty())
            {
                UserOrderItem.OrderMedicalItem medicalItem = item.orderlist.get(0);
                setTextViewText(convertView, R.id.tv_durg_name, medicalItem.drugName);
                String info = "包装规格：" + medicalItem.specs + "    产地：" + medicalItem.place + "\n" +
                        "生产厂家：" + medicalItem.producer;
                String fee = "共" + medicalItem.count + "件商品,合计：￥：" + item.fee + " （含运费￥0.00）";
                setTextViewText(convertView, R.id.tv_durg_info, info);
                setTextViewText(convertView, R.id.tv_order_fee_info, fee);
                setTextViewText(convertView, R.id.tv_durg_price, medicalItem.unitPrice);
                setTextViewText(convertView, R.id.tv_durg_count, "X " + medicalItem.count);
            }
            /***
             * 评价按钮点击事件
             */
            setViewOnClick(convertView, R.id.tv_evaluation, new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    getActivity().showCroutonToast("评价");
                }
            });
            /***
             * 删除订单按钮点击事件
             */
            setViewOnClick(convertView, R.id.tv_del_order, new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    getActivity().showCroutonToast("删除订单");
                }
            });
        }
    }
}
