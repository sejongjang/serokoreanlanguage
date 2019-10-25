package koreanlearning.hangul.serokorean.utility;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

public class FullScreenCall {

    public static void fullScreen(Activity activity) {
        if(Build.VERSION.SDK_INT < 19){
            View v = activity.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else {
            //for higher api versions.
            View decorView = activity.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }
}
