
package ru.intervale.TeamProject.config.restTemplate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;

import java.io.IOException;

/**
 * The type Custom client http request interceptor.
 */
@Slf4j
public class RequestLoggerInterceptor implements ClientHttpRequestInterceptor {

    @Override
    @NonNull
    public ClientHttpResponse intercept(
            @NonNull HttpRequest request,
            @NonNull byte[] body,
            ClientHttpRequestExecution execution
    ) throws IOException {
        logRequestDetails(request);
        return execution.execute(request, body);
    }
    private void logRequestDetails(HttpRequest request) {
        log.debug("Request Method: {} Headers: {}", request.getMethod(), request.getHeaders());
        log.debug("Request URI: {}", request.getURI());
    }
}
