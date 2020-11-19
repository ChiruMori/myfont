package work.cxlm.security.ott;

import org.springframework.lang.NonNull;

import java.util.Optional;

/**
 * 管理一次性口令的接口
 * created 2020/11/19 16:23
 *
 * @author johnniang
 * @author Chiru
 */
public interface OneTimeTokenService {

    /**
     * 根据 Token 得到对应的 URL
     */
    @NonNull
    Optional<String> get(@NonNull String oneTimeToken);

    /**
     * 使用 URL 生成对应的 Token
     */
    @NonNull
    String create(@NonNull String uri);

    /**
     * 收回发放的 Token
     */
    void revoke(@NonNull String oneTimeToken);
}
