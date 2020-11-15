package work.cxlm.controller.admin.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import work.cxlm.annotation.DisableOnCondition;
import work.cxlm.model.dto.UserDTO;
import work.cxlm.model.entity.User;
import work.cxlm.model.params.PasswordParam;
import work.cxlm.model.params.UserParam;
import work.cxlm.model.support.BaseResponse;
import work.cxlm.model.support.UpdateCheck;
import work.cxlm.service.UserService;
import work.cxlm.utils.ValidationUtils;

import javax.validation.Valid;

/**
 * created 2020/11/15 10:45
 *
 * @author johnniang
 * @author Chiru
 */
@RestController
@RequestMapping("/font/api/admin/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @ApiOperation("获取用户信息")
    @GetMapping("profiles")
    public UserDTO getProfile(User user) {
        return new UserDTO().convertFrom(user);
    }

    @ApiOperation("更新用户信息")
    @PutMapping("profiles")
    @DisableOnCondition
    public UserDTO updateProfile(@RequestBody UserParam userParam, User user) {
        ValidationUtils.validate(userParam, UpdateCheck.class);
        userParam.update(user);
        return new UserDTO().convertFrom(userService.update(user));
    }

    @ApiOperation("修改用户密码")
    @PutMapping("profiles/password")
    @DisableOnCondition
    public BaseResponse<String> updatePassword(@RequestBody @Valid PasswordParam passwordParam, User user) {
        userService.updatePassword(passwordParam.getOldPassword(), passwordParam.getNewPassword(), user.getId());
        return BaseResponse.ok("密码修改成功");
    }
}
