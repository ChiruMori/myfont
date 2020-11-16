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
     * @param realName 用户名，不可为 null
     * @return Optional 封装的 User
     */
    @NonNull
    Optional<User> getByRealName(@NonNull String realName);

    /**
     * 通过用户名获取用户
     *
     * @param realName 用户名，不可为 null
     * @return User 实例
     * @throws NotFoundException 用户不存在时抛出
     */
    @NonNull
    User getByRealNameOfNonNull(@NonNull String realName);

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
     * 新建一个用户（持久化）
     *
     * @param userParam 用户信息表单，不能为 null
     * @return 新建的 User 实例
     */
    @NonNull
    User createBy(@NonNull UserParam userParam);


    /**
     * 校验用户名和邮箱地址
     *
     * @param realName 用户名，不能为 null
     * @param email    邮箱地址，不能为 null
     * @return boolean
     */
    boolean verifyUser(@NonNull String realName, @NonNull String email);

}
