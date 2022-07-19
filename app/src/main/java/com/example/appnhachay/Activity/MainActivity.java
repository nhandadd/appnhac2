package com.example.appnhachay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;


import com.example.appnhachay.Adapter.ViewPagerAdapterFragment;
import com.example.appnhachay.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {
    ViewPagerAdapterFragment viewPagerPlaynhacAdapter;
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
        viewPagerPlaynhacAdapter = new ViewPagerAdapterFragment(this);
        viewPager2.setAdapter(viewPagerPlaynhacAdapter);
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            tab.setIcon(tabIcons[position]);
            switch (position){
//                case 0:
//                    tab.setText("Trang chu");
//                   break;
//                case 1:
//                    tab.setText("Tim Kiem");
            }
        }).attach();
       viewPager2.setUserInputEnabled(false);
    }
}