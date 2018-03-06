package com.evolveum.midpoint.client.api.query;

import com.evolveum.midpoint.xml.ns._public.common.common_3.ObjectType;

public interface FilterEntry<O extends ObjectType> extends AtomicFilterEntry<O> {

	AtomicFilterEntry<O> not();
}
