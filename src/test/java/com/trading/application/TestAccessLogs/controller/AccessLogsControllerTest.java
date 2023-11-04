package com.trading.application.TestAccessLogs.controller;

import com.trading.application.logs.controller.AccessLogController;
import com.trading.application.logs.entity.AccessLog;
import com.trading.application.logs.service.AccessLogService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
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

    @Mock
    private HttpServletRequest request;

    @BeforeEach
    public void setup() {
        String jwtToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6ImpER3QxOC1LQ09lM25wTWZacGhjRiJ9" +
                ".eyJpc3MiOiJodHRwczovL2Rldi1oemtzajg0NjhoZ2o0cTVmLnVzLmF1dGgwLmNvbS8iLCJzdWIiOiJuUXZNSmMyQUlrNGFxa2pHUWtCdHFLSVJaWVRnNHBXREBjbGllbnRzIiwiYXVkIjoiaHR0cHM6Ly9nb2xkbWFuLmNvbSIsImlhdCI6MTY5ODc0MTExMywiZXhwIjoxNzAxMzMzMTEzLCJhenAiOiJuUXZNSmMyQUlrNGFxa2pHUWtCdHFLSVJaWVRnNHBXRCIsImd0eSI6ImNsaWVudC1jcmVkZW50aWFscyJ9.gJCNGKAKSrI8366jKpr24-lGjoPPbb-Ce0Q7kFQc0iTDNxuJ57g-3602dRv9bLlN9mltILAF6a1-gqmuGEepG8Ey79wdAvRgZahgCdgRoRd7yc1gYTaSlmSvWrPm41XJOxAtB23d3H4yT38RdAn6CqX3V7vp5KR1t3nUu4-Gk_eel5N6a1cckYk70Fh5OahaYT__-rUPxNcoWzs_FgQefUsAwwZaEzCSlVnk4ZRivx5ph4Ygu8-jp36m_nKwo7g0Es3da_2p_wFSXMKF88Wf35bqicFMVR7L-cCWLyx9ITjx41hUxoXKCVh3hNj3lsky-E9cxqFx555x7_s_l8jnAg";
        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwtToken);
    }

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
