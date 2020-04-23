package koreanlearning.hangul.serokorean.bottomNavigation;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hangul.serokorean.R;

import java.util.ArrayList;
import java.util.List;

import koreanlearning.hangul.serokorean.bottomNavigation.vocab.CustomVocab;
import koreanlearning.hangul.serokorean.model.VocabModel;


public class Vocab extends Fragment {

    private RecyclerView vocabRecyclerview;
    private RecyclerView.Adapter adapter;
    private List<VocabModel> vocabList = new ArrayList<>();
    private List<VocabModel> privateVocabList = new ArrayList<>();

    private ImageView vocab_custom;

    public Vocab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vocab, container, false);
        vocabRecyclerview = (RecyclerView) view.findViewById(R.id.vocab_recyclerview);
        vocabRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        fillOutVocabList();
        adapter = new VocabAdapter(getActivity(), vocabList);
        vocabRecyclerview.setAdapter(adapter);

        vocab_custom = view.findViewById(R.id.vocab_custom);
        vocab_custom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent customVocab = new Intent(getActivity(), CustomVocab.class);
                startActivity(customVocab);
            }
        });


        return view;
    }

    private void fillOutVocabList() {
        VocabModel v0 = new VocabModel("to eat", "먹다", "밥을 먹다 (to eat meal).", R.raw.v0);
        VocabModel v1 = new VocabModel("to buy", "사다", "시계를 사다 (to buy watch).", R.raw.v1);
        VocabModel v2 = new VocabModel("to sleep", "자다", "잠을 자다 (to sleep).", R.raw.v2);
        VocabModel v3 = new VocabModel("to help", "돕다", "친구를 돕다 (to help friend).", R.raw.v3);
        VocabModel v4 = new VocabModel("to cook", "만들다(요리하다)", "떡볶이를 만들다 (to make Tteokbokki).", R.raw.v4);
        VocabModel v5 = new VocabModel("bathroom", "화장실", "화장실이 어디에요? (where is bathroom?).", R.raw.v5);
        VocabModel v6 = new VocabModel("to go", "가다", "집에 가다 (to go home).", R.raw.v6);
        VocabModel v7 = new VocabModel("wow", "와우", "와우 (wow).", R.raw.v7);
        VocabModel v8 = new VocabModel("to get", "얻다", "정보를 얻다 (to get information).", R.raw.v8);
        VocabModel v9 = new VocabModel("to live", "살다", "나는 미국에 산다 (I am living in the United States).", R.raw.v9);

        vocabList.add(v0);
        vocabList.add(v1);
        vocabList.add(v2);
        vocabList.add(v3);
        vocabList.add(v4);
        vocabList.add(v5);
        vocabList.add(v6);
        vocabList.add(v7);
        vocabList.add(v8);
        vocabList.add(v9);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public class VocabAdapter extends RecyclerView.Adapter<VocabViewHolder> {
        private List<VocabModel> vocabList;
        private Context context;

        public VocabAdapter(Context context, List<VocabModel> vocabList) {
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
            VocabModel currentVocab = vocabList.get(position);

            TextView vocab_korean = holder.vocab_korean;
            TextView vocab_english = holder.vocab_english;
            TextView vocab_description = holder.vocab_description;
            RelativeLayout vocab_row = holder.vocab_row;
            ImageView add_vocab = holder.add_vocab;

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

            if(currentVocab.isAdded()) add_vocab.setImageDrawable(getResources().getDrawable(R.drawable.ic_remove_white_24dp));
            else add_vocab.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_white_24dp));

            add_vocab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(vocabList.get(position).isAdded()){
                        vocabList.get(position).setAdded(false);
                        privateVocabList.remove(currentVocab);
                        add_vocab.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_white_24dp));
                        Toast.makeText(getContext(), "removed " + vocabList.get(position).getEnglish(), Toast.LENGTH_SHORT).show();
                    }
                    else{
                        vocabList.get(position).setAdded(true);
                        privateVocabList.add(currentVocab);
                        add_vocab.setImageDrawable(getResources().getDrawable(R.drawable.ic_remove_white_24dp));
                        Toast.makeText(getContext(), "added " + vocabList.get(position).getEnglish(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        @Override
        public int getItemCount() { return vocabList.size(); }
    }

    public class VocabViewHolder extends RecyclerView.ViewHolder{
        private TextView vocab_korean;
        private TextView vocab_english;
        private TextView vocab_description;
        private RelativeLayout vocab_row;
        private ImageView add_vocab;

        public VocabViewHolder(@NonNull View itemView) {
            super(itemView);
            this.vocab_korean = itemView.findViewById(R.id.vocab_korean);
            this.vocab_english = itemView.findViewById(R.id.vocab_english);
            this.vocab_description = itemView.findViewById(R.id.vocab_description);
            this.vocab_row = itemView.findViewById(R.id.vocab_row);
            this.add_vocab = itemView.findViewById(R.id.add_vocab);
        }
    }
}
