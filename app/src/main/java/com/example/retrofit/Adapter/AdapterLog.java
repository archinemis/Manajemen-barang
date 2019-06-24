package com.example.retrofit.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.retrofit.Model.LogBarang;
import com.example.retrofit.Model.Result;
import com.example.retrofit.R;
import com.example.retrofit.View.UpdateStok;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterLog extends RecyclerView.Adapter<AdapterLog.ViewHolder> {

    private Context context;
    private List<LogBarang> result;

    public AdapterLog(Context context, List<LogBarang> result) {
        this.context = context;
        this.result = result;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_log,parent,false);
        AdapterLog.ViewHolder holder = new AdapterLog.ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(AdapterLog.ViewHolder holder, int position){
        LogBarang results = result.get(position);
        holder.txtsku.setText(results.getSku());
        holder.txtnama.setText(results.getNama());
        if (results.getDesc().equals("Dikurangi")){
            holder.stok.setTextColor(Color.parseColor("#FFEC407A"));
            holder.stok.setText("- "+results.getJumlah());
        }else{
            holder.stok.setTextColor(Color.parseColor("#FF26A69A"));
            holder.stok.setText("+ "+results.getJumlah());
        }
        holder.txttgl.setText(results.getDesc()+" pada : "+results.getTgl());
    }

    @Override
    public int getItemCount(){
        return result.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.txtSKU)
        TextView txtsku;
        @BindView(R.id.txtNama)
        TextView txtnama;
        @BindView(R.id.txtTgl)
        TextView txttgl;
        @BindView(R.id.stok)
        TextView stok;

        public ViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
