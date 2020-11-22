package work.cxlm.model.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import work.cxlm.model.support.CreateCheck;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * created 2020/11/21 14:53
 *
 * @author Chiru
 */
@Data
public class AuthorityParam {

    @NotEmpty(message = "学号不能为空", groups = CreateCheck.class)
    private Long studentNo;

    @ApiModelProperty("是否为系统管理员权限")
    private boolean systemAdmin;

    @ApiModelProperty("管理的社团 ID，授权为系统管理员时，此项目可缺省")
    private Integer clubId;
}
