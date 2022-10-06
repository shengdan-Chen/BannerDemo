package com.shengdan.bannerx.recylerview.holder;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shengdan.bannerx.R;
import com.shengdan.bannerx.entity.BannerItemEntity;

public class ImageHolder extends AbstractiViewHolder<BannerItemEntity> {
    public ImageView imageview;
    private Context context;
    public ImageHolder(@NonNull View itemView) {
        super(itemView);
        imageview = itemView.findViewById(R.id.imageview);
    }

    @Override
    public void bindHolder(BannerItemEntity model) {
        Glide.with(imageview.getContext()).load(model.getUrl())
                .into(imageview);
    }
}
