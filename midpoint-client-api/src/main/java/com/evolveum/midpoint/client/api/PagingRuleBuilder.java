package com.evolveum.midpoint.client.api;

import com.evolveum.midpoint.xml.ns._public.common.common_3.ObjectType;
import com.evolveum.prism.xml.ns._public.types_3.ItemPathType;

/**
 * @author jakmor
 */
public interface PagingRuleBuilder <O extends ObjectType>
{
    public PagingRuleBuilder<O> orderBy(ItemPathType itemPath);
    public PagingRuleBuilder<O> offSet(Integer offsetAmount);
    public PagingRuleBuilder<O> maxSize(Integer size);
    public PagingRuleBuilder<O> groupBy(ItemPathType itemPath);

    public  QueryBuilder<O> finishPaging();
}
