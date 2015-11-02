package basti.coryphaei.com.baseactivity;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Bowen on 2015-11-02.
 */
public class DialogUtils {

    private ProgressDialog mProgressDialog;
    private Context mContext;

    public DialogUtils(Context context){
        mContext = context;
        mProgressDialog = new ProgressDialog(context);
    }

    public void showProgressDialog(boolean show,String message){
        if (show){
            mProgressDialog.setMessage(message);
            mProgressDialog.show();
        }else {
            mProgressDialog.hide();
        }
    }

    public void showProgressDialog(boolean show){
        showProgressDialog(show,"");
    }

}
