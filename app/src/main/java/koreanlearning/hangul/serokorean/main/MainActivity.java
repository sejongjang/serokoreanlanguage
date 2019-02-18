package koreanlearning.hangul.serokorean.main;

import android.animation.ArgbEvaluator;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.hangul.serokorean.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    MainActivityCardAdapter adapter;
    List<MainActivityModel> models;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        FullScreencall();
        getWindow().setStatusBarColor(Color.parseColor("#c0000000"));
        getWindow().setNavigationBarColor(Color.parseColor("#c0000000"));
        setContentView(R.layout.activity_main);

        models = new ArrayList<>();
        models.add(new MainActivityModel(R.drawable.beginnerone, "Level 1 (Brand New Learner)", "Remember every great thing starts somewhere."));
        models.add(new MainActivityModel(R.drawable.beginnertwo, "Level 2 (Beginner)", "The good thing about learning languages \nis you don't need to be perfect!"));
        models.add(new MainActivityModel(R.drawable.intermediateone, "Level 3 (Lower Intermediate)", "Time to start impressing the natives!"));
        models.add(new MainActivityModel(R.drawable.castle, "Level 4 (Upper Intermediate)", "Learn how to sound intelligent."));
        models.add(new MainActivityModel(R.drawable.southkorea, "Level 5 (Advanced)", "Become Korean."));

        adapter = new MainActivityCardAdapter(models, this);

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(50, 0 ,50 ,0);
//        int marginPx = getResources().getDimensionPixelSize(R.dimen.page_margin);
//        viewPager.setPageMargin(marginPx);

        Integer[] colors_temp = {
                getResources().getColor(R.color.beginnerone),
                getResources().getColor(R.color.beginnertwo),
                getResources().getColor(R.color.black),
                getResources().getColor(R.color.nicewhite),
                getResources().getColor(R.color.aqua),
        };

        colors = colors_temp;

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positonOffsetPixels) {
                if((position < (adapter.getCount()) - 1) && position < (colors.length - 1)){
                    viewPager.setBackgroundColor(
                            (Integer) argbEvaluator.evaluate(
                                    positionOffset, colors[position], colors[position + 1]
                            )
                    );
                }else{
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                }

            }

            @Override
            public void onPageSelected(int currentPosition) { }

            @Override
            public void onPageScrollStateChanged(int i) { }
        });
    }
}
