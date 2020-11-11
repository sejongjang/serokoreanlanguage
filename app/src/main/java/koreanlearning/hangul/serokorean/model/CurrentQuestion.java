package koreanlearning.hangul.serokorean.model;

public class CurrentQuestion {
    private int questionIndex;
    private Quiz.ANSWER_TYPE type;

    public CurrentQuestion(int questionIndex, Quiz.ANSWER_TYPE type) {
        this.questionIndex = questionIndex;
        this.type = type;
    }

    public int getQuestionIndex() {
        return questionIndex;
    }

    public void setQuestionIndex(int questionIndex) {
        this.questionIndex = questionIndex;
    }

    public Quiz.ANSWER_TYPE getType() {
        return type;
    }

    public void setType(Quiz.ANSWER_TYPE type) {
        this.type = type;
    }
}
