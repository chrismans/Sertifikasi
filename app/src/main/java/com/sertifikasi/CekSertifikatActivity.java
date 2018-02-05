package com.sertifikasi;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.sertifikasi.adapter.SertifikatAdapter;
import com.sertifikasi.model.SertifikatModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CekSertifikatActivity extends AppCompatActivity {

    final int REQUEST_CODE_CAMERA = 1;

    @BindView(R.id.list_sertifikat)
    RecyclerView sertifkat_rv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.query_search)
    EditText query;
    @BindView(R.id.txt_not_found)
    TextView txtNotFound;
    @BindView(R.id.loading)
    RelativeLayout loading;

    private SertifikatAdapter adapter;
    private List<SertifikatModel> data_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cek_sertifikat);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        data_list = new ArrayList<>();
        sertifkat_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new SertifikatAdapter(this, data_list);
        sertifkat_rv.setAdapter(adapter);

        query.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    loading.setVisibility(View.VISIBLE);
                    if(query.getText().toString().matches("\\d+(?:\\.\\d+)?")){
                        getDataSertifikat("empty", query.getText().toString());
                    }else{
                        getDataSertifikat(query.getText().toString(), "empty");
                    }
                    return true;
                }
                return false;
            }
        });
    }

    private void getDataSertifikat(String param1, String param2) {
        txtNotFound.setVisibility(View.GONE);
        data_list.clear();
        AndroidNetworking.get("http://jmkp.or.id/api/sertifikat/fb27fb1d9ef87fdcac983bcad3eafd86/"+param1+"/"+param2)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        loading.setVisibility(View.GONE);
                        try {
                            JSONArray sertifikatArray = new JSONArray(response);
                            if (sertifikatArray.length() > 0){
                                for (int i = 0; i < sertifikatArray.length(); i++){
                                    JSONObject dt = sertifikatArray.getJSONObject(i);
                                    data_list.add(new SertifikatModel(dt.getInt("id"),dt.getString("nama_lengkap"),dt.getString("no_registrasi"),
                                            dt.getString("no_sertifikat"),dt.getString("no_seri"),dt.getString("tanggal_terbit"),dt.getString("tanggal_rcc"),dt.getString("skema")));
                                }
                            }else{
                                txtNotFound.setVisibility(View.VISIBLE);
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            txtNotFound.setVisibility(View.VISIBLE);
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), anError.getErrorDetail(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sertifikat_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.nav_barcode:
                scanBarcode();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void scanBarcode() {
        int cameraPermissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (cameraPermissionCheck != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                return;
            }else{
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        REQUEST_CODE_CAMERA);
            }
        }else{
            Intent intent = new Intent(CekSertifikatActivity.this, ScannerActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_CODE_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(CekSertifikatActivity.this, ScannerActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
