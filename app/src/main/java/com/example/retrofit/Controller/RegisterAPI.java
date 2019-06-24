package com.example.retrofit.Controller;

import com.example.retrofit.Model.LogBarang;
import com.example.retrofit.Model.LogValue;
import com.example.retrofit.Model.Value;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RegisterAPI {
    @FormUrlEncoded
    @POST("/insertBarang.php")
    Call<Value> barang(
            @Field("sku") String sku,
            @Field("nama") String nama,
            @Field("stok") String stok,
            @Field("gambar") String gambar
    );

    @GET("/selectBarang.php")
    Call<Value> view();

    @FormUrlEncoded
    @POST("/updateBarang.php")
    Call<Value> ubah(
            @Field("oldsku") String oldsku,
            @Field("sku") String sku,
            @Field("nama") String nama,
            @Field("stok") String stok,
            @Field("gambar") String gambar
    );

    @FormUrlEncoded
    @POST("/deleteBarang.php")
    Call<Value> hapus(@Field("sku") String sku);

    @FormUrlEncoded
    @POST("/insertLog.php")
    Call<Value> stok(
            @Field("sku") String sku,
            @Field("jumlah") String jumlah,
            @Field("desc") String desc,
            @Field("tgl") String tgl
    );

    @GET("/selectLog.php")
    Call<LogValue> log();
}
