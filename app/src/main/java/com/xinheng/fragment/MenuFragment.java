package com.xinheng.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.xinheng.MainActivity;
import com.xinheng.R;
import com.xinheng.RegisterActivity;
import com.xinheng.base.BaseFragment;

/**
 * 作者： raiyi-suzhou
 * 日期： 2015/8/17 0017
 * 时间： 16:53
 * 说明：
 */
public class MenuFragment extends BaseFragment
{

    public static MenuFragment newInstance()
    {
        MenuFragment fragment = new MenuFragment();
        return fragment;
    }

    private ListView mListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_menu, null);//TODO
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        mListView = (ListView) view.findViewById(R.id.lv_listview);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mListView.setAdapter(ArrayAdapter.createFromResource(mActivity, R.array.array_menu, android.R.layout.simple_list_item_1));

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                final String text = parent.getAdapter().getItem(position).toString();
                new Handler(mActivity.getMainLooper()).postDelayed(new Runnable()
                {
                    public void run()
                    {

                        if (getString(R.string.tv_activity_register).equals(text))//  注册
                        {

                            RegisterActivity.actionRegister(mActivity);
                        }

                    }
                }, 200L);

                if (mActivity instanceof MainActivity)
                {
                    MainActivity mainActivity = (MainActivity) mActivity;
                    mainActivity.closeMenu();
                }
            }
        });


    }

    @Override
    public String getFragmentTitle()
    {
        return getString(R.string.tv_fragment_menu);
    }
}
