package basti.coryphaei.com.baseactivity;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Bowen on 2015-11-02.
 */
public class ToastUtils {

    private Toast mToast;
    private Context mContext;

    public ToastUtils(Context context){
        mContext = context;
        mToast = Toast.makeText(context,"",Toast.LENGTH_SHORT);
    }

    public void showToast(String s){
        mToast.setText(s);
        mToast.show();
    }
}
