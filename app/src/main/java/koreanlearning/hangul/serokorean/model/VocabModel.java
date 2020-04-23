package koreanlearning.hangul.serokorean.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@IgnoreExtraProperties
public class VocabModel {
    public String id;
    public String english;
    public String korean;
    public String description;
    public int audioSource;

    public long last_time_reivewed_time_stamp;
    public int num_of_times_reviewd;
    public List<String> example_sentences;

    public boolean isAdded = false;

    public VocabModel() { }

    public VocabModel(String english, String korean, String description, int audioSource) {
        this.english = english;
        this.korean = korean;
        this.description = description;
        this.audioSource = audioSource;
    }

    public VocabModel(String english, String korean, String description, long last_time_reivewed_time_stamp, int num_of_times_reviewd, boolean isAdded) {
        this.english = english;
        this.korean = korean;
        this.description = description;
        this.last_time_reivewed_time_stamp = last_time_reivewed_time_stamp;
        this.num_of_times_reviewd = num_of_times_reviewd;
        this.isAdded = isAdded;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("english", english);
        result.put("korean", korean);
        result.put("description", description);
        result.put("last_time_reivewed_time_stamp", last_time_reivewed_time_stamp);
        result.put("num_of_times_reviewd", num_of_times_reviewd);
        result.put("isAdded", isAdded);

        return result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public long getLast_time_reivewed_time_stamp() {
        return last_time_reivewed_time_stamp;
    }

    public void setLast_time_reivewed_time_stamp(long last_time_reivewed_time_stamp) {
        this.last_time_reivewed_time_stamp = last_time_reivewed_time_stamp;
    }

    public int getNum_of_times_reviewd() {
        return num_of_times_reviewd;
    }

    public void setNum_of_times_reviewd(int num_of_times_reviewd) {
        this.num_of_times_reviewd = num_of_times_reviewd;
    }

    public List<String> getExample_sentences() {
        return example_sentences;
    }

    public void setExample_sentences(List<String> example_sentences) {
        this.example_sentences = example_sentences;
    }

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }
}
