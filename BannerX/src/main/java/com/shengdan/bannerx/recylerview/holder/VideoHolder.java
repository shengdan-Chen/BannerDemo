package com.shengdan.bannerx.recylerview.holder;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shengdan.bannerx.R;
import com.shengdan.bannerx.entity.BannerItemEntity;

import xyz.doikki.videoplayer.exo.ExoMediaPlayerFactory;
import xyz.doikki.videoplayer.player.VideoView;

public class VideoHolder extends AbstractiViewHolder<BannerItemEntity>{
    public VideoView player;
    public VideoHolder(@NonNull View itemView) {
        super(itemView);
        player = itemView.findViewById(R.id.player);
        itemView.setTag(player);//将播放器设为tag，方便外侧取出控制关闭播放
    }

    @Override
    public void bindHolder(BannerItemEntity model) {
        //播放由外边控制
        player.setUrl(model.getUrl()); //设置视频地址
        player.setLooping(false);
        player.setPlayerFactory(ExoMediaPlayerFactory.create());
        player.setOnClickListener(v -> {
            Log.d("VideoHolder", "onClick: getCurrentPlayState "+player.getCurrentPlayState());
            Log.d("VideoHolder", "onClick: getCurrentPlayerState "+player.getCurrentPlayerState());
        });
    }
}
