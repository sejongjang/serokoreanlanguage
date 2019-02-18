package koreanlearning.hangul.serokorean.main;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hangul.serokorean.R;
import koreanlearning.hangul.serokorean.beginnerone.BeginnerOne;

import java.util.List;

public class MainActivityCardAdapter extends PagerAdapter {

    private List<MainActivityModel> models;
    private LayoutInflater layoutInflater;
    private Context context;

    public MainActivityCardAdapter(List<MainActivityModel> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item, container, false);
        ImageView imageView;
        TextView title, desc;

        imageView = view.findViewById(R.id.image);
        title = view.findViewById(R.id.title);
        desc = view.findViewById(R.id.desc);

        imageView.setImageResource(models.get(position).getImage());
        title.setText(models.get(position).getTitle());
        desc.setText(models.get(position).getDesc());

        container.addView(view, 0);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = models.get(position).getTitle();

                switch(title){
                    case "Level 1 (Brand New Learner)":
                        Intent beginnerOneActivity = new Intent(container.getContext(), BeginnerOne.class);
                        container.getContext().startActivity(beginnerOneActivity);
                        break;
                    case "Level 2 (Beginner)":
                        Intent beginnerTwoActivity = new Intent(container.getContext(), BeginnerOne.class);
                        container.getContext().startActivity(beginnerTwoActivity);
                        break;
                    case "Level 3 (Lower Intermediate)":
                        Intent IntermediateOneActivity = new Intent(container.getContext(), BeginnerOne.class);
                        container.getContext().startActivity(IntermediateOneActivity);
                        break;
                    case "Level 4 (Upper Intermediate)":
                        Intent IntermediateTwoActivity = new Intent(container.getContext(), BeginnerOne.class);
                        container.getContext().startActivity(IntermediateTwoActivity);
                        break;
                    case "Level 5 (Advanced)":
                        Intent advancedActivity = new Intent(container.getContext(), BeginnerOne.class);
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
