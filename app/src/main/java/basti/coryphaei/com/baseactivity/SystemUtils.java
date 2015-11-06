package basti.coryphaei.com.baseactivity;

import android.os.Environment;

/**
 * 系统工具类
 * Created by Bowen on 2015-11-05.
 */
public class SystemUtils {

    /*
    查看手机是否有SD卡
     */
    public static boolean hasSdCard(){
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        }
            return false;
    }
}
