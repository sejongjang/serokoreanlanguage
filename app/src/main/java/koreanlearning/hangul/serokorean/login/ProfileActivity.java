package koreanlearning.hangul.serokorean.login;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.hangul.serokorean.R;

import java.net.URL;

import koreanlearning.hangul.serokorean.bottomNavigation.user.User;

public class ProfileActivity extends AppCompatActivity {

    private GoogleSignInClient googleSignInClient;

    private String fb_first_name = "";
    private String fb_last_name = "";
    private String fb_email = "";
    private String fb_id = "";
    private String fb_profilePhotoString = "";
    private Uri fb_profilePhotoUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            fb_first_name = bundle.getString("first_name");
            fb_last_name = bundle.getString("last_name");
            fb_email = bundle.getString("email");
            fb_id = bundle.getString("id");
            fb_profilePhotoString = bundle.getString("profilePhotoURL");
            fb_profilePhotoUri = Uri.parse(fb_profilePhotoString);
        }

        Button sign_out = findViewById(R.id.sign_out);
        TextView name = findViewById(R.id.name);
        TextView email = findViewById(R.id.email);
        TextView userId = findViewById(R.id.userId);
        ImageView photo = findViewById(R.id.photo);

        if(fb_id != null){
            name.setText(fb_first_name + fb_last_name);
            email.setText(fb_email);
            userId.setText(fb_id);
            Glide.with(this).load(fb_profilePhotoUri).into(photo);
            sign_out.setOnClickListener((view)-> signOut());
        }
        else{
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).requestEmail().build();
            googleSignInClient = GoogleSignIn.getClient(this, gso);

            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(ProfileActivity.this);
            if(account != null){
                String personName = account.getDisplayName();
                String personGivenName = account.getGivenName();
                String personFamilyName = account.getFamilyName();
                String personEmail = account.getEmail();
                String personId = account.getId();
                Uri personPhoto = account.getPhotoUrl();

                name.setText("Name: " + personName);
                email.setText("Email: " + personEmail);
                userId.setText("UserID: " + personId);
                Glide.with(this).load(personPhoto).into(photo);
            }
            sign_out.setOnClickListener((view)-> signOut());
        }
    }

    private void signOut(){
        // facebook sign out
        if(Profile.getCurrentProfile() != null || fb_id != null){
            LoginManager.getInstance().logOut();
            String url = User.getUser().getPhotoURL();
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            finish();
        }
        // google sign out
        else{
            googleSignInClient.signOut().addOnCompleteListener(this, (task -> {
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                finish();
            }));
        }
    }
}
