package com.xinheng.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;

import com.google.gson.reflect.TypeToken;
import com.xinheng.R;
import com.xinheng.UserAppointmentAddDetailActivity;
import com.xinheng.UserAppointmentDetailActivity;
import com.xinheng.adapter.user.UserAppointmentListAdapter;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.user.UserAppointmentItem;
import com.xinheng.mvp.presenter.UserAppointmentPresenter;
import com.xinheng.mvp.presenter.impl.UserAppointmentPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.DensityUtils;
import com.xinheng.util.GsonUtils;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：  我的预约列表
 */
public class UserAppointmentListFragment extends PTRListFragment implements DataView
{
    private static final String DATA = UserAppointmentItem.DEBUG_SUCCESS;
    /***
     * 预约挂号的标志
     */
    public static final String TYPE_0 = "0";
    /**
     * 预约加号的标志
     */
    public static final String TYPE_1 = "1";

    public static final String ARG_TYPE = "type";

    public static UserAppointmentListFragment newInstance(String type)
    {
        UserAppointmentListFragment fragment = new UserAppointmentListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    private LinkedList<UserAppointmentItem> mUserSubscribeItems = new LinkedList<>();
    private UserAppointmentListAdapter mUserAppointmentListAdapter;
    /***
     * 预约类型， 0表示预约挂号 1表示预约加号
     */
    private String mType = TYPE_0;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mType = getArguments().getString(ARG_TYPE);
        getListView().setSelector(new ColorDrawable(0x00000000));
        getListView().setDividerHeight(0);
        getListView().setBackgroundColor(0xFFF0F0F0);
        int leftRight = DensityUtils.dpToPx(mActivity, 10.f);
        getListView().setPadding(leftRight, 0, leftRight, 0);
        getListView().setDivider(new ColorDrawable(0x00000000));
    }

    @Override
    public String getFragmentTitle()
    {
        return getString(R.string.tv_fragment_ptr_list);
    }

    @Override
    protected void doRefresh()
    {
        mUserSubscribeItems.clear();
        doGetData();
    }

    @Override
    protected void doGetData()
    {
        UserAppointmentPresenter userSubscribePresenter = new UserAppointmentPresenterImpl(mActivity, this);
        userSubscribePresenter.doGetUserAppointment(mType);
    }

    @Override
    public void onGetDataSuccess(ResultItem resultItem, String requestTag)
    {
        refreshComplete();
        if (null != resultItem)
        {
            mActivity.showToast(resultItem.message);
            if (resultItem.success())
            {
                Type type = new TypeToken<List<UserAppointmentItem>>()
                {
                }.getType();
                List<UserAppointmentItem> items = GsonUtils.jsonToResultItemToList(GsonUtils.toJson(resultItem), type);
                mUserSubscribeItems.addAll(items);
                if (null == mUserAppointmentListAdapter)
                {
                    mUserAppointmentListAdapter = new UserAppointmentListAdapter(mActivity, mUserSubscribeItems, mType);
                    getListView().setAdapter(mUserAppointmentListAdapter);
                }
                else
                {
                    mUserAppointmentListAdapter.notifyDataSetChanged();
                }
                getListView().setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        UserAppointmentItem item = (UserAppointmentItem) parent.getAdapter().getItem(position);
                        if (TYPE_0.equals(mType))
                        {
                            UserAppointmentDetailActivity.actionUserAppointmentDetail(mActivity, item.id);
                        }
                        else if (TYPE_1.equals(mType))
                        {
                            UserAppointmentAddDetailActivity.actionUserAppointmentAddDetail(mActivity, item.id);
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onGetDataFailured(String msg, String requestTag)
    {
        mActivity.showCroutonToast(msg);
    }
}
