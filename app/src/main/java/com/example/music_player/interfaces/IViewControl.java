package com.example.music_player.interfaces;

public interface IViewControl {
    /**
     * 播放状态改变通知
     * @param state
     */
    void onPlayerStateChange(int state);

    /**
     * 播放的进度改变
     * @param seek
     */
    void onSeekChange(int seek);
}
