package com.trading.application.logs.controller;

import com.trading.application.logs.entity.AccessLog;
import com.trading.application.logs.service.AccessLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Objects;
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
    public ResponseEntity<String> addLog(@RequestBody AccessLog accessLog) throws ExecutionException, InterruptedException {

        String output = accessLogService.addLog(accessLog);

        if(output.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
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
    public ResponseEntity<ArrayList<AccessLog>> getLogs(@PathVariable String userId) throws ExecutionException, InterruptedException {

        ArrayList<AccessLog> userAccessLogs = accessLogService.getLogs(userId);

        if (userAccessLogs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(userAccessLogs, HttpStatus.OK);
    }
}
