package com.evolveum.midpoint.client.impl.prism.handler;

import com.evolveum.midpoint.prism.PrismContext;

import com.evolveum.midpoint.util.exception.SchemaException;

import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.xpath.res.XPATHErrorResources_it;

import java.io.IOException;

public class HttpClientPrismResponseHandler<T> implements HttpClientResponseHandler<T> {

    private PrismContext prismContext;

    public HttpClientPrismResponseHandler(PrismContext ctx) {
        super();
        this.prismContext = ctx;
    }

    @Override
    public T handleResponse(ClassicHttpResponse classicHttpResponse) throws HttpException, IOException {

        try {
            return (T) prismContext.parserFor(classicHttpResponse.getEntity().getContent()).language("xml").parseRealValue();
        } catch (SchemaException e) {
            throw new IOException(e);
        } finally {
            classicHttpResponse.close();
        }
//        return null;
    }
}
