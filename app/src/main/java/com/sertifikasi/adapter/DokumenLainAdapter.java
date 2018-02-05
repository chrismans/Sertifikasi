package com.sertifikasi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.sertifikasi.PendaftaranActivity;
import com.sertifikasi.R;
import com.sertifikasi.model.DokumenLainModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pandu on 1/22/2018.
 */

public class DokumenLainAdapter extends RecyclerView.Adapter<DokumenLainAdapter.MyViewHolder> {

    private Context context;
    private List<DokumenLainModel> data_list;

    public DokumenLainAdapter(Context context, List<DokumenLainModel> data_list){
        this.context = context;
        this.data_list = data_list;
    }


    @Override
    public DokumenLainAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_dokumen_lain, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final DokumenLainAdapter.MyViewHolder holder, final int position) {
        final DokumenLainModel item = data_list.get(position);

        final List<String> dokumenValueItems = new ArrayList<>();
        dokumenValueItems.add("== Nama Dokumen ==");
        dokumenValueItems.add("foro");
        dokumenValueItems.add("ktp");
        dokumenValueItems.add("ijazah");
        dokumenValueItems.add("skkd");
        dokumenValueItems.add("cp");
        dokumenValueItems.add("surat_pelatihan");
        dokumenValueItems.add("surat_referensi");
        dokumenValueItems.add("job_description");
        dokumenValueItems.add("demonstrasi_pekerjaan");
        dokumenValueItems.add("ws");
        dokumenValueItems.add("pengalaman_industri");
        dokumenValueItems.add("bukti_relevan");
        dokumenValueItems.add("sertifikat_expired");

        List<String> dokumenTitileItems = new ArrayList<>();
        dokumenTitileItems.add("== Nama Dokumen ==");
        dokumenTitileItems.add("Foto");
        dokumenTitileItems.add("KTP");
        dokumenTitileItems.add("Ijazah");
        dokumenTitileItems.add("Surat Keterangan Keaslian Dok");
        dokumenTitileItems.add("Contoh / Report Pekerjaan (CP)");
        dokumenTitileItems.add("Sertifikat Pelatihan (SK)");
        dokumenTitileItems.add("Surat Referensi dari Perusahaan ");
        dokumenTitileItems.add("Job Description (JD)");
        dokumenTitileItems.add("Demonstrasi Pekerjaan (De)");
        dokumenTitileItems.add("Wawancara dg. Supervisor, teman atau klien");
        dokumenTitileItems.add("Pengalaman Industri (Pe)");
        dokumenTitileItems.add("Bukti-Bukti Lain yang Masih Relevan / CV");
        dokumenTitileItems.add("Sertifikat Expired");
        holder.nmDokumen.setItems(dokumenTitileItems);
        holder.nmDokumen.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int pos, long id, Object itemDok) {
                data_list.set(position, new DokumenLainModel(dokumenValueItems.get(pos), item.getFile_image()));
                notifyDataSetChanged();
            }
        });

        if (item.getFile_image() != null){
            Picasso.with(holder.imagePreview.getContext())
                    .load(item.getFile_image())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(holder.imagePreview);
        }
        holder.imagePreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((PendaftaranActivity) context).pickImageOther(60, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data_list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.nama_dokumen)
        MaterialSpinner nmDokumen;
        @BindView(R.id.img_preview)
        ImageView imagePreview;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
