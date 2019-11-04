package koreanlearning.hangul.serokorean.beginnerone.quiz;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import com.hangul.serokorean.R;
import koreanlearning.hangul.serokorean.beginnerone.quiz.adapter.CategoryAdapter;
import koreanlearning.hangul.serokorean.beginnerone.quiz.common.SpaceDecoration;
import koreanlearning.hangul.serokorean.utility.FullScreenCall;

public class QuizCategories extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FullScreenCall.fullScreen(this);
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
//        CategoryAdapter adapter = new CategoryAdapter(QuizCategories.this, DBhelper.getInstance(this).getAllCategories());


        CategoryAdapter adapter = new CategoryAdapter(QuizCategories.this, null);

        int spaceInPixel = 4;
        recyclerView_category.addItemDecoration(new SpaceDecoration(spaceInPixel));
        recyclerView_category.setAdapter(adapter);
    }

}
