package work.cxlm.model.params;


import lombok.Data;
import org.hibernate.validator.constraints.Length;
import work.cxlm.model.dto.base.InputConverter;
import work.cxlm.model.entity.Room;
import work.cxlm.model.enums.TimeState;
import java.util.Date;

/**
 * @program: myfont
 * @author: beizi
 * @create: 2020-11-22 17:39
 * @application :
 * @Version 1.0
 **/
@Data
public class TimeParam implements InputConverter<Room> {


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

    @Length(max = 8)
    private String showText;

    /**
     * 当前时段是否已签到
     */

    private Boolean signed;

    /**
     * 用户是否在当前时段迟到，缺席并不算迟到
     */

    private Boolean late;

    /*
     * 当前时间段的状态：空闲  预定  预定且被关注  禁用
     * */
     //默认空闲
    private TimeState timeState;

}
