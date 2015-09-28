package com.xinheng.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xinheng.APIURL;
import com.xinheng.FindMedicalActivity;
import com.xinheng.PrescriptionActivity;
import com.xinheng.R;
import com.xinheng.UserOrderActivity;
import com.xinheng.adapter.main.AdPagerAdapter;
import com.xinheng.adapter.online.OnlineCenterGridAdapter;
import com.xinheng.adapter.online.OnlineViewPagerAdapter;
import com.xinheng.base.AbsImageLoadingListener;
import com.xinheng.base.BaseAdapter;
import com.xinheng.base.BaseFragment;
import com.xinheng.mvp.model.AdItem;
import com.xinheng.mvp.model.HomeGridItem;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.online.HomeOnLineItem;
import com.xinheng.mvp.model.online.OnLineBottomItem;
import com.xinheng.mvp.model.online.OnLineCenterItem;
import com.xinheng.mvp.presenter.OnLinePresenter;
import com.xinheng.mvp.presenter.impl.OnLinePresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.ACache;
import com.xinheng.util.DensityUtils;
import com.xinheng.util.GsonUtils;
import com.xinheng.view.CustomGridView;
import com.xinheng.view.InfiniteViewPagerIndicatorView;
import com.xinheng.view.TabViewPagerIndicator;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/17 0017
 * 时间： 17:38
 * 说明： 在线售药内容
 */
public class OnlineFragment extends BaseFragment implements DataView
{

    /**
     * 顶部的滚动广告位置
     */
    private InfiniteViewPagerIndicatorView mInfiniteViewPagerIndicatorView;
    private ScrollView mScrollView;
    /**
     * 中间的四个功能按钮
     */
    private CustomGridView mCustomGridView;
    /***
     * 顶部的布局包含，上面的滑动Viewpager和4个功能按钮
     */
    private LinearLayout mLinearTopContainer;

    /**
     * 功能按钮下面的布局(中间布局)
     */
    private LinearLayout mLinearCenterContainer;
    /**
     * 最下面的左右滑动的布局
     */
    private TabViewPagerIndicator mTabViewPagerIndicator;

    private PtrClassicFrameLayout mPtrClassicFrameLayout;

