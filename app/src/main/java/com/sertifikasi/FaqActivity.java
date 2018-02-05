package com.sertifikasi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.sertifikasi.adapter.FaqAdapter;
import com.sertifikasi.model.FaqModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by macpro on 02/02/18.
 */

public class FaqActivity extends AppCompatActivity {

    @BindView(R.id.faq_rv)
    RecyclerView faqRV;
    FaqAdapter adapter;
    List<FaqModel> data_list;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        ButterKnife.bind(this);

        data_list = new ArrayList<>();
        faqRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        faqRV.setHasFixedSize(true);
        adapter = new FaqAdapter(this, data_list);
        faqRV.setAdapter(adapter);
        getDataFaq();
    }

    private void getDataFaq() {
        data_list.clear();
        AndroidNetworking.get("https://jmkp.or.id/api/faq/fb27fb1d9ef87fdcac983bcad3eafd86")
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Parsing Data JSON Array Disini
                        for (int i = 0; i < response.length(); i++){
                            try {
                                JSONObject item = response.getJSONObject(i);
                                data_list.add(new FaqModel(item.getString("pertanyaan"), item.getString("jawaban")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}