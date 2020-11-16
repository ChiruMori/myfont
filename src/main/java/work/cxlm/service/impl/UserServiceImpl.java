package work.cxlm.service.impl;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import work.cxlm.event.UserUpdatedEvent;
import work.cxlm.exception.BadRequestException;
import work.cxlm.exception.NotFoundException;
import work.cxlm.model.entity.User;
import work.cxlm.model.params.UserParam;
import work.cxlm.repository.UserRepository;
import work.cxlm.service.UserService;
import work.cxlm.service.base.AbstractCrudService;

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
    public Optional<User> getByRealName(String realName) {
        return userRepository.findByRealName(realName);
    }

    @Override
    public User getByRealNameOfNonNull(String realName) {
        return getByRealName(realName).orElseThrow(() -> new NotFoundException("用户名不存在").setErrorData(realName));
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
    public User createBy(UserParam userParam) {
        Assert.notNull(userParam, "用户参数对象不能为 null");
        User user = userParam.convertTo();
        return create(user);
    }

    @Override
    public boolean verifyUser(String realName, String email) {
        User nowUser = getCurrentUser().orElseThrow(() -> new BadRequestException("无有效用户"));
        return nowUser.getRealName().equals(realName) && nowUser.getEmail().equals(email);
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
