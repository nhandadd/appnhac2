package com.example.appnhachay.Service;

public class ApiService {
    private static String base_url = "https://nhacmp3nhachay.000webhostapp.com/Server/";
    public static DataService getService(){
                return ApiRetrofitClient.getClient(base_url).create(DataService.class);
    }
}
