package basti.coryphaei.com.baseactivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.RequestQueue;

import java.util.HashMap;
import java.util.Map;

/**
 * BaseActivity类
 * 封装了ProgressDialog,Toast,volley的请求类
 * Created by Bowen on 2015-11-02.
 */
public class BaseActivity extends Activity implements NetworkCallback {

    private DialogUtils mProgressDialogUtils;
    private ToastUtils mToast;
    private NetworkUtils mNetworkUtils;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUtils();
    }

    private void initUtils() {
        mProgressDialogUtils = new DialogUtils(this);
        mToast = new ToastUtils(this);
        mNetworkUtils = new NetworkUtils(this,this);
    }

    @Override
    public void parseResults(JSONObject jsonObject, int tag) {
        mProgressDialogUtils.showProgressDialog(false);
    }

    @Override
    public void onFailure(int tag) {
        mProgressDialogUtils.showProgressDialog(false);
        mToast.showToast(getResources().getString(R.string.internet_error));
    }

    public void showToast(String message){
        mToast.showToast(message);
    }

    public void showProgressDiaolog(boolean show,String message){
        mProgressDialogUtils.showProgressDialog(show, message);
    }

    public void showProgressDiaolog(boolean show){
        mProgressDialogUtils.showProgressDialog(show);
    }

    public void gettNetwork(int tag,String url){
        mProgressDialogUtils.showProgressDialog(true,getResources().getString(R.string.loading));
        mNetworkUtils.LoadData(tag, url, null, NetworkUtils.RequestMethod.GET);
    }

    public void postNetwork(int tag,String url,Map<String,String> params){
        mProgressDialogUtils.showProgressDialog(true,getResources().getString(R.string.loading));
        mNetworkUtils.LoadData(tag, url, params, NetworkUtils.RequestMethod.POST);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mNetworkUtils.cancelRequest();
    }
}
