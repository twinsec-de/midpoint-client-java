package com.evolveum.midpoint.client.api.query;

import javax.xml.namespace.QName;

import com.evolveum.midpoint.xml.ns._public.common.common_3.ObjectType;
import com.evolveum.prism.xml.ns._public.types_3.ItemPathType;

public interface FilterExit<O extends ObjectType> extends QueryExit<O>{

	AtomicFilterExit<O> endBlock();
    FilterExit<O> asc(QName... names);
    FilterExit<O> asc(ItemPathType path);
    FilterExit<O> desc(QName... names);
    FilterExit<O> desc(ItemPathType path);
    FilterExit<O> group(QName... names);
    FilterExit<O> group(ItemPathType path);
    FilterExit<O> offset(Integer n);
    FilterExit<O> maxSize(Integer n);
	
}
