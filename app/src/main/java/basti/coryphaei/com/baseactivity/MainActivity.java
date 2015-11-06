package basti.coryphaei.com.baseactivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends UploadActivity implements UploadActivity.UpLoadFile {


    private static final String url1= "https://slack.com/api/api.test";
    private static final String url2= "http://121.40.31.41:7777/foryou/service/getCategory.do";
    private static final String url3= "https://api.imgur.com/3/image";
    private TextView tv1,tv2;
    private Button button,btUpload;
    private Map<String,String> map1;
    private Map<String,String> map2;
    private RequestParams mUploadParams;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initDates();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*map1.put("phone", "18260108169");
                map1.put("password", "123456");
                map1.put("server", "56846a8a2fee49d14901d39cc48b8b2a");*/
                //postNetwork(1, url1, map1);
                getNetwork(1, url1);
                map2.put("campusId", "1");
                map2.put("server", "56846a8a2fee49d14901d39cc48b8b2a");
                postNetwork(2, url2, map2);
            }
        });

        btUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImg(getImageSize());
            }
        });
    }

    private void initDates() {
        map1 = new HashMap<>();
        map2 = new HashMap<>();
        mUploadParams = new RequestParams();
        setOnUploadListener(this);
    }


    private void initView() {
        tv1 = (TextView) findViewById(R.id.result1);
        tv2 = (TextView) findViewById(R.id.result2);
        button = (Button) findViewById(R.id.button);
        btUpload = (Button) findViewById(R.id.upload);
    }

    @Override
    public void parseResults(JSONObject jsonObject, int tag) {
        super.parseResults(jsonObject, tag);
        switch (tag){
            case 1:
                tv1.setText(jsonObject.toString());
                break;
            case 2:
                tv2.setText(jsonObject.toString());
                break;
            default:
                break;
        }
    }

    @Override
    public void onFailure(int tag) {
        super.onFailure(tag);
    }

    //裁剪本地图片成功
    @Override
    public void loadFinished() {
        Log.i("test", "test");

        try {
            mUploadParams.put("image", getFile());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        uploadBitmap(mUploadParams,url3);
    }
}
