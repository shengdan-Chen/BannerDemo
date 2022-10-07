package com.shengdan.bannerx;

import static android.media.session.PlaybackState.PLAYBACK_POSITION_UNKNOWN;
import static android.media.session.PlaybackState.STATE_ERROR;
import static android.media.session.PlaybackState.STATE_NONE;
import static com.shengdan.bannerx.BannerXConst.FROM_VIDEO;
import static xyz.doikki.videoplayer.player.BaseVideoView.STATE_PLAYBACK_COMPLETED;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.shengdan.bannerx.entity.BannerItemEntity;
import com.shengdan.bannerx.recylerview.BannerAdapter;
import com.shengdan.bannerx.recylerview.OnViewPagerListener;
import com.shengdan.bannerx.recylerview.ViewPagerLayoutManager;

import java.util.ArrayList;
import java.util.List;

import xyz.doikki.videoplayer.player.BaseVideoView;
import xyz.doikki.videoplayer.player.VideoView;

public class BannerX extends LinearLayout implements LifecycleObserver, BaseVideoView.OnStateChangeListener {
    private RecyclerView recyclerView;
    private BannerAdapter bannerAdapter;
    private String TAG = getClass().getSimpleName();
    private List<BannerItemEntity> dataList = new ArrayList<>();
    private int index = 1;
    private final int slideWhat = 1, changeDurationWhat = 2;
    private boolean isSlide,isPause;
    private Handler slideHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case slideWhat:
                    //处理轮播
                    index++;
                    if (index > dataList.size() - 1) {
                        index = 2;
                    }
                    recyclerView.smoothScrollToPosition(index);
                    slideHandler.sendEmptyMessageDelayed(slideWhat, dataList.get(index).getTime());
                    break;
            }

            return false;
        }
    });

    public BannerX(Context context) {
        super(context);
        initialize(context);
    }

    public BannerX(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public BannerX(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    /**
     * 初始化View
     *
     * @param context Context
     */
    private void initialize(@NonNull Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_bannerx, this, true);
        recyclerView = view.findViewById(R.id.recyclerview);
        initRv();
    }

    /**
     * 初始化列表
     */
    private void initRv() {
        ViewPagerLayoutManager layoutManager = new ViewPagerLayoutManager(getContext(), OrientationHelper.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onInitComplete() {
            }

            @Override
            public void onPageRelease(boolean isNext, int position) {
                stopVideoPlay();
            }

            @Override
            public void onPageSelected(int position, boolean isBottom) {

            }
        });

        bannerAdapter = new BannerAdapter(getContext());
        recyclerView.setAdapter(bannerAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                int lastItemPosition;//最后可见 右边
                int firstItemPosition;//第一次可见 右边
                if (layoutManager instanceof LinearLayoutManager) {
                    Log.d(TAG, "onScrolled: 滑动回调");
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    lastItemPosition = linearManager.findLastVisibleItemPosition();
                    firstItemPosition = linearManager.findFirstVisibleItemPosition();
                    if ((firstItemPosition == lastItemPosition) && lastItemPosition == dataList.size() - 1) {
                        //移动到末尾了，移至第一项
                        recyclerView.scrollToPosition(1);
                    } else if ((firstItemPosition == lastItemPosition) && lastItemPosition == 0) {
                        //移动到头部了，移至倒数第二项
                        recyclerView.scrollToPosition(dataList.size() - 2);
                    } else if (firstItemPosition == lastItemPosition) {
                        //移动完成，在这里控制开启视频播放，上面的那个onPageRelease主要用来释放回调
                        Log.d(TAG, "onScrolled: 相同");
                        startVideoPlay(firstItemPosition);
                    }

                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }

    /**
     * 开启视频播放
     * @param firstItemPosition 在firstItemPosition == lastItemPosition时调用，随便选一个传入即可
     */
    private void startVideoPlay(int firstItemPosition) {
        if (isSlide)changeSlideMsg(firstItemPosition);//同步修改自动轮播的index

        View itemView = recyclerView.getChildAt(0);
        VideoView player = (VideoView) itemView.getTag();
        //取出当前的播放器控件，开启播放
        if (player != null) {
            Log.d(TAG, "startVideoPlay: 视频 当前播放器状态 "+player.getCurrentPlayState());
            //如果播放完成了 则重播
            if (player.getCurrentPlayState() == STATE_PLAYBACK_COMPLETED){
                Log.d(TAG, "startVideoPlay: 播放完成，重新播放");
                player.replay(true);
                player.start();
            }else{
                Log.d(TAG, "startVideoPlay: 开始播放");
                player.start();
            }

            //如果时跟随视频时长而移动的item，则注册监听；而后在item移走时移除
            if (dataList.get(firstItemPosition).getTime() == FROM_VIDEO){
                Log.d(TAG, "startVideoPlay: 根据视频时长播放");
                slideHandler.removeMessages(slideWhat);
                player.setOnStateChangeListener(BannerX.this);
            }
        }
    }

    /**
     * 关闭视频播放
     * 循环遍历 从tag中取出播放器，然后关闭其视频播放
     */
    private void stopVideoPlay() {
        for (int i = 0; i < recyclerView.getChildCount(); i++) {
            View itemView = recyclerView.getChildAt(i);
            VideoView player = (VideoView) itemView.getTag();
            if (player != null) {
                player.removeOnStateChangeListener(BannerX.this);
                player.pause();
            }
        }
    }

    /**
     * 修改轮播的index
     * @param nowItemPosition
     */
    private void changeSlideMsg(int nowItemPosition) {
        index = nowItemPosition;
        slideHandler.removeMessages(slideWhat);
        slideHandler.sendEmptyMessageDelayed(slideWhat, dataList.get(index).getTime());
    }

    /**
     * 首尾各添一项，无限轮播效果
     *
     * @param newBannerData
     * @return
     */
    private List<BannerItemEntity> addFooterAndHeader(List<BannerItemEntity> newBannerData) {
        newBannerData.add(0, new BannerItemEntity(newBannerData.get(newBannerData.size() - 1).getType(), newBannerData.get(newBannerData.size() - 1).getUrl()));
        newBannerData.add(new BannerItemEntity(newBannerData.get(1).getType(), newBannerData.get(1).getUrl()));
        return newBannerData;
    }

    /**
     * 设置banner
     *
     * @param newBannerData
     */
    public void setBannerItem(Lifecycle lifecycle, List<BannerItemEntity> newBannerData) {
        if (recyclerView == null || bannerAdapter == null) return;
        lifecycle.addObserver(this);
        dataList = addFooterAndHeader(newBannerData);
        recyclerView.setItemViewCacheSize(dataList.size());
        bannerAdapter.setData(dataList);
        recyclerView.scrollToPosition(1);
    }

    /**
     * 开启轮播
     */
    public void startSidle() {
        if (recyclerView == null || bannerAdapter == null || dataList.size() == 0) return;
        isSlide = true;
        slideHandler.removeMessages(slideWhat);
        slideHandler.sendEmptyMessageDelayed(slideWhat, dataList.get(index).getTime());
    }

    /**
     * 停止轮播
     */
    public void stopSidle() {
        slideHandler.removeMessages(slideWhat);
        slideHandler.removeCallbacksAndMessages("");
    }

    /**
     * 关闭轮播
     */
    public void closeSidle() {
        slideHandler.removeMessages(slideWhat);
        slideHandler.removeCallbacksAndMessages("");
        isSlide = false;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private void onDestroy() {
        bannerAdapter.clear();
        closeSidle();//防止内存泄漏
        recyclerView.setLayoutManager(null);
        recyclerView.removeOnScrollListener(null);
        recyclerView.removeAllViews();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void onPause() {
        isPause = true;
        stopSidle();//防止内存泄漏,移除handler轮播msg
        stopVideoPlay();//暂停视频播放
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void onResume() {
        if (isPause){ //如果是从另一个页面回来
            if (isSlide)startSidle();//开启轮播
            startVideoPlay(index);
            isPause = false;
        }
    }

    @Override
    public void onPlayerStateChanged(int playerState) {
        Log.d(TAG, "onPlayerStateChanged:  当前播放器状态"+playerState);

    }

    @Override
    public void onPlayStateChanged(int playState) {
        Log.d(TAG, "onPlayStateChanged: "+playState);
        //如果是网络异常，30s后才会走STATE_ERROR，如果是路径错误或格式异常，立刻走PLAYBACK_POSITION_UNKNOWN
        if (playState == STATE_PLAYBACK_COMPLETED || playState == STATE_ERROR || playState == PLAYBACK_POSITION_UNKNOWN
               ) {
            slideHandler.sendEmptyMessage(slideWhat);
        }
    }
}
