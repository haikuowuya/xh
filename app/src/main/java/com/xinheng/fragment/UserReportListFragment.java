package com.xinheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.xinheng.AddRecipeActivity;
import com.xinheng.R;
import com.xinheng.adapter.user.UserReportListAdapter;
import com.xinheng.base.BaseFragment;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.UserMedicalItem;
import com.xinheng.mvp.model.UserReportItem;
import com.xinheng.mvp.presenter.UserReportPresenter;
import com.xinheng.mvp.presenter.impl.UserReportPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.DensityUtils;
import com.xinheng.util.GsonUtils;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：  我的报告列表
 */
public class UserReportListFragment extends BaseFragment implements DataView
{
    private static final String DATA = UserMedicalItem.DEBUG_SUCCESS;

    public static UserReportListFragment newInstance()
    {
        UserReportListFragment fragment = new UserReportListFragment();
        return fragment;
    }
    private LinkedList<UserReportItem> mUserReportItems = new LinkedList<>();
    private UserReportListAdapter mUserReportListAdapter;


    private ListView mListView;
    private PtrClassicFrameLayout mPtrClassicFrameLayout;
    /**
     *
     * 添加报告
     */
    private ImageView mIvAddReport;
    /**
     * ListView 的headerview
     */
    private ImageView mListHeaderImageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_user_report, null);
        initView(view);
        mIsInit = true;
        return view;
    }
    private void initView(View view)
    {
        mListView = (ListView) view.findViewById(R.id.lv_listview);
        mPtrClassicFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.ptr_list_container);
        mIvAddReport = (ImageView) view.findViewById(R.id.iv_add_report);
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
        mListView.addHeaderView(mListHeaderImageView);
        mListView.setAdapter(ArrayAdapter.createFromResource(mActivity, R.array.array_menu, android.R.layout.simple_list_item_activated_1));
        mPtrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler()
        {
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout)
            {
                doRefresh();
            }
        });
        mIvAddReport.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AddRecipeActivity.actionAddRecipe(mActivity);
            }
        });
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
        UserReportPresenter userReportPresenter = new UserReportPresenterImpl(mActivity, this);
        userReportPresenter.doGetUserReport();
    }

    @Override
    public void onGetDataSuccess(ResultItem resultItem)
    {
        refreshComplete();
        if(null != resultItem)
        {
            mActivity.showCroutonToast(resultItem.message);
            if(resultItem.success())
            {
                Type type = new TypeToken<List<UserReportItem>>()
                {
                }.getType();
                List<UserReportItem> items = GsonUtils.jsonToResultItemToList(GsonUtils.toJson(resultItem), type);
                if (null != items)
                {
                    mUserReportItems.addAll(items);
                    if (null == mUserReportListAdapter)
                    {
                        mUserReportListAdapter = new UserReportListAdapter(mActivity, mUserReportItems);

                        mListView.setAdapter(mUserReportListAdapter);
                    }
                    else
                    {
                        mUserReportListAdapter.notifyDataSetChanged();
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
