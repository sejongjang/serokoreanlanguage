package koreanlearning.hangul.serokorean.bottomNavigation;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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

import koreanlearning.hangul.serokorean.model.YoutubeVideoModel;

import static com.facebook.FacebookSdk.getApplicationContext;

public class FAQ extends Fragment {

    //AIzaSyD2KmLaL7RJOr0DKiKvvMP1TEddjoL0sBY
    private static final String YOUTUBE_API_KEY = "AIzaSyD2KmLaL7RJOr0DKiKvvMP1TEddjoL0sBY";
    private static final String CHANNEL_ID = "UC4Oqg2xJLbOkGnTpn68pErA";
    private static final String CHANNEL_GET_URL = "https://www.googleapis.com/youtube/v3/search?key=" + YOUTUBE_API_KEY + "&channelId=" + CHANNEL_ID + "&part=snippet,id&order=date&maxResults=20";

    private RecyclerView videoRecyclerview;
    private VideoPostAdapter adapter;
    private ArrayList<YoutubeVideoModel> youtubeList = new ArrayList<>();
//    private String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&channelid="+ CHANNEL_ID + "&key=" + YOUTUBE_API_KEY;


    public FAQ() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faq, container, false);
        videoRecyclerview = (RecyclerView) view.findViewById(R.id.youtube_recyclerview);
        videoRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new VideoPostAdapter(getActivity(), youtubeList);
        videoRecyclerview.setAdapter(adapter);
        new RequestYoutubeAPI().execute();
//        displayVideos();

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
            if(response != null){
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    Log.e("resonse", jsonObject.toString());
                    System.out.println(jsonObject.toString());
                    youtubeList = parseVideoListFromResponse(jsonObject);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
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

    private void displayVideos() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, CHANNEL_GET_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("items");

                    for(int i=0; i<jsonArray.length(); ++i){
                        JSONObject item = jsonArray.getJSONObject(i);
                        JSONObject jsonVideoId = item.getJSONObject("id");
                        JSONObject snippet = item.getJSONObject("snippet");

                        JSONObject jsonDefault = snippet.getJSONObject("thumbnails").getJSONObject("medium");
                        String video_id = jsonVideoId.getString("videoId");

                        YoutubeVideoModel model = new YoutubeVideoModel();
                        model.setVideoId(video_id);
                        model.setTitle(snippet.getString("title"));
                        model.setDescription(snippet.getString("description"));

                        youtubeList.add(model);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                videoRecyclerview.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);
    }
}