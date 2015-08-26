package com.xinheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.xinheng.DeptFindMedicalActivity;
import com.xinheng.R;
import com.xinheng.base.BaseFragment;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明：  轻松找药Fragment界面
 */
public class FindMedicalFragment extends BaseFragment
{

    public static FindMedicalFragment newInstance()
    {
        FindMedicalFragment fragment = new FindMedicalFragment();
        return fragment;
    }

    /**
     * 搜索按钮
     */
    private ImageView mIvSearch;
    /**
     * 搜索框
     */
    private EditText mEtSearch;

    /***
     * 按照科室找药container
     */
    private RelativeLayout mRelativeDeptContainer;
    /***
     * 按照部位找药container
     */
    private RelativeLayout mRelativePersonContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_find_medical, null);
        initView(view);
        mIsInit = true;
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        OnClickListenerImpl onClickListener = new OnClickListenerImpl();
        mIvSearch.setOnClickListener(onClickListener);
        mRelativeDeptContainer.setOnClickListener(onClickListener);
        mRelativePersonContainer.setOnClickListener(onClickListener);

    }

    private void initView(View view)
    {
        mIvSearch = (ImageView) view.findViewById(R.id.iv_search);
        mEtSearch = (EditText) view.findViewById(R.id.et_search);
        mRelativeDeptContainer = (RelativeLayout) view.findViewById(R.id.relative_dept_container);
        mRelativePersonContainer = (RelativeLayout) view.findViewById(R.id.relative_person_container);
    }

    @Override
    public String getFragmentTitle()
    {
        return "我的订单";
    }

    protected void doRefresh()
    {
        doGetData();
    }

    @Override
    protected void doGetData()
    {

    }

    private class OnClickListenerImpl implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.iv_search://搜索
                    search();
                    break;
                case R.id.relative_dept_container://科室查找:
                    dept();
                    break;

                case R.id.relative_person_container://部位查找
                    person();
                    break;
            }
        }

        /***
         * 搜索按钮的点击事件
         */
        private void search()
        {
            String searchText = mEtSearch.getText().toString();
            if (TextUtils.isEmpty(searchText))
            {
                mActivity.showCroutonToast("搜索关键字不可以为空");
                return;
            }
        }

        /**
         * 按照科室查找container的点击事件
         */
        private void dept()
        {
//            mActivity.showCroutonToast("科室查找");
            DeptFindMedicalActivity.actionDeptFindMedical(mActivity);
        }

        /**
         * 按照人体查找container的点击事件
         */
        private void person()
        {
           mActivity.showCroutonToast("人体查找");

        }
    }

}
