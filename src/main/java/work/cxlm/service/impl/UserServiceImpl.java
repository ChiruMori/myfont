package work.cxlm.service.impl;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import work.cxlm.event.LogEvent;
import work.cxlm.exception.BadRequestException;
import work.cxlm.exception.NotFoundException;
import work.cxlm.model.entity.User;
import work.cxlm.model.enums.LogType;
import work.cxlm.model.params.UserParam;
import work.cxlm.repository.UserRepository;
import work.cxlm.service.UserService;
import work.cxlm.service.base.AbstractCrudService;
import work.cxlm.utils.MyFontUtils;

import java.util.List;
import java.util.Optional;

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
        return null;
    }

    @Override
    public void mustNotExpire(User user) {

    }

    @Override
    public boolean passwordMatch(User user, String plainPassword) {
        return false;
    }

    @Override
    public void setPassword(User user, String plainPassword) {

    }

    @Override
    public boolean verifyUser(String username, String email) {
        return false;
    }
}
