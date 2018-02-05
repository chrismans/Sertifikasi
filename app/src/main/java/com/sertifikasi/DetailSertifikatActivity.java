package com.sertifikasi;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DetailSertifikatActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.foto)
    CircleImageView foto;
    @BindView(R.id.nama)
    TextView nama;
    @BindView(R.id.no_sertifikat)
    TextView noSertifikat;
    @BindView(R.id.no_registrasi)
    TextView noRegistrasi;
    @BindView(R.id.no_seri)
    TextView noSeri;
    @BindView(R.id.tgl_terbit)
    TextView tglTerbit;
    @BindView(R.id.tgl_rcc)
    TextView tglRcc;
    @BindView(R.id.no_uji)
    TextView noUji;
    @BindView(R.id.skema)
    TextView skema;
    @BindView(R.id.tempat_uji)
    TextView tempatUji;
    @BindView(R.id.nama_asesor)
    TextView namaAsesor;
    @BindView(R.id.tgl_daftar)
    TextView tglDaftar;
    @BindView(R.id.tgl_pra_asesmen)
    TextView tglPraAsesmen;
    @BindView(R.id.tanggal_uji)
    TextView tglUji;
    @BindView(R.id.rekomendasi_asesor)
    TextView rekomendasi;
    @BindView(R.id.tgl_terbit_sertifikat)
    TextView tglTerbitSertifikat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sertifikat);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setTitle("Detail Sertifikat");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getDetailSertifikat(getIntent().getExtras().getString("id"));
    }

    private void getDetailSertifikat(String id) {
        AndroidNetworking.get("http://jmkp.or.id/api/sertifikat_detail/fb27fb1d9ef87fdcac983bcad3eafd86/"+id)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Picasso.with(foto.getContext())
                                    .load(response.getString("foto_user"))
                                    .error(R.drawable.avatar)
                                    .placeholder(R.drawable.avatar)
                                    .into(foto);
                            nama.setText(response.getString("nama_lengkap"));
                            noSertifikat.setText(response.getString("no_sertifikat"));
                            noRegistrasi.setText(response.getString("no_registrasi"));
                            noSeri.setText(response.getString("no_seri"));
                            tglTerbit.setText(response.getString("tanggal_terbit"));
                            tglRcc.setText(response.getString("tanggal_rcc"));
                            noUji.setText(response.getString("no_uji_kompetensi"));
                            skema.setText(response.getString("skema_sertifikasi"));
                            tempatUji.setText(response.getString("tempat_uji_kompetensi"));
                            namaAsesor.setText(response.getString("nama_asesor"));
                            tglDaftar.setText(response.getString("tanggal_pendaftaran"));
                            tglPraAsesmen.setText(response.getString("tanggal_pra_asesmen"));
                            tglUji.setText(response.getString("tanggal_uji_kompetensi"));
                            rekomendasi.setText(response.getString("rekomendasi_asesor"));
                            tglTerbitSertifikat.setText(response.getString("tanggal_penerbitan_sertifikat"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        showToast(anError.getErrorDetail(), MDToast.TYPE_ERROR);
                    }
                });
    }

    private void showToast(String msg, int type) {
        MDToast mdToast = MDToast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG, type);
        mdToast.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
