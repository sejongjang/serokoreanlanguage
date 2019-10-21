package koreanlearning.hangul.serokorean.beginnerone;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.hangul.serokorean.R;
import koreanlearning.hangul.serokorean.bottomNavigation.FAQ;
import koreanlearning.hangul.serokorean.bottomNavigation.Home;
import koreanlearning.hangul.serokorean.bottomNavigation.More;
import koreanlearning.hangul.serokorean.bottomNavigation.Vocab;

public class BeginnerOne extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private BottomNavigationView bottomNavigationView;

    private Home home = new Home();
    private Vocab vocab = new Vocab();
    private FAQ faq = new FAQ();
    private More more = new More();
    private ViewPager viewPager;
    private MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // change top status bar color
        getWindow().setStatusBarColor(Color.parseColor("#1e1e1e"));
        getWindow().setNavigationBarColor(Color.parseColor("#383838"));
        setContentView(R.layout.activity_beginner_one);

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        //set default fragment only when the activity begins
        if(savedInstanceState == null){
            bottomNavigationView.setSelectedItemId(R.id.home);
        }

        viewPager = findViewById(R.id.beginnerone_viewpager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        BeginnerOneViewPagerAdapter viewPagerAdapter = new BeginnerOneViewPagerAdapter(getSupportFragmentManager());
        home = new Home();
        vocab = new Vocab();
        faq = new FAQ();
        more = new More();
        viewPagerAdapter.addFragment(home);
        viewPagerAdapter.addFragment(vocab);
        viewPagerAdapter.addFragment(faq);
        viewPagerAdapter.addFragment(more);
        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){

        for (int i = 0; i < bottomNavigationView.getMenu().size(); i++) {
            MenuItem menuItem = bottomNavigationView.getMenu().getItem(i);
            boolean isChecked = menuItem.getItemId() == item.getItemId();
            menuItem.setChecked(isChecked);
        }

        //switch fragments
        switch(item.getItemId()){
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.BeginnerOneContainer, home).commit();
                //.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                return true;
            case R.id.vocab:
                getSupportFragmentManager().beginTransaction().replace(R.id.BeginnerOneContainer, vocab).commit();
                return true;
            case R.id.FAQ:
                getSupportFragmentManager().beginTransaction().replace(R.id.BeginnerOneContainer, faq).commit();
                return true;
            case R.id.more:
                getSupportFragmentManager().beginTransaction().replace(R.id.BeginnerOneContainer, more).commit();
                return true;

        }
        return true;
    }
}
