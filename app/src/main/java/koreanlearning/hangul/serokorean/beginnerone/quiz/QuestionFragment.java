package koreanlearning.hangul.serokorean.beginnerone.quiz;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hangul.serokorean.R;

import koreanlearning.hangul.serokorean.beginnerone.quiz.Model.CurrentQuestion;
import koreanlearning.hangul.serokorean.beginnerone.quiz.Model.Question;
import koreanlearning.hangul.serokorean.beginnerone.quiz.common.QuizCommon;
import koreanlearning.hangul.serokorean.beginnerone.quiz.quizInterface.IQuestion;

public class QuestionFragment extends Fragment implements IQuestion {

    private TextView txt_question_text;
    private CheckBox ckbA, ckbB, ckbC, ckbD;
    private FrameLayout layout_image;
    private ProgressBar progressBar;
    private Boolean isAlreadyVisited = false;
    private int TEXT_SIZE = 20;

    private Question question;
    private int questionIndex = -1;


    public QuestionFragment() {
        // Required empty public constructor
    }

    public static QuestionFragment newInstance(String param1, String param2) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public Boolean getAlreadyVisited() {
        return isAlreadyVisited;
    }

    public void setAlreadyVisited(Boolean alreadyVisited) {
        isAlreadyVisited = alreadyVisited;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View itemView = inflater.inflate(R.layout.fragment_question, container, false);

        //get question
        questionIndex = getArguments().getInt("index", -1);
        question = QuizCommon.questionList.get(questionIndex);

        if(question != null){

            layout_image = itemView.findViewById(R.id.layout_image);
            progressBar = itemView.findViewById(R.id.progress_bar);

            if(question.isImageQuestion()){
                ImageView img_question = itemView.findViewById(R.id.img_question);
                //TODO: Picasso.get().load(question.getQuestionImage()).into(img_question, new Callback())
            }
            else{
                layout_image.setVisibility(View.GONE);
            }


            //view
            txt_question_text = itemView.findViewById(R.id.txt_question_text);
            txt_question_text.setText(question.getQuestionText());

            ckbA = itemView.findViewById(R.id.ckbA);
            ckbA.setText(question.getAnswerA());
            ckbA.setTextSize(TEXT_SIZE);
            ckbA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        QuizCommon.selected_values.add(ckbA.getText().toString());
                        if(question.getAnswerA().equals(question.getCorrectAnswer())){
                            // show animation for the correct answer
                            Toast.makeText(getContext(), "correct", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            // show animation for the correct answer
                            Toast.makeText(getContext(), "wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        QuizCommon.selected_values.remove(ckbA.getText().toString());
                    }
                }
            });

            ckbB = itemView.findViewById(R.id.ckbB);
            ckbB.setText(question.getAnswerB());
            ckbB.setTextSize(TEXT_SIZE);
            ckbB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        QuizCommon.selected_values.add(ckbB.getText().toString());
                        if(question.getAnswerB().equals(question.getCorrectAnswer())){
                            // show animation for the correct answer
                            Toast.makeText(getContext(), "correct", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            // show animation for the correct answer
                            Toast.makeText(getContext(), "wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        QuizCommon.selected_values.remove(ckbB.getText().toString());
                    }
                }
            });

