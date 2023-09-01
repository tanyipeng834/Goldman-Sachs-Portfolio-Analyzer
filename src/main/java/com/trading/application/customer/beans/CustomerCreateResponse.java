package com.trading.application.beans;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CustomerCreateResponse {

    private String id;
    private Date updatedTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
}
