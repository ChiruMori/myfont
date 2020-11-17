package work.cxlm.model.params;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import work.cxlm.model.dto.base.InputConverter;
import work.cxlm.model.entity.User;
import work.cxlm.model.support.CreateCheck;
import work.cxlm.model.support.UpdateCheck;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 用户登录初始化表单
 * created 2020/10/21 23:29
 *
 * @author johnniang
 * @author cxlm
 */
@Data
public class UserLoginParam {

    @NotBlank(message = "用户名不能为空", groups = CreateCheck.class)
    @Size(max = 100, message = "用户名的字符长度不能超过 {max}", groups = CreateCheck.class)
    private String nickName;

    @Email(message = "电子邮件地址的格式不正确", groups = CreateCheck.class)
    @NotBlank(message = "电子邮件地址不能为空", groups = CreateCheck.class)
    @Size(max = 127, message = "电子邮件的字符长度不能超过 {max}", groups = CreateCheck.class)
    private String avatarUrl;

    @Range(min = 0, max = 2, groups = CreateCheck.class)
    private Integer gender;

    @NotBlank(message = "用户登录凭证不能为空", groups = CreateCheck.class)
    private String code;

}

