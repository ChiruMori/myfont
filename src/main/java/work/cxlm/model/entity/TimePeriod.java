package work.cxlm.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.Date;

/**
 * 时段实体类
 * created 2020/11/16 19:32
 *
 * @author Chiru
 */
@Data
@Entity
@Table(name = "time_period")
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class TimePeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "work.cxlm.model.entity.support.CustomIdGenerator")
    private Integer id;

    /**
     * 时段开始时间
     */
    @Column(name = "start_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    /**
     * 时段结束时间
     */
    @Column(name = "end_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    /**
     * 关联的活动室 ID
     */
    @Column(name = "room_id", nullable = false)
    private Integer roomId;

    /**
     * 预订了当前时段的用户，可以为空，
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 显示在时间表格中的文本
     */
    @Column(name = "show_text", nullable = false)
    @Length(max = 8)
    private String showText;

    /**
     * 当前时段是否已签到
     */
    @Column(name = "signed")
    @ColumnDefault("0")
    private Boolean signed;

    /**
     * 用户是否在当前时段迟到，缺席并不算迟到
     */
    @Column(name = "late")
    @ColumnDefault("1")
    private Boolean late;
}
