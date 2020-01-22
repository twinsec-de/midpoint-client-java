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

import com.evolveum.midpoint.client.api.ExecuteScriptRpcService;
import com.evolveum.midpoint.client.api.TaskFuture;
import com.evolveum.midpoint.client.api.exception.*;
import com.evolveum.midpoint.xml.ns._public.common.api_types_3.ExecuteScriptResponseType;
import com.evolveum.midpoint.xml.ns._public.common.common_3.OperationResultType;
import com.evolveum.midpoint.xml.ns._public.model.scripting_3.ExecuteScriptType;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;

/**
 * Preliminary implementation. Adapt as necessary.
 *
 * @author mederly
 */
public class RestJaxbExecuteScriptRpcService implements ExecuteScriptRpcService {

	private RestJaxbService service;
	private String path;

	private ExecuteScriptType script;

	public RestJaxbExecuteScriptRpcService(RestJaxbService service, String path, ExecuteScriptType script) {
		this.service = service;
		this.path = path;
		this.script = script;
	}

	@Override
	public TaskFuture<ExecuteScriptResponseType> apost() throws CommonException {
		Response response = service.post(path, script);

		switch (response.getStatus()) {
			case 200:
				ExecuteScriptResponseType executeScriptResponse = response.readEntity(ExecuteScriptResponseType.class);
				return new RestJaxbCompletedFuture<>(executeScriptResponse);
			case 409:
				OperationResultType operationResultType = response.readEntity(OperationResultType.class);
				throw new PolicyViolationException(operationResultType.getMessage());
			default:
				throw new UnsupportedOperationException("Implement other status codes, unsupported return status: " + response.getStatus());
		}

	}
}
