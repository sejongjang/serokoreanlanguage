package koreanlearning.hangul.serokorean.bottomNavigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import koreanlearning.hangul.serokorean.R;
import koreanlearning.hangul.serokorean.Search.Search;

public class More extends Fragment {

    ImageView imageView;

    public More() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_more, container, false);

        imageView = view.findViewById(R.id.moresearch);
        imageView.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Search.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
