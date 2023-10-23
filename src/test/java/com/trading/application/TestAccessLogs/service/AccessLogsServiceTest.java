package com.trading.application.TestAccessLogs.service;

import com.trading.application.logs.entity.AccessLog;
import com.trading.application.logs.repository.AccessLogRepository;
import com.trading.application.logs.service.AccessLogService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class AccessLogsServiceTest {

    /**
     * The Access Log Service.
     */
    @InjectMocks
    @Spy
    private AccessLogService accessLogService;

    /**
     * The Access Log Repository.
     */
    @Mock
    private AccessLogRepository accessLogRepository;


    /**
     * Method under test: {@link AccessLogService#addLog(AccessLog)}
     *
     * @throws InterruptedException the interrupted exception
     * @throws ExecutionException   the execution exception
     */
    @Test
    public void shouldCreateAccessLog() throws ExecutionException, InterruptedException {


        AccessLog accessLog = new AccessLog(); // Create an AccessLog object
        when(accessLogRepository.addLog(accessLog))
                .thenReturn("201 CREATED"); // Mock the repository method

        // Act
        String result = accessLogService.addLog(accessLog);

        // Assert
        assertEquals("201 CREATED", result); // Check the result


    }


    /**
     * Method under test: {@link AccessLogService#getLogs(String)}
     *
     * @throws InterruptedException the interrupted exception
     * @throws ExecutionException   the execution exception
     */
    @Test
    public void shouldReturnAllAccessLogsByUserId() throws ExecutionException, InterruptedException {

        AccessLog mockLog = new AccessLog();

        ArrayList<AccessLog> accessLogs = new ArrayList<>();
        accessLogs.add(mockLog);
        when(accessLogRepository.getLogs("testId")).thenReturn(accessLogs);

        ArrayList<AccessLog> result = accessLogService.getLogs("testId");

        assertEquals(accessLogs, result);

    }

}
