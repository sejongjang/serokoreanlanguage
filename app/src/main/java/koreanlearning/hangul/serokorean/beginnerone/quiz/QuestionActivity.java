package koreanlearning.hangul.serokorean.beginnerone.quiz;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.hangul.serokorean.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import koreanlearning.hangul.serokorean.beginnerone.quiz.DBhelper.DBhelper;
import koreanlearning.hangul.serokorean.beginnerone.quiz.Model.CurrentQuestion;
import koreanlearning.hangul.serokorean.beginnerone.quiz.Model.Question;
import koreanlearning.hangul.serokorean.beginnerone.quiz.adapter.AnswerSheetAdapter;
import koreanlearning.hangul.serokorean.beginnerone.quiz.adapter.QuestionFragmentsAdapter;
import koreanlearning.hangul.serokorean.beginnerone.quiz.common.QuizCommon;

public class QuestionActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int time_play = QuizCommon.TOTAL_TIME;
    boolean isAnswerModeView = false;
    RecyclerView answer_sheet_view;
    AnswerSheetAdapter answerSheetAdapter;

    TextView txt_right_answer, txt_timer;
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onDestroy() {
        if(QuizCommon.countDownTimer != null){
            QuizCommon.countDownTimer.cancel();
        }
        super.onDestroy();
    }

    public void fullScreencall() {
        if(Build.VERSION.SDK_INT < 19){
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else {
            //for higher api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fullScreencall();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(QuizCommon.selectedCategory.getName());
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        
        //take questions from DB
        Bundle bundle = getIntent().getExtras();
        int level = bundle.getInt("level");
        int chapter = bundle.getInt("chapter");
//        takeQuestion();
        importQuestion(level, chapter);

        if(QuizCommon.questionList.size() > 0){

            //show textView right answer
            txt_right_answer = findViewById(R.id.txt_question_right);
            txt_right_answer.setVisibility(View.VISIBLE);
            txt_right_answer.setText(new StringBuilder(String.format("%d/%d", QuizCommon.right_answer_count, QuizCommon.questionList.size())));

            //view
            answer_sheet_view = findViewById(R.id.grid_answer);
            answer_sheet_view.setHasFixedSize(true);
            if(QuizCommon.questionList.size() > 5){ // if question list have size > 5, we will sperate 2 rows
                answer_sheet_view.setLayoutManager(new GridLayoutManager(this, QuizCommon.questionList.size()/2));
            }
            answerSheetAdapter = new AnswerSheetAdapter(this, QuizCommon.answerSheetList);
            answer_sheet_view.setAdapter(answerSheetAdapter);
            
            viewPager = findViewById(R.id.quizViewpager);
            tabLayout = findViewById(R.id.sliding_tabs);
            
            genFragmentList();
            QuestionFragmentsAdapter questionFragmentsAdapter = new QuestionFragmentsAdapter(getSupportFragmentManager(), this, QuizCommon.fragmentsList);
            viewPager.setAdapter(questionFragmentsAdapter);

            tabLayout.setupWithViewPager(viewPager);

            // event
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
                    int position = 0;
                    if(i > 0){
                        if(isScrollingRight()){
                            // get previous when user scroll right
                            questionFragment = QuizCommon.fragmentsList.get(i-1);
                            position = i-1;
                        }
                        else if(isScrollingLeft()){
                            // get next when user scroll left
                            questionFragment = QuizCommon.fragmentsList.get(i+1);
                            position = i+1;
                        }
                        else{
                            questionFragment = QuizCommon.fragmentsList.get(position);
                        }
                    }
                    else{
                        questionFragment = QuizCommon.fragmentsList.get(0);
                        position = 0;
                    }

                    //if you want to show correct answer, just call function here
                    CurrentQuestion question_state = questionFragment.getSelectedAnswer();
                    QuizCommon.answerSheetList.set(position, question_state);
                    answerSheetAdapter.notifyDataSetChanged(); // change color in answer sheet

                    countCorrectAnswer();
                    txt_right_answer.setText(new StringBuilder(String.format("%d", QuizCommon.right_answer_count))
                            .append("/")
                            .append(String.format("%d", QuizCommon.questionList.size())).toString());

                    if(question_state.getType() != QuizCommon.ANSWER_TYPE.NO_ANSWER){
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

    private void countCorrectAnswer() {
        // reset variable
        QuizCommon.right_answer_count = QuizCommon.wrong_answer_counnt = 0;
        for(CurrentQuestion item : QuizCommon.answerSheetList){
            if(item.getType() == QuizCommon.ANSWER_TYPE.RIGHT_ANSWER){
                QuizCommon.right_answer_count++;
            }
            else if(item.getType() == QuizCommon.ANSWER_TYPE.WRONG_ANSWER){
                QuizCommon.wrong_answer_counnt++;
            }
        }
    }

    private void genFragmentList() {
        for(int i = 0; i< QuizCommon.questionList.size(); ++i){
            Bundle bundle = new Bundle();
            bundle.putInt("index", i);
            QuestionFragment fragment = new QuestionFragment();
            fragment.setArguments(bundle);

            QuizCommon.fragmentsList.add(fragment);
        }
    }

    private void importQuestion(int level, int chapter){
        StringBuilder quizName = new StringBuilder();
        quizName.append("level");
        quizName.append(Integer.toString(level));
        quizName.append("chapter");
        quizName.append(Integer.toString(chapter));
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

            QuizCommon.questionList = questionList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

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

    private void takeQuestion() {
        QuizCommon.questionList = DBhelper.getInstance(this).getQuestionByCategory(QuizCommon.selectedCategory.getId());

        if(QuizCommon.questionList.size() == 0){
            //if there is no question
            new MaterialStyledDialog.Builder(this)
                    .setTitle("There is no question")
                    .setIcon(R.drawable.lb_ic_shuffle)
                    .setDescription("We don't have any question in this" + QuizCommon.selectedCategory.getName() + " category")
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

            if(QuizCommon.answerSheetList.size() > 0){
                QuizCommon.answerSheetList.clear();
            }

            for(int i = 0; i< QuizCommon.questionList.size(); i++){
                QuizCommon.answerSheetList.add(new CurrentQuestion(i, QuizCommon.ANSWER_TYPE.NO_ANSWER));
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.question, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
