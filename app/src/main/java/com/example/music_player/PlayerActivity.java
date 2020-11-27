package com.example.music_player;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.example.music_player.interfaces.IPlayerControl;
import com.example.music_player.interfaces.IViewControl;
import com.example.music_player.service.PlayerService;

import static com.example.music_player.interfaces.IPlayerControl.MUSIC_PAUSE;
import static com.example.music_player.interfaces.IPlayerControl.MUSIC_PLAY;
import static com.example.music_player.interfaces.IPlayerControl.MUSIC_STOP;

public class PlayerActivity extends AppCompatActivity {
    private static final String TAG = "PlayerActivity";
    private SeekBar mSeeBar;
    private Button mPlay;
    private Button mClose;
    private PlayerConnection mConnection;
    private IPlayerControl mIPlayerControl;
    private boolean isUserTouch = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_avtivity);
        initView();
        initEvent();
        //开启服务
        initService();
        //绑定服务
        initBindService();
    }

    /**
     * 开启服务
     */
    private void initService() {
        Log.d(TAG, "initService ");
        Intent intent = new Intent(PlayerActivity.this, PlayerService.class);
        startService(intent);
    }

    /**
     * 绑定服务
     */
    private void initBindService() {
        Log.d(TAG, "initBindService ");
        Intent intent = new Intent(PlayerActivity.this, PlayerService.class);
        if (mConnection == null) {
            mConnection = new PlayerConnection();

        }
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    private class PlayerConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected");
            mIPlayerControl = (IPlayerControl) service;
            mIPlayerControl.registerViewController(mIPlayerControl);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIPlayerControl = null;
        }
    }

    /**
     * 设置相关事件
     */
    private void initEvent() {
        mSeeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //进度条发生变化

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //我的收已经触摸上去拖动
                isUserTouch = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                isUserTouch = false;
                int touchProgress = seekBar.getProgress();
                Log.d(TAG, "touchProgress: " + touchProgress);
                //停止播放
                if (mIPlayerControl!=null) {
                    mIPlayerControl.seekTo(seekBar.getProgress());
                }
            }
        });
        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //播放或者暂停
                if (mIPlayerControl != null) {
                    mIPlayerControl.playOrPause();
                }
            }
        });
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭按钮被点击了
                if (mIPlayerControl != null) {
                    mIPlayerControl.stopPlay();
                }
            }
        });
    }

    private void initView() {
        mSeeBar = findViewById(R.id.seek_bar);
        mPlay = findViewById(R.id.btn_player);
        mClose = findViewById(R.id.btn_close);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mConnection != null) {
            //释放资源
            mIPlayerControl.unRegisterViewController();
            unbindService(mConnection);
            Log.d(TAG, "onDestroy");
        }
    }
    private IViewControl mIViewControl = new IViewControl() {
        @Override
        public void onPlayerStateChange(int state) {
            //我们要根据播放状态
            switch (state) {
                case MUSIC_PLAY:
                    //修改按钮显示成暂停UI
                    mPlay.setText("暂停");
                    break;
                case MUSIC_PAUSE:
                    mPlay.setText("播放");
                    break;
                case MUSIC_STOP:
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onSeekChange(int seek) {
            //进度条改变，有一个条件当用户触摸到进度条的时候，就不更新
            if (!isUserTouch) {
                mSeeBar.setProgress(seek);
            }
        }
    };
}