package com.xinheng.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xinheng.MainActivity;
import com.xinheng.OnlineActivity;
import com.xinheng.R;
import com.xinheng.RegisterActivity;
import com.xinheng.XHApplication;
import com.xinheng.drawable.DrawerArrowDrawable;
import com.xinheng.fragment.MenuFragment;
import com.xinheng.mvp.model.LoginSuccessItem;
import com.xinheng.slidingmenu.SlidingMenu;
import com.xinheng.util.Constants;
import com.xinheng.util.GsonUtils;
import com.xinheng.util.IntentUtils;
import com.xinheng.util.ToastUtils;
import com.xinheng.util.ViewUtils;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 *
 */
public abstract class BaseActivity extends AppCompatActivity implements IActivityTitle
{
    /***
     * Activity 跳转时通过Intent传值的KEY
     */
    protected static final String EXTRA_ITEM = "item";
    protected SlidingMenu mSlidingMenu;
    /**
     * Activity的一个实例{@link  BaseActivity#onCreate(Bundle) }
     */
    protected BaseActivity mActivity;
    /**
     * {@link SharedPreferences}的一个实例
     */
    protected SharedPreferences mPreferences;
    /**
     * 继承自baseActivity的外层容器{@link  BaseActivity#setContentView(int)}
     * }
     */
    private FrameLayout mFrameContainer;
    private PtrClassicFrameLayout mPtrClassicFrameLayout;

    private TextView mTvCenterTitle;
    /**
     * 左上角的返回按钮
     */
    private ImageView mIvBack;
    /**
     * 右上角的功能按钮
     */
    private ImageView mIvRight;

    /**
     * 顶部的Title布局
     */
    private LinearLayout mLinearTitleContainer;

    /**
     * 网络加载时显示的进度框
     */
    private ProgressDialog mProgressDialog;

