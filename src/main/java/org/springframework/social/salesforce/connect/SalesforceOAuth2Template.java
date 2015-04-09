package org.springframework.social.salesforce.connect;

import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.net.URLDecoder.decode;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.util.StringUtils.isEmpty;

/**
 * Salesforce OAuth2Template implementation.
 * <p/>
 * The reason to extend is to extract non-standard instance_url from Salesforce's oauth token response.
 *
 * @author Umut Utkan
 */
public class SalesforceOAuth2Template extends OAuth2Template {

    private static final Pattern TLD_REGEX = Pattern.compile(".*?([^.]+\\.[^.]+)");
    private static final List<String> VALID_SALESFORCE_DOMAINS = Arrays.asList("force.com", "salesforce.com");


    static final String AUTHORIZE_PATH = "services/oauth2/authorize";
    static final String TOKEN_PATH = "services/oauth2/token";


    private String instanceUrl = null;
    private final String baseUrl;


    public SalesforceOAuth2Template(String clientId, String clientSecret, String baseUrl) {
        super(clientId, clientSecret, baseUrl + AUTHORIZE_PATH, null, baseUrl + TOKEN_PATH);
        setUseParametersForClientAuthentication(true);
        this.baseUrl = baseUrl;
    }


    @Override
    public String buildAuthorizeUrl(GrantType grantType, OAuth2Parameters parameters) {

        String endpointUrlParam = parameters.getFirst("endpoint");

        /** Endpoint parameter should be removed before building the Authorize URL:
           We are only using it for use with custom SF domains. **/
        parameters.remove("endpoint");

        final String baseAuthorizeURL = super.buildAuthorizeUrl(grantType, parameters);

        if (isEmpty(endpointUrlParam)) {
            return baseAuthorizeURL;
        } else {

            boolean isValidSalesforceHostname;
            final String endpointUrlHost;
            final String protocol;

            try {
                final URI uri = new URI(decode(endpointUrlParam, UTF_8.name()));
                endpointUrlHost = uri.getHost();
                protocol = getProtocol(uri);

                final Matcher m = TLD_REGEX.matcher(endpointUrlHost);
                isValidSalesforceHostname = (m.matches() && VALID_SALESFORCE_DOMAINS.contains(m.group(1)));

            } catch (Exception e) {
                return baseAuthorizeURL;
            }

            return isValidSalesforceHostname ? baseAuthorizeURL.replaceFirst(baseUrl, protocol + verifyURLEndsWithSlash(endpointUrlHost)) : baseAuthorizeURL;
        }
    }

    @Override
    protected AccessGrant createAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn, Map<String, Object> response) {
        this.instanceUrl = (String) response.get("instance_url");

        return super.createAccessGrant(accessToken, scope, refreshToken, expiresIn, response);
    }

    private String getProtocol(URI uri) {
        return uri.getScheme() + "://";
    }


    public String getInstanceUrl() {
        return instanceUrl;
    }

    private String verifyURLEndsWithSlash(String endpointUrl) {
        return endpointUrl.endsWith("/") ? endpointUrl : endpointUrl + "/";
    }

}
