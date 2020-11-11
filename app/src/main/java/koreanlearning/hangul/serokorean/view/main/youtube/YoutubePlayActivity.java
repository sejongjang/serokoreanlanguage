package koreanlearning.hangul.serokorean.view.main.youtube;

import android.os.Bundle;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.hangul.serokorean.R;

public class YoutubePlayActivity extends YouTubeBaseActivity {

    private static final String GOOGLE_API_KEY = "AIzaSyD2KmLaL7RJOr0DKiKvvMP1TEddjoL0sBY";

    private String VIDEO_ID = "";
    private YouTubePlayerView youTubePlayerView = null;
    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() { }

        @Override
        public void onLoaded(String s) { }

        @Override
        public void onAdStarted() { }

        @Override
        public void onVideoStarted() { }

        @Override
        public void onVideoEnded() { }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) { }
    };
    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener(){

        @Override
        public void onPlaying() { }

        @Override
        public void onPaused() { }

        @Override
        public void onStopped() { }

        @Override
        public void onBuffering(boolean b) { }

        @Override
        public void onSeekTo(int i) { }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_play);

        Bundle bundle = getIntent().getExtras();
        VIDEO_ID = bundle.getString("videoId");

        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player);
        youTubePlayerView.initialize(GOOGLE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
                youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
                youTubePlayer.setPlaybackEventListener(playbackEventListener);

                if(!wasRestored){
                    youTubePlayer.cueVideo(VIDEO_ID);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }
}
