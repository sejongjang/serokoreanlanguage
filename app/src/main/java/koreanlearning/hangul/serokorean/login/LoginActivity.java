package koreanlearning.hangul.serokorean.login;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
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

import org.json.JSONObject;

import java.util.Arrays;

import koreanlearning.hangul.serokorean.bottomNavigation.user.User;

public class LoginActivity extends AppCompatActivity {

    private int RC_SIGN_IN = 0;
    private SignInButton google_signInButton;
    private LoginButton facebook_signInButton;
    private CallbackManager callbackManager;
    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(AccessToken.getCurrentAccessToken() != null ){
            getFacebookMe();
        }

        // google sign in
        googleSignInFlow();

        // facebook sign in
        facebookSignInFlow();
    }

    private void googleSignInFlow() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        // configure sign in to request the user's id, email and basics
        // profile id and basic profile are included in DEFAULT_SIGN_IN
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        // build a GoogleSignInClient with the options specified by gso
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        if(account != null ){
            setupGoogleUser(account);
            Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
            setResult(Activity.RESULT_OK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();
        }

        // initializing view
        google_signInButton = findViewById(R.id.google_sign_in_button);
        google_signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

    }

    private void setupGoogleUser(GoogleSignInAccount account){
        User.getUser().resetUser();
        User.getUser().setGoogleSignInAccount(account);
//        User.getUser().setEmail(account.getEmail());
//        User.getUser().setFirstname(account.getGivenName());
//        User.getUser().setLastname(account.getFamilyName());
//        User.getUser().setPhotoURL(account.getPhotoUrl().toString());
//        User.getUser().setUserId(account.getId());
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try{
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            User.getUser().setGoogleSignInAccount(account);
            setResult(Activity.RESULT_OK);
            startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    // facebook login flow
    private void setupFacebookUser(JSONObject object){
        User.getUser().setProfile(Profile.getCurrentProfile());
        Profile facebookProfile= Profile.getCurrentProfile();

        if(facebookProfile != null){
            User.getUser().setFirstname(facebookProfile.getFirstName());
            User.getUser().setLastname(facebookProfile.getLastName());
            User.getUser().setUserId(facebookProfile.getId());
            User.getUser().setPhotoURL("https://graph.facebook.com/" + facebookProfile.getId() + "/picture?width=250&height=250");
        }
        else if(object != null){

        }
    }

    private void facebookSignInFlow() {
        callbackManager = CallbackManager.Factory.create();
        facebook_signInButton = findViewById(R.id.facebook_sign_in_button);
        facebook_signInButton.setReadPermissions("email");
        facebook_signInButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        if(response != null){
                            setupFacebookUser(object);
                            Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                            setResult(Activity.RESULT_OK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            finish();
                        }
                    }
                });
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() { }

            @Override
            public void onError(FacebookException error) { }
        });
    }

    public void getFacebookMe() {
        Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
        setResult(Activity.RESULT_OK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // result from facebook
        if(requestCode == 64206){
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }

        // Result returned from launching the intent from GoogleSignInClient.getSignInIntent
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
}
