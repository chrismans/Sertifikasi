package com.sertifikasi;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.slider)
    BannerSlider slider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setTitle("JMKP");
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.ic_drawer);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });

        navigationView.setNavigationItemSelectedListener(this);

        List<Banner> banners=new ArrayList<>();
        banners.add(new RemoteBanner("http://jmkp.or.id/assets/images/slide1.png"));
        banners.add(new RemoteBanner("http://jmkp.or.id/assets/images/slide2.png"));
        banners.add(new RemoteBanner("http://jmkp.or.id/assets/images/slide3.png"));
        banners.add(new RemoteBanner("http://jmkp.or.id/assets/images/slide4.png"));
        slider.setBanners(banners);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.nav_login:
                Intent intentLogin = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intentLogin);
                break;
            case R.id.nav_cek_sertifikasi:
                Intent intentCekSertifikat = new Intent(HomeActivity.this, CekSertifikatActivity.class);
                startActivity(intentCekSertifikat);
                break;
            case R.id.nav_pendaftaran:
                Intent intentPendaftaran = new Intent(HomeActivity.this, PendaftaranActivity.class);
                startActivity(intentPendaftaran);
                break;
            case R.id.nav_bantuan:
                Intent intentBantuan = new Intent(HomeActivity.this, FaqActivity.class);
                startActivity(intentBantuan);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
