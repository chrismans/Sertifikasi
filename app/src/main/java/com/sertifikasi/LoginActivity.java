package com.sertifikasi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.klinker.android.link_builder.Link;
import com.klinker.android.link_builder.LinkBuilder;
import com.valdesekamdem.library.mdtoast.MDToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.input_username)
    EditText username;
    @BindView(R.id.input_password)
    EditText password;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.loading)
    ProgressBar loading;
    @BindView(R.id.txt_no_akun)
    TextView txtNoAkun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Login");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLogin.setVisibility(View.GONE);
                loading.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnLogin.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);
                        if (username.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
                            showToast("Username dan Password tidak boleh kosong", MDToast.TYPE_ERROR);
                        }else if(!username.getText().toString().equals("pandu")){
                            showToast("Username dan Password salah", MDToast.TYPE_ERROR);
                        }
                    }
                }, 5000);
            }
        });

        Link link = new Link("Daftar Disini")
                .setTextColor(Color.parseColor("#1D94D3"))
                .setTextColorOfHighlightedLink(Color.parseColor("#1D94D3"))
                .setHighlightAlpha(.4f)
                .setUnderlined(false)
                .setBold(true)
                .setOnLongClickListener(new Link.OnLongClickListener() {
                    @Override
                    public void onLongClick(String clickedText) {

                    }
                })
                .setOnClickListener(new Link.OnClickListener() {
                    @Override
                    public void onClick(String clickedText) {
                        Intent intent = new Intent(LoginActivity.this, PendaftaranActivity.class);
                        startActivity(intent);
                    }
                });

        LinkBuilder.on(txtNoAkun)
                .addLink(link)
                .build();
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
