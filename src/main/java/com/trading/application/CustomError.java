package com.trading.application;

import org.springframework.stereotype.Component;

/**
 * The type Custom error.
 */
public class CustomError {

    /**
     * The Error code.
     */
    private int errorCode;
    /**
     * The Error msg.
     */
    private String errorMsg;

    /**
     * Instantiates a new Custom error.
     *
     * @param errorCode the error code
     * @param errorMsg  the error msg
     */
    public CustomError(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    /**
     * Gets error code.
     *
     * @return the error code
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * Sets error code.
     *
     * @param errorCode the error code
     */
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Gets error msg.
     *
     * @return the error msg
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * Sets error msg.
     *
     * @param errorMsg the error msg
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
