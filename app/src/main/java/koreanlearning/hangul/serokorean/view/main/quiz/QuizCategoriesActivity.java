package koreanlearning.hangul.serokorean.view.main.quiz;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import com.hangul.serokorean.R;
import koreanlearning.hangul.serokorean.view.main.quiz.adapter.CategoryAdapter;
import koreanlearning.hangul.serokorean.model.SpaceDecoration;
import koreanlearning.hangul.serokorean.utility.FullScreenCall;

public class QuizCategoriesActivity extends AppCompatActivity {

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

        CategoryAdapter adapter = new CategoryAdapter(QuizCategoriesActivity.this, null);

        int spaceInPixel = 4;
        recyclerView_category.addItemDecoration(new SpaceDecoration(spaceInPixel));
        recyclerView_category.setAdapter(adapter);
    }

}
