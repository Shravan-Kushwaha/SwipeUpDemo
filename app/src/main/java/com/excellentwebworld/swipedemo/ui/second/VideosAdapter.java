package com.excellentwebworld.swipedemo.ui.second;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.excellentwebworld.swipedemo.R;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;

import java.util.List;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideoViewHolder>{
    private List<String> mVideoItems;
    SimpleExoPlayer simpleExoPlayer;
    Context mContext;


    public VideosAdapter(List<String> videoItems) {
        mVideoItems = videoItems;

    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new VideoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_simple,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        simpleExoPlayer = new SimpleExoPlayer.Builder(mContext).build();
        holder.setSimpleExoPlayer(mVideoItems.get(position), holder);

    }

    @Override
    public int getItemCount() {
        return mVideoItems.size();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder{
        PlayerView mVideoView;
        ProgressBar mProgressBar;
        private Boolean mute = false;
        MediaPlayer mediaPlayer;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            mVideoView = itemView.findViewById(R.id.videoView);
            mProgressBar = itemView.findViewById(R.id.progressBar);

        }
/*
        void setVideoData(String videoItem, VideoViewHolder holder){

            mVideoView.setVidPath(videoItem);
            mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                @Override
                public void onPrepared(MediaPlayer mp) {
                    mProgressBar.setVisibility(View.GONE);
                    mediaPlayer = mp;
                    mp.start();

                    float videoRatio = mp.getVideoWidth() / (float)mp.getVideoHeight();
                    float screenRatio = mVideoView.getWidth() / (float)mVideoView.getHeight();
                    float scale  = videoRatio / screenRatio;
                    if (scale >= 1f){
                        mVideoView.setScaleX(scale);
                    }else {
                        mVideoView.setScaleY(1f / scale);
                    }
                }
            });
            mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.start();
                }
            });

            holder.itemView.setOnClickListener(view -> {
                Log.e("paused", ""+ mute);
                if (mute) {
                    mediaPlayer.setVolume(0,0);

                }else{
                    mediaPlayer.setVolume(1,1);
                }
                mute =!mute;
            });
        }
*/

        void setSimpleExoPlayer(String videoItem, VideoViewHolder holder) {
            mVideoView.setPlayer(VideosAdapter.this.simpleExoPlayer);
            MediaItem mediaItem = MediaItem.fromUri(videoItem);
            VideosAdapter.this.simpleExoPlayer.addMediaItem(mediaItem);
            VideosAdapter.this.simpleExoPlayer.prepare();
            VideosAdapter.this.simpleExoPlayer.play();
            holder.itemView.setOnClickListener(view -> {
                Log.e("paused", ""+ mute);
                if (mute) {
                    mediaPlayer.setVolume(0,0);
                    VideosAdapter.this.simpleExoPlayer.setVolume(0f);

                }else{
                    VideosAdapter.this.simpleExoPlayer.setVolume(1f);
                }
                mute =!mute;
            });

        }
    }

    void stop(){
        try {
        VideosAdapter.this.simpleExoPlayer.stop();
        }catch (Exception e){
            Log.e("VideoAdapter", "stop() Exception = " + e.getLocalizedMessage());
        }
    }

    public void reLoad() {
        notifyDataSetChanged();
    }

    public void resume() {

        try {
            simpleExoPlayer.setPlayWhenReady(true);
            simpleExoPlayer.getPlaybackState();
        } catch (Exception e) {
            Log.e("VideoAdapter", "resume() Exception = " + e.getLocalizedMessage());
        }
    }

}
