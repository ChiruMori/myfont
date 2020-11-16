package work.cxlm.model.entity.id;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Join 实体，联合主键类
 * created 2020/11/16 22:53
 *
 * @author Chiru
 */
@Embeddable
@Data
@EqualsAndHashCode
public class JoiningId implements Serializable {
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "club_id", nullable = false)
    private Integer clubId;
}
