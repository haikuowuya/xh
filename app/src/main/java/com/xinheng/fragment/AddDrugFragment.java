package com.xinheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
import com.xinheng.util.GsonUtils;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：  按方抓药-添加药品Fragment页面
 */
public class AddDrugFragment extends BaseFragment   implements DataView
{
    public static AddDrugFragment newInstance()
    {
        AddDrugFragment fragment = new AddDrugFragment();
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
    private List<DrugItem> mDrugItems =new LinkedList<>();
    private DrugListAdapter mDrugListAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        OnClickListenerImpl onClickListener = new OnClickListenerImpl();
        mBtnSearch.setOnClickListener(onClickListener);
        search("");
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
            mActivity.showCroutonToast(resultItem.message);
            if(resultItem.success())
            {
                Type type = new TypeToken<List<DrugItem>>()
                {
                }.getType();
                List<DrugItem> drugItems = GsonUtils.jsonToList(resultItem.properties.getAsJsonObject().get("list").getAsJsonArray().toString(),type);
                if (null != drugItems)
                {
                    mDrugItems.clear();
                    mDrugItems.addAll(drugItems);
                    if (null == mDrugListAdapter)
                    {
                        mDrugListAdapter = new DrugListAdapter(mActivity, mDrugItems);
                        mListView.setAdapter(mDrugListAdapter);
                    }
                    else
                    {
                        mDrugListAdapter.notifyDataSetChanged();
                    }
                }
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
        drugSearchPresenter.doSearchDrug("402881b44e706cab014e7075fdff0004", keyword);
    }
}
