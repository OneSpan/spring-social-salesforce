package org.springframework.social.salesforce.connect;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Parameters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalesforceOAuth2TemplateTest {

    private static final String BASE_URL = "http://base.oauth.url.salesforce.com";

    private final SalesforceOAuth2Template classUnderTest = new SalesforceOAuth2Template(
            "clientId",
            "clientSecret",
            BASE_URL
    );

    @Test
    public void testBuildAuthorizeUrlWithValidSalesforceDomain() throws Exception {
        final String validSalesforceOauthEndpoint = "https://valid-custom-domain.force.com";
        final OAuth2Parameters parameters = buildOAuthParametersWithEndpoint(validSalesforceOauthEndpoint);


        final String authorizeUrl = classUnderTest.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, parameters);


        Assert.assertTrue(authorizeUrl.contains(validSalesforceOauthEndpoint));
    }

    @Test
    public void testCreateAccessGrantWithInvalidSalesforceDomain() throws Exception {
        final String invalidBaseUrl = "https://rogue.auth.domain.com";
        final OAuth2Parameters parameters = buildOAuthParametersWithEndpoint(invalidBaseUrl);


        final String authorizeUrl = classUnderTest.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, parameters);


        Assert.assertFalse(authorizeUrl.contains(invalidBaseUrl));
        Assert.assertTrue(authorizeUrl.contains(BASE_URL));
    }

    @Test
    public void testBuildAuthorizeUrlWithNoProvidedSalesforceDomain() throws Exception {
        final OAuth2Parameters parameters = new OAuth2Parameters();

        final String authorizeUrl = classUnderTest.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, parameters);

        Assert.assertTrue(authorizeUrl.contains(BASE_URL));
    }


    private OAuth2Parameters buildOAuthParametersWithEndpoint(String endpoint) {
        final List<String> paramValues = new ArrayList<String>();
        paramValues.add(endpoint);

        final Map<String, List<String>> params = new HashMap<String, List<String>>();
        params.put(SalesforceOAuth2Template.ENDPOINT, paramValues);

        return new OAuth2Parameters(params);
    }
}