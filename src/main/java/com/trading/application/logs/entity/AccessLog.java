package com.trading.application.logs.entity;

/**
 * The type Access log.
 */
public class AccessLog {
    /**
     * The User id.
     */
    private String userId;
    /**
     * The Action.
     */
    private String action;
    /**
     * The Ip address.
     */
    private String ipAddress;
    /**
     * The Info.
     */
    private String info;
    /**
     * The Date time.
     */
    private String dateTime;
    /**
     * The Success.
     */
    private boolean success;

    /**
     * Instantiates a new Access log.
     */
    public AccessLog(){

    }

    /**
     * Instantiates a new Access log.
     *
     * @param userId    the user id
     * @param action    the action
     * @param ipAddress the ip address
     * @param info      the info
     * @param dateTime  the date time
     * @param success   the success
     */
    public AccessLog(String userId, String action, String ipAddress, String info, String dateTime, boolean success) {
        this.userId = userId;
        this.action = action;
        this.ipAddress = ipAddress;
        this.info = info;
        this.dateTime = dateTime;
        this.success = success;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Gets action.
     *
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * Sets action.
     *
     * @param action the action
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Gets ip address.
     *
     * @return the ip address
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * Sets ip address.
     *
     * @param ipAddress the ip address
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * Gets info.
     *
     * @return the info
     */
    public String getInfo() {
        return info;
    }

    /**
     * Sets info.
     *
     * @param info the info
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * Gets date time.
     *
     * @return the date time
     */
    public String getDateTime() {
        return dateTime;
    }

    /**
     * Sets date time.
     *
     * @param dateTime the date time
     */
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Is success boolean.
     *
     * @return the boolean
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Sets success.
     *
     * @param success the success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }
}
