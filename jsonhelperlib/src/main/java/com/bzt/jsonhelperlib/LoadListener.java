package com.bzt.jsonhelperlib;

/**
 * 读取回调
 * Created by SHIBW-PC on 2015/12/7.
 */
public interface LoadListener {

    //读取完成
    void finishLoad(int loadTag,LoadBean loadBean);
}
