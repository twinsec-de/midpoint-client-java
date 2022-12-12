/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.evolveum.midpoint.client.impl.prism;


import com.evolveum.midpoint.client.api.ObjectModifyService;
import com.evolveum.midpoint.client.api.ObjectReference;
import com.evolveum.midpoint.client.api.TaskFuture;
import com.evolveum.midpoint.client.api.exception.CommonException;
import com.evolveum.midpoint.prism.path.ItemPath;
import com.evolveum.midpoint.xml.ns._public.common.common_3.ObjectType;
import com.evolveum.prism.xml.ns._public.types_3.ItemDeltaType;
import com.evolveum.prism.xml.ns._public.types_3.ModificationTypeType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author breidenbach
 * @param <O>
 */
public class RestPrismObjectModifyService<O extends ObjectType> implements ObjectModifyService<O> {

    final private String oid;
    final private Class<O> type;
    final private RestPrismService service;
    private List<ItemDeltaType> modifications;
   
    public RestPrismObjectModifyService (RestPrismService service,Class<O> type,String oid) {
        this.service=service;
        this.type=type;
        this.oid=oid;
        modifications = new ArrayList<>();
    }

    
    
    
    @Override
    public TaskFuture<ObjectReference<O>> apost() throws CommonException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ObjectModifyService<O> add(String path, Object value) {
        addModification(path, value, ModificationTypeType.ADD);
        return this;
        
    }

    @Override
    public ObjectModifyService<O> add(Map<String, Object> modifications) {
        addModifications(modifications, ModificationTypeType.ADD);
        return this;
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
    
    private void addModification(String path, Object value, ModificationTypeType modificationType){
        modifications.add(RestPrismUtil.buildItemDelta(modificationType, path, value));
        
    }
    private void addModifications(Map<String, Object> modifications,  ModificationTypeType modificationType){
        modifications.forEach((path, value) ->
        addModification(path, value, modificationType));
    }
    
   
    
}
