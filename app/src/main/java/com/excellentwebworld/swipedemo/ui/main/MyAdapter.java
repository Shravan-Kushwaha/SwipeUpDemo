package com.excellentwebworld.swipedemo.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.excellentwebworld.swipedemo.App;
import com.excellentwebworld.swipedemo.databinding.ListVidBinding;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.DefaultMediaSourceFactory;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<String> urlList;
    private Context mContext;

    private SimpleExoPlayer simpleExoPlayer;
    private CacheDataSource.Factory cacheDataSourceFactory;
    private DefaultHttpDataSource.Factory httpDataSourceFactory;
    private DefaultDataSourceFactory defaultDataSourceFactory;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ListVidBinding mBinding;

        public ViewHolder(@NonNull ListVidBinding itemView) {
            super(itemView.getRoot());
            this.mBinding = itemView;
        }
    }
    public MyAdapter(List<String> urlList, Context context) {
        this.urlList = urlList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new ViewHolder(ListVidBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
//        Log.e("Image url 0", ""+urlList.get(1))

        setExoPlayer(holder, urlList, position);

    }
    @Override
    public int getItemCount() {
        return urlList.size();
    }


    private void setExoPlayer(ViewHolder holder, List<String> urlList, int position) {
        holder.mBinding.progressbar.setVisibility(View.VISIBLE);
        httpDataSourceFactory = new DefaultHttpDataSource.Factory().setAllowCrossProtocolRedirects(true);
        defaultDataSourceFactory = new DefaultDataSourceFactory(mContext, httpDataSourceFactory);

        cacheDataSourceFactory = new CacheDataSource.Factory()
                .setCache(App.simpleCache)
                .setUpstreamDataSourceFactory(httpDataSourceFactory)
                .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR);

        // this is it!

        simpleExoPlayer = new SimpleExoPlayer.Builder(mContext)
                .setMediaSourceFactory(new DefaultMediaSourceFactory(cacheDataSourceFactory)).build();

        if (urlList.get(position)!= null) {
            Uri uri = Uri.parse(urlList.get(position));
            MediaItem mediaItem = MediaItem.fromUri(uri);
            holder.mBinding.ivItem.setPlayer(simpleExoPlayer);
            ProgressiveMediaSource mediaSource = new ProgressiveMediaSource.Factory(cacheDataSourceFactory).createMediaSource(mediaItem);

            simpleExoPlayer.setPlayWhenReady(true);
            simpleExoPlayer.seekTo(0, 0);
            simpleExoPlayer.setRepeatMode(Player.REPEAT_MODE_ONE);
            simpleExoPlayer.setMediaSource(mediaSource, true);
            simpleExoPlayer.prepare();
            simpleExoPlayer.addListener(new ExoPlayer.Listener() {
                @Override
                public void onPlaybackStateChanged(@Player.State int state) {
                    if (state == ExoPlayer.STATE_BUFFERING) {
                        holder.mBinding.progressbar.setVisibility(View.VISIBLE);
                    } else {
                        holder.mBinding.progressbar.setVisibility(View.GONE);
                    }
                }
            });
        }
    }
    public void pause() {

        try {

            simpleExoPlayer.setPlayWhenReady(false);
            simpleExoPlayer.getPlaybackState();

        } catch (Exception e) {
            Log.e("MyAdapter", "pause() Exception = " + e);
        }
    }

    public void resume() {

        try {

            simpleExoPlayer.setPlayWhenReady(true);
            simpleExoPlayer.getPlaybackState();

        } catch (Exception e) {
            Log.e("MyAdapter", "resume() Exception = " + e.getLocalizedMessage());
        }
    }


}


