package com.example.appnhachay.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appnhachay.Adapter.AlbumAdapter;
import com.example.appnhachay.Model.Album;
import com.example.appnhachay.R;
import com.example.appnhachay.Service.ApiService;
import com.example.appnhachay.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumHotFragment extends Fragment {
    View view;
    RecyclerView recyclerViewAlbum;
    TextView tvTitleAlbum, tvXemThemAlbum;
    AlbumAdapter albumAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.fragment_album_hot, container, false);
        recyclerViewAlbum = view.findViewById(R.id.rcvAlbum);
       tvTitleAlbum = view.findViewById(R.id.tvTitleAlbum);
       tvXemThemAlbum = view.findViewById(R.id.tvXemthemAlbum);
       getData();
       return view;
    }

    private void getData() {
        DataService dataService = ApiService.getService();
        Call<List<Album>> callAlbum = dataService.GetAlbumHot();
        callAlbum.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                ArrayList<Album> albumArrayList = (ArrayList<Album>) response.body();
                albumAdapter = new AlbumAdapter(getActivity(), albumArrayList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                recyclerViewAlbum.setLayoutManager(linearLayoutManager);
                recyclerViewAlbum.setAdapter(albumAdapter);
            }
            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
            }
        });
    }
}