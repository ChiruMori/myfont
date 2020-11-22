package work.cxlm.service.impl;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import work.cxlm.cache.AbstractStringCacheStore;
import work.cxlm.config.QfzsProperties;
import work.cxlm.exception.AuthenticationException;
import work.cxlm.exception.BadRequestException;
import work.cxlm.exception.ForbiddenException;
import work.cxlm.model.entity.Room;
import work.cxlm.model.entity.TimePeriod;
import work.cxlm.model.entity.User;
import work.cxlm.model.enums.RoomState;
import work.cxlm.model.enums.TimeState;
import work.cxlm.repository.RoomRepository;
import work.cxlm.repository.TimeRepository;
import work.cxlm.security.context.SecurityContextHolder;
import work.cxlm.service.JoiningService;
import work.cxlm.service.TimeService;
import work.cxlm.service.base.AbstractCrudService;


/**
 * @program: myfont
 * @author: beizi
 * @create: 2020-11-20 12:52
 * @application :
 * @Version 1.0
 **/
@Slf4j
@Service
public class TimeServiceImpl extends AbstractCrudService<TimePeriod, Integer> implements TimeService {

    private final RoomRepository roomRepository;
    private final QfzsProperties qfzsProperties;
    private final ApplicationEventPublisher eventPublisher;
    private final AbstractStringCacheStore cacheStore;
    private final JoiningService joiningService;
    private final TimeRepository timeRepository;

    public TimeServiceImpl(RoomRepository roomRepository,
                           QfzsProperties qfzsProperties,
                           ApplicationEventPublisher eventPublisher,
                           AbstractStringCacheStore cacheStore,
                           JoiningService joiningService,
                           TimeRepository timeRepository) {
        super(timeRepository);
        this.roomRepository = roomRepository;
        this.qfzsProperties = qfzsProperties;
        this.eventPublisher = eventPublisher;
        this.cacheStore = cacheStore;
        this.joiningService = joiningService;
        this.timeRepository = timeRepository;
    }


    /*
     *
     *
     * @description: 判断进入预约模块的成员信息是否过期
     * @param null
     * @return:
     * @time: 2020/11/21 19:47
     */
    public @NonNull User getUser() {
        return SecurityContextHolder.getCurrentUser().orElseThrow(
                () -> new AuthenticationException("用户登录凭证无效"));
    }

    public @NonNull Room getRoom(@NonNull Integer roomId){
        /*
         *
         *
         * @description:  通过roomId找到活动室
         * @param roomId  活动室编号
         * @return: work.cxlm.model.entity.Room
         * @time: 2020/11/21 21:39
         */
        return roomRepository.findById(roomId).orElseThrow(() -> new BadRequestException("找不到给定编号的活动室，请输入正确的活动室编号"));
    }

    public @NonNull TimePeriod getTimePeriod(@NonNull Integer timeId){
        /*
         *
         *
         * @description: 通过时间段编号找到时间段
         * @param timeId
         * @return: work.cxlm.model.entity.TimePeriod
         * @time: 2020/11/21 21:38
         */
        return timeRepository.findById(timeId).orElseThrow(() -> new BadRequestException("找不到该时间段，请输入正确的时间段编号"));
    }

    public @NonNull Room getRoomByTimeId(@NonNull Integer timeId){
        /*
         *
         *
         * @description:  通过timeId确定活动室
         * @param timeId
         * @return: work.cxlm.model.entity.Room
         * @time: 2020/11/21 21:38
         */
        TimePeriod timePeriod = timeRepository.findById(timeId).orElseThrow(() -> new BadRequestException("找不到该时间段，请输入正确的时间段编号"));
        Integer roomId =timePeriod.getRoomId();
        return roomRepository.findById(roomId).orElseThrow(() -> new BadRequestException("找不到给定编号的活动室，请输入正确的活动室编号"));

    }

    @Override
    /*
     *
     *
     * @description:
     * @param roomId  活动室的id
     * @param timeId  活动室某一时间段的id
     * @return: work.cxlm.model.entity.TimePeriod  返回时间段
     * @time: 2020/11/21 16:48
     */
    public @NonNull TimePeriod querryRoomTime(@NonNull Integer roomId, @NonNull Integer timeId) {
        Assert.isNull(roomId, "活动室编号不能为空！");
        Assert.isNull(timeId, "活动室时间段编号不能为空！");
        @NonNull Room room = getRoom(roomId);
        return room.getTimePeriod().get(timeId);

    }

