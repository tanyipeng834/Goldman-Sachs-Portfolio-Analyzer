package com.trading.application.logs.entity;

public class AccessLog {

    private String userId;
    private String action;
    private String ipAddress;
    private String info;
    private String dateTime;
    private boolean success;


    public AccessLog(){

    }
    public AccessLog(String userId, String action, String ipAddress, String info, String dateTime, boolean success) {
        this.userId = userId;
        this.action = action;
        this.ipAddress = ipAddress;
        this.info = info;
        this.dateTime = dateTime;
        this.success = success;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
