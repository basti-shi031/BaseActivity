package com.bzt.jsonhelperlib;

/**
 * Created by SHIBW-PC on 2015/12/7.
 */
public class LoadBean {

    private String result;
    private boolean success;

    public LoadBean() {
    }

    public LoadBean(String result, boolean success) {
        this.result = result;
        this.success = success;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResult() {

        return result;
    }

    public boolean isSuccess() {
        return success;
    }
}
