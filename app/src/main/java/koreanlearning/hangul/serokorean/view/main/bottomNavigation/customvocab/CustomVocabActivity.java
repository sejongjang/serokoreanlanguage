package koreanlearning.hangul.serokorean.view.main.bottomNavigation.customvocab;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hangul.serokorean.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import koreanlearning.hangul.serokorean.model.VocabModel;

public class CustomVocabActivity extends AppCompatActivity { //implements SwipeRefreshLayout.OnRefreshListener

    private ImageView add_custom_vocab;
    private RecyclerView custom_vocab_recycler_view;
    private CustomVocabAdapter customVocabAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private Dialog custom_vocab_dialog;
    private Button submitTweet;
    private Button cancelTweet;
    private EditText typeKorean;
    private EditText typeEnglish;
    private EditText typeDescription;

    private Dialog review_vocab_dialog;
    private TextView review_word;
    private TextView review_meaning;
    private TextView review_memo;

    private List<VocabModel> privateVocabList = new ArrayList<>();

    // TODO: need to seperate this into controller
    private FirebaseUser firebaseUser;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(Color.parseColor("#1e1e1e"));
        getWindow().setNavigationBarColor(Color.parseColor("#383838"));
        setContentView(R.layout.activity_custom_vocab);

        custom_vocab_dialog = new Dialog(this);
        custom_vocab_dialog.setContentView(R.layout.custom_vocab_popup);

        review_vocab_dialog = new Dialog(this);
        review_vocab_dialog.setContentView(R.layout.review_vocab_popup);

