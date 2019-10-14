package koreanlearning.hangul.serokorean.beginnerone.quiz.Model;

import koreanlearning.hangul.serokorean.beginnerone.quiz.common.QuizCommon;

public class CurrentQuestion {
    private int questionIndex;
    private QuizCommon.ANSWER_TYPE type;

    public CurrentQuestion(int questionIndex, QuizCommon.ANSWER_TYPE type) {
        this.questionIndex = questionIndex;
        this.type = type;
    }

    public int getQuestionIndex() {
        return questionIndex;
    }

    public void setQuestionIndex(int questionIndex) {
        this.questionIndex = questionIndex;
    }

    public QuizCommon.ANSWER_TYPE getType() {
        return type;
    }

    public void setType(QuizCommon.ANSWER_TYPE type) {
        this.type = type;
    }
}
