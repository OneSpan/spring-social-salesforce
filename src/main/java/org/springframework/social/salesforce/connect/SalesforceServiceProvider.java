package org.springframework.social.salesforce.connect;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;
import org.springframework.social.salesforce.api.Salesforce;
import org.springframework.social.salesforce.api.impl.SalesforceTemplate;

/**
 * Salesforce ServiceProvider implementation.
 *
 * @author Umut Utkan
 */
public class SalesforceServiceProvider extends AbstractOAuth2ServiceProvider<Salesforce> {

    public static final String BASE_URL = "https://login.salesforce.com/";


    public SalesforceServiceProvider(String clientId, String clientSecret) {
        super(new SalesforceOAuth2Template(clientId, clientSecret, BASE_URL));
    }


    public Salesforce getApi(String accessToken) {
        return configureTemplate(new SalesforceTemplate(accessToken));
    }

    private SalesforceTemplate configureTemplate(SalesforceTemplate template) {
        // gets the returned instance url and sets to Salesforce template as base url.
        final SalesforceOAuth2Template oAuthOperations = (SalesforceOAuth2Template) getOAuthOperations();

        String instanceUrl = oAuthOperations.getInstanceUrl();
        if (instanceUrl != null) {
            template.setInstanceUrl(instanceUrl);
        }

        final String profileUrl = oAuthOperations.getProfileUrl();
        if (profileUrl != null) {
            template.setProfileUrl(profileUrl);
        }

        return template;
    }

}
