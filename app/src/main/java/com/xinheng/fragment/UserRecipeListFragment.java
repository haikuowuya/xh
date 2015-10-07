package com.xinheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.xinheng.AddRecipeActivity;
import com.xinheng.R;
import com.xinheng.UserRecipeDetailActivity;
import com.xinheng.adapter.user.UserRecipeListAdapter;
import com.xinheng.base.BaseFragment;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.user.UserMedicalItem;
import com.xinheng.mvp.model.user.UserRecipeItem;
import com.xinheng.mvp.presenter.UserRecipePresenter;
import com.xinheng.mvp.presenter.impl.UserRecipePresenterImpl;
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
 * 说明：  我的处方列表
 */
public class UserRecipeListFragment extends BaseFragment implements DataView
{
    private static final String DATA = UserMedicalItem.DEBUG_SUCCESS;

    public static UserRecipeListFragment newInstance()
    {
        UserRecipeListFragment fragment = new UserRecipeListFragment();
        return fragment;
    }

    private LinkedList<UserRecipeItem> mUserRecipeItems = new LinkedList<>();
    private UserRecipeListAdapter mUserRecipeListAdapter;

    private ListView mListView;
    private PtrClassicFrameLayout mPtrClassicFrameLayout;
    /**
     * 添加处方
     */
    private ImageView mIvAddRecipe;
    /**
     * ListView 的headerview
     */
    private ImageView mListHeaderImageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_user_recipe, null);
        initView(view);
        mIsInit = true;
        return view;
    }

    private void initView(View view)
    {
        mListView = (ListView) view.findViewById(R.id.lv_listview);
        mPtrClassicFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.ptr_list_container);
        mIvAddRecipe = (ImageView) view.findViewById(R.id.iv_add_recipe);
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
        mPtrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler()
        {
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout)
            {
                doRefresh();
            }
        });
        mIvAddRecipe.setOnClickListener(new View.OnClickListener()
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
        mUserRecipeItems.clear();
        doGetData();
    }

    @Override
    protected void doGetData()
    {
        UserRecipePresenter userRecipePresenter = new UserRecipePresenterImpl(mActivity, this);
        userRecipePresenter.doGetUserRecipe();
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
                Type type = new TypeToken<List<UserRecipeItem>>()
                {
                }.getType();
                List<UserRecipeItem> items = GsonUtils.jsonToResultItemToList(GsonUtils.toJson(resultItem), type);
                if (null != items)
                {
                    mUserRecipeItems.addAll(items);
                    if (null == mUserRecipeListAdapter)
                    {
                        mListView.addHeaderView(mListHeaderImageView);
                        mUserRecipeListAdapter = new UserRecipeListAdapter(mActivity, mUserRecipeItems);
                        mListView.setAdapter(mUserRecipeListAdapter);
                        mListView.setOnItemClickListener(new OnItemClickListenerImpl());
                    }
                    else
                    {
                        mUserRecipeListAdapter.notifyDataSetChanged();
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

    private class OnItemClickListenerImpl implements AdapterView.OnItemClickListener
    {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            if (!(parent.getAdapter().getItemViewType(position) == AdapterView.ITEM_VIEW_TYPE_HEADER_OR_FOOTER))
            {
                UserRecipeItem item = (UserRecipeItem) parent.getAdapter().getItem(position);
                UserRecipeDetailActivity.actionUserRecipeDetail(mActivity, item.id);
            }
            else
            {
                //点击的是headerview
            }
        }
    }
}
