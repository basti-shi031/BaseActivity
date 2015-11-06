package basti.coryphaei.com.baseactivity;

import android.app.Activity;
import android.os.Bundle;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * BaseActivity类
 * 封装了ProgressDialog,Toast,volley的请求类
 * Created by Bowen on 2015-11-02.
 */
public class BaseActivity extends Activity implements NetworkCallback {

    private DialogUtils mProgressDialogUtils;
    private ToastUtils mToast;
    private NetworkUtils mNetworkUtils;
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

    //get请求
    public void getNetwork(int tag,String url){
        getNetwork(tag, url, true);
    }

    public void getNetwork(int tag,String url,boolean showProgressbar){
        if (showProgressbar)
        mProgressDialogUtils.showProgressDialog(true,getResources().getString(R.string.loading));

        mNetworkUtils.LoadData(tag, url, null, NetworkUtils.RequestMethod.GET);
    }

    //post请求
    public void postNetwork(int tag,String url,Map<String,String> params){
        postNetwork(tag,url,params,true);
    }
    public void postNetwork(int tag,String url,Map<String,String> params,boolean showProgressbar){
        if (showProgressbar)
        mProgressDialogUtils.showProgressDialog(true,getResources().getString(R.string.loading));
        mNetworkUtils.LoadData(tag, url, params, NetworkUtils.RequestMethod.POST);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProgressDialogUtils.dismiss();
        mNetworkUtils.cancelRequest();
    }
}
