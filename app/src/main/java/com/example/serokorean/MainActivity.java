package com.example.serokorean;

import android.animation.ArgbEvaluator;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    MainActivityCardAdapter adapter;
    List<MainActivityModel> models;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        models = new ArrayList<>();
        models.add(new MainActivityModel(R.drawable.beginnerone, "Beginner 1", "Learning very basic"));
        models.add(new MainActivityModel(R.drawable.beginnertwo, "Beginner 2", "Learning basic vocab and grammar"));
        models.add(new MainActivityModel(R.drawable.intermediateone, "Intermediate 1", "Usage of vocab and grammar"));
        models.add(new MainActivityModel(R.drawable.namecard, "Intermediate 2", "Make sentences and speak based off of basic"));
        models.add(new MainActivityModel(R.drawable.sticker, "Advanced", "Advanced sentences and usages"));

        adapter = new MainActivityCardAdapter(models, this);

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(50, 0 ,50 ,0);

        Integer[] colors_temp = {
                getResources().getColor(R.color.beginnerone),
                getResources().getColor(R.color.beginnertwo),
                getResources().getColor(R.color.intermediateone),
                getResources().getColor(R.color.intermediatetwo),
                getResources().getColor(R.color.advanced),
        };

        colors = colors_temp;

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positonOffsetPixels) {
                if((position < (adapter.getCount()) - 1) && position < (colors.length - 1)){
                    viewPager.setBackgroundColor(
                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position + 1]
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
