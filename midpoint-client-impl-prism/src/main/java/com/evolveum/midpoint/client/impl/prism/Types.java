/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.evolveum.midpoint.client.impl.prism;

import com.evolveum.midpoint.schema.constants.SchemaConstants;
import com.evolveum.midpoint.xml.ns._public.common.api_types_3.ExecuteCredentialResetRequestType;
import com.evolveum.midpoint.xml.ns._public.common.api_types_3.ObjectListType;
import com.evolveum.midpoint.xml.ns._public.common.api_types_3.ObjectModificationType;
import com.evolveum.midpoint.xml.ns._public.common.api_types_3.PolicyItemsDefinitionType;
import com.evolveum.midpoint.xml.ns._public.common.common_3.ArchetypeType;
import com.evolveum.midpoint.xml.ns._public.common.common_3.AssignmentType;
import com.evolveum.midpoint.xml.ns._public.common.common_3.ConnectorHostType;
import com.evolveum.midpoint.xml.ns._public.common.common_3.ConnectorType;
import com.evolveum.midpoint.xml.ns._public.common.common_3.FormType;
import com.evolveum.midpoint.xml.ns._public.common.common_3.GenericObjectType;
import com.evolveum.midpoint.xml.ns._public.common.common_3.LookupTableType;
import com.evolveum.midpoint.xml.ns._public.common.common_3.ObjectCollectionType;
import com.evolveum.midpoint.xml.ns._public.common.common_3.ObjectTemplateType;
import com.evolveum.midpoint.xml.ns._public.common.common_3.OrgType;
import com.evolveum.midpoint.xml.ns._public.common.common_3.ResourceType;
import com.evolveum.midpoint.xml.ns._public.common.common_3.RoleType;
import com.evolveum.midpoint.xml.ns._public.common.common_3.SecurityPolicyType;
import com.evolveum.midpoint.xml.ns._public.common.common_3.SequenceType;
import com.evolveum.midpoint.xml.ns._public.common.common_3.ShadowType;
import com.evolveum.midpoint.xml.ns._public.common.common_3.SystemConfigurationType;
import com.evolveum.midpoint.xml.ns._public.common.common_3.TaskType;
import com.evolveum.midpoint.xml.ns._public.common.common_3.UserType;
import com.evolveum.midpoint.xml.ns._public.common.common_3.ValuePolicyType;
import com.evolveum.midpoint.xml.ns._public.model.scripting_3.ExecuteScriptType;
import com.evolveum.prism.xml.ns._public.query_3.QueryType;
import java.util.Arrays;
import javax.xml.namespace.QName;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author breidenbach
 */
public enum Types {USERS(UserType.class, new QName(SchemaConstants.NS_C, "UserType"), new QName(SchemaConstants.NS_C, "user"), "users"),
	VALUE_POLICIES(ValuePolicyType.class, new QName(SchemaConstants.NS_C, "ValuePolicyType"), new QName(SchemaConstants.NS_C, "valuePolicy"), "valuePolicies"),
	SECURITY_POLICIES(SecurityPolicyType.class, new QName(SchemaConstants.NS_C, "SecurityPolicyType"), new QName(SchemaConstants.NS_C, "securityPolicy"), "securityPolicies"),
	CONNECTORS(ConnectorType.class, new QName(SchemaConstants.NS_C, "ConnectorType"), new QName(SchemaConstants.NS_C, "connector"), "connectors"),
	CONNECTOR_HOSTS(ConnectorHostType.class, new QName(SchemaConstants.NS_C, "ConnectorHostType"), new QName(SchemaConstants.NS_C, "connectorHost"), "connectorHosts"),
	GENERIC_OBJECTS(GenericObjectType.class, new QName(SchemaConstants.NS_C, "GenericObjectType"), new QName(SchemaConstants.NS_C, "genericObject"), "genericObjects"),
	RESOURCES(ResourceType.class, new QName(SchemaConstants.NS_C, "ResourceType"), new QName(SchemaConstants.NS_C, "resource"), "resources"),
	OBJECT_TEMPLATES(ObjectTemplateType.class, new QName(SchemaConstants.NS_C, "ObjectTemplateType"), new QName(SchemaConstants.NS_C, "objectTemplate"), "objectTemplates"),
	OBJECT_COLLECTIONS(ObjectCollectionType.class, new QName(SchemaConstants.NS_C, "ObjectCollectionType"), new QName(SchemaConstants.NS_C, "objectCollection"), "objectCollections"),
	SYSTEM_CONFIGURATIONS(SystemConfigurationType.class, new QName(SchemaConstants.NS_C, "SystemConfigurationType"), new QName(SchemaConstants.NS_C, "systemConfiguration"), "systemConfigurations"),
	TASKS(TaskType.class, new QName(SchemaConstants.NS_C, "TaskType"), new QName(SchemaConstants.NS_C, "task"), "tasks"),
	SHADOWS(ShadowType.class, new QName(SchemaConstants.NS_C, "ShadowType"), new QName(SchemaConstants.NS_C, "shadow"), "shadows"),
	ROLES(RoleType.class, new QName(SchemaConstants.NS_C, "RoleType"), new QName(SchemaConstants.NS_C, "role"), "roles"),
	ORGS(OrgType.class, new QName(SchemaConstants.NS_C, "OrgType"), new QName(SchemaConstants.NS_C, "org"), "orgs"),
    ARCHETYPES(ArchetypeType.class, new QName(SchemaConstants.NS_C, "ArchetypeType"), new QName(SchemaConstants.NS_C, "archetype"), "archetypes"),
    FORMS(FormType.class, new QName(SchemaConstants.NS_C, "FormType"), new QName(SchemaConstants.NS_C, "form"), "forms"),
    SEQUENCES(SequenceType.class, new QName(SchemaConstants.NS_C, "SequenceType"), new QName(SchemaConstants.NS_C, "sequence"), "sequences"),

