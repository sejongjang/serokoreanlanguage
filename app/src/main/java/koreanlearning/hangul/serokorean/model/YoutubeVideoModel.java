package koreanlearning.hangul.serokorean.model;

public class YoutubeVideoModel {
    private String videoId;
    private String title;
    private String description;
    private String thumbnail;
    private String date;

    public YoutubeVideoModel() {
    }

    public YoutubeVideoModel(String title, String description, String thumbnail, String date, String videoId) {
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.date = date;
        this.videoId = videoId;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
