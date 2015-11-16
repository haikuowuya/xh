package com.xinheng.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xinheng.APIURL;
import com.xinheng.R;
import com.xinheng.ShoppingCartConfirmOrderActivity;
import com.xinheng.base.BaseFragment;
import com.xinheng.common.AbsImageLoadingListener;
import com.xinheng.eventbus.OnShoppingCartSumbitEvent;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.ShoppingCartItem;
import com.xinheng.mvp.presenter.ShoppingCartPresenter;
import com.xinheng.mvp.presenter.impl.ShoppingCartPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.DensityUtils;
import com.xinheng.util.GsonUtils;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：  购物车Fragment
 */
public class ShoppingCartFragment extends BaseFragment implements DataView
{
    public static final String REQUEST_GET_SHOPPING_CART_LIST_TAG = "get_shopping_cart_list";
    public static final String REQUEST_SAVE_SHOPPING_CART_TAG = "save_shopping_cart";

    public static final String TEXT_FINISHED = "完成";
    public static final String TEXT_EDIT = "编辑";

    public static ShoppingCartFragment newInstance()
    {
        ShoppingCartFragment fragment = new ShoppingCartFragment();
        return fragment;
    }

    private LinearLayout mLinearShoppingContainer;

    private View mLinearScrollViewContainer;

    /***
     * 用于复选框状态发生变化时计算的价格
     */
    private double mJSPrice = 0;

    private int mCheckedCount = 0;

