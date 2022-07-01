package com.example.appnhachay.Fragment;

import android.graphics.text.TextRunShaper;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.appnhachay.Adapter.SearchBaihatAdapter;
import com.example.appnhachay.Model.Baihat;
import com.example.appnhachay.R;
import com.example.appnhachay.Service.ApiService;
import com.example.appnhachay.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimKiem_Fragment extends Fragment {
    View view;
    Toolbar toolbar;
    RecyclerView rcvTimkiem;
    TextView tvThongbao;
    SearchBaihatAdapter searchBaihatAdapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DemoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TimKiem_Fragment newInstance(String param1, String param2) {
        TimKiem_Fragment fragment = new TimKiem_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public TimKiem_Fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tim_kiem_, container, false);
        toolbar = view.findViewById(R.id.toolbarTimkiem);
        rcvTimkiem = view.findViewById(R.id.rcvTimkiem);
        tvThongbao = view.findViewById(R.id.tvThongbao);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("");
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
         inflater.inflate(R.menu.search_baihat,menu);
        MenuItem menuItem = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchTuKhoaBaiHat(query);
                Log.d("BBB",query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                SearchTuKhoaBaiHat(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
    private void SearchTuKhoaBaiHat(String query){
        DataService dataService = ApiService.getService();
        Call<List<Baihat>> callBaihatTk = dataService.GetBaiHatTimKiem(query);
        callBaihatTk.enqueue(new Callback<List<Baihat>>() {
            @Override
            public void onResponse(Call<List<Baihat>> call, Response<List<Baihat>> response) {
                ArrayList<Baihat> baihatArrayList = (ArrayList<Baihat>) response.body();

                if (baihatArrayList.size() > 0){
                    Log.d("BBB","baihat"+ baihatArrayList.get(0).getTenBaihat());
                    searchBaihatAdapter = new SearchBaihatAdapter(getActivity(),baihatArrayList);
                    rcvTimkiem.setAdapter(searchBaihatAdapter);
                    tvThongbao.setVisibility(View.GONE);
                }else {
                    rcvTimkiem.setVisibility(View.GONE);
                    tvThongbao.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Baihat>> call, Throwable t) {

            }
        });
    }
}