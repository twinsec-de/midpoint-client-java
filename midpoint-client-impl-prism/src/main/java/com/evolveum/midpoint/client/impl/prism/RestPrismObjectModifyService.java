/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.evolveum.midpoint.client.impl.prism;

import com.evolveum.midpoint.client.api.ObjectModifyService;
import com.evolveum.midpoint.client.api.ObjectReference;
import com.evolveum.midpoint.client.api.TaskFuture;
import com.evolveum.midpoint.client.api.exception.CommonException;
import com.evolveum.midpoint.prism.delta.ItemDelta;
import com.evolveum.midpoint.schema.constants.ObjectTypes;
import com.evolveum.midpoint.xml.ns._public.common.api_types_3.ObjectModificationType;
import com.evolveum.midpoint.xml.ns._public.common.common_3.ObjectType;
import com.evolveum.prism.xml.ns._public.types_3.ItemDeltaType;
import java.util.List;
import java.util.Map;

/**
 *
 * @author breidenbach
 * @param <O>
 */
public class RestPrismObjectModifyService<O extends ObjectType> implements ObjectModifyService<O> {

    private RestPrismService service;
    private ObjectTypes type;
    private O object;
    private ObjectModificationType modifications;
    final private ObjectTypes types = null;
    final private List<String> options = null;

    public RestPrismObjectModifyService(RestPrismService service, ObjectTypes type, O object) {

        this.service = service;
        this.type = type;
        this.object = object;
        modifications = new ObjectModificationType();
    }

    @Override
    public TaskFuture<ObjectReference<O>> apost() throws CommonException {
        //String restPath = RestPrismUtil.subUrl(Types.findType(getType()).getRestPath(), getOid());
        //String string = service.modifyObject(types, object, options);

        String oid = service.modifyObject(type, object.getOid(), modifications);
        
        RestPrismObjectReference<O> ref = new RestPrismObjectReference<>(oid, type.getClassDefinition());
        return new RestPrismCompletedFuture<>(ref);

    }

    public void setItemDelta (ItemDeltaType itemDelta){
        modifications.itemDelta(itemDelta);
    }
    
    @Override
    public ObjectModifyService<O> add(String path, Object value) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ObjectModifyService<O> add(Map<String, Object> modifications) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ObjectModifyService<O> replace(String path, Object value) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ObjectModifyService<O> replace(Map<String, Object> modifications) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ObjectModifyService<O> delete(String path, Object value) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ObjectModifyService<O> delete(Map<String, Object> modifications) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
