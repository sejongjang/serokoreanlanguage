package com.example.serokorean.beginnerone;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.example.serokorean.R;
import com.example.serokorean.bottomNavigation.BookMark;
import com.example.serokorean.bottomNavigation.FAQ;
import com.example.serokorean.bottomNavigation.Home;
import com.example.serokorean.bottomNavigation.More;
import com.example.serokorean.bottomNavigation.Search;
import com.example.serokorean.bottomNavigation.Settings;
import com.example.serokorean.bottomNavigation.Vocab;

public class BeginnerOne extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private BottomNavigationView bottomNavigationView;

//    Search search = new Search();
//    Settings settings = new Settings();
//    BookMark bookmark = new BookMark();

    Home home = new Home();
    Vocab vocab = new Vocab();
    FAQ faq = new FAQ();
    More more = new More();

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

        switch(item.getItemId()){
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.BeginnerOneContainer, home).commit(); //.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
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
//            case R.id.search:
//                getSupportFragmentManager().beginTransaction().replace(R.id.BeginnerOneContainer, search).commit();
//                return true;
//            case R.id.bookmark:
//                getSupportFragmentManager().beginTransaction().replace(R.id.BeginnerOneContainer, bookmark).commit();
//                return true;
//            case R.id.settings:
//                getSupportFragmentManager().beginTransaction().replace(R.id.BeginnerOneContainer, settings).commit();
//                return true;

        }
        return true;
    }
//    private void setSingleEvent(GridLayout mainGrid){
//
//        for (int i=0; i<mainGrid.getChildCount(); i++){
//            final CardView cardView = (CardView) mainGrid.getChildAt(i);
//            final int finalI = i;
//            final String chapter = Integer.toString(i);
//            int pages = 0;
//
//            cardView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    Toast.makeText(BeginnerOne.this, "Clicked Button" + finalI, Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(BeginnerOne.this, BeginnerOneWebView.class); //ChapterPages.class
////                    Intent intent = new Intent(BeginnerOne.this, ChapterPages.class); //ChapterPages.class
//
//                    switch (chapter){
//                        case "0": intent.putExtra("chapter", "chapter 1"); intent.putExtra("pages", 17); break;
//                        case "1": intent.putExtra("chapter", "chapter 2"); intent.putExtra("pages", 9); break;
//                        case "2": intent.putExtra("chapter", "chapter 3"); intent.putExtra("pages", 7); break;
//                        case "3": intent.putExtra("chapter", "chapter 4"); intent.putExtra("pages", 4); break;
//                        case "4": intent.putExtra("chapter", "chapter 5"); intent.putExtra("pages", 6); break;
//                        case "5": intent.putExtra("chapter", "chapter 6"); intent.putExtra("pages", 13); break;
//                        case "6": intent.putExtra("chapter", "chapter 7"); intent.putExtra("pages", 13); break;
//                        case "7": intent.putExtra("chapter", "chapter 8"); intent.putExtra("pages", 13); break;
//                        case "8": intent.putExtra("chapter", "chapter 9"); intent.putExtra("pages", 13); break;
//                        case "9": intent.putExtra("chapter", "chapter 10"); intent.putExtra("pages", 13); break;
//                        case "10": intent.putExtra("chapter", "chapter 11"); intent.putExtra("pages", 13); break;
//                        case "11": intent.putExtra("chapter", "chapter 12"); intent.putExtra("pages", 13); break;
//                        case "12": intent.putExtra("chapter", "chapter 13"); intent.putExtra("pages", 13); break;
//                        case "13": intent.putExtra("chapter", "chapter 14"); intent.putExtra("pages", 13); break;
//                        case "14": intent.putExtra("chapter", "chapter 15"); intent.putExtra("pages", 13); break;
//                        case "15": intent.putExtra("chapter", "chapter 16"); intent.putExtra("pages", 13); break;
//                        case "16": intent.putExtra("chapter", "chapter 17"); intent.putExtra("pages", 13); break;
//                        case "17": intent.putExtra("chapter", "chapter 18"); intent.putExtra("pages", 13); break;
//                        case "18": intent.putExtra("chapter", "chapter 19"); intent.putExtra("pages", 13); break;
//                        case "19": intent.putExtra("chapter", "chapter 20"); intent.putExtra("pages", 13); break;
//                        case "20": intent.putExtra("chapter", "chapter 21"); intent.putExtra("pages", 13); break;
//                        case "21": intent.putExtra("chapter", "chapter 22"); intent.putExtra("pages", 13); break;
//                        case "22": intent.putExtra("chapter", "chapter 23"); intent.putExtra("pages", 13); break;
//                        case "23": intent.putExtra("chapter", "chapter 24"); intent.putExtra("pages", 13); break;
//                        case "24": intent.putExtra("chapter", "chapter 25"); intent.putExtra("pages", 13); break;
//                        case "25": intent.putExtra("chapter", "chapter 26"); intent.putExtra("pages", 13); break;
//                        case "26": intent.putExtra("chapter", "chapter 27"); intent.putExtra("pages", 13); break;
//                        case "27": intent.putExtra("chapter", "chapter 28"); intent.putExtra("pages", 13); break;
//                        case "28": intent.putExtra("chapter", "chapter 29"); intent.putExtra("pages", 13); break;
//                        case "29": intent.putExtra("chapter", "chapter 30"); intent.putExtra("pages", 13); break;
//                    }
//                    startActivity(intent);
//                }
//            });
//
//        }
//    }


}
