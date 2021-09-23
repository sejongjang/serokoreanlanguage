package koreanlearning.hangul.serokorean.view.main.navigation;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hangul.serokorean.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import koreanlearning.hangul.serokorean.model.YoutubeSingleton;
import koreanlearning.hangul.serokorean.model.YoutubeVideoModel;
import koreanlearning.hangul.serokorean.view.main.youtube.VideoPostAdapter;

public class FAQFragment extends Fragment {

//    private static final String YOUTUBE_API_KEY = "AIzaSyD2KmLaL7RJOr0DKiKvvMP1TEddjoL0sBY"; //sero google developer console
    private static final String YOUTUBE_API_KEY = "AIzaSyC3Hnr6eJ6ECXNFz4Gn9zXu3C5CcoxMNrc"; //temp google developer console
    private static final String CHANNEL_ID = "UCKIicR8VM3HczwmJM9UxtTw";
    private static final String CHANNEL_GET_URL = "https://www.googleapis.com/youtube/v3/search?key=" + YOUTUBE_API_KEY + "&channelId=" + CHANNEL_ID + "&part=snippet,id&order=date&maxResults=20";

    private RecyclerView videoRecyclerview;
    private VideoPostAdapter adapter;
    private ArrayList<YoutubeVideoModel> youtubeList = new ArrayList<>();

    private ProgressBar waitingSpinner;
    private TextView youtube_api_error_message;
    private YoutubeSingleton youtubeSingleton = YoutubeSingleton.getInstance();

    public FAQFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faq, container, false);
        videoRecyclerview = (RecyclerView) view.findViewById(R.id.youtube_recyclerview);
        videoRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        waitingSpinner = view.findViewById(R.id.youtube_waiting_spinner);

        // check singleton class to see if it still store youtube api response, so it does not make other api call to get same result.
        if(youtubeSingleton.getYoutubeList().size() != 0){
            waitingSpinner.setVisibility(View.GONE);
            adapter = new VideoPostAdapter(getActivity(), youtubeSingleton.getYoutubeList());
        }
        // if the singleton class does not contains response, then set up empty list.
        else{
            waitingSpinner.setVisibility(View.VISIBLE);
            adapter = new VideoPostAdapter(getActivity(), youtubeList);
        }

        videoRecyclerview.setAdapter(adapter);
        youtube_api_error_message = view.findViewById(R.id.youtube_api_error_message);

        // request only if the list is empty, otherwise it will request every time user swipe on to the video fragment
        if(youtubeSingleton.getYoutubeList().size() == 0){
            new RequestYoutubeAPI().execute();
        }

        return view;
    }

    private class RequestYoutubeAPI extends AsyncTask<Void, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(CHANNEL_GET_URL);

            Log.e("URL", CHANNEL_GET_URL);

            HttpResponse response = null;
            try{
                response = httpClient.execute(httpGet);
                HttpEntity httpEntity = response.getEntity();
                String json = EntityUtils.toString(httpEntity);
                return json;

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);

            // parse response as a JSON, if there is an error print out and give user solution.
            if(response != null){
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    Log.e("resonse", jsonObject.toString());
                    System.out.println(jsonObject.toString());

                    String errorResponse = doesResponseHasAnError(jsonObject);
                    if(!errorResponse.equals("no error")){
                        youtube_api_error_message.setText(errorResponse);
                        youtube_api_error_message.setVisibility(View.VISIBLE);
                    }

                    youtubeList = parseVideoListFromResponse(jsonObject);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                waitingSpinner.setVisibility(View.GONE);
            }
        }
    }

    private String doesResponseHasAnError(JSONObject jsonObject){
        if(jsonObject.has("error")) {
                try {
                    JSONObject error = jsonObject.getJSONObject("error");

                    if(error.has("errors")){
                        JSONArray errorsArray = error.getJSONArray("errors");

                        for (int i = 0; i < errorsArray.length(); ++i) {
                            JSONObject errors = errorsArray.getJSONObject(i);

                            if (errors.has("reason")) {
                                return errors.getString("reason");
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

        }
        return "no error";
    }

    private ArrayList<YoutubeVideoModel> parseVideoListFromResponse(JSONObject jsonObject){
        ArrayList<YoutubeVideoModel> mList = new ArrayList<>();

        try {
            if(jsonObject.has("items")){
                JSONArray jsonArray = jsonObject.getJSONArray("items");

                for(int i=0; i<jsonArray.length(); ++i){
                    JSONObject json = jsonArray.getJSONObject(i);

                    if(json.has("id")){
                        JSONObject jsonID = json.getJSONObject("id");
                        if(jsonID.has("kind")){
                            if(jsonID.getString("kind").equals("youtube#video")){
                                // get data from snippet
                                String videoId = jsonID.getString("videoId");

                                JSONObject snippet = json.getJSONObject("snippet");
                                String title = snippet.getString("title");
                                String description = snippet.getString("description");
                                String date = snippet.getString("publishedAt");
                                String thumbnailURL = snippet.getJSONObject("thumbnails").getJSONObject("high").getString("url");

                                YoutubeVideoModel youtubeVideoModel = new YoutubeVideoModel(title, description, thumbnailURL, date, videoId);
                                youtubeList.add(youtubeVideoModel);
                                youtubeSingleton.getYoutubeList().add(youtubeVideoModel);
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return mList;
    }
}