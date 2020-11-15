package work.cxlm.service.impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import work.cxlm.cache.AbstractStringCacheStore;
import work.cxlm.config.MyFontProperties;
import work.cxlm.event.logger.LogEvent;
import work.cxlm.exception.BadRequestException;
import work.cxlm.exception.MissingPropertyException;
import work.cxlm.exception.NotFoundException;
import work.cxlm.exception.ServiceException;
import work.cxlm.mail.MailService;
import work.cxlm.model.dto.StatisticDTO;
import work.cxlm.model.entity.User;
import work.cxlm.model.enums.LogType;
import work.cxlm.model.params.LoginParam;
import work.cxlm.model.params.ResetPasswordParam;
import work.cxlm.security.authentication.Authentication;
import work.cxlm.security.context.SecurityContextHandler;
import work.cxlm.security.token.AuthToken;
import work.cxlm.security.util.SecurityUtils;
import work.cxlm.service.AdminService;
import work.cxlm.service.UserService;
import work.cxlm.utils.MyFontUtils;

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
                    userService.getByUsernameOfNonNull(username);
        } catch (NotFoundException e) {
            log.error("用户名不存在： " + username);
            eventPublisher.publishEvent(new LogEvent(this, loginParam.getUsername(), LogType.LOGGED_FAILED, loginParam.getUsername()));

            throw new BadRequestException(mismatchTip);
        }

        userService.mustNotExpire(user);

        if (!userService.passwordMatch(user, loginParam.getPassword())) {
            // 密码不匹配
            eventPublisher.publishEvent(new LogEvent(this, loginParam.getUsername(), LogType.LOGGED_FAILED, loginParam.getUsername()));

            throw new BadRequestException(mismatchTip);
        }

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
        eventPublisher.publishEvent(new LogEvent(this, user.getUsername(), LogType.LOGGED_OUT, "用户登出：" + user.getNickname()));

        log.info("用户登出");
    }

    @Override
    public void sendResetPasswordCode(ResetPasswordParam param) {
        cacheStore.getAny("code", String.class).ifPresent(code -> {
            throw new ServiceException("已经获取过验证码，请查收或者稍后再试");
        });

        // 生成四位随机数
        String code = RandomUtil.randomNumbers(8);
        log.info("获取了重设密码验证码：[{}]", code);

        // 设置验证码有效时长
        cacheStore.putAny("code", code, 5, TimeUnit.MINUTES);

        String content = "您正在进行重置密码操作，如果不是本人操作，请尽快应对。重置验证码为【" + code + "】，五分钟有效";
        mailService.sendTextMail(param.getEmail(), "MyFont：找回密码", content);
    }

    @Override
    public void resetPasswordByCode(ResetPasswordParam param) {
        if (StringUtils.isEmpty(param.getCode())) {
            throw new MissingPropertyException("验证码不能为空");
        }

        if (StringUtils.isEmpty(param.getPassword())) {
            throw new MissingPropertyException("密码不能为空");
        }

        if (!userService.verifyUser(param.getUsername(), param.getEmail())) {
            throw new BadRequestException("用户名、邮箱验证失败");
        }

        String code = cacheStore.getAny("code", String.class).orElseThrow(() -> new BadRequestException("请首先获取验证码"));
        if (!code.equals(param.getCode())) {
            throw new BadRequestException("验证码错误");
        }

        // 更新用户名密码，删除缓存的验证码
        User nowUser = userService.getCurrentUser().orElseThrow(() -> new BadRequestException("请先初始化系统"));
        userService.setPassword(nowUser, param.getPassword());
        userService.update(nowUser);
        cacheStore.delete("code");
    }

    @Override
    public StatisticDTO getCount() {
        StatisticDTO res = new StatisticDTO();
        // TODO: 统计字体相关数据（调用 FontService 相关过程统计数量）
        res.setAttachCount(0);
        res.setCreatedFontCount(0);
        res.setFontCount(0);
        res.setKanjiCount(0);
        return res;
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

        authToken.setAccessToken(MyFontUtils.randomUUIDWithoutDash());
        authToken.setExpiredIn(ACCESS_TOKEN_EXPIRED_SECONDS);
        authToken.setRefreshToken(MyFontUtils.randomUUIDWithoutDash());

        cacheStore.putAny(SecurityUtils.buildAccessTokenKey(authToken.getAccessToken()), user.getId(), ACCESS_TOKEN_EXPIRED_SECONDS, TimeUnit.SECONDS);
        cacheStore.putAny(SecurityUtils.buildRefreshTokenKey(authToken.getRefreshToken()), user.getId(), REFRESH_TOKEN_EXPIRED_DAYS, TimeUnit.DAYS);
        return authToken;
    }
}
