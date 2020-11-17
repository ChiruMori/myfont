package work.cxlm.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Range;
import work.cxlm.model.dto.base.OutputConverter;
import work.cxlm.model.entity.User;
import work.cxlm.model.enums.UserGender;

import javax.persistence.Column;
import java.util.Date;

/**
 * created 2020/11/15 10:47
 *
 * @author johnniang
 * @author Chiru
 */
@Data
@ToString
@EqualsAndHashCode
public class UserDTO implements OutputConverter<UserDTO, User> {

    private String wxId;

    private String wxName;

    private String studentNo;

    private String institute;

    private String major;

    private int enrollYear;

    private String realName;

    private UserGender gender;

    private String head;

    private String sign;

    private String email;

}
