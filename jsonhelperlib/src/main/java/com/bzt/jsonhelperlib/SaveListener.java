package com.bzt.jsonhelperlib;

/**
 * 保存回调
 * Created by SHIBW-PC on 2015/12/7.
 */
public interface SaveListener {

    //保存完成
    void finishSave(int saveTag,boolean success);
}
