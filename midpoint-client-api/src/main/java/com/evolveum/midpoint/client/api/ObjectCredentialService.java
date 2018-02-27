package com.evolveum.midpoint.client.api;

import com.evolveum.midpoint.client.api.verb.Post;
import com.evolveum.midpoint.xml.ns._public.common.api_types_3.ExecuteCredentialResetRequestType;
import com.evolveum.midpoint.xml.ns._public.common.api_types_3.ExecuteCredentialResetResponseType;
import com.evolveum.midpoint.xml.ns._public.common.common_3.ObjectType;

/**
 * Description
 *
 * @author Jake Morris - jake
 * @version 1.0
 * @since 1.0
 */
public interface ObjectCredentialService<O extends ObjectType> extends Post<ExecuteCredentialResetResponseType>
{
    ObjectCredentialService<O> executeResetPassword(ExecuteCredentialResetRequestType executeCredentialResetRequest);
}
