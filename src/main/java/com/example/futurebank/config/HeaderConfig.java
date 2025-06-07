package com.example.futurebank.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.header.HeaderWriter;
import org.springframework.security.web.header.HeaderWriterFilter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.security.web.header.writers.XContentTypeOptionsHeaderWriter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

import java.util.Arrays;
import java.util.List;

@Configuration
public class HeaderConfig {

    @Bean
    public FilterRegistrationBean<HeaderWriterFilter> securityHeadersFilter() {
        List<HeaderWriter> headerWriters = Arrays.asList(
                new XContentTypeOptionsHeaderWriter(),
                new XXssProtectionHeaderWriter(),
                new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.DENY),
                new ReferrerPolicyHeaderWriter(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN),
                new StaticHeadersWriter("Strict-Transport-Security", "max-age=31536000 ; includeSubDomains"),
                new StaticHeadersWriter("Content-Security-Policy",
                        "default-src 'self'; script-src 'self' 'unsafe-inline'; style-src 'self' 'unsafe-inline'; img-src 'self' data:")
        );

        HeaderWriterFilter filter = new HeaderWriterFilter(headerWriters);

        FilterRegistrationBean<HeaderWriterFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(filter);
        registration.setOrder(Integer.MIN_VALUE); // Highest priority
        return registration;
    }
}