    public SharedPreferences getPreferences()
    {
        return mPreferences;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mActivity = this;
        XHApplication application = (XHApplication) getApplication();
        application.recordActivity(mActivity);
        mPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        /**
         * 这样设置的话，不会出现滚动
         */
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
              getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE|WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mProgressDialog = new ProgressDialog(mActivity);
        mProgressDialog.setMessage("正在获取数据中……");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener()
        {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event)
            {
                if (keyCode == KeyEvent.KEYCODE_BACK)
                {
                    mProgressDialog.dismiss();
                    mActivity.finish();
                    return true;
                }
                return false;
            }
        });

        if (!IntentUtils.isLauncherIntent(getIntent()) && !(this instanceof RegisterActivity))
        {
            int statusCoclor = getResources().getColor(R.color.color_title_background_color);
            ViewUtils.alphaStatusBarAndNavBar(mActivity, statusCoclor, 0xFF000000);
        }
    }

    public void showProgressDialog()
    {
        showProgressDialog(null);
    }

    public void showProgressDialog(CharSequence msg)
    {
        if (!mProgressDialog.isShowing())
        {
            if (!TextUtils.isEmpty(msg))
            {
                mProgressDialog.setMessage(msg);
            }
            mProgressDialog.show();

        }
    }

    public void dismissProgressDialog()
    {
        if (mProgressDialog.isShowing())
        {
            mProgressDialog.dismiss();
            ;
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (mProgressDialog.isShowing())
        {
            mProgressDialog.dismiss();
        }
    }

    public void showToast(String text)
    {
        Toast.makeText(mActivity, text, Toast.LENGTH_SHORT).show();
    }

    public void showCroutonToast(String text)
    {
        ToastUtils.showCrouton(mActivity, text, getContentViewGroup());
    }

    public View getCenterTitleView()
    {
        return mTvCenterTitle;
    }

    public View getTitleContainer()
    {
        return mLinearTitleContainer;
    }

    @Override
    public void setContentView(int layoutResID)
    {
        super.setContentView(R.layout.activity_base);   //TODO XXX
        mFrameContainer = (FrameLayout) findViewById(R.id.frame_container);
        mPtrClassicFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.ptr_container);
        mLinearTitleContainer = (LinearLayout) findViewById(R.id.linear_title_container);
        mTvCenterTitle = (TextView) findViewById(R.id.tv_center_title);
        mIvRight = (ImageView) findViewById(R.id.iv_right);
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mTvCenterTitle.setText(getActivityTitle());
        View contentView = LayoutInflater.from(mActivity).inflate(layoutResID, null);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        mFrameContainer.addView(contentView, layoutParams);
        mIvBack.setImageDrawable(new DrawerArrowDrawable.BackDrawerArrowDrawable(mActivity));
        if (IntentUtils.isLauncherIntent(getIntent()))
        {
            mIvBack.setVisibility(View.GONE);
        }
        //首页和在线售药页面隐藏左上角的返回按钮
        if(this instanceof  MainActivity || this instanceof OnlineActivity)
        {
            mIvBack.setVisibility(View.GONE);
        }
        mIvBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });

        findViewById(R.id.iv_right).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mSlidingMenu.toggle(true);
            }
        });
        mPtrClassicFrameLayout.setPtrHandler(new PtrHandlerImpl());
        initSlidingMenu();
    }

    private void initSlidingMenu()
    {
        mSlidingMenu = new SlidingMenu(mActivity);
        mSlidingMenu.setMode(SlidingMenu.RIGHT);
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
//        mSlidingMenu.setTouchModeBehind(SlidingMenu.TOUCHMODE_FULLSCREEN);
        mSlidingMenu.setShadowWidthRes(R.dimen.dimen_slidingmenu_shadow_width);
        mSlidingMenu.setShadowDrawable(R.drawable.shape_slidingmenu_shadow);
        mSlidingMenu.setBehindOffsetRes(R.dimen.dimen_slidingmenu_offset);
        mSlidingMenu.setFadeDegree(0.65f);
        mSlidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        mSlidingMenu.setBehindCanvasTransformer(new SlidingMenu.CanvasTransformer()
        {
            @Override
            public void transformCanvas(Canvas canvas, float percentOpen)
            {
                float scale = (float) (percentOpen * 0.25 + 0.75);
                canvas.scale(scale, scale, canvas.getWidth() / 2, canvas.getHeight() / 2);
                // canvas.scale(percentOpen, 1, 0, 0);
            }
        });
        mSlidingMenu.setMenu(R.layout.layout_menu);
        // 设置隐藏在AboveMenu菜单后面的菜单
        getSupportFragmentManager().beginTransaction().replace(R.id.menu_container, MenuFragment.newInstance()).commit();

    }

    public void setIvRightVisibility(int visibility)
    {
        mIvRight.setVisibility(visibility);
    }

    public ViewGroup getContentViewGroup()
    {
        return mFrameContainer;
    }

    public boolean canDoRefresh()
    {
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_MENU)
        {
            mSlidingMenu.toggle(true);
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            if (mSlidingMenu.isMenuShowing())
            {
                mSlidingMenu.showContent(true);
                return true;
            }
            else if (mActivity instanceof MainActivity)
            {
                XHApplication.getInstance().showExitDialog(mActivity);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void closeMenu()
    {
        if (mSlidingMenu.isMenuShowing())
        {
            mSlidingMenu.showContent(true);
        }
    }

    /***
     * 获取登录成功后的用户信息，用于下一步的操作
     *
     * @return null  或者 {@link LoginSuccessItem}对象
     */
    public LoginSuccessItem getLoginSuccessItem()
    {
        LoginSuccessItem loginSuccessItem = null;
        String prefLogin = mPreferences.getString(Constants.PREF_LOGIN, null);
        if (!TextUtils.isEmpty(prefLogin))
        {
            loginSuccessItem = GsonUtils.jsonToClass(prefLogin, LoginSuccessItem.class);
        }
        return loginSuccessItem;
    }

    /***
     * 将登陆成功后的用户信息保存到sharepreference中，
     * @param loginSuccessItem
     */
    public void saveLoginSuccessItem(LoginSuccessItem loginSuccessItem)
    {
        mPreferences.edit().putString(Constants.PREF_LOGIN, GsonUtils.toJson(loginSuccessItem)).commit();
    }

    /**
     * 隐藏软键盘，根据当前焦点View
     */
    public void hideSoftKeyBorard()
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive())
        {
            if (imm.isActive())
            {
                View focusView = mActivity.getCurrentFocus();
                if (null != focusView)
                {
                    imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
                }
            }
        }
    }

    /**
     * 隐藏软键盘，根据给定的View
     * @param view
     */
    public void hideSoftKeyBorard(View view)
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive())
        {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private class PtrHandlerImpl implements PtrHandler
    {
        @Override
        public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View view1)
        {
            return canDoRefresh();
        }

        @Override
        public void onRefreshBegin(PtrFrameLayout ptrFrameLayout)
        {
            ptrFrameLayout.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    mPtrClassicFrameLayout.refreshComplete();
                }
            }, 2000L);
        }
    }

}
