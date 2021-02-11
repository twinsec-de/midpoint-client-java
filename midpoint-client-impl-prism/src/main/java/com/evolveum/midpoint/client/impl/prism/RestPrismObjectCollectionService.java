package com.evolveum.midpoint.client.impl.prism;

import com.evolveum.midpoint.client.api.ObjectAddService;
import com.evolveum.midpoint.client.api.ObjectCollectionService;
import com.evolveum.midpoint.client.api.ObjectService;
import com.evolveum.midpoint.client.api.SearchService;
import com.evolveum.midpoint.schema.constants.ObjectTypes;
import com.evolveum.midpoint.xml.ns._public.common.common_3.ObjectType;

public class RestPrismObjectCollectionService<O extends ObjectType> extends CommonPrismService implements ObjectCollectionService<O> {

    public RestPrismObjectCollectionService(RestPrismService service, ObjectTypes type) {
        super(service, type);
    }

    @Override
    public ObjectService<O> oid(String oid) {
        return new RestPrismObjectService<>(getService(), getType(), oid);

    }

    @Override
    public SearchService<O> search() {
        return null;
    }

    @Override
    public ObjectAddService<O> add(O object) {
        return new RestPrismObjectAddService<>(getService(), getType(), object);
    }
}
