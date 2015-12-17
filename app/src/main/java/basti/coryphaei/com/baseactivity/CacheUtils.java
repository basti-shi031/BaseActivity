package basti.coryphaei.com.baseactivity;

import android.content.Context;

import com.bzt.jsonhelperlib.DeleteAllListener;
import com.bzt.jsonhelperlib.DeleteListener;
import com.bzt.jsonhelperlib.JsonHelper;
import com.bzt.jsonhelperlib.LoadBean;
import com.bzt.jsonhelperlib.LoadListener;
import com.bzt.jsonhelperlib.SaveListener;

/**
 * 缓存工具
 * Created by SHIBW-PC on 201
 * 5/12/17.
 */
public class CacheUtils implements SaveListener,LoadListener,DeleteListener,DeleteAllListener{

    private JsonHelper jsonHelper;

    public CacheUtils(SaveListener saveListener,LoadListener loadListener,DeleteListener deleteListener,DeleteAllListener deleteAllListener){
        init(saveListener,loadListener,deleteListener,deleteAllListener);
    }

    private void init(SaveListener saveListener,LoadListener loadListener,DeleteListener deleteListener,DeleteAllListener deleteAllListener){
        jsonHelper = new JsonHelper();
        jsonHelper.setOnAllListener(saveListener, loadListener, deleteListener, deleteAllListener);
    }

    public void save(String content,String path,final String filename, int saveTag){
        jsonHelper.saveJson(content, path, filename, saveTag);
    }

    public void load(String path,String filename,int loadTag){
        jsonHelper.loadJson(path, filename, loadTag);
    }

    @Override
    public void finishSave(int saveTag, boolean success) {

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
}
