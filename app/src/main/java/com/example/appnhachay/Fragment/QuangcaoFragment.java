package com.example.appnhachay.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appnhachay.Adapter.QuangcaoAdapter;
import com.example.appnhachay.Model.Quangcao;
import com.example.appnhachay.R;
import com.example.appnhachay.Service.ApiService;
import com.example.appnhachay.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import me.relex.circleindicator.CircleIndicator3;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class QuangcaoFragment extends Fragment {
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    QuangcaoAdapter quangcaoAdapter;
    View view;
    Runnable runnable;
    Handler handler;
    int currentItem;
    ArrayList<Quangcao> arrayQuangcao;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_quangcao, container, false);
        anhxa();
        getData();
        return view;
    }
    private void anhxa() {
        viewPager = view.findViewById(R.id.viewPager2);
        circleIndicator = view.findViewById(R.id.indicatorDefalt);
    }
    private void getData(){
        DataService dataService = ApiService.getService();
        Call<List<Quangcao>> callQuangcao = dataService.GetDataBanner();
        callQuangcao.enqueue(new Callback<List<Quangcao>>() {
            @Override
            public void onResponse(Call<List<Quangcao>> call, Response<List<Quangcao>> response) {
                arrayQuangcao  = (ArrayList<Quangcao>) response.body();
               quangcaoAdapter = new QuangcaoAdapter(getActivity(), arrayQuangcao);
                viewPager.setAdapter(quangcaoAdapter);
                circleIndicator.setViewPager(viewPager);
                handler = new Handler();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        currentItem = viewPager.getCurrentItem();
                        currentItem++;
                        if (currentItem >= viewPager.getAdapter().getCount()){
                            currentItem = 0;
                        }
                        viewPager.setCurrentItem(currentItem,true);
                        handler.postDelayed(runnable, 4500);
                    }
                };
                handler.postDelayed(runnable, 4500);
            }
            @Override
            public void onFailure(Call<List<Quangcao>> call, Throwable t) {
            }
        });
    }
}