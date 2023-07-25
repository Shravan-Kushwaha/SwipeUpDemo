package com.excellentwebworld.swipedemo.ui.main;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.excellentwebworld.swipedemo.R;
import com.excellentwebworld.swipedemo.databinding.ActivityMainBinding;
import com.excellentwebworld.swipedemo.ui.second.SecondActivity;
import com.excellentwebworld.swipedemo.video_clip_animation.layoutmanager.SwipeableTouchHelperCallback;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LifecycleOwner {

    private ActivityMainBinding binding;
    ArrayList<String> list = new ArrayList<>();
    private MyAdapter myAdapter;
    private SwipeableTouchHelperCallback swipeableTouchHelperCallback;
    private ItemTouchHelper itemTouchHelper;
    public static Boolean isSwipeRight = false;
    private SimpleExoPlayer simpleExoPlayer;
    private CacheDataSource.Factory cacheDataSourceFactory;
    private DefaultHttpDataSource.Factory httpDataSourceFactory;
    private DefaultDataSourceFactory defaultDataSourceFactory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        list.add("https://media.geeksforgeeks.org/wp-content/uploads/20201217192146/Screenrecorder-2020-12-17-19-17-36-828.mp4?_=1");
        list.add("https://media.geeksforgeeks.org/wp-content/uploads/20201217163353/Screenrecorder-2020-12-17-16-32-03-350.mp4");
        list.add("https://bestvpn.org/html5demos/assets/dizzy.mp4");
        list.add("https://media.geeksforgeeks.org/wp-content/uploads/20201217192146/Screenrecorder-2020-12-17-19-17-36-828.mp4?_=1");
        list.add("https://media.geeksforgeeks.org/wp-content/uploads/20201217192146/Screenrecorder-2020-12-17-19-17-36-828.mp4?_=1");



//        binding.rv.setAdapter(new MyAdapter(list, this));

        myAdapter = new MyAdapter(list, this);
        binding.rv.setLayoutManager(new LinearLayoutManager(this));
        binding.rv.setAdapter(myAdapter);

        ItemTouchHelper.SimpleCallback touchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            private final ColorDrawable background = new ColorDrawable(getResources().getColor(R.color.black));

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//                adapter.showMenu(viewHolder.getAdapterPosition());
                startActivity(new Intent(getApplicationContext(), SecondActivity.class));
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                View itemView = viewHolder.itemView;

                if (dX > 0) {
                    background.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + ((int) dX), itemView.getBottom());
                } else if (dX < 0) {
                    background.setBounds(itemView.getRight() + ((int) dX), itemView.getTop(), itemView.getRight(), itemView.getBottom());
                } else {
                    background.setBounds(0, 0, 0, 0);
                }

                background.draw(c);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchHelperCallback);
        itemTouchHelper.attachToRecyclerView(binding.rv);


    }

    private void setRecyclerView(List<ClipData> clipData) {


    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(() -> myAdapter.resume(), 1000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        myAdapter.pause();
    }
}