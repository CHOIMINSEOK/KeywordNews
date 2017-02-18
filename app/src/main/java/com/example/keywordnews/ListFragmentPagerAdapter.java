package com.example.keywordnews;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ListFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private List<ListFragment> mFragments;

    public ListFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragments = new ArrayList<>();

        for(int i = 0; i< 2; i++){
            addListFragment(new ListFragment());

        }
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object fragment = super.instantiateItem(container, position);
        mFragments.set(position, (ListFragment) fragment);
        return fragment;
    }

    public void addListFragment(ListFragment fragment) {
        mFragments.add(fragment);
    }

    public ListFragment getFragment(int position){
        return mFragments.get(position);
    }

}
