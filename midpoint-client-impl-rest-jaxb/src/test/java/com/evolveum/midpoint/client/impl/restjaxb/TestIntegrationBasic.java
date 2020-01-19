package com.evolveum.midpoint.client.impl.restjaxb;

import com.evolveum.midpoint.client.api.ObjectReference;
import com.evolveum.midpoint.client.api.SearchResult;
import com.evolveum.midpoint.client.api.Service;
import com.evolveum.midpoint.xml.ns._public.common.common_3.AssignmentType;
import com.evolveum.midpoint.xml.ns._public.common.common_3.ObjectReferenceType;
import com.evolveum.midpoint.xml.ns._public.common.common_3.OrgType;
import org.testng.annotations.Test;

import javax.xml.namespace.QName;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;
import static org.testng.AssertJUnit.assertTrue;

public class TestIntegrationBasic extends AbstractTest {

    private static final String ENDPOINT_ADDRESS = "http://localhost:8080/midpoint/ws/rest";

    private String test300oid;

    @Test
    public void test300OrgAdd() throws Exception {
        Service service = getService();

        OrgType orgBefore = new OrgType();
        orgBefore.setName(service.util().createPoly("test300"));

        // WHEN
        ObjectReference<OrgType> ref = service.orgs().add(orgBefore).post();

        // THEN
        test300oid = ref.getOid();
        assertNotNull("Null oid", test300oid);

        OrgType orgAfter = ref.get();
        Asserts.assertPoly(service, "Wrong name", "test300", orgAfter.getName());
    }

    private String test310oid;

    @Test
    public void test310SubOrgAdd() throws Exception {
        Service service = getService();

        OrgType orgBefore = new OrgType();
        orgBefore.setName(service.util().createPoly("test310"));
        ObjectReferenceType parentRef = new ObjectReferenceType();
        parentRef.setOid(test300oid);
        parentRef.setType(new QName("OrgType"));
        AssignmentType assignment = new AssignmentType();
        assignment.setTargetRef(parentRef);
        orgBefore.getAssignment().add(assignment);

        // WHEN
        ObjectReference<OrgType> ref = service.orgs().add(orgBefore).post();

        // THEN
        test310oid = ref.getOid();
        assertNotNull("Null oid", test310oid);

        OrgType orgAfter = ref.get();
        Asserts.assertPoly(service, "Wrong name", "test310", orgAfter.getName());
    }

    private String test320oid;

    @Test
    public void test320SubOrgAdd() throws Exception {
        Service service = getService();

        OrgType orgBefore = new OrgType();
        orgBefore.setName(service.util().createPoly("test320"));
        ObjectReferenceType parentRef = new ObjectReferenceType();
        parentRef.setOid(test310oid);
        parentRef.setType(new QName("OrgType"));
        AssignmentType assignment = new AssignmentType();
        assignment.setTargetRef(parentRef);
        orgBefore.getAssignment().add(assignment);

        // WHEN
        ObjectReference<OrgType> ref = service.orgs().add(orgBefore).post();

        // THEN
        test320oid = ref.getOid();
        assertNotNull("Null oid", test320oid);

        OrgType orgAfter = ref.get();
        Asserts.assertPoly(service, "Wrong name", "test320", orgAfter.getName());
    }

    @Test
    public void test330OrgDirectChildSearch() throws Exception {
        Service service = getService();

        // WHEN
        SearchResult<OrgType> result = service.orgs().search()
                .queryFor(OrgType.class)
                .isDirectChildOf(test300oid)
                .get();

        // THEN
        assertEquals(result.size(), 1);
        Asserts.assertPoly(service, "Wrong name", "test310", result.get(0).getName());
    }

    @Test
    public void test340OrgChildSearch() throws Exception {
        Service service = getService();

        // WHEN
        SearchResult<OrgType> result = service.orgs().search()
                .queryFor(OrgType.class)
                .isChildOf(test300oid)
                .get();

        // THEN
        assertEquals(result.size(), 2);
        Set<String> names = result.stream()
                .map(org -> service.util().getOrig(org.getName()))
                .collect(Collectors.toSet());
        assertEquals(new HashSet<>(Arrays.asList("test310", "test320")), names);
    }

    @Test
    public void test350RootSearch() throws Exception {
        Service service = getService();

        // WHEN
        SearchResult<OrgType> result = service.orgs().search()
                .queryFor(OrgType.class)
                .isRoot()
                .get();

        // THEN
        Set<String> names = result.stream()
                .map(org -> service.util().getOrig(org.getName()))
                .collect(Collectors.toSet());
        assertTrue("test300 is not among roots", names.contains("test300"));
    }

    private Service getService() throws IOException {
        return getService(ADMIN, ADMIN_PASS, ENDPOINT_ADDRESS, AuthenticationType.BASIC);
    }
}
