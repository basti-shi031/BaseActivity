package basti.coryphaei.com.baseactivity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends BaseActivity{


    private static final String url1= "http://121.40.31.41:7777/foryou/user/toLogin.do";
    private static final String url2= "http://121.40.31.41:7777/foryou/service/getCategory.do";
    private TextView tv1,tv2;
    private Button button;
    private Map<String,String> map1;
    private Map<String,String> map2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initDates();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                map1.put("phone", "18260108169");
                map1.put("password", "123456");
                map1.put("server", "56846a8a2fee49d14901d39cc48b8b2a");
                postNetwork(1, url1, map1);
                map2.put("campusId", "1");
                map2.put("server", "56846a8a2fee49d14901d39cc48b8b2a");
                postNetwork(2,url2,map2);
            }
        });
    }

    private void initDates() {
        map1 = new HashMap<>();
        map2 = new HashMap<>();
    }


    private void initView() {
        tv1 = (TextView) findViewById(R.id.result1);
        tv2 = (TextView) findViewById(R.id.result2);
        button = (Button) findViewById(R.id.button);
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
        }
    }

    @Override
    public void onFailure(int tag) {
        super.onFailure(tag);
    }
}
