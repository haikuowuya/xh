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
import com.xinheng.UserMedicalDetailActivity;
import com.xinheng.adapter.user.UserMedicalListAdapter;
import com.xinheng.base.BaseFragment;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.user.UserMedicalItem;
import com.xinheng.mvp.presenter.UserMedicalPresenter;
import com.xinheng.mvp.presenter.impl.UserMedicalPresenterImpl;
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
 * 说明：  我的病历列表
 */
public class UserMedicalListFragment extends BaseFragment implements DataView
{
    private static final String DATA = UserMedicalItem.DEBUG_SUCCESS;

    public static UserMedicalListFragment newInstance()
    {
        UserMedicalListFragment fragment = new UserMedicalListFragment();
        return fragment;
    }
    private LinkedList<UserMedicalItem> mUserMedicalItems = new LinkedList<>();
    private UserMedicalListAdapter mUserMedicalListAdapter;


    private ListView mListView;
    private PtrClassicFrameLayout mPtrClassicFrameLayout;
    /**
     *
     * 添加病历
     */
    private ImageView mIvAddMedical;
    /**
     * ListView 的headerview
     */
    private ImageView mListHeaderImageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_user_medical, null);
        initView(view);
        mIsInit = true;
        return view;
    }
    private void initView(View view)
    {
        mListView = (ListView) view.findViewById(R.id.lv_listview);
        mPtrClassicFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.ptr_list_container);
        mIvAddMedical = (ImageView) view.findViewById(R.id.iv_add_medical);
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
//        mListView.setAdapter(ArrayAdapter.createFromResource(mActivity, R.array.array_menu, android.R.layout.simple_list_item_activated_1));
        mPtrClassicFrameLayout.setPtrHandler(
                new PtrDefaultHandler()
                {
                    public void onRefreshBegin(PtrFrameLayout ptrFrameLayout)
                    {
                        doRefresh();
                    }
                });
        mIvAddMedical.setOnClickListener(
                new View.OnClickListener()
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
    {   mUserMedicalItems.clear();
        doGetData();
    }
    @Override
    protected void doGetData()
    {
        UserMedicalPresenter userMedicalPresenter = new UserMedicalPresenterImpl(mActivity, this);
        userMedicalPresenter.doGetUserMedical();
    }

    @Override
    public void onGetDataSuccess(ResultItem resultItem,String requestTag)
    {
        refreshComplete();
        if(null != resultItem)
        {
            mActivity.showToast(resultItem.message);
            if (resultItem.success())
            {
                Type type = new TypeToken<List<UserMedicalItem>>()
                {
                }.getType();
                List<UserMedicalItem> items = GsonUtils.jsonToResultItemToList(GsonUtils.toJson(resultItem), type);
                if (null != items && !items.isEmpty())
                {
                    mUserMedicalItems.addAll(items);
                    if (null == mUserMedicalListAdapter)
                    {
                        mListView.addHeaderView(mListHeaderImageView);
                        mUserMedicalListAdapter = new UserMedicalListAdapter(mActivity, mUserMedicalItems);
                        mListView.setAdapter(mUserMedicalListAdapter);
                        mListView.setOnItemClickListener(
                                new AdapterView.OnItemClickListener()
                                {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                                    {
                                        UserMedicalItem item = (UserMedicalItem) parent.getAdapter().getItem(position);
                                        UserMedicalDetailActivity.actionUserMedicalDetail(mActivity, item.id);
                                    }
                                });
                    }
                    else
                    {
                        mUserMedicalListAdapter.notifyDataSetChanged();
                    }
                }
                else
                {
                    mActivity.showToast("我的病历列表为空");
                }
            }
        }
    }

    @Override
    public void onGetDataFailured(String msg,String requestTag)
    {

    }

    protected  void refreshComplete()
    {
        mPtrClassicFrameLayout.refreshComplete();
    }
}
