package com.xinheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.xinheng.R;
import com.xinheng.adapter.drug.DrugListAdapter;
import com.xinheng.base.BaseFragment;
import com.xinheng.eventbus.OnAddDrugItemEvent;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.prescription.DrugItem;
import com.xinheng.mvp.presenter.DrugSearchPresenter;
import com.xinheng.mvp.presenter.impl.DrugSearchPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.ACache;
import com.xinheng.util.Constants;
import com.xinheng.util.GsonUtils;

import java.lang.reflect.Type;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：  按方抓药-添加药品Fragment页面
 */
public class AddDrugFragment extends BaseFragment  implements DataView
{
    public static final String  ARG_DRUG_ITEMS_JSON="drug_items_json";
    public static AddDrugFragment newInstance(String itemsJson )
    {
        AddDrugFragment fragment = new AddDrugFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_DRUG_ITEMS_JSON, itemsJson);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_add_drug, null);
        initView(view);
        mIsInit = true;
        return view;
    }

    private ListView mListView;
    private Button mBtnSearch;
    private EditText mEtSearch;
    private List<DrugItem> mDrugItems = null;
    private List<DrugItem> mSelectedItems = null;
    private DrugListAdapter mDrugListAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        setListener();
        String itemsJson =  getArguments().getString(ARG_DRUG_ITEMS_JSON);
        if(!TextUtils.isEmpty(itemsJson))
        {
            Type type = new TypeToken<List<DrugItem>>(){}.getType();
            mSelectedItems = GsonUtils.jsonToList(itemsJson, type);
        }
        String cacheJson = ACache.get(mActivity).getAsString(mActivity.getActivityTitle().toString());
        if (!TextUtils.isEmpty(cacheJson))
        {
            Type type = new TypeToken<List<DrugItem>>(){}.getType();
            mDrugItems = GsonUtils.jsonToList(cacheJson, type);
            System.out.println("mDrugItems = " + mDrugItems.size());
            showListContentData();
        }
        search("");
    }

    private void setListener()
    {
        OnClickListenerImpl onClickListener = new OnClickListenerImpl();
        mBtnSearch.setOnClickListener(onClickListener);
        mEtSearch.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                 if(TextUtils.isEmpty(mEtSearch.getText()))
                 {
                     String cacheJson = ACache.get(mActivity).getAsString(mActivity.getActivityTitle().toString());
                     if (!TextUtils.isEmpty(cacheJson))
                     {
                         Type type = new TypeToken<List<DrugItem>>(){}.getType();
                         mDrugItems = GsonUtils.jsonToList(cacheJson, type);
                         System.out.println("mdrugItemssss = " + mDrugItems.size());
                        if(null != mDrugItems && !mDrugItems.isEmpty())
                        {
                            showListContentData();
                        }
                         else
                        {
                            search("");
                        }
                     }
                 }
            }
        });
    }

    private void initView(View view)
    {
        mEtSearch = (EditText) view.findViewById(R.id.et_search);
        mBtnSearch = (Button) view.findViewById(R.id.btn_search);
        mListView = (ListView) view.findViewById(R.id.lv_listview);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if(null != mDrugListAdapter)
        {
            EventBus.getDefault().post(new OnAddDrugItemEvent(mDrugListAdapter.getSelectedItems()));
        }
    }

    @Override
    public String getFragmentTitle()
    {
        return "我的订单";
    }
    @Override
    public void onGetDataSuccess(ResultItem resultItem,String requestTag)
    {
        if(resultItem != null)
        {
            mActivity.showToast(resultItem.message);
            if(resultItem.success())
            {
                Type type = new TypeToken<List<DrugItem>>()
                {
                }.getType();
                List<DrugItem> drugItems = GsonUtils.jsonToList(resultItem.properties.getAsJsonObject().get("list").getAsJsonArray().toString(),type);
                mDrugItems.clear();
                mDrugItems.addAll(drugItems);
                showListContentData( );
            }
        }
    }

    private void showListContentData(  )
    {
        if (null != mDrugItems)
        {
            if (null == mDrugListAdapter)
            {
                mDrugListAdapter = new DrugListAdapter(mActivity, mDrugItems,mSelectedItems);
                mListView.setAdapter(mDrugListAdapter);
            }
            else
            {
                mDrugListAdapter.notifyDataSetChanged();
            }
            if(!mDrugItems.isEmpty() && TextUtils.isEmpty(mEtSearch.getText()))
            {
                ACache.get(mActivity).put(mActivity.getActivityTitle().toString(), GsonUtils.toJson(mDrugItems));
            }
        }
    }

    @Override
    public void onGetDataFailured(String msg,String requestTag)
    {

    }

    private class OnClickListenerImpl implements View.OnClickListener
    {
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.btn_search://搜索
                    search(mEtSearch.getText().toString());
                    break;
            }
        }
    }

    private void search(String searchText )
    {
        if(TextUtils.isEmpty( searchText))
        {
        //    mActivity.showCroutonToast("请输入搜索关键字");
          //  return;
        }
        mActivity.hideSoftKeyBorard(mEtSearch);
        String keyword = searchText;
        DrugSearchPresenter drugSearchPresenter = new DrugSearchPresenterImpl(mActivity, this);
        drugSearchPresenter.doSearchDrug(Constants.HID, keyword);
    }
}
