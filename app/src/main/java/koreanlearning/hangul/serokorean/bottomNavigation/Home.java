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

public class Home extends Fragment {

    private GridLayout mainGrid;
    private CardView intro;
    private final int CHAPTERS = 30;
    private final int CHAPTER_ONE_NUM_OF_PAGE = 17;
    private final int CHAPTER_TWO_NUM_OF_PAGE = 9;
    private final int CHAPTER_THREE_NUM_OF_PAGE = 7;
    private final int CHAPTER_FOUR_NUM_OF_PAGE = 4;
    private final int CHAPTER_FIVE_NUM_OF_PAGE = 6;
    private final int CHAPTER_SIX_NUM_OF_PAGE = 11;
    private final int CHAPTER_SEVEN_NUM_OF_PAGE = 6;
    private final int CHAPTER_EIGHT_NUM_OF_PAGE = 4;
    private final int CHAPTER_NINE_NUM_OF_PAGE = 7;
    private final int CHAPTER_TEN_NUM_OF_PAGE = 7;

    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        intro = view.findViewById(R.id.intro);
        intro.setOnClickListener(new View.OnClickListener()   {
            public void onClick(View v)  {
                try {
                    Intent intent = new Intent(getActivity(), BeginnerOneWebView.class);
                    intent.putExtra("chapter", "chapter 0");
                    intent.putExtra("pages", 5);
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

                    int numberOfPages = detectTheNumberOfPages(chapter);
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

    private int detectTheNumberOfPages(String chapter){
        switch (chapter){
            case "1": return CHAPTER_ONE_NUM_OF_PAGE;
            case "2": return CHAPTER_TWO_NUM_OF_PAGE;
            case "3": return CHAPTER_THREE_NUM_OF_PAGE;
            case "4": return CHAPTER_FOUR_NUM_OF_PAGE;
            case "5": return CHAPTER_FIVE_NUM_OF_PAGE;
            case "6": return CHAPTER_SIX_NUM_OF_PAGE;
            case "7": return CHAPTER_SEVEN_NUM_OF_PAGE;
            case "8": return CHAPTER_EIGHT_NUM_OF_PAGE;
            case "9": return CHAPTER_NINE_NUM_OF_PAGE;
            case "10": return CHAPTER_TEN_NUM_OF_PAGE;
            case "11": return 20;
            case "12": return 20;
            case "13": return 20;
            case "14": return 20;
            case "15": return 20;
            case "16": return 20;
            case "17": return 20;
            case "18": return 20;
            case "19": return 20;
            case "20": return 20;
            case "21": return 20;
            case "22": return 20;
            case "23": return 20;
            case "24": return 20;
            case "25": return 20;
            case "26": return 20;
            case "27": return 20;
            case "28": return 20;
            case "29": return 20;
            case "30": return 20;
        }
        return 0;
    }
}
