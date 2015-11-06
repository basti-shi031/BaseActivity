package basti.coryphaei.com.baseactivity;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Bowen on 2015-11-02.
 */
public class BaseApplication extends Application {

    protected RequestQueue mQueue;
    //调试模式，线下版本置为true，上线后改成false
    protected boolean DEBUG_MODE = true;

    @Override
    public void onCreate()
    {
        super.onCreate();
    }


    public synchronized RequestQueue getRequestQueue()
    {
        if(null == mQueue){
            mQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mQueue;
    }
}
