package com.sertifikasi;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.sertifikasi.adapter.DokumenLainAdapter;
import com.sertifikasi.adapter.SkemaAdapter;
import com.sertifikasi.model.DokumenLainModel;
import com.sertifikasi.model.SkemaModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PendaftaranActivity extends AppCompatActivity {

    final int REQUEST_CODE_CAMERA = 100;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabLayout;

    @BindView(R.id.skema_rv)
    RecyclerView skemaRV;
    List<SkemaModel> data_list;
    SkemaAdapter skemaAdapter;

    @BindView(R.id.step1)
    LinearLayout step1;
    @BindView(R.id.step2)
    LinearLayout step2;
    @BindView(R.id.step3)
    LinearLayout step3;
    @BindView(R.id.step4)
    LinearLayout step4;

    @BindView(R.id.dokumenlain_rv)
    RecyclerView dokumenLainRv;
    List<DokumenLainModel> dokumenlain_list;
    DokumenLainAdapter dokumenLainAdapter;

    @BindView(R.id.pendaftar)
    MaterialSpinner spinnerPendaftar;
    @BindView(R.id.jenis_kelamin)
    MaterialSpinner spinnerJenisKelamin;
    @BindView(R.id.kewarganegaraan)
    MaterialSpinner spinnerKewarganegara;
    @BindView(R.id.btn_lokasi)
    Button btnLokasi;
    @BindView(R.id.btn_to_step3)
    Button btnToStep3;
    @BindView(R.id.img_foto)
    ImageView imgFoto;
    @BindView(R.id.img_ktp)
    ImageView imgKtp;
    @BindView(R.id.img_cv)
    ImageView imgCv;
    @BindView(R.id.img_pendukung)
    ImageView imgPendukung;

    @BindView(R.id.btn_tambah_dokumen)
    Button btnTambahDokumen;

    String pasFotoName = "";
    String identitasName = "";
    String cvName = "";
    String buktiPendukungName = "";


    SpinnerDialog lokasiDialog;
    private ArrayList<String> idLokasi = new ArrayList<>();
    private ArrayList<String> dataLokasi = new ArrayList<>();

    private int dokLainPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendaftaran);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Pendaftaran");
        tabLayout.addTab(tabLayout.newTab().setText("Step 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Step 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Step 3"));
        tabLayout.addTab(tabLayout.newTab().setText("Step 4"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                step1.setVisibility(View.GONE);
                step2.setVisibility(View.GONE);
                step3.setVisibility(View.GONE);
                step4.setVisibility(View.GONE);

                switch (tab.getPosition()){
                    case 0:
                        step1.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        step2.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        step3.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        step4.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        data_list = new ArrayList<>();
        skemaAdapter = new SkemaAdapter(this, data_list);
        skemaRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        skemaRV.setHasFixedSize(true);
        skemaRV.setAdapter(skemaAdapter);
        getDataSkema();

        dokumenlain_list = new ArrayList<>();
        dokumenLainAdapter = new DokumenLainAdapter(this, dokumenlain_list);
        dokumenLainRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        dokumenLainRv.setHasFixedSize(true);
        dokumenLainRv.setAdapter(dokumenLainAdapter);
        btnTambahDokumen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dokumenlain_list.add(new DokumenLainModel("", null));
                dokumenLainAdapter.notifyDataSetChanged();
            }
        });

        List<String> pendaftarItems = new ArrayList<>();
        pendaftarItems.add("== Pendaftar ==");
        pendaftarItems.add("Perorangan");
        pendaftarItems.add("Organisasi");
        pendaftarItems.add("Jejaring");
        spinnerPendaftar.setItems(pendaftarItems);

        List<String> jkItems = new ArrayList<>();
        jkItems.add("== Jenis Kelamin ==");
        jkItems.add("Laki-Laki");
        jkItems.add("Perempuan");
        spinnerJenisKelamin.setItems(jkItems);

        List<String> kewargaItems = new ArrayList<>();
        kewargaItems.add("== Kewarganegaraan ==");
        kewargaItems.add("WNI");
        kewargaItems.add("WNA");
        spinnerKewarganegara.setItems(kewargaItems);

        setupLokasiDialog();
        btnLokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lokasiDialog.showSpinerDialog();
            }
        });

        btnToStep3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nextStep(2);
            }
        });

        imgFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage(20);
            }
        });
        imgKtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage(30);
            }
        });
        imgCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage(40);
            }
        });
        imgPendukung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage(50);
            }
        });
    }

    private void pickImage(int request_code) {
        ImagePicker.create(this)
                .folderTitle("Pilih Dokumen")
                .single()
                .folderMode(true)
                .showCamera(true)
                .theme(R.style.ImagePickerTheme)
                .start(request_code);
    }

    public void pickImageOther(int request_code, int position) {
        dokLainPosition = position;
        ImagePicker.create(this)
                .folderTitle("Pilih Dokumen")
                .single()
                .folderMode(true)
                .showCamera(true)
                .theme(R.style.ImagePickerTheme)
                .start(request_code);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 20:
                if (resultCode == RESULT_OK){
                    ArrayList<Image> images = (ArrayList<Image>) ImagePicker.getImages(data);
                    if (images == null) return;
                    File file = new File(images.get(0).getPath());
                    Picasso.with(imgFoto.getContext())
                            .load(file)
                            .placeholder(R.drawable.placeholder)
                            .error(R.drawable.placeholder)
                            .into(imgFoto);
                    uploadFile(file, requestCode);
                }
                break;
            case 30:
                if (resultCode == RESULT_OK){
                    ArrayList<Image> images = (ArrayList<Image>) ImagePicker.getImages(data);
                    if (images == null) return;
                    File file = new File(images.get(0).getPath());
                    Picasso.with(imgKtp.getContext())
                            .load(file)
                            .placeholder(R.drawable.placeholder)
                            .error(R.drawable.placeholder)
                            .into(imgKtp);
                }
                break;
            case 40:
                if (resultCode == RESULT_OK){
                    ArrayList<Image> images = (ArrayList<Image>) ImagePicker.getImages(data);
                    if (images == null) return;
                    File file = new File(images.get(0).getPath());
                    Picasso.with(imgCv.getContext())
                            .load(file)
                            .placeholder(R.drawable.placeholder)
                            .error(R.drawable.placeholder)
                            .into(imgCv);
                }
                break;
            case 50:
                if (resultCode == RESULT_OK){
                    ArrayList<Image> images = (ArrayList<Image>) ImagePicker.getImages(data);
                    if (images == null) return;
                    File file = new File(images.get(0).getPath());
                    Picasso.with(imgPendukung.getContext())
                            .load(file)
                            .placeholder(R.drawable.placeholder)
                            .error(R.drawable.placeholder)
                            .into(imgPendukung);
                }
                break;
            case 60:
                if (resultCode == RESULT_OK){
                    ArrayList<Image> images = (ArrayList<Image>) ImagePicker.getImages(data);
                    if (images == null) return;
                    File file = new File(images.get(0).getPath());
                    dokumenlain_list.get(dokLainPosition).setFile_image(file);
                    dokumenLainAdapter.notifyDataSetChanged();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadFile(File file, final int string) {
        final ProgressDialog loading = new ProgressDialog(this);
        loading.setMessage("Uploading...");
        loading.setIndeterminate(true);
        loading.setCancelable(false);
        loading.show();
        AndroidNetworking.upload("http://jmkp.or.id/api/upload_file/fb27fb1d9ef87fdcac983bcad3eafd86")
                .addMultipartFile("filename", file)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.hide();
                        try {
                            switch (string){
                                case 20:
                                    pasFotoName = response.getString("file_nsme");
                                    break;
                                case 30:
                                    identitasName = response.getString("file_nsme");
                                    break;
                                case 40:
                                    cvName = response.getString("file_nsme");
                                    break;
                                case 50:
                                    buktiPendukungName = response.getString("file_nsme");
                                    break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    private void setupLokasiDialog() {
        AndroidNetworking.get("http://jmkp.or.id/api/provinsi/fb27fb1d9ef87fdcac983bcad3eafd86")
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray lokasiArray = new JSONArray(response);
                            for (int i = 0; i < lokasiArray.length(); i++){
                                JSONObject lokasiObject = lokasiArray.getJSONObject(i);
                                idLokasi.add(lokasiObject.getString("id"));
                                dataLokasi.add(lokasiObject.getString("nama_provinsi"));
                            }
                            lokasiDialog = new SpinnerDialog(PendaftaranActivity.this, dataLokasi, "Pilih Lokasi", "Tutup");
                            lokasiDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                                @Override
                                public void onClick(String s, int i) {
                                    btnLokasi.setText(s);
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    public void nextStep(int index){
        TabLayout.Tab tab = tabLayout.getTabAt(index);
        tab.select();
    }

    private void getDataSkema() {
        data_list.clear();
        AndroidNetworking.get("http://jmkp.or.id/api/skema/fb27fb1d9ef87fdcac983bcad3eafd86")
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray skemaArray = new JSONArray(response);
                            for (int i = 0; i < skemaArray.length(); i++){
                                JSONObject skema = skemaArray.getJSONObject(i);
                                data_list.add(new SkemaModel(skema.getInt("id"),skema.getString("kode_skema"),skema.getString("skema_sertifikasi"),
                                        skema.getString("kategori_skema"),skema.getString("biaya_skema"),skema.getString("link_download_dokumen"),skema.getString("deskripsi_skema")));
                            }
                            skemaAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    public void getAsesmenMandiri(String id){
        AndroidNetworking.get("http://jmkp.or.id/api/asesmen_mandiri/fb27fb1d9ef87fdcac983bcad3eafd86/"+id)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        try {
                            JSONArray responseArray = new JSONArray(response);
                            for (int i = 0; i < responseArray.length(); i++){
                                JSONObject dt = responseArray.getJSONObject(i);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
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
