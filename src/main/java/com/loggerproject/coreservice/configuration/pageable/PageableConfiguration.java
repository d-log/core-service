package com.loggerproject.coreservice.configuration.pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;

@Configuration
public class PageableConfiguration {

    @Autowired
    public void configurePageable(PageableHandlerMethodArgumentResolver resolver, @Value("${spring.data.rest.defaultPageSize}") Integer defaultPageSize) {
        /*
         * @GetMapping
         * public Object get(Pageable pageable) {
         *      // pageable would now inherit `defaultPageSize` instead of 20
         * }
         */
        resolver.setFallbackPageable(new PageRequest(0, defaultPageSize));
    }
}
