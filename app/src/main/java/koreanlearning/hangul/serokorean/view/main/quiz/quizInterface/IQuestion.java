package koreanlearning.hangul.serokorean.view.main.quiz.quizInterface;

import koreanlearning.hangul.serokorean.model.CurrentQuestion;

public interface IQuestion {
    CurrentQuestion getSelectedAnswer(); // get selected answer
    void showCorrectAnswer(); // bold correct answer
    void disableAnswer(); // disable all check box
    void resetQuestion(); // reset all function on questions

}
