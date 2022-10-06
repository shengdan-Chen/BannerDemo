package com.shengdan.bannerx.recylerview.holder;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shengdan.bannerx.R;
import com.shengdan.bannerx.entity.BannerItemEntity;

import xyz.doikki.videoplayer.player.VideoView;

public class VideoHolder extends AbstractiViewHolder<BannerItemEntity>{
    public VideoView player;
    public VideoHolder(@NonNull View itemView) {
        super(itemView);
        player = itemView.findViewById(R.id.player);
        itemView.setTag(player);
    }

    @Override
    public void bindHolder(BannerItemEntity model) {
        //播放由外边控制
        player.setUrl(model.getUrl()); //设置视频地址
        player.setLooping(false);
    }
}
