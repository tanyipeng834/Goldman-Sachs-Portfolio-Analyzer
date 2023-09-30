package com.trading.application.logs.entity;

import org.springframework.stereotype.Component;

public class AccessLog {

    private String userId;
    private String action;
    private String ipAddress;
    private String info;
    private String dateTime;

    public AccessLog(){

    }
    public AccessLog(String userId, String action, String ipAddress, String info, String dateTime) {
        this.userId = userId;
        this.action = action;
        this.ipAddress = ipAddress;
        this.info = info;
        this.dateTime = dateTime;
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
}
