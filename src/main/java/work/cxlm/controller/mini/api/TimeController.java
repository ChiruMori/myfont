package work.cxlm.controller.mini.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import work.cxlm.service.TimeService;

/**
 * 时段预订相关 API 接口
 * created 2020/11/16 19:24
 *
 * @author Chiru
 */
@Slf4j
@RestController
@RequestMapping("/key3/time/")
public class TimeController {

    private TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }
}
