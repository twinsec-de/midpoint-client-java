package com.evolveum.midpoint.client.impl.prism;

import com.evolveum.midpoint.prism.PrismContext;
import com.evolveum.midpoint.prism.util.PrismContextFactory;
import com.evolveum.midpoint.schema.MidPointPrismContextFactory;
import com.evolveum.midpoint.util.exception.SchemaException;
import org.springframework.http.MediaType;
import org.springframework.http.codec.DecoderHttpMessageReader;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.xml.sax.SAXException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class RestPrismServiceBuilder {

    private PrismContext prismContext;
    private String baseUrl;
    private String username;
    private String password;

    public static RestPrismServiceBuilder create() throws FileNotFoundException, com.evolveum.midpoint.client.api.exception.SchemaException {
        RestPrismServiceBuilder builder = new RestPrismServiceBuilder();
        PrismContextFactory pcf = new MidPointPrismContextFactory();
        try {
            builder.prismContext = pcf.createPrismContext();
            builder.prismContext.initialize();
        } catch (SchemaException | SAXException | IOException e) {
            throw new com.evolveum.midpoint.client.api.exception.SchemaException(e);
        }

        return builder;
    }

    public RestPrismServiceBuilder baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public RestPrismServiceBuilder username(String username) {
        this.username = username;
        return this;
    }

    public RestPrismServiceBuilder password(String password) {
        this.password = password;
        return this;
    }

    public RestPrismService build() {
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codec -> {
                        codec.registerDefaults(false);
                        codec.customCodecs().decoder(new MidpointDecoder(prismContext));
                }).build();

        WebClient client = WebClient.builder()
                .baseUrl(baseUrl)
                .filter(ExchangeFilterFunctions.basicAuthentication(username, password))
                .defaultHeaders(h -> {
                    h.setAccept(Arrays.asList(MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON));
                    h.setContentType(MediaType.APPLICATION_XML);
                })
                .exchangeStrategies(strategies)
                .build();

        RestPrismService service = new RestPrismService(client, prismContext);
        return service;
    }



}