        add_custom_vocab = findViewById(R.id.add_custom_vocab);
        add_custom_vocab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCustomVocabControl();
            }
        });

        readFromDB();

        custom_vocab_recycler_view = findViewById(R.id.custom_vocab_recycler_view);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        custom_vocab_recycler_view.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(custom_vocab_recycler_view.getContext(),
                linearLayoutManager.getOrientation());
        custom_vocab_recycler_view.addItemDecoration(dividerItemDecoration);

        customVocabAdapter = new CustomVocabAdapter(this, privateVocabList);
        custom_vocab_recycler_view.setAdapter(customVocabAdapter);
    }

    private void addCustomVocabControl() {
        custom_vocab_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        custom_vocab_dialog.show();

        submitTweet = custom_vocab_dialog.findViewById(R.id.pop_up_submit);
        cancelTweet = custom_vocab_dialog.findViewById(R.id.pop_up_cancel);

        typeKorean = custom_vocab_dialog.findViewById(R.id.type_korean);
        typeKorean.setText("");
        typeEnglish = custom_vocab_dialog.findViewById(R.id.type_english);
        typeEnglish.setText("");
        typeDescription = custom_vocab_dialog.findViewById(R.id.type_description);
        typeDescription.setText("");

        submitTweet.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                String korean = typeKorean.getText().toString();
                String english = typeEnglish.getText().toString();
                String description = typeDescription.getText().toString();
                VocabModel newVocab = new VocabModel(english, korean, description, 0);

                postNewVocab(newVocab);
                custom_vocab_dialog.dismiss();
            }
        });

        cancelTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                custom_vocab_dialog.dismiss();
            }
        });
    }

    private static String hashString(String message, String algorithm){
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            byte[] hashedBytes = digest.digest(message.getBytes("UTF-8"));

            return convertByteArrayToHexString(hashedBytes);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            ex.getStackTrace();
        }
        return null;
    }

    public static String generateMD5(String message) {
        return hashString(message, "MD5");
    }

    private static String convertByteArrayToHexString(byte[] arrayBytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < arrayBytes.length; i++) {
            stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }
        return stringBuffer.toString();
    }



    private void readFromDB(){
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String email = firebaseUser.getEmail();
        String replaced = email;
        if(email.contains(".")){
            replaced = email.replace(".", "_dot_");
        }
        String id = replaced;
//        "/" + id + "/posts/"
        mDatabase = FirebaseDatabase.getInstance().getReference("/" + id + "/posts/");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snap : dataSnapshot.getChildren()){
                    VocabModel vocab = snap.getValue(VocabModel.class);
                    vocab.setId(snap.getKey());
                    privateVocabList.add(vocab);
                }

                // load initial data from db
                customVocabAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private String relaceEmailDot(String email){
        String replaced = email;
        if(email.contains(".")){
            replaced = email.replace(".", "_dot_");
        }
       return replaced;
    }

    private void incrementReviewCount(VocabModel vocab){
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        String id = relaceEmailDot(firebaseUser.getEmail());

        vocab.setNum_of_times_reviewd(vocab.getNum_of_times_reviewd()+1);
        Map<String, Object> postValues = vocab.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/" + id + "/posts/" + vocab.getId(), postValues);

        mDatabase.updateChildren(childUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Toast.makeText(CustomVocab.this, "successfully updated " + vocab.getEnglish(), Toast.LENGTH_LONG).show();
                customVocabAdapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CustomVocabActivity.this, e.getMessage() + " " + id, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void postNewVocab(VocabModel newVocab) {
        newVocab.setAdded(true);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        String id = relaceEmailDot(firebaseUser.getEmail());
        String key = mDatabase.child("id").push().getKey();
        newVocab.setId(key);
        Map<String, Object> postValues = newVocab.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/" + id + "/posts/" + key, postValues);

        mDatabase.updateChildren(childUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Toast.makeText(CustomVocab.this, "successfully added " + newVocab.getEnglish(), Toast.LENGTH_LONG).show();
                int index = 0;
                privateVocabList.add(index, newVocab);
                customVocabAdapter.notifyItemInserted(index);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CustomVocabActivity.this, e.getMessage() + " " + id, Toast.LENGTH_LONG).show();
            }
        });
    }

    private class CustomVocabAdapter extends RecyclerView.Adapter<VocabViewHolder>  {
        private Context context;
        private List<VocabModel> vocabList;

        public CustomVocabAdapter() { loadMoreItem(); }

        public CustomVocabAdapter(Context context, List<VocabModel> vocabList){
            this.context = context;
            this.vocabList = vocabList;
            loadMoreItem();
        }

        private void loadMoreItem(){
            // TODO: 452 delete after SQL test -------------------------
//            VocabAsync vocabAsync = new VocabAsync(new VocabService());
//            vocabAsync.execute(new VocabRequest(123));
            // TODO: ---------------------------------------------------
        }

        void addItem() { }

        void removeItem() { }

        void addItems(){ }

        @NonNull
        @Override
        public VocabViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vocab_post_layout, parent, false);
            VocabViewHolder vocabViewHolder = new VocabViewHolder(view);
            return vocabViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull VocabViewHolder holder, int position) {
            TextView vocab_korean = holder.vocab_korean;
            TextView vocab_english = holder.vocab_english;
            TextView vocab_description = holder.vocab_description;
            RelativeLayout vocab_row = holder.vocab_row;

            VocabModel myVocab = vocabList.get(position);

            vocab_korean.setText(myVocab.getKorean());
            vocab_english.setText(myVocab.getEnglish());
            vocab_description.setText(myVocab.getDescription());

            vocab_row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    // play sound for the clicked instance
//                    MediaPlayer mediaPlayer = MediaPlayer.create(context, myVocab.getAudioSource());
//                    mediaPlayer.start();
                    controlReviewPopup(myVocab);
                    incrementReviewCount(myVocab);
                }
            });
        }

        private void controlReviewPopup(VocabModel myVocab) {
            review_vocab_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            review_vocab_dialog.show();

            review_word = review_vocab_dialog.findViewById(R.id.review_vocab_popup_word);
            review_word.setText(myVocab.getKorean());
            review_meaning = review_vocab_dialog.findViewById(R.id.review_vocab_popup_mearing);
            review_meaning.setText(myVocab.getEnglish());
            review_memo = review_vocab_dialog.findViewById(R.id.review_vocab_popup_memo);
            review_memo.setText(myVocab.getDescription());
        }

        @Override
        public int getItemCount() { return vocabList.size(); }

    }

    public class VocabViewHolder extends RecyclerView.ViewHolder{
        private TextView vocab_korean;
        private TextView vocab_english;
        private TextView vocab_description;
        private RelativeLayout vocab_row;

        public VocabViewHolder(@NonNull View itemView) {
            super(itemView);
            this.vocab_korean = itemView.findViewById(R.id.vocab_korean);
            this.vocab_english = itemView.findViewById(R.id.vocab_english);
            this.vocab_description = itemView.findViewById(R.id.vocab_description);
            this.vocab_row = itemView.findViewById(R.id.vocab_row);
        }
    }
}
