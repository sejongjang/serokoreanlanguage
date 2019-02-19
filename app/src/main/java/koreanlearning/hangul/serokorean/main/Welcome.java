package koreanlearning.hangul.serokorean.main;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import koreanlearning.hangul.serokorean.R;

public class Welcome extends AppCompatActivity {
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FullScreencall();
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

    public void FullScreencall() {
        if(Build.VERSION.SDK_INT < 19){
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else {
            //for higher api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }
}
