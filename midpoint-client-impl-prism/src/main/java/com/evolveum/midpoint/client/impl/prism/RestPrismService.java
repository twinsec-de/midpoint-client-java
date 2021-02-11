package com.evolveum.midpoint.client.impl.prism;

import java.io.IOException;
import java.net.URI;

import com.evolveum.midpoint.prism.PrismObject;

import com.evolveum.midpoint.schema.result.OperationResult;

import org.apache.commons.lang.StringUtils;
import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.client5.http.fluent.Response;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.*;
import org.apache.hc.core5.http.io.entity.StringEntity;

import com.evolveum.midpoint.client.api.*;
import com.evolveum.midpoint.client.api.exception.*;
import com.evolveum.midpoint.client.api.scripting.ScriptingUtil;
import com.evolveum.midpoint.client.impl.prism.handler.HttpClientPrismResponseHandler;
import com.evolveum.midpoint.prism.PrismContext;
import com.evolveum.midpoint.schema.constants.ObjectTypes;
import com.evolveum.midpoint.xml.ns._public.common.common_3.*;

public class RestPrismService implements Service {

    private PrismContext prismContext;
    private UsernamePasswordCredentials credentials;
    private String baseUrl;


    RestPrismService (UsernamePasswordCredentials credentials, String baseUrl, PrismContext prismContext) {
        this.credentials = credentials;
        this.prismContext = prismContext;
        this.baseUrl = baseUrl;
    }

    <O extends ObjectType> O getObject(ObjectTypes type, String oid) throws ObjectNotFoundException, SchemaException {

        try {
            String fullPath = baseUrl + "/" + type.getRestType() + "/" + oid;
            Response response = httpGet(fullPath);

            ClassicHttpResponse httpResponse = (ClassicHttpResponse) response.returnResponse();
            switch (httpResponse.getCode()) {
                case 240:
                case 250:
                case HttpStatus.SC_OK :
                    return parseObject(httpResponse.getEntity());
                case HttpStatus.SC_NOT_FOUND:
                    OperationResult result = getOperationResult(httpResponse.getEntity());
                    if (result.getCause() != null) {

                    }
                    String message = result.getMessage();
                    throw new ObjectNotFoundException("Cannot find object located at: " + fullPath + ", " + message);
                case HttpStatus.SC_FORBIDDEN:
                    throw new SecurityViolationException("Cannot read object, " + httpResponse.getReasonPhrase());
//                default:
//                    parseOperationResult(httpResponse.getEntity());
            }
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }


        return null;
    }

    private <O extends ObjectType> O parseObject(HttpEntity httpEntity) throws SchemaException {
        PrismObject<O> object;
        try {

            object = prismContext.parserFor(httpEntity.getContent()).language(getParserLanguage(httpEntity.getContentType())).parse();
        } catch (com.evolveum.midpoint.util.exception.SchemaException | IOException e) {
            throw new SchemaException(e.getMessage(), e);
        }
        if (object == null) {
            return null;
        }
        return object.asObjectable();
    }

    private OperationResult getOperationResult(HttpEntity httpEntity) throws SchemaException {
        OperationResultType result = parseOperationResult(httpEntity);
        return OperationResult.createOperationResult(result);
    }

    private OperationResultType parseOperationResult(HttpEntity httpEntity) throws SchemaException {
        try {
            return prismContext.parserFor(httpEntity.getContent()).language(getParserLanguage(httpEntity.getContentType())).parseRealValue(OperationResultType.class);
        } catch (IOException | com.evolveum.midpoint.util.exception.SchemaException e) {
            throw new SchemaException(e.getMessage(), e);
        }
    }

    private String getParserLanguage(String contentType) {
        if (ContentType.APPLICATION_XML.equals(contentType)) {
            return "xml";
        }
        if (ContentType.APPLICATION_JSON.equals(contentType)) {
            return "json";
        }
        if ("application/yaml".equals(contentType)) {
            return "yaml";
        }
        return "xml";
    }

    Response httpGet(String fullPath) throws IOException {
        CloseableHttpClient client = createClient();
        return Request.get(fullPath).execute(client);
    }

