package work.cxlm.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import work.cxlm.cache.AbstractStringCacheStore;
import work.cxlm.cache.CacheStore;
import work.cxlm.config.QfzsProperties;
import work.cxlm.event.UserUpdatedEvent;
import work.cxlm.exception.BadRequestException;
import work.cxlm.exception.NotFoundException;
import work.cxlm.model.entity.User;
import work.cxlm.model.params.UserLoginParam;
import work.cxlm.model.params.UserParam;
import work.cxlm.model.rpc.RpcClient;
import work.cxlm.model.rpc.code2session.Code2SessionParam;
import work.cxlm.model.rpc.code2session.Code2SessionResponse;
import work.cxlm.repository.UserRepository;
import work.cxlm.service.UserService;
import work.cxlm.service.base.AbstractCrudService;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static work.cxlm.model.support.QfzsConst.USER_CACHE_PREFIX;

/**
 * created 2020/10/22 16:00
 *
 * @author ryanwang
 * @author johnniang
 * @author cxlm
 */
@Service
@Slf4j
public class UserServiceImpl extends AbstractCrudService<User, Integer> implements UserService {

    private final UserRepository userRepository;
    private final QfzsProperties qfzsProperties;
    private final ApplicationEventPublisher eventPublisher;
    private final AbstractStringCacheStore cacheStore;

    public UserServiceImpl(UserRepository userRepository,
                           ApplicationEventPublisher eventPublisher,
                           QfzsProperties qfzsProperties,
                           AbstractStringCacheStore cacheStore) {
        super(userRepository);
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
        this.qfzsProperties = qfzsProperties;
        this.cacheStore = cacheStore;
    }

//    @Override
//    public User create(User user) {
//        if (count() != 0) {
//            throw new BadRequestException("当前系统不允许多个用户同时存在");
//        }
//        User newUser = super.create(user);
//        eventPublisher.publishEvent(new UserUpdatedEvent(this, newUser.getId()));
//
//        return newUser;
//    }

    @Override
    public String getOpenIdByCode(@NonNull String code) {
        Code2SessionParam param = new Code2SessionParam(qfzsProperties.getAppId(), qfzsProperties.getAppSecret(),
                code, "authorization_code");
        Code2SessionResponse response = RpcClient.getUrl(qfzsProperties.getAppRequestUrl(), Code2SessionResponse.class, param);
        return response.getOpenid();
    }

    @Override
    public User login(@Nullable String openId) {
        if (openId == null) {
            log.debug("openId 为空，无法登录");
            return null;
        }
        String nowUserCacheKey = USER_CACHE_PREFIX + openId;
        return cacheStore.getAny(nowUserCacheKey, User.class).orElseGet(() -> {
            Optional<User> userInStorage = userRepository.findByWxId(openId);
            if (userInStorage.isPresent()) {  // 存在该用户，进行缓存并返回
                cacheStore.putAny(nowUserCacheKey, User.class, 30, TimeUnit.MINUTES);
                log.info("用户 [{}] 登录系统", userInStorage.get().getRealName());
                return userInStorage.get();
            }
            log.debug("登录失败，openId: [{}]", openId);
            return null;
        });
    }

    @Override
    @Nullable
    public User updateUserByParam(@Nullable User user, @NonNull UserParam param) {
        if (user == null) { // 缓存中没有用户信息
            user = userRepository.findByStudentNo(param.getStudentNo()).orElse(null);
            if (user == null) { // 数据库中也没有用户信息
                return null;
            }
        }
        param.update(user);
        eventPublisher.publishEvent(new UserUpdatedEvent(param, user.getId()));
        cacheStore.putAny(USER_CACHE_PREFIX + user.getWxId(), User.class, 30, TimeUnit.MINUTES);
        return user;
    }
}
