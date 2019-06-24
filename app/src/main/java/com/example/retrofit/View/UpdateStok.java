package com.example.retrofit.View;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.retrofit.Controller.RegisterAPI;
import com.example.retrofit.Controller.getAPI;
import com.example.retrofit.Model.Value;
import com.example.retrofit.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateStok extends AppCompatActivity {

    @BindView(R.id.nama)
    EditText txtNama;
    @BindView(R.id.sku)
    EditText txtSku;
    @BindView(R.id.tanggal)
    EditText txtTanggal;
    @BindView(R.id.jumlah)
    EditText txtJml;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private ProgressDialog progress;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_stok);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Daftar Stok");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent i = getIntent();
        String sku = i.getStringExtra("sku");
        String nama = i.getStringExtra("nama");

        String sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(new Date());

        txtNama.setText(nama);
        txtSku.setText(sku);
        txtTanggal.setText(sdf);
    }

    @OnClick(R.id.tanggal) void tanggal(){
        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year,month,dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
                txtTanggal.setText(sdf.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR),newCalendar.get(Calendar.MONTH),newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @OnClick(R.id.kurangi) void kurang(){
        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Loading ...");
        progress.show();

        //mengambil data dari edittext
        String sku = txtSku.getText().toString();
        String jumlah = txtJml.getText().toString();
        String desc = "Mengurangi";
        String tgl = txtTanggal.getText().toString();

        RegisterAPI api = getAPI.getRetrofit().create(RegisterAPI.class);
        Call<Value> call = api.stok(sku, jumlah, desc, tgl);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();
                progress.dismiss();
                if (value.equals("1")) {
                    finish();
                    Toast.makeText(UpdateStok.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UpdateStok.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(UpdateStok.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.tambah) void menambah(){
//        Toast.makeText(this, "Oh", Toast.LENGTH_SHORT).show();
        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Loading ...");
        progress.show();

        //mengambil data dari edittext
        String sku = txtSku.getText().toString();
        String jumlah = txtJml.getText().toString();
        String desc = "Menambah";
        String tgl = txtTanggal.getText().toString();

        RegisterAPI api = getAPI.getRetrofit().create(RegisterAPI.class);
        Call<Value> call = api.stok(sku, jumlah, desc, tgl);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();
                progress.dismiss();
                if (value.equals("1")) {
                    finish();
                    Toast.makeText(UpdateStok.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UpdateStok.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(UpdateStok.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
