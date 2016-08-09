package com.tongcheng.qichezulin.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by 林尧 on 2016/7/23.
 */
public class FragmentStateAdaper extends FragmentStatePagerAdapter {

    //标签
    List<String> stringList;
    //片段集合
    List<Fragment> fragmentList;


    public FragmentStateAdaper(FragmentManager fm, List<String> stringList, List<Fragment> fragmentList) {
        super(fm);
        this.stringList = stringList;
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList == null ? 0 : fragmentList.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return stringList.get(position);
    }
}
