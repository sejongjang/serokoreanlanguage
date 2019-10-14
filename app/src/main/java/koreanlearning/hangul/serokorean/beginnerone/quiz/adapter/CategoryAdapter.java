package koreanlearning.hangul.serokorean.beginnerone.quiz.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hangul.serokorean.R;
import java.util.List;

import koreanlearning.hangul.serokorean.beginnerone.quiz.Model.Category;
import koreanlearning.hangul.serokorean.beginnerone.quiz.QuestionActivity;
import koreanlearning.hangul.serokorean.beginnerone.quiz.QuizCategories;
import koreanlearning.hangul.serokorean.beginnerone.quiz.common.QuizCommon;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    Context context;
    List<Category> categories;

    public CategoryAdapter(QuizCategories quizCategories, List<Category> allCategories) {
        context = quizCategories;
        categories = allCategories;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_category_item, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.quiz_chapter.setText(categories.get(i).getName());
    }

    @Override
    public int getItemCount() {
        if(categories == null){
            return 0;
        }
        return categories.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView card_category;
        TextView quiz_chapter;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            card_category = itemView.findViewById(R.id.card_catetory);
            quiz_chapter = itemView.findViewById(R.id.quiz_category_one);
            card_category.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    QuizCommon.selectedCategory = categories.get(getAdapterPosition()); //assign current category position
                    Intent selectedCategory = new Intent(context, QuestionActivity.class);
                    context.startActivity(selectedCategory);
                }
            });
        }
    }
}
