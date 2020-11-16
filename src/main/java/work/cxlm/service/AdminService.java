package work.cxlm.service;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import work.cxlm.model.entity.User;
import work.cxlm.model.params.LoginParam;
import work.cxlm.security.token.AuthToken;

/**
 * created 2020/10/21 14:49
 *
 * @author johnniang
 * @author ryanwang
 * @author cxlm
 */
@Service
public interface AdminService {
    /**
     * ACCESS TOKEN 时效
     */
    int ACCESS_TOKEN_EXPIRED_SECONDS = 24 * 3600;

    int REFRESH_TOKEN_EXPIRED_DAYS = 30;

    /**
     * 通过用户表单验证用户名、密码
     *
     * @param loginParam 登录表单，不可为 null
     * @return User 用户实例
     */
    @NonNull
    User authenticate(@NonNull LoginParam loginParam);

    /**
     * 清除用户授权
     */
    void clearToken();

    /**
     * 刷新 token.
     *
     * @param refreshToken 新的 token
     * @return AuthToken 刷新后的实例
     */
    @NonNull
    AuthToken refreshToken(@NonNull String refreshToken);
}
