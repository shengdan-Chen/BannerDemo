package com.example.bannerdemo;


import static com.shengdan.bannerx.BannerXConst.FROM_VIDEO;
import static com.shengdan.bannerx.BannerXConst.IMAGE;
import static com.shengdan.bannerx.BannerXConst.VIDEO;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.danikula.videocache.HttpProxyCacheServer;
import com.shengdan.bannerx.BannerX;
import com.shengdan.bannerx.entity.BannerItemEntity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BannerX banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        banner = findViewById(R.id.banner);
        banner.setBannerItem(getLifecycle(),getData1());
        banner.startSidle();
    }

    private String getCache(String url) {
        HttpProxyCacheServer proxy = MyApplication.getProxy(this);
        String proxyUrl = proxy.getProxyUrl(url);
        return proxyUrl;
    }

    private List<BannerItemEntity> getData1() {
        List<BannerItemEntity> list = new ArrayList<>();
        list.add(new BannerItemEntity(IMAGE, "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F8%2F5453005f74be2.jpg&refer=http%3A%2F%2Fpic1.win4000.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1667182840&t=6929030743c250ce6279dbea06c4db9d"));
        list.add(new BannerItemEntity(VIDEO, getCache("http://117.149.147.100:8890/yk/shuttle/autopilot_photo_upload/a6e6691076466fb745c8479b07b84a82.mp4"),FROM_VIDEO));
        list.add(new BannerItemEntity(IMAGE, "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg2.niutuku.com%2Fdesk%2F1208%2F0759%2Fbizhi-0759-8796.jpg&refer=http%3A%2F%2Fimg2.niutuku.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1667184198&t=6dd498cd6cdc9b7a15bfaf94981b6e87"));
        list.add(new BannerItemEntity(VIDEO, getCache("http://117.149.147.100:8890/yk/shuttle/autopilot_photo_upload/a311ba5d41a52305ef3ffa377eb03ea1.mp4"),5000));
        list.add(new BannerItemEntity(IMAGE, "https://img0.baidu.com/it/u=2371305810,3587591415&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=281"));
        list.add(new BannerItemEntity(VIDEO, getCache("http://117.149.147.100:8890/yk/shuttle/autopilot_photo_upload/a826e3abd8c0d91acfca4e7f4f271e67.mp4"),10000));
        list.add(new BannerItemEntity(IMAGE, "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F2019-07-02%2F5d1b27f8f0681.jpg&refer=http%3A%2F%2Fpic1.win4000.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1667184314&t=6b66534da470d446c90b36aabbd08cc0"));
        list.add(new BannerItemEntity(IMAGE, "https://img2.baidu.com/it/u=2697101817,1543570296&fm=253&fmt=auto&app=120&f=JPEG?w=1100&h=733"));
        return list;
    }

    private List<BannerItemEntity> getData2() {
        List<BannerItemEntity> list = new ArrayList<>();
//        list.add(new BannerItemEntity(IMAGE, "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F8%2F5453005f74be2.jpg&refer=http%3A%2F%2Fpic1.win4000.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1667182840&t=6929030743c250ce6279dbea06c4db9d"));
        list.add(new BannerItemEntity(VIDEO, getCache("http://117.149.147.100:8890/yk/shuttle/autopilot_photo_upload/a6e6691076466fb745c8479b07b84a82.mp4"),FROM_VIDEO));
//        list.add(new BannerItemEntity(IMAGE, "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg2.niutuku.com%2Fdesk%2F1208%2F0759%2Fbizhi-0759-8796.jpg&refer=http%3A%2F%2Fimg2.niutuku.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1667184198&t=6dd498cd6cdc9b7a15bfaf94981b6e87"));
//        list.add(new BannerItemEntity(VIDEO, getCache("http://117.149.147.100:8890/yk/shuttle/autopilot_photo_upload/a311ba5d41a52305ef3ffa377eb03ea1.mp4"),5000));
//        list.add(new BannerItemEntity(IMAGE, "https://img0.baidu.com/it/u=2371305810,3587591415&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=281"));
//        list.add(new BannerItemEntity(VIDEO, getCache("http://117.149.147.100:8890/yk/shuttle/autopilot_photo_upload/a826e3abd8c0d91acfca4e7f4f271e67.mp4"),10000));
//        list.add(new BannerItemEntity(IMAGE, "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F2019-07-02%2F5d1b27f8f0681.jpg&refer=http%3A%2F%2Fpic1.win4000.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1667184314&t=6b66534da470d446c90b36aabbd08cc0"));
//        list.add(new BannerItemEntity(IMAGE, "https://img2.baidu.com/it/u=2697101817,1543570296&fm=253&fmt=auto&app=120&f=JPEG?w=1100&h=733"));
        return list;
    }

    public void onClick(View view) {
//        startActivity(new Intent(this,SplashActivity.class));
        banner.setBannerItem(getLifecycle(),getData2());

    }
}