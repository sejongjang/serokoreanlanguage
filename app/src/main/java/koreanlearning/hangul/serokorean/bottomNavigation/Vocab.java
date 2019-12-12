package koreanlearning.hangul.serokorean.bottomNavigation;

import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hangul.serokorean.R;

import java.util.ArrayList;

import koreanlearning.hangul.serokorean.model.VocabModel;


public class Vocab extends Fragment {

    private RecyclerView vocabRecyclerview;
    private RecyclerView.Adapter adapter;
    private ArrayList<VocabModel> vocabList = new ArrayList<>();

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
}
