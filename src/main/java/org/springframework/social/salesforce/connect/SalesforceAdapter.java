package org.springframework.social.salesforce.connect;

import org.springframework.social.ApiException;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;
import org.springframework.social.salesforce.api.Salesforce;
import org.springframework.social.salesforce.api.SalesforceProfile;

/**
 * Salesforce ApiAdapter implementation.
 *
 * @author Umut Utkan
 */
public class SalesforceAdapter implements ApiAdapter<Salesforce> {

    public boolean test(Salesforce salesForce) {
        try {
            salesForce.chatterOperations().getUserProfile();
            return true;
        } catch (ApiException e) {
            return false;
        }
    }

    public void setConnectionValues(Salesforce salesforce, ConnectionValues values) {
        SalesforceProfile profile = salesforce.chatterOperations().getUserProfile();
        values.setProviderUserId(profile.getId());
        values.setDisplayName(profile.getFirstName() + " " + profile.getLastName());
    }

    public UserProfile fetchUserProfile(Salesforce salesforce) {
        SalesforceProfile profile = salesforce.chatterOperations().getUserProfile();

        final String firstName = profile.getFirstName();
        final String lastName = profile.getLastName();
        final String email = profile.getEmail();

        return new UserProfileBuilder()
                .setName(firstName)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setEmail(email)
                .setUsername(email)
                .build();
    }


    public void updateStatus(Salesforce salesforce, String message) {
        salesforce.chatterOperations().updateStatus(message);
    }
}
