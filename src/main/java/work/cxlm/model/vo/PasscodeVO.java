package work.cxlm.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 管理员登录使用的一次性 passcode 传输对象
 * created 2020/11/18 19:47
 *
 * @author Chiru
 */
@Data
public class PasscodeVO {

    @ApiModelProperty("管理员登录管理后台使用的一次性 passcode")
    private String passcode;
}
