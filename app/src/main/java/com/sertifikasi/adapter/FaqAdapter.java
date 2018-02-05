package com.sertifikasi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sertifikasi.R;
import com.sertifikasi.model.FaqModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by macpro on 05/02/18.
 */

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.MyViewHolder> {

    //init private constructor
    private Context context;
    private List<FaqModel> data_list;

    //Buat constructor
    public FaqAdapter(Context context, List<FaqModel> data_list) {
        this.context = context;
        this.data_list = data_list;
    }
    @Override
    public FaqAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_faq, parent, false);
        return new MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(FaqAdapter.MyViewHolder holder, int position) {
        FaqModel item = data_list.get(position);
        holder.pertanyaan.setText(item.getPertanyaan());
    }
    @Override
    public int getItemCount() {
        return data_list.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.pertanyaan)
        TextView pertanyaan;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}