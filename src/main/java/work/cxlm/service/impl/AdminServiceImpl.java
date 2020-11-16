package work.cxlm.service.impl;

import cn.hutool.core.lang.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import work.cxlm.cache.AbstractStringCacheStore;
import work.cxlm.event.logger.LogEvent;
import work.cxlm.exception.BadRequestException;
import work.cxlm.exception.NotFoundException;
import work.cxlm.mail.MailService;
import work.cxlm.model.entity.User;
import work.cxlm.model.enums.LogType;
import work.cxlm.model.params.LoginParam;
import work.cxlm.security.authentication.Authentication;
import work.cxlm.security.context.SecurityContextHandler;
import work.cxlm.security.token.AuthToken;
import work.cxlm.security.util.SecurityUtils;
import work.cxlm.service.AdminService;
import work.cxlm.service.UserService;
import work.cxlm.utils.QfzsUtils;

import java.util.concurrent.TimeUnit;

/**
 * created 2020/10/21 15:40
 *
 * @author johnniang
 * @author ryanwang
 * @author cxlm
 */
@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;
    private final AbstractStringCacheStore cacheStore;
    private final MailService mailService;

    public AdminServiceImpl(UserService userService,
                            ApplicationEventPublisher eventPublisher,
                            AbstractStringCacheStore cacheStore,
                            MailService mailService) {
        this.userService = userService;
        this.eventPublisher = eventPublisher;
        this.cacheStore = cacheStore;
        this.mailService = mailService;
    }

    // FIXME: 登录不采用密码方式进行，需要修改验证相关逻辑
    @Override
    public User authenticate(LoginParam loginParam) {
        Assert.notNull(loginParam, "输入参数不能为 null");

        String username = loginParam.getUsername();

        String mismatchTip = "用户名或者密码不正确";

        final User user;

        try {
            // 通过用户名或邮箱获取用户
            user = Validator.isEmail(username) ?
                    userService.getByEmailOfNonNull(username) :
                    userService.getByRealNameOfNonNull(username);
        } catch (NotFoundException e) {
            log.error("用户名不存在： " + username);
            eventPublisher.publishEvent(new LogEvent(this, loginParam.getUsername(), LogType.LOGGED_FAILED, loginParam.getUsername()));

            throw new BadRequestException(mismatchTip);
        }

        mustNotExpire(user);

//        if (!userService.passwordMatch(user, loginParam.getPassword())) {
//            // 密码不匹配
//            eventPublisher.publishEvent(new LogEvent(this, loginParam.getUsername(), LogType.LOGGED_FAILED, loginParam.getUsername()));
//
//            throw new BadRequestException(mismatchTip);
//        }

        return user;
    }

    @Override
    public void clearToken() {
        Authentication authentication = SecurityContextHandler.getContext().getAuthentication();  // 获取登录凭证

        if (authentication == null) {
            throw new BadRequestException("您尚未登录，无法注销");
        }

        // 获取当前登录的用户
        User user = authentication.getUserDetail().getUser();

        // 清除 Token
        cacheStore.getAny(SecurityUtils.buildAccessTokenKey(user), String.class).ifPresent(accessToken -> {
            cacheStore.delete(SecurityUtils.buildAccessTokenKey(user));
            cacheStore.delete(SecurityUtils.buildAccessTokenKey(accessToken));
        });
        cacheStore.getAny(SecurityUtils.buildRefreshTokenKey(user), String.class).ifPresent(refreshToken -> {
            cacheStore.delete(SecurityUtils.buildRefreshTokenKey(user));
            cacheStore.delete(SecurityUtils.buildRefreshTokenKey(refreshToken));
        });

        // 注销事件
        eventPublisher.publishEvent(new LogEvent(this, user.getRealName(), LogType.LOGGED_OUT, "用户登出：" + user.getRealName()));

        log.info("用户登出");
    }

    @Override
    @NonNull
    public AuthToken refreshToken(String refreshToken) {
        Assert.hasText(refreshToken, "RefreshToken 不能为空");
        Integer uid = cacheStore.getAny(SecurityUtils.buildRefreshTokenKey(refreshToken), Integer.class)
                .orElseThrow(() -> new BadRequestException("登录状态已失效，请重新登录"));
        // 清除缓存的 Token
        User user = userService.getById(uid);
        cacheStore.getAny(SecurityUtils.buildAccessTokenKey(user), String.class)
                .ifPresent(accessToken -> cacheStore.delete(SecurityUtils.buildAccessTokenKey(accessToken)));
        cacheStore.delete(SecurityUtils.buildRefreshTokenKey(refreshToken));
        cacheStore.delete(SecurityUtils.buildAccessTokenKey(user));
        cacheStore.delete(SecurityUtils.buildRefreshTokenKey(user));
        // build auth
        return buildAuthToken(user);
    }

    @NonNull
    private AuthToken buildAuthToken(User user) {
        Assert.notNull(user, "用户不能为 Null");
        AuthToken authToken = new AuthToken();

        authToken.setAccessToken(QfzsUtils.randomUUIDWithoutDash());
        authToken.setExpiredIn(ACCESS_TOKEN_EXPIRED_SECONDS);
        authToken.setRefreshToken(QfzsUtils.randomUUIDWithoutDash());

        cacheStore.putAny(SecurityUtils.buildAccessTokenKey(authToken.getAccessToken()), user.getId(), ACCESS_TOKEN_EXPIRED_SECONDS, TimeUnit.SECONDS);
        cacheStore.putAny(SecurityUtils.buildRefreshTokenKey(authToken.getRefreshToken()), user.getId(), REFRESH_TOKEN_EXPIRED_DAYS, TimeUnit.DAYS);
        return authToken;
    }

    @Override
    public void mustNotExpire(User user) {
        Assert.notNull(user, "User 不能为 null");

        // TODO：验证此代码段是否可复用，若不可则移除
//        Date now = QfzsDateUtils.now();
//        if (user.getExpireTime() != null && user.getExpireTime().after(now)) {
//            // 未到达停用时间
//            long seconds = TimeUnit.MILLISECONDS.toSeconds(user.getExpireTime().getTime() - now.getTime());
//            throw new ForbiddenException("账号已被停用，请" + QfzsUtils.timeFormat(seconds) + "后重试").setErrorData(seconds);
//        }
    }
}
