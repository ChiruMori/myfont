package work.cxlm.model.params;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 重设密码表单
 * created 2020/10/21 15:19
 *
 * @author ryanwang
 * @author cxlm
 */
@Data
public class ResetPasswordParam {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "邮箱不能为空")
    private String email;

    private String code;

    private String password;
}
