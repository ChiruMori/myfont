package work.cxlm.model.dto;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @program: myfont
 * @author: beizi
 * @create: 2020-11-21 20:43
 * @application :
 * @Version 1.0
 **/
@Data
public class TimeSimpleDTO {

    private Integer id;

    /**
     * 时段开始时间
     */

    private Date startTime;

    /**
     * 时段结束时间
     */

    private Date endTime;

    /**
     * 关联的活动室 ID
     */

    private Integer roomId;

    /**
     * 预订了当前时段的用户，可以为空，
     */

    private Integer userId;

    /**
     * 显示在时间表格中的文本
     */

    private String showText;

    /**
     * 当前时段是否已签到
     */

    private Boolean signed;

    /**
     * 用户是否在当前时段迟到，缺席并不算迟到
     */

    private Boolean late;

}
