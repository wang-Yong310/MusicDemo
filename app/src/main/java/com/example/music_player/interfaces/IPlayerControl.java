package com.example.music_player.interfaces;

public interface IPlayerControl {
    //播放状态
    //播放
    int MUSIC_PLAY = 1;
    int MUSIC_PAUSE = 2;
    int MUSIC_STOP = 3;

    /**
     * 把UI的控制接口设置给逻辑层
     *
     * @param viewController
     */
    void registerViewController(IPlayerControl viewController);

    /**
     * 取消接口通知的注册
     */
    void unRegisterViewController();

    /**
     * 播放音乐
     */
    void playOrPause();


    /**
     * 继续播放
     */
    void resume();

    /**
     * 停止播放
     */
    void stopPlay();

    /**
     * 设置播放进度
     */
    void seekTo(int seek);
}
