package com.shengdan.bannerx.entity;

public class BannerItemEntity {


    private Integer type = 5000;
    private String url;
    private long time = 5 * 1000;//切换时间，单位毫秒，默认5秒
    private boolean isVideoPlay;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isVideoPlay() {
        return isVideoPlay;
    }

    public void setVideoPlay(boolean videoPlay) {
        isVideoPlay = videoPlay;
    }

    public BannerItemEntity(Integer type, String url) {
        this.type = type;
        this.url = url;
    }

    public BannerItemEntity(Integer type, String url, long time) {
        this.type = type;
        this.url = url;
        this.time = time;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
