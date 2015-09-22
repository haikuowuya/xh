package com.xinheng.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.xinheng.CityListActivity;
import com.xinheng.R;
import com.xinheng.adapter.city.AreaListAdapter;
import com.xinheng.adapter.city.CityListAdapter;
import com.xinheng.adapter.city.ProvinceListAdapter;
import com.xinheng.base.BaseFragment;
import com.xinheng.eventbus.OnProvinceCityAreaSelectEvent;
import com.xinheng.mvp.model.city.CityItem;
import com.xinheng.util.AssetUtils;
import com.xinheng.util.DensityUtils;
import com.xinheng.util.GsonUtils;

import java.lang.reflect.Type;
import java.util.List;

import de.greenrobot.event.EventBus;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：  城市列表
 */
public class CityListFragment extends BaseFragment
{
    public static final int WHAT = 111;

    public static final String ARG_CITY_ITEM = "city_item";
    public static final String ARG_AREA_ITEM = "area_item";

    public static CityListFragment newInstance()
    {
        CityListFragment fragment = new CityListFragment();
        return fragment;
    }

    public static CityListFragment newInstance(CityItem cityItem)
    {
        CityListFragment fragment = new CityListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_CITY_ITEM, cityItem);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static CityListFragment newInstance(CityItem cityItem, CityItem.AreaItem areaItem)
    {
        CityListFragment fragment = new CityListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_CITY_ITEM, cityItem);
        bundle.putSerializable(ARG_AREA_ITEM, areaItem);
        fragment.setArguments(bundle);
        return fragment;
    }

    private ListView mListView;
    private PtrClassicFrameLayout mPtrClassicFrameLayout;
    /**
     * ListView 的headerview
     */
    private TextView mTvHeaderTextView;

    private List<CityItem> mCityItems;

    private CityItem mCityItem;
    private CityItem.AreaItem mAreaItem;
    private Handler mHandler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            mActivity.dismissProgressDialog();
            if (null != mCityItems && !mCityItems.isEmpty())
            {
                mListView.setAdapter(new ProvinceListAdapter(mActivity, mCityItems));
                mListView.setOnItemClickListener(
                        new AdapterView.OnItemClickListener()
                        {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                            {
                                mCityItem = (CityItem) parent.getAdapter().getItem(position);
                                if (getActivity() instanceof CityListActivity)
                                {
                                    CityListActivity cityListActivity = (CityListActivity) getActivity();
                                    cityListActivity.toCity(mCityItem);
                                }
                            }
                        });
            }
        }
    };

    private Runnable mRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            Type type = new TypeToken<List<CityItem>>()
            {
            }.getType();
            mCityItems = GsonUtils.jsonToList(AssetUtils.readAssetData(mActivity, "city"), type);
            mHandler.sendEmptyMessage(WHAT);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_city_list, null);
        initView(view);
        mIsInit = true;
        return view;
    }

    private void initView(View view)
    {
        mListView = (ListView) view.findViewById(R.id.lv_listview);
        mPtrClassicFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.ptr_list_container);
        mTvHeaderTextView = new TextView(mActivity);
        mTvHeaderTextView.setGravity(Gravity.CENTER_VERTICAL);
        int paddingLRTB = DensityUtils.dpToPx(mActivity, 10.f);
        mTvHeaderTextView.setPadding(paddingLRTB, paddingLRTB, paddingLRTB, paddingLRTB);
        mTvHeaderTextView.setTextColor(getResources().getColor(R.color.color_main_text_color));
        mTvHeaderTextView.setTextSize(16.f);
        int height = DensityUtils.dpToPx(mActivity, 44.f);
        AbsListView.LayoutParams layoutparams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        mTvHeaderTextView.setLayoutParams(layoutparams);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        if (null != getArguments() && null != getArguments().getSerializable(ARG_CITY_ITEM))
        {
            mCityItem = (CityItem) getArguments().getSerializable(ARG_CITY_ITEM);
        }
        if (null != getArguments() && null != getArguments().getSerializable(ARG_AREA_ITEM))
        {
            mAreaItem = (CityItem.AreaItem) getArguments().getSerializable(ARG_AREA_ITEM);
        }
        if (null == mCityItem)
        {
            mActivity.showProgressDialog();
            new Thread(mRunnable).start();
        } else
        {
            if (null == mAreaItem)
            {
                mTvHeaderTextView.setText(mCityItem.name);
                mListView.addHeaderView(mTvHeaderTextView);
                mListView.setAdapter(new CityListAdapter(mActivity, mCityItem.cityList));
                mListView.setOnItemClickListener(
                        new AdapterView.OnItemClickListener()
                        {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                            {
                                mAreaItem = (CityItem.AreaItem) parent.getAdapter().getItem(position);
                                if (getActivity() instanceof CityListActivity)
                                {
                                    CityListActivity cityListActivity = (CityListActivity) getActivity();
                                    cityListActivity.toArea(mCityItem, mAreaItem);
                                }
                            }
                        });
            } else
            {
                mTvHeaderTextView.setText(mCityItem.name + "-" + mAreaItem.name);
                mListView.addHeaderView(mTvHeaderTextView);
                mListView.setAdapter(new AreaListAdapter(mActivity, mAreaItem.areaList));
                mListView.setOnItemClickListener(
                        new AdapterView.OnItemClickListener()
                        {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                            {
                                String province = mCityItem.name;
                                String city = mAreaItem.name;
                                String area = parent.getAdapter().getItem(position).toString();
//                                mActivity.showCroutonToast("省市地区都选择完毕，" + (mCityItem.name + "-" + mAreaItem.name + "-" + area) + " 可以finish");
                                EventBus.getDefault().post(new OnProvinceCityAreaSelectEvent(province, city, area));
                                mActivity.finish();
                            }
                        });
            }
        }
        mPtrClassicFrameLayout.setPtrHandler(
                new PtrDefaultHandler()
                {
                    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header)
                    {
                        return false;
                    }

                    public void onRefreshBegin(PtrFrameLayout ptrFrameLayout)
                    {
                        doRefresh();
                    }
                });

//        mListView.setOnItemClickListener(new OnItemClickListenerImpl());
    }

    @Override
    public void onDestroy()
    {
        mHandler.removeMessages(WHAT);
        super.onDestroy();

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

    }

    protected void refreshComplete()
    {
        mPtrClassicFrameLayout.refreshComplete();
    }
}
