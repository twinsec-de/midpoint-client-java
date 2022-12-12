/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.evolveum.midpoint.client.impl.prism;

import com.evolveum.midpoint.prism.path.ItemPath;
import com.evolveum.prism.xml.ns._public.types_3.ItemDeltaType;
import com.evolveum.prism.xml.ns._public.types_3.ItemPathType;
import com.evolveum.prism.xml.ns._public.types_3.ModificationTypeType;

/**
 *
 * @author breidenbach
 */
public class RestPrismUtil {
    
    
    
    public static ItemDeltaType buildItemDelta(ModificationTypeType modificationType, String path, Object value)
	{
		//Create ItemDelta
		ItemDeltaType itemDeltaType = new ItemDeltaType();
		itemDeltaType.setModificationType(modificationType);

		//Set Path
		ItemPathType itemPathType = new ItemPathType();
		
		itemDeltaType.setPath(itemPathType);

		if (value != null) {
            itemDeltaType.setModificationType((ModificationTypeType) value);
        }

		return itemDeltaType;
	}
    
}
