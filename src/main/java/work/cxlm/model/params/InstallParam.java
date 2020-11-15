package work.cxlm.model.params;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Range;
import work.cxlm.model.support.CreateCheck;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 安装系统时填写的表单
 * created 2020/11/15 10:59
 *
 * @author johnniang
 * @author Chiru
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class InstallParam extends UserParam {

    @NotBlank(message = "发件服务器地址不能为空", groups = CreateCheck.class)
    @Size(max = 255, message = "发件服务器地址长度不能超过 {max}", groups = CreateCheck.class)
    private String emailHost;

    @NotBlank(message = "发件协议不能为空", groups = CreateCheck.class)
    @Size(max = 127, message = "发件协议长度不能超过 {max}", groups = CreateCheck.class)
    private String emailProtocol;

    @NotBlank(message = "发件服务端口不能为空", groups = CreateCheck.class)
    @Range(min = 0, max = 65535, message = "端口范围非法")
    private Integer emailSslPort;

    @Email(message = "电子邮件地址的格式不正确", groups = CreateCheck.class)
    @NotBlank(message = "电子邮件地址不能为空", groups = CreateCheck.class)
    @Size(max = 127, message = "电子邮件的字符长度不能超过 {max}", groups = CreateCheck.class)
    private String emailUsername;

    @NotBlank(message = "发件邮箱口令不能为空", groups = CreateCheck.class)
    @Size(max = 255, message = "发件邮箱口令长度不能超过 {max}", groups = CreateCheck.class)
    private String emailPassword;

    @NotBlank(message = "发件名不能为空", groups = CreateCheck.class)
    @Size(max = 255, message = "发件名不能为空长度不能超过 {max}", groups = CreateCheck.class)
    private String fromName;
}
