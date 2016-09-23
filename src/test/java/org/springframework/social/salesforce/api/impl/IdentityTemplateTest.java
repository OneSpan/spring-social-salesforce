package org.springframework.social.salesforce.api.impl;

import org.junit.Test;
import org.springframework.social.connect.UserProfile;

import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.RequestMatchers.method;
import static org.springframework.test.web.client.match.RequestMatchers.requestTo;
import static org.springframework.test.web.client.response.ResponseCreators.withSuccess;


/**
 * @author Umut Utkan
 */
public class IdentityTemplateTest extends AbstractSalesforceTest {

    @Test
    public void getProfile() {
        mockServer.expect(requestTo(PROFILE_URL))
                .andExpect(method(GET))
                .andRespond(withSuccess(loadResource("profile.json"), APPLICATION_JSON));
        UserProfile profile = salesforce.identityOperations().retrieveUserProfile();
        assertEquals("Umut", profile.getFirstName());
        assertEquals("Utkan", profile.getLastName());
        assertEquals("umut.utkan@foo.com", profile.getEmail());
        assertEquals("005A0000001cRuvIAE", profile.getId());
        assertEquals("umut.utkan@foo.com", profile.getUsername());
    }

}
