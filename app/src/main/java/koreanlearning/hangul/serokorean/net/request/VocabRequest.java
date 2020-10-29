package koreanlearning.hangul.serokorean.net.request;

import koreanlearning.hangul.serokorean.model.Vocab;

public class VocabRequest {
    private int userID;
    private int vocabID;
    private Vocab vocab;

    public VocabRequest() { }

    public VocabRequest(int userID) {
        this.userID = userID;
    }

    public VocabRequest(int userID, int vocabID) {
        this.userID = userID;
        this.vocabID = vocabID;
    }

    public VocabRequest(int userID, Vocab vocab){
        this.userID = userID;
        this.vocab = vocab;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getVocabID() {
        return vocabID;
    }

    public void setVocabID(int vocabID) {
        this.vocabID = vocabID;
    }

    public Vocab getVocab() {
        return vocab;
    }

    public void setVocab(Vocab vocab) {
        this.vocab = vocab;
    }
}
