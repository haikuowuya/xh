package com.xinheng.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.xinheng.AddOrModifyPatientActivity;
import com.xinheng.R;
import com.xinheng.adapter.user.UserPatientListAdapter;
import com.xinheng.base.BaseFragment;
import com.xinheng.eventbus.OnAddPatientItemEvent;
import com.xinheng.eventbus.OnSelectPatientEvent;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.user.UserPatientItem;
import com.xinheng.mvp.presenter.DeletePatientPresenter;
import com.xinheng.mvp.presenter.UserPatientPresenter;
import com.xinheng.mvp.presenter.impl.DeletePatientPresenterImpl;
import com.xinheng.mvp.presenter.impl.UserPatientPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明： 常用就诊人列表界面
 */
public class UserPatientListFragment extends BaseFragment implements DataView
{
    public static final String ARG_FROM_SELECT_PATIENT = "from_select_patient";
    public static final String REQUEST_GET_PATIENT_LIST_TAG = "get_patient_list";
    public static final String REQUEST_DELETE_PATIENT_TAG = "delete_patient";

    /***
     * 修改更新
     */
    private final int CONTEXTMENU_UPDATE = 2;
    /**
     * 删除
     */
    private final int CONTEXTMENU_DELETE = 3;
    /***
     * 取消
     */
    private final int CONTEXTMENU_CANCEL = 4;

    /***
     * 当前长按操作的position
     */
    private int mOptPosition = 0;

    public static UserPatientListFragment newInstance(boolean fromSelectPatient)
    {
        UserPatientListFragment fragment = new UserPatientListFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(ARG_FROM_SELECT_PATIENT, fromSelectPatient);
        fragment.setArguments(bundle);
        return fragment;
    }

    private boolean mFromSelectPatient = false;
    private LinkedList<UserPatientItem> mUserPatientItems = new LinkedList<>();
    private UserPatientListAdapter mUserPatientListAdapter;
    private ListView mListView;
    private PtrClassicFrameLayout mPtrClassicFrameLayout;
    /**
     * 添加就诊人
     */
    private ImageView mIvAddPatient;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_user_patient, null);
        initView(view);
        mIsInit = true;
        return view;
    }

    private void initView(View view)
    {
        mListView = (ListView) view.findViewById(R.id.lv_listview);
        mPtrClassicFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.ptr_list_container);
        mIvAddPatient = (ImageView) view.findViewById(R.id.iv_add_patient);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mFromSelectPatient = getArguments().getBoolean(ARG_FROM_SELECT_PATIENT);
        EventBus.getDefault().register(this);
        mPtrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler()
        {
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout)
            {
                doRefresh();
            }
        });
        mIvAddPatient.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AddOrModifyPatientActivity.actionAddPatient(mActivity, null);
            }
        });
    }

    //===============================EVENT BUS========================
    @Subscribe
    public void onEventMainThread(OnAddPatientItemEvent event)
    {
        if (null != event && null != event.mUserPatientItem)
        {
            doGetData();
            //重新去请求一次服务器吧，下面的方式会出现年龄为null

//            mUserPatientItems.addFirst(event.mUserPatientItem);
//            if(null != mUserPatientListAdapter)
//            {
//                mUserPatientListAdapter.notifyDataSetChanged();
//            }
//            else
//            {
//                mUserPatientListAdapter = new UserPatientListAdapter(mActivity, mUserPatientItems);
//                mListView.setAdapter(mUserPatientListAdapter);
//            }
        }
    }

    //===============================EVENT BUS========================
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
        UserPatientPresenter userPatientPresenter = new UserPatientPresenterImpl(mActivity, this, REQUEST_GET_PATIENT_LIST_TAG);
        userPatientPresenter.doGetUserPatient();
        mUserPatientItems.clear();
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
                if (REQUEST_GET_PATIENT_LIST_TAG.equals(requestTag))
                {
                    Type type = new TypeToken<List<UserPatientItem>>()
                    {
                    }.getType();
                    List<UserPatientItem> items = GsonUtils.jsonToResultItemToList(GsonUtils.toJson(resultItem), type);
                    if (null != items)
                    {
                        mUserPatientItems.addAll(items);
                        if (null == mUserPatientListAdapter)
                        {
                            mUserPatientListAdapter = new UserPatientListAdapter(mActivity, mUserPatientItems);
                            mListView.setAdapter(mUserPatientListAdapter);
                        }
                        else
                        {
                            mUserPatientListAdapter.notifyDataSetChanged();
                        }
                        mListView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener()
                        {
                            public void onCreateContextMenu(ContextMenu conMenu, View view, ContextMenu.ContextMenuInfo info)
                            {
                                conMenu.setHeaderTitle("功能选择");
                                conMenu.add(0, CONTEXTMENU_UPDATE, 0, "修改");
                                conMenu.add(0, CONTEXTMENU_DELETE, 0, "删除");
                                conMenu.add(0, CONTEXTMENU_CANCEL, 0, "取消");
                            }
                        });
                        mListView.setOnItemClickListener(new OnItemClickListenerImpl());
                    }
                }

                else if (REQUEST_DELETE_PATIENT_TAG.equals(requestTag))
                {
                    mUserPatientItems.remove(mOptPosition);
                    mUserPatientListAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        mOptPosition = info.position;
        switch (item.getItemId())
        {
            case CONTEXTMENU_UPDATE:
                AddOrModifyPatientActivity.actionAddPatient(mActivity, mUserPatientItems.get(mOptPosition));
                return true;
            case CONTEXTMENU_DELETE:
                DeletePatientPresenter deletePatientPresenter = new DeletePatientPresenterImpl(mActivity, this, REQUEST_DELETE_PATIENT_TAG);
                deletePatientPresenter.doDeletePatient(mUserPatientItems.get(mOptPosition).id);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onGetDataFailured(String msg, String requestTag)
    {

    }

    private class OnItemClickListenerImpl implements AdapterView.OnItemClickListener
    {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            UserPatientItem patientItem = (UserPatientItem) parent.getAdapter().getItem(position);
            if (mFromSelectPatient)
            {
                EventBus.getDefault().post(new OnSelectPatientEvent(patientItem));
                mActivity.finish();
            }
            else
            {
                AddOrModifyPatientActivity.actionAddPatient(mActivity, patientItem);
            }
        }
    }

    protected void refreshComplete()
    {
        mPtrClassicFrameLayout.refreshComplete();
    }
}
