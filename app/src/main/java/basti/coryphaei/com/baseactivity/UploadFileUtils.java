package basti.coryphaei.com.baseactivity;

import android.app.Activity;
import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

/**
 * 图片上传类
 * Created by Bowen on 2015-11-05.
 */
public class UploadFileUtils {

    public interface UpLoadFileListener{
        void onUpLoadSuccess(String result);
        void onUpLoadFailure(String result);
        void onUpLoadProgress(int written, int total);
    }

    public static void upLoadFile(final Context context,RequestParams params,String url, final UpLoadFileListener mUpLoadFileListener){

        AsyncHttpClient client = new AsyncHttpClient();
        KeyStore trustStore = null;
        try {
            trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            MySSLSocketFactory sf = new MySSLSocketFactory(trustStore);
            sf.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            client.setSSLSocketFactory(sf);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }


        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (mUpLoadFileListener != null){
                    try {
                        String s = new String(responseBody, "GB2312");
                        mUpLoadFileListener.onUpLoadSuccess(s);
                        LogUtils.Log(((MyApplication) ((Activity) context).getApplication()).DEBUG_MODE,"TAG",s);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if (mUpLoadFileListener != null){
                    try {
                        String s = new String(responseBody, "GB2312");
                        mUpLoadFileListener.onUpLoadFailure(s);
                        LogUtils.Log(((MyApplication) ((Activity) context).getApplication()).DEBUG_MODE, "TAG", s);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onProgress(int bytesWritten, int totalSize) {
                if (mUpLoadFileListener != null){
                    mUpLoadFileListener.onUpLoadProgress(bytesWritten,totalSize);
                }
                super.onProgress(bytesWritten, totalSize);
            }
        });
    }
}