    Response httpPost(String fullPath, HttpEntity object) throws IOException {
        CloseableHttpClient client = createClient();
        return Request.post(fullPath).body(object).execute(client);
    }

    String addObject(ObjectTypes type, ObjectType object) throws ObjectAlreadyExistsException, SchemaException {
        String fullPath = baseUrl + "/" + type.getRestType();
        try {
            Response response = httpPost(fullPath, createEntity(object));

            CloseableHttpResponse httpResponse = (CloseableHttpResponse) response.returnResponse();
            switch (httpResponse.getCode()) {
                case HttpStatus.SC_OK:
                case HttpStatus.SC_CREATED:
                case HttpStatus.SC_ACCEPTED:
                case 240:
                case 250:
                    try {
                        Header header = httpResponse.getHeader("Location");
                        if (header == null) {
                            throw new IllegalStateException("No location returned, something went wrong");
                        }
                        String location = header.getValue();
                        if (StringUtils.isNotBlank(location)) {
                            return location.substring(location.lastIndexOf("/") + 1);
                        }
                    } catch (ProtocolException e) {
                        throw new IllegalStateException("Unexpected header found: " + e.getMessage(), e);
                    }
                    return null;
                case HttpStatus.SC_CONFLICT:
                    OperationResult result = getOperationResult(httpResponse.getEntity());
                    throw new ObjectAlreadyExistsException(result.getMessage());
                case HttpStatus.SC_FORBIDDEN:
                    throw new SecurityViolationException("User not authorized for " + fullPath);
                default:
                    throw new UnsupportedOperationException("Not impelemnted yet: " + httpResponse);
            }
        } catch (IOException e) {
            throw new SchemaException(e.getMessage(), e);
        }

    }

    private <O extends ObjectType> StringEntity createEntity(O object) throws SchemaException {
        try {
            return new StringEntity(prismContext.xmlSerializer().serializeRealValue(object), ContentType.APPLICATION_XML);
        } catch (com.evolveum.midpoint.util.exception.SchemaException e) {
            throw new SchemaException(e);
        }
    }

    private CloseableHttpClient createClient() {
        BasicCredentialsProvider provider = new BasicCredentialsProvider();

        URI uri = URI.create(baseUrl);
        HttpHost httpHost = HttpHost.create(uri);
        provider.setCredentials(new AuthScope(httpHost), credentials);
        return HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
    }


    @Override
    public FocusCollectionService<UserType> users() {
        return new RestPrismFocusCollectionService<>(this, ObjectTypes.USER);
    }

    @Override
    public <T> RpcService<T> rpc() {
        return null;
    }

    @Override
    public ObjectCollectionService<ValuePolicyType> valuePolicies() {
        return null;
    }

    @Override
    public UserType self() throws AuthenticationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Service impersonate(String oid) {
        return null;
    }

    @Override
    public Service addHeader(String header, String value) {
        return null;
    }

    @Override
    public ObjectCollectionService<SecurityPolicyType> securityPolicies() {
        return null;
    }

    @Override
    public ObjectCollectionService<ConnectorType> connectors() {
        return null;
    }

    @Override
    public ObjectCollectionService<ConnectorHostType> connectorHosts() {
        return null;
    }

    @Override
    public ObjectCollectionService<GenericObjectType> genericObjects() {
        return null;
    }

    @Override
    public ResourceCollectionService resources() {
        return null;
    }

    @Override
    public ObjectCollectionService<ObjectTemplateType> objectTemplates() {
        return null;
    }

    @Override
    public ObjectCollectionService<SystemConfigurationType> systemConfigurations() {
        return null;
    }

    @Override
    public TaskCollectionService tasks() {
        return null;
    }

    @Override
    public ShadowCollectionService shadows() {
        return null;
    }

    @Override
    public FocusCollectionService<RoleType> roles() {
        return null;
    }

    @Override
    public FocusCollectionService<OrgType> orgs() {
        return null;
    }

    @Override
    public ServiceUtil util() {
        return null;
    }

    @Override
    public ScriptingUtil scriptingUtil() {
        return null;
    }
}
