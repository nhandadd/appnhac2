package com.example.appnhachay.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.appnhachay.Fragment.TimKiem_Fragment;
import com.example.appnhachay.Fragment.TrangChu_Fragment;

public class ViewPagerAdapterFragment extends FragmentStateAdapter {

    private static final int NUM_PAGES = 2;

    public ViewPagerAdapterFragment(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new TimKiem_Fragment();

            default:
                return new TrangChu_Fragment();
        }
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}
