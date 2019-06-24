package com.example.retrofit.View;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.retrofit.R;
import com.example.retrofit.Adapter.RecyclerViewAdapter;
import com.example.retrofit.Controller.RegisterAPI;
import com.example.retrofit.Model.Result;
import com.example.retrofit.Model.Value;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ManajemenBarang extends AppCompatActivity {

    private FloatingActionButton mfab;
    private Intent mIntent;
    private Toolbar mToolbar;
    public static final String url = "https://denandra.000webhostapp.com/";
    private List<Result> results = new ArrayList<>();
    private RecyclerViewAdapter viewAdapter;

    @BindView(R.id.recyclerView)
    RecyclerView mRecycleerView;
    @BindView(R.id.parogress_bar)
    ProgressBar mprogressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manajemen_barang);
        ButterKnife.bind(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Daftar Barang");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.ic_kembali);

        mfab = findViewById(R.id.floating_tambah);
        mfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent = new Intent(ManajemenBarang.this, TambahData.class);
                startActivity(mIntent);
            }
        });

        viewAdapter = new RecyclerViewAdapter(this, results);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecycleerView.setItemAnimator(new DefaultItemAnimator());
        mRecycleerView.setLayoutManager(mLayoutManager);
        mRecycleerView.setAdapter(viewAdapter);

        loadDataBarang();
    }

    private void loadDataBarang(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RegisterAPI api = retrofit.create(RegisterAPI.class);
        Call<Value> call = api.view();
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                String value = response.body().getValue();
                if (value.equals("1")){
                    mprogressBar.setVisibility(View.GONE);
                    results = response.body().getResult();
                    viewAdapter = new RecyclerViewAdapter(ManajemenBarang.this, results);
                    mRecycleerView.setAdapter(viewAdapter);
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
//                progressBar.setVisibility(View.GONE);
                Toast.makeText(ManajemenBarang.this,"Gagal",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDataBarang();
    }

    @Override
    public void onRestart()
    {
        super.onRestart();
        loadDataBarang();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