    public static OnlineFragment newInstance()
    {
        OnlineFragment fragment = new OnlineFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_online, null);//TODO
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        mPtrClassicFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.ptr_scrollview_container);
        mInfiniteViewPagerIndicatorView = (InfiniteViewPagerIndicatorView) view.findViewById(R.id.infinite_viewpager);
        mTabViewPagerIndicator = (TabViewPagerIndicator) view.findViewById(R.id.tab_viewpager_online_indicator);
        mLinearCenterContainer = (LinearLayout) view.findViewById(R.id.linear_online_center_container);
        mLinearTopContainer = (LinearLayout) view.findViewById(R.id.linear_online_top_container);
        mScrollView = (ScrollView) view.findViewById(R.id.sv_scrollview);
        mCustomGridView = (CustomGridView) view.findViewById(R.id.custom_gridview);
        fillTopContainer(null);
        fillCenterContainer(null);
        fillBottomContainer(null);
    }

    private void fillTopContainer(List<AdItem> adItems)
    {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mInfiniteViewPagerIndicatorView.getLayoutParams();
        layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
        layoutParams.height = (int) getResources().getDimension(R.dimen.dimen_home_ad_height);
        mInfiniteViewPagerIndicatorView.setLayoutParams(layoutParams);
        mInfiniteViewPagerIndicatorView.setViewPagerAdapter(genAdapter(adItems));
        mInfiniteViewPagerIndicatorView.stopAutoCycle();
        mInfiniteViewPagerIndicatorView.startAutoCycle();
        mCustomGridView.setAdapter(genGridAdapter());
    }

    private void fillCenterContainer(List<OnLineCenterItem> centerItems)
    {
        mLinearCenterContainer.removeAllViews();

        if (null != centerItems && !centerItems.isEmpty())
        {
            for (int i = 0; i < centerItems.size(); i++)
            {
                OnLineCenterItem item = centerItems.get(i);
                View view = LayoutInflater.from(mActivity).inflate(R.layout.layout_online_center_item, null);
                TextView tvName = (TextView) view.findViewById(R.id.tv_name);
                final ImageView imageView0 = (ImageView) view.findViewById(R.id.iv_0);
                final ImageView imageView1 = (ImageView) view.findViewById(R.id.iv_1);
                final ImageView imageView2 = (ImageView) view.findViewById(R.id.iv_2);
                tvName.setText(item.name);
                if (item.items != null && !item.items.isEmpty())
                {
                    for (int ii = 0; ii < item.items.size(); ii++)
                    {
                        HomeOnLineItem.Item adItem = item.items.get(ii);
                        String img = adItem.img;
                        if (!TextUtils.isEmpty(img))
                        {
                            if (!img.startsWith(APIURL.BASE_API_URL))
                            {
                                img = APIURL.BASE_API_URL + img;
                            }
                            if (ii == 0)
                            {
                                ImageLoader.getInstance().loadImage(
                                        img, new AbsImageLoadingListener()
                                        {
                                            @Override
                                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
                                            {
                                                if (null != loadedImage)
                                                {
                                                    imageView0.setImageBitmap(loadedImage);
                                                }
                                            }
                                        });

                            } else if (ii == 1)
                            {
                                ImageLoader.getInstance().loadImage(
                                        img, new AbsImageLoadingListener()
                                        {
                                            @Override
                                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
                                            {
                                                if (null != loadedImage)
                                                {
                                                    imageView1.setImageBitmap(loadedImage);
                                                }
                                            }
                                        });

                            } else if (ii == 2)
                            {
                                ImageLoader.getInstance().loadImage(
                                        img, new AbsImageLoadingListener()
                                        {
                                            @Override
                                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
                                            {
                                                if (null != loadedImage)
                                                {
                                                    imageView2.setImageBitmap(loadedImage);
                                                }
                                            }
                                        });
                            }
                        }
                    }
                }
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.bottomMargin = DensityUtils.dpToPx(mActivity, 10.f);
                mLinearCenterContainer.addView(view, layoutParams);
            }
        }

    }

    private void fillBottomContainer(List<OnLineBottomItem> bottomItems)
    {
        if (null != bottomItems && !bottomItems.isEmpty())
        {
            mTabViewPagerIndicator.getIndicator().setVisibility(View.GONE);
            OnlineViewPagerAdapter pagerAdapter = new OnlineViewPagerAdapter(getChildFragmentManager(), bottomItems);
            mTabViewPagerIndicator.setViewPagerAdapter(pagerAdapter);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mCustomGridView.setOnItemClickListener(
                new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        if (position == 0)//按方抓药
                        {
                            PrescriptionActivity.actionGetMedical(mActivity);
                        }
                        if (position == 1)    //轻松找药
                        {
                            FindMedicalActivity.actionFindMedical(mActivity);
                        }
                        if (position == 3)//我的订单
                        {
                            UserOrderActivity.actionUserOrder(mActivity);
                        }
                    }
                });

        mPtrClassicFrameLayout.disableWhenHorizontalMove(true);
        mPtrClassicFrameLayout.setPtrHandler(
                new PtrDefaultHandler()
                {
                    @Override
                    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header)
                    {
                        return mScrollView.getScrollY() == 0;
                    }

                    public void onRefreshBegin(PtrFrameLayout ptrFrameLayout)
                    {
                        doGetData();
                    }
                });
        String cacheJson = ACache.get(mActivity).getAsString(mActivity.getActivityTitle().toString());
        if (!TextUtils.isEmpty(cacheJson))
        {
            HomeOnLineItem homeOnLineItem = GsonUtils.jsonToClass(cacheJson, HomeOnLineItem.class);
            if (null != homeOnLineItem)
            {
                fillHomeOnLineItemDataToView(homeOnLineItem);
            }
        }
        doGetData();
    }

    public boolean canDoRefresh()
    {
        //  return mScrollView.getScrollY() == 0;
        return false;
    }

    @Override
    protected void doGetData()
    {
        OnLinePresenter onLinePresenter = new OnLinePresenterImpl(mActivity, this);
        onLinePresenter.doGetOnLine();
    }

    private BaseAdapter genGridAdapter()
    {
        List<HomeGridItem> data = new LinkedList<>();
        data.add(new HomeGridItem(R.mipmap.ic_online_0, "按方抓药"));
        data.add(new HomeGridItem(R.mipmap.ic_online_1, "轻松找药"));
        data.add(new HomeGridItem(R.mipmap.ic_online_2, "购物车"));
        data.add(new HomeGridItem(R.mipmap.ic_online_3, "我的订单"));
        OnlineCenterGridAdapter gridAdapter = new OnlineCenterGridAdapter(mActivity, data);
        return gridAdapter;
    }

    private PagerAdapter genAdapter(List<AdItem> items)
    {
        if (items == null || items.isEmpty())
        {
            items = new LinkedList<>();
            items.add(new AdItem(null));
            items.add(new AdItem(null));
            items.add(new AdItem(null));
            items.add(new AdItem(null));
        }
        AdPagerAdapter adPagerAdapter = new AdPagerAdapter(mActivity, items);
        return adPagerAdapter;
    }

    @Override
    public void onGetDataSuccess(ResultItem resultItem, String requestTag)
    {
        mPtrClassicFrameLayout.refreshComplete();
        if (null != resultItem)
        {
            mActivity.showToast(resultItem.message);
        }
        handleResultItem(resultItem);
    }

    private void handleResultItem(ResultItem resultItem)
    {
        HomeOnLineItem homeOnLineItem = new HomeOnLineItem();
        // resultItem = GsonUtils.jsonToClass(HomeOnLineItem.DEBUG_SUCCESS, ResultItem.class);
        if (null != resultItem && resultItem.properties.isJsonObject())
        {
            JsonObject jsonObject = resultItem.properties.getAsJsonObject();
            if (jsonObject != null)
            {
                Type adType = new TypeToken<List<AdItem>>()
                {
                }.getType();
                Type subjectType = new TypeToken<List<OnLineCenterItem>>()
                {
                }.getType();
                Type listType = new TypeToken<List<OnLineBottomItem>>()
                {
                }.getType();
                List<AdItem> advertisement = GsonUtils.jsonToList(jsonObject.getAsJsonArray(HomeOnLineItem.KEY_ADVERTISEMENT).toString(), adType);
                List<OnLineCenterItem> subject = GsonUtils.jsonToList(jsonObject.getAsJsonArray(HomeOnLineItem.KEY_SUBJECT).toString(), subjectType);
                List<OnLineBottomItem> list = GsonUtils.jsonToList(jsonObject.getAsJsonArray(HomeOnLineItem.KEY_LIST).toString(), listType);
                homeOnLineItem.advertisement = advertisement;
                homeOnLineItem.subject = subject;
                homeOnLineItem.list = list;
            }
            fillHomeOnLineItemDataToView(homeOnLineItem);
            ACache.get(mActivity).put(mActivity.getActivityTitle().toString(), GsonUtils.toJson(homeOnLineItem));
        }
    }

    /***
     * 将后台获取到的数据解析后与对应的View绑定后显示，
     *
     * @param homeOnLineItem
     */
    private void fillHomeOnLineItemDataToView(HomeOnLineItem homeOnLineItem)
    {
        if (homeOnLineItem != null)
        {
            fillTopContainer(homeOnLineItem.advertisement);
            fillCenterContainer(homeOnLineItem.subject);
            fillBottomContainer(homeOnLineItem.list);
        }

    }

    @Override
    public void onGetDataFailured(String msg, String requestTag)
    {
        mPtrClassicFrameLayout.refreshComplete();
        mActivity.showToast(msg);
        handleResultItem(null);
    }

    @Override
    public String getFragmentTitle()
    {
        return getString(R.string.tv_fragment_main);
    }
}
