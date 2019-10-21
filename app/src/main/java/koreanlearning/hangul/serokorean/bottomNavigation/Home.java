package koreanlearning.hangul.serokorean.bottomNavigation;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import com.hangul.serokorean.R;

import koreanlearning.hangul.serokorean.beginnerone.webview.BeginnerOneWebView;
import koreanlearning.hangul.serokorean.utility.NumOfPages;

public class Home extends Fragment {

    private GridLayout mainGrid;
    private CardView intro;
    private final int CHAPTERS = 30;

    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // set intro chapter's chapter and the number of pages
        intro = view.findViewById(R.id.intro);
        intro.setOnClickListener(new View.OnClickListener()   {
            public void onClick(View v)  {
                try {
                    Intent intent = new Intent(getActivity(), BeginnerOneWebView.class);
                    intent.putExtra("chapter", "chapter 0");
                    intent.putExtra("pages", NumOfPages.getChapterIntroNumOfPage());
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Typeface customFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/helvetica.ttf");
        changeFont(view, customFont);

        mainGrid = view.findViewById(R.id.mainGrid);
        setChaptersGrid(mainGrid);

        // Inflate the layout for this fragment
        return view;
    }

    private void setChaptersGrid(GridLayout mainGrid){

        for (int i=0; i<mainGrid.getChildCount(); i++){
            final CardView cardView = (CardView) mainGrid.getChildAt(i);
            final String chapter = Integer.toString(i+1);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), BeginnerOneWebView.class);

                    int numberOfPages = NumOfPages.detectTheNumberOfPages(chapter);
                    intent.putExtra("chapter", "chapter " + chapter);
                    intent.putExtra("pages", numberOfPages);
                    startActivity(intent);
                }
            });

        }
    }

    private void changeFont(View view, Typeface customFont) {
        TextView tv;
        tv = view.findViewById(R.id.introduction);
        tv.setTypeface(customFont);

        for(int i=1; i<=CHAPTERS; ++i){
            try {
                int id = R.id.class.getField("ch" + i).getInt(0);
                tv = view.findViewById(id);
                tv.setTypeface(customFont);
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }
}
