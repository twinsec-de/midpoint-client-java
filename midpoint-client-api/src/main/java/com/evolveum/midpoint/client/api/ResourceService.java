package com.evolveum.midpoint.client.api;

import com.evolveum.midpoint.client.api.exception.AuthenticationException;
import com.evolveum.midpoint.client.api.exception.ObjectNotFoundException;
import com.evolveum.midpoint.xml.ns._public.common.common_3.OperationResultType;
import com.evolveum.midpoint.xml.ns._public.common.common_3.ResourceType;

/**
 * @author Viliam Repan (lazyman)
 */
public interface ResourceService extends ObjectService<ResourceType> {

    OperationResultType test() throws ObjectNotFoundException, AuthenticationException;
}
