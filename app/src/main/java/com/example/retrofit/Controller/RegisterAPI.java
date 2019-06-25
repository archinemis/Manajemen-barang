package com.example.retrofit.Controller;

import com.example.retrofit.Model.LogBarang;
import com.example.retrofit.Model.LogValue;
import com.example.retrofit.Model.UploadImage;
import com.example.retrofit.Model.Value;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RegisterAPI {
    @FormUrlEncoded
    @POST("/insertBarang.php")
    Call<Value> barang(
            @Field("sku") String sku,
            @Field("nama") String nama,
            @Field("stok") String stok
    );

    @GET("/selectBarang.php")
    Call<Value> view();

    @FormUrlEncoded
    @POST("/updateBarang.php")
    Call<Value> ubah(
            @Field("oldsku") String oldsku,
            @Field("sku") String sku,
            @Field("nama") String nama,
            @Field("stok") String stok
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

    @Multipart
    @POST("/UploadImage.php")
    Call<ResponseBody> upload(@Part("image\"; fileName=\"myFile.jpg\" ") RequestBody file);
}
