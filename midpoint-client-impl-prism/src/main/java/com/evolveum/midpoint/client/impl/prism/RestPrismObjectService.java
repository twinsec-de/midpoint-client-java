package com.evolveum.midpoint.client.impl.prism;

import com.evolveum.midpoint.client.api.ObjectCredentialService;
import com.evolveum.midpoint.client.api.ObjectModifyService;
import com.evolveum.midpoint.client.api.ObjectService;
import com.evolveum.midpoint.client.api.ValidateGenerateRpcService;
import com.evolveum.midpoint.client.api.exception.AuthenticationException;
import com.evolveum.midpoint.client.api.exception.ObjectNotFoundException;
import com.evolveum.midpoint.schema.constants.ObjectTypes;
import com.evolveum.midpoint.xml.ns._public.common.common_3.ObjectType;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

import java.util.List;

public class RestPrismObjectService<O extends ObjectType> extends CommonPrismService implements ObjectService<O> {

    private String oid;

    public RestPrismObjectService(RestPrismService service, ObjectTypes type, String oid) {
        super(service, type);
        this.oid = oid;
    }


    @Override
    public O get(List<String> options) throws ObjectNotFoundException, AuthenticationException {
        return null;
    }

    @Override
    public O get(List<String> options, List<String> include, List<String> exclude) throws ObjectNotFoundException, AuthenticationException {
       return null;
    }

    @Override
    public ObjectModifyService<O> modify() throws ObjectNotFoundException, AuthenticationException {
        return null;
    }

    @Override
    public ObjectCredentialService<O> credential() {
        return null;
    }

    @Override
    public ValidateGenerateRpcService generate() {
        return null;
    }

    @Override
    public ValidateGenerateRpcService validate() {
        return null;
    }

    @Override
    public void delete() throws ObjectNotFoundException, AuthenticationException {

    }

    @Override
    public O get() throws ObjectNotFoundException, AuthenticationException {
        System.out.println("getting object ");
        O response = (O) getService().getClient()
                .get()
                .uri("/users/{oid}", oid)
                .retrieve()
                .onStatus(s -> HttpStatus.NOT_FOUND.value() == s.value(), clientResponse -> Mono.error(new ObjectNotFoundException("ObjectNot found")))
                .bodyToMono(getType().getClassDefinition())
                .block();

        if (response == null) {
            throw new ObjectNotFoundException("null returned");
        }
        System.out.println("got object " + response);

        return response;
    }
}
