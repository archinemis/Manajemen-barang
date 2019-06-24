package com.example.retrofit.View;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.retrofit.Controller.RegisterAPI;
import com.example.retrofit.Controller.getAPI;
import com.example.retrofit.Model.Value;
import com.example.retrofit.R;

import butterknife.BindView;
import butterknife.ButterKnife;
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

    private ProgressDialog progress;

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