    private TextView mTvShoppingCartInfo;
    private CheckBox mCbAll;
    private List<ShoppingCartItem> mShoppingCartItems;
    private TextView mTvBalance;
    private List<ShoppingCartItem.ListItem> mCheckedListItem = new LinkedList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_shopping_cart, null);  //TODO 布局文件
        initView(view);
        mIsInit = true;
        return view;
    }

    private void initView(View view)
    {
        mLinearShoppingContainer = (LinearLayout) view.findViewById(R.id.linear_shopping_container);
        mLinearScrollViewContainer = view.findViewById(R.id.linear_scrollview_container);
        mCbAll = (CheckBox) view.findViewById(R.id.cb_all);
        mTvShoppingCartInfo = (TextView) view.findViewById(R.id.tv_shopping_cart_info);
        mTvBalance = (TextView) view.findViewById(R.id.tv_balance);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
        setTextPrice(0);
        setListener();
    }

    @Subscribe
    public void onEventMainThread(OnShoppingCartSumbitEvent event)
    {
        if (null != event)
        {
            mActivity.showToast("购物车现已提交成功");
            mLinearScrollViewContainer.setVisibility(View.GONE);
            mActivity.finish();
        }
    }

    //===============================EVENT BUS========================
    @Override
    public void onDestroy()
    {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public String getFragmentTitle()
    {
        return getString(R.string.tv_fragment_ptr_list);
    }

    private void setListener()
    {
        mCbAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                    {
                        onAllCheckedChanged(isChecked);
                    }
                });
        mTvBalance.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (mCheckedCount == 0 || mCheckedListItem.isEmpty())
                {
                    mActivity.showToast("你还没有选择商品");
                    return;
                }
                ShoppingCartConfirmOrderActivity.actionShoppingCartConfirmOrder(mActivity, GsonUtils.toJson(mCheckedListItem), mJSPrice + "", mShoppingCartItems.get(0).hid);
            }
        });
    }

    @Override
    protected void doGetData()
    {
        ShoppingCartPresenter shoppingCartPresenter = new ShoppingCartPresenterImpl(mActivity, this, REQUEST_GET_SHOPPING_CART_LIST_TAG);
        shoppingCartPresenter.doGetShoppingCartList();
        mLinearScrollViewContainer.setVisibility(View.GONE);
    }

    @Override
    public void onGetDataSuccess(ResultItem resultItem, String requestTag)
    {
        if (null != resultItem)
        {
            mActivity.showToast(resultItem.message);
            if (resultItem.success())
            {
                if (REQUEST_GET_SHOPPING_CART_LIST_TAG.equals(requestTag))
                {
                    Type type = new TypeToken<List<ShoppingCartItem>>()
                    {
                    }.getType();
                    List<ShoppingCartItem> items = GsonUtils.jsonToResultItemToList(GsonUtils.toJson(resultItem), type);
                    if (null != items && !items.isEmpty())
                    {
                        mShoppingCartItems = items;
                        fillLinearShoppingContainer(items);
                    }
                    else
                    {
                        mActivity.showToast("购物车为空");
                    }
                }
                else if (REQUEST_SAVE_SHOPPING_CART_TAG.equals(requestTag))
                {
                    onEditFinsh();
                    if (mShoppingCartItems != null && mShoppingCartItems.get(0).list != null && mShoppingCartItems.get(0).list.isEmpty())
                    {
                        mLinearScrollViewContainer.setVisibility(View.GONE);
                        mActivity.showToast("购物车现已清空");
                    }
                }
            }
        }
    }

    @Override
    public void onGetDataFailured(String msg, String requestTag)
    {

    }

    private void onEdit()
    {

        for (int i = 0; i < mLinearShoppingContainer.getChildCount(); i++)
        {
            ViewGroup view = (ViewGroup) mLinearShoppingContainer.getChildAt(i);
            if (view.findViewById(R.id.relative_normal) != null)
            {
                view.findViewById(R.id.relative_normal).setVisibility(View.GONE);
                view.findViewById(R.id.linear_edit_container).setVisibility(View.VISIBLE);
            }
        }
    }

    private void onAllCheckedChanged(boolean isChecked)
    {
        if (null != mShoppingCartItems && null != mShoppingCartItems.get(0) && null != mShoppingCartItems.get(0).list)
        {
            if (isChecked || mCheckedCount == 0 || mCheckedCount == mShoppingCartItems.get(0).list.size())
            {
                mCbAll.setChecked(isChecked);
                if (mLinearShoppingContainer.findViewById(R.id.cb_hospital_all) != null)
                {
                    CheckBox checkBoxHospital = (CheckBox) mLinearShoppingContainer.findViewById(R.id.cb_hospital_all);
                    checkBoxHospital.setChecked(isChecked);
                }
                double price = 0;
                for (int i = 0; i < mLinearShoppingContainer.getChildCount(); i++)
                {
                    ViewGroup view = (ViewGroup) mLinearShoppingContainer.getChildAt(i);
                    if (view.findViewById(R.id.cb_cb) != null)
                    {
                        CheckBox checkBox = (CheckBox) view.findViewById(R.id.cb_cb);
                        checkBox.setChecked(isChecked);
                    }
                    if (isChecked)
                    {
                        if (view.findViewById(R.id.relative_normal) != null)
                        {
                            TextView tvDrugPrice = (TextView) view.findViewById(R.id.tv_drug_price);
                            final TextView tvEditCount = (TextView) view.findViewById(R.id.tv_edit_count);
                            if (null != tvDrugPrice && null != tvEditCount)
                            {
                                String itemPrice = tvDrugPrice.getText().toString();
                                if (itemPrice.contains("￥"))
                                {
                                    itemPrice = itemPrice.replaceAll("￥", "");
                                }
                                price += Double.parseDouble(itemPrice) * Double.parseDouble(tvEditCount.getText().toString());
                            }
                        }
                    }
                }
                if (isChecked)
                {
                    setTextPrice(price);
                    if (null != mShoppingCartItems && null != mShoppingCartItems.get(0) && null != mShoppingCartItems.get(0).list)
                    {
                        mCheckedCount = mShoppingCartItems.get(0).list.size();
                    }
                }

            }
        }
    }

    private void onEditFinsh()
    {
        if (null != mLinearShoppingContainer.findViewById(R.id.tv_edit))
        {
            ((TextView) mLinearShoppingContainer.findViewById(R.id.tv_edit)).setText(TEXT_EDIT);
        }
        for (int i = 0; i < mLinearShoppingContainer.getChildCount(); i++)
        {
            ViewGroup view = (ViewGroup) mLinearShoppingContainer.getChildAt(i);
            if (view.findViewById(R.id.relative_normal) != null)
            {
                view.findViewById(R.id.relative_normal).setVisibility(View.VISIBLE);
                view.findViewById(R.id.linear_edit_container).setVisibility(View.GONE);
            }
        }
    }

    /**
     * 将购物车中的药品展示出来
     *
     * @param items
     */
    private void fillLinearShoppingContainer(List<ShoppingCartItem> items)
    {
        if (null != items)
        {
            mLinearScrollViewContainer.setVisibility(View.VISIBLE);
            mLinearShoppingContainer.setVisibility(View.VISIBLE);
            mLinearShoppingContainer.removeAllViews();
            for (int i = 0; i < items.size(); i++)
            {
                final ShoppingCartItem item = items.get(i);
                final View viewHospital = LayoutInflater.from(mActivity).inflate(R.layout.layout_shopping_cart_hospital_item, null);  //TODO
                TextView tvHospital = (TextView) viewHospital.findViewById(R.id.tv_hospital_name);
                final TextView tvEdit = (TextView) viewHospital.findViewById(R.id.tv_edit);
                final CheckBox cbHospitalAll = (CheckBox) viewHospital.findViewById(R.id.cb_hospital_all);
                cbHospitalAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                        {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                            {
                                onAllCheckedChanged(isChecked);
                            }
                        });
                tvEdit.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
//                                if (item.list != null && !item.list.isEmpty())
//                                {
                                if (TEXT_FINISHED.equals(tvEdit.getText().toString()))
                                {
                                    doSave();
                                }
                                else
                                {
                                    tvEdit.setText(TEXT_FINISHED);
                                    onEdit();
                                }
//                                } else
//                                {
//                                    mActivity.showToast("购物车列表为空，无法进行编辑");
//                                }

                            }
                        });
                tvHospital.setText(item.hospital);
                mLinearShoppingContainer.addView(viewHospital);
                if (item.list != null && !item.list.isEmpty())
                {
                    for (int j = 0; j < item.list.size(); j++)
                    {
                        final ShoppingCartItem.ListItem listItem = item.list.get(j);
                        final View view = LayoutInflater.from(mActivity).inflate(R.layout.layout_shopping_cart_item, null);  //TODO
                        final ImageView ivImageView = (ImageView) view.findViewById(R.id.iv_image);
                        final CheckBox cbcb = (CheckBox) view.findViewById(R.id.cb_cb);
                        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.relative_normal);
                        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linear_edit_container);
                        relativeLayout.setVisibility(View.VISIBLE);
                        linearLayout.setVisibility(View.GONE);
                        TextView tvDrugName = (TextView) relativeLayout.findViewById(R.id.tv_drug_name);
                        TextView tvDrugInfo = (TextView) relativeLayout.findViewById(R.id.tv_drug_info);
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
                        TextView tvDrugPrice = (TextView) relativeLayout.findViewById(R.id.tv_drug_price);
                        final TextView tvDrugCount = (TextView) relativeLayout.findViewById(R.id.tv_drug_count);
                        tvDrugName.setText(listItem.name);
                        tvDrugPrice.setText("￥" + listItem.price);
                        tvDrugCount.setText("x1");
                        String info = "包装规格：" + listItem.specs + "    产地：" + listItem.place + "\n生产厂家：" + listItem.producer;
                        tvDrugInfo.setText(info);
                        final double cost = Double.parseDouble(listItem.price);
                        ImageView ivIncrease = (ImageView) linearLayout.findViewById(R.id.iv_edit_increase);
                        ImageView ivDecrease = (ImageView) linearLayout.findViewById(R.id.iv_edit_decrease);
                        TextView tvDelete = (TextView) linearLayout.findViewById(R.id.tv_edit_delete);
                        TextView tvUnit = (TextView) linearLayout.findViewById(R.id.tv_unit);
                        tvUnit.setText("包装规格：" + listItem.specs);
                        final TextView tvEditCount = (TextView) linearLayout.findViewById(R.id.tv_edit_count);
                        tvEditCount.setText("1");
                        final int finalI = i;
                        cbcb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                                {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                                    {
                                        double tmpPrice = Double.parseDouble(listItem.price) * Double.parseDouble(tvEditCount.getText().toString());
                                        if (isChecked)
                                        {
                                            mCheckedCount += 1;
                                            mJSPrice += tmpPrice;
                                            mCheckedListItem.add(listItem);
                                        }
                                        else
                                        {
                                            mCheckedCount -= 1;
                                            mJSPrice -= tmpPrice;
                                            mCheckedListItem.remove(listItem);
                                        }
                                        if (mJSPrice < 0)
                                        {
                                            mJSPrice = 0;
                                        }
                                        if (mCheckedCount < 0)
                                        {
                                            mCheckedCount = 0;
                                        }
                                        mCbAll.setChecked(mCheckedCount == mShoppingCartItems.get(finalI).list.size());
                                        cbHospitalAll.setChecked(mCheckedCount == mShoppingCartItems.get(finalI).list.size());
                                        setTextPrice(mJSPrice);
                                    }
                                });
                        ivIncrease.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View v)
                                    {
                                        int count = Integer.parseInt(tvEditCount.getText().toString());
                                        count += 1;
                                        if (cbcb.isChecked())
                                        {
                                            mJSPrice += cost;
                                        }
                                        tvEditCount.setText(count + "");
                                        tvDrugCount.setText("x" + count + "");
                                        setTextPrice(mJSPrice);
                                    }
                                });
                        ivDecrease.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View v)
                                    {
                                        int count = Integer.parseInt(tvEditCount.getText().toString());
                                        count -= 1;
                                        if (count < 1)
                                        {
                                            mActivity.showCroutonToast("数量不可以小于1");
                                            return;
                                        }
                                        if (cbcb.isChecked())
                                        {
                                            mJSPrice -= cost;
                                        }
                                        tvEditCount.setText(count + "");
                                        tvDrugCount.setText("x" + count + "");
                                        setTextPrice(mJSPrice);
                                    }
                                });

                        tvDelete.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View v)
                                    {
                                        if (view.getParent() != null)
                                        {
                                            int count = Integer.parseInt(tvEditCount.getText().toString());
                                            if (cbcb.isChecked())
                                            {
                                                mJSPrice = mJSPrice - cost * count;
                                                mCheckedCount -= 1;
                                                mCheckedListItem.remove(listItem);
                                            }

                                            mLinearShoppingContainer.removeView(view);
                                            setTextPrice(mJSPrice);
                                            mShoppingCartItems.get(finalI).list.remove(listItem);
                                        }
                                        if (mShoppingCartItems.get(finalI).list.isEmpty())
                                        {
                                            // tvEdit.setText(TEXT_EDIT);
                                        }
                                    }
                                });
                        int height = DensityUtils.dpToPx(mActivity, 100.f);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                        mLinearShoppingContainer.addView(view, layoutParams);
                    }
                }
            }
        }
    }

    private void doSave()
    {
        List<String> drugIds = new LinkedList<>();
        List<String> counts = new LinkedList<>();
        if (null != mShoppingCartItems && null != mShoppingCartItems.get(0) && null != mShoppingCartItems.get(0).list)
        {
            for (ShoppingCartItem.ListItem listItem : mShoppingCartItems.get(0).list)
            {
                drugIds.add(listItem.drugId);
            }
            for (int i = 0; i < mLinearShoppingContainer.getChildCount(); i++)
            {
                ViewGroup view = (ViewGroup) mLinearShoppingContainer.getChildAt(i);
                if (view.findViewById(R.id.relative_normal) != null)
                {
                    final TextView tvEditCount = (TextView) view.findViewById(R.id.tv_edit_count);
                    counts.add(tvEditCount.getText().toString());
                }
            }
        }
        System.out.println("drugids = " + drugIds + " counts = " + counts);
        ShoppingCartPresenter shoppingCartPresenter = new ShoppingCartPresenterImpl(mActivity, this, REQUEST_SAVE_SHOPPING_CART_TAG);
        shoppingCartPresenter.doEditSaveShoppingCart(drugIds, counts);
    }

    private void setTextPrice(double price)
    {

        String HJ = "合计：";
        String hj_fee = HJ + "￥" + price + "  ";
        String dispatchFeeStr = "\n含运费（0.00）";
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(hj_fee + dispatchFeeStr);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(0xFFFFA800), HJ.length(), hj_fee.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(0xFF999999), hj_fee.length(), spannableStringBuilder.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        mTvShoppingCartInfo.setText(spannableStringBuilder);
        mTvBalance.setText("结算（" + mCheckedCount + "）");
    }

}
