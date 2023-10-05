package com.trading.application.logs.service;

import com.trading.application.logs.entity.AccessLog;
import com.trading.application.logs.repository.AccessLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

@Service
public class AccessLogService  {





    @Autowired
    private AccessLogRepository accessLogRepository = new AccessLogRepository();

    public String addLog(AccessLog accessLog) throws ExecutionException, InterruptedException {
        return accessLogRepository.addLog(accessLog);
    }

    public ArrayList<AccessLog> getLogs(String userId) throws ExecutionException, InterruptedException {
        return accessLogRepository.getLogs(userId);
    }
}
