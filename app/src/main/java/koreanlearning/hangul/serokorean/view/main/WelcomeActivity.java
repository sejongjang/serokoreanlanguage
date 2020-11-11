package koreanlearning.hangul.serokorean.view.main;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.hangul.serokorean.R;

import koreanlearning.hangul.serokorean.view.main.levelselection.LevelSelectionActivity;

public class WelcomeActivity extends AppCompatActivity {
    private TextView tv;
    private ImageView iv;

    private VideoView videoView;

    //application starts from here.
    //initiate first activity can be changed in manifests setting
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.welcome_main);

        videoView = findViewById(R.id.welcome_video_view);
        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.welcome);
        videoView.setVideoURI(video);

        final Intent intent = new Intent(this, LevelSelectionActivity.class);
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                startActivity(intent);
                finish();
            }
        });

        videoView.start();

//        Thread timer = new Thread() {
//            public void run() {
//                try {
//                    sleep(2000);
//                    } catch (InterruptedException e) {
//                    e.printStackTrace();
//                    } finally {
//                    startActivity(intent);
//                    finish();
//                    }
//                    }
//                    };
//                    timer.start();
    }
}

// old version welcome screen

////callFullScreenCall and set up View before it sets up on onCreate
////get rid of top status bar and bottom navigation bar
//        FullScreenCall.fullScreen(this);
//                super.onCreate(savedInstanceState);
//                setContentView(R.layout.welcome_main);
//
//                //welcome page text, Sero Korean
////        tv = findViewById(R.id.tv);
//                iv = findViewById(R.id.iv);
//
//                //set up welcome page font
////        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/makgeolli.ttf");
////        tv.setTypeface(customFont);
//
//                //wait for 2 seconds before the main page starts
//                Animation animation = AnimationUtils.loadAnimation(this, R.anim.loading);
//                iv.startAnimation(animation);
////iv.startAnimation(animation);
//
//final Intent intent = new Intent(this, LevelSelectionActivity.class);
//
//        Thread timer = new Thread() {
//public void run() {
//        try {
//        sleep(2000);
//        } catch (InterruptedException e) {
//        e.printStackTrace();
//        } finally {
//        startActivity(intent);
//        finish();
//        }
//        }
//        };
//        timer.start();