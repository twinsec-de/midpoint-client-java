package com.evolveum.midpoint.client.impl.prism;

import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.evolveum.midpoint.client.api.ObjectReference;
import com.evolveum.midpoint.client.api.Service;
import com.evolveum.midpoint.client.api.exception.ObjectNotFoundException;
import com.evolveum.midpoint.schema.result.OperationResult;
import com.evolveum.midpoint.xml.ns._public.common.common_3.OperationResultType;
import com.evolveum.midpoint.xml.ns._public.common.common_3.SystemObjectsType;
import com.evolveum.midpoint.xml.ns._public.common.common_3.UserType;

public class TestRestPrismService {


    private Service service;

    @BeforeClass
    public void init() throws Exception {
        service = createClient();
    }


    @Test
    public void test001getUser() throws Exception {

        UserType user = service.users().oid(SystemObjectsType.USER_ADMINISTRATOR.value()).get();

        System.out.println("User : " + user.getName());

        AssertJUnit.assertEquals("administrator", user.getName().getOrig());

    }

    @Test
    public void test002getUserNotFound() throws Exception {
        try {
            service.users().oid("not-exists").get();
            AssertJUnit.fail("ObejctNotFoundException should be thrown.");
        } catch (ObjectNotFoundException e) {
            //this is expected
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void test010addUser() throws Exception {

        UserType user = new UserType().name("00clientUser").givenName("given").familyName("family");
        ObjectReference<UserType> ref = service.users().add(user).post();

        System.out.println("oid: " + ref.getOid());
    }

    @Test
    public void test100testResource() throws Exception {

        OperationResultType resultType = service.resources().oid("ac5199fb-c5bd-46c3-a549-d82e8fd30dc2").test().post();
        OperationResult result = OperationResult.createOperationResult(resultType);

        AssertJUnit.assertTrue(result.isSuccess());
    }

    @Test
    public void test120testResourceDown() throws Exception {

        OperationResultType resultType = service.resources().oid("2a7c7130-7a34-11e4-bdf6-001e8c717e5b").test().post();
        OperationResult result = OperationResult.createOperationResult(resultType);

        AssertJUnit.assertTrue(result.isFatalError());
    }

    private Service createClient() throws Exception {
        RestPrismServiceBuilder builder = RestPrismServiceBuilder.create();
        return builder.username("administrator")
                .password("5ecr3t")
                .baseUrl("http://localhost:8080/midpoint/ws/rest")
                .build();
    }


}
