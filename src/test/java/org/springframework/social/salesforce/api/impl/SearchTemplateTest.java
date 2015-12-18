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
public class SearchTemplateTest extends AbstractSalesforceTest {

    @Test
    public void search() {
        mockServer.expect(requestTo("https://na7.salesforce.com/services/data/v23.0/search?q=FIND+%7Bxxx*%7D+IN+ALL+FIELDS+RETURNING+Contact%2C+Account"))
                .andExpect(method(GET))
                .andRespond(withSuccess(loadResource("search.json"), APPLICATION_JSON));
        List<ResultItem> results = salesforce.searchOperations().search("FIND {xxx*} IN ALL FIELDS RETURNING Contact, Account");
        assertEquals(4, results.size());
        assertEquals("Contact", results.get(0).getType());
        assertEquals("Contact", results.get(1).getType());
        assertEquals("Account", results.get(2).getType());
        assertEquals("Account", results.get(3).getType());
    }

}
