package com.example.retrofit.View;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.nfc.Tag;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.retrofit.Controller.RegisterAPI;
import com.example.retrofit.Controller.getAPI;
import com.example.retrofit.Model.Value;
import com.example.retrofit.R;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TambahData extends AppCompatActivity {

    public static final String URL = "https://denandra.000webhostapp.com/";

    @BindView(R.id.sku)
    EditText txtSku;
    @BindView(R.id.nama)
    EditText txtNama;
    @BindView(R.id.stok)
    EditText txtStok;
    @BindView(R.id.gambar)
    EditText txtGambar;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.imgGambar)
    ImageView mGambar;

    private ProgressDialog progress;
    private static final int REQUEST_GALLERY_CODE = 1;
    String imgDecodableString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Tambah Barang");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.ic_kembali);
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    //intent ke gallery
    @OnClick(R.id.tambahGambar) void tambah(){
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i,REQUEST_GALLERY_CODE);
    }

    //menambah gambar ke imgView
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        try{
            if (requestCode == REQUEST_GALLERY_CODE && resultCode == RESULT_OK && null != data){
                Uri selectedImg = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImg,filePath,null,null,null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePath[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                mGambar.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));

            }else{
                Toast.makeText(this, "Tidak ada gambar dipilih", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e){
            Toast.makeText(this, "Error! ", Toast.LENGTH_SHORT).show();
        }

    }

    //proses insert
    public void insert(View view) {
        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Loading ...");
        progress.show();

        //mengambil data dari edittext
        String sku = txtSku.getText().toString();
        String nama = txtNama.getText().toString();
        String stok = txtStok.getText().toString();
        String gambar = txtGambar.getText().toString();

        RegisterAPI api = getAPI.getRetrofit().create(RegisterAPI.class);
        Call<Value> call = api.barang(sku, nama, stok, gambar);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();
                progress.dismiss();
                finish();
                if (value.equals("1")) {
//                    finish();
                    Toast.makeText(TambahData.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TambahData.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(TambahData.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
