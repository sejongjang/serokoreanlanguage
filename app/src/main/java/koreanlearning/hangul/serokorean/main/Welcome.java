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

import com.hangul.serokorean.R;

public class Welcome extends AppCompatActivity {
    private TextView tv;

    //get rid of top status bar and bottom navigation bar
    public void fullScreencall() {
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

    //application starts from here.
    //initiate first activity can be changed in manifests setting
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //callFullScreenCall and set up View before it sets up on onCreate
        fullScreencall();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_main);

        //welcome page text, Sero Korean
        tv = findViewById(R.id.tv);
        //iv = findViewById(R.id.iv);

        //set up welcome page font
        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/makgeolli.ttf");
        tv.setTypeface(customFont);

        //wait for 2 seconds before the main page starts
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
