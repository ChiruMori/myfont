package work.cxlm.security.token;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * created 2020/10/21 15:29
 *
 * @author johnniang
 * @author cxlm
 */
public class AuthToken {

    /**
     * Access token.
     */
    @JsonProperty("access_token")
    private String accessToken;

    /**
     * Expired in. (seconds)
     */
    @JsonProperty("expired_in")
    private int expiredIn;

    /**
     * Refresh token.
     */
    @JsonProperty("refresh_token")
    private String refreshToken;
}
