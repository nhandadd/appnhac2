package com.example.appnhachay.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class PagerPlayNhacAdapter extends FragmentStatePagerAdapter {
    public final ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    public PagerPlayNhacAdapter(@NonNull FragmentManager fm) {

        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }
    public void AddFragmet(Fragment fragment){
        fragmentArrayList.add(fragment);
    }
}
