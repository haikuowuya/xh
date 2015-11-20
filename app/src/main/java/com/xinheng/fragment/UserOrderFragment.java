package com.xinheng.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.xinheng.R;
import com.xinheng.UserOrderDetailActivity;
import com.xinheng.adapter.user.UserOrderListAdapter;
import com.xinheng.base.BaseFragment;
import com.xinheng.eventbus.OnDeleteUserOrderEvent;
import com.xinheng.eventbus.OnOrderStatusChangedEvent;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.user.UserOrderItem;
import com.xinheng.mvp.presenter.UserOrderPresenter;
import com.xinheng.mvp.presenter.UserOrderSearchPresenter;
import com.xinheng.mvp.presenter.impl.UserOrderPresenterImpl;
import com.xinheng.mvp.presenter.impl.UserOrderSearchPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.ptr.PullToRefreshBase;
import com.xinheng.ptr.PullToRefreshListView;
import com.xinheng.util.Constants;
import com.xinheng.util.DensityUtils;
import com.xinheng.util.GsonUtils;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 作者：  libo
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：  我的订单列表
 */
public class UserOrderFragment extends BaseFragment implements DataView
{
    private static final String DATA = UserOrderItem.DEBUG_SUCCESS;
    public static final String ARG_ORDER_STATUS = "order_status";

    public static UserOrderFragment newInstance()
    {
        return newInstance(UserOrderItem.ORDER_STATUS_0);
    }

    public static UserOrderFragment newInstance(String orderStatus)
    {
        UserOrderFragment fragment = new UserOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_ORDER_STATUS, orderStatus);
        fragment.setArguments(bundle);
        return fragment;
    }

    private PtrClassicFrameLayout mPtrClassicFrameLayout;
    private ListView mListView;
    private LinkedList<UserOrderItem> mUserOrderItems = new LinkedList<>();
    private UserOrderListAdapter mUserOrderListAdapter;

    /***
     * 请求的当前页数
     */
    private int mCurrentPage = 0;
    /***
     * 是否在加载数据的标志
     */
    private boolean mIsLoadingData = false;
    /**
     * 搜索按钮
     */
    private ImageView mIvSearch;
    /**
     * 搜索框
     */
    private EditText mEtSearch;

    /**
     * 订单的当前状态
     */
    private String mOrderStatus = UserOrderItem.ORDER_STATUS_0;

    /***
     * 搜索HeaderView 布局
     */
    private View mHeaderView;
    private View mFooterView;

    private PullToRefreshListView mPullToRefreshListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_user_order, null);
        initView(view);
        mIsInit = true;
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
        setListener();
        mOrderStatus = getArguments().getString(ARG_ORDER_STATUS, mOrderStatus);
        mPtrClassicFrameLayout.disableWhenHorizontalMove(true);
        mPtrClassicFrameLayout.setPtrHandler(
                new PtrDefaultHandler()
                {
                    @Override
                    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header)
                    {
//                        return mListView.getFirstVisiblePosition() == 0;
                    return  false;
                    }


                    public void onRefreshBegin(PtrFrameLayout ptrFrameLayout)
                    {
                        mCurrentPage = 0;
                        doRefresh();
                    }
                });
