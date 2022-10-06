package com.example.bannerdemo;

import android.content.Context;

public class AppSettings {

    private static volatile AppSettings singleton;
    private Context mContext;
    private AppSettings(Context context) {
        this.mContext = context;
    }

    public static AppSettings getInstance(Context context) {
        if (singleton == null) {
            synchronized (AppSettings.class) {
                if (singleton == null) {
                    singleton = new AppSettings(context);
                }
            }
        }
        return singleton;
    }
}