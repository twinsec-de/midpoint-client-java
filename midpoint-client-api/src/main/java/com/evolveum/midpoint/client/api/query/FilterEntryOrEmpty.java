package com.evolveum.midpoint.client.api.query;

import com.evolveum.midpoint.xml.ns._public.common.common_3.ObjectType;

public interface FilterEntryOrEmpty<O extends ObjectType> extends FilterEntry<O>, FilterExit<O> {

}