    LOOKUP_TABLE(LookupTableType.class, new QName(SchemaConstants.NS_C, "LookupTableType"), new QName(SchemaConstants.NS_C, "lookupTable"), "lookupTables"),

	//Other types NS_api_Types gibt es nicht mehr alternative Ns_types?
	QUERY(QueryType.class, new QName(SchemaConstants.NS_QUERY, "QueryType"), new QName(SchemaConstants.NS_QUERY, "query"), null),
	OBJECT_LIST_TYPE(ObjectListType.class, new QName(SchemaConstants.NS_TYPES, "ObjectListType"), new QName(SchemaConstants.NS_TYPES, "objectList"), ""),
	POLICY_ITEMS_DEFINITION(PolicyItemsDefinitionType.class, new QName(SchemaConstants.NS_TYPES, "PolicyItemsDefinitionType"), new QName(SchemaConstants.NS_TYPES, "policyItemsDefinition"), ""),
	OBJECT_MODIFICATION_TYPE(ObjectModificationType.class, new QName(SchemaConstants.NS_TYPES, "ObjectModificationType"), new QName(SchemaConstants.NS_TYPES, "objectModification"), ""),
	EXECUTE_CREDENTIAL_RESET_REQUEST(ExecuteCredentialResetRequestType.class, new QName(SchemaConstants.NS_TYPES, "ExecuteCredentialResetRequestType"), new QName(SchemaConstants.NS_TYPES, "executeCredentialResetRequest"), ""),
	EXECUTE_SCRIPT(ExecuteScriptType.class, new QName(SchemaConstants.NS_SCRIPTING, "ExecuteScriptType"), new QName(SchemaConstants.NS_SCRIPTING, "executeScript"), ""),
	ASSIGNMENT(AssignmentType.class, new QName(SchemaConstants.NS_C, "AssignmentType"), new QName(SchemaConstants.NS_C, "assignment"), "");

	private Class<?> clazz;
	/**
	 * element name - used for XML jaxb serialization
	 */
	private QName elementName;
	/**
	 * type name as QName, e.g. c:RoleType, c:ValuePolicyType.. e.g. we need it to the reference
	 */
	private QName typeQName;
	private String restPath;

	private Types(Class<?> clazz, QName typeQName, QName elementName, String restPath) {
		this.clazz = clazz;
		this.typeQName = typeQName;
		this.elementName = elementName;
		this.restPath = restPath;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public QName getElementName() {
		return elementName;
	}

	public String getRestPath() {
		return restPath;
	}

	public QName getTypeQName() {
		return typeQName;
	}

	public static Types findType(Class<?> clazz) {
		return Arrays.asList(values()).stream().filter(type -> type.getClazz().equals(clazz)).findAny().orElse(null);
	}

	public static Types findType(String localname) {
		if (StringUtils.isBlank(localname)) {
			return null;
		}
		for (Types type : values()) {
			if (type.getTypeQName().getLocalPart().equals(localname)) {
				return type;
			}
		}
		throw new UnsupportedOperationException("Not supported type: " + localname);
	}
    
}
