package org.springframework.social.salesforce.connect.test;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.salesforce.api.impl.SalesforceTemplate;
import org.springframework.social.salesforce.api.test.SalesforceTest;
import org.springframework.social.salesforce.connect.SalesforceOAuth2Template;

/**
 * Created by mpoitras on 07/02/14.
 */
public class SalesforceTestServiceProvider extends AbstractOAuth2ServiceProvider<SalesforceTest> {

    public static final String BASE_URL = "https://test.salesforce.com/";


    public SalesforceTestServiceProvider(String clientId, String clientSecret) {
        super(new SalesforceOAuth2Template(clientId, clientSecret, BASE_URL));
    }


    public SalesforceTest getApi(String accessToken) {
        SalesforceTemplate template = new SalesforceTemplate(accessToken);

        // gets the returned instance url and sets to Salesforce template as base url.
        String instanceUrl = ((SalesforceOAuth2Template) getOAuthOperations()).getInstanceUrl();
        if (instanceUrl != null) {
            template.setInstanceUrl(instanceUrl);
        }

        return template;
    }

}
