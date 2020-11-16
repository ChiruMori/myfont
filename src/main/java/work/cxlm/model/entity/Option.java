package work.cxlm.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import work.cxlm.model.enums.OptionType;

import javax.persistence.*;

/**
 * created 2020/11/9 15:12
 *
 * @author Chiru
 */
@Data
@Entity
@Table(name = "system_options")
@ToString
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Option extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "work.cxlm.model.entity.support.CustomIdGenerator")
    private Integer id;

    // 选项类型
    @Column(name = "type")
    @ColumnDefault("0")
    private OptionType type;

    @Column(name = "option_key", length = 100, nullable = false)
    private String key;

    @Column(name = "option_value", nullable = false)
    @Lob
    private String value;

    private Option(String key, String value) {
        this.key = key;
        this.value = value;
    }

    private Option(OptionType type, String key, String value) {
        this.type = type;
        this.key = key;
        this.value = value;
    }

    @PrePersist
    public void prePersist() {
        super.prePersist();
        if (type == null) {
            type = OptionType.INTERNAL;
        }
    }
}
