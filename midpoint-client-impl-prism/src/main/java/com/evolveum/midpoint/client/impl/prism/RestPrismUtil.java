/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.evolveum.midpoint.client.impl.prism;

import com.evolveum.midpoint.prism.PrismContext;
import com.evolveum.midpoint.prism.path.ItemName;
import com.evolveum.midpoint.prism.path.ItemPath;
import com.evolveum.midpoint.xml.ns._public.common.api_types_3.ObjectModificationType;
import com.evolveum.prism.xml.ns._public.types_3.ItemDeltaType;
import com.evolveum.prism.xml.ns._public.types_3.ItemPathType;
import com.evolveum.prism.xml.ns._public.types_3.ModificationTypeType;
import com.evolveum.prism.xml.ns._public.types_3.RawType;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author breidenbach
 */
public class RestPrismUtil {

    public static String subUrl(String... segments) {
        return "/" + StringUtils.join(segments, "/");
    }

    public static String subUrl(final String urlPrefix, final String pathSegment) {
        // TODO: better code (e.g. escaping)
        return "/" + urlPrefix + "/" + pathSegment;
    }

    public static ObjectModificationType buildModifyObject(List<ItemDeltaType> itemDeltas) {
        /*HttpEntity entity = new HttpEntity();
                 itemDeltas.forEach((itemDelta) ->
				entity.getTrailers().get().add();*/

        ObjectModificationType objectModificationType = new ObjectModificationType();
        itemDeltas.forEach((itemDelta)
                -> objectModificationType.getItemDelta().add(itemDelta));

        return objectModificationType;
    }

    public static ItemDeltaType buildItemDelta(ModificationTypeType modificationType, String path, Object value) {
        //Create ItemDelta
        ItemDeltaType itemDeltaType = new ItemDeltaType();
        itemDeltaType.setModificationType(modificationType);

        //Set Path
        ItemPath itemPath = new ItemName(path);

        ItemPathType itemPathType = new ItemPathType();
        itemPathType.setItemPath(itemPath);
        itemDeltaType.setPath(itemPathType);

        if (value != null) {
            PrismContext prism = PrismContext.get();

            RawType raw = RawType.create(path, prism);

            itemDeltaType.getValue().add(raw);
        }

        return itemDeltaType;
    }

}
