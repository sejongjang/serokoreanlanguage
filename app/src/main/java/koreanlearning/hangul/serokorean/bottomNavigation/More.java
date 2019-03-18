package koreanlearning.hangul.serokorean.bottomNavigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hangul.serokorean.R;

import koreanlearning.hangul.serokorean.bottomNavigation.user.LoginFragment;
import koreanlearning.hangul.serokorean.bottomNavigation.user.Model;
import koreanlearning.hangul.serokorean.bottomNavigation.user.ProfileFragment;
import koreanlearning.hangul.serokorean.search.Search;

public class More extends Fragment {

    private ImageView imageView;
    private RelativeLayout loginView;
    private LoginFragment loginFragment;
    private ProfileFragment profileFragment;

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

        loginView = view.findViewById(R.id.login);
        loginView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Model model = Model.getModel();

                if(model.getAuthtoken() == null){
                    Toast.makeText(getContext(), "login button pressed", Toast.LENGTH_SHORT).show();
                    loginFragment = LoginFragment.newInstance();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.BeginnerOneContainer, loginFragment).addToBackStack("login").commit();
                }
                else{
                    switchToProfileFragment();
                }
            }
        });

        return view;
    }

    private void switchToProfileFragment() {
    }
}
