/**
 * Copyright (c) 2017-2018 Evolveum
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.evolveum.midpoint.client.impl.restjaxb;

import com.evolveum.midpoint.client.api.ResourceService;
import com.evolveum.midpoint.client.api.exception.AuthenticationException;
import com.evolveum.midpoint.client.api.exception.ObjectNotFoundException;
import com.evolveum.midpoint.xml.ns._public.common.common_3.OperationResultType;
import com.evolveum.midpoint.xml.ns._public.common.common_3.ResourceType;
import org.apache.cxf.jaxrs.client.WebClient;

import javax.ws.rs.core.Response;

/**
 * @author Viliam Repan (lazyman)
 */
public class RestJaxbResourceService extends RestJaxbObjectService<ResourceType> implements ResourceService {

	public RestJaxbResourceService(final RestJaxbService service, final String oid) {
		super(service, ResourceType.class, oid);
	}

	@Override
	public OperationResultType test() throws ObjectNotFoundException, AuthenticationException {
		String urlPrefix = RestUtil.subUrl(Types.findType(ResourceType.class).getRestPath(), getOid(), "test");

		WebClient client = getService().getClient();
		Response response = client.path(urlPrefix).post(null);

		if (Response.Status.OK.getStatusCode() == response.getStatus()) {
			return response.readEntity(OperationResultType.class);
		}

		if (Response.Status.NOT_FOUND.getStatusCode() == response.getStatus()) {
			throw new ObjectNotFoundException("Cannot test resource. No such resource");
		}
		return null;
	}
}
