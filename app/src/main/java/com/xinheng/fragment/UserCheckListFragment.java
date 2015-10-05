package com.xinheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.xinheng.AddCheckActivity;
import com.xinheng.R;
import com.xinheng.adapter.user.UserCheckListAdapter;
import com.xinheng.base.BaseFragment;
import com.xinheng.eventbus.OnAddCheckEvent;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.user.UserChecktItem;
import com.xinheng.mvp.model.user.UserMedicalItem;
import com.xinheng.mvp.presenter.UserCheckPresenter;
import com.xinheng.mvp.presenter.impl.UserCheckPresenterImpl;
import com.xinheng.mvp.view.DataView;
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
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：  我的病历列表
 */
public class UserCheckListFragment extends BaseFragment implements DataView
{
    private static final String DATA = UserMedicalItem.DEBUG_SUCCESS;

    public static UserCheckListFragment newInstance()
    {
        UserCheckListFragment fragment = new UserCheckListFragment();
        return fragment;
    }

    private LinkedList<UserChecktItem> mUserChecktItems = new LinkedList<>();
    private UserCheckListAdapter mUserCheckListAdapter;

    private ListView mListView;
    private PtrClassicFrameLayout mPtrClassicFrameLayout;
    /**
     * 添加病历
     */
    private ImageView mIvAddCheck;
    /**
     * ListView 的headerview
     */
    private ImageView mListHeaderImageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_user_check, null);
        initView(view);
        mIsInit = true;
        return view;
    }

    private void initView(View view)
    {
        mListView = (ListView) view.findViewById(R.id.lv_listview);
        mPtrClassicFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.ptr_list_container);
        mIvAddCheck = (ImageView) view.findViewById(R.id.iv_add_check);
        mListHeaderImageView = new ImageView(mActivity);
        mListHeaderImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        int height = DensityUtils.dpToPx(mActivity, 130.f);
        AbsListView.LayoutParams layoutparams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        mListHeaderImageView.setLayoutParams(layoutparams);
        mListHeaderImageView.setImageResource(R.mipmap.ic_user_medical_banner);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
        mListView.addHeaderView(mListHeaderImageView);
//        mListView.setAdapter(ArrayAdapter.createFromResource(mActivity, R.array.array_menu, android.R.layout.simple_list_item_activated_1));
        mPtrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler()
        {
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout)
            {
                doRefresh();
            }
        });
        mIvAddCheck.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AddCheckActivity.actionAddCheck(mActivity, true);
            }
        });
    }

    //添加检查成功事件
    @Subscribe
    public void onEventMainThread(OnAddCheckEvent event)
    {
        if (null != event)
        {
            doRefresh();
        }
    }

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

    protected void doRefresh()
    {
        mUserChecktItems.clear();
        doGetData();
    }

    @Override
    protected void doGetData()
    {
        UserCheckPresenter userCheckPresenter = new UserCheckPresenterImpl(mActivity, this);
        userCheckPresenter.doGetUserCheck();
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
                Type type = new TypeToken<List<UserChecktItem>>()
                {
                }.getType();
                List<UserChecktItem> items = GsonUtils.jsonToResultItemToList(GsonUtils.toJson(resultItem), type);
                if (null != items)
                {
                    mUserChecktItems.addAll(items);
                    if (null == mUserCheckListAdapter)
                    {
                        mUserCheckListAdapter = new UserCheckListAdapter(mActivity, mUserChecktItems);
                        mListView.setAdapter(mUserCheckListAdapter);
                    }
                    else
                    {
                        mUserCheckListAdapter.notifyDataSetChanged();
                    }
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
