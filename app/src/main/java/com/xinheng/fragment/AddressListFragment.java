package com.xinheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.xinheng.AddOrModifyAddressActivity;
import com.xinheng.R;
import com.xinheng.adapter.address.AddressListAdapter;
import com.xinheng.base.BaseFragment;
import com.xinheng.eventbus.OnAddOrModifyOrDelAddressEvent;
import com.xinheng.eventbus.OnSelectAddressEvent;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.address.AddressItem;
import com.xinheng.mvp.model.user.UserPatientItem;
import com.xinheng.mvp.presenter.AddressPresenter;
import com.xinheng.mvp.presenter.impl.AddressPresenterImpl;
import com.xinheng.mvp.view.DataView;
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
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明： 收货地址列表界面
 */
public class AddressListFragment extends BaseFragment implements DataView
{
    private static final String DATA = UserPatientItem.DEBUG_SUCCESS;
    public static final String ARG_FROM_CONFIRM_ORDER = "from_confirm_order";

    public static AddressListFragment newInstance(boolean fromConfirmOrder)
    {
        AddressListFragment fragment = new AddressListFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(ARG_FROM_CONFIRM_ORDER, fromConfirmOrder);
        fragment.setArguments(bundle);
        return fragment;
    }

    private boolean mFromConfirmOrder = false;
    private LinkedList<AddressItem> mAddressItems = new LinkedList<>();
    private AddressListAdapter mAddressListAdapter;

    private ListView mListView;
    private PtrClassicFrameLayout mPtrClassicFrameLayout;

    /***
     * 新增收货地址
     */
    private Button mBtnAddAddress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_address_manager, null);
        initView(view);
        mIsInit = true;
        return view;
    }

    private void initView(View view)
    {
        mListView = (ListView) view.findViewById(R.id.lv_listview);
        mPtrClassicFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.ptr_list_container);
        mBtnAddAddress = (Button) view.findViewById(R.id.btn_add_address);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
        mFromConfirmOrder = getArguments().getBoolean(ARG_FROM_CONFIRM_ORDER);
//        mListView.setAdapter(ArrayAdapter.createFromResource(mActivity, R.array.array_menu, android.R.layout.simple_list_item_activated_1));
        mPtrClassicFrameLayout.setPtrHandler(
                new PtrDefaultHandler()
                {
                    public void onRefreshBegin(PtrFrameLayout ptrFrameLayout)
                    {
                        doRefresh();
                    }
                });
        mBtnAddAddress.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        AddOrModifyAddressActivity.actionAddAddress(mActivity);
                    }
                });
    }

    //===============================EVENT BUS========================
    @Subscribe
    public void onEventMainThread(OnAddOrModifyOrDelAddressEvent event)
    {
        if (null != event)
        {
            doGetData();
            //重新去请求一次服务器吧，下面的方式会出现年龄为null
        }
    }

    //===============================EVENT BUS========================
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public String getFragmentTitle()
    {
        return getString(R.string.tv_fragment_ptr_list);
    }

    protected void doRefresh()
    {
        doGetData();
    }

    @Override
    protected void doGetData()
    {
        AddressPresenter addressPresenter = new AddressPresenterImpl(mActivity, this);
        addressPresenter.doGetAddressList();
    }

    @Override
    public void onGetDataSuccess(ResultItem resultItem, String requestTag)
    {
        refreshComplete();
        if (null != resultItem)
        {
            mActivity.showCroutonToast(resultItem.message);
            if (resultItem.success())
            {
                Type type = new TypeToken<List<AddressItem>>()
                {
                }.getType();
                List<AddressItem> items = GsonUtils.jsonToResultItemToList(GsonUtils.toJson(resultItem), type);
                if (null != items)
                {
                    mAddressItems.clear();
                    mAddressItems.addAll(items);
                    if (null == mAddressListAdapter)
                    {
                        mAddressListAdapter = new AddressListAdapter(mActivity, mAddressItems);
                        mListView.setAdapter(mAddressListAdapter);
                    } else
                    {
                        mAddressListAdapter.notifyDataSetChanged();
                    }
                    mListView.setOnItemClickListener(
                            new AdapterView.OnItemClickListener()
                            {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                                {
                                        AddressItem addressItem = mAddressItems.get(position);
                                    if(!mFromConfirmOrder)
                                    {
                                        AddOrModifyAddressActivity.actionAddAddress(mActivity, addressItem);
                                    }
                                    else
                                    {
                                        EventBus.getDefault().post(new OnSelectAddressEvent(addressItem));
                                          mActivity.finish();
                                    }
                                }
                            });
                }
            }
        }
    }

    @Override
    public void onGetDataFailured(String msg, String requestTag)
    {

    }

    protected void refreshComplete()
    {
        mPtrClassicFrameLayout.refreshComplete();
    }
}
