package com.evolveum.midpoint.client.impl.prism;

import java.io.IOException;
import java.util.List;

import com.evolveum.midpoint.client.api.ObjectModifyService;
import com.evolveum.midpoint.client.api.ObjectService;
import com.evolveum.midpoint.client.api.exception.AuthenticationException;
import com.evolveum.midpoint.client.api.exception.ObjectNotFoundException;
import com.evolveum.midpoint.client.api.exception.SchemaException;
import com.evolveum.midpoint.schema.constants.ObjectTypes;
import com.evolveum.midpoint.xml.ns._public.common.common_3.ObjectType;

public class RestPrismObjectService<O extends ObjectType> extends CommonPrismService implements ObjectService<O> {

    private String oid;

    public RestPrismObjectService(RestPrismService service, ObjectTypes type, String oid) {
        super(service, type);
        this.oid = oid;
    }


    @Override
    public O get(List<String> options) throws ObjectNotFoundException, AuthenticationException {
        return null;
    }

    @Override
    public O get(List<String> options, List<String> include, List<String> exclude) throws ObjectNotFoundException, AuthenticationException {
       return null;
    }

    @Override
    public ObjectModifyService<O> modify() throws ObjectNotFoundException, AuthenticationException {
        return null;
    }

    @Override
    public void delete() throws ObjectNotFoundException, AuthenticationException {

    }

    @Override
    public O get() throws ObjectNotFoundException, AuthenticationException, SchemaException {
        System.out.println("getting object ");
        O response = getService().getObject(getType(), oid);

        if (response == null) {
            throw new ObjectNotFoundException("null returned");
        }
        System.out.println("got object " + response);

        return response;
    }

    String getOid() {
        return oid;
    }
}


