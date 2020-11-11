package koreanlearning.hangul.serokorean.model;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import koreanlearning.hangul.serokorean.view.main.quiz.QuestionFragment;

public class Quiz {
    public static Category selectedCategory = new Category();
    public static List<Question> questionList = new ArrayList<>();
    public static List<CurrentQuestion> answerSheetList = new ArrayList<>();
    public static int right_answer_count = 0;
    public static int wrong_answer_counnt = 0;
    public static ArrayList<QuestionFragment> fragmentsList = new ArrayList<>();
    public static TreeSet<String> selected_values = new TreeSet<>();
    public List<Question> wrongQuestionList = new ArrayList<>();

    public enum ANSWER_TYPE{
        NO_ANSWER,
        WRONG_ANSWER,
        RIGHT_ANSWER
    }

    public void countCorrectAnswer(){
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




}
