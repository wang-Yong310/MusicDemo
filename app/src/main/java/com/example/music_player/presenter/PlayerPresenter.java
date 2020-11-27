package com.example.music_player.presenter;

import android.os.Binder;
import android.util.Log;

import com.example.music_player.interfaces.IPlayerControl;

public class PlayerPresenter extends Binder implements IPlayerControl {

    private static final String TAG = "PlayerPresenter";
    private IPlayerControl viewController;

    @Override
    public void registerViewController(IPlayerControl viewController) {
        this.viewController = viewController;
    }

    @Override
    public void unRegisterViewController() {
        viewController = null;
    }

    @Override
    public void playOrPause() {
        Log.d(TAG, "playOrPause");

    }

    @Override
    public void resume() {
        Log.d(TAG, "resume");
    }

    @Override
    public void stopPlay() {
        Log.d(TAG, "stopPlay");
    }

    @Override
    public void seekTo(int seek) {
        Log.d(TAG, "seekTo");
    }
}
