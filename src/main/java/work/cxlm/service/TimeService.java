package work.cxlm.service;

import lombok.NonNull;
import org.springframework.lang.Nullable;
import work.cxlm.model.entity.TimePeriod;
import work.cxlm.service.base.CrudService;

/**
 * 预约时段业务处理接口
 * created 2020/11/16 23:05
 *
 * @author Chiru
 */
public interface TimeService extends CrudService<TimePeriod, Integer> {

    //查找活动室某一时间段
    @NonNull
    TimePeriod querryRoomTime(@NonNull Integer roomId,@NonNull Integer timeId);

    // 预定活动室某一时间段
    @Nullable
    TimePeriod orderRoom(@NonNull Integer roomId,@NonNull Integer timeId);

    //关注活动室某一时间段
    TimePeriod attRoom(@NonNull Integer roomId,@NonNull Integer timeId);

    //取消预订活动室某一时间段
    TimePeriod noOrderRoom(@NonNull Integer roomId,@NonNull Integer timeId);

    //取消关注活动室某一时间段
    TimePeriod noAttRoom(@NonNull Integer roomId,@NonNull Integer timeId);

    //更新活动室使用信息
    boolean updateRoom(Integer roomId);

    //管理员禁用活动室
    boolean adminStop(@NonNull Integer roomId);

    //管理员恢复活动室使用
    boolean adminAllow(@NonNull Integer roomId);


}
