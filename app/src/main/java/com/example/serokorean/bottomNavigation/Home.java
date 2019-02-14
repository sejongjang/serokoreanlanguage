package com.example.serokorean.bottomNavigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import com.example.serokorean.beginnerone.webview.BeginnerOneWebView;
import com.example.serokorean.R;

public class Home extends Fragment {

    GridLayout mainGrid;
    CardView intro;

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

        mainGrid = view.findViewById(R.id.mainGrid);
        setSingleEvent(mainGrid);

        // Inflate the layout for this fragment
        return view;
    }

    private void setSingleEvent(GridLayout mainGrid){

        for (int i=0; i<mainGrid.getChildCount(); i++){
            final CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;
            final String chapter = Integer.toString(i);
            int pages = 0;

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(BeginnerOne.this, "Clicked Button" + finalI, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), BeginnerOneWebView.class); //ChapterPages.class
//                    Intent intent = new Intent(BeginnerOne.this, ChapterPages.class); //ChapterPages.class

                    switch (chapter){
                        case "0": intent.putExtra("chapter", "chapter 1"); intent.putExtra("pages", 17); break;
                        case "1": intent.putExtra("chapter", "chapter 2"); intent.putExtra("pages", 9); break;
                        case "2": intent.putExtra("chapter", "chapter 3"); intent.putExtra("pages", 7); break;
                        case "3": intent.putExtra("chapter", "chapter 4"); intent.putExtra("pages", 4); break;
                        case "4": intent.putExtra("chapter", "chapter 5"); intent.putExtra("pages", 6); break;
                        case "5": intent.putExtra("chapter", "chapter 6"); intent.putExtra("pages", 13); break;
                        case "6": intent.putExtra("chapter", "chapter 7"); intent.putExtra("pages", 13); break;
                        case "7": intent.putExtra("chapter", "chapter 8"); intent.putExtra("pages", 13); break;
                        case "8": intent.putExtra("chapter", "chapter 9"); intent.putExtra("pages", 13); break;
                        case "9": intent.putExtra("chapter", "chapter 10"); intent.putExtra("pages", 13); break;
                        case "10": intent.putExtra("chapter", "chapter 11"); intent.putExtra("pages", 13); break;
                        case "11": intent.putExtra("chapter", "chapter 12"); intent.putExtra("pages", 13); break;
                        case "12": intent.putExtra("chapter", "chapter 13"); intent.putExtra("pages", 13); break;
                        case "13": intent.putExtra("chapter", "chapter 14"); intent.putExtra("pages", 13); break;
                        case "14": intent.putExtra("chapter", "chapter 15"); intent.putExtra("pages", 13); break;
                        case "15": intent.putExtra("chapter", "chapter 16"); intent.putExtra("pages", 13); break;
                        case "16": intent.putExtra("chapter", "chapter 17"); intent.putExtra("pages", 13); break;
                        case "17": intent.putExtra("chapter", "chapter 18"); intent.putExtra("pages", 13); break;
                        case "18": intent.putExtra("chapter", "chapter 19"); intent.putExtra("pages", 13); break;
                        case "19": intent.putExtra("chapter", "chapter 20"); intent.putExtra("pages", 13); break;
                        case "20": intent.putExtra("chapter", "chapter 21"); intent.putExtra("pages", 13); break;
                        case "21": intent.putExtra("chapter", "chapter 22"); intent.putExtra("pages", 13); break;
                        case "22": intent.putExtra("chapter", "chapter 23"); intent.putExtra("pages", 13); break;
                        case "23": intent.putExtra("chapter", "chapter 24"); intent.putExtra("pages", 13); break;
                        case "24": intent.putExtra("chapter", "chapter 25"); intent.putExtra("pages", 13); break;
                        case "25": intent.putExtra("chapter", "chapter 26"); intent.putExtra("pages", 13); break;
                        case "26": intent.putExtra("chapter", "chapter 27"); intent.putExtra("pages", 13); break;
                        case "27": intent.putExtra("chapter", "chapter 28"); intent.putExtra("pages", 13); break;
                        case "28": intent.putExtra("chapter", "chapter 29"); intent.putExtra("pages", 13); break;
                        case "29": intent.putExtra("chapter", "chapter 30"); intent.putExtra("pages", 13); break;
                    }
                    startActivity(intent);
                }
            });

        }
    }
}
