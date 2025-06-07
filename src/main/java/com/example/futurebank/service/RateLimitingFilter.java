package com.example.futurebank.service;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
@Order(1)
public class RateLimitingFilter implements Filter {

    private static final int MAX_REQUESTS_PER_MINUTE = 100;
    private final ConcurrentHashMap<String, RequestCounter> requestCounts = new ConcurrentHashMap<>();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String ipAddress = httpRequest.getRemoteAddr();

        RequestCounter counter = requestCounts.computeIfAbsent(
                ipAddress,
                k -> new RequestCounter()
        );

        if (counter.incrementAndCheck() > MAX_REQUESTS_PER_MINUTE) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(429); //status code!
            httpResponse.getWriter().write("Too many requests");
            return;
        }

        chain.doFilter(request, response);
    }

    private static class RequestCounter {
        private int count;
        private long lastResetTime;

        public RequestCounter() {
            this.count = 0;
            this.lastResetTime = System.currentTimeMillis();
        }

        public synchronized int incrementAndCheck() {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastResetTime > TimeUnit.MINUTES.toMillis(1)) {
                count = 0;
                lastResetTime = currentTime;
            }
            return ++count;
        }
    }
}