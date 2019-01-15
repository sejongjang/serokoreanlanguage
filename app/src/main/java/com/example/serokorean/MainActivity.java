package com.example.serokorean;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private TextView tv;
//    private ImageView iv;
//    private MainPage mainPage;
//    private FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("serokorean");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tv);
        //iv = findViewById(R.id.iv);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.loading);
        tv.startAnimation(animation);
        //iv.startAnimation(animation);

        final Intent intent = new Intent(this, MainPage.class);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(5000);
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
