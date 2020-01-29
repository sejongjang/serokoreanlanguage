package koreanlearning.hangul.serokorean.bottomNavigation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hangul.serokorean.R;

import java.util.Arrays;
import java.util.List;

import koreanlearning.hangul.serokorean.bottomNavigation.acknowlegement.Acknowledgement;
import koreanlearning.hangul.serokorean.login.ProfileActivity;
import koreanlearning.hangul.serokorean.search.Search;

public class More extends Fragment{

    private int RC_SIGN_IN = 0;
    private ImageView searchIcon;
    private RelativeLayout loginView;
    private LinearLayout acknowledgement;
    private ImageView more_userPhoto;
    private TextView more_username;

    // this is the firebase custom login UI for the each providers.
    private List<AuthUI.IdpConfig> providers;
    private FirebaseUser currentUser;


    public More() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_more, container, false);
        more_username = view.findViewById(R.id.more_username);
        more_userPhoto = view.findViewById(R.id.more_userimage);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        searchIcon = view.findViewById(R.id.moresearch);
        searchIcon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Search.class);
                startActivity(intent);
            }
        });

        loginView = view.findViewById(R.id.login);
        loginView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                if(currentUser != null){
                    startProfileIntent();
                }
                else{
                    // init login providers, google and facebook
                    providers = Arrays.asList(new AuthUI.IdpConfig.FacebookBuilder().build(),
                            new AuthUI.IdpConfig.GoogleBuilder().build());

                    startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).setTheme(R.style.noWhitePreview).build(), RC_SIGN_IN);
                    getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });

        // set current user's name and profile picture
        if(currentUser != null){
            more_username.setText(currentUser.getDisplayName());

            if(currentUser.getPhotoUrl() != null){
                more_userPhoto.setBackground(null);
                Glide.with(this).load(currentUser.getPhotoUrl()).into(more_userPhoto);
            }
        }

        // login flow without firebase
        //
//        // Facebook current user checker. if user signed in already, then change name and profile picture
//        if(Profile.getCurrentProfile() != null){
//            more_username.setText(Profile.getCurrentProfile().getFirstName() + " " + Profile.getCurrentProfile().getLastName());
//            more_userPhoto.setBackground(null);
//
//            ProfilePictureView profilePictureView = view.findViewById(R.id.facebook_profile_picture);
//            profilePictureView.setProfileId(Profile.getCurrentProfile().getId());
//            profilePictureView.setVisibility(View.VISIBLE);
//        }
//
//        // Google sign in account check
//        if(GoogleSignIn.getLastSignedInAccount(getActivity()) != null){
//            GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(getActivity());
//            more_username.setText(googleSignInAccount.getDisplayName());
//
//            // background has to be reset because profile photo will be cover this
//            if(googleSignInAccount.getPhotoUrl() != null){
//                more_userPhoto.setBackground(null);
//                Glide.with(this).load(googleSignInAccount.getPhotoUrl()).into(more_userPhoto);
//            }
//        }

        // on click event for acknowledgement activity
        acknowledgement = view.findViewById(R.id.aknowledgementBox);
        acknowledgement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent acknowledgement = new Intent(getActivity(), Acknowledgement.class);
                startActivity(acknowledgement);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the intent from the Firebase
        if(requestCode == RC_SIGN_IN){
            if(resultCode == getActivity().RESULT_OK){
                currentUser = FirebaseAuth.getInstance().getCurrentUser();
                startProfileIntent();

                // once sign in successfully, update name and picture on the more tap
                more_username.setText(currentUser.getDisplayName());
                if(currentUser.getPhotoUrl() != null){
                    more_userPhoto.setBackground(null);
                    Glide.with(this).load(currentUser.getPhotoUrl()).into(more_userPhoto);
                }
            }
        }
    }

    private void startProfileIntent(){
        Intent intent = new Intent(getActivity(), ProfileActivity.class);
        getActivity().setResult(Activity.RESULT_OK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                getActivity().finish();
    }
}
