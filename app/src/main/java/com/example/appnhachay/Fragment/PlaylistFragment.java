package com.example.appnhachay.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.appnhachay.Activity.DanhsachBaihatActivity;
import com.example.appnhachay.Activity.DanhsachPlaylistActivity;
import com.example.appnhachay.Adapter.PlaylistAdapter;
import com.example.appnhachay.Model.Playlist;
import com.example.appnhachay.R;
import com.example.appnhachay.Service.ApiService;
import com.example.appnhachay.Service.DataService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaylistFragment extends Fragment {

    View view;
    ListView lvPlaylist;
    TextView tvTitle, tvXemthem;
    ArrayList<Playlist> playlistArrayList;
    PlaylistAdapter playlistAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_playlist, container, false);
        lvPlaylist = view.findViewById(R.id.listviewPlaylist);
        tvTitle = view.findViewById(R.id.tvTitlePlaylist);
        tvXemthem = view.findViewById(R.id.tvViewMorePlaylist);
        tvXemthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent1 = new Intent(getActivity(),DanhsachPlaylistActivity.class);
                startActivity(intent1);
            }
        });
        getData();
        return view;
    }
        private void getData(){
            DataService dataService = ApiService.getService();
            Call<List<Playlist>> callQuangcao = dataService.GetPlaylistCurrentDay();
            callQuangcao.enqueue(new Callback<List<Playlist>>() {
                @Override
                public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {
                    playlistArrayList = (ArrayList<Playlist>) response.body();
                   playlistAdapter = new PlaylistAdapter(getActivity(), android.R.layout.simple_list_item_1,playlistArrayList);
                    lvPlaylist.setAdapter(playlistAdapter);
                   setListViewHeightBasedOnChildren(lvPlaylist);
                    lvPlaylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getActivity(), DanhsachBaihatActivity.class);
                            intent.putExtra("itemplaylist",playlistArrayList.get(position));
                            startActivity(intent);
                        }
                    });
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