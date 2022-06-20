package com.example.appnhachay.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;


import com.example.appnhachay.Adapter.ViewPagerAdapterFragment;
import com.example.appnhachay.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
    ViewPagerAdapterFragment viewPagerAdapterFragment;
    ViewPager2 viewPager2;
    TabLayout tabLayout;
    private int[] tabIcons = {
            R.drawable.icontrangchu,
            R.drawable.iconsearch,
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager2 = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tablayout);
        viewPagerAdapterFragment = new ViewPagerAdapterFragment(this);
        viewPager2.setAdapter(viewPagerAdapterFragment);
        tabLayout.getTabAt(0).setIcon(  R.drawable.icontrangchu);
        tabLayout.getTabAt(1).setIcon(  R.drawable.iconsearch);
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText("Tab"+position);
            }
        }).attach();
    }
}