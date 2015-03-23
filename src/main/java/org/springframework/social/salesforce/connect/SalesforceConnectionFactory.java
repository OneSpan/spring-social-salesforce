package org.springframework.social.salesforce.connect;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.salesforce.api.Salesforce;

/**
 * @author Umut Utkan
 */
public class SalesforceConnectionFactory extends OAuth2ConnectionFactory<Salesforce> {


    public SalesforceConnectionFactory(String clientId, String clientSecret) {
        super(Salesforce.PROVIDER_ID, new SalesforceServiceProvider(clientId, clientSecret),
                new SalesforceAdapter());
    }

    public SalesforceConnectionFactory(String clientId, String clientSecret, String authorizeUrl, String tokenUrl) {
        super(Salesforce.PROVIDER_ID, new SalesforceServiceProvider(clientId, clientSecret
        ), new SalesforceAdapter());
    }

}
