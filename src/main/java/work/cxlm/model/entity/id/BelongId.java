package work.cxlm.model.entity.id;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Belong 实体联合主键类
 * created 2020/11/16 22:56
 *
 * @author Chiru
 */
@Embeddable
@Data
@EqualsAndHashCode
public class BelongId implements Serializable {

    @Column(name = "club_id", nullable = false)
    private Integer clubId;

    @Column(name = "room_id", nullable = false)
    private Integer roomId;
}
