package org.springframework.social.salesforce.api.impl;


import org.junit.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.social.salesforce.api.SObjectDetail;
import org.springframework.social.salesforce.api.SObjectSummary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.RequestMatchers.method;
import static org.springframework.test.web.client.match.RequestMatchers.requestTo;
import static org.springframework.test.web.client.response.ResponseCreators.withSuccess;


/**
 * @author Umut Utkan
 */
public class SObjectsTemplateTest extends AbstractSalesforceTest {

    @Test
    public void getSObjects() {
        mockServer.expect(requestTo("https://na7.salesforce.com/services/data/v37.0/sobjects"))
                .andExpect(method(GET))
                .andRespond(withSuccess(loadResource("sobjects.json"), APPLICATION_JSON));
        List<Map> sobjects = salesforce.sObjectsOperations().getSObjects();
        assertEquals(160, sobjects.size());
        assertEquals("Account", sobjects.get(0).get("name"));
        assertEquals("Account", sobjects.get(0).get("label"));
        assertEquals("Accounts", sobjects.get(0).get("labelPlural"));
        assertEquals("/services/data/v37.0/sobjects/Account", ((Map) sobjects.get(0).get("urls")).get("sobject"));
    }

    @Test
    public void getSObject() {
        mockServer.expect(requestTo("https://na7.salesforce.com/services/data/v37.0/sobjects/Account"))
                .andExpect(method(GET))
                .andRespond(withSuccess(loadResource("account.json"), APPLICATION_JSON));
        SObjectSummary account = salesforce.sObjectsOperations().getSObject("Account");
        assertNotNull(account);
        assertEquals("Account", account.getName());
        assertEquals("Account", account.getLabel());
        assertEquals(true, account.isUndeletable());
        assertEquals("001", account.getKeyPrefix());
        assertEquals(false, account.isCustom());
        assertEquals("/services/data/v37.0/sobjects/Account/{ID}", account.getUrls().get("rowTemplate"));
    }

    @Test
    public void describeSObject() {
        mockServer.expect(requestTo("https://na7.salesforce.com/services/data/v37.0/sobjects/Account/describe"))
                .andExpect(method(GET))
                .andRespond(withSuccess(loadResource("account_desc.json"), APPLICATION_JSON));
        SObjectDetail account = salesforce.sObjectsOperations().describeSObject("Account");
        assertNotNull(account);
        assertEquals("Account", account.getName());
        assertEquals("Account", account.getLabel());
        assertEquals(45, account.getFields().size());
        assertEquals("Id", account.getFields().get(0).getName());
        assertEquals(1, account.getRecordTypeInfos().size());
        assertEquals("Master", account.getRecordTypeInfos().get(0).getName());
        assertEquals(36, account.getChildRelationships().size());
        assertEquals("ParentId", account.getChildRelationships().get(0).getField());
    }

    @Test
    public void getBlob() throws IOException {
        mockServer.expect(requestTo("https://na7.salesforce.com/services/data/v37.0/sobjects/Account/xxx/avatar"))
                .andExpect(method(GET))
                .andRespond(withSuccess(new ByteArrayResource("does-not-matter".getBytes("UTF-8")), APPLICATION_JSON));
        BufferedReader reader = new BufferedReader(new InputStreamReader(salesforce.sObjectsOperations().getBlob("Account", "xxx", "avatar")));
        assertEquals("does-not-matter", reader.readLine());
    }

    @Test
    public void testCreate() throws IOException {
        mockServer.expect(requestTo("https://na7.salesforce.com/services/data/v37.0/sobjects/Lead"))
                .andExpect(method(POST))
                .andRespond(withSuccess(new ByteArrayResource("{\"Id\" : \"1234\"}".getBytes("UTF-8")), APPLICATION_JSON));
        Map<String, String> fields = new HashMap<String, String>();
        fields.put("LastName", "Doe");
        fields.put("FirstName", "John");
        fields.put("Company", "Acme, Inc.");
        Map<?, ?> result = salesforce.sObjectsOperations().create("Lead", fields);
        assertEquals(1, result.size());
        assertEquals("1234", result.get("Id"));
    }

}
