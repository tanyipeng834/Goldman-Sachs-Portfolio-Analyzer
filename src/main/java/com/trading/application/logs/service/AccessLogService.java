package com.trading.application.logs.service;

import com.trading.application.logs.entity.AccessLog;
import com.trading.application.logs.repository.AccessLogRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * The type Access log service.
 */
@Service
public class AccessLogService  {

    /**
     * The Access log repository.
     */
    @Autowired
    private AccessLogRepository accessLogRepository = new AccessLogRepository();

    /**
     * Add log string.
     *
     * @param accessLog the access log
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public String addLog(AccessLog accessLog) throws ExecutionException, InterruptedException {
        return accessLogRepository.addLog(accessLog);
    }

    /**
     * Gets logs.
     *
     * @param userId the user id
     * @return the logs
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    public ArrayList<AccessLog> getLogs(String userId) throws ExecutionException, InterruptedException {
        return accessLogRepository.getLogs(userId);
    }
}
