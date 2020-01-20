/*
 * Copyright (c) 2017-2018 Evolveum
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.evolveum.midpoint.client.impl.restjaxb;

import java.io.IOException;
import java.lang.reflect.Type;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.bind.Unmarshaller;

import com.evolveum.midpoint.client.api.*;
import com.evolveum.midpoint.xml.ns._public.common.common_3.*;
import com.evolveum.midpoint.client.api.scripting.ScriptingUtil;
import com.evolveum.midpoint.client.impl.restjaxb.scripting.ScriptingUtilImpl;

import org.apache.cxf.jaxrs.client.ClientConfiguration;
import org.apache.cxf.jaxrs.client.WebClient;

import com.evolveum.midpoint.client.api.exception.AuthenticationException;
import com.evolveum.midpoint.client.api.exception.ObjectNotFoundException;

/**
 * @author semancik
 * @author katkav
 *
 */
public class RestJaxbService implements Service {

	private static final String IMPERSONATE_HEADER = "Switch-To-Principal";
	private final ServiceUtil util;
	private final ScriptingUtil scriptingUtil;

	private String endpoint;

	// TODO: jaxb context
	
//	private WebClient client;
	private DomSerializer domSerializer;
	private JAXBContext jaxbContext;
	private AuthenticationManager<?> authenticationManager;
	private List<AuthenticationType> supportedAuthenticationsByServer;
	
	public WebClient getClient() {
		CustomAuthNProvider<?> authNProvider = new CustomAuthNProvider<>(authenticationManager, this);
		WebClient client = WebClient.create(endpoint, Arrays.asList(new JaxbXmlProvider<>(jaxbContext)));
		ClientConfiguration config = WebClient.getConfig(client);
		config.getInInterceptors().add(authNProvider);
		config.getInFaultInterceptors().add(authNProvider);
		client.accept(MediaType.APPLICATION_XML);
		client.type(MediaType.APPLICATION_XML);

		if (authenticationManager != null) {
			client.header("Authorization", authenticationManager.createAuthorizationHeader());
		}

		return client;
	}
	
	public DomSerializer getDomSerializer() {
		return domSerializer;
	}
	
	public JAXBContext getJaxbContext() {
		return jaxbContext;
	}
	
	public List<AuthenticationType> getSupportedAuthenticationsByServer() {
		if (supportedAuthenticationsByServer == null) {
			supportedAuthenticationsByServer = new ArrayList<>();
		}
		return supportedAuthenticationsByServer;
	}
	
	
	@SuppressWarnings("unchecked")
	public <T extends AuthenticationChallenge> AuthenticationManager<T> getAuthenticationManager() {
		return (AuthenticationManager<T>) authenticationManager;
	}
	
	public RestJaxbService() {
		super();
//		client = WebClient.create("");
		util = new RestJaxbServiceUtil(null);
		scriptingUtil = new ScriptingUtilImpl(util);
	}
	
	RestJaxbService(String endpoint, String username, String password, AuthenticationType authentication, List<SecurityQuestionAnswer> secQ) throws IOException {
		super();
		try {
			jaxbContext = createJaxbContext();
		} catch (JAXBException e) {
			throw new IOException(e);
		}
		this.endpoint = endpoint;
		
		if (AuthenticationType.SECQ == authentication) {
			authenticationManager = new SecurityQuestionAuthenticationManager(username, secQ);
		} else if (authentication != null ){
			authenticationManager = new BasicAuthenticationManager(username, password);
		}

		util = new RestJaxbServiceUtil(jaxbContext);
		scriptingUtil = new ScriptingUtilImpl(util);
		domSerializer = new DomSerializer(jaxbContext);
	}

	@Override
	public Service impersonate(String oid){
		getClient().header(IMPERSONATE_HEADER, oid);
		return this;
	}

	@Override
	public Service addHeader(String header, String value){
		getClient().header(header, value);
		return this;
	}
	

