package work.cxlm.security.util;

import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import work.cxlm.model.entity.User;

/**
 * created 2020/11/9 10:03
 *
 * @author johnniang
 * @author Chiru
 */
public class SecurityUtils {

    // Access Token 前缀
    private static final String TOKEN_ACCESS_CACHE_PREFIX = "qfzs.admin.access.token.";
    private static final String ACCESS_TOKEN_CACHE_PREFIX = "qfzs.admin.access_token.";

    public static final String TOKEN_REFRESH_CACHE_PREFIX = "qfzs.admin.refresh.token.";
    public static final String REFRESH_TOKEN_CACHE_PREFIX = "qfzs.admin.refresh_token.";

    @NonNull
    public static String buildAccessTokenKey(@NonNull User user) {
        Assert.notNull(user, "用户不能为 null");
        return ACCESS_TOKEN_CACHE_PREFIX + user.getId();
    }

    @NonNull
    public static String buildAccessTokenKey(@NonNull String accessToken) {
        Assert.hasText(accessToken, "Access Token 不能为空");
        return TOKEN_REFRESH_CACHE_PREFIX + accessToken;
    }

    @NonNull
    public static String buildRefreshTokenKey(@NonNull User user) {
        Assert.notNull(user, "用户不能为 null");
        return REFRESH_TOKEN_CACHE_PREFIX + user.getId();
    }

    @NonNull
    public static String buildRefreshTokenKey(@NonNull String refreshToken) {
        Assert.hasText(refreshToken, "Refresh token 不能为空");
        return TOKEN_REFRESH_CACHE_PREFIX + refreshToken;
    }
}
