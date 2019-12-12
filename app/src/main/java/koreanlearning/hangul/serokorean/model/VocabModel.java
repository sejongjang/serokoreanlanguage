package koreanlearning.hangul.serokorean.model;

import android.net.Uri;

import java.io.InputStream;

public class VocabModel {
    private String english;
    private String korean;
    private String description;
    private int audioSource;

//    public VocabModel(String english, String korean, String description) {
//        this.english = english;
//        this.korean = korean;
//        this.description = description;
//    }

    public VocabModel(String english, String korean, String description, int audioSource) {
        this.english = english;
        this.korean = korean;
        this.description = description;
        this.audioSource = audioSource;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getKorean() {
        return korean;
    }

    public void setKorean(String korean) {
        this.korean = korean;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAudioSource() {
        return audioSource;
    }

    public void setAudioSource(int audioSource) {
        this.audioSource = audioSource;
    }
}
