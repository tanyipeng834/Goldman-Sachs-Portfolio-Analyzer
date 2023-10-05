package com.trading.application.logs.controller;

import com.trading.application.logs.entity.AccessLog;
import com.trading.application.logs.service.AccessLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/accesslog")
@CrossOrigin(origins = "http://localhost:8080")
public class AccessLogController {

    @Autowired
    private AccessLogService accessLogService;


    // add access log
    @PostMapping
    @RequestMapping("/add")
    public String addLog(@RequestBody AccessLog accessLog) throws ExecutionException, InterruptedException {
        return accessLogService.addLog(accessLog);
    }

    @GetMapping
    @RequestMapping("/getlogs/{userId}")
    public ArrayList<AccessLog> getLogs(@PathVariable String userId) throws ExecutionException, InterruptedException {
        return accessLogService.getLogs(userId);
    }
}
