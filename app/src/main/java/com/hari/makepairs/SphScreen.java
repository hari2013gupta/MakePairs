package com.hari.makepairs;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.ImageView;

public class SphScreen extends Activity {

    private Thread mSplashThread;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Splash screen view
        setContentView(R.layout.splash);

        // Start animating the image
        final ImageView splashImageView = (ImageView) findViewById(R.id.SplashImageView);
        splashImageView.setBackgroundResource(R.drawable.flag);
        final AnimationDrawable frameAnimation = (AnimationDrawable) splashImageView.getBackground();
        splashImageView.post(new Runnable() {
            @Override
            public void run() {
                frameAnimation.start();
            }
        });

        // The thread to wait for splash screen events
        mSplashThread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        // Wait given period of time or exit on touch
                        wait(4000);
                    }
                } catch (InterruptedException ex) {
                }

                finish();

                // Run next activity
                Intent intent = new Intent();
                intent.setClass(SphScreen.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        };

        mSplashThread.start();

    }//endoncrea

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return false;
    }

    /**
     * Processes splash screen touch events
     */
    @Override
    public boolean onTouchEvent(MotionEvent evt) {
        if (evt.getAction() == MotionEvent.ACTION_DOWN) {
            synchronized (mSplashThread) {
                mSplashThread.notifyAll();
            }
        }
        return true;
    }
}
