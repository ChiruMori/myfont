package work.cxlm.repository;

import org.springframework.lang.NonNull;
import work.cxlm.model.entity.User;

import java.util.Optional;

/**
 * created 2020/10/22 16:03
 *
 * @author johnniang
 * @author cxlm
 */
public interface UserRepository extends BaseRepository<User, Integer> {
    /**
     * @param realName 用户名，不可为 null
     * @return Optional 包装的 User 实例
     */
    @NonNull
    Optional<User> findByRealName(@NonNull String realName);

    /**
     * @param email 邮箱地址，不可为 null
     * @return Optional 包装的 User 实例
     */
    @NonNull
    Optional<User> findByEmail(@NonNull String email);
}
