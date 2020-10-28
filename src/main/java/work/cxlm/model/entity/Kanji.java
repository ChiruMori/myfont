package work.cxlm.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author cxlm
 * created 2020/10/17 14:07
 * <p>
 * 汉字实体类
 */
@Data
@Entity
@ToString
@EqualsAndHashCode(callSuper = true)
@Table(name = "kanji")
public class Kanji extends BaseEntity {

    /**
     * 汉字编码，作为主键使用
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "work.cxlm.model.entity.support.CustomIdGenerator")
    private int id;

    /**
     * 汉字的字符串值
     */
    @Column(name = "val", unique = true)
    private String val;

}
