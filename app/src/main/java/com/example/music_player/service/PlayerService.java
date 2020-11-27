package com.example.music_player.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.music_player.presenter.*;

public class PlayerService extends Service {

    private static final String TAG = "PlayerService";
    private PlayerPresenter mPlayerPresenter;

    @Override
    public void onCreate() {
        if (mPlayerPresenter == null) {
            mPlayerPresenter = new PlayerPresenter();
        }
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return mPlayerPresenter;

    }
}
