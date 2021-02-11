package com.evolveum.midpoint.client.impl.prism;

import com.evolveum.midpoint.client.api.ObjectReference;
import com.evolveum.midpoint.client.api.exception.ObjectNotFoundException;
import com.evolveum.midpoint.xml.ns._public.common.common_3.ObjectType;

public class RestPrismObjectReference<O extends ObjectType> implements ObjectReference<O> {

    private String oid;
    private Class<O> type;

    public RestPrismObjectReference(String oid, Class type) {
        this.oid = oid;
        this.type = type;
    }

    @Override
    public String getOid() {
        return oid;
    }

    @Override
    public Class<O> getType() {
        return type;
    }

    @Override
    public O getObject() throws ObjectNotFoundException {
        return null;
    }

    @Override
    public boolean containsObject() {
        return false;
    }

    @Override
    public O get() throws ObjectNotFoundException {
        return null;
    }
}
