package com.evolveum.midpoint.client.impl.prism;

import com.evolveum.midpoint.client.api.ObjectReference;

import org.apache.hc.core5.http.ProtocolException;

import com.evolveum.midpoint.client.api.ResourceImportService;
import com.evolveum.midpoint.client.api.ResourceOperationService;
import com.evolveum.midpoint.client.api.ResourceService;
import com.evolveum.midpoint.client.api.exception.ObjectNotFoundException;
import com.evolveum.midpoint.client.api.exception.SchemaException;
import com.evolveum.midpoint.schema.constants.ObjectTypes;
import com.evolveum.midpoint.xml.ns._public.common.common_3.OperationResultType;
import com.evolveum.midpoint.xml.ns._public.common.common_3.ResourceType;
import com.evolveum.midpoint.xml.ns._public.common.common_3.TaskType;

public class RestPrismResourceService extends RestPrismObjectService<ResourceType> implements ResourceService, ResourceImportService {

    private static final String TEST_RESOURCE_PATH = ObjectTypes.RESOURCE.getRestType() + "/%s/test";
    private static final String IMPORT_FROM_RESOURCE_PATH = ObjectTypes.RESOURCE.getRestType() + "/%s/import/%s";

    public RestPrismResourceService(RestPrismService service, ObjectTypes type, String oid) {
        super(service, type, oid);
    }

    @Override
    public ResourceOperationService<OperationResultType> test() throws ObjectNotFoundException {

        return new RestPrismResourceOperationService<>(getService(),
                String.format(TEST_RESOURCE_PATH, getOid()),
                (response) -> {
                    try {
                        return getService().parseOperationResult(response.getEntity());
                    } catch (SchemaException e) {
                        return null;
                    }
                });
    }

    @Override
    public ResourceImportService importFromResource() {
        return this;
    }

    @Override
    public ResourceOperationService<ObjectReference<TaskType>> objectClass(String objectClass) {
        return new RestPrismResourceOperationService<>(getService(),
                String.format(IMPORT_FROM_RESOURCE_PATH, getOid(), objectClass),
                (response) -> {
                    try {
                        String oid =  getService().getOidFromLocation(response);
                        return new RestPrismObjectReference<>(oid, TaskType.class);
                    } catch (ProtocolException e) {
                        return null;
                    }
                });
    }
}
