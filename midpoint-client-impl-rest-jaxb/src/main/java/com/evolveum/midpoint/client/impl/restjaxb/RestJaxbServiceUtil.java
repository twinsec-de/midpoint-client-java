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

import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import com.evolveum.midpoint.client.api.ServiceUtil;
import com.evolveum.prism.xml.ns._public.types_3.ItemPathType;
import com.evolveum.prism.xml.ns._public.types_3.PolyStringType;
import com.evolveum.prism.xml.ns._public.types_3.ProtectedStringType;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;

/**
 * @author semancik
 *
 */
public class RestJaxbServiceUtil implements ServiceUtil {

	private static final String F_CLEAR_VALUE = "clearValue";

	private static DatatypeFactory df;
	
	static {
        try {
            df = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException dce) {
            throw new IllegalStateException("Exception while obtaining Datatype Factory instance", dce);
        }
    }
	
	@Override
	public PolyStringType createPoly(String orig) {
		PolyStringType poly = new PolyStringType();
		poly.getContent().add(orig);
		return poly;
	}

	@Override
	public String getOrig(PolyStringType poly) {
		if (poly == null) {
			return null;
		}
		for (Object content: poly.getContent()) {
			if (content instanceof String) {
				return (String)content;
			}
			// TODO: DOM elements and JAXB elements
		}
		return null;
	}
	
	@Override
	public ItemPathType createItemPathType(QName... qname) {
		ItemPathType itemPathType = new ItemPathType();
		itemPathType.setValue("");
		Arrays.asList(qname).forEach(name -> itemPathType.setValue(itemPathType + "/" + name.getLocalPart()));
		return itemPathType;
	}
	
	@Override
	public XMLGregorianCalendar asXMLGregorianCalendar(Date date) {
		if (date == null) {
			return null;
		} else {
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTimeInMillis(date.getTime());
			return df.newXMLGregorianCalendar(gc);
		}
	}

	@Override
	public String getClearValue(ProtectedStringType protectedString) {
		if (protectedString == null) {
			return null;
		}
		boolean hasSubelements = false;
		StringBuilder textContentBuilder = new StringBuilder();
		for (Object o : protectedString.getContent()) {
			if (o instanceof JAXBElement) {
				JAXBElement element = (JAXBElement) o;
				if (F_CLEAR_VALUE.equals(element.getName().getLocalPart())) {
					return (String) element.getValue();
				}
				hasSubelements = true;
			} else if (o instanceof Element) {
				Element element = (Element) o;
				if (F_CLEAR_VALUE.equals(element.getLocalName())) {
					return element.getTextContent();
				}
				hasSubelements = true;
			} else if (o instanceof String) {
				textContentBuilder.append((String) o);
			}
		}
		if (!hasSubelements) {
			String textContent = textContentBuilder.toString();
			// This is probably not quite correct because it treats alternative representation of blank password as no password.
			// But there's no way how to distinguish these cases.
			if (StringUtils.isNotBlank(textContent)) {
				return textContent;
			}
		}
		return null;
	}
}
