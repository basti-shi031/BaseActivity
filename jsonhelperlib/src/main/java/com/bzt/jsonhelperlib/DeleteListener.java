package com.bzt.jsonhelperlib;

/**
 * 删除回调
 * Created by SHIBW-PC on 2015/12/7.
 */
public interface DeleteListener {
    //删除完成
    void finishDelete(int deleteTag,boolean success);
}
