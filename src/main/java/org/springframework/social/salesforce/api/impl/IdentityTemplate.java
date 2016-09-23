package org.springframework.social.salesforce.api.impl;

import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;
import org.springframework.social.salesforce.api.IdentityOperations;
import org.springframework.social.salesforce.api.Salesforce;
import org.springframework.social.salesforce.api.SalesforceProfile;
import org.springframework.web.client.RestTemplate;

/**
 * Created by antoine
 * 23/09/16 1:55 PM.
 */
public class IdentityTemplate extends AbstractSalesForceOperations<Salesforce> implements IdentityOperations {

    private RestTemplate restTemplate;

    public IdentityTemplate(Salesforce api, RestTemplate restTemplate) {
        super(api);
        this.restTemplate = restTemplate;
    }

    @Override
    public UserProfile retrieveUserProfile() {
        final SalesforceProfile salesforceProfile = restTemplate.getForObject(api.getProfileUrl(), SalesforceProfile.class);

        return convertSalesforceProfileToUserProfile(salesforceProfile);
    }

    private static UserProfile convertSalesforceProfileToUserProfile(SalesforceProfile profile) {
        final String firstName = profile.getFirstName();
        final String lastName = profile.getLastName();
        final String email = profile.getEmail();

        return new UserProfileBuilder()
                .setId(profile.getId())
                .setName(firstName)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setUsername(email)
                .build();
    }
}
