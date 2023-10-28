package com.trading.application.TestAccessLogs.repository;

import com.trading.application.logs.entity.AccessLog;
import com.trading.application.logs.repository.AccessLogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {AccessLog.class})
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AccessLogsRepositoryTest {
    

    @Autowired
    private AccessLog accessLog;

    @MockBean
    private AccessLogRepository accessLogRepository;

    @Test
    public void shouldCreateAccessLog() throws InterruptedException, ExecutionException {
        when(accessLogRepository.addLog(Mockito.<AccessLog>any())).thenReturn("Create Access Log");
        assertEquals("Create Access Log", accessLogRepository.addLog(accessLog));
        verify(accessLogRepository).addLog(Mockito.<AccessLog>any());
    }


    @Test
    void shouldReturnAllAccessLogsByUserId() throws InterruptedException, ExecutionException {
        ArrayList<AccessLog> accessLogsList = new ArrayList<>();
        when(accessLogRepository.getLogs("testId")).thenReturn(accessLogsList);
        ArrayList<AccessLog> actualAllAccessLogs = accessLogRepository.getLogs("testId");
        assertSame(accessLogsList, actualAllAccessLogs);
        assertTrue(actualAllAccessLogs.isEmpty());
        verify(accessLogRepository).getLogs("testId");
    }
    
    
}
