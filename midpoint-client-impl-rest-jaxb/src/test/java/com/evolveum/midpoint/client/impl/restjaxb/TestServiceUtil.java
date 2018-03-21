/*
 * Copyright (c) 2017-2018 Evolveum
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.evolveum.midpoint.client.impl.restjaxb;

import com.evolveum.midpoint.client.api.ServiceUtil;
import com.evolveum.prism.xml.ns._public.types_3.ProtectedStringType;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;

/**
 * @author mederly
 *
 */
public class TestServiceUtil {
	
	private static final String DATA_DIR = "src/test/resources/other";

	private Unmarshaller unmarshaller;
	private ServiceUtil util;

	public TestServiceUtil() throws JAXBException, IOException {
		util = new RestJaxbServiceUtil();
		unmarshaller = createJaxbContext().createUnmarshaller();
	}

	@Test
	public void testProtectedStringWithClearValue() throws Exception {
		testProtectedString("protected-string-with-clear-value.xml", "nbusr123");
	}

	@Test
	public void testProtectedStringSimple() throws Exception {
		testProtectedString("protected-string-simple.xml", "init1234");
	}

	@Test
	public void testProtectedStringEncrypted() throws Exception {
		testProtectedString("protected-string-encrypted.xml", null);
	}

	@Test
	public void testProtectedStringEmpty() throws Exception {
		testProtectedString("protected-string-empty.xml", null);
	}

	private void testProtectedString(String filename, String expectedValue) throws Exception {
		//noinspection unchecked
		ProtectedStringType ps = ((JAXBElement<ProtectedStringType>) unmarshaller.unmarshal(new File(DATA_DIR, filename))).getValue();
		String clearValue = util.getClearValue(ps);
		AssertJUnit.assertEquals("Unexpected clear value", expectedValue, clearValue);
	}

	private JAXBContext createJaxbContext() throws IOException {
		try {
			return JAXBContext.newInstance("com.evolveum.midpoint.xml.ns._public.common.api_types_3:"
					+ "com.evolveum.midpoint.xml.ns._public.common.audit_3:"
					+ "com.evolveum.midpoint.xml.ns._public.common.common_3:"
					+ "com.evolveum.midpoint.xml.ns._public.connector.icf_1.connector_extension_3:"
					+ "com.evolveum.midpoint.xml.ns._public.connector.icf_1.connector_schema_3:"
					+ "com.evolveum.midpoint.xml.ns._public.connector.icf_1.resource_schema_3:"
					+ "com.evolveum.midpoint.xml.ns._public.gui.admin_1:"
					+ "com.evolveum.midpoint.xml.ns._public.model.extension_3:"
					+ "com.evolveum.midpoint.xml.ns._public.model.scripting_3:"
					+ "com.evolveum.midpoint.xml.ns._public.model.scripting.extension_3:"
					+ "com.evolveum.midpoint.xml.ns._public.report.extension_3:"
					+ "com.evolveum.midpoint.xml.ns._public.resource.capabilities_3:"
					+ "com.evolveum.midpoint.xml.ns._public.task.extension_3:"
					+ "com.evolveum.midpoint.xml.ns._public.task.jdbc_ping.handler_3:"
					+ "com.evolveum.midpoint.xml.ns._public.task.noop.handler_3:"
					+ "com.evolveum.prism.xml.ns._public.annotation_3:"
					+ "com.evolveum.prism.xml.ns._public.query_3:"
					+ "com.evolveum.prism.xml.ns._public.types_3");
		} catch (JAXBException e) {
			throw new IOException(e);
		}
	}
}
