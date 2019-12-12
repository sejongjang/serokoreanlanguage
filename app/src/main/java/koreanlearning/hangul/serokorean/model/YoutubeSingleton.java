package koreanlearning.hangul.serokorean.model;

import java.util.ArrayList;

public class YoutubeSingleton {
    private static YoutubeSingleton singleton_instance;
    private ArrayList<YoutubeVideoModel> youtubeList = new ArrayList<>();

    private YoutubeSingleton(){

    }

    public static YoutubeSingleton getInstance(){
        if(singleton_instance == null){
            singleton_instance = new YoutubeSingleton();
        }
        return singleton_instance;
    }

    public ArrayList<YoutubeVideoModel> getYoutubeList() {
        return youtubeList;
    }

    public void setYoutubeList(ArrayList<YoutubeVideoModel> youtubeList) {
        this.youtubeList = youtubeList;
    }
}
