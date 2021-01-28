package com.evolveum.midpoint.client.impl.prism;

import com.evolveum.midpoint.client.api.Focus;
import com.evolveum.midpoint.client.api.FocusPolicyService;
import com.evolveum.midpoint.client.api.FocusService;
import com.evolveum.midpoint.client.api.ValidateGenerateRpcService;
import com.evolveum.midpoint.schema.constants.ObjectTypes;
import com.evolveum.midpoint.xml.ns._public.common.common_3.CredentialsPolicyType;
import com.evolveum.midpoint.xml.ns._public.common.common_3.FocusType;

public class RestPrismFocusService<F extends FocusType> extends RestPrismObjectService<F> implements FocusService<F> {


    public RestPrismFocusService(RestPrismService service, ObjectTypes type, String oid) {
        super(service, type, oid);
    }

    @Override
    public Focus<F> credential() {
        return null;
    }

    @Override
    public ValidateGenerateRpcService generate() {
        return null;
    }

    @Override
    public ValidateGenerateRpcService validate() {
        return null;
    }

    @Override
    public FocusPolicyService<CredentialsPolicyType> credentialsPolicy() {
        return null;
    }


}
