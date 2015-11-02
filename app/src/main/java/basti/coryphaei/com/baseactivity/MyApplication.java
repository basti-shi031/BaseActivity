package basti.coryphaei.com.baseactivity;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Bowen on 2015-11-02.
 */
public class MyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
