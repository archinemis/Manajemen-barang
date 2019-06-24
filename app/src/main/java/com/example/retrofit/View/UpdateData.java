package com.example.retrofit.View;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.Toast;

import com.example.retrofit.Controller.RegisterAPI;
import com.example.retrofit.Controller.getAPI;
import com.example.retrofit.Model.Value;
import com.example.retrofit.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateData extends AppCompatActivity {

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
    static String oldSku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);
        ButterKnife.bind(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Ubah Barang");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent i = getIntent();
        String sku = i.getStringExtra("sku");
        String nama = i.getStringExtra("nama");
        String stok = i.getStringExtra("stok");
        String gambar = i.getStringExtra("gambar");

        txtSku.setText(sku);
        txtNama.setText(nama);
        txtStok.setText(stok);
        txtGambar.setText(gambar);
        oldSku = sku;
    }

    @OnClick(R.id.submit) void ubah(){
        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Loading...");
        progress.show();

        String sku = txtSku.getText().toString();
        String nama = txtNama.getText().toString();
        String stok = txtStok.getText().toString();
        String gambar = txtGambar.getText().toString();

        RegisterAPI api = getAPI.getRetrofit().create(RegisterAPI.class);
        Call<Value> call = api.ubah(oldSku,sku,nama,stok,gambar);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();
                progress.dismiss();
                if (value.equals("1")){
                    finish();
                    Toast.makeText(UpdateData.this, message, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(UpdateData.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                t.printStackTrace();
                progress.dismiss();
                Toast.makeText(UpdateData.this, "Jaringan error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.delete) void hapus(){
        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Loading...");
        progress.show();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Peringatan");
        alertDialogBuilder
                .setMessage("Apakah Anda yakin ingin mengapus data ini?")
                .setCancelable(false)
                .setPositiveButton("Hapus",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        String sku = txtSku.getText().toString();
                        RegisterAPI api = getAPI.getRetrofit().create(RegisterAPI.class);
                        Call<Value> call = api.hapus(sku);
                        call.enqueue(new Callback<Value>() {
                            @Override
                            public void onResponse(Call<Value> call, Response<Value> response) {
                                String value = response.body().getValue();
                                String message = response.body().getMessage();
                                progress.dismiss();
                                if (value.equals("1")) {
                                    Toast.makeText(UpdateData.this, message, Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(UpdateData.this, message, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Value> call, Throwable t) {
                                t.printStackTrace();
                                progress.dismiss();
                                Toast.makeText(UpdateData.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("Batal",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

}
