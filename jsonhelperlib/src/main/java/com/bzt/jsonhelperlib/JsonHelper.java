package com.bzt.jsonhelperlib;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;

/**
 * 工具类，用于保存/读取Json数据
 * Created by SHIBW-PC on 2015/12/7.
 */
public class JsonHelper {

    private SaveListener saveListener;
    private LoadListener loadListener;
    private DeleteListener deleteListener;
    private DeleteAllListener deleteAllListener;
    private String TAG = "BZTJsonHelper";
    private static final int SAVE = 0;
    private static final int LOAD = 1;
    private static final int DELETE = 2;
    private static final int DELETE_ALL = 3;
    public JsonHelper(){
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.arg1){
                case SAVE:
                    if (saveListener != null){
                        saveListener.finishSave(msg.arg2, (Boolean) msg.obj);
                    }
                    break;
                case LOAD:
                    if (loadListener != null){
                        loadListener.finishLoad(msg.arg2, (LoadBean) msg.obj);
                    }
                    break;
                case DELETE:
                    if (deleteListener != null){
                        deleteListener.finishDelete(msg.arg2, (Boolean) msg.obj);
                    }
                    break;
                case DELETE_ALL:
                    if (deleteAllListener!= null)
                        deleteAllListener.finishDeleteAll(msg.arg2);
                    break;
            }
        }
    };

    public void setOnAllListener(SaveListener saveListener,LoadListener loadListener,DeleteListener deleteListener,DeleteAllListener deleteAllListener){
        setOnSaveListener(saveListener);
        setOnLoadListener(loadListener);
        setOnDeleteListener(deleteListener);
        setOnDeleteAllListener(deleteAllListener);
    }

    public void setOnLoadListener(LoadListener listener){
        loadListener = listener;
    }

    public void setOnDeleteListener(DeleteListener listener){
        deleteListener = listener;
    }

    public void setOnDeleteAllListener(DeleteAllListener listener){
        deleteAllListener = listener;
    }

    public void setOnSaveListener(SaveListener listener){
        saveListener = listener;
    }
    public void saveJson(final String jsonObject, final String path, final String filename, final int saveTag){

        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = Message.obtain();
                message.arg1 = SAVE;
                message.arg2 = saveTag;
                message.obj = false;
                Writer writer = null;
                try {
                    File file = new File(path);
                    if(!file.exists()){
                        file.mkdirs();
                    }
                    writer = new BufferedWriter(new OutputStreamWriter(
                            new FileOutputStream(path+filename), "utf-8"));
                    writer.write(jsonObject);

                    message.obj = true;

                } catch (IOException ex) {
                } finally {
                    try {
                        writer.close();
                        mHandler.sendMessage(message);
                    } catch (Exception ex) {}
                }
            }
        }).start();
    }

    public void loadJson(final String path, final String filename, final int loadTag){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = Message.obtain();
                message.arg1 = LOAD;
                message.arg2 = loadTag;
                try {
                    Scanner in = new Scanner(new FileReader(path+filename));
                    LoadBean loadBean = new LoadBean(in.nextLine(),true);
                    message.obj = loadBean;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    LoadBean loadBean = new LoadBean("",false);
                    message.obj = loadBean;
                }

                mHandler.sendMessage(message);
            }
        }).start();
    }

    public void clearCache(final String path, final String filename, final int deleteTag){

        new Thread(new Runnable() {
            @Override
            public void run() {
                File file = new File(path+filename);
                Message message = Message.obtain();
                message.arg1 = DELETE;
                message.arg2 = deleteTag;
                if(file.exists()){
                    file.delete();
                    message.obj = true;
                }else {
                    message.obj = false;
                }
                mHandler.sendMessage(message);
            }
        }).start();
    }

    public void clearAllCache(final String path, final int deleteTag){
        new Thread(new Runnable() {
            @Override
            public void run() {
                File file = new File(path);
                File files[] = file.listFiles();
                if (files != null){
                    for (int i = 0; i < files.length; i++) {
                        files[i].delete();
                    }
                    Message message = Message.obtain();
                    message.arg1 = DELETE_ALL;
                    message.arg2 = deleteTag;
                    mHandler.sendMessage(message);
                }
            }
        }).start();
    }
}
