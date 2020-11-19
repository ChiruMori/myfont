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

    @NotBlank(message = "用户登录凭证不能为空", groups = CreateCheck.class)
    private String code;

}

