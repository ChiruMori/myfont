package work.cxlm.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.util.StringUtils;
import work.cxlm.model.enums.UserGender;
import work.cxlm.model.support.QfzsConst;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户实体类
 * FIXME: 修改需求相关的用户字段
 * created 2020/10/21 15:03
 *
 * @author cxlm
 */
@Data
@Entity
@Table(name = "users")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "work.cxlm.model.entity.support.CustomIdGenerator")
    private Integer id;

    /**
     * 微信 ID
     */
    @Column(name = "username", length = 30)
    private String wxId;

    /**
     * 学工号
     */
    @Column(name = "student_no", length = 15)
    private String studentNo;

    /**
     * 学院名
     */
    @Column(name = "institute", length = 50)
    private String institute;

    /**
     * 专业名
     */
    @Column(name = "major", length = 50)
    private String major;

    /**
     * 入学年份
     */
    @Column(name = "enroll_year")
    @Range(min = 1920, max = 2120)
    private int enrollYear;

    /**
     * 真实姓名
     */
    @Column(name = "real_name")
    private String realName;

    /**
     * 用户性别
     */
    @Column(name = "gender")
    @ColumnDefault("0")
    private UserGender gender;

    /**
     * 用户头像
     */
    @Column(name = "head")
    private String head;

    /**
     * 个性签名
     */
    @Column(name = "sign")
    private String sign;

    /**
     * 用户邮箱
     */
    @Column(name = "email", length = 60)
    private String email;

    @Override
    public void prePersist() {
        super.prePersist();

        if (email == null) {
            email = "";
        }

        if (StringUtils.isEmpty(sign)) {
            sign = QfzsConst.DEFAULT_USER_SIGNATURE;
        }
    }

}
