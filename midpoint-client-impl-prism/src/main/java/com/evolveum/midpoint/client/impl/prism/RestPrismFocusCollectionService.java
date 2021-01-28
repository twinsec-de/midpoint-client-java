package com.evolveum.midpoint.client.impl.prism;

import com.evolveum.midpoint.client.api.FocusCollectionService;
import com.evolveum.midpoint.client.api.FocusService;
import com.evolveum.midpoint.client.api.ObjectAddService;
import com.evolveum.midpoint.client.api.SearchService;
import com.evolveum.midpoint.schema.constants.ObjectTypes;
import com.evolveum.midpoint.xml.ns._public.common.common_3.FocusType;
import com.evolveum.midpoint.xml.ns._public.common.common_3.ObjectType;

public class RestPrismFocusCollectionService<F extends FocusType> extends RestPrismObjectCollectionService<F> implements FocusCollectionService<F> {

    public RestPrismFocusCollectionService(RestPrismService service, ObjectTypes type) {
        super(service, type);
    }

    @Override
    public FocusService<F> oid(String oid) {
        return new RestPrismFocusService<>(getService(), getType(), oid);
    }
}
