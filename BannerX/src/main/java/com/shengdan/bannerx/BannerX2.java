package com.shengdan.bannerx;

import static com.shengdan.bannerx.BannerXConst.IMAGE;
import static com.shengdan.bannerx.BannerXConst.VIDEO;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.shengdan.bannerx.entity.BannerItemEntity;
import com.shengdan.bannerx.recylerview.BannerAdapter;
import com.shengdan.bannerx.recylerview.OnViewPagerListener;
import com.shengdan.bannerx.recylerview.ViewPagerLayoutManager;
import com.shengdan.bannerx.recylerview.holder.VideoHolder;

import java.util.ArrayList;
import java.util.List;

public class BannerX2 extends Fragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private BannerAdapter bannerAdapter;
    private Button bt;
    private String TAG = getClass().getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bannerx, container, true);
        recyclerView = view.findViewById(R.id.recyclerview);
        bt.setOnClickListener(this);
        initRv();
        return view;
    }

    private List<BannerItemEntity> list;

    private void initRv() {
        ViewPagerLayoutManager layoutManager = new ViewPagerLayoutManager(getContext(), OrientationHelper.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onInitComplete() {
                //自动播放第index条
//                startPlay(mIndex);
                Log.d(TAG, "onInitComplete: ");
            }

            @Override
            public void onPageRelease(boolean isNext, int position) {
//                if (mCurPos == position) {
//                    mVideoView.release();
//                }
                for (int i = 0; i < recyclerView.getChildCount(); i++) {
                    View itemView = recyclerView.getChildAt(i);
                    VideoHolder viewHolder = (VideoHolder) itemView.getTag();
                    if (viewHolder != null) {
                        Log.d(TAG, "onPageRelease: 暂停");
                        viewHolder.player.pause();
                    }
                }

                Log.d(TAG, "onPageRelease: " + position + "  ");
            }

            @Override
            public void onPageSelected(int position, boolean isBottom) {
                View itemView = recyclerView.getChildAt(0);
                VideoHolder viewHolder = (VideoHolder) itemView.getTag();
                if (viewHolder != null) {
                    Log.d(TAG, "onPageSelected: 开始");
                    viewHolder.player.start();
                }
                Log.d(TAG, "onPageSelected: " + position);
                if (position == list.size()-1){
                    recyclerView.scrollToPosition(1);
                }
            }
        });
        bannerAdapter = new BannerAdapter(getContext());
        list = getData();
        list.add(0, list.get(list.size() - 1));
        list.add(list.get(1));

        bannerAdapter.setData(list);
        recyclerView.setAdapter(bannerAdapter);
        recyclerView.scrollToPosition(1);

    }

    private List<BannerItemEntity> getData() {
        List<BannerItemEntity> list = new ArrayList<>();
        list.add(new BannerItemEntity(IMAGE, "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F8%2F5453005f74be2.jpg&refer=http%3A%2F%2Fpic1.win4000.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1667182840&t=6929030743c250ce6279dbea06c4db9d"));
        list.add(new BannerItemEntity(IMAGE, "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg2.niutuku.com%2Fdesk%2F1208%2F0759%2Fbizhi-0759-8796.jpg&refer=http%3A%2F%2Fimg2.niutuku.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1667184198&t=6dd498cd6cdc9b7a15bfaf94981b6e87"));
        list.add(new BannerItemEntity(VIDEO, "http://117.149.147.100:8890/yk/shuttle/autopilot_photo_upload/a6e6691076466fb745c8479b07b84a82.mp4"));
        list.add(new BannerItemEntity(IMAGE, "https://img0.baidu.com/it/u=2371305810,3587591415&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=281"));
        list.add(new BannerItemEntity(IMAGE, "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F2019-07-02%2F5d1b27f8f0681.jpg&refer=http%3A%2F%2Fpic1.win4000.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1667184314&t=6b66534da470d446c90b36aabbd08cc0"));
        list.add(new BannerItemEntity(IMAGE, "https://img2.baidu.com/it/u=2697101817,1543570296&fm=253&fmt=auto&app=120&f=JPEG?w=1100&h=733"));

        return list;
    }

    @Override
    public void onClick(View v) {
        bannerAdapter.notifyDataSetChanged();
    }
}
