package basti.coryphaei.com.baseactivity;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by Bowen on 2015-11-02.
 */
public interface NetworkCallback {

    void parseResults(JSONObject jsonObject, int tag);

    void onFailure(int tag);

}
