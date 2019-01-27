package com.example.serokorean.temp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.serokorean.R;

import java.util.List;

public class ContentAdapter extends PagerAdapter {

    private List<ContentModel> models;
    private LayoutInflater layoutInflater;
    private Context context;

    public ContentAdapter(List<ContentModel> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.contents_item, container, false);

        TextView chapter, desc;
//
//        chapter = view.findViewById(R.id.content1);
//        desc = view.findViewById(R.id.desc1);
//
//        chapter.setText(models.get(position).getTitle());
//        desc.setText(models.get(position).getDesc());

        container.addView(view, 0);

        return view;
    }



    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }
}
