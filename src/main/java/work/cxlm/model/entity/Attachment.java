package work.cxlm.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author cxlm
 * created 2020/10/18 11:02
 * <p>
 * 文件实体类
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@ToString
@Table(name = "attachment")
public class Attachment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "work.cxlm.model.entity.support.CustomIdGenerator")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "path", length = 1023, nullable = false)
    private String path;

    @Column(name = "type", length = 127, nullable = false)
    private String type;

    @Column(name = "size", nullable = false)
    private Long size;

}
