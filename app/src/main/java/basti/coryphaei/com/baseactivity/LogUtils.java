package basti.coryphaei.com.baseactivity;

import android.util.Log;

/**
 * Created by Bowen on 2015-11-02.
 */
public class LogUtils {

    public static void Log(boolean DEBUG_MODE,String TAG,String Message){
        if (DEBUG_MODE){
            Log.i(TAG,Message);
        }
    }
}