//        mListView.setAdapter(ArrayAdapter.createFromResource(mActivity, R.array.array_menu, android.R.layout.simple_list_item_activated_1));
        mListView.setSelector(new ColorDrawable(0x00000000));
        mListView.setDividerHeight(DensityUtils.dpToPx(mActivity, 10.f));
        mListView.setDivider(new ColorDrawable(0x00000000));

    }

    private void setListener()
    {
        mIvSearch.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        doSearch();
                    }
                });
    }

    //===============================EVENT BUS========================
    @Subscribe
    public void onEventMainThread(OnDeleteUserOrderEvent event)
    {
        if (null != event && null != event.mUserOrderItem && null != mUserOrderListAdapter && mUserOrderItems.contains(event.mUserOrderItem))
        {
            mUserOrderItems.remove(event.mUserOrderItem);
            mUserOrderListAdapter.notifyDataSetChanged();
        }
    }

    //订单状态发生变化事件未付款 --> 付款 (从订单详情->订单付款)
    @Subscribe
    public void onEventMainThread(OnOrderStatusChangedEvent event)
    {
        if (null != event)
        {
            doRefresh();
        }
    }

    //===============================EVENT BUS========================

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /***
     * 点击搜索按钮的事件
     */
    private void doSearch()
    {
        String searchText = mEtSearch.getText().toString();
        if (TextUtils.isEmpty(searchText))
        {
            mActivity.showCroutonToast("搜索关键字不可以为空");
            return;
        }
        mActivity.hideSoftKeyBorard(mEtSearch);
        mUserOrderItems.clear();
        mUserOrderListAdapter.notifyDataSetChanged();
        UserOrderSearchPresenter userOrderSearchPresenter = new UserOrderSearchPresenterImpl(mActivity, this);
        userOrderSearchPresenter.doGetUserOrderSearch(searchText, mOrderStatus);
    }

    private void initView(View view)
    {
        mListView = (ListView) view.findViewById(R.id.lv_listview);
        mPullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.ptr_listview);
        mPtrClassicFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.ptr_scrollview_container);
        mHeaderView = LayoutInflater.from(mActivity).inflate(R.layout.layout_user_order_search, null);
        mIvSearch = (ImageView) mHeaderView.findViewById(R.id.iv_search);
        mEtSearch = (EditText) mHeaderView.findViewById(R.id.et_search);
        mFooterView = LayoutInflater.from(mActivity).inflate(R.layout.layout_listview_footer, null);
        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
    }

    @Override
    public String getFragmentTitle()
    {
        return "我的订单";
    }

    protected void doRefresh()
    {
        mUserOrderItems.clear();
        mUserOrderListAdapter = null;
        mListView.removeHeaderView(mHeaderView);
        mListView.removeFooterView(mFooterView);
        mListView.setOnScrollListener(new OnScrollListenerImpl());
        mPullToRefreshListView.setOnRefreshListener(new OnRefreshListenerImpl());
        doGetData();
    }

    @Override
    protected void doGetData()
    {
        UserOrderPresenter userOrderPresenter = new UserOrderPresenterImpl(mActivity, this);
        mCurrentPage = mCurrentPage + 1;
        userOrderPresenter.doGetUserOrder(mOrderStatus, mCurrentPage);
        mIsLoadingData = true;
        mFooterView.findViewById(R.id.pb_progress).setVisibility(View.VISIBLE);
        ((TextView) mFooterView.findViewById(R.id.tv_progress_hint)).setText("正在加载中……");
    }

    @Override
    public void onGetDataSuccess(ResultItem resultItem, String requestTag)
    {
        mPtrClassicFrameLayout.refreshComplete();
        mIsLoadingData = false;
        if (null != resultItem)
        {
            mActivity.showCroutonToast(resultItem.message);
            if (resultItem.success())
            {
                Type type = new TypeToken<List<UserOrderItem>>()
                {
                }.getType();
                List<UserOrderItem> userOrderItems = GsonUtils.jsonToResultItemToList(GsonUtils.toJson(resultItem), type);
                //  List<UserOrderItem>   userOrderItems = GsonUtils.jsonToResultItemToList( DATA, type);
                if (null != userOrderItems && !userOrderItems.isEmpty())
                {
                    //  System.out.println("userOrderItems.size = " + userOrderItems.size());
                    mUserOrderItems.addAll(userOrderItems);
                    if (null == mUserOrderListAdapter)
                    {
                        mListView.addHeaderView(mHeaderView);
                        mListView.addFooterView(mFooterView);
                        mPullToRefreshListView.getRefreshableView().addHeaderView(mHeaderView);
                        mUserOrderListAdapter = new UserOrderListAdapter(mActivity, mUserOrderItems);
                        mListView.setAdapter(mUserOrderListAdapter);
                        mPullToRefreshListView.setAdapter(mUserOrderListAdapter);
                        mListView.setOnScrollListener(new OnScrollListenerImpl());
                        if (mUserOrderListAdapter.getCount() < Constants.PRE_PAGE_SIZE)
                        {
                            mFooterView.findViewById(R.id.pb_progress).setVisibility(View.INVISIBLE);
                            ((TextView) mFooterView.findViewById(R.id.tv_progress_hint)).setText("数据已经加载完毕");
                            mActivity.showToast("数据已经加载完毕");
                            mListView.setOnScrollListener(null);
                        } else
                        {
                            ((TextView) mFooterView.findViewById(R.id.tv_progress_hint)).setText("加载下一页");
                            mFooterView.findViewById(R.id.pb_progress).setVisibility(View.INVISIBLE);
                        }
                    } else
                    {
                        ((TextView) mFooterView.findViewById(R.id.tv_progress_hint)).setText("加载下一页");
                        mFooterView.findViewById(R.id.pb_progress).setVisibility(View.INVISIBLE);
                        mUserOrderListAdapter.notifyDataSetChanged();
                        if (userOrderItems.size() != Constants.PRE_PAGE_SIZE)
                        {
                            mFooterView.findViewById(R.id.pb_progress).setVisibility(View.INVISIBLE);
                            ((TextView) mFooterView.findViewById(R.id.tv_progress_hint)).setText("数据已经加载完毕");
                            mActivity.showToast("数据已经加载完毕");
                            mListView.setOnScrollListener(null);
                        }
                    }
                } else
                {
                    if (mUserOrderListAdapter != null)   //数据加载完毕
                    {
                        mFooterView.findViewById(R.id.pb_progress).setVisibility(View.INVISIBLE);
                        ((TextView) mFooterView.findViewById(R.id.tv_progress_hint)).setText("数据已经加载完毕");
                        mActivity.showToast("数据已经加载完毕");
                        mListView.setOnScrollListener(null);
                    } else
                    {
                        mListView.setAdapter(null);
                        mListView.setEmptyView(mActivity.findViewById(android.R.id.empty));
                    }
                }
                mListView.setOnItemClickListener(new OnItemClickListenerImpl());
            }
        }
    }

    @Override
    public void onGetDataFailured(String msg, String requestTag)
    {
        mIsLoadingData = false;
        mCurrentPage = mCurrentPage - 1;
        if (mCurrentPage < 1)
        {
            mCurrentPage = 0;
        }
    }

    private class OnItemClickListenerImpl implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            if (parent.getAdapter().getItemViewType(position) != AdapterView.ITEM_VIEW_TYPE_HEADER_OR_FOOTER)
            {
                UserOrderItem userOrderItem = (UserOrderItem) parent.getAdapter().getItem(position);
                UserOrderDetailActivity.actionUserOrderDetail(mActivity, userOrderItem.orderId);
            } else
            {
                if (view == mFooterView)
                {
                    doGetData();
                }
            }
        }
    }

    private class OnScrollListenerImpl implements AbsListView.OnScrollListener
    {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState)
        {
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
        {
            System.out.println(" firstVisibleItem = " + firstVisibleItem + " visibleItemCount = " + visibleItemCount + " totalItemCount = " + totalItemCount);
            if (firstVisibleItem + visibleItemCount == totalItemCount && !mIsLoadingData && null != mUserOrderListAdapter && mUserOrderListAdapter.getCount() >= Constants.PRE_PAGE_SIZE)
            {
                /// doGetData();
            }
        }
    }

    private class OnRefreshListenerImpl implements PullToRefreshBase.OnRefreshListener2<ListView>
    {

        @Override
        public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
        {
            doRefresh();
        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
        {
            doGetData();
        }
    }
}
