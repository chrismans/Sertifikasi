package com.sertifikasi;

import android.app.Application;
import android.support.v7.widget.AppCompatCheckBox;

import com.androidnetworking.AndroidNetworking;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by pandu on 1/19/2018.
 */

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/RobotoCondensed-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        AndroidNetworking.initialize(getApplicationContext());
    }
}
