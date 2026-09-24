package iot.temperature.stats.configs;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
@Component
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    private static final String API_KEY_HEADER = "API-Key";

    @Value("${app.arduino-api-key}")
    private String expectedArduinoKey;
    @Value("${app.arduino-frontend-api-key}")
    private String expectedFrontendKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        String expectedKey;

        if (path.startsWith("/frontend")) {
            expectedKey = expectedFrontendKey;
        } else if (path.startsWith("/arduino")) {
            expectedKey = expectedArduinoKey;            
        } else {
            chain.doFilter(request, response);
            return;
        }

        String requestKey = request.getHeader(API_KEY_HEADER);
        if (requestKey == null || !MessageDigest.isEqual(requestKey.getBytes(StandardCharsets.UTF_8), expectedKey.getBytes(StandardCharsets.UTF_8))) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        chain.doFilter(request, response);
    }
}