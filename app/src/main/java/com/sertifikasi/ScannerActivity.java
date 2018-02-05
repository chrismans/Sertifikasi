package com.sertifikasi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.zxing.Result;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONException;
import org.json.JSONObject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerActivity extends Activity implements ZXingScannerView.ResultHandler {

    ZXingScannerView scannerView;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);

        loading = new ProgressDialog(this);
        loading.setIndeterminate(true);
        loading.setCancelable(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        String textBarcode = result.getText();
        String id  = textBarcode.replaceAll("[^0-9]", "");
        loading.setMessage("Validasi Barcode...");
        loading.show();
        validasiBarcode(id);
    }

    private void validasiBarcode(String id) {
        AndroidNetworking.get("http://jmkp.or.id/api/sertifikat_detail/fb27fb1d9ef87fdcac983bcad3eafd86/"+id)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Intent intent = new Intent(ScannerActivity.this, DetailSertifikatActivity.class);
                            intent.putExtra("id", response.getString("id"));
                            startActivity(intent);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            loading.hide();
                            showToast("BARCODE TIDAK VALID", MDToast.TYPE_ERROR);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    scannerView.resumeCameraPreview(ScannerActivity.this);
                                }
                            }, 5000);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.hide();
                        showToast("BARCODE TIDAK VALID", MDToast.TYPE_ERROR);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                scannerView.resumeCameraPreview(ScannerActivity.this);
                            }
                        }, 5000);
                    }
                });
    }

    private void showToast(String msg, int type) {
        MDToast mdToast = MDToast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG, type);
        mdToast.show();
    }
}
