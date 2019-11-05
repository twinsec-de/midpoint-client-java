package com.evolveum.midpoint.client.impl.prism;

import com.evolveum.midpoint.schema.constants.ObjectTypes;

public class CommonPrismService {

    private RestPrismService service;
    private ObjectTypes type;

    public CommonPrismService(RestPrismService service) {
        this.service = service;
    }

    public CommonPrismService(RestPrismService service, ObjectTypes type) {
        this.service = service;
        this.type = type;
    }

    public RestPrismService getService() {
        return service;
    }

    public ObjectTypes getType() {
        return type;
    }
}
