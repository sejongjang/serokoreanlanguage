package koreanlearning.hangul.serokorean.bottomNavigation;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hangul.serokorean.R;

import java.util.ArrayList;

import koreanlearning.hangul.serokorean.model.VocabModel;

public class VocabAdapter extends RecyclerView.Adapter<VocabAdapter.VocabViewHolder> {
    private ArrayList<VocabModel> vocabList;
    private Context context;

    public VocabAdapter(Context context, ArrayList<VocabModel> vocabList) {
        this.context = context;
        this.vocabList = vocabList;
    }

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
                // play sound for the clicked instance
                MediaPlayer mediaPlayer = MediaPlayer.create(context, myVocab.getAudioSource());
                mediaPlayer.start();
            }
        });
    }

    @Override
    public int getItemCount() {
        return vocabList.size();
    }

    public static class VocabViewHolder extends RecyclerView.ViewHolder{
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
