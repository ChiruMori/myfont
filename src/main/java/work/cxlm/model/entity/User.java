package work.cxlm.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户实体类，本程序中，只应存在一个合法用户，但需要兼容多个用户的情况
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username", length = 50, nullable = false)
    private String username;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", length = 127)
    private String email;

    /**
     * 当前用户被停用的到期时间
     */
    @Column(name="expire_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expireTime;


    @Override
    public void prePersist() {
        super.prePersist();

        if (email == null) {
            email = "";
        }
    }

}
