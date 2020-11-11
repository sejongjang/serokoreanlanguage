package koreanlearning.hangul.serokorean.view.main.quiz;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.hangul.serokorean.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import koreanlearning.hangul.serokorean.model.CurrentQuestion;
import koreanlearning.hangul.serokorean.model.Question;
import koreanlearning.hangul.serokorean.view.main.quiz.adapter.QuestionFragmentsAdapter;
import koreanlearning.hangul.serokorean.model.Quiz;
import koreanlearning.hangul.serokorean.utility.FullScreenCall;

import static koreanlearning.hangul.serokorean.model.Quiz.questionList;

public class QuestionActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private int currentPosition=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FullScreenCall.fullScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        // read level and chapter and load questions
        Bundle bundle = getIntent().getExtras();
        int level = bundle.getInt("level");
        int chapter = bundle.getInt("chapter");

        questionList.clear();
        importQuestion(level, chapter);

        // only if there are questions
        if(questionList.size() > 0) {
            viewPager = findViewById(R.id.quizViewpager);
            tabLayout = findViewById(R.id.sliding_tabs);
            genQuestionFragmentList();

            // set up question list on viewPager for the swipe
            QuestionFragmentsAdapter questionFragmentsAdapter = new QuestionFragmentsAdapter(getSupportFragmentManager(), this, Quiz.fragmentsList);
            viewPager.setAdapter(questionFragmentsAdapter);
            viewPager.setOffscreenPageLimit(questionList.size()-1);
            tabLayout.setupWithViewPager(viewPager);

            // detect the swipe between fragments
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                int SCROLLINNG_RIGHT = 0;
                int SCROLLINNG_LEFT = 1;
                int SCROLLINNG_UNDETERMINDED = 2;
                int currentScrollDirection = 2;

                private void setScrollingDirection(float positionOffset){
                    if((1-positionOffset) >= 0.5){
                        this.currentScrollDirection = SCROLLINNG_RIGHT;
                    }
                    else if((1-positionOffset) <= 0.5){
                        this.currentScrollDirection = SCROLLINNG_LEFT;
                    }
                }

                private boolean isScrollDirectionUndetermined(){
                    return currentScrollDirection == SCROLLINNG_UNDETERMINDED;
                }

                private boolean isScrollingRight(){
                    return currentScrollDirection == SCROLLINNG_RIGHT;
                }

                private boolean isScrollingLeft(){
                    return currentScrollDirection == SCROLLINNG_LEFT;
                }

                @Override
                public void onPageScrolled(int i, float v, int i1) {
                    if(isScrollDirectionUndetermined()){
                        setScrollingDirection(v);
                    }
                }

                @Override
                public void onPageSelected(int i) {
                    QuestionFragment questionFragment;
                    currentPosition = i;
                    int position = i;

                    if(i > 0){
                        if(isScrollingRight()){
                            // get previous when user scroll right
                            questionFragment = Quiz.fragmentsList.get(i-1);
                            position = i-1;
                        }
                        else if(isScrollingLeft()){
                            // get next when user scroll left
                            questionFragment = Quiz.fragmentsList.get(i+1);
                            position = i+1;
                        }
                        else{
                            questionFragment = Quiz.fragmentsList.get(position);
                        }
                    }
                    else{
                        questionFragment = Quiz.fragmentsList.get(0);
                        position = 0;
                    }

                    // logic to tell if the selected item is correct or not
                    CurrentQuestion question_state = questionFragment.getSelectedAnswer();
                    if(!questionFragment.getAlreadyVisited()){
                        Quiz.answerSheetList.set(position, question_state);
                        if(!question_state.getType().equals(Quiz.ANSWER_TYPE.NO_ANSWER)){
                            questionFragment.setAlreadyVisited(true);// change color in answer sheet
                        }
                        countCorrectAnswer();
                    }

                    if(question_state.getType() != Quiz.ANSWER_TYPE.NO_ANSWER){
                        questionFragment.showCorrectAnswer();
                        questionFragment.disableAnswer();
                    }
                }

                @Override
                public void onPageScrollStateChanged(int i) {
                    if(i == ViewPager.SCROLL_STATE_IDLE){
                        this.currentScrollDirection = SCROLLINNG_UNDETERMINDED;
                    }
                }
            });
        }
    }

    private boolean isVisitedAll(){
        for(QuestionFragment item : Quiz.fragmentsList){
            if(!item.getAlreadyVisited()) return false;
        }
        return true;
    }

    private void countCorrectAnswer() {
        // reset variable
        Quiz.right_answer_count = Quiz.wrong_answer_counnt = 0;
        for(CurrentQuestion item : Quiz.answerSheetList){
            if(item.getType() == Quiz.ANSWER_TYPE.RIGHT_ANSWER){
                Quiz.right_answer_count++;
            }
            else if(item.getType() == Quiz.ANSWER_TYPE.WRONG_ANSWER){
                Quiz.wrong_answer_counnt++;
            }
        }
    }

    // load question from the json file
    private void importQuestion(int level, int chapter){
        StringBuilder quizName = new StringBuilder();
        quizName.append("level");
        quizName.append(Integer.toString(level));
        quizName.append("chapter");
        quizName.append(Integer.toString(chapter));
        quizName.append(".json");
        String jsonString = loadJsonFromAssert(quizName.toString());
        List<Question> questionList = new ArrayList<>();

        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray questions = jsonObject.getJSONArray("questions");
            for(int i=0; i<questions.length(); ++i){
                JSONObject questionObj = questions.getJSONObject(i);

                String questionText = questionObj.getString("question");
                int id = questionObj.getInt("id");
                String answerA = questionObj.getString("answerA");
                String answerB = questionObj.getString("answerB");
                String answerC = questionObj.getString("answerC");
                String answerD = questionObj.getString("answerD");
                String correctAns = questionObj.getString("correct_answer");
                questionList.add(new Question(id, questionText, answerA, answerB, answerC, answerD, correctAns));
            }

            Quiz.questionList = questionList;

            if(Quiz.questionList.size() == 0){
                //if there is no question
                new MaterialStyledDialog.Builder(this)
                        .setTitle("There is no question")
                        .setIcon(R.drawable.lb_ic_shuffle)
                        .setDescription("We don't have any question in this" + Quiz.selectedCategory.getName() + " category")
                        .setPositiveText("OK")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                                finish();
                            }
                        }).show();
            }
            else {
                //generate answerSheet item from question
                if(Quiz.answerSheetList.size() > 0){
                    Quiz.answerSheetList.clear();
                }

                for(int i = 0; i< Quiz.questionList.size(); i++){
                    Quiz.answerSheetList.add(new CurrentQuestion(i, Quiz.ANSWER_TYPE.NO_ANSWER));
                }
            }

            Quiz.questionList = shuffleQuizDeck(Quiz.questionList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private List<Question> shuffleQuizDeck(List<Question> questionList){
        ArrayList<Question> shuffledDeck = new ArrayList<Question>();

        while (questionList.size() > 0) {
            int index = (int) (Math.random() * questionList.size());
            shuffledDeck.add(questionList.remove(index));
        }

        return shuffledDeck;
    }

    // read json file
    private String loadJsonFromAssert(String file){
        String json = "";
        try{
            InputStream inputStream = getAssets().open(file);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    // load each question as a fragment
    private void genQuestionFragmentList() {
        for(int i = 0; i< questionList.size(); ++i){
            Bundle bundle = new Bundle();
            bundle.putInt("index", i);
            QuestionFragment fragment = new QuestionFragment();
            fragment.setArguments(bundle);

            Quiz.fragmentsList.add(fragment);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return true;
    }
}
