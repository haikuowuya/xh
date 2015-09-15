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
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.drug.DrugItem;
import com.xinheng.mvp.presenter.UserMedicalSearchPresenter;
import com.xinheng.mvp.presenter.impl.UserMedicalSearchPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

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
    }

    private void initView(View view)
    {
        mEtSearch = (EditText) view.findViewById(R.id.et_search);
        mBtnSearch = (Button) view.findViewById(R.id.btn_search);
        mListView = (ListView) view.findViewById(R.id.lv_listview);
    }


    @Override
    public String getFragmentTitle()
    {
        return "我的订单";
    }
    @Override
    public void onGetDataSuccess(ResultItem resultItem)
    {
        if(resultItem != null)
        {
            mActivity.showCroutonToast(resultItem.message);
            if(resultItem.success())
            {

            }
            else
            {
                Type type = new TypeToken<List<DrugItem>>()
                {
                }.getType();
               List<DrugItem> drugItems = GsonUtils.jsonToResultItemToList(DrugItem.DEBUG_SUCCESS, type);
                if (null != drugItems)
                {
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
    public void onGetDataFailured(String msg)
    {

    }

    private class OnClickListenerImpl implements View.OnClickListener
    {
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.btn_search://搜索
                    search();
                    break;
            }
        }
    }

    private void search()
    {
        if(TextUtils.isEmpty(mEtSearch.getText()))
        {
            mActivity.showCroutonToast("请输入搜索关键字");
            return;
        }
        mActivity.hideSoftKeyBorard(mEtSearch);
        String keyword = mEtSearch.getText().toString();
        UserMedicalSearchPresenter   userMedicalSearchPresenter = new UserMedicalSearchPresenterImpl(mActivity, this);
        userMedicalSearchPresenter.doAddMedicalSearch(keyword);
    }


}
