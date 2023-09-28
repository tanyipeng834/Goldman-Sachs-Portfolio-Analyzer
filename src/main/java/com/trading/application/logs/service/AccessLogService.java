package com.trading.application.logs.service;

import com.trading.application.logs.entity.AccessLog;
import com.trading.application.logs.repository.AccessLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

@Service
public class AccessLogService {

    @Autowired
    private AccessLogRepository accessLogRepository;

    public String addLog(AccessLog accessLog) throws ExecutionException, InterruptedException {
        return accessLogRepository.addLog(accessLog);
    }

    public ArrayList<AccessLog> getLogs(String userId) throws ExecutionException, InterruptedException {
        return accessLogRepository.getLogs(userId);
    }
}
