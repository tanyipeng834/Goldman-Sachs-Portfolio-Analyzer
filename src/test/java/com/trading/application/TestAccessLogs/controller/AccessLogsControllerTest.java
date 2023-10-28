package com.trading.application.TestAccessLogs.controller;

import com.trading.application.logs.controller.AccessLogController;
import com.trading.application.logs.entity.AccessLog;
import com.trading.application.logs.service.AccessLogService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@AutoConfigureMockMvc
public class AccessLogsControllerTest {

    @InjectMocks
    private AccessLogController accessLogController;

    @Mock
    private AccessLogService accessLogService;


    @Test
    public void shouldCreateAccessLog() throws ExecutionException, InterruptedException {

        AccessLog accessLog = new AccessLog(); // Create an AccessLog object
        when(accessLogService.addLog(accessLog))
                .thenReturn("201 CREATED"); // Mock the repository method

        // Act
        HttpStatusCode result = accessLogController.addLog(accessLog).getStatusCode();

        // Assert
        assertEquals(HttpStatus.CREATED, result); // Check the result

    }


    @Test
    public void shouldReturnAllAccessLogsByUserId() throws ExecutionException, InterruptedException {

        AccessLog mockLog = new AccessLog();

        List<AccessLog> accessLogs = new ArrayList<>();
        accessLogs.add(mockLog);
        when(accessLogService.getLogs("testId")).thenReturn((ArrayList<AccessLog>) accessLogs);

        ArrayList<AccessLog> result = accessLogController.getLogs("testId").getBody();
        HttpStatusCode resultCode = accessLogController.getLogs("testId").getStatusCode();

        assertEquals(accessLogs, result);
        assertEquals(HttpStatus.OK, resultCode);
    }

}
