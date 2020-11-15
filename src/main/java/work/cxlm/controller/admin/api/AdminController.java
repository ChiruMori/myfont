package work.cxlm.controller.admin.api;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import work.cxlm.annotation.DisableOnCondition;
import work.cxlm.cache.lock.CacheLock;
import work.cxlm.model.dto.StatisticDTO;
import work.cxlm.model.params.LoginParam;
import work.cxlm.model.params.ResetPasswordParam;
import work.cxlm.model.properties.PrimaryProperties;
import work.cxlm.model.support.BaseResponse;
import work.cxlm.security.token.AuthToken;
import work.cxlm.service.AdminService;
import work.cxlm.service.OptionService;

import javax.validation.Valid;

/**
 * created 2020/10/21 14:31
 *
 * @author cxlm
 */
@Slf4j
@RestController
@RequestMapping("/font/api/admin")
public class AdminController {

    private final AdminService adminService;
    private final OptionService optionService;

    public AdminController(AdminService adminService, OptionService optionService) {
        this.adminService = adminService;
        this.optionService = optionService;
    }

    @ApiOperation("检验系统安装状态")
    @GetMapping("/is_installed")
    public boolean isInstall() {
        return optionService.getByPropertyOrDefault(PrimaryProperties.IS_INSTALLED, Boolean.class, false);
    }

    @ApiOperation("登录")
    @CacheLock(autoDelete = false, prefix = "login_check")
    @PostMapping("/login")
    public void authLogin(@RequestBody @Valid LoginParam loginParam) {
        adminService.authenticate(loginParam);
    }

    @ApiOperation("登出")
    @CacheLock(autoDelete = false)
    @PostMapping("/logout")
    public void logout() {
        adminService.clearToken();
    }

    @ApiOperation("发送用于重设密码的验证码")
    @CacheLock(autoDelete = false)
    @PostMapping("/pwd/code")
    @DisableOnCondition
    public void sendResetCode(@RequestBody @Valid ResetPasswordParam resetParam) {
        adminService.sendResetPasswordCode(resetParam);
    }

    @ApiOperation("使用 code 重设密码")
    @CacheLock("false")
    @PostMapping("/pwd/reset")
    @DisableOnCondition
    public void resetPwdByCode(@RequestBody @Valid ResetPasswordParam resetParam) {
        adminService.resetPasswordByCode(resetParam);
    }

    @ApiOperation("刷新 Token")
    @PostMapping("refresh/{refreshToken}")
    @CacheLock(autoDelete = false)
    public AuthToken refresh(@PathVariable("refreshToken") String refreshToken) {
        return adminService.refreshToken(refreshToken);
    }

    @ApiOperation("获取统计数据")
    @GetMapping("counts")
    @Deprecated
    public StatisticDTO getCount() {
        return adminService.getCount();
    }

}
