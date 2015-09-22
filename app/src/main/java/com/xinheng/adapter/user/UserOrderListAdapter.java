package com.xinheng.adapter.user;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xinheng.R;
import com.xinheng.base.BaseActivity;
import com.xinheng.base.BaseAdapter;
import com.xinheng.base.ViewHolder;
import com.xinheng.eventbus.OnDeleteUserOrderEvent;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.user.UserOrderItem;
import com.xinheng.mvp.presenter.DeleteUserOrderPresenter;
import com.xinheng.mvp.presenter.impl.DeleteUserOrderPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.DensityUtils;

import java.util.List;

import de.greenrobot.event.EventBus;

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
    public void bindDataToView(View convertView, final UserOrderItem item)
    {
        if (null != item)
        {
        LinearLayout linearDrugContainer = ViewHolder.getView(convertView , R.id.linear_drug_container);
            linearDrugContainer.removeAllViews();
            setTextViewText(convertView, R.id.tv_hospital_name, item.hospital);
            if (null != item.orderList && !item.orderList.isEmpty())
            {
                String countInfo =  "共" + item.orderList.size() + "件商品,合计：￥:";
                String countFeeInfo  =countInfo  + item.fee + "(含运费￥0.00)";
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(countFeeInfo);
                spannableStringBuilder.setSpan(new ForegroundColorSpan(0xFFFFA800),countInfo.length(), (countInfo+item.fee).length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                setTextViewText(convertView, R.id.tv_order_fee_info, spannableStringBuilder);
                for(UserOrderItem.OrderMedicalItem medicalItem :item.orderList)
                {
                    final View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_prescription_drug_item, null);  //TODO
                    RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.relative_normal);
                    LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linear_edit_container);
                    relativeLayout.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.GONE);
                    TextView tvDrugName = (TextView) relativeLayout.findViewById(R.id.tv_drug_name);
                    TextView tvDrugInfo = (TextView) relativeLayout.findViewById(R.id.tv_drug_info);
                    TextView tvDrugPrice = (TextView) relativeLayout.findViewById(R.id.tv_drug_price);
                    final TextView tvDrugCount = (TextView) relativeLayout.findViewById(R.id.tv_drug_count);
                    tvDrugName.setText(medicalItem.drugName);
                    tvDrugPrice.setText("￥" + medicalItem.unitPrice);
                    tvDrugCount.setText("x1");
                    String info = "包装规格：" + medicalItem.specs + "    产地：" + medicalItem.place + "\n生产厂家：" + medicalItem.producer;
                    tvDrugInfo.setText(info);
                    int height = DensityUtils.dpToPx(getActivity(), 100.f);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                    linearDrugContainer.addView(view, layoutParams);
                }
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
//
//          getActivity().showCroutonToast("删除订单");
                    DataView dataView = new DataView()
                    {
                        @Override
                        public void onGetDataSuccess(ResultItem resultItem,String requestTag)
                        {
                            if(resultItem != null)
                            {
                                getActivity().showCroutonToast(resultItem.message);
                                if(resultItem.success())
                                {
                                    EventBus.getDefault().post(new OnDeleteUserOrderEvent(item));
                                }

                            }
                        }

                        @Override
                        public void onGetDataFailured(String msg,String requestTag)
                        {
                            getActivity().showCroutonToast( msg );
                        }
                    };
                    DeleteUserOrderPresenter deleteUserOrderPresenter = new DeleteUserOrderPresenterImpl(getActivity(), dataView);
                    deleteUserOrderPresenter.doDeleteUserOrder(item.orderId);
                }
            });

        }
    }
}
