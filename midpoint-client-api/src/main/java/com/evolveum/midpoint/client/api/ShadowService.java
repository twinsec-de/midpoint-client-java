package com.evolveum.midpoint.client.api;

import com.evolveum.midpoint.client.api.exception.AuthenticationException;
import com.evolveum.midpoint.client.api.exception.ObjectNotFoundException;
import com.evolveum.midpoint.xml.ns._public.common.common_3.ShadowType;

/**
 * @author Viliam Repan (lazyman)
 */
public interface ShadowService extends ObjectService<ShadowType> {

    ObjectReference<ShadowType> importShadow() throws ObjectNotFoundException, AuthenticationException;
}