	@Override
	public ObjectCollectionService<UserType> users() {
		return new RestJaxbObjectCollectionService<>(this, Types.USERS.getRestPath(), UserType.class);
	}

	@Override
	public ObjectCollectionService<ValuePolicyType> valuePolicies() {
		return new RestJaxbObjectCollectionService<>(this, Types.VALUE_POLICIES.getRestPath(), ValuePolicyType.class);
	}

	@Override
	public ObjectCollectionService<SecurityPolicyType> securityPolicies() {
		return new RestJaxbObjectCollectionService<>(this, Types.SECURITY_POLICIES.getRestPath(), SecurityPolicyType.class);
	}

	@Override
	public ObjectCollectionService<ConnectorType> connectors() {
		return new RestJaxbObjectCollectionService<>(this, Types.CONNECTORS.getRestPath(), ConnectorType.class);
	}

	@Override
	public ObjectCollectionService<ConnectorHostType> connectorHosts() {
		return new RestJaxbObjectCollectionService<>(this, Types.CONNECTOR_HOSTS.getRestPath(), ConnectorHostType.class);
	}

	@Override
	public ObjectCollectionService<GenericObjectType> genericObjects() {
		return new RestJaxbObjectCollectionService<>(this, Types.GENERIC_OBJECTS.getRestPath(), GenericObjectType.class);
	}

	@Override
	public ResourceCollectionService resources() {
		return new RestJaxbResourceCollectionService(this);
	}

	@Override
	public ObjectCollectionService<ObjectTemplateType> objectTemplates() {
		return new RestJaxbObjectCollectionService<>(this, Types.OBJECT_TEMPLATES.getRestPath(), ObjectTemplateType.class);
	}

	@Override
	public ObjectCollectionService<SystemConfigurationType> systemConfigurations() {
		return new RestJaxbObjectCollectionService<>(this, Types.SYSTEM_CONFIGURATIONS.getRestPath(), SystemConfigurationType.class);
	}

	@Override
	public ObjectCollectionService<TaskType> tasks() {
		return new RestJaxbObjectCollectionService<>(this, Types.TASKS.getRestPath(), TaskType.class);
	}

	@Override
	public ShadowCollectionService shadows() {
		return new RestJaxbShadowCollectionService(this);
	}

	@Override
	public ObjectCollectionService<RoleType> roles() {
		return new RestJaxbObjectCollectionService<>(this, Types.ROLES.getRestPath(), RoleType.class);
	}

	@Override
	public ObjectCollectionService<OrgType> orgs() {
		return new RestJaxbObjectCollectionService<>(this, Types.ORGS.getRestPath(), OrgType.class);
	}

	@Override
	public <T> RpcService<T> rpc() {
		return new RestJaxbRpcService<>(this);
	}

	@Override
	public ServiceUtil util() {
		return util;
	}

	@Override
	public ScriptingUtil scriptingUtil() {
		return scriptingUtil;
	}

	<O extends ObjectType> O getObject(final Class<O> type, final String oid)
			throws ObjectNotFoundException, AuthenticationException {
		return getObject(type, oid, null, null, null);
	}

	/**
	 * Used frequently at several places. Therefore unified here.
	 * @throws ObjectNotFoundException 
	 */
	<O extends ObjectType> O getObject(final Class<O> type, final String oid, List<String> options,
									   List<String> include, List<String> exclude)
			throws ObjectNotFoundException, AuthenticationException {

		String urlPrefix = RestUtil.subUrl(Types.findType(type).getRestPath(), oid);
		WebClient cli = getClient().path(urlPrefix);
		addQueryParameter(cli, "options", options);
		addQueryParameter(cli, "include", include);
		addQueryParameter(cli, "exclude", exclude);

		Response response = cli.get();

		if (Status.OK.getStatusCode() == response.getStatus() ) {
			return response.readEntity(type);
		}
		
		if (Status.NOT_FOUND.getStatusCode() == response.getStatus()) {
			throw new ObjectNotFoundException("Cannot get object with oid" + oid + ". Object doesn't exist");
		}
		
		if (Status.UNAUTHORIZED.getStatusCode() == response.getStatus()) {
			throw new AuthenticationException(response.getStatusInfo().getReasonPhrase());
		}
		
		return null;
	}

