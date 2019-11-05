package com.evolveum.midpoint.client.impl.prism;

import com.evolveum.midpoint.client.api.Service;
import com.evolveum.midpoint.xml.ns._public.common.common_3.SystemObjectsType;
import com.evolveum.midpoint.xml.ns._public.common.common_3.UserType;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

public class TestRestPrismService {


    @Test
    public void test001addUser() throws Exception {

        UserType user = createClient().users().oid(SystemObjectsType.USER_ADMINISTRATOR.value()).get();

        System.out.println("User : " + user.getName());

        AssertJUnit.assertEquals("administrator", user.getName().getOrig());

    }

    private Service createClient() throws Exception {
        RestPrismServiceBuilder builder = RestPrismServiceBuilder.create();
        return builder.username("administrator")
                .password("5ecr3t")
                .baseUrl("http://localhost:8080/midpoint/ws/rest")
                .build();
    }
}
