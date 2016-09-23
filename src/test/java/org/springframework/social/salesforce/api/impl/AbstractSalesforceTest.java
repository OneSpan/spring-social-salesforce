package org.springframework.social.salesforce.api.impl;

import org.junit.Before;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.web.client.MockRestServiceServer;

/**
 * @author Umut Utkan
 */
abstract public class AbstractSalesforceTest {

    protected static final String PROFILE_URL = "https://login.salesforce.com/id/AN_ORGANIZATION_ID/A_USER_ID";

    protected SalesforceTemplate salesforce;

    protected SalesforceTemplate unauthorizedSalesforce;

    protected MockRestServiceServer mockServer;

    @Before
    public void setup() {
        salesforce = new SalesforceTemplate("ACCESS_TOKEN");
        salesforce.setInstanceUrl("https://na7.salesforce.com");
        salesforce.setProfileUrl(PROFILE_URL);
        mockServer = MockRestServiceServer.createServer(salesforce.getRestTemplate());
        unauthorizedSalesforce = new SalesforceTemplate();

        // create a mock server just to avoid hitting real salesforce if something gets past the authorization check
        MockRestServiceServer.createServer(unauthorizedSalesforce.getRestTemplate());
    }

    protected Resource loadResource(String name) {
        return new ClassPathResource("/" + name, getClass());
    }

}
