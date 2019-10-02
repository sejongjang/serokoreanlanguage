package koreanlearning.hangul.serokorean.beginnerone.quiz.quizInterface;

import koreanlearning.hangul.serokorean.beginnerone.quiz.Model.CurrentQuestion;

public interface IQuestion {
    CurrentQuestion getSelectedAnswer(); // get selected answer
    void showCorrectAnswer(); // bold correct answer
    void disableAnswer(); // disable all check box
    void resetQuestion(); // reset all function on questions

}
