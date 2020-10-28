package work.cxlm.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author cxlm
 * created 2020/10/18 14:56
 * <p>
 * 字体文件实体
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@ToString
@Table(name = "font")
public class Font extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "work.cxlm.model.entity.support.CustomIdGenerator")
    private Integer id;

    @Column(name = "name", unique = true)
    private String name;

    // 附件 ID
    @Column(name = "att_id")
    private Integer attId;

}
