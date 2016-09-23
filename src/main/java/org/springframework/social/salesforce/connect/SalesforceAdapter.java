package org.springframework.social.salesforce.connect;

import org.springframework.social.ApiException;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.salesforce.api.Salesforce;

/**
 * Salesforce ApiAdapter implementation.
 *
 * @author Umut Utkan
 */
public class SalesforceAdapter implements ApiAdapter<Salesforce> {

    public boolean test(Salesforce salesForce) {
        try {
            fetchUserProfile(salesForce);
            return true;
        } catch (ApiException e) {
            return false;
        }
    }

    public void setConnectionValues(Salesforce salesforce, ConnectionValues values) {
        final UserProfile userProfile = fetchUserProfile(salesforce);
        values.setProfileUrl(salesforce.getProfileUrl());
        values.setProviderUserId(userProfile.getId());
        values.setDisplayName(userProfile.getFirstName() + ' ' + userProfile.getLastName());
    }

    public UserProfile fetchUserProfile(Salesforce salesforce) {
        return salesforce.identityOperations().retrieveUserProfile();
    }


    public void updateStatus(Salesforce salesforce, String message) {
        salesforce.chatterOperations().updateStatus(message);
    }
}
