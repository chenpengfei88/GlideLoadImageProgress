package com.fe.glideloadimageprogress;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fe.glideloadimageprogress.progress.ProgressImageView;
import com.fe.glideloadimageprogress.progress.ProgressModelLoader;
import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private ProgressImageView progressImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressImageView = (ProgressImageView) findViewById(R.id.progress_imageview);
        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(MainActivity.this).using(new ProgressModelLoader(new ProgressHandler(MainActivity.this, progressImageView))).
                        load("http://image2.sina.com.cn/dy/o/2004-11-10/1100077821_2laygS.jpg")
                        .diskCacheStrategy(DiskCacheStrategy.NONE).into(progressImageView.getImageView());
            }
        });

    }


    private static class ProgressHandler extends Handler {

        private final WeakReference<Activity> mActivity;
        private final ProgressImageView mProgressImageView;

        public ProgressHandler(Activity activity, ProgressImageView progressImageView) {
            super(Looper.getMainLooper());
            mActivity = new WeakReference<>(activity);
            mProgressImageView = progressImageView;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final Activity activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case 1:
                        int percent = msg.arg1*100/msg.arg2;
                        mProgressImageView.setProgress(percent);
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
