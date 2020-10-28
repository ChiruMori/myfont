package work.cxlm.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author cxlm
 * created 2020/10/17 16:26
 * <p>
 * 汉字的加密写法，就是图片
 */
@Data
@Entity
@ToString
@EqualsAndHashCode(callSuper = true)
@Table(name = "img")
public class Shape extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "work.cxlm.model.entity.support.CustomIdGenerator")
    private Integer id;

    /**
     * 图片访问路径
     */
    @Column(name = "url", length = 1023, nullable = false)
    private String url;

    /**
     * 源文件信息
     */
    @Column(name = "att_id", nullable = false)
    private Integer attId;
}
