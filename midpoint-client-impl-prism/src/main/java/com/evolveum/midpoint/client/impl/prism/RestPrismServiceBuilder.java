package com.evolveum.midpoint.client.impl.prism;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.xml.sax.SAXException;

import com.evolveum.midpoint.prism.PrismContext;
import com.evolveum.midpoint.prism.util.PrismContextFactory;
import com.evolveum.midpoint.schema.MidPointPrismContextFactory;
import com.evolveum.midpoint.util.exception.SchemaException;

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
        UsernamePasswordCredentials usernamePasswordCredentials = new UsernamePasswordCredentials(username, password.toCharArray());



        RestPrismService service = new RestPrismService(usernamePasswordCredentials, baseUrl, prismContext);
        return service;
    }



}
