package work.cxlm.model.entity.id;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 实体 Follow 联合主键
 * created 2020/11/16 23:02
 *
 * @author Chiru
 */
@Data
@EqualsAndHashCode
@Embeddable
public class FollowId implements Serializable {

    @Column(name = "time_unit_id")
    private Integer timeUnitId;

    @Column(name = "user_id")
    private Integer userId;
}
