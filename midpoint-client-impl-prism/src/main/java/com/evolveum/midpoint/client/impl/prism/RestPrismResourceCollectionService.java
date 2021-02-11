package com.evolveum.midpoint.client.impl.prism;

import com.evolveum.midpoint.client.api.ResourceCollectionService;
import com.evolveum.midpoint.client.api.ResourceService;
import com.evolveum.midpoint.schema.constants.ObjectTypes;
import com.evolveum.midpoint.xml.ns._public.common.common_3.ResourceType;

public class RestPrismResourceCollectionService extends RestPrismObjectCollectionService<ResourceType> implements ResourceCollectionService {

    public RestPrismResourceCollectionService(RestPrismService service) {
        super(service, ObjectTypes.RESOURCE);
    }


    @Override
    public ResourceService oid(String oid) {
        return new RestPrismResourceService(getService(), getType(), oid);
    }


}
