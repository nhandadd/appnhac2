package com.example.appnhachay.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.appnhachay.Adapter.PlaylistAdapter;
import com.example.appnhachay.Model.Playlist;
import com.example.appnhachay.R;
import com.example.appnhachay.Service.ApiService;
import com.example.appnhachay.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaylistFragment extends Fragment {

    View view;
    ListView listView;
    TextView tvTitle, tvXemthem;
    ArrayList<Playlist> arrayPlaylist;
    PlaylistAdapter playlistAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_playlist, container, false);
        listView = view.findViewById(R.id.listviewPlaylist);
        tvTitle = view.findViewById(R.id.tvTitlePlaylist);
        tvXemthem = view.findViewById(R.id.tvViewMorePlaylist);
        getData();
        return view;
    }
        private void getData(){
            DataService dataService = ApiService.getService();
            Call<List<Playlist>> callQuangcao = dataService.GetPlaylistCurrentDay();
            callQuangcao.enqueue(new Callback<List<Playlist>>() {
                @Override
                public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {
                  arrayPlaylist = (ArrayList<Playlist>) response.body();
                   playlistAdapter = new PlaylistAdapter(getActivity(), android.R.layout.simple_list_item_1,arrayPlaylist);
                   listView.setAdapter(playlistAdapter);
                   setListViewHeightBasedOnChildren(listView);
                }
                @Override
                public void onFailure(Call<List<Playlist>> call, Throwable t) {
                }
            });
    }
    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);

            if(listItem != null){
                // This next line is needed before you call measure or else you won't get measured height at all. The listitem needs to be drawn first to know the height.
                listItem.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += listItem.getMeasuredHeight();

            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}