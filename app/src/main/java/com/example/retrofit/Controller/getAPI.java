package com.example.retrofit.Controller;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class getAPI {
    public static final String URL = "https://denandra.000webhostapp.com/";
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
