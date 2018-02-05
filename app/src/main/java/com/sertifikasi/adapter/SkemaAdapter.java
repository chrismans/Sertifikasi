package com.sertifikasi.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLayout;
import com.sertifikasi.PendaftaranActivity;
import com.sertifikasi.R;
import com.sertifikasi.model.SkemaModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pandu on 1/21/2018.
 */

public class SkemaAdapter extends RecyclerView.Adapter<SkemaAdapter.MyViewHolder> {

    private Context context;
    private List<SkemaModel> data_list;

    public SkemaAdapter(Context context, List<SkemaModel> data_list){
        this.context = context;
        this.data_list = data_list;
    }

    @Override
    public SkemaAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_skema, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final SkemaAdapter.MyViewHolder holder, int position) {
        final SkemaModel item = data_list.get(position);
        holder.skemaSertifikasi.setText((position+1)+". "+item.getSkema_sertifikasi());
        holder.deskripsi.setText(item.getDeskripsi_skema());
        holder.detailSkema.collapse();
        holder.skemaSertifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.detailSkema.toggle();
            }
        });
        holder.btnPilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((PendaftaranActivity) context).getAsesmenMandiri(item.getId()+"");
                ((PendaftaranActivity) context).nextStep(1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data_list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.skema_sertifikasi)
        TextView skemaSertifikasi;
        @BindView(R.id.deskripsi)
        TextView deskripsi;
        @BindView(R.id.detail_skema)
        ExpandableLayout detailSkema;
        @BindView(R.id.btn_pilih)
        Button btnPilih;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
