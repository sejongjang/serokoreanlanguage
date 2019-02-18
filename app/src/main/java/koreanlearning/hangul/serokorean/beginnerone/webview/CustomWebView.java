package koreanlearning.hangul.serokorean.beginnerone.webview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

public class CustomWebView extends WebView {

    private Float x;
    private Float y;
    private Float prevX, prevY;
    private String link, image;
    CustomWebView webView;

    BeginnerOneWebView.PlaceholderFragment webViewFragment;

    public CustomWebView(Context context) {
        super(context);
    }

    public CustomWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        webView = this;
    }

    public CustomWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setFragment(BeginnerOneWebView.PlaceholderFragment webViewFragment) {
        this.webViewFragment = webViewFragment;

    }
    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        //Enables ViewPager when scroll reaches end.
        webViewFragment.setViewPager(true);
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        //disables ViewPager when user presses down
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            webViewFragment.setViewPager(false);
            return true;
        }

        return true;


    }
}
