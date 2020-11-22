package work.cxlm.model.params;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 管理员登录表单参数
 * created 2020/10/21 15:07
 *
 * @author johnniang
 * @author cxlm
 */
@Data
@ToString
public class LoginParam {

    @NotBlank(message = "授权学号不能为空")
    private Long studentNo;

    @NotBlank(message = "登录口令不能为空")
    @Size(max = 10, message = "登录口令长度不能超过 {max}")
    private String passcode;

}
