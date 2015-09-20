package com.xinheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.xinheng.AddPatientActivity;
import com.xinheng.R;
import com.xinheng.adapter.user.UserPatientListAdapter;
import com.xinheng.base.BaseFragment;
import com.xinheng.eventbus.OnAddPatientItemEvent;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.user.UserPatientItem;
import com.xinheng.mvp.presenter.UserPatientPresenter;
import com.xinheng.mvp.presenter.impl.UserPatientPresenterImpl;
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
 * 说明： 常用就诊人列表界面
 */
public class UserPatientListFragment extends BaseFragment implements DataView
{
    private static final String DATA = UserPatientItem.DEBUG_SUCCESS;

    public static UserPatientListFragment newInstance()
    {
        UserPatientListFragment fragment = new UserPatientListFragment();
        return fragment;
    }
    private LinkedList<UserPatientItem> mUserPatientItems = new LinkedList<>();
    private UserPatientListAdapter mUserPatientListAdapter;


    private ListView mListView;
    private PtrClassicFrameLayout mPtrClassicFrameLayout;
    /**
     *
     * 添加就诊人
     */
    private ImageView mIvAddPatient;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_user_patient, null);
        initView(view);
        mIsInit = true;
        return view;
    }
    private void initView(View view)
    {
        mListView = (ListView) view.findViewById(R.id.lv_listview);
        mPtrClassicFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.ptr_list_container);
        mIvAddPatient = (ImageView) view.findViewById(R.id.iv_add_patient);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
        mListView.setAdapter(ArrayAdapter.createFromResource(mActivity, R.array.array_menu, android.R.layout.simple_list_item_activated_1));
        mPtrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler()
        {
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout)
            {
                doRefresh();
            }
        });
        mIvAddPatient.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AddPatientActivity.actionAddPatient(mActivity);
            }
        });
    }
    //===============================EVENT BUS========================
    @Subscribe
    public void onEventMainThread(OnAddPatientItemEvent event)
    {
        if(null != event && null != event.mUserPatientItem)
        {
            doGetData();
            //重新去请求一次服务器吧，下面的方式会出现年龄为null

//            mUserPatientItems.addFirst(event.mUserPatientItem);
//            if(null != mUserPatientListAdapter)
//            {
//                mUserPatientListAdapter.notifyDataSetChanged();
//            }
//            else
//            {
//                mUserPatientListAdapter = new UserPatientListAdapter(mActivity, mUserPatientItems);
//                mListView.setAdapter(mUserPatientListAdapter);
//            }
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
        UserPatientPresenter userPatientPresenter = new UserPatientPresenterImpl(mActivity, this);
        userPatientPresenter.doGetUserPatient();
        mUserPatientItems.clear();
    }

    @Override
    public void onGetDataSuccess(ResultItem resultItem)
    {
        refreshComplete();
        if(null != resultItem)
        {
            mActivity.showCroutonToast(resultItem.message);
            if (resultItem.success())
            {
                Type type = new TypeToken<List<UserPatientItem>>()
                {
                }.getType();
                List<UserPatientItem> items = GsonUtils.jsonToResultItemToList(GsonUtils.toJson(resultItem), type);
                if (null != items)
                {
                    mUserPatientItems.addAll(items);
                    if (null == mUserPatientListAdapter)
                    {
                        mUserPatientListAdapter = new UserPatientListAdapter(mActivity, mUserPatientItems);
                        mListView.setAdapter(mUserPatientListAdapter);
                    }
                    else
                    {
                        mUserPatientListAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }

    @Override
    public void onGetDataFailured(String msg)
    {

    }

    protected  void refreshComplete()
    {
        mPtrClassicFrameLayout.refreshComplete();
    }
}
