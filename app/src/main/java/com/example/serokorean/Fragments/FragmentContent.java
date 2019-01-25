package com.example.serokorean.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.serokorean.R;


public class FragmentContent extends Fragment {

    private static final String KEY_TITLE = "Level";
    //private static final String KEY_TITLE = "file:///android_asset/chapter";
    //private String URL = "file:///android_asset/chapter";
    private WebView webView;

    public FragmentContent() {
        // Required empty public constructor
    }

//    public static FragmentContent newInstance(String param1) {
//        FragmentContent fragment = new FragmentContent();
//        Bundle args = new Bundle();
//        //args.putString(KEY_TITLE, param1);
//        args.putString(KEY_TITLE, param1 + ".html");
//        fragment.setArguments(args);
//
//        return fragment;
//    }

    public static FragmentContent newInstance(String chapter, String level) {
        FragmentContent fragment = new FragmentContent();
        Bundle args = new Bundle();
        //args.putString(KEY_TITLE, param1);
        args.putString(KEY_TITLE, level + " " + chapter + ".html");
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_content, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webView = view.findViewById(R.id.title);
        String title = getArguments().getString(KEY_TITLE);
        //((TextView) view.findViewById(R.id.title)).setText(title);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("file:///android_asset/");
        stringBuilder.append(title);
        String URL = stringBuilder.toString();
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(URL);
    }
}
