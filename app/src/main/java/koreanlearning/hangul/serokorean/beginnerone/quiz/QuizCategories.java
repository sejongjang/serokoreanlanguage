package koreanlearning.hangul.serokorean.beginnerone.quiz;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import com.hangul.serokorean.R;

import koreanlearning.hangul.serokorean.beginnerone.quiz.DBhelper.DBhelper;
import koreanlearning.hangul.serokorean.beginnerone.quiz.adapter.CategoryAdapter;
import koreanlearning.hangul.serokorean.beginnerone.quiz.common.SpaceDecoration;

public class QuizCategories extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView_category;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fullScreencall();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_categories);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Chapter Quiz");
        setSupportActionBar(toolbar);

        recyclerView_category = findViewById(R.id.recycler_category);
        recyclerView_category.setHasFixedSize(true);
        recyclerView_category.setLayoutManager(new GridLayoutManager(this, 2));

//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int height = displayMetrics.heightPixels / 8;
        CategoryAdapter adapter = new CategoryAdapter(QuizCategories.this, DBhelper.getInstance(this).getAllCategories());
        int spaceInPixel = 4;
        recyclerView_category.addItemDecoration(new SpaceDecoration(spaceInPixel));
        recyclerView_category.setAdapter(adapter);
    }
}