    @Override
    /*
     *
     *
     * @description: 成员预订活动室某一个时间段
     * @param roomId
     * @param timeId
     * @return: work.cxlm.model.entity.TimePeriod
     * @time: 2020/11/21 19:46
     */
    public @NonNull TimePeriod orderRoom(@NonNull Integer roomId, @NonNull Integer timeId) {
        Assert.isNull(roomId, "活动室编号不能为空！");
        Assert.isNull(timeId, "活动室时间段编号不能为空！");

        @NonNull User user = getUser();
        if (user != null) {
            @NonNull TimePeriod timePeriod = querryRoomTime(roomId, timeId);
            @NonNull Room room = getRoom(roomId);
            if (timePeriod != null) {
                if (timePeriod.getTimeState().isIdle()) {  //是空闲
                    timePeriod.setTimeState(TimeState.Time_ORDER);  // 活动室状态变为已经被预约
                    timePeriod.setUserId(user.getId());
                    updateRoom(roomId);
                    log.info("[{}]--预约--[{}]--活动室的--[{}]<<<>>>[{}] ", user.getRealName(), room.getName(),timePeriod.getStartTime(),timePeriod.getEndTime());
                    return timePeriod;
                } else {
                    throw new ForbiddenException("该时间段已经被预定，不能预定!");
                }
            }
            return null;
        } else {
            throw new ForbiddenException("登录信息已丢失!");
        }

    }

    @Override
    /*
     *
     *
     * @description: 成员关注某一个活动室的时间段
     * @param roomId
     * @param timeId
     * @return: work.cxlm.model.entity.TimePeriod
     * @time: 2020/11/21 19:46
     */
    public TimePeriod attRoom(@NonNull Integer roomId, @NonNull Integer timeId) {
        Assert.isNull(roomId, "活动室编号不能为空！");
        Assert.isNull(timeId, "活动室时间段编号不能为空！");
        @NonNull User user = getUser();
        @NonNull Room room = getRoom(roomId);
        if (user != null) {
            @NonNull TimePeriod timePeriod = querryRoomTime(roomId, timeId);
            timePeriod.setTimeState(TimeState.Time_ATT);
            timePeriod.setUserId(user.getId());
            updateRoom(roomId);
            log.info("[{}]--关注--[{}]--活动室的--[{}]<<<>>>[{}] ", user.getRealName(), room.getName(),timePeriod.getStartTime(),timePeriod.getEndTime());
            return timePeriod;

        }
        return null;

    }

    @Override
    /*
     *
     *
     * @description: 成员取消预订活动室的某一个时间段
     * @param roomId
     * @param timeId
     * @return: work.cxlm.model.entity.TimePeriod
     * @time: 2020/11/21 19:45
     */
    public TimePeriod noOrderRoom(@NonNull Integer roomId, @NonNull Integer timeId) {
        Assert.isNull(roomId, "活动室编号不能为空！");
        Assert.isNull(timeId, "活动室时间段编号不能为空！");

        getUser();
        @NonNull TimePeriod timePeriod = querryRoomTime(roomId, timeId);
        timePeriod.setTimeState(TimeState.Time_IDLE);
        timePeriod.setUserId(null);
        updateRoom(roomId);
        log.debug("456");

        return timePeriod;

    }

    @Override
    /*
     *
     *
     * @description: 成员取消时间段的关注
     * @param roomId
     * @param timeId
     * @return: work.cxlm.model.entity.TimePeriod
     * @time: 2020/11/21 19:45
     */
    public TimePeriod noAttRoom(@NonNull Integer roomId, @NonNull Integer timeId) {
        Assert.isNull(roomId, "活动室编号不能为空！");
        Assert.isNull(timeId, "活动室时间段编号不能为空！");

        getUser();
        @NonNull TimePeriod timePeriod = querryRoomTime(roomId, timeId);
        timePeriod.setTimeState(TimeState.Time_ORDER);
        updateRoom(roomId);
        timePeriod.setUserId(null);
        log.debug("123");
        return timePeriod;

    }


    @Override
    /*
     *
     *
     * @description:  更新房间信息
     * @param roomId
     * @return: void
     * @time: 2020/11/21 16:50
     */
    public boolean updateRoom(Integer roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new BadRequestException("查询不到活动室信息，请您核对活动室编号后使用。").setErrorData(roomId));
        roomRepository.save(room);
        log.debug("");
        return true;
    }

    @Override
    /*
     *
     *
     * @description:  管理员禁止活动室被使用
     * @param roomId
     * @return: boolean
     * @time: 2020/11/21 19:44
     */
    public boolean adminStop(@NonNull Integer roomId) {
        Assert.isNull(roomId, "活动室编号不能为空!");
        @NonNull User user = getUser();
        if (user.getRole().isAdminRole()) {
            Room room = roomRepository.findById(roomId).orElseThrow(
                    () -> new BadRequestException("查询不到活动室信息，请您核对活动室编号后使用。").setErrorData(roomId));
            room.setRoomState(RoomState.BAN);
            log.debug("管理员：[{}]--禁止--[{}]--活动室的预约 ", user.getRealName(), room.getName());
            return true;
        }
        return false;
    }

    @Override
    /*
     *
     *
     * @description: 管理员恢复房间的使用
     * @param roomId
     * @return: boolean
     * @time: 2020/11/21 19:45
     */
    public boolean adminAllow(@NonNull Integer roomId) {
        Assert.isNull(roomId, "活动室编号不能为空!");
        @NonNull User user = getUser();
        if (user.getRole().isAdminRole()) {
            Room room = roomRepository.findById(roomId).orElseThrow(
                    () -> new BadRequestException("查询不到活动室信息，请您核对活动室编号后使用。").setErrorData(roomId));
            room.setRoomState(RoomState.ALLOW);
            log.debug("");
            return true;
        }
        return false;
    }


}
