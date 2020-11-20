package work.cxlm.security.context;

import org.springframework.lang.Nullable;
import work.cxlm.security.authentication.Authentication;

/**
 * 安全上下文
 * created 2020/11/9 9:20
 *
 * @author johnniang
 * @author Chiru
 */
public interface SecurityContext {

    /**
     * 获取当前已验证的验证实例
     */
    @Nullable
    Authentication getAuthentication();

    /**
     * 为当前安全上下文设置登录凭证
     */
    void setAuthentication(@Nullable Authentication authentication);

    /**
     * 获取当前安全上下文是否已存在合法登录凭证
     */
    default boolean isAuthenticated() {
        return getAuthentication() != null;
    }
}
