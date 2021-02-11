package com.evolveum.midpoint.client.impl.prism;

import java.io.IOException;
import java.util.function.Function;

import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;

import com.evolveum.midpoint.client.api.ResourceOperationService;
import com.evolveum.midpoint.client.api.TaskFuture;
import com.evolveum.midpoint.client.api.exception.CommonException;

public class RestPrismResourceOperationService<T> implements ResourceOperationService<T> {

    private RestPrismService service;
    private String path;
    private Function<CloseableHttpResponse, T> responseHandler;


    public RestPrismResourceOperationService(RestPrismService service, String path, Function<CloseableHttpResponse, T> responseHandler) {
        this.service = service;
        this.path = path;
        this.responseHandler = responseHandler;
    }


    @Override
    public TaskFuture<T> apost() throws CommonException {
        try {
            CloseableHttpResponse response = (CloseableHttpResponse) service.httpPost(path, null).returnResponse();
            T parsedResponse = responseHandler.apply(response);
            return new RestPrismCompletedFuture<>(parsedResponse);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
