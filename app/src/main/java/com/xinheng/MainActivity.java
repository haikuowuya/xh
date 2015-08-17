package com.xinheng;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.KeyEvent;

import com.xinheng.base.BaseActivity;
import com.xinheng.fragment.MainFragment;
import com.xinheng.fragment.MenuFragment;
import com.xinheng.slidingmenu.SlidingMenu;

public class MainActivity extends BaseActivity
{
    private SlidingMenu mSlidingMenu;
    private MainFragment mMainFragment;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);
        initSlidingMenu();
        mMainFragment = MainFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, mMainFragment).commit();
//      requestGetDataFromUrl("https://blog.stylingandroid.com");
    }

    @Override
    public boolean canDoRefresh()
    {
        return  mMainFragment.canDoRefresh();
    }

    private void initSlidingMenu()
    {
        mSlidingMenu = new SlidingMenu(mActivity);
        mSlidingMenu.setMode(SlidingMenu.RIGHT);
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        mSlidingMenu.setShadowWidthRes(R.dimen.dimen_slidingmenu_shadow_width);
//        slidingMenu.setShadowDrawable(R.drawable.shape_slidingmenu_shadow);
        mSlidingMenu.setBehindOffsetRes(R.dimen.dimen_slidingmenu_offset);
        mSlidingMenu.setFadeDegree(0.65f);
        mSlidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        mSlidingMenu.setBehindCanvasTransformer(new SlidingMenu.CanvasTransformer()
        {
            @Override
            public void transformCanvas(Canvas canvas, float percentOpen)
            {
                float scale = (float) (percentOpen*0.25 + 0.75);
                canvas.scale(scale, scale, canvas.getWidth()/2, canvas.getHeight()/2);
               // canvas.scale(percentOpen, 1, 0, 0);
            }
        });
        mSlidingMenu.setMenu(R.layout.layout_menu);
        // 设置隐藏在AboveMenu菜单后面的菜单
        getSupportFragmentManager().beginTransaction().replace(R.id.menu_container, MenuFragment.newInstance()).commit();

    }

    @Override
    public CharSequence getActivityTitle()
    {
        return getString(R.string.tv_activity_main);
    }




    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_MENU)
        {
            this.mSlidingMenu.toggle(true);
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            if (mSlidingMenu.isMenuShowing())
            {
                mSlidingMenu.showContent(true);
                return true;
            }
            else
            {
                onBackPressed();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
