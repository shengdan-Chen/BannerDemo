package com.shengdan.bannerx.recylerview;


import static com.shengdan.bannerx.BannerXConst.IMAGE;
import static com.shengdan.bannerx.BannerXConst.VIDEO;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shengdan.bannerx.R;
import com.shengdan.bannerx.entity.BannerItemEntity;
import com.shengdan.bannerx.recylerview.holder.AbstractiViewHolder;
import com.shengdan.bannerx.recylerview.holder.ImageHolder;
import com.shengdan.bannerx.recylerview.holder.VideoHolder;

import java.util.List;

public class BannerAdapter extends RecyclerView.Adapter<AbstractiViewHolder<BannerItemEntity>> {
    private String TAG = getClass().getSimpleName();
    private List<BannerItemEntity> data;
    private Context context;

    public BannerAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<BannerItemEntity> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getType();
    }

    @NonNull
    @Override
    public AbstractiViewHolder<BannerItemEntity> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case IMAGE:
                View image = LayoutInflater.from(context).inflate(R.layout.banner_img_item, parent,false);
                return new ImageHolder(image);
            case VIDEO:
                View video = LayoutInflater.from(context).inflate(R.layout.banner_video_item, parent,false);
                return new VideoHolder(video);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AbstractiViewHolder<BannerItemEntity> holder, int position) {
        Log.d(TAG, "onBindViewHolder: "+position);
        switch (data.get(position).getType()){
            case IMAGE:
                ((ImageHolder)holder).bindHolder(data.get(position));
            break;
            case VIDEO:
                ((VideoHolder)holder).bindHolder(data.get(position));
                break;
        }
    }

    @Override
    public int getItemCount() {
//        return data.size() < 2 ? 1 : Integer.MAX_VALUE;
        return data == null ? 0 : data.size();
    }

    public void clear() {
        if (data == null)return;
        data.clear();
        notifyDataSetChanged();
    }
}
