package koreanlearning.hangul.serokorean.view.main.levelselection;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hangul.serokorean.R;
import koreanlearning.hangul.serokorean.view.beginnerone.BeginnerOneActivity;
import koreanlearning.hangul.serokorean.model.LevelCardModel;

import java.util.List;

public class LevelCardsAdapter extends PagerAdapter {

    private List<LevelCardModel> levelCards;
    private LayoutInflater layoutInflater;
    private Context context;

    //create instance of the Adapter with the levelCards and mainActivity's context
    public LevelCardsAdapter(List<LevelCardModel> levelCards, Context context) {
        this.levelCards = levelCards;
        this.context = context;
    }

    @Override
    public int getCount() {
        return levelCards.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }

    //instantiateItem is called when the viewPager calls the getItem, onPageScrolled
    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        //set up layout inflater with the context from the main activity
        //define inflater -> inflate -> view
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item, container, false);
        ImageView imageView;
        TextView title, desc;

        //define cardView's image, title and description
        imageView = view.findViewById(R.id.image);
        title = view.findViewById(R.id.title);
        desc = view.findViewById(R.id.desc);

        imageView.setImageResource(levelCards.get(position).getImage());
        title.setText(levelCards.get(position).getTitle());
        desc.setText(levelCards.get(position).getDesc());

        Typeface customFont = Typeface.createFromAsset(view.getContext().getAssets(), "fonts/helvetica.ttf");
        title.setTypeface(customFont);
        desc.setTypeface(customFont);

        container.addView(view, 0);

        //viewPager onClick action => change activity to level activity
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = levelCards.get(position).getTitle();

                switch(title){
                    case "Level 1 (Brand New Learner)":
                        Intent beginnerOneActivity = new Intent(container.getContext(), BeginnerOneActivity.class);
                        container.getContext().startActivity(beginnerOneActivity);
                        break;
                    case "Level 2 (Beginner)":
                        Intent beginnerTwoActivity = new Intent(container.getContext(), BeginnerOneActivity.class);
                        container.getContext().startActivity(beginnerTwoActivity);
                        break;
                    case "Level 3 (Lower Intermediate)":
                        Intent IntermediateOneActivity = new Intent(container.getContext(), BeginnerOneActivity.class);
                        container.getContext().startActivity(IntermediateOneActivity);
                        break;
                    case "Level 4 (Upper Intermediate)":
                        Intent IntermediateTwoActivity = new Intent(container.getContext(), BeginnerOneActivity.class);
                        container.getContext().startActivity(IntermediateTwoActivity);
                        break;
                    case "Level 5 (Advanced)":
                        Intent advancedActivity = new Intent(container.getContext(), BeginnerOneActivity.class);
                        container.getContext().startActivity(advancedActivity);
                        break;
                }
            }
        });

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
