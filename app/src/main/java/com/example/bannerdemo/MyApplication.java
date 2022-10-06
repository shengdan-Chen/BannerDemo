package com.example.bannerdemo;

import android.app.Application;
import android.content.Context;

import com.danikula.videocache.HttpProxyCacheServer;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class MyApplication extends Application {
    private RefWatcher refWatcher;
    @Override
    public void onCreate() {
        super.onCreate();

        refWatcher = setupLeakCanary();//2
//        initSpeech();
    }

    private RefWatcher setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return RefWatcher.DISABLED;
        }
        return LeakCanary.install(this);//1
    }

    private HttpProxyCacheServer proxy;
    public static HttpProxyCacheServer getProxy(Context context) {
        MyApplication app = (MyApplication) context.getApplicationContext();
        return app.proxy == null ? (app.proxy = app.newProxy()) : app.proxy;
    }
    private HttpProxyCacheServer newProxy() {
        return new HttpProxyCacheServer(this);
    }
}
