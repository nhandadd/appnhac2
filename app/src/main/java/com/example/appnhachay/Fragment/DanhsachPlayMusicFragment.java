package com.example.appnhachay.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appnhachay.Activity.PlayMusicActivity;
import com.example.appnhachay.Adapter.DanhsachPlayMusicAdapter;
import com.example.appnhachay.Model.Baihat;
import com.example.appnhachay.R;

public class DanhsachPlayMusicFragment extends Fragment {
    RecyclerView rcvDsPlayMusic;
    View view;
    DanhsachPlayMusicAdapter playMusicAdapter;
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
    public static DanhsachPlayMusicFragment newInstance(String param1, String param2) {
        DanhsachPlayMusicFragment fragment = new DanhsachPlayMusicFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public DanhsachPlayMusicFragment() {
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
        view = inflater.inflate(R.layout.fragment_danhsach_play_music, container, false);
        rcvDsPlayMusic = view.findViewById(R.id.rcvDanhsachPlay);
        if (PlayMusicActivity.baihatArrayList.size() > 0 ){
            playMusicAdapter = new DanhsachPlayMusicAdapter(getActivity(), PlayMusicActivity.baihatArrayList);
            rcvDsPlayMusic.setAdapter(playMusicAdapter);
        }
        return view;
    }
}