package com.xinheng.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.xinheng.AutoCheckActivity;
import com.xinheng.R;
import com.xinheng.adapter.auto.SymptomListAdapter;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.auto.BodyKV;
import com.xinheng.mvp.presenter.BodypartDetailPresenter;
import com.xinheng.mvp.presenter.impl.BodypartDetailPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.DensityUtils;
import com.xinheng.util.GsonUtils;

import java.lang.reflect.Type;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：  身体症状列表
 */
public class SymptomListFragment extends PTRListFragment implements DataView
{
    public static final String ARG_BODY_KV = "body_kv";

    public static SymptomListFragment newInstance(BodyKV bodyKV)
    {

        SymptomListFragment fragment = new SymptomListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_BODY_KV, bodyKV);
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * 身体部位
     */
    private BodyKV mBodyPartKV;

    private TextView mTvHeaderTextView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mBodyPartKV = getArguments().getSerializable(ARG_BODY_KV) == null ? null : (BodyKV) getArguments().getSerializable(ARG_BODY_KV);
        if (null != mBodyPartKV)
        {
            if (null != mPtrClassicFrameLayout)
            {
                mPtrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler()
                {
                    @Override
                    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header)
                    {
                        return false;
                    }

                    @Override
                    public void onRefreshBegin(PtrFrameLayout ptrFrameLayout)
                    {

                    }
                });
            }
            getListView().setDividerHeight(0);
            getListView().setBackgroundColor(0xFFF0F0F0);
            int leftRight = DensityUtils.dpToPx(mActivity, 10.f);
            getListView().setPadding(leftRight, leftRight, leftRight, 0);
            getListView().setDivider(new ColorDrawable(0x00000000));
            getListView().setBackgroundColor(0xFFFFFFFF);
            mTvHeaderTextView = new TextView(mActivity);
            mTvHeaderTextView.setGravity(Gravity.CENTER);
            mTvHeaderTextView.setTextColor(0xFF666666);
            mTvHeaderTextView.getPaint().setFakeBoldText(true);
            mTvHeaderTextView.setTextSize(17);
            mTvHeaderTextView.setBackgroundColor(0xFFDBEABF);
            int height = DensityUtils.dpToPx(mActivity, 44.f);
            AbsListView.LayoutParams layoutparams = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
            mTvHeaderTextView.setLayoutParams(layoutparams);

        }
    }

    @Override
    public String getFragmentTitle()
    {
        return getString(R.string.tv_fragment_ptr_list);
    }

    @Override
    protected void doRefresh()
    {
        doGetData();
    }

    @Override
    protected void doGetData()
    {
        BodypartDetailPresenter bodypartDetailPresenter = new BodypartDetailPresenterImpl(mActivity, this);
        bodypartDetailPresenter.doGetBodypartDetailList(mBodyPartKV.key);
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
                Type type = new TypeToken<List<BodyKV.SymptomItem>>()
                {
                }.getType();
                List<BodyKV.SymptomItem> symptomItems = GsonUtils.jsonToList(resultItem.properties.getAsJsonArray().toString(), type);
                if (null != symptomItems && !symptomItems.isEmpty())
                {
                    getListView().addHeaderView(mTvHeaderTextView);
                    mTvHeaderTextView.setText(mBodyPartKV.value + "症状列表");
                    getListView().setAdapter(new SymptomListAdapter(mActivity, symptomItems));
                    getListView().setOnItemClickListener(new AdapterView.OnItemClickListener()
                    {
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                        {
                            if (!(parent.getAdapter().getItemViewType(position) == AdapterView.ITEM_VIEW_TYPE_HEADER_OR_FOOTER))
                            {
                                BodyKV.SymptomItem symptomItem = (BodyKV.SymptomItem) parent.getAdapter().getItem(position);
                                if (mActivity instanceof AutoCheckActivity)
                                {
                                    AutoCheckActivity autoCheckActivity = (AutoCheckActivity) mActivity;
                                    autoCheckActivity.addFragment(SymptomCheckFragment.newInstance(symptomItem));
                                }
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
}
