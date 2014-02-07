package org.springframework.social.salesforce.connect.test;

import org.springframework.social.ApiException;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;
import org.springframework.social.salesforce.api.SalesforceProfile;
import org.springframework.social.salesforce.api.test.SalesforceTest;

/**
 * Created by mpoitras on 07/02/14.
 */
public class SalesforceTestAdapter implements ApiAdapter<SalesforceTest> {

    public boolean test(SalesforceTest salesForce) {
        try {
            salesForce.chatterOperations().getUserProfile();
            return true;
        } catch (ApiException e) {
            return false;
        }
    }

    public void setConnectionValues(SalesforceTest salesforce, ConnectionValues values) {
        SalesforceProfile profile = salesforce.chatterOperations().getUserProfile();
        values.setProviderUserId(profile.getId());
        values.setDisplayName(profile.getFirstName() + " " + profile.getLastName());
    }

    public UserProfile fetchUserProfile(SalesforceTest salesforce) {
        SalesforceProfile profile = salesforce.chatterOperations().getUserProfile();
        return new UserProfileBuilder().setName(profile.getFirstName()).setFirstName(profile.getFirstName())
                .setLastName(profile.getLastName()).setEmail(profile.getEmail())
                .setUsername(profile.getEmail()).build();
    }


    public void updateStatus(SalesforceTest salesforce, String message) {
        salesforce.chatterOperations().updateStatus(message);
    }
}
