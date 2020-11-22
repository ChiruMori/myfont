package work.cxlm.model.params;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import work.cxlm.model.support.CreateCheck;
import work.cxlm.model.support.UpdateCheck;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * created 2020/11/21 21:32
 *
 * @author Chiru
 */
@Data
public class NewMemberParam {

    @NotBlank(message = "必须填写学号", groups = {CreateCheck.class, UpdateCheck.class})
    private Long studentNo;

    @NotBlank(message = "必须指定社团", groups = {CreateCheck.class, UpdateCheck.class})
    private Integer clubId;

    @NotBlank(message = "真实姓名不能为空，删除时无需填写", groups = CreateCheck.class)
    @Size(max = 50, message = "真实姓名长度必须小于 {max}")
    private String realName;

    @Size(max = 50, message = "职务名长度必须小于 {max}", groups = CreateCheck.class)
    private String position;

    private Boolean admin;
}
