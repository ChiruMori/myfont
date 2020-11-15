package work.cxlm.controller.admin.api;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import work.cxlm.cache.lock.CacheLock;
import work.cxlm.event.logger.LogEvent;
import work.cxlm.exception.BadRequestException;
import work.cxlm.model.entity.User;
import work.cxlm.model.enums.LogType;
import work.cxlm.model.params.InstallParam;
import work.cxlm.model.properties.EmailProperties;
import work.cxlm.model.properties.PrimaryProperties;
import work.cxlm.model.properties.PropertyEnum;
import work.cxlm.model.support.BaseResponse;
import work.cxlm.model.support.CreateCheck;
import work.cxlm.service.OptionService;
import work.cxlm.service.UserService;
import work.cxlm.utils.ValidationUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * created 2020/11/15 10:45
 *
 * @author ryanwang
 * @author Chiru
 */
@Slf4j
@Controller
@RequestMapping("/font/api/admin/installations")
public class InstallController {

    private final OptionService optionService;
    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;

    public InstallController(OptionService optionService,
                             UserService userService,
                             ApplicationEventPublisher eventPublisher) {
        this.optionService = optionService;
        this.userService = userService;
        this.eventPublisher = eventPublisher;
    }

    @ApiOperation("系统初始化（安装）")
    @PostMapping
    @ResponseBody
    @CacheLock
    public BaseResponse<String> installSystem(@RequestBody InstallParam installParam) {
        ValidationUtils.validate(installParam, CreateCheck.class);  // 校验表单
        boolean isInstalled = optionService.getByPropertyOrDefault(PrimaryProperties.IS_INSTALLED, Boolean.class, false);
        if (isInstalled) {
            throw new BadRequestException("系统已安装，请勿重复安装");
        }
        initSettings(installParam);
        User admin = createUser(installParam);

        eventPublisher.publishEvent(new LogEvent(this, admin.getId().toString(), LogType.SYSTEM_INSTALLED, "系统安装成功"));
        return BaseResponse.ok("安装成功");
    }

    private User createUser(InstallParam installParam) {
        // Get user
        return userService.getCurrentUser().map(user -> {
            installParam.update(user);
            userService.setPassword(user, installParam.getPassword());
            return userService.update(user);
        }).orElseGet(() -> {
            // 用户 Gravatar 头像的设定
            // String gravatar = "//cn.gravatar.com/avatar/" + SecureUtil.md5(installParam.getEmail()) +
            //         "?s=256&d=mm";
            // installParam.setAvatar(gravatar);
            return userService.createBy(installParam);
        });
    }


    // 初始化系统设置
    private void initSettings(InstallParam installParam) {
        Map<PropertyEnum, String> properties = new HashMap<>(8);
        properties.put(PrimaryProperties.IS_INSTALLED, Boolean.TRUE.toString());
        // 初始化系统相关参数
        properties.put(EmailProperties.HOST, installParam.getEmailHost());
        properties.put(EmailProperties.PROTOCOL, installParam.getPassword());
        properties.put(EmailProperties.SSL_PORT, installParam.getEmailSslPort().toString());
        properties.put(EmailProperties.USERNAME, installParam.getEmailUsername());
        properties.put(EmailProperties.PASSWORD, installParam.getEmailPassword());
        properties.put(EmailProperties.FROM_NAME, installParam.getFromName());

        Long startTime = optionService.getByPropertyOrDefault(PrimaryProperties.START_TIME, Long.class, 0L);

        if (startTime.equals(0L)) {
            properties.put(PrimaryProperties.START_TIME, String.valueOf(System.currentTimeMillis()));
        }

        optionService.saveProperties(properties);
    }
}
