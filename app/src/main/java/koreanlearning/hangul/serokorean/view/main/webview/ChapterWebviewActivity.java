package koreanlearning.hangul.serokorean.view.main.webview;

import android.content.Intent;
import android.graphics.Bitmap;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.hangul.serokorean.R;
import java.util.ArrayList;

import koreanlearning.hangul.serokorean.view.main.quiz.QuestionActivity;
import koreanlearning.hangul.serokorean.utility.ChapterUtil;
import koreanlearning.hangul.serokorean.utility.FullScreenCall;

public class ChapterWebviewActivity extends AppCompatActivity implements ParentRequestInterface{

    private SectionsPagerAdapter sectionsPagerAdapter;
    private CustomViewPager customViewPager;
    private int numberOfPages = 0;
    private int currentChapterNum = 0;
    private String currentChapter = "";
    private boolean isFromNext = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FullScreenCall.fullScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_webview_test);

        //bundle gets passed in parameters when chapters' onClick from the Home
        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            // handles bundle error here
        }
        // if not, stores the parameters are passed in
        else{
            numberOfPages = bundle.getInt("pages");
            currentChapter = bundle.getString("chapter");
            isFromNext = bundle.getBoolean("isFromNext");

            StringBuilder chapNum = new StringBuilder();

            //check the length of the string, and gets the number as a substring, 0-9
            if(currentChapter.length()<=9){
                chapNum.append(currentChapter.substring(8,9));
            }
            //number greater than 9
            else{
                chapNum.append(currentChapter.substring(8,10));
            }
            currentChapterNum = Integer.parseInt(chapNum.toString());
        }

        // Create the adapter that will return a fragment for each. primary sections of the activity.
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), customViewPager, this);

        // Set up the ViewPager with the sections adapter.
        customViewPager = findViewById(R.id.container);
        customViewPager.setAdapter(sectionsPagerAdapter);

        // detect to see if this request to get to previous chapter, if so current index should be the last page of the chapter.
        if(isFromNext) {
            int index = ChapterUtil.findFirstPageOfChapter(currentChapterNum+1);
            customViewPager.setCurrentItem(index-1);
        }

        // helps to scroll beyond edges
        customViewPager.setOnSwipeOutListener(new CustomViewPager.OnSwipeOutListener() {
            private int nextChapterIndex = currentChapterNum + 1;
            private int previousChapterIndex = currentChapterNum - 1;

            // first to the last page of last chapter
            @Override
            public void onSwipeOutAtStart() {
                if(previousChapterIndex >= 0){
                    Intent intent = new Intent(ChapterWebviewActivity.this, ChapterWebviewActivity.class);
                    int numberOfPages = ChapterUtil.detectTheNumberOfPages(Integer.toString(previousChapterIndex));
                    intent.putExtra("chapter", "chapter " + previousChapterIndex);
                    intent.putExtra("pages", numberOfPages);
                    intent.putExtra("isFromNext", true);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
                //Toast.makeText(getApplicationContext(), "try to swipe right", Toast.LENGTH_SHORT).show();
            }

            // last page to the first page of next chapter
            @Override
            public void onSwipeOutAtEnd() {
                if(nextChapterIndex <= 30){
                    Intent intent = new Intent(ChapterWebviewActivity.this, ChapterWebviewActivity.class);
                    int numberOfPages = ChapterUtil.detectTheNumberOfPages(Integer.toString(nextChapterIndex));
                    intent.putExtra("chapter", "chapter " + nextChapterIndex);
                    intent.putExtra("pages", numberOfPages);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                //Toast.makeText(getApplicationContext(), "try to swipe left", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void setViewPagerStatus(Boolean b) { customViewPager.setPagingEnabled(b); }

    public static class PlaceholderFragment extends Fragment{

        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String CURRENT_CHAPTER = "current_chapter";
        private static final String NUMBER_OF_PAGES = "number_of_pages";
        private ChapterWebviewActivity parentActivity;
        private ProgressBar webviewProgressBar;

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
            int position = arguments.getInt(ARG_SECTION_NUMBER);
            int currentChapterNum = arguments.getInt(CURRENT_CHAPTER);
            int numberOfPages = arguments.getInt(NUMBER_OF_PAGES);
            parentActivity = (ChapterWebviewActivity) getActivity();

            // initialize webview and progressBar
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            final CustomWebView webView = rootView.findViewById(R.id.webView);
            webviewProgressBar = rootView.findViewById(R.id.webview_progress_bar);
            webView.setFragment(this);

            // load all the level 1 html pages for the transition also between chapters
            ArrayList<String> htmlFiles = loadAllHTML();
            int index = ChapterUtil.findFirstPageOfChapter(currentChapterNum);
            String url = htmlFiles.get(position + index);

            // change webview setting and javascript setting here
            WebSettings settings = webView.getSettings();
            webviewClientSettings(webView, settings);
            webView.loadUrl(url);

            return rootView;
        }

        private ArrayList<String> loadAllHTML(){
            ArrayList<String> htmlPaths = new ArrayList<>();

            for(int i=0; i<ChapterUtil.getNumOfChapters(); ++i){
                int numOfPages = ChapterUtil.detectTheNumberOfPages(Integer.toString(i));

                for(int j=0; j<numOfPages; ++j){
                    StringBuilder htmlPath = new StringBuilder();
                    htmlPath.append("file:///android_asset/level 1/");
                    htmlPath.append(i);
                    htmlPath.append("-");
                    htmlPath.append(j);
                    htmlPath.append(".html");
                    htmlPaths.add(htmlPath.toString());
                }
            }
            return htmlPaths;
        }

        private void webviewClientSettings(WebView webView, WebSettings settings){
            settings.setAllowFileAccessFromFileURLs(true);
            settings.setAllowUniversalAccessFromFileURLs(true);
            settings.setMediaPlaybackRequiresUserGesture(false);
            settings.setUseWideViewPort(true);
            settings.setDomStorageEnabled(true);

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

            // this is how we call quiz activity from javascript
            webView.setWebViewClient(new WebViewClient(){
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    webviewProgressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    webviewProgressBar.setVisibility(View.GONE);
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    String url = request.getUrl().toString();
                    if(url.contains("chapter")){
                        Intent intent = new Intent(getActivity(), QuestionActivity.class); //ChapterOneQuiz.class
                        intent.putExtra("level", Integer.parseInt(url.substring(12,13)));
                        intent.putExtra("chapter", Integer.parseInt(url.substring(20,21)));
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                    else if (url != null && (url.startsWith("http://") || url.startsWith("https://"))) {
                        view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                        return true;
                    }
                    return true;
                }

                // it takes care of url load fail (web page not found error)
                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    super.onReceivedError(view, errorCode, description, failingUrl);
                    if(failingUrl.contains("chapter")){
                        view.stopLoading();
                        Intent intent = new Intent(getActivity(), QuestionActivity.class); //ChapterOneQuiz.class
                        intent.putExtra("level", Integer.parseInt(failingUrl.substring(12,13)));
                        intent.putExtra("chapter", Integer.parseInt(failingUrl.substring(20,21)));
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }
            });
            webView.setBackgroundColor(383838);

            settings.setJavaScriptEnabled(true);
            settings.setAllowFileAccess(true);
            settings.setBuiltInZoomControls(true);
            settings.setSupportZoom(true);
            settings.setDisplayZoomControls(false);
            settings.setLoadWithOverviewMode(true);
            settings.setUseWideViewPort(false);
        }
        public void setViewPager(boolean b) {
            parentActivity.setViewPagerStatus(b);
        }
        public void setActivity(ChapterWebviewActivity activity) { }
        public void setPager(CustomViewPager viewpager) { }
        public PlaceholderFragment() { }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private CustomViewPager viewPager;
        private ChapterWebviewActivity activity;

        public SectionsPagerAdapter(FragmentManager fm, CustomViewPager viewPager, ChapterWebviewActivity activity) {
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

