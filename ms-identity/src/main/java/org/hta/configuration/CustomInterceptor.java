package org.hta.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.AsyncWebRequestInterceptor;
import org.springframework.web.context.request.WebRequest;

@Component
@Slf4j
public class CustomInterceptor implements AsyncWebRequestInterceptor {

    private int count = 0;

    @Override
    public void afterConcurrentHandlingStarted(WebRequest request) {

    }

    @Override
    public void preHandle(WebRequest request) throws Exception {
        count++;
    }

    @Override
    public void postHandle(WebRequest request, ModelMap model) throws Exception {
        log.info("Count logging success " + count);
    }

    @Override
    public void afterCompletion(WebRequest request, Exception ex) throws Exception {

    }
}