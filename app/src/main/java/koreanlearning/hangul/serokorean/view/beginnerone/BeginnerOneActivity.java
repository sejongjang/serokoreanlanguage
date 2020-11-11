package koreanlearning.hangul.serokorean.view.beginnerone;

import android.graphics.Color;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.hangul.serokorean.R;
import koreanlearning.hangul.serokorean.view.main.bottomNavigation.FAQFragment;
import koreanlearning.hangul.serokorean.view.main.bottomNavigation.HomeFragment;
import koreanlearning.hangul.serokorean.view.main.bottomNavigation.MoreFragment;
import koreanlearning.hangul.serokorean.view.main.bottomNavigation.VocabFragment;

public class BeginnerOneActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;
    private MenuItem prevMenuItem;
    private BeginnerOneViewPagerAdapter viewPagerAdapter;
    private int currentItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // change top status bar color
        getWindow().setStatusBarColor(Color.parseColor("#1e1e1e"));
        getWindow().setNavigationBarColor(Color.parseColor("#383838"));
        setContentView(R.layout.activity_beginner_one);

        // getting extra from the profile activity, because once user sign out name and picture should be updated as default
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            currentItem = extras.getInt("sign out");
        }

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.vocabFragment:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.Videos:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.moreFragment:
                        viewPager.setCurrentItem(3);
                        break;
                }

                return false;
            }
        });

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
        viewPagerAdapter = new BeginnerOneViewPagerAdapter(getSupportFragmentManager());
        HomeFragment homeFragment = new HomeFragment();
        VocabFragment vocabFragment = new VocabFragment();
        FAQFragment faqFragment = new FAQFragment();
        MoreFragment moreFragment = new MoreFragment();
        viewPagerAdapter.addFragment(homeFragment);
        viewPagerAdapter.addFragment(vocabFragment);
        viewPagerAdapter.addFragment(faqFragment);
        viewPagerAdapter.addFragment(moreFragment);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(currentItem);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == 10001){
//            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            Fragment moreFragment = viewPagerAdapter.getItem(3);
//            fragmentTransaction.detach(moreFragment).attach(moreFragment).commit();
//        }
//    }
}
