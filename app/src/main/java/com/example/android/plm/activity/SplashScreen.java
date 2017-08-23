package com.example.android.plm.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.example.android.plm.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashScreen extends AppCompatActivity {

    @Bind(R.id.linear_layout) LinearLayout _linearLayout;
    @Bind(R.id.logo) ImageView _logo;
    @Bind(R.id.app_name) TextView _appName;
    @Bind(R.id.app_quote) TextView _appQuote;

    Handler handler;
    Runnable runnable;
    Long delay_time;
    Long time = 2600L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_screen);
        ButterKnife.bind(this);
        handler = new Handler();
        
        StartAnimations();
        
        runnable = new Runnable() {
            public void run() {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };

    }

    public void onResume() {
        super.onResume();
        delay_time = time;
        handler.postDelayed(runnable, delay_time);
        time = System.currentTimeMillis();
    }

    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
        time = delay_time - (System.currentTimeMillis() - time);
    }

    public void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
        time = delay_time - (System.currentTimeMillis() - time);
    }

    private void StartAnimations() {

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
        animation.reset();

        _linearLayout.clearAnimation();
        _linearLayout.setAnimation(animation);

        animation = AnimationUtils.loadAnimation(this, R.anim.translate);
        animation.reset();

        _logo.clearAnimation();
        _logo.setAnimation(animation);

        _appName.clearAnimation();
        _appName.setAnimation(animation);

        _appQuote.clearAnimation();
        _appQuote.setAnimation(animation);

    }
}
