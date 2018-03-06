package com.evolveum.midpoint.client.api.query;

import javax.xml.namespace.QName;

import com.evolveum.midpoint.xml.ns._public.common.common_3.ObjectReferenceType;
import com.evolveum.midpoint.xml.ns._public.common.common_3.ObjectType;
import com.evolveum.prism.xml.ns._public.types_3.ItemPathType;

public interface AtomicFilterEntry<O extends ObjectType> {

//	AtomicFilterExit<O> all();
//	AtomicFilterExit<O> none();
//	AtomicFilterExit<O> undefined();
	ConditionEntry<O> item(ItemPathType itemPath); 
	ConditionEntry<O> item(QName... qnames);
//	AtomicFilterExit<O> id(String... identifiers);
//	AtomicFilterExit<O> id(long... identifiers);
//	AtomicFilterExit<O> ownerId(String... identifiers);
//	AtomicFilterExit<O> ownerId(long... identifiers);
//	AtomicFilterExit<O> isDirectChildOf(ObjectReferenceType value);
//	AtomicFilterExit<O> isChildOf(ObjectReferenceType value);
//	AtomicFilterExit<O> isDirectChildOf(String oid);
//	AtomicFilterExit<O> isChildOf(String oid);
//	AtomicFilterExit<O> isParentOf(ObjectReferenceType value);            // reference should point to OrgType
//	AtomicFilterExit<O> isParentOf(String oid);                           // oid should be of an OrgType
////	AtomicFilterExit<O> isInScopeOf(String oid, OrgFilter.Scope scope);
////	AtomicFilterExit<O> isInScopeOf(PrismReferenceValue value, OrgFilter.Scope scope);
//	AtomicFilterExit<O> isRoot() ;
//	AtomicFilterExit<O> fullText(String... words);
//	FilterEntryOrEmpty<O> block();
////	FilterEntryOrEmpty<O> type(Class<? extends Containerable> type) ;
//	FilterEntryOrEmpty<O> exists(QName... names) ;
}
