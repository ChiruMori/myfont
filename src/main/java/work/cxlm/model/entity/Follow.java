package work.cxlm.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import work.cxlm.model.entity.id.FollowId;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 关系实体，用户关注了某个时段
 * created 2020/11/16 23:01
 *
 * @author Chiru
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
@Entity
@Table(name = "follow")
public class Follow extends BaseEntity {

    @Id
    private FollowId id;
}
