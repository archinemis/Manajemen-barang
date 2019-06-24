package com.example.retrofit.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.retrofit.R;
import com.example.retrofit.Model.Result;
import com.example.retrofit.View.UpdateData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Result> result;

    public RecyclerViewAdapter(Context context, List<Result> result) {
        this.context = context;
        this.result = result;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, parent, false);
        ViewHolder holder = new ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Result results = result.get(position);
        holder.txtsku.setText(results.getSku());
        holder.txtnama.setText(results.getNama());
        holder.txtstok.setText(results.getStok());
        holder.txtgambar.setText(results.getGambar());
    }

    @Override
    public int getItemCount(){
        return result.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.txtSKU)
        TextView txtsku;
        @BindView(R.id.txtNama)
        TextView txtnama;
        @BindView(R.id.txtStok)
        TextView txtstok;
        @BindView(R.id.txtGambar)
        TextView txtgambar;

        public ViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            String sku = txtsku.getText().toString();
            String nama = txtnama.getText().toString();
            String stok = txtstok.getText().toString();
            String gambar = txtgambar.getText().toString();

            Intent i = new Intent(context, UpdateData.class);
            i.putExtra("sku",sku);
            i.putExtra("nama",nama);
            i.putExtra("stok",stok);
            i.putExtra("gambar",gambar);
            context.startActivity(i);
        }
    }
}
