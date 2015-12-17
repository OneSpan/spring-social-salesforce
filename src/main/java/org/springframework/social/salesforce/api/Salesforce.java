package org.springframework.social.salesforce.api;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.social.ApiBinding;

import java.util.List;

/**
 * Specifies operations performed on Salesforce.
 *
 * @author Umut Utkan
 */
public interface Salesforce extends ApiBinding {

    String PROVIDER_ID = "salesforce";

    ApiOperations apiOperations();

    ChatterOperations chatterOperations();

    QueryOperations queryOperations();

    RecentOperations recentOperations();

    SearchOperations searchOperations();

    SObjectOperations sObjectsOperations();

    <T> List<T> readList(JsonNode jsonNode, Class<T> type);

    <T> T readObject(JsonNode jsonNode, Class<T> type);

    String getBaseUrl();

    String getInstanceUrl();

}
