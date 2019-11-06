package com.evolveum.midpoint.client.impl.prism;

import com.evolveum.midpoint.prism.PrismContext;
import com.evolveum.midpoint.util.exception.SchemaException;
import com.evolveum.midpoint.xml.ns._public.common.common_3.ObjectType;
import org.reactivestreams.Publisher;
import org.springframework.core.ResolvableType;
import org.springframework.core.codec.AbstractDataBufferDecoder;
import org.springframework.core.codec.AbstractDecoder;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpInputMessage;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.util.MimeType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MidpointDecoder<T> extends AbstractDataBufferDecoder<T> {

    private PrismContext prismContext;

    MidpointDecoder(PrismContext prismContext) {
        this.prismContext = prismContext;
    }

    @Override
    public boolean canDecode(ResolvableType elementType, MimeType mimeType) {
        return true;
    }
    
    @Override
    protected T decodeDataBuffer(DataBuffer dataBuffer, ResolvableType resolvableType, MimeType mimeType, Map<String, Object> map) {
        InputStream is = dataBuffer.asInputStream();

        if (ObjectType.class.isAssignableFrom(resolvableType.getRawClass())) {
            try {
                return (T) prismContext.parserFor(is).xml().parse().asObjectable();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SchemaException e) {
                e.printStackTrace();
            }
        } else {
            try {
                return (T) prismContext.parserFor(is).xml().parseRealValue();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SchemaException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
