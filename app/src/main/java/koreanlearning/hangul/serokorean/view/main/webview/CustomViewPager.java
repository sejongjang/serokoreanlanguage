package koreanlearning.hangul.serokorean.view.main.webview;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MotionEventCompat;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CustomViewPager extends ViewPager {
    private boolean isPagingEnabled = true;

    float mStartDragX;
    OnSwipeOutListener mOnSwipeOutListener;

    public interface OnSwipeOutListener {
        void onSwipeOutAtStart();
        void onSwipeOutAtEnd();
    }

    public void setOnSwipeOutListener(OnSwipeOutListener listener) {
        mOnSwipeOutListener = listener;
    }

    private void onSwipeOutAtStart() {
        if (mOnSwipeOutListener!=null) {
            mOnSwipeOutListener.onSwipeOutAtStart();
        }
    }

    private void onSwipeOutAtEnd() {
        if (mOnSwipeOutListener!=null) {
            mOnSwipeOutListener.onSwipeOutAtEnd();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
//        return this.isPagingEnabled && super.onTouchEvent(event);
        if(getCurrentItem()==0 || getCurrentItem()==getAdapter().getCount()-1){
            final int action = ev.getAction();
            float x = ev.getX();
            switch(action & MotionEventCompat.ACTION_MASK){
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_UP:
                    if (getCurrentItem()==0 && x>mStartDragX) {
                        onSwipeOutAtStart();
                    }
                    if (getCurrentItem()==getAdapter().getCount()-1 && x<mStartDragX){
                        onSwipeOutAtEnd();
                    }
                    break;
            }
        }else{
            mStartDragX=0;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        try {
//            return this.isPagingEnabled && super.onInterceptTouchEvent(event);
//        }
//        catch(IllegalArgumentException exception){
//            exception.printStackTrace();
//        }
//        return false;
        switch(ev.getAction() & MotionEventCompat.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                mStartDragX = ev.getX();
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }


    public CustomViewPager(@NonNull Context context) {
        super(context);
    }

    public CustomViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        return this.isPagingEnabled && super.canScroll(v, checkV, dx, x, y);
    }

    public void setPagingEnabled(boolean b) {
        this.isPagingEnabled = b;
    }

    @Override
    public void removeView(View view) {
        super.removeView(view);
    }


    public Boolean getPagingStatus() {
        return isPagingEnabled;
    }

}
