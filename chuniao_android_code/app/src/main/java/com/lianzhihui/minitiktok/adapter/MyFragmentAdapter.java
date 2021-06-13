package com.lianzhihui.minitiktok.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Copyright (C), 2019-2019, wase_five
 * FileName: MyFragmentAdapter
 * Author: echo
 * Date: 2019/10/24 16:54
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
public class MyFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    public MyFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }


    @Override
    public int getCount() {
        return fragments.size();
    }
}
