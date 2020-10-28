package work.cxlm.service.impl;

import cn.hutool.core.lang.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import work.cxlm.event.LogEvent;
import work.cxlm.exception.BadRequestException;
import work.cxlm.exception.NotFoundException;
import work.cxlm.model.dto.StatisticDTO;
import work.cxlm.model.entity.User;
import work.cxlm.model.enums.LogType;
import work.cxlm.model.params.LoginParam;
import work.cxlm.model.params.ResetPasswordParam;
import work.cxlm.security.token.AuthToken;
import work.cxlm.service.AdminService;
import work.cxlm.service.UserService;

/**
 * created 2020/10/21 15:40
 *
 * @author johnniang
 * @author ryanwang
 * @author cxlm
 * TODO IMPLEMENT THIS
 */
@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;

    public AdminServiceImpl(UserService userService,
                            ApplicationEventPublisher eventPublisher) {
        this.userService = userService;
        this.eventPublisher = eventPublisher;
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
            // TODO 事件监听
            eventPublisher.publishEvent(new LogEvent(this, loginParam.getUsername(), LogType.LOGGED_FAILED, loginParam.getUsername()));

            throw new BadRequestException(mismatchTip);
        }

        userService.mustNotExpire(user);

        if (!userService.passwordMatch(user, loginParam.getPassword())) {
            // If the password is mismatch
            eventPublisher.publishEvent(new LogEvent(this, loginParam.getUsername(), LogType.LOGGED_FAILED, loginParam.getUsername()));

            throw new BadRequestException(mismatchTip);
        }

        return user;
    }

    @Override
    public void clearToken() {

    }

    @Override
    public void sendResetPasswordCode(ResetPasswordParam param) {

    }

    @Override
    public void resetPasswordByCode(ResetPasswordParam param) {

    }

    @Override
    public StatisticDTO getCount() {
        return null;
    }

    @Override
    public AuthToken refreshToken(String refreshToken) {
        return null;
    }
}
