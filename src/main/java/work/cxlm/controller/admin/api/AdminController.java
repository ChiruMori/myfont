package work.cxlm.controller.admin.api;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import work.cxlm.cache.lock.CacheLock;
import work.cxlm.model.params.LoginParam;
import work.cxlm.security.token.AuthToken;
import work.cxlm.service.AdminService;

import javax.validation.Valid;

/**
 * created 2020/10/21 14:31
 *
 * @author cxlm
 */
@Slf4j
@RestController
@RequestMapping("/key3/admin/")
public class AdminController {

//    private final AdminService adminService;
//
//    public AdminController(AdminService adminService) {
//        this.adminService = adminService;
//    }

//    @ApiOperation("登录")
//    @CacheLock(autoDelete = false, prefix = "login_check")
//    @PostMapping("/login")
//    public void authLogin(@RequestBody @Valid LoginParam loginParam) {
//        adminService.authenticate(loginParam);
//    }

//    @ApiOperation("登出")
//    @CacheLock(autoDelete = false)
//    @PostMapping("/logout")
//    public void logout() {
//        adminService.clearToken();
//    }
//
//    @ApiOperation("刷新 Token")
//    @PostMapping("refresh/{refreshToken}")
//    @CacheLock(autoDelete = false)
//    public AuthToken refresh(@PathVariable("refreshToken") String refreshToken) {
//        return adminService.refreshToken(refreshToken);
//    }

}
