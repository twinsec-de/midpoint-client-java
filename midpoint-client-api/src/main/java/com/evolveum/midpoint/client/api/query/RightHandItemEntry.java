package com.evolveum.midpoint.client.api.query;

import javax.xml.namespace.QName;

import com.evolveum.midpoint.xml.ns._public.common.common_3.ObjectType;
import com.evolveum.prism.xml.ns._public.types_3.ItemPathType;

public interface RightHandItemEntry<O extends ObjectType> {
	
	AtomicFilterExit<O> item(QName... names);
	AtomicFilterExit<O> item(ItemPathType itemPath);
}
