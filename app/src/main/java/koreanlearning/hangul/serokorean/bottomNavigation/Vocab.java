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
        VocabModel v0 = new VocabModel("to eat", "먹다", "VERB to eat or consume entirely: often used as an exhortation to children", R.raw.v0);
        VocabModel v1 = new VocabModel("to buy", "사다", "VERB If you buy something, you obtain it by paying money for it.", R.raw.v1);
        VocabModel v2 = new VocabModel("sleep", "자다", "NOUN Sleep is the natural state of rest in which your eyes are closed, your body is inactive, and your mind does not think.", R.raw.v2);
        VocabModel v3 = new VocabModel("help", "돕다", "VERB If you help someone, you make it easier for them to do something", R.raw.v3);
        VocabModel v4 = new VocabModel("to cook", "요리하다", "VERB When you cook a meal, you prepare food for eating by heating it.", R.raw.v4);
        VocabModel v5 = new VocabModel("bathroom", "화장실", "NOUN A bathroom is a room in a house that contains a bath or shower, a washbasin, and sometimes a toilet.", R.raw.v5);
        VocabModel v6 = new VocabModel("to go", "가다", "VERB When you go somewhere, you move or travel there.", R.raw.v6);
        VocabModel v7 = new VocabModel("wow", "와우", "EXCLAM feelings, INFORMAL You can say `wow' when you are very impressed, surprised, or pleased.", R.raw.v7);
        VocabModel v8 = new VocabModel("to get", "얻다", "VERB If you get something that you want or need, you obtain it.", R.raw.v8);
        VocabModel v9 = new VocabModel("to live", "살다", "VERB If someone lives in a particular place or with a particular person, their home is in that place or with that person.", R.raw.v9);

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
