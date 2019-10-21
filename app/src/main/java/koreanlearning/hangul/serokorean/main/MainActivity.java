package koreanlearning.hangul.serokorean.main;

import android.animation.ArgbEvaluator;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import java.util.ArrayList;
import java.util.List;

import com.hangul.serokorean.R;

public class MainActivity extends AppCompatActivity {

    //viewPager for the horizontal scroll
    ViewPager viewPager;
    LevelCardsAdapter levelCardsViewPagerAdapter;
    List<LevelCardModel> levelCards;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //change top status bar and bottom naviation bar color change
        getWindow().setStatusBarColor(Color.parseColor("#1e1e1e"));
        getWindow().setNavigationBarColor(Color.parseColor("#383838"));

        //setContentView matches the Java file and Xml file
        //view pager is declared in activity_main
        setContentView(R.layout.activity_main);

        //save five different level cards in the Model class
        levelCards = new ArrayList<>();
        levelCards.add(new LevelCardModel(R.drawable.beginnerone, "Level 1 (Brand New Learner)", "Remember every great thing starts somewhere."));
        levelCards.add(new LevelCardModel(R.drawable.beginnertwo, "Level 2 (Beginner)", "The good thing about learning languages \nis you don't need to be perfect!"));
        levelCards.add(new LevelCardModel(R.drawable.intermediateone, "Level 3 (Lower Intermediate)", "Time to start impressing the natives!"));
        levelCards.add(new LevelCardModel(R.drawable.castle, "Level 4 (Upper Intermediate)", "Learn how to sound intelligent."));
        levelCards.add(new LevelCardModel(R.drawable.southkorea, "Level 5 (Advanced)", "Become Korean."));

        //view pager adapter holds the view for the view pager in main activity
        //pass levelCards model which contains 5 different level and this activities context
        levelCardsViewPagerAdapter = new LevelCardsAdapter(levelCards, this);

        //view pager's view is passed through pagerAdapter
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(levelCardsViewPagerAdapter);
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

        //setOnPageChangeListener is being deprecated
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //This method will be invoked when the current page is scrolled => instantiateItem in the levelCardsViewPagerAdapter
            @Override
            public void onPageScrolled(int position, float positionOffset, int positonOffsetPixels) {
                if((position < (levelCardsViewPagerAdapter.getCount()) - 1) && position < (colors.length - 1)){
                    viewPager.setBackgroundColor((Integer) argbEvaluator.evaluate(
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
