package com.shengdan.bannerx.recylerview.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class AbstractiViewHolder<T> extends RecyclerView.ViewHolder {
    public AbstractiViewHolder(@NonNull View itemView) {
        super(itemView);
    }
    public abstract void bindHolder(T model);

}
