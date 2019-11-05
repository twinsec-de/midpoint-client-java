package com.evolveum.midpoint.client.impl.prism;

import com.evolveum.midpoint.client.api.*;
import com.evolveum.midpoint.client.api.exception.AuthenticationException;
import com.evolveum.midpoint.client.api.scripting.ScriptingUtil;
import com.evolveum.midpoint.prism.PrismContext;
import com.evolveum.midpoint.schema.constants.ObjectTypes;
import com.evolveum.midpoint.xml.ns._public.common.common_3.*;
import org.springframework.web.reactive.function.client.WebClient;

public class RestPrismService implements Service {

    private PrismContext prismContext;
    private WebClient webClient;

    RestPrismService (WebClient client, PrismContext prismContext) {
        this.webClient = client;
        this.prismContext = prismContext;
    }

    public WebClient getClient() {
        return webClient;
    }


    @Override
    public ObjectCollectionService<UserType> users() {
        return new RestPrismObjectCollectionService<>(this, ObjectTypes.USER);
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
    public ObjectCollectionService<TaskType> tasks() {
        return null;
    }

    @Override
    public ShadowCollectionService shadows() {
        return null;
    }

    @Override
    public ObjectCollectionService<RoleType> roles() {
        return null;
    }

    @Override
    public ObjectCollectionService<OrgType> orgs() {
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
