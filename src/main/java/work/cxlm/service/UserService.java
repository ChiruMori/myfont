package work.cxlm.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import work.cxlm.exception.NotFoundException;
import work.cxlm.model.entity.User;
import work.cxlm.model.params.UserLoginParam;
import work.cxlm.model.params.UserParam;
import work.cxlm.model.vo.PageUserVO;
import work.cxlm.model.vo.PasscodeVO;
import work.cxlm.security.token.AuthToken;
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

//    /**
//     * 通过用户名获取用户
//     *
//     * @param realName 用户名，不可为 null
//     * @return Optional 封装的 User
//     */
//    @NonNull
//    Optional<User> getByRealName(@NonNull String realName);
//
//    /**
//     * 通过用户名获取用户
//     *
//     * @param realName 用户名，不可为 null
//     * @return User 实例
//     * @throws NotFoundException 用户不存在时抛出
//     */
//    @NonNull
//    User getByRealNameOfNonNull(@NonNull String realName);

    /**
     * 通过用户登录凭证获取 openId
     *
     * @param code 登录凭证
     * @return openId，可能为 null
     */
    @Nullable
    String getOpenIdByCode(@NonNull String code);

    /**
     * 用户使用 openId 登录，通过小程序登录
     *
     * @param openId 小程序中得到的 openId，用户唯一标识
     * @return 登录后的用户凭证
     */
    @Nullable
    AuthToken login(@Nullable String openId);

    /**
     * 通过表单更新用户信息
     *
     * @param param 填写的表单
     * @return 更新后的用户，如果找不到对应的用户将返回 null
     */
    @Nullable
    User updateUserByParam(@NonNull UserParam param);

    @NonNull
    Page<PageUserVO> getClubUserPage(@Nullable Integer clubId, Pageable pageable);

    /**
     * 使用用户创建 Passcode 传输对象
     */
    @NonNull
    PasscodeVO getPasscode();

    /**
     * 刷新用户登录凭证到期事件
     */
    AuthToken refreshToken(String refreshToken);

    /**
     * 通过 openId 查询用户信息
     */
    User getByOpenId(String integer);
}
