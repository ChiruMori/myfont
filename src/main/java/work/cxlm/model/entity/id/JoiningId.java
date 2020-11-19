package work.cxlm.model.entity.id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
@AllArgsConstructor
@NoArgsConstructor
public class JoiningId implements Serializable {
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "club_id", nullable = false)
    private Integer clubId;
}
