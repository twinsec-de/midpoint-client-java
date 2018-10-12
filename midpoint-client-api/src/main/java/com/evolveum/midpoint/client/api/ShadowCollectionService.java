package com.evolveum.midpoint.client.api;

import com.evolveum.midpoint.xml.ns._public.common.common_3.ShadowType;

/**
 * @author Viliam Repan (lazyman)
 */
public interface ShadowCollectionService extends ObjectCollectionService<ShadowType> {

    @Override
    ShadowService oid(String oid);
}
