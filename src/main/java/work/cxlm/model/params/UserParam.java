package work.cxlm.model.params;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Range;
import work.cxlm.model.dto.base.InputConverter;
import work.cxlm.model.entity.User;
import work.cxlm.model.enums.UserGender;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * 用户更新信息时填写的表单，如果前端要求指定字段名，需要重写 convertTo 方法
 * created 2020/11/17 22:48
 *
 * @author Chiru
 */
@Data
public class UserParam implements InputConverter<User> {

    @Size(max = 30, message = "openId 长度不能超过 {max}")
    private String wxId;

    @Size(max = 100, message = "微信名长度不能超过 {max}")
    private String wxName;

    @Size(max = 15, message = "学工号长度不能超过 {max}")
    private String studentNo;

    @Size(max = 50, message = "学院名长度不能超过 {max}")
    private String institute;

    @Size(max = 60, message = "专业名长度不能超过 {max}")
    private String major;

    @Max(value = 2120, message = "错误的入学年份")
    @Min(value = 1920, message = "错误的入学年份")
    private int enrollYear;

    @Size(max = 30, message = "真实姓名长度不能超过 {max}")
    private String realName;

    private UserGender gender;

    @Size(max = 255, message = "头像链接长度不能超过 {max}")
    private String head;

    @Size(max = 255, message = "个性签名长度不能超过 {max}")
    private String sign;

    @Size(max = 60, message = "邮箱地址长度不能超过 {max}")
    private String email;

}
