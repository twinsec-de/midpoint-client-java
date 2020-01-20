/**
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

import com.evolveum.midpoint.client.api.ObjectCredentialService;
import com.evolveum.midpoint.client.api.TaskFuture;
import com.evolveum.midpoint.client.api.exception.*;
import com.evolveum.midpoint.xml.ns._public.common.api_types_3.ExecuteCredentialResetResponseType;
import com.evolveum.midpoint.xml.ns._public.common.common_3.ObjectType;
import com.evolveum.midpoint.xml.ns._public.common.api_types_3.ExecuteCredentialResetRequestType;
import com.evolveum.midpoint.xml.ns._public.common.common_3.OperationResultType;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Jakmor
 *
 */
public class RestJaxbObjectCredentialService<O extends ObjectType>  extends AbstractObjectWebResource<O> implements ObjectCredentialService<O>
{
    private ExecuteCredentialResetRequestType executeCredentialResetRequest;

    public RestJaxbObjectCredentialService(final RestJaxbService service, final Class<O> type, final String oid)
    {
        super(service, type, oid);
    }

    @Override
    public ObjectCredentialService<O> executeResetPassword(ExecuteCredentialResetRequestType executeCredentialResetRequest)
    {
        this.executeCredentialResetRequest = executeCredentialResetRequest;

        return this;
    }

    @Override
    public TaskFuture<ExecuteCredentialResetResponseType> apost() throws CommonException
    {
        String oid = getOid();
        String restPath = RestUtil.subUrl(Types.findType(getType()).getRestPath(), oid);

        restPath = restPath.concat("/credential");

        Response response = getService().getClient().path(restPath).post(executeCredentialResetRequest);

        switch (response.getStatus()) {
            case 200:
                ExecuteCredentialResetResponseType executeCredentialResetResponse = response.readEntity(ExecuteCredentialResetResponseType.class);

                return new RestJaxbCompletedFuture<>(executeCredentialResetResponse);
            case 400:
                throw new BadRequestException(response.getStatusInfo().getReasonPhrase());
            case 401:
                throw new AuthenticationException(response.getStatusInfo().getReasonPhrase());
            case 403:
                throw new AuthorizationException(response.getStatusInfo().getReasonPhrase());
                //TODO: Do we want to return a reference? Might be useful.
            case 404:
                throw new ObjectNotFoundException(response.getStatusInfo().getReasonPhrase());
            case 409:
                OperationResultType operationResultType = response.readEntity(OperationResultType.class);
                throw new PolicyViolationException(operationResultType.getMessage());
            default:
                throw new UnsupportedOperationException("Implement other status codes, unsupported return status: " + response.getStatus());
        }
    }
}
