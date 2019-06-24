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
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.manajemenBarang) void menejemenBarang(){
        Intent i = new Intent(MainActivity.this,ManajemenBarang.class);
        startActivity(i);
    }

    @OnClick(R.id.manajemenStok) void manajemenStok(){
        Intent i = new Intent(MainActivity.this,ManajemenStok.class);
        startActivity(i);
    }

    @OnClick(R.id.logStok) void manajemenLog(){
        Intent i = new Intent(MainActivity.this,ManajemenLog.class);
        startActivity(i);
    }


}