	private void addQueryParameter(WebClient client, String name, List<String> values) {
		if (values == null || values.isEmpty()) {
			return;
		}

		for (String value : values) {
			client.query(name, value);
		}
	}

	<O extends ObjectType> void deleteObject(final Class<O> type, final String oid) throws ObjectNotFoundException, AuthenticationException {
		String urlPrefix = RestUtil.subUrl(Types.findType(type).getRestPath(), oid);
		Response response = getClient().path(urlPrefix).delete();

		//TODO: Looks like midPoint returns a 204 and not a 200 on success
		if (Status.OK.getStatusCode() == response.getStatus() ) {
			//TODO: Do we want to return anything on successful delete or just remove this if block?
		}

		if (Status.NO_CONTENT.getStatusCode() == response.getStatus() ) {
			//TODO: Do we want to return anything on successful delete or just remove this if block?
		}


		if (Status.BAD_REQUEST.getStatusCode() == response.getStatus()) {
			throw new BadRequestException("Bad request");
		}

		if (Status.NOT_FOUND.getStatusCode() == response.getStatus()) {
			throw new ObjectNotFoundException("Cannot delete object with oid" + oid + ". Object doesn't exist");
		}

		if (Status.UNAUTHORIZED.getStatusCode() == response.getStatus()) {
			throw new AuthenticationException("Cannot authentication user");
		}
	}

	@Override
	public UserType self() throws AuthenticationException{
		String urlPrefix = "/self";
		Response response = getClient().path(urlPrefix).get();


		if (Status.OK.getStatusCode() == response.getStatus() ) {
			return response.readEntity(UserType.class);
		}

		if (Status.BAD_REQUEST.getStatusCode() == response.getStatus()) {
			throw new BadRequestException("Bad request");
		}

		if (Status.UNAUTHORIZED.getStatusCode() == response.getStatus()) {
			throw new AuthenticationException("Cannot authentication user");
		}
		return null;
	}

	private JAXBContext createJaxbContext() throws JAXBException {
		return JAXBContext.newInstance("com.evolveum.midpoint.xml.ns._public.common.api_types_3:"
				+ "com.evolveum.midpoint.xml.ns._public.common.audit_3:"
				+ "com.evolveum.midpoint.xml.ns._public.common.common_3:"
				+ "com.evolveum.midpoint.xml.ns._public.connector.icf_1.connector_extension_3:"
				+ "com.evolveum.midpoint.xml.ns._public.connector.icf_1.connector_schema_3:"
				+ "com.evolveum.midpoint.xml.ns._public.connector.icf_1.resource_schema_3:"
				+ "com.evolveum.midpoint.xml.ns._public.model.extension_3:"
				+ "com.evolveum.midpoint.xml.ns._public.model.scripting_3:"
				+ "com.evolveum.midpoint.xml.ns._public.model.scripting.extension_3:"
				+ "com.evolveum.midpoint.xml.ns._public.report.extension_3:"
				+ "com.evolveum.midpoint.xml.ns._public.resource.capabilities_3:"
				+ "com.evolveum.midpoint.xml.ns._public.task.extension_3:"
				+ "com.evolveum.midpoint.xml.ns._public.task.jdbc_ping.handler_3:"
				+ "com.evolveum.midpoint.xml.ns._public.task.noop.handler_3:"
				+ "com.evolveum.prism.xml.ns._public.annotation_3:"
				+ "com.evolveum.prism.xml.ns._public.query_3:"
				+ "com.evolveum.prism.xml.ns._public.types_3");
	}
}
