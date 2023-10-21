package com.trading.application.logs.controller;

import com.trading.application.logs.entity.AccessLog;
import com.trading.application.logs.service.AccessLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * The type Access log controller.
 */
@RestController
@RequestMapping("/accesslog")
@CrossOrigin(origins = "http://localhost:8080")
public class AccessLogController {

    /**
     * The Access log service.
     */
    @Autowired
    private AccessLogService accessLogService;


    /**
     * Add log string.
     *
     * @param accessLog the access log
     * @return the string
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    @PostMapping
    @RequestMapping("/add")
    public String addLog(@RequestBody AccessLog accessLog) throws ExecutionException, InterruptedException {
        return accessLogService.addLog(accessLog);
    }

    /**
     * Gets logs.
     *
     * @param userId the user id
     * @return the logs
     * @throws ExecutionException   the execution exception
     * @throws InterruptedException the interrupted exception
     */
    @GetMapping
    @RequestMapping("/getlogs/{userId}")
    public ArrayList<AccessLog> getLogs(@PathVariable String userId) throws ExecutionException, InterruptedException {
        return accessLogService.getLogs(userId);
    }
}
