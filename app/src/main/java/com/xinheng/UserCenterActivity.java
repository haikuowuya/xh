package com.xinheng;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinheng.base.BaseActivity;
import com.xinheng.mvp.model.IconTextItem;
import com.xinheng.util.DensityUtils;
import com.xinheng.util.SplitUtils;
import com.xinheng.util.ToastUtils;
import com.xinheng.view.CircleImageView;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/19 0019
 * 时间： 11:27
 * 说明：
 */
public class UserCenterActivity extends BaseActivity
{
    private LinearLayout mLinearListContainer;

    private LinearLayout mLinearGridContainer;
    /**
     * 用户头像
     */
    private CircleImageView mIvPhoto;
    /***
     * 用户名称
     */
    private TextView mTvUserName;

    public static void actionUserCenter(BaseActivity activity)
    {
        Intent intent = new Intent(activity, UserCenterActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center); //TODO
        initView();
        fillLinearGridContainer();
        fillLinearListContainer();

    }

    private void fillLinearListContainer()
    {
        String[] strings = getResources().getStringArray(R.array.array_user_list_nav);
        for (int i = 0; i < strings.length; i++)
        {
            String[] tmp = SplitUtils.split(strings[i]);
            if (null != tmp && tmp.length > 0 && tmp.length == 2)
            {
                int iconId = getResources().getIdentifier(tmp[0], "mipmap", getPackageName());
                String text = tmp[1];
                View view = LayoutInflater.from(mActivity).inflate(R.layout.list_icon_text_item, null);
                TextView textView = (TextView) view.findViewById(R.id.tv_text);
                ImageView imageView = (ImageView) view.findViewById(R.id.iv_icon);
                textView.setText(text);
                imageView.setImageResource(iconId);
                int height = (int) getResources().getDimension(R.dimen.dimen_user_list_single_item_height);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,  height);
                mLinearListContainer.addView(view, layoutParams);
                view.setOnClickListener(new OnLinearItemClickListenerImpl(new IconTextItem(iconId, text)));
            }
            else
            {
                View view = new View(mActivity);
                int height = DensityUtils.dpToPx(mActivity, 20.f);
                view.setBackgroundColor(0xFFEBEBEB);
                mLinearListContainer.addView(view, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height));
            }
        }
    }

    private void fillLinearGridContainer()
    {
        String[] strings = getResources().getStringArray(R.array.array_user_grid_nav);
        for (int i = 0; i < strings.length; i++)
        {
            String[] tmp = SplitUtils.split(strings[i]);
            if (null != tmp && tmp.length > 0 && tmp.length == 2)
            {
                int iconId = getResources().getIdentifier(tmp[0], "mipmap", getPackageName());
                String text = tmp[1];
                View view = LayoutInflater.from(mActivity).inflate(R.layout.grid_user_icon_text_item, null);
                TextView textView = (TextView) view.findViewById(R.id.tv_text);
                ImageView imageView = (ImageView) view.findViewById(R.id.iv_icon);
                textView.setText(text);
                imageView.setImageResource(iconId);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,1.f);
                mLinearGridContainer.addView(view, layoutParams);
                view.setOnClickListener(new OnLinearItemClickListenerImpl(new IconTextItem(iconId, text)));
            }
        }
    }

    private void initView()
    {
        mLinearListContainer = (LinearLayout) findViewById(R.id.linear_list_container);
        mLinearGridContainer = (LinearLayout) findViewById(R.id.linear_grid_container);
        mIvPhoto = (CircleImageView) findViewById(R.id.iv_photo);
        mTvUserName = (TextView) findViewById(R.id.tv_username);
    }

     private class  OnLinearItemClickListenerImpl implements  View.OnClickListener
     {
         private IconTextItem mIconTextItem;

         public OnLinearItemClickListenerImpl(IconTextItem iconTextItem)
         {
             mIconTextItem = iconTextItem;
         }

         @Override
         public void onClick(View v)
         {
//             ToastUtils.showCrouton(mActivity, mIconTextItem.text);
             if(getString(R.string.tv_activity_user_counsel).equals(mIconTextItem.text))
             {
                 UserCounselActivity.actionUserCounsel(mActivity);
             }
             else if(getString(R.string.tv_activity_user_remind).equals(mIconTextItem.text))
             {
                 UserRemindActivity.actionUserRemind(mActivity);
             }
             else if(getString(R.string.tv_activity_user_subscribe).equals(mIconTextItem.text))
             {
                 UserSubscribeActivity.actionUserSubscribe(mActivity);
             }
         }
     }


    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_user_center);
    }
}
