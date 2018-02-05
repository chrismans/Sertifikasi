package com.sertifikasi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sertifikasi.DetailSertifikatActivity;
import com.sertifikasi.R;
import com.sertifikasi.model.SertifikatModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pandu on 1/20/2018.
 */

public class SertifikatAdapter extends RecyclerView.Adapter<SertifikatAdapter.MyViewHolder> {

    private Context context;
    private List<SertifikatModel> data_list;

    public SertifikatAdapter(Context context, List<SertifikatModel> data_list){
        this.context = context;
        this.data_list = data_list;
    }

    @Override
    public SertifikatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View  v = LayoutInflater.from(context).inflate(R.layout.item_sertifikat, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SertifikatAdapter.MyViewHolder holder, int position) {
        final SertifikatModel item = data_list.get(position);

        SimpleDateFormat dateparser = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        holder.nama.setText(item.getNama_lengkap());
        holder.noReg.setText("NO REG : "+item.getNo_registrasi());
        holder.noSertifikat.setText("NO SERTIFIKAT : "+item.getNo_sertifikat());
        holder.skema.setText("SKEMA : "+item.getSkema());
        if (item.getTanggal_terbit().equals("null")){
            holder.tglTerbit.setText("TGL TERBIT : -");
        }else{
            try {
                Date date = dateparser.parse(item.getTanggal_terbit());
                holder.tglTerbit.setText("TGL TERBIT : "+dateFormat.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (item.getTanggal_rcc().equals("null")){
            holder.tglRcc.setText("TGL RCC : -");
        }else{
            try {
                Date date = dateparser.parse(item.getTanggal_rcc());
                holder.tglRcc.setText("TGL RCC : "+dateFormat.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        holder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailSertifikatActivity.class);
                intent.putExtra("id",item.getId()+"");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data_list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_nama)
        TextView nama;
        @BindView(R.id.txt_no_sertifikat)
        TextView noSertifikat;
        @BindView(R.id.txt_no_reg)
        TextView noReg;
        @BindView(R.id.txt_tgl_terbit)
        TextView tglTerbit;
        @BindView(R.id.txt_tgl_rcc)
        TextView tglRcc;
        @BindView(R.id.txt_skema)
        TextView skema;
        @BindView(R.id.btn_detail)
        Button btnDetail;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
