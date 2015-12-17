package org.springframework.social.salesforce.api.impl;

import org.junit.Test;
import org.springframework.social.salesforce.api.ResultItem;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.RequestMatchers.method;
import static org.springframework.test.web.client.match.RequestMatchers.requestTo;
import static org.springframework.test.web.client.response.ResponseCreators.withSuccess;

/**
 * @author Umut Utkan
 */
public class RecentTemplateTest extends AbstractSalesforceTest {

    @Test
    public void search() {
        mockServer.expect(requestTo("https://na7.salesforce.com/services/data/v23.0/recent"))
                .andExpect(method(GET))
                .andRespond(withSuccess(loadResource("recent.json"), APPLICATION_JSON).headers(responseHeaders));
        List<ResultItem> items = salesforce.recentOperations().recent();
        assertEquals(9, items.size());
        assertEquals("User", items.get(0).getType());
        assertEquals("/services/data/v23.0/sobjects/User/005A0000001cRuvIAE", items.get(0).getUrl());
        assertEquals("005A0000001cRuvIAE", items.get(0).getAttributes().get("Id"));
        assertEquals("Umut Utkan", items.get(0).getAttributes().get("Name"));
    }

}
