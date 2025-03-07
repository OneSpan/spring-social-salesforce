package org.springframework.social.salesforce.api.impl;

import org.junit.Test;
import org.springframework.social.salesforce.api.ApiVersion;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.RequestMatchers.method;
import static org.springframework.test.web.client.match.RequestMatchers.requestTo;
import static org.springframework.test.web.client.response.ResponseCreators.withSuccess;


/**
 * @author Umut Utkan
 */
public class MetaApiTemplateTest extends AbstractSalesforceTest {

    @Test
    public void getApiVersions() {
        mockServer.expect(requestTo("https://na7.salesforce.com/services/data"))
                .andExpect(method(GET))
                .andRespond(withSuccess(loadResource("versions.json"), APPLICATION_JSON));
        List<ApiVersion> versions = salesforce.apiOperations().getVersions();
        assertEquals(4, versions.size());
        assertEquals("Summer '16", versions.get(3).getLabel());
        assertEquals("37.0", versions.get(3).getVersion());
        assertEquals("/services/data/v37.0", versions.get(3).getUrl());
    }

    @Test
    public void getServices() {
        mockServer.expect(requestTo("https://na7.salesforce.com/services/data/v37.0"))
                .andExpect(method(GET))
                .andRespond(withSuccess(loadResource("services.json"), APPLICATION_JSON));
        Map<String, String> services = salesforce.apiOperations().getServices("37.0");
        assertEquals(6, services.size());
        assertEquals("/services/data/v37.0/sobjects", services.get("sobjects"));
        assertEquals("/services/data/v37.0/chatter", services.get("chatter"));
    }

}
