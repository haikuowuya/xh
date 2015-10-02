package com.xinheng.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ListPopupWindow;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.xinheng.R;
import com.xinheng.adapter.auto.PopupListAdapter;
import com.xinheng.base.BaseFragment;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.auto.BodyKV;
import com.xinheng.mvp.presenter.BodypartDetailPresenter;
import com.xinheng.mvp.presenter.BodypartPresenter;
import com.xinheng.mvp.presenter.impl.BodypartDetailPresenterImpl;
import com.xinheng.mvp.presenter.impl.BodypartPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.DensityUtils;
import com.xinheng.util.GsonUtils;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/18 0018
 * 时间： 17:48
 * 说明： 症状自测-智能导诊页面
 */
public class AutoCheckFragment extends BaseFragment implements DataView
{
    public static final String REQUEST_BODY_PART_DETAIL_LIST_TAG = "body_part_detail_list";
    public static final String REQUEST_BODY_PART_LIST_TAG = "body_part_list";

    public static AutoCheckFragment newInstance()
    {
        AutoCheckFragment fragment = new AutoCheckFragment();
        return fragment;
    }

    /**
     * 标识当前选择的类别状态
     */
    private TextView mTvText;
    /***
     * 类型选择switch(图片、列表)
     */
    private Switch mSwitchType;
    /***
     * 性别选择switch
     */
    private Switch mSwitchSex;
    private ListPopupWindow mListPopupWindow;
    /***
     * 图片
     */
    private ImageView mIvImage;
    /**
     * 旋转
     */
    private ImageView mIvSwitch;
    private List<BodyKV> mBodyKVs = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_auto_check, null);
        initView(view);
        mIsInit = true;
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        setListener();
    }

    private void setListener()
    {
        OnCheckedChangeListenerImpl onCheckedChangeListener = new OnCheckedChangeListenerImpl();
        mSwitchType.setOnCheckedChangeListener(onCheckedChangeListener);
        mSwitchSex.setOnCheckedChangeListener(onCheckedChangeListener);
        OnClickListenerImpl onClickListener = new OnClickListenerImpl();
        mIvSwitch.setOnClickListener(onClickListener);
    }

    private void initView(View view)
    {
        mTvText = (TextView) view.findViewById(R.id.tv_text);
        mSwitchSex = (Switch) view.findViewById(R.id.switch_sex);
        mSwitchType = (Switch) view.findViewById(R.id.switch_type);
        mIvImage = (ImageView) view.findViewById(R.id.iv_image);
        mIvSwitch = (ImageView) view.findViewById(R.id.iv_switch);
        initListPopupWindow();
    }

    private void initListPopupWindow()
    {
        mListPopupWindow = new ListPopupWindow(mActivity);
        mListPopupWindow.setWidth(DensityUtils.getScreenWidthInPx(mActivity));
        mListPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mListPopupWindow.setForceIgnoreOutsideTouch(true);
        mListPopupWindow.setBackgroundDrawable(new ColorDrawable(0xFFF2F2F2));
    }

    @Override
    public String getFragmentTitle()
    {
        return "我的订单";
    }

    @Override
    public void onGetDataSuccess(ResultItem resultItem, String requestTag)
    {
        if (resultItem != null)
        {
            mActivity.showToast(resultItem.message);
            if (resultItem.success())
            {
                if (REQUEST_BODY_PART_LIST_TAG.equals(requestTag))
                {
                    Type type = new TypeToken<List<BodyKV>>()
                    {
                    }.getType();
                    List<BodyKV> bodyKVs = GsonUtils.jsonToList(resultItem.properties.getAsJsonArray().toString(), type);
                    if (null != bodyKVs && !bodyKVs.isEmpty())
                    {
                        mBodyKVs = new LinkedList<>();
                        mBodyKVs.addAll(bodyKVs);
                        ShowListPopupWindow();
                    }
                }
                else if (REQUEST_BODY_PART_DETAIL_LIST_TAG.equals(requestTag))
                {

                }
            }

        }
    }

    @Override
    public void onGetDataFailured(String msg, String requestTag)
    {

    }

    private class OnClickListenerImpl implements View.OnClickListener
    {
        public void onClick(View v)
        {
            switch (v.getId())
            {
                case R.id.iv_switch:
                    if (mSwitchSex.isChecked())
                    {
                        BitmapDrawable bitmapDrawable = (BitmapDrawable) mActivity.getResources().getDrawable(R.mipmap.ic_auto_check_man_0);
                        Bitmap initBitmap = ((BitmapDrawable) mIvImage.getDrawable()).getBitmap();
                        if (initBitmap == bitmapDrawable.getBitmap())
                        {
                            mIvImage.setImageResource(R.mipmap.ic_auto_check_man_1);
                        }
                        else
                        {
                            mIvImage.setImageResource(R.mipmap.ic_auto_check_man_0);
                        }
                    }
                    else
                    {
                        BitmapDrawable bitmapDrawable = (BitmapDrawable) mActivity.getResources().getDrawable(R.mipmap.ic_auto_check_woman_0);
                        Bitmap initBitmap = ((BitmapDrawable) mIvImage.getDrawable()).getBitmap();
                        if (initBitmap == bitmapDrawable.getBitmap())
                        {
                            mIvImage.setImageResource(R.mipmap.ic_auto_check_woman_1);
                        }
                        else
                        {
                            mIvImage.setImageResource(R.mipmap.ic_auto_check_woman_0);
                        }
                    }
                    break;

            }
        }
    }

    private class OnCheckedChangeListenerImpl implements CompoundButton.OnCheckedChangeListener
    {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
        {
            if (buttonView == mSwitchType)
            {
                if (isChecked)
                {
                    mTvText.setText("身体部位");
                    if (mListPopupWindow.getAnchorView() == mSwitchType && mListPopupWindow.isShowing())
                    {
                        mListPopupWindow.dismiss();
                        return;
                    }
                }
                else
                {
                    mTvText.setText("器官列表");
                    if (mBodyKVs == null)
                    {
                        BodypartPresenter bodypartPresenter = new BodypartPresenterImpl(mActivity, AutoCheckFragment.this, REQUEST_BODY_PART_LIST_TAG);
                        bodypartPresenter.doGetBodypartList();
                    }
                    else
                    {
                        ShowListPopupWindow();
                    }
                }
            }
            else if (buttonView == mSwitchSex)
            {
                if (mListPopupWindow.getAnchorView() == mSwitchType && mListPopupWindow.isShowing())
                {
                    mSwitchType.setChecked(true);
                }

                if (isChecked)
                {
                    mIvImage.setImageResource(R.mipmap.ic_auto_check_man_1);
                }
                else
                {
                    mIvImage.setImageResource(R.mipmap.ic_auto_check_woman_1);
                }
            }
        }
    }

    private void ShowListPopupWindow()
    {
        mListPopupWindow.setAdapter(new PopupListAdapter(mActivity, mBodyKVs));
        if (mListPopupWindow.getAnchorView() == mSwitchType && mListPopupWindow.isShowing())
        {
            mListPopupWindow.dismiss();
            return;
        }
        else
        {
            mListPopupWindow.setAnchorView(mSwitchType);
            mListPopupWindow.show();

        }
        mListPopupWindow.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                BodyKV bodyKV = (BodyKV) parent.getAdapter().getItem(position);
                BodypartDetailPresenter bodypartDetailPresenter = new BodypartDetailPresenterImpl(mActivity, AutoCheckFragment.this, REQUEST_BODY_PART_DETAIL_LIST_TAG);
                bodypartDetailPresenter.doGetBodypartDetailList(bodyKV.key);
            }
        });
    }

}
