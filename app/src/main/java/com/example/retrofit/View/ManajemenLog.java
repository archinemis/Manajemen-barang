package com.example.retrofit.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.retrofit.Adapter.AdapterLog;
import com.example.retrofit.Adapter.AdapterStok;
import com.example.retrofit.Controller.RegisterAPI;
import com.example.retrofit.Controller.getAPI;
import com.example.retrofit.Model.LogBarang;
import com.example.retrofit.Model.LogValue;
import com.example.retrofit.Model.Result;
import com.example.retrofit.Model.Value;
import com.example.retrofit.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ManajemenLog extends AppCompatActivity {

    private Intent mIntent;
    private Toolbar mToolbar;
    public static final String url = "https://denandra.000webhostapp.com/";
    private List<LogBarang> results = new ArrayList<>();
    private AdapterLog viewAdapter;

    @BindView(R.id.recyclerView)
    RecyclerView mRecycleerView;
    @BindView(R.id.parogress_bar)
    ProgressBar mprogressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manajemen_log);
        ButterKnife.bind(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Log Barang");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.ic_kembali);

        viewAdapter = new AdapterLog(this, results);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecycleerView.setItemAnimator(new DefaultItemAnimator());
        mRecycleerView.setLayoutManager(mLayoutManager);
        mRecycleerView.setAdapter(viewAdapter);

        loadDataBarang();
    }

    private void loadDataBarang(){
        RegisterAPI api = getAPI.getRetrofit().create(RegisterAPI.class);
        Call<LogValue> call = api.log();
        call.enqueue(new Callback<LogValue>() {
            @Override
            public void onResponse(Call<LogValue> call, Response<LogValue> response) {
                String value = response.body().getValue();
                if (value.equals("1")){
                    mprogressBar.setVisibility(View.GONE);
                    results = response.body().getResult();
                    viewAdapter = new AdapterLog(ManajemenLog.this, results);
                    mRecycleerView.setAdapter(viewAdapter);
                }
            }

            @Override
            public void onFailure(Call<LogValue> call, Throwable t) {
//                progressBar.setVisibility(View.GONE);
                Toast.makeText(ManajemenLog.this,"Gagal",Toast.LENGTH_SHORT).show();
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
