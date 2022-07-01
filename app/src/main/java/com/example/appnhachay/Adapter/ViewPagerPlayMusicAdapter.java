package com.example.appnhachay.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.appnhachay.Fragment.DanhsachPlayMusicFragment;
import com.example.appnhachay.Fragment.DianhacFragment;
import com.example.appnhachay.Fragment.TimKiem_Fragment;
import com.example.appnhachay.Fragment.TrangChu_Fragment;

public class ViewPagerPlayMusicAdapter extends FragmentStateAdapter {
    private static final int NUM_PAGES = 2;
    public ViewPagerPlayMusicAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new DianhacFragment();

            default:
                return new DanhsachPlayMusicFragment();
        }
    }
    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}
