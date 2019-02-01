package com.example.serokorean;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class Welcome extends AppCompatActivity {
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_main);

        tv = findViewById(R.id.tv);
        //iv = findViewById(R.id.iv);

        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/makgeolli.ttf");

        tv.setTypeface(customFont);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.loading);
        tv.startAnimation(animation);
        //iv.startAnimation(animation);

        final Intent intent = new Intent(this, MainActivity.class);

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(intent);
                    finish();
                }
            }
        };
        timer.start();
    }
}
