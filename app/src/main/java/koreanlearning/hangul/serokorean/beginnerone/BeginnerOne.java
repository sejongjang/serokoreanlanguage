package koreanlearning.hangul.serokorean.beginnerone;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
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

    Home home = new Home();
    Vocab vocab = new Vocab();
    FAQ faq = new FAQ();
    More more = new More();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#c0000000"));
        getWindow().setNavigationBarColor(Color.parseColor("#c0000000"));
        setContentView(R.layout.activity_beginner_one);

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        //set default fragment only when the activity begins
        if(savedInstanceState == null){
            bottomNavigationView.setSelectedItemId(R.id.home);
        }
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
