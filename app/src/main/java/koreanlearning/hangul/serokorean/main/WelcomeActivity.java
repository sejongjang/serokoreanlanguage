package koreanlearning.hangul.serokorean.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.hangul.serokorean.R;

import koreanlearning.hangul.serokorean.utility.FullScreenCall;

public class WelcomeActivity extends AppCompatActivity {
    private TextView tv;
    private ImageView iv;

    //application starts from here.
    //initiate first activity can be changed in manifests setting
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //callFullScreenCall and set up View before it sets up on onCreate
        //get rid of top status bar and bottom navigation bar
        FullScreenCall.fullScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_main);

        //welcome page text, Sero Korean
//        tv = findViewById(R.id.tv);
        iv = findViewById(R.id.iv);

        //set up welcome page font
//        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/makgeolli.ttf");
//        tv.setTypeface(customFont);

        //wait for 2 seconds before the main page starts
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.loading);
        iv.startAnimation(animation);
        //iv.startAnimation(animation);

        final Intent intent = new Intent(this, LevelSelectionActivity.class);

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
