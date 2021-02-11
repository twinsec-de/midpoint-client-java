package com.evolveum.midpoint.client.impl.prism.handler;

import com.evolveum.midpoint.prism.Containerable;

import com.evolveum.midpoint.prism.PrismContext;

import com.evolveum.midpoint.util.exception.SchemaException;

import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.AbstractHttpEntity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PrismHttpEntity extends AbstractHttpEntity {

    private Containerable containerable;
    private PrismContext prismContext;

    protected PrismHttpEntity(Containerable containerable, String contentType, String contentEncoding) {
        super(contentType, contentEncoding);
        this.containerable = containerable;
    }

    public PrismHttpEntity(Containerable containerable, PrismContext prismContext, ContentType contentType, String contentEncoding) {
        super(contentType, contentEncoding);
        this.containerable = containerable;
        this.prismContext = prismContext;
    }

    @Override
    public InputStream getContent() throws IOException, UnsupportedOperationException {
        try {
            String serialized = prismContext.xmlSerializer().serializeRealValue(containerable);
            return new ByteArrayInputStream(serialized.getBytes());
        } catch (SchemaException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
    }

    @Override
    public boolean isStreaming() {
        return true;
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public long getContentLength() {
        return 50000;
    }
}
