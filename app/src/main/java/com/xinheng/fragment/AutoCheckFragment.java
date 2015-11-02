package com.xinheng.fragment;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.xinheng.AutoCheckActivity;
import com.xinheng.R;
import com.xinheng.adapter.auto.BodyPartListAdapter;
import com.xinheng.base.BaseFragment;
import com.xinheng.mvp.model.ResultItem;
import com.xinheng.mvp.model.auto.BodyKV;
import com.xinheng.mvp.presenter.BodypartPresenter;
import com.xinheng.mvp.presenter.impl.BodypartPresenterImpl;
import com.xinheng.mvp.view.DataView;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.SplitUtils;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

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
    /**
     * 前部
     */
    public static final int TYPE_FRONT = 0;
    /**
     * 后部
     */
    public static final int TYPE_BACK = 1;

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
     * 类型选择switch(图片、列表),选中是为图片， 否则为列表
     */
    private Switch mSwitchType;
    /***
     * 性别选择switch,选中时为男， 否则为女;
     */
    private Switch mSwitchSex;
    /***
     * 图片
     */
    private ImageView mIvImage;
    /**
     * 旋转
     */
    private ImageView mIvSwitch;
    private List<BodyKV> mBodyKVs = null;
    /***
     * 点击获取的身体部位对应的症状
     */
    private BodyKV mClickBodyKv;

    private ListView mListView;
    PhotoViewAttacher mAttacher;

    /***
     * 默认前部
     */
    private int mFrontOrBack = TYPE_FRONT;

    private HashMap<String, String> mDefaultBodyKVs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_auto_check, null);    //TODO
        initView(view);
        mIsInit = true;
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        setListener();
        if (mDefaultBodyKVs == null)
        {
            mDefaultBodyKVs = new HashMap<>();
            String[] arrayStrs = getResources().getStringArray(R.array.array_body_kvs);
            if (null != arrayStrs && arrayStrs.length > 0)
            {
                for (String str : arrayStrs)
                {

                    String[] tmp = SplitUtils.split(str);

                    mDefaultBodyKVs.put(tmp[0], tmp[1]);

                }
            }
        }
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
        mListView = (ListView) view.findViewById(R.id.lv_listview);
        mListView.setVisibility(View.GONE);
        initListPopupWindow();
        mAttacher = new PhotoViewAttacher(mIvImage);

        mAttacher.setOnViewTapListener(new OnPhotoTapListenerImpl());
        mAttacher.setOnPhotoTapListener(new OnPhotoTapListenerImpl());
    }

    private void initListPopupWindow()
    {
//        mListPopupWindow = new ListPopupWindow(mActivity);
//        mListPopupWindow.setWidth(DensityUtils.getScreenWidthInPx(mActivity));
//        mListPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//        mListPopupWindow.setForceIgnoreOutsideTouch(true);
//        mListPopupWindow.setBackgroundDrawable(new ColorDrawable(0xFFF2F2F2));
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
                        showBodyListView();
                    }
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
                            mFrontOrBack = TYPE_FRONT;
                        }
                        else
                        {
                            mIvImage.setImageResource(R.mipmap.ic_auto_check_man_0);
                            mFrontOrBack = TYPE_BACK;

                        }
                    }
                    else
                    {
                        BitmapDrawable bitmapDrawable = (BitmapDrawable) mActivity.getResources().getDrawable(R.mipmap.ic_auto_check_woman_0);
                        Bitmap initBitmap = ((BitmapDrawable) mIvImage.getDrawable()).getBitmap();
                        if (initBitmap == bitmapDrawable.getBitmap())
                        {
                            mIvImage.setImageResource(R.mipmap.ic_auto_check_woman_1);
                            mFrontOrBack = TYPE_FRONT;
                        }
                        else
                        {
                            mIvImage.setImageResource(R.mipmap.ic_auto_check_woman_0);
                            mFrontOrBack = TYPE_BACK;
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
                mSwitchType.setChecked(isChecked);
                if (isChecked)
                {
                    mTvText.setText("身体部位");
                    mListView.setVisibility(View.GONE);
                    mIvSwitch.setVisibility(View.VISIBLE);
                }
                else
                {
                    mTvText.setText("器官列表");
                    mIvSwitch.setVisibility(View.GONE);
                    if (mBodyKVs == null)
                    {
                        BodypartPresenter bodypartPresenter = new BodypartPresenterImpl(mActivity, AutoCheckFragment.this, REQUEST_BODY_PART_LIST_TAG);
                        bodypartPresenter.doGetBodypartList();
                    }
                    else
                    {
                        showBodyListView();
                    }
                }
            }
            else if (buttonView == mSwitchSex)
            {
                mSwitchType.setChecked(true);
                mSwitchSex.setChecked(isChecked);
                mFrontOrBack = TYPE_FRONT;
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

    /**
     * 显示身体部位ListView
     */
    private void showBodyListView()
    {
        mListView.setVisibility(View.VISIBLE);
        mListView.setAdapter(new BodyPartListAdapter(mActivity, mBodyKVs));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        mClickBodyKv = (BodyKV) parent.getAdapter().getItem(position);
                        if (mActivity instanceof AutoCheckActivity)
                        {
                            AutoCheckActivity autoCheckActivity = (AutoCheckActivity) mActivity;
                            autoCheckActivity.addFragment(SymptomListFragment.newInstance(mClickBodyKv));
                        }
                    }
                });
    }

    private class OnPhotoTapListenerImpl implements PhotoViewAttacher.OnPhotoTapListener, PhotoViewAttacher.OnViewTapListener
    {
        public void onPhotoTap(View view, float x, float y)
        {
            float xPercentage = x * 100f;
            float yPercentage = y * 100f;
            xPercentage = Math.round(xPercentage);
            yPercentage = Math.round(yPercentage);
            if (mSwitchType.isChecked())
            {
                String bodyName ="其他";
                if (mSwitchSex.isChecked())       //男
                {
                      bodyName = clickManToBodyKv(xPercentage, yPercentage);
                  //   System.out.println("man x = " + xPercentage + " y = " + yPercentage);
                }
                else     //女
                {
                 bodyName =    clickWomanToBodyKv(xPercentage, yPercentage);
                   // System.out.println("woman x = " + xPercentage + " y = " + yPercentage);

                }
                String value = mDefaultBodyKVs.get(bodyName);
             //   System.out.println("body name = "+ bodyName+" body value = " + value);
                if(!TextUtils.isEmpty(value))
                {
                    BodyKV bodyKV = new BodyKV();
                    bodyKV.key = value;
                    bodyKV.value = bodyName;
                    mClickBodyKv = bodyKV;
                    Toast.makeText(mActivity, bodyName, Toast.LENGTH_SHORT).show();
                    if (mActivity instanceof AutoCheckActivity)
                    {
                        AutoCheckActivity autoCheckActivity = (AutoCheckActivity) mActivity;
                        autoCheckActivity.addFragment(SymptomListFragment.newInstance(mClickBodyKv));
                    }
                }

            }

        }

        @Override
        public void onViewTap(View view, float x, float y)
        {
            float xPercentage = x * 100f;
            float yPercentage = y * 100f;
            //  mActivity.showToast("onViewTap  x =" + xPercentage + " y = " + yPercentage );
        }
    }

    private String  clickWomanToBodyKv(float xPercentage, float yPercentage)
    {
        String bodyName ="其他";
        if (xPercentage > 31.f && xPercentage < 61.f)
        {
            if (yPercentage > 2.f && yPercentage < 14.f)
            {
                bodyName ="头部";
//                mActivity.showToast("头部");

            }
            else if (yPercentage >= 14.f && yPercentage < 21.f)
            {
                bodyName ="颈部";
//                mActivity.showToast("颈部");

            }
        }
        if (xPercentage > 31.f && xPercentage < 62.f)
        {
            if (mFrontOrBack == TYPE_FRONT)
            {
                if (yPercentage >= 21.f && yPercentage < 32.f)
                {
                    bodyName ="胸部";
//                    mActivity.showToast("胸部");

                }
                else if (yPercentage >= 32.f && yPercentage < 48.f)
                {
                    bodyName="腹部";
//                    mActivity.showToast("腹部");

                }
                else if (yPercentage >= 48.f && yPercentage < 54.f)
                {
                    bodyName ="生殖器";
//                    mActivity.showToast("生殖器");

                }
                else if (yPercentage >= 54.f && yPercentage < 95.f)
                {
                    bodyName ="下肢";
//                    mActivity.showToast("下肢");

                }
            }
            else
            {
                if (yPercentage >= 21.f && yPercentage < 48.f)
                {
                    bodyName ="背部";
//                    mActivity.showToast("背部");

                }
                else if (yPercentage >= 48.f && yPercentage < 56.f)
                {
                    bodyName="排泄部";
//                    mActivity.showToast("排泄部");

                }
                else if (yPercentage >= 56.f && yPercentage < 95.f)
                {
                    bodyName ="下肢";
//                    mActivity.showToast("下肢");

                }
            }
        }
        boolean left = ((xPercentage > 5.f && xPercentage < 26.f) && ((yPercentage > 21.f) && yPercentage < 56.f));
        boolean right = (xPercentage > 66.f && xPercentage < 98.f) && (yPercentage > 22.f && yPercentage < 56.f);
        if (left || right)
        {
            bodyName ="手臂";
//            mActivity.showToast("手臂");

        }
        return bodyName;
    }

    private String clickManToBodyKv(float xPercentage, float yPercentage)
    {
        String bodyName = "其他";
        if (xPercentage > 43.f && xPercentage < 72.f)
        {
            if (yPercentage > 2.f && yPercentage < 15.f)
            {
                bodyName = "头部";
//                mActivity.showToast("头部");

            }
            else if (yPercentage >= 15.f && yPercentage < 21.f)
            {
                bodyName = "颈部";
//                mActivity.showToast("颈部");

            }
        }
        if (xPercentage > 40.f && xPercentage < 78.f)
        {
            if (mFrontOrBack == TYPE_FRONT)
            {
                if (yPercentage >= 21.f && yPercentage < 32.f)
                {
                    bodyName = "胸部";
//                    mActivity.showToast("胸部");

                }
                else if (yPercentage >= 32.f && yPercentage < 48.f)
                {
                    bodyName = "腹部";
//                    mActivity.showToast("腹部");

                }
                else if (yPercentage >= 48.f && yPercentage < 54.f)
                {
                    bodyName = "生殖器";
//                    mActivity.showToast("生殖器");

                }
                else if (yPercentage >= 54.f && yPercentage < 95.f)
                {
                    bodyName = "下肢";
//                    mActivity.showToast("下肢");

                }
            }
            else
            {
                if (yPercentage >= 21.f && yPercentage < 48.f)
                {
                    bodyName = "背部";
//                    mActivity.showToast("背部");

                }
                else if (yPercentage >= 48.f && yPercentage < 56.f)
                {
                    bodyName = "排泄部";
//                    mActivity.showToast("排泄部");

                }
                else if (yPercentage >= 56.f && yPercentage < 95.f)
                {
                    bodyName = "下肢";
//                    mActivity.showToast("下肢");

                }
            }
        }
        boolean left = ((xPercentage > 8.f && xPercentage < 38.f) && ((yPercentage > 23.f) && yPercentage < 60.f));
        boolean right = (xPercentage > 78.f && xPercentage < 99.f) && (yPercentage > 23.f && yPercentage < 62.f);
        if (left || right)
        {
            bodyName = "手臂";
//            mActivity.showToast("手臂");

        }
        return bodyName;
    }

}
