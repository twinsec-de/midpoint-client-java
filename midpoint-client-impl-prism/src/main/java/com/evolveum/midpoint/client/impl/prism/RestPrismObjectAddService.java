package com.evolveum.midpoint.client.impl.prism;

import com.evolveum.midpoint.client.api.ObjectAddService;
import com.evolveum.midpoint.client.api.ObjectReference;
import com.evolveum.midpoint.client.api.TaskFuture;
import com.evolveum.midpoint.client.api.exception.CommonException;
import com.evolveum.midpoint.client.api.exception.ObjectAlreadyExistsException;
import com.evolveum.midpoint.client.api.exception.SchemaException;
import com.evolveum.midpoint.schema.constants.ObjectTypes;
import com.evolveum.midpoint.xml.ns._public.common.common_3.ObjectType;

import java.io.IOException;

public class RestPrismObjectAddService<O extends ObjectType> implements ObjectAddService<O> {

    private RestPrismService service;
    private ObjectTypes type;
    private O object;

    public RestPrismObjectAddService(RestPrismService service, ObjectTypes type, O object) {
        this.service = service;
        this.type = type;
        this.object = object;
    }

    @Override
    public TaskFuture<ObjectReference<O>> apost() throws ObjectAlreadyExistsException, SchemaException {

        String oid = service.addObject(type, object);

        RestPrismObjectReference ref = new RestPrismObjectReference(oid, type.getClassDefinition());
        return new RestPrismCompletedFuture<>(ref);
    }
}
