package org.springframework.social.salesforce.connect.test;

import org.springframework.social.ApiException;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.salesforce.api.test.SalesforceTest;

/**
 * Created by mpoitras on 07/02/14.
 */
public class SalesforceTestAdapter implements ApiAdapter<SalesforceTest> {

    public boolean test(SalesforceTest salesForce) {
        try {
            fetchUserProfile(salesForce);
            return true;
        } catch (ApiException e) {
            return false;
        }
    }

    public void setConnectionValues(SalesforceTest salesforce, ConnectionValues values) {
        final UserProfile userProfile = fetchUserProfile(salesforce);
        values.setProfileUrl(salesforce.getProfileUrl());
        values.setProviderUserId(userProfile.getId());
        values.setDisplayName(userProfile.getFirstName() + ' ' + userProfile.getLastName());
    }

    public UserProfile fetchUserProfile(SalesforceTest salesforce) {
        return salesforce.identityOperations().retrieveUserProfile();
    }


    public void updateStatus(SalesforceTest salesforce, String message) {
        salesforce.chatterOperations().updateStatus(message);
    }
}
