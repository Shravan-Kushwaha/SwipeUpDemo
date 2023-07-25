package com.excellentwebworld.swipedemo;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import com.google.android.exoplayer2.database.ExoDatabaseProvider;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;

public class App extends Application implements LifecycleOwner {

    private LeastRecentlyUsedCacheEvictor leastRecentlyUsedCacheEvictor;
    private ExoDatabaseProvider exoDatabaseProvider;
    public static SimpleCache simpleCache;
    private long exoPlayerCacheSize = 90 * 1024 * 1024;

    @Override
    public void onCreate() {
        super.onCreate();


        leastRecentlyUsedCacheEvictor = new LeastRecentlyUsedCacheEvictor(exoPlayerCacheSize);
        exoDatabaseProvider = new ExoDatabaseProvider(this);
        simpleCache = new SimpleCache(getCacheDir(), leastRecentlyUsedCacheEvictor, exoDatabaseProvider);
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return null;
    }
}
