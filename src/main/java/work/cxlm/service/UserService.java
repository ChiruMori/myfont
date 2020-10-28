package work.cxlm.service;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import work.cxlm.exception.ForbiddenException;
import work.cxlm.exception.NotFoundException;
import work.cxlm.model.entity.User;
import work.cxlm.model.params.UserParam;
import work.cxlm.service.base.CrudService;

import java.util.Optional;

/**
 * created 2020/10/21 16:01
 *
 * @author johnniang
 * @author ryanwang
 * @author cxlm
 */
public interface UserService extends CrudService<User, Integer> {
    /**
     * 统计失败登录尝试的 key
     */
    String LOGIN_FAILURE_COUNT_KEY = "login.failure.count";

    /**
     * 最大允许失败尝试次数
     */
    int MAX_LOGIN_TRY = 5;

    /**
     * 失败次数到达上限后，锁住的时间
     */
    int LOCK_MINUTES = 10;

    /**
     * 获取当前用户
     *
     * @return Optional 封装的 User
     */
    @NonNull
    Optional<User> getCurrentUser();

    /**
     * 通过用户名获取用户
     *
     * @param username 用户名，不可为 null
     * @return Optional 封装的 User
     */
    @NonNull
    Optional<User> getByUsername(@NonNull String username);

    /**
     * 通过用户名获取用户
     *
     * @param username 用户名，不可为 null
     * @return User 实例
     * @throws NotFoundException 用户不存在时抛出
     */
    @NonNull
    User getByUsernameOfNonNull(@NonNull String username);

    /**
     * 通过邮箱地址获取用户
     *
     * @param email 邮箱地址，不可为 null
     * @return User 实例
     * @throws NotFoundException 用户不存在时抛出
     */
    @NonNull
    Optional<User> getByEmail(@NonNull String email);

    /**
     * 通过邮箱地址获取用户
     *
     * @param email 邮箱地址，不可为 null
     * @return User 实例
     * @throws NotFoundException 用户不存在时抛出
     */
    @NonNull
    User getByEmailOfNonNull(@NonNull String email);

    /**
     * 更新用户密码
     *
     * @param oldPassword 旧密码，不能为空
     * @param newPassword 新密码，不能为空
     * @param userId      用户 ID，不能为 null
     * @return 更新后的用户信息
     */
    @NonNull
    User updatePassword(@NonNull String oldPassword, @NonNull String newPassword, @NonNull Integer userId);

    /**
     * 新建一个用户（持久化）
     *
     * @param userParam 用户信息表单，不能为 null
     * @return 新建的 User 实例
     */
    @NonNull
    User createBy(@NonNull UserParam userParam);

    /**
     * 用户未过期
     *
     * @param user 用户实例，必不能为 null
     * @throws ForbiddenException 用户已过期时抛出
     */
    void mustNotExpire(@NonNull User user);

    /**
     * 校验密码与用户密码是否匹配
     *
     * @param user          用户实例，必不能为 null
     * @param plainPassword 明文密码
     */
    boolean passwordMatch(@NonNull User user, @Nullable String plainPassword);

    /**
     * 设置用户密码
     *
     * @param user          用户实例，必不能为 null
     * @param plainPassword 明文密码，不能为空
     */
    void setPassword(@NonNull User user, @NonNull String plainPassword);

    /**
     * 校验用户名和邮箱地址
     *
     * @param username 用户名，不能为 null
     * @param email    邮箱地址，不能为 null
     * @return boolean
     */
    boolean verifyUser(@NonNull String username, @NonNull String email);

}
