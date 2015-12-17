package org.springframework.social.salesforce.api.impl.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.social.salesforce.api.QueryResult;
import org.springframework.social.salesforce.api.ResultItem;

import java.io.IOException;
import java.util.Map;

/**
 * Deserializer for {@see org.springframework.social.salesforce.api.ResultItem}
 *
 * @author Umut Utkan
 */
public class ResultItemDeserializer extends JsonDeserializer<ResultItem> {

    private static final TypeReference<Map<String, Object>> STRING_EMAIL_MAP = new TypeReference<Map<String, Object>>() {};

    @Override
    public ResultItem deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setConfig(ctxt.getConfig());
        jp.setCodec(mapper);

        JsonNode jsonNode = jp.readValueAsTree();
        Map<String, Object> map = mapper.readValue(mapper.treeAsTokens(jsonNode), STRING_EMAIL_MAP);
        ResultItem item = new ResultItem((String) ((Map) map.get("attributes")).get("type"),
                (String) ((Map) map.get("attributes")).get("url"));
        map.remove("attributes");

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            final Map attributes = item.getAttributes();
            final String entryKey = entry.getKey();

            if (entry.getValue() instanceof Map) {
                Map<String, Object> inner = (Map) entry.getValue();
                final JsonParser entryParser = mapper.treeAsTokens(jsonNode.get(entryKey));

                if (inner.get("records") != null) {
                    attributes.put(entryKey, mapper.readValue(entryParser, QueryResult.class));
                } else {
                    attributes.put(entryKey, mapper.readValue(entryParser, ResultItem.class));
                }
            } else {
                attributes.put(entryKey, entry.getValue());
            }

        }

        return item;
    }

}
