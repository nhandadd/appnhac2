package com.example.appnhachay.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appnhachay.Adapter.BaihatHotAdapter;
import com.example.appnhachay.Model.Baihat;
import com.example.appnhachay.R;
import com.example.appnhachay.Service.ApiService;
import com.example.appnhachay.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BaiHatHotFragment extends Fragment {
    View view;
    RecyclerView rcvBaihatHot;
    BaihatHotAdapter baihatHotAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bai_hat_hot, container, false);
        rcvBaihatHot = view.findViewById(R.id.rcvBaihatHot);
        getDaTa();
        return view;
    }

    private void getDaTa() {
        DataService dataService = ApiService.getService();
        Call<List<Baihat>> callBaihat = dataService.GetBaiHatHot();
        callBaihat.enqueue(new Callback<List<Baihat>>() {
            @Override
            public void onResponse(Call<List<Baihat>> call, Response<List<Baihat>> response) {
                ArrayList<Baihat> baihatArrayList = (ArrayList<Baihat>) response.body();
                baihatHotAdapter = new BaihatHotAdapter(getActivity(), baihatArrayList);
                rcvBaihatHot.setAdapter(baihatHotAdapter);

            }

            @Override
            public void onFailure(Call<List<Baihat>> call, Throwable t) {

            }
        });
    }
}