package com.shengdan.bannerx;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.shengdan.bannerx.entity.BannerItemEntity;

import java.util.List;

import xyz.doikki.videoplayer.player.VideoView;

/**
 * 用来提前静默加载视频
 */
public class PreDKPlayer {
    private  VideoView videoView;
    private static PreDKPlayer preDKPlayer;

    public static PreDKPlayer getInstance() {
        if (preDKPlayer == null) {
            synchronized (PreDKPlayer.class) {
                if (preDKPlayer == null) {
                    preDKPlayer = new PreDKPlayer();
                }
            }
        }
        return preDKPlayer;
    }

    /**
     * 这里建议传入AppContext
     * @param context
     */
    public void init(Context context) {
        videoView = new VideoView(context);
    }

    public void getVideoListDuration(List<BannerItemEntity> itemEntityList, Handler h){

    }
}
