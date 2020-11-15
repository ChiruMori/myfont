package work.cxlm.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import work.cxlm.event.logger.LogEvent;
import work.cxlm.event.UserUpdatedEvent;
import work.cxlm.exception.BadRequestException;
import work.cxlm.exception.ForbiddenException;
import work.cxlm.exception.NotFoundException;
import work.cxlm.model.entity.User;
import work.cxlm.model.enums.LogType;
import work.cxlm.model.params.UserParam;
import work.cxlm.repository.UserRepository;
import work.cxlm.service.UserService;
import work.cxlm.service.base.AbstractCrudService;
import work.cxlm.utils.MyFontDateUtils;
import work.cxlm.utils.MyFontUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * created 2020/10/22 16:00
 *
 * @author ryanwang
 * @author johnniang
 * @author cxlm
 */
@Service
public class UserServiceImpl extends AbstractCrudService<User, Integer> implements UserService {

    private final UserRepository userRepository;

    private final ApplicationEventPublisher eventPublisher;

    public UserServiceImpl(UserRepository userRepository,
                           ApplicationEventPublisher eventPublisher) {
        super(userRepository);
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Optional<User> getCurrentUser() {
        List<User> users = listAll();
        // 空的
        if (CollectionUtils.isEmpty(users)) {
            return Optional.empty();
        }
        // 返回第一个用户，系统仅存在一个合法用户
        return Optional.of(users.get(0));
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User getByUsernameOfNonNull(String username) {
        return getByUsername(username).orElseThrow(() -> new NotFoundException("用户名不存在").setErrorData(username));
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getByEmailOfNonNull(String email) {
        return getByEmail(email).orElseThrow(() -> new NotFoundException("邮箱地址不存在").setErrorData(email));
    }

    @Override
    public User updatePassword(String oldPassword, String newPassword, Integer userId) {
        Assert.hasText(oldPassword, "oldPassword 不能为空");
        Assert.hasText(newPassword, "newPassword 不能为空");
        Assert.notNull(userId, "userId 不能为 null");

        if (oldPassword.equals(newPassword)) {
            throw new BadRequestException("新密码和旧密码不能相同");
        }

        User targetUser = getById(userId);
        setPassword(targetUser, newPassword);

        User updatedUser = update(targetUser);

        eventPublisher.publishEvent(new LogEvent(this, updatedUser.getId().toString(), LogType.PWD_UPDATED,
                MyFontUtils.desensitize(oldPassword, 2, 1)));

        return updatedUser;
    }

    @Override
    public User createBy(UserParam userParam) {
        Assert.notNull(userParam, "用户参数对象不能为 null");
        User user = userParam.convertTo();
        setPassword(user, userParam.getPassword());
        return create(user);
    }

    @Override
    public void mustNotExpire(User user) {
        Assert.notNull(user, "User 不能为 null");

        Date now = MyFontDateUtils.now();
        if (user.getExpireTime() != null && user.getExpireTime().after(now)) {
            // 未到达停用时间
            long seconds = TimeUnit.MILLISECONDS.toSeconds(user.getExpireTime().getTime() - now.getTime());
            throw new ForbiddenException("账号已被停用，请" + MyFontUtils.timeFormat(seconds) + "后重试").setErrorData(seconds);
        }
    }

    @Override
    public boolean passwordMatch(User user, String plainPassword) {
        Assert.notNull(user, "用户必不能为 null");
        return !StringUtils.isBlank(plainPassword) && BCrypt.checkpw(plainPassword, user.getPassword());
    }

    @Override
    public void setPassword(User user, String plainPassword) {
        Assert.notNull(user, "用户不能为 null");
        Assert.hasText(plainPassword, "密码不能为空");
        // 使用 MD5 加密
        user.setPassword(BCrypt.hashpw(plainPassword, BCrypt.gensalt()));
    }

    @Override
    public boolean verifyUser(String username, String email) {
        User nowUser = getCurrentUser().orElseThrow(() -> new BadRequestException("无有效用户"));
        return nowUser.getUsername().equals(username) && nowUser.getEmail().equals(email);
    }

    @Override
    public User create(User user) {
        if (count() != 0) {
            throw new BadRequestException("当前系统不允许多个用户同时存在");
        }
        User newUser = super.create(user);
        eventPublisher.publishEvent(new UserUpdatedEvent(this, newUser.getId()));

        return newUser;
    }
}
