package koreanlearning.hangul.serokorean.beginnerone.webview;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


import com.hangul.serokorean.R;
import java.util.ArrayList;

import koreanlearning.hangul.serokorean.beginnerone.quiz.QuestionActivity;


public class BeginnerOneWebView extends AppCompatActivity implements ParentRequestInterface{

    private SectionsPagerAdapter sectionsPagerAdapter;
    private CustomViewPager customViewPager;

    private int numberOfPages = 0;
    private int currentPage = 0;
    private int currentChapterNum = 0;
    private String currentChapter = "";
    private ArrayList<String> htmlFiles = new ArrayList<>();
    private int isPrevious = 0;

    public void fullScreencall() {
        if(Build.VERSION.SDK_INT < 19){
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else {
            //for higher api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fullScreencall();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_webview_test);

        //bundle gets passed in parameters when chapters' onClick from the Home
        Bundle bundle = getIntent().getExtras();
        StringBuilder stringBuilderPageNum = new StringBuilder();
        StringBuilder stringBuilderChapNum = new StringBuilder();

        //check bundle to see if it's null
        if(bundle == null){
//            textView.setText("bundle error");
        }
        //if not, stores the parameters are passed in
        else{
            //bundle is like a Map, matches key and stores the value
            numberOfPages = bundle.getInt("pages");
            currentChapter = bundle.getString("chapter");
//            isPrevious = bundle.getInt("previous");
            stringBuilderPageNum.append(bundle.getString("chapter"));
            stringBuilderPageNum.append(" number of pages:");
            stringBuilderPageNum.append(Integer.toString(bundle.getInt("pages")));

            //check the length of the string, and gets the number as a substring, 0-9
            if(currentChapter.length()<=9){
                stringBuilderChapNum.append(currentChapter.substring(8,9));
            }
            //number greater than 9
            else{
                stringBuilderChapNum.append(currentChapter.substring(8,10));
            }
            currentChapterNum = Integer.parseInt(stringBuilderChapNum.toString());
        }

        // Create the adapter that will return a fragment for each. primary sections of the activity.
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), customViewPager, this);

        // Set up the ViewPager with the sections adapter.
        customViewPager = (CustomViewPager) findViewById(R.id.container);
        customViewPager.setAdapter(sectionsPagerAdapter);
        customViewPager.setOffscreenPageLimit(2);
    }


    @Override
    public void setViewPagerStatus(Boolean b) { customViewPager.setPagingEnabled(b); }

    public static class PlaceholderFragment extends Fragment{

        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String CURRENT_CHAPTER = "current_chapter";
        private static final String NUMBER_OF_PAGES = "number_of_pages";
        private int position;
        private int currentChapterNum;
        private int numberOfPages;
        private BeginnerOneWebView activity;
        private CustomViewPager viewpager;
        private BeginnerOneWebView parentActivity;

        public static PlaceholderFragment newInstance(int position , int chapterNum, int numberOfPages) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, position);
            args.putInt(CURRENT_CHAPTER, chapterNum);
            args.putInt(NUMBER_OF_PAGES, numberOfPages);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            Bundle arguments = getArguments();
            String url="";
            String lastPage="";
            ArrayList<String> htmlFiles = new ArrayList<>();

            position = arguments.getInt(ARG_SECTION_NUMBER);
            currentChapterNum = arguments.getInt(CURRENT_CHAPTER);
            numberOfPages = arguments.getInt(NUMBER_OF_PAGES);
            parentActivity = (BeginnerOneWebView) getActivity();

            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            final CustomWebView webView = rootView.findViewById(R.id.webView);
            webView.setFragment(this);

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("file:///android_asset/level 1/chapter ");
            stringBuilder.append(Integer.toString(currentChapterNum));
            stringBuilder.append("/");

            for(int i=0; i<numberOfPages; ++i){
                htmlFiles.add(stringBuilder.toString() + Integer.toString(i) + ".html");
            }

            url = htmlFiles.get(position);
            lastPage = htmlFiles.get(numberOfPages-1);

            WebSettings settings = webView.getSettings();
            webView.setWebChromeClient(new WebChromeClient());
            webView.setScrollContainer(false);
            webView.setVerticalScrollBarEnabled(false);
            webView.setHorizontalScrollBarEnabled(false);
            webView.setWebChromeClient(new WebChromeClient(){
                @Override
                public void onPermissionRequest(final PermissionRequest request) {
                    request.grant(request.getResources());
                }
            });

            webView.setWebViewClient(new WebViewClient(){

                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl){
                    if(failingUrl.contains("chapter")){
                        //level1chapter1
                        Bundle args = new Bundle();
                        args.putInt("level", Integer.parseInt(failingUrl.substring(12,13)));
                        args.putInt("chapter", Integer.parseInt(failingUrl.substring(20,21)));
                        Intent intent = new Intent(getActivity(), QuestionActivity.class); //ChapterOneQuiz.class
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(getActivity(), "System error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    String url = request.getUrl().toString();
                    if(url.contains("chapter")){
//                        Intent intent = new Intent(getActivity(), QuizCategories.class); //ChapterOneQuiz.class
                        Bundle args = new Bundle();
                        args.putInt("level", Integer.parseInt(url.substring(5,6)));
                        args.putInt("chapter", Integer.parseInt(url.substring(13)));
                        Intent intent = new Intent(getActivity(), QuestionActivity.class); //ChapterOneQuiz.class
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                    return true;
                }
            });

            settings.setJavaScriptEnabled(true);
            settings.setBuiltInZoomControls(true);
            settings.setSupportZoom(true);
            settings.setDisplayZoomControls(false);
            settings.setLoadWithOverviewMode(true);
            settings.setUseWideViewPort(false);

            webView.loadUrl(url);
            return rootView;
        }

        public void setViewPager(boolean b) {
            parentActivity.setViewPagerStatus(b);
        }
        public void setActivity(BeginnerOneWebView activity) {
            this.activity = activity;
        }
        public void setPager(CustomViewPager viewpager) {
            this.viewpager = viewpager;
        }
        public PlaceholderFragment() { }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        CustomViewPager viewPager;
        BeginnerOneWebView activity;

        public SectionsPagerAdapter(FragmentManager fm, CustomViewPager viewPager, BeginnerOneWebView activity) {
            super(fm);
            this.viewPager=viewPager;
            this.activity=activity;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            PlaceholderFragment fragment= PlaceholderFragment.newInstance(position, currentChapterNum, numberOfPages);
            fragment.setActivity(activity);
            fragment.setPager(viewPager);
            return fragment;
        }

        @Override
        public int getCount() { return numberOfPages; }
    }
}
