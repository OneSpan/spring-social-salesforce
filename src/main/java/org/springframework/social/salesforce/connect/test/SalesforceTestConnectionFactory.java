package org.springframework.social.salesforce.connect.test;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.salesforce.api.test.SalesforceTest;

/**
 * Created by mpoitras on 07/02/14.
 */
public class SalesforceTestConnectionFactory extends OAuth2ConnectionFactory<SalesforceTest> {

    public SalesforceTestConnectionFactory(String clientId, String clientSecret) {
        super(SalesforceTest.TEST_PROVIDER_ID, new SalesforceTestServiceProvider(clientId, clientSecret),
              new SalesforceTestAdapter());
    }

}