            ckbC = itemView.findViewById(R.id.ckbC);
            ckbC.setText(question.getAnswerC());
            ckbC.setTextSize(TEXT_SIZE);
            ckbC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        QuizCommon.selected_values.add(ckbC.getText().toString());
                        if(question.getAnswerC().equals(question.getCorrectAnswer())){
                            // show animation for the correct answer
                            Toast.makeText(getContext(), "correct", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            // show animation for the correct answer
                            Toast.makeText(getContext(), "wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        QuizCommon.selected_values.remove(ckbC.getText().toString());
                    }
                }
            });

            ckbD = itemView.findViewById(R.id.ckbD);
            ckbD.setText(question.getAnswerD());
            ckbD.setTextSize(TEXT_SIZE);
            ckbD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        QuizCommon.selected_values.add(ckbD.getText().toString());
                        if(question.getAnswerD().equals(question.getCorrectAnswer())){
                            // show animation for the correct answer
                            Toast.makeText(getContext(), "correct", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            // show animation for the correct answer
                            Toast.makeText(getContext(), "wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        QuizCommon.selected_values.remove(ckbD.getText().toString());
                    }
                }
            });
        }

        // Inflate the layout for this fragment
        return itemView;
    }

    @Override
    public CurrentQuestion getSelectedAnswer() {
        // it will return state of answer (right or wrong)
        CurrentQuestion currentQuestion = new CurrentQuestion(questionIndex, QuizCommon.ANSWER_TYPE.NO_ANSWER);
        StringBuilder result = new StringBuilder();
        if(QuizCommon.selected_values.size() > 1){
            // multiple choices' answer will be array
            // ex. arr[0] = A. New York
            // ex. arr[1] = B. Paris

            Object[] arrayAnswer = QuizCommon.selected_values.toArray();
            for(int i=0; i<arrayAnswer.length; ++i){
                if(i < arrayAnswer.length -1){
                    // Take first letter of the answer
                    // ex. A. New York => only take A
                    result.append(new StringBuilder(((String)arrayAnswer[i]).substring(0,1)).append(","));
                }
                else{
                    result.append(new StringBuilder((String)arrayAnswer[i]).substring(0,1));
                }
            }
        }
        else if(QuizCommon.selected_values.size() == 1){
            Object[] arrayAnswer = QuizCommon.selected_values.toArray();
            result.append((String)arrayAnswer[0]).substring(0,1);
        }

        if(question != null){
            // compare correctAnswer with user answer
            if(!TextUtils.isEmpty(result)){
                if(result.toString().equals(question.getCorrectAnswer())){
                    currentQuestion.setType(QuizCommon.ANSWER_TYPE.RIGHT_ANSWER);
                }
                else{
                    currentQuestion.setType(QuizCommon.ANSWER_TYPE.WRONG_ANSWER);
                }
            }
            else{
                currentQuestion.setType(QuizCommon.ANSWER_TYPE.NO_ANSWER);
            }
        }
        else{
            Toast.makeText(getContext(), "Cannot get quiz", Toast.LENGTH_SHORT).show();
        }
        // always clear selected_value when it's done
        QuizCommon.selected_values.clear();

        return currentQuestion;
    }

    @Override
    public void showCorrectAnswer() {
        // bold correct answer
        String[] correctAnswer = question.getCorrectAnswer().split(",");
        for(String answer : correctAnswer){
            if(answer.equals("A")){
                ckbA.setTypeface(null, Typeface.BOLD);
                ckbA.setTextColor(Color.RED);
            }
            else if(answer.equals("B")){
                ckbB.setTypeface(null, Typeface.BOLD);
                ckbB.setTextColor(Color.RED);
            }
            else if(answer.equals("C")){
                ckbC.setTypeface(null, Typeface.BOLD);
                ckbC.setTextColor(Color.RED);
            }
            else if(answer.equals("D")){
                ckbD.setTypeface(null, Typeface.BOLD);
                ckbD.setTextColor(Color.RED);
            }
        }
    }

    @Override
    public void disableAnswer() {
        ckbA.setEnabled(false);
        ckbB.setEnabled(false);
        ckbC.setEnabled(false);
        ckbD.setEnabled(false);
    }

    @Override
    public void resetQuestion() {

        //enable checkbox
        ckbA.setEnabled(true);
        ckbB.setEnabled(true);
        ckbC.setEnabled(true);
        ckbD.setEnabled(true);

        //remove all selected
        ckbA.setChecked(false);
        ckbB.setChecked(false);
        ckbC.setChecked(false);
        ckbD.setChecked(false);

        //remove all bold on text
        ckbA.setTypeface(null, Typeface.NORMAL);
        ckbA.setTextColor(Color.BLACK);
        ckbB.setTypeface(null, Typeface.NORMAL);
        ckbB.setTextColor(Color.BLACK);
        ckbC.setTypeface(null, Typeface.NORMAL);
        ckbC.setTextColor(Color.BLACK);
        ckbD.setTypeface(null, Typeface.NORMAL);
        ckbD.setTextColor(Color.BLACK);
    }
}
