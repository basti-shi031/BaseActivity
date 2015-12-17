package basti.coryphaei.com.baseactivity;

import android.app.Activity;
import android.os.Bundle;

import com.alibaba.fastjson.JSONObject;
import com.bzt.jsonhelperlib.DeleteAllListener;
import com.bzt.jsonhelperlib.DeleteListener;
import com.bzt.jsonhelperlib.LoadBean;
import com.bzt.jsonhelperlib.LoadListener;
import com.bzt.jsonhelperlib.SaveListener;

import java.util.Map;

/**
 * BaseActivity类
 * 封装了ProgressDialog,Toast,volley的请求类
 * Created by Bowen on 2015-11-02.
 */
public class BaseActivity extends Activity implements NetworkCallback, DeleteAllListener, DeleteListener, LoadListener, SaveListener {

    private DialogUtils mProgressDialogUtils;
    private ToastUtils mToast;
    private NetworkUtils mNetworkUtils;
    private CacheUtils mCacheUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUtils();
    }

    private void initUtils() {
        mProgressDialogUtils = new DialogUtils(this);
        mToast = new ToastUtils(this);
        mNetworkUtils = new NetworkUtils(this,this);
        mCacheUtils = new CacheUtils(this,this,this,this);
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
        postNetwork(tag, url, params, true, false);
    }
    public void postNetwork(int tag,String url,Map<String,String> params,boolean showProgressbar,boolean enableCache){
        if (showProgressbar)
        mProgressDialogUtils.showProgressDialog(true,getResources().getString(R.string.loading));

        mNetworkUtils.LoadData(tag, url, params, NetworkUtils.RequestMethod.POST);
    }

    public void loadJson(String path,String filename,int loadTag){
        mCacheUtils.load(path, filename, loadTag);
    }

    public void saveJson(String content,String path,final String filename, int saveTag){
        mCacheUtils.save(content,path,filename,saveTag);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProgressDialogUtils.dismiss();
        mNetworkUtils.cancelRequest();
    }

    @Override
    public void finishDeleteAll(int deleteTag) {

    }

    @Override
    public void finishDelete(int deleteTag, boolean success) {

    }

    @Override
    public void finishLoad(int loadTag, LoadBean loadBean) {

    }

    @Override
    public void finishSave(int saveTag, boolean success) {

    }
}
