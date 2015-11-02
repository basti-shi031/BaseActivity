package basti.coryphaei.com.baseactivity;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

/**
 * Created by Bowen on 2015-11-02.
 */
public class NetworkUtils {

    public NetworkCallback mCallback;
    private Context mContext;
    public int Method = 0;
    public int TimeoutMs = 10*1000;
    public String TAG = "Request";
    private BaseApplication mApp;
    private RequestQueue mQueue;

    public NetworkUtils(NetworkCallback callback,Context context){
        mCallback = callback;
        mContext = context;
        mApp = (BaseApplication) ((Activity)mContext).getApplication();
        mQueue = mApp.getRequestQueue();
    }

    public void LoadData(final int Tag,String url, final Map<String,String> params,RequestMethod method){

        switch (method){
            case GET:Method = Request.Method.GET;
                break;
            case POST:Method = Request.Method.POST;
                break;
            default:break;
        }

        StringRequest request = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.i("response",s);
                JSONObject jsonObject = JSON.parseObject(s);
                mCallback.parseResults(jsonObject,Tag);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mCallback.onFailure(Tag);
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };

        setRequestProperty(request);
        addRequestProperty(request);

    }

    private void addRequestProperty(StringRequest request) {
        mQueue.add(request);
    }

    private void setRequestProperty(StringRequest request) {

        request.setTag(TAG);
        request.setRetryPolicy(new DefaultRetryPolicy(TimeoutMs, 1, 1f));
    }

    public void cancelRequest(){
        mQueue.cancelAll(TAG);
    }

    enum RequestMethod{
        GET,POST
    }
}
