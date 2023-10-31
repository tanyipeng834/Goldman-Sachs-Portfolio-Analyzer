package com.trading.application.middleware;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {



    @Override
    public void commence(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, AuthenticationException authException) throws IOException, jakarta.servlet.ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // Set the response content type
        response.setContentType("application/json");
        // Write a custom error message to the response
        response.getWriter().write("{\"error\": \"Unauthorized User\", \"message\": \"You are not an authorized user for this application.\"}");
    }
}
