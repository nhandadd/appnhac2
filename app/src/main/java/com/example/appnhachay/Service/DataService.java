package com.example.appnhachay.Service;

import com.example.appnhachay.Model.Album;
import com.example.appnhachay.Model.Baihat;
import com.example.appnhachay.Model.Playlist;
import com.example.appnhachay.Model.Quangcao;
import com.example.appnhachay.Model.TheLoaiTrongNgay;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DataService {
    @GET("songbanner.php")
    Call<List<Quangcao>> GetDataBanner();
    @GET("playlistforcurrenday.php")
    Call<List<Playlist>> GetPlaylistCurrentDay();
    @GET("chudevatheloaitrongngay.php")
    Call<TheLoaiTrongNgay> GetChudeTheLoai();
    @GET("albumhot.php")
    Call<List<Album>> GetAlbumHot();
    @GET("baihatduocthich.php")
    Call<List<Baihat>> GetBaiHatHot();
}
