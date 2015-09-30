package com.xinheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.xinheng.R;
import com.xinheng.base.BaseFragment;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：
 */
public class PTRListFragment extends BaseFragment
{
    public static PTRListFragment newInstance()
    {
        PTRListFragment fragment = new PTRListFragment();
        return fragment;
    }

    private ListView mListView;
    private PtrClassicFrameLayout mPtrClassicFrameLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_ptr_list, null);
        initView(view);
        mIsInit = true;
        return view;
    }

    public ListView getListView()
    {
        return mListView;
    }

    private void initView(View view)
    {
        mListView = (ListView) view.findViewById(R.id.lv_listview);
        mPtrClassicFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.ptr_list_container);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
//        mListView.setAdapter(ArrayAdapter.createFromResource(mActivity, R.array.array_menu, android.R.layout.simple_list_item_activated_1));
        mPtrClassicFrameLayout.disableWhenHorizontalMove(true);
        mPtrClassicFrameLayout.setPtrHandler(
                new PtrDefaultHandler()
                {

                    public void onRefreshBegin(PtrFrameLayout ptrFrameLayout)
                    {
                        doRefresh();
                    }
                });
    }

    /**
     * 重写此方法进行刷新请求
     */
    protected void doRefresh()
    {
        mPtrClassicFrameLayout.postDelayed(
                new Runnable()
                {
                    @Override
                    public void run()
                    {
                        mPtrClassicFrameLayout.refreshComplete();
                    }
                }, 2000L);
    }

    protected void refreshComplete()
    {
        mPtrClassicFrameLayout.refreshComplete();
    }

    @Override
    public String getFragmentTitle()
    {
        return getString(R.string.tv_fragment_ptr_list);
    }
}
