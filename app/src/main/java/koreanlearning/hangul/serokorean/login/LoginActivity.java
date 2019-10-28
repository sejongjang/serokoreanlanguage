package koreanlearning.hangul.serokorean.login;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.hangul.serokorean.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import koreanlearning.hangul.serokorean.bottomNavigation.user.User;

public class LoginActivity extends AppCompatActivity {

    private int RC_SIGN_IN = 0;
    private SignInButton google_signInButton;
    private LoginButton facebook_signInButton;
    private CallbackManager callbackManager;
    private GoogleSignInClient googleSignInClient;
    private User user = User.getUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // initializing view
        google_signInButton = findViewById(R.id.google_sign_in_button);

        // configure sign in to request the user's id, email and basics
        // profile id and basic profile are included in DEFAULT_SIGN_IN
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        // build a GoogleSignInClient with the options specified by gso
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        google_signInButton.setOnClickListener((view)-> signIn());

        // facebook sign in
        facebookSignInFlow();
    }

    private void facebookSignInFlow() {
        callbackManager = CallbackManager.Factory.create();
        facebook_signInButton = findViewById(R.id.facebook_sign_in_button);
        facebook_signInButton.setReadPermissions("email");
        facebook_signInButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String accesstoken = loginResult.getAccessToken().getToken();
                String userId = loginResult.getAccessToken().getUserId();

                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        if(response != null){
                            String first_name = "";
                            String last_name = "";
                            String email = "";
                            String id = "";
                            String profilePhotoURL = "";

                            try {
                                User.getUser().setFirstname(object.getString("first_name"));
                                User.getUser().setLastname(object.getString("last_name"));
                                User.getUser().setEmail(object.getString("email"));
                                User.getUser().setUserId(object.getString("id"));
                                User.getUser().setPhotoURL("https://graph.facebook.com/" + object.getString("id") + "/picture?width=250&height=250");
                                User.getUser().setAuthtoken(loginResult.getAccessToken().getToken());
                                profilePhotoURL = "https://graph.facebook.com/" + object.getString("id") + "/picture?width=250&height=250";
                                first_name = object.getString("first_name");
                                last_name = object.getString("last_name");
                                email = object.getString("email");
                                id = object.getString("id");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                            intent.putExtra("first_name", first_name);
                            intent.putExtra("last_name", last_name);
                            intent.putExtra("email", email);
                            intent.putExtra("id", id);
                            intent.putExtra("profilePhotoURL", profilePhotoURL);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            finish();
                        }
//                        displayUserInfo(object);
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields" , "first_name ,last_name , email , id");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() { }

            @Override
            public void onError(FacebookException error) { }
        });

        // if Facebook already login in
        if(AccessToken.getCurrentAccessToken() != null){
            getFacebookMe();
        }
    }

    public void getFacebookMe() {
//        if (AccessToken.getCurrentAccessToken() != null) {
//            GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
//                @Override
//                public void onCompleted(JSONObject object, GraphResponse response) {
//                    Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
//                    try {
//                        intent.putExtra("first_name", object.getString("first_name"));
//                        intent.putExtra("last_name", object.getString("last_name"));
//                        intent.putExtra("email", object.getString("email"));
//                        intent.putExtra("id", object.getString("id"));
//                        intent.putExtra("profilePhotoURL", "https://graph.facebook.com/" + object.getString("id") + "/picture?width=250&height=250");
//                        startActivity(intent);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        } else {
//
//        }
        if(Profile.getCurrentProfile() != null){
            Profile currentProfile = Profile.getCurrentProfile();
            Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
            intent.putExtra("first_name", currentProfile.getFirstName());
            intent.putExtra("last_name", currentProfile.getLastName());
//            intent.putExtra("email", currentProfile.get());
            intent.putExtra("id", currentProfile.getId());
            intent.putExtra("profilePhotoURL", "https://graph.facebook.com/" + currentProfile.getId() + "/picture?width=250&height=250");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        }
    }

    private void displayUserInfo(JSONObject object){
        String first_name;
        String last_name;
        String email;
        String id;
        URL profilePhotoURL;

        try {
            profilePhotoURL = new URL("https://graph.facebook.com/" + object.getString("id") + "/picture?width=250&height=250");
            first_name = object.getString("first_name");
            last_name = object.getString("last_name");
            email = object.getString("email");
            id = object.getString("id");
        } catch (JSONException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void signIn(){
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 64206){
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }

        // Result returned from launching the intent from GoogleSignInClient.getSignInIntent
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try{
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        // check for existing google sign in account, if the user is already signed in
        // the GoogleSignInAccount will be not null
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null){
            setUpUserData(account);
            startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
        }
        super.onStart();
    }

    private void setUpUserData(GoogleSignInAccount account){
        user.setEmail(account.getEmail());
        user.setFirstname(account.getDisplayName());
        user.setPhotoURL(account.getPhotoUrl().toString());
        user.setUserId(account.getId());
    }
}
