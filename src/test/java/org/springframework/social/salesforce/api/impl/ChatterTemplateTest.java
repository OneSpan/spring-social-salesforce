package org.springframework.social.salesforce.api.impl;

import org.junit.Test;
import org.springframework.social.salesforce.api.SalesforceProfile;
import org.springframework.social.salesforce.api.Status;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.RequestMatchers.content;
import static org.springframework.test.web.client.match.RequestMatchers.method;
import static org.springframework.test.web.client.match.RequestMatchers.requestTo;
import static org.springframework.test.web.client.response.ResponseCreators.withSuccess;


/**
 * @author Umut Utkan
 */
public class ChatterTemplateTest extends AbstractSalesforceTest {

    @Test
    public void getProfile() {
        mockServer.expect(requestTo("https://na7.salesforce.com/services/data/v37.0/chatter/users/me"))
                .andExpect(method(GET))
                .andRespond(withSuccess(loadResource("profile.json"), APPLICATION_JSON));
        SalesforceProfile profile = salesforce.chatterOperations().getUserProfile();
        assertEquals("Umut Utkan", profile.getFullName());
        assertEquals("umut.utkan@foo.com", profile.getEmail());
        assertEquals("005A0000001cRuvIAE", profile.getId());
        assertEquals("005A0000001cRuvIAE", profile.getUsername());
        assertEquals("Umut Utkan", profile.getFullName());
        assertEquals("https://c.na7.content.force.com/profilephoto/005/F", profile.getPhoto().getLargePhotoUrl());
        assertEquals("https://c.na7.content.force.com/profilephoto/005/T", profile.getPhoto().getSmallPhotoUrl());
    }

    @Test
    public void getStatus() {
        mockServer.expect(requestTo("https://na7.salesforce.com/services/data/v37.0/chatter/users/me/status"))
                .andExpect(method(GET))
                .andRespond(withSuccess(loadResource("chatter-status.json"), APPLICATION_JSON));

        Status status = salesforce.chatterOperations().getStatus();
        assertNotNull(status);
        assertEquals("I am also working on #hede", status.getText());
    }

    @Test
    public void updateStatus() {
        mockServer.expect(requestTo("https://na7.salesforce.com/services/data/v37.0/chatter/users/me/status"))
                .andExpect(method(POST))
                .andExpect(content().string("text=Updating+status+via+%23spring-social-salesforce%21"))
                .andRespond(withSuccess(loadResource("chatter-status.json"), APPLICATION_JSON));

        Status status = salesforce.chatterOperations().updateStatus("Updating status via #spring-social-salesforce!");
        assertNotNull(status);
    }

}
