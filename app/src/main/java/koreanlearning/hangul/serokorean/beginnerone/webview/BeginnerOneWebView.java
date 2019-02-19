package koreanlearning.hangul.serokorean.beginnerone.webview;


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
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;


import com.hangul.serokorean.R;

import java.util.ArrayList;



public class BeginnerOneWebView extends AppCompatActivity implements ParentRequestInterface{

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private CustomViewPager mViewPager;

    private int numberOfPages = 0;
    private int currentPage = 0;
    private int currentChapterNum = 0;
    private String currentChapter = "";
    private ArrayList<String> htmlFiles = new ArrayList<>();
    private int isPrevious = 0;

    public void FullScreencall() {
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
        FullScreencall();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_webview_test);

        Bundle bundle = getIntent().getExtras();
        StringBuilder stringBuilderPageNum = new StringBuilder();
        StringBuilder stringBuilderChapNum = new StringBuilder();

        if(bundle == null){
//            textView.setText("bundle error");
        }
        else{
            numberOfPages = bundle.getInt("pages");
            currentChapter = bundle.getString("chapter");
            isPrevious = bundle.getInt("previous");
            stringBuilderPageNum.append(bundle.getString("chapter"));
            stringBuilderPageNum.append(" number of pages:");
            stringBuilderPageNum.append(Integer.toString(bundle.getInt("pages")));

            if(currentChapter.length()<=9){
                stringBuilderChapNum.append(currentChapter.substring(8,9));
            }
            else{
                stringBuilderChapNum.append(currentChapter.substring(8,10));
            }
            currentChapterNum = Integer.parseInt(stringBuilderChapNum.toString());
//            currentChapterNum = Integer.valueOf(currentChapter.charAt(8) - '0');
        }



        // Create the adapter that will return a fragment for each. primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), mViewPager, this);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (CustomViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
    }


    @Override
    public void setViewPagerStatus(Boolean b) { mViewPager.setPagingEnabled(b); }

    public static class PlaceholderFragment extends Fragment{

        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String CURRENT_CHAPTER = "current_chapter";
        private static final String NUMBER_OF_PAGES = "number_of_pages";
        private int position;
        private int currentChapterNum;
        private int numberOfPages;
        BeginnerOneWebView activity;
        CustomViewPager viewpager;
        BeginnerOneWebView parentActivity;

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
            ArrayList<String> htmlFiles = new ArrayList<>();

            position = arguments.getInt(ARG_SECTION_NUMBER);
            currentChapterNum = arguments.getInt(CURRENT_CHAPTER);
            numberOfPages = arguments.getInt(NUMBER_OF_PAGES);
            parentActivity = (BeginnerOneWebView) getActivity();

            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            CustomWebView webView = rootView.findViewById(R.id.webView);
            webView.setFragment(this);

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("file:///android_asset/level 1/chapter ");
            stringBuilder.append(Integer.toString(currentChapterNum));
            stringBuilder.append("/");

            for(int i=0; i<numberOfPages; ++i){
                htmlFiles.add(stringBuilder.toString() + Integer.toString(i) + ".html");
            }

            url= htmlFiles.get(position);

            WebSettings settings = webView.getSettings();
            webView.setWebChromeClient(new WebChromeClient());
            webView.setWebViewClient(new WebViewClient());
            settings.setJavaScriptEnabled(true);
            webView.setScrollContainer(false);
            webView.setVerticalScrollBarEnabled(false);
            webView.setHorizontalScrollBarEnabled(false);
            
            //implement night view
//            webView.setBackgroundColor(); //night mode
//            webView = rootView.findViewById(R.id.webView);
//            String rawHTML = "<HTML>"+
//                    "<head>"+"<style  type=\"text/css\">"+
//                    "body,h1{color: #000000;"+
//                    "background-color: #ffffff;}"+
//                    "</style></head>"+
//                    "<body><h1>Hello Android </h1></body>"+
//                    "</HTML>";
//            webView.loadData(rawHTML, "text/html; charset=UTF-8",null);

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

//        @Override
//        public CharSequence getPageTitle(int position) {
//            switch (position) {
//                case 0: return "SECTION 1";
//                case 1: return "SECTION 2";
//                case 2: return "SECTION 3";
//            }
//            return null;
//        }
    }
}
