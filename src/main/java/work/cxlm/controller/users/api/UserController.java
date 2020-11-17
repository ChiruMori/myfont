package work.cxlm.controller.users.api;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import work.cxlm.annotation.DisableOnCondition;
import work.cxlm.annotation.OpenIdUser;
import work.cxlm.model.dto.UserDTO;
import work.cxlm.model.entity.User;
import work.cxlm.model.params.UserLoginParam;
import work.cxlm.model.params.UserParam;
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
@RequestMapping("/key3/users/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation("获取用户信息，注意，包含 @OpenIdUser 注解的方法需要在请求时将 openId 作为参数传递")
    @GetMapping("/info")
    public UserDTO getProfile(@OpenIdUser User user) {
        return new UserDTO().convertFrom(user);
    }

    @ApiOperation("更新用户信息，用户微信相关信息的绑定在本方法中首次进行")
    @PutMapping("/info")
    public UserDTO uploadUserInfo(@OpenIdUser User user, @Valid @RequestBody UserParam userParam) {
        return new UserDTO().convertFrom(userService.updateUserByParam(user, userParam));
    }

    @ApiOperation("用户登录，如果使用的登录凭证有效则会得到用户信息，否则得到 null")
    @GetMapping("/login")
    public UserDTO userLogin(@Valid @RequestBody UserLoginParam userLoginParam) {
        String openId = userService.getOpenIdByCode(userLoginParam.getCode());
        User nowUser = userService.login(openId);
        UserDTO res = new UserDTO();
        if (nowUser != null) {
            res.convertFrom(nowUser);
        }
        return res;
    }
}
