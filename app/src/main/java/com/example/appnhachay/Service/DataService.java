package com.example.appnhachay.Service;

import com.example.appnhachay.Model.Album;
import com.example.appnhachay.Model.Baihat;
import com.example.appnhachay.Model.ChuDe;
import com.example.appnhachay.Model.Playlist;
import com.example.appnhachay.Model.Quangcao;
import com.example.appnhachay.Model.TheLoai;
import com.example.appnhachay.Model.TheLoaiTrongNgay;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

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
    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<Baihat>> GetBaiHatQuangCao(@Field("idquangcao") String id);
    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<Baihat>> GetBaiHatPlayList(@Field("idplaylist") String idplaylist);
    @GET("danhsachcacplaylist.php")
    Call<List<Playlist>> GetListPlayList();
    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<Baihat>> GetBaiHatTheLoai(@Field("idtheloai") String idtheloai);
    @GET("tatcachude.php")
    Call<List<ChuDe>> GetListChuDe();
    @FormUrlEncoded
    @POST("theloaitheochude.php")
    Call<List<TheLoai>> GetTheLoaiTheoChuDe(@Field("idchude") String idchude);
    @GET("tatcaalbum.php")
    Call<List<Album>> GetListAlbum();
    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<Baihat>> GetBaiHatAlbum(@Field("idalbum") String idalbum);
    @FormUrlEncoded
    @POST("updateluotthich.php")
    Call<String> UpdateLuotThich(@Field("luotthich") String luotthich, @Field("idbaihat") String idbaihat);
    @FormUrlEncoded
    @POST("searchbaihat.php")
    Call<List<Baihat>> GetBaiHatTimKiem(@Field("tukhoa") String tukhoa);
}
